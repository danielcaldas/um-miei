<?php

	include '../../models/Aluno.php';

	// Recolher variaveis do pedido ajax
	$request = $_REQUEST["request"];

	if($request=="cursos") {
		$nomes = Aluno::getNomesCursos();

		// Devolver ao pedido ajax o curso serializado num objecto JSON
		echo json_encode($nomes);
	}
	
?>
