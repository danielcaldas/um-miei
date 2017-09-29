<?php

	require_once '../../models/Curso.php';
	require_once '../../models/Professor.php';

	// Recolher variaveis do pedido ajax
	$idProfessor = $_REQUEST["idProfessor"];
	$idCurso = $_REQUEST["idCurso"];

	// Associar o professor ao curso
	Curso::dissociarProfessorDoCurso($idProfessor, $idCurso);

	// Buscar instancia do professor a base de dados
	$p = Professor::getProfessor($idProfessor);

	// Buscar a instancia curso a base de dados
	$c = Curso::getCurso($idCurso);

	$msg = "O professor ".$p->nome." foi removido com sucesso do corpo docente do curso ".$c->designacao.".";

	echo $msg;
	
?>