<?php

	include '../../models/Instrumento.php';

	// Recolher variaveis do pedido ajax
	$request = $_REQUEST["request"];

	if($request=="instrumentos") {
		$nomes = Instrumento::getNomesInstrumentos();

		// Devolver ao pedido ajax lista de nomes de instrumentos serializados num objecto JSON
		echo json_encode($nomes);
	}
	
?>
