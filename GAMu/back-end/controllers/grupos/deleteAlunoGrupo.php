<?php

	require_once '../../models/Grupo.php';
	require_once '../../models/Aluno.php';

	// Recolher variaveis do pedido ajax
	$idAluno = $_REQUEST["idAluno"];
	$idGrupo = $_REQUEST["idGrupo"];

	// Dissociar o aluno do grupo
	Grupo::dissociarAlunoDoGrupo($idAluno, $idGrupo);

	// Buscar instancia do aluno a base de dados
	$a = Aluno::getAluno($idAluno);

	// Buscar a instancia grupo a base de dados
	$g = Grupo::getGrupo($idGrupo);

	$msg = "O aluno ".$a->nome." foi removido com sucesso do grupo ".$g->nome.".";

	echo $msg;
	
?>