var http = require('http'), fs = require('fs');
var htmlPage = "index.html";

var server = http.createServer(function (request, response) {
    fs.readFile(htmlPage, 'utf-8', function (error, data) {
        response.writeHead(200, {'Content-Type': 'text/html'});
        response.write(data);
        response.end();
    });
}).listen(8080);

var io = require('socket.io').listen(server);

// General settings for running monitoring session
var PORT;
var IP_ADDRESS;
var session;

io.sockets.on('connection', function(socket) {
    socket.on('message_to_server', function(data) {
        if(data["fetch"]=="fetch") {
          io.sockets.emit("message_to_client",{ ipInDelivers: ppm[oids[0]].toString(),
                                                ipInReceives: ppm[oids[1]].toString(),
                                                ipInHdrErrors: packetsCounter[oids[2]].toString(),
                                                ipInAddrErrors: packetsCounter[oids[3]].toString(),
                                                pollingTime: pollingTime.toString()
                                              });
        } else if(data["stop"]=="stop"){
        	console.log("Client stoped monitoring session.");
        	htmlPage = "index.html";
        	// Close snmp session and server
        	session.close ();
        }
        else if(data["start"]=="start"){
          console.log("Client started monitoring session . . .");
          htmlPage = "monitor.html";
          // This mean we didn't start a session yet
          PORT = parseInt(data["port"]);
          IP_ADDRESS = data["ip"];
          createStartSession();
        }
    });
});

/*------------------------------------------------------------------------------------------------------*/
/*----------------------------------------- SNMP Code --------------------------------------------------*/
/*------------------------------------------------------------------------------------------------------*/

/*
* Create session according to user parameters
*/
function createStartSession() {
  var options = {
    port: PORT,
    retries: 1,
    timeout: 5000,
    transport: "udp",
    trapPort: 162,
    version: snmp.Version1
  };
  session = snmp.createSession(IP_ADDRESS, "public");
  updateData();
}

var snmp = require ("net-snmp");

// OIDs related to ip packages metrics
var oids = ["1.3.6.1.2.1.4.9", "1.3.6.1.2.1.4.3", "1.3.6.1.2.1.4.4", "1.3.6.1.2.1.4.5", "1.3.6.1.2.1.1.3"];
var oidsNames = ["ipInDelivers", "ipInReceives", "ipInHdrErrors", "ipInAddrErrors", "sysUpTime"];
var packetsCounter = [];

// Get system current time, to measure time between snmp requests
var begin = Date.now();

// Default polling time for SNMP requests is set to 2 second by default
var pollingTime = 2000;

var MAX_LIMIT_POLLTIME = 25000;

// Default min and max polling time for SNMP requests is set to 2 second by default
var maxPollTime, minPollTime;
minPollTime = maxPollTime = 2000;

var flagFirstUpdate=true;

// Store elapsed time since agente changed object value
var elapsedTimeTillChange = [];
var elapsedSysUpTimeTillChange = [];

// Save Date instance of last updated value for calculation of elapsedTimeTillChange
var timeStamps = [];
var sysUptimeStamps = [];

// Save packets means per minute for the various mib objects in oids[] (ppm - packets per minute)
var ppm = [];

// Initializing variables
for(var i=0; i < oids.length; i++) {
  elapsedTimeTillChange[oids[i]] = 0;
  elapsedSysUpTimeTillChange[oids[i]] = 0;
  timeStamps[oids[i]] = Date.now();
  sysUptimeStamps[oids[i]] = 0;
  packetsCounter[oids[i]] = 0;
  ppm[oids[i]] = 0;
}

function updateData() {
    session.getNext(oids, function (error, varbinds) {
        console.log("\n-----------------------------------------------\n");
        var elapsedTime = (Date.now() - begin);
        console.log("Tempo: "+parseFloat( elapsedTime * 0.001 ).toFixed(2)+" seconds");
        console.log("Polling time: "+pollingTime);
        console.log("Max Polling time: "+maxPollTime);
        console.log("Min Polling time: "+minPollTime);

        if (error) {
            console.log("\nClient disconnected ...\n");
        } else {
        	// Get new value for sysUpTime
        	var newSysUpTime = varbinds[4].value;
        	if(sysUptimeStamps[0]==0) {
        		for(var i=0; i < varbinds.length; i++) {
        			sysUptimeStamps[i] = newSysUpTime;
        		}
        	}

            for (var i = 0; i < varbinds.length; i++){
                if (snmp.isVarbindError (varbinds[i])) {
                	// Unmark for debbug
                    console.error(snmp.varbindError(varbinds[i]));
                }
                else {
                    // Unmark for debug on terminal
                    console.log ("["+oidsNames[i]+": "+packetsCounter[oids[i]]+"]   [OID: "+oids[i]+"]   [elapsedTimeTillChange: "+elapsedTimeTillChange[oids[i]]+"]   [elapsedSysUpTimeTillChange: "+ elapsedSysUpTimeTillChange[oids[i]]+"]   [ppm: "+ppm[oids[i]]+"]   [timeStamp: "+timeStamps[oids[i]]+"]");
                    
                    if(packetsCounter[oids[i]]!=varbinds[i].value){
                      // At this point we know that the agent as updated the value
                      // of the mib object oids[i]

                      var variation = varbinds[i].value - packetsCounter[oids[i]];

                      // packetsCounter[oids[i]] = varbinds[i].value;
                      elapsedTimeTillChange[oids[i]] = ( Date.now() - timeStamps[oids[i]] );
                      elapsedSysUpTimeTillChange[oids[i]] = (newSysUpTime -  elapsedSysUpTimeTillChange[oids[i]]);

                      // packetsCounter[oids[i]] stores new value for mib object oids[i]
                      packetsCounter[oids[i]] = varbinds[i].value;

                      // stores packets per min for mib object oids[i]
                      if(variation!=varbinds[i].value) {
                      	ppm[oids[i]] = (variation * 6000) / elapsedTimeTillChange[oids[i]];
                      	sysUptimeStamps[oids[i]] = newSysUpTime;
                  	  }

                      // Updating min and max pooling time values
                      if(elapsedTimeTillChange[oids[i]] > maxPollTime) {
                        maxPollTime = elapsedTimeTillChange[oids[i]];
                      }
                      if(elapsedTimeTillChange[oids[i]] < minPollTime && !flagFirstUpdate) {
                        minPollTime = elapsedTimeTillChange[oids[i]];
                      }
                      flagFirstUpdate = true;

                      timeStamps[oids[i]] = Date.now();

                      // We need to be sure that the poll time does not take absurd values due to
                      // random generation so, i set a maximum limit at MAX_LIMIT_POLLTIME
                      if(maxPollTime > MAX_LIMIT_POLLTIME) {
                        maxPollTime = minPollTime = 2000;
                      }
                    }
               }
            }
            pollingTime = Math.floor(Math.random() * (maxPollTime - minPollTime + 1)) + minPollTime;
       }
   });

    session.trap (snmp.TrapType.LinkDown, function (error) {
        if (error) console.error (error);
    });

    var sessionTimer = setTimeout(updateData, pollingTime);
}
