var sys = require('util');
var exec = require('child_process').exec;

var ontology="";
var query="";
var collectionName="";
var nargs=0;
var username;
var password;

process.argv.forEach(function (val, index, array) {
	nargs=index;
	switch (index) {
		case 2:
		if(val!=="") ontology = val;
		else {
			console.log("Sorry, but you didn't provide an OWL file.");
			process.exit();
		}
		break;
		case 3:
		if(val!=="") query = val;
		else {
			console.log("Sorry, but you didn't provide an OWL file.");
			process.exit();
		}
		break;
		case 4:
		if(val!=="") collectionName = val;
		else {
			console.log("Sorry, but you didn't provide an OWL file.");
			process.exit();
		}
		break;
		case 5:
		if(val!=="") username = val;
		else {
			console.log("Sorry, but you didn't provide an username.");
			process.exit();
		}
		break;
		case 6:
		if(val!=="") password = val;
		else {
			console.log("Sorry, but you didn't provide a password.");
			process.exit();
		}
		break;
		default: break;
	}
});

var MODE = "LOCAL";

if(nargs==6) {
	MODE = "REMOTE";
}

// Check arg ontology file
if(!(ontology.match(/.+owl/g))) {
	console.log("Sorry. Not an OWL file.");
	process.exit();
}

// Query the ontology and extract information
child = exec('arq --out JSON --data '+ontology+' --query '+query, {maxBuffer: 1024 * 1000}, function (error, stdout, stderr) {
	if (error !== null) {
		console.log('exec error: ' + error);
	} else {
		var res = JSON.parse(stdout);

		// Get bindings from result set
		var content = res.results.bindings;
		var chars = content;
		var characters = {};
		var colors = {};

		for(var i=0; i < chars.length; i++) {
			var c = chars[i];
			if(c.cityName) {
				if(!characters[c.id.value]) {
					characters[c.id.value] = {};

					/*------------------------------------------------
					Positioning Algorithm
					-------------------------------------------------*/
					var diff=0;
					var scale = Math.floor((Math.random() * 50) + 2);
					var signalLat, signalLong;

					var tmp = Math.floor((Math.random() * 10) + 1);
					if(tmp<5) signalLat=-1;
					else if(tmp>7) signalLat=1;
					else signalLat=0;

					tmp = Math.floor((Math.random() * 10) + 1);
					if(tmp<5) signalLong=1;
					else if(tmp>7) signalLong=-1;
					else signalLong=0;

					var addToNewLat = scale*0.1*signalLat;
					var addToNewLon = scale*0.1*signalLong;
					/*------------------------------------------------*/

					characters[c.id.value].id = c.id.value;
					if(c.name) characters[c.id.value].name = c.name.value;
					if(c.realName) characters[c.id.value].realName = c.realName.value;
					if(c.thumbnail) characters[c.id.value].thumbnail = c.thumbnail.value;
					if(c.shortDescription) characters[c.id.value].shortDescription = c.shortDescription.value;
					if(c.facebook) characters[c.id.value].facebook = c.facebook.value;
					if(c.occupation) characters[c.id.value].occupation = c.occupation.value;
					if(c.creationYear) characters[c.id.value].creationYear = c.creationYear.value;
					characters[c.id.value].city = c.cityName.value;
					if(c.latitude && c.longitude) {
						var latNum = parseFloat(c.latitude.value);
						var longNum = parseFloat(c.longitude.value);
						var newLat = latNum + addToNewLat;
						var newLon = longNum + addToNewLon;

						if(c.latitude) characters[c.id.value].latitude = newLat.toString();
						if(c.longitude) characters[c.id.value].longitude = newLon.toString();
						if(characters[c.id.value].longitude && characters[c.id.value].latitude) {
							characters[c.id.value].position = characters[c.id.value].latitude+","+characters[c.id.value].longitude;
						}
					}
					if(c.realLocation) characters[c.id.value].realLocation = c.realLocation.value;
					var charColor = getRandomColor();
					while(colors[charColor]) {
						charColor = getRandomColor();
					}
					characters[c.id.value].color = charColor;
					// console.log(c.id.value+", lat: "+characters[c.id.value].latitude+", lon:"+characters[c.id.value].longitude);
				}
			}
		}

		var charactersArray = [];
		for(var char in characters) {
			charactersArray.push(characters[char]);
		}

		// Dump result to file
		var fs = require('fs');
		var wstream = fs.createWriteStream('tmp-bad.json');
		wstream.write(JSON.stringify(charactersArray));
		wstream.end();

		console.log('('+collectionName+') SPARQL query donne ... [OK]');
		// Import files into MongoDB with (--jsonArray param)
		if(MODE==="REMOTE") {
			child = exec('mongoimport -h ds011893.mlab.com:11893 -d heroku_64lffp80 -c '+collectionName+' -u '+username+' -p '+password+' --jsonArray --file tmp-bad.json', {maxBuffer: 1024 * 1000}, function (error, stdout, stderr) {
				if (error !== null) {
					console.log('exec error: ' + error);
				} else {
					console.log('('+collectionName+') Imported collection to remote MongoDB ... [OK]');
				}
			});
		} else if(MODE=="LOCAL") {
			child = exec('mongoimport --db dcdb --collection '+collectionName+' --jsonArray --file tmp-bad.json', {maxBuffer: 1024 * 1000}, function (error, stdout, stderr) {
				if (error !== null) {
					console.log('exec error: ' + error);
				} else {
					console.log('('+collectionName+') Imported collection to MongoDB ... [OK]');
				}
			});
		}
	}
});

function getRandomColor() {
  var letters = '0123456789ABCDEF'.split('');
  var color = '#';
  for (var i = 0; i < 6; i++ ) {
    color += letters[Math.floor(Math.random() * 16)];
  }
  return color;
}

function camelize(str){
  return str.replace(/\w\S*/g, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();});
}
