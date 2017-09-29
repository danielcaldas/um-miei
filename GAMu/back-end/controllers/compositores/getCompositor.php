<?php

	include '../../models/Compositor.php';

	// Recolher variaveis do pedido ajax
	$idComp = $_REQUEST["idComp"];
	$c = Compositor::getCompositor($idComp);

	// Devolver ao pedido ajax o compositor serializado num objecto JSON
	echo json_encode($c);
	
?>
