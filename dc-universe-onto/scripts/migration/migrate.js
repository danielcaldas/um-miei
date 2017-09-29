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

		// Dump result to file
		var fs = require('fs');
		var wstream = fs.createWriteStream('tmp-bad.json');
		wstream.write(JSON.stringify(content));
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
