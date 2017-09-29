// Esta funcao do javascript permite fazer parse do URL e retornar um elemento dada a respetiva chave
function getParameterByName(name) {
	name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
	var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
	    results = regex.exec(location.search);
	return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

// Esta funcao permite fazer a validacao de tempos, duracoes etc. ...
// E verificado um formato para horas do tipo hh:mm
function validaTempo(tempo) {
	// Expressao regular para fazer o match com a string de
	re = /^\d{1,2}:\d{2}([ap]m)?$/;
	if(tempo != '' && !tempo.match(re)) {
		alert("Formato de tempo invalido "+tempo+". Por favor insira um formato correto, e.g 5:33.");
		return false;
	}
	return true;
}

// Esta funcao converte horas no formato 00-12 AM/PM
// no respetivo formato 24h
function converteAMPMPara24(time) {
	var hours = Number(time.match(/^(\d+)/)[1]);
	var minutes = Number(time.match(/:(\d+)/)[1]);
	var AMPM = time.match(/\s(.*)$/)[1].toLowerCase();

	if (AMPM == "pm" && hours < 12) hours = hours + 12;
	if (AMPM == "am" && hours == 12) hours = hours - 12;
	var sHours = hours.toString();
	var sMinutes = minutes.toString();
	if (hours < 10) sHours = "0" + sHours;
	if (minutes < 10) sMinutes = "0" + sMinutes;
	return sHours+":"+sMinutes;
}

// Esta funcao converte horas no formato 24h
// no respetivo formato 00-12 AM/PM
function converte24ParaAMPMP(time) {
	var partes = time.split(":");
	var hours = parseInt(partes[0]);
	var minutes = parseInt(partes[1]);
	var res;
	if(hours > 12) {
		var a = hours-12;
		res = parseInt(a)+":"+minutes+" PM";
	} else if(hours==12) {
  		res = "00:"+minutes+" AM";
 	} else {
		res = hours+":"+minutes+" AM";
	}
	return res;
}
