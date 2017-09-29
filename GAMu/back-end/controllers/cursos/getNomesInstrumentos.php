<?php

	include '../../models/Curso.php';

	// Recolher variaveis do pedido ajax
	$request = $_REQUEST["request"];

	if($request=="instrumentos") {
		$nomes = Curso::getNomesInstrumentos();

		// Devolver ao pedido ajax o curso serializado num objecto JSON
		echo json_encode($nomes);
	}
	
?>
