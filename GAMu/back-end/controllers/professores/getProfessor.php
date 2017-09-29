<?php

	include '../../models/Professor.php';

	// Recolher variaveis do pedido ajax
	$idProfessor = $_REQUEST["idProfessor"];
	$p = Professor::getProfessor($idProfessor);

	// Devolver ao pedido ajax o compositor serializado num objecto JSON
	echo json_encode($p);
	
?>