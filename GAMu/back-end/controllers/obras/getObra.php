<?php

	include '../../models/Obra.php';

	// Recolher variaveis do pedido ajax
	$idObra = $_REQUEST["idObra"];
	$o = Obra::getObra2($idObra);

	// Devolver ao pedido ajax a obra serializada num objecto JSON
	echo json_encode($o);
	
?>
