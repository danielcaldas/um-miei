<?php

	include '../../models/Aluno.php';

	// Recolher variaveis do pedido ajax
	$idAluno = $_REQUEST["idAluno"];
	$al = Aluno::getAluno($idAluno);

	// Devolver ao pedido ajax o aluno serializado num objecto JSON
	echo json_encode($al);
?>
