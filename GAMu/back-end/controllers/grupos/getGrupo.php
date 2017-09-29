<?php

	include '../../models/Grupo.php';

	// Recolher variaveis do pedido ajax
	$idGrupo = $_REQUEST["idGrupo"];
	$gr = Grupo::getGrupo($idGrupo);

	// Devolver ao pedido ajax o compositor serializado num objecto JSON
	echo json_encode($gr);
	
?>