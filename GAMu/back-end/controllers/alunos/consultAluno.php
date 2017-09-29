<?php

	include '../../models/Aluno.php';

	$idAluno = $_REQUEST["idAluno"];

	// Gerar pagina do aluno
	Aluno::criarPaginaDoAluno($idAluno);
	
?>
