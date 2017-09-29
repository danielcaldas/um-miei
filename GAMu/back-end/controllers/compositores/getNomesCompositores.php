<?php

	include '../../models/Compositor.php';

	// Recolher variaveis do pedido ajax
	$request = $_REQUEST["request"];

	if($request=="compositores") {
		$nomes = Compositor::getNomesCompositores();

		// Devolver ao pedido ajax o compositor serializado num objecto JSON
		echo json_encode($nomes);
	}
	
?>
