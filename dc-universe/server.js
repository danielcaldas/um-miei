var express = require('express');
var cluster = require('cluster');
var compression = require('compression');
var minify = require('express-minify');
var MongoClient = require('mongodb');
var path = require("path");
var bodyParser = require("body-parser");
var mongodb = require("mongodb");

var app = express();

// ---------------------------------------------------- Database Operations

var mongoURI = process.env.MONGODB_URI || "mongodb://localhost:27017/dcdb";

mongodb.MongoClient.connect(mongoURI, function (err, database) {
  if (err) {
    console.log(err);
    process.exit(1);
  }

  db = database;
  console.log("Database connection ready ["+mongoURI+"]");

  // Characters
  var collection = db.collection('characters');
  collection.find({}).toArray(function(err, chars) {
    getCharactersSuccessCallback(chars);
  });

  // Powers
  collection = db.collection('powers');
  collection.find({}).toArray(function(err, powers) {
    getCharactersPowersSuccessCallback(powers);
  });

  // Connections
  collection = db.collection('connections');
  collection.find({}).toArray(function(err, connections) {
    getCharactersConnectionsSuccessCallback(connections);
  });

  // Connections Weights
  collection = db.collection('weights');
  collection.find({}).toArray(function(err, weights) {
    getCharactersConnectionsWeightsSuccessCallback(weights);
  });

  if (cluster.isMaster) {
      var cpuCount = require('os').cpus().length;
      console.log('running on %d cpus: %s', cpuCount);
      for (var i = 0; i < cpuCount; i += 1) {
          cluster.fork();
      }

  } else {
      app.use(compression());
      app.use(minify());
      app.use(bodyParser.json());

      app.use(express.static(__dirname + '/public'));

      // Initialize the app.
      var server = app.listen(process.env.PORT || 8080, function () {
        var port = server.address().port;
        console.log("App now running on port", port);
      });
  }

});


// ---------------------------------------------------- Database Operations Callbaks
var characters = {};

var getCharactersSuccessCallback = function(chars) {
  for(var i=0; i < chars.length; i++) {
    characters[chars[i].id] = chars[i];
  }
};

var getCharactersPowersSuccessCallback = function(powers) {
  for(var i=0; i < powers.length; i++) {
    var p = powers[i];
    if(characters[p.charID.value]) {
      if(!characters[p.charID.value].powers) {
        characters[p.charID.value].powers = camelize(p.powerDesc.value);
      } else {
        characters[p.charID.value].powers += ", "+camelize(p.powerDesc.value);
      }
    }
  }
};

var getCharactersConnectionsSuccessCallback = function(connections) {
  var visitedKeys = {};
  for(var i=0; i < connections.length; i++) {
    var conn = connections[i];
    var c1 = characters[conn.c1ID.value];
    var c2 = characters[conn.c2ID.value];

    if(c1 && c2 && c1.latitude && c1.longitude && c2.latitude && c2.longitude) {
      var connID = c1.id+"-"+c2.id;
      var connIDInv = c2.id+"-"+c1.id;

      if(!visitedKeys[connID] && !visitedKeys[connIDInv]) {
        visitedKeys[connID] = 1;
        visitedKeys[connIDInv] = 1;

        if(!c1.connections) c1.connections = [];
        if(!c2.connections) c2.connections = [];

        var creationYear;
        if(c1.creationYear > c2.creationYear) creationYear = c1.creationYear;
        else creationYear = c2.creationYear;

        var geoFrom = c1.latitude+","+c1.longitude;
        var geoTo = c2.latitude+","+c2.longitude;

        var newconn = {
          id: connID,
          from: geoFrom,
          to: geoTo,
          toChar: c2.id,
          weight: 1,
          creationYear: creationYear
        };

        c1.connections.push(newconn);
        c2.connections.push(newconn);
      }
    }
  }
};

var getCharactersConnectionsWeightsSuccessCallback = function(connections) {
  for(var i=0; i < connections.length; i++) {
    var conn = connections[i];
    var cFrom = characters[conn.c1ID.value];
    var cTo = characters[conn.c2ID.value];
    if(cFrom && cTo && cFrom.connections && cTo.connections) {
      for(var j=0; j < cFrom.connections.length; j++) {
        var cFromConn = cFrom.connections[j];
        if(cFromConn.toChar===cTo.id) cFromConn.weight = (conn.weight.value * 0.6);
      }
    }
  }
};


// ----------------------------------------------------- Other functions

function camelize(str){
  return str.replace(/\w\S*/g, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();});
}


// ---------------------------------------------------- REST API endpoints

// Get all characters
app.get('/dc/characters', function (req, res) {
  res.send(characters);
});
