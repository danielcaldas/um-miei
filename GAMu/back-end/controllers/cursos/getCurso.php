<?php

	include '../../models/Curso.php';

	// Recolher variaveis do pedido ajax
	$idCurso = $_REQUEST["idCurso"];
	$c = Curso::getCurso($idCurso);

	// Devolver ao pedido ajax o curso serializado num objecto JSON
	echo json_encode($c);
?>
