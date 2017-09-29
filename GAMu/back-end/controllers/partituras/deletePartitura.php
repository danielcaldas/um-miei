<?php

	include '../../models/Partitura.php';

	$idPartitura = $_REQUEST["idPartitura"];

	// Apagar instancia de uma partitura
	Partitura::delete($idPartitura);
	
	$msg = "Partitura apagada com sucesso. Este ato é irreverssível.";

	echo $msg;
?>
