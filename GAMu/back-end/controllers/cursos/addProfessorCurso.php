<?php

	require_once '../../models/Curso.php';
	require_once '../../models/Professor.php';

	// Recolher variaveis do pedido ajax
	$idProfessor = $_REQUEST["idProfessor"];
	$idCurso = $_REQUEST["idCurso"];

	// Associar o professor ao curso
	Curso::associarProfessorAoCurso($idProfessor, $idCurso);

	// Buscar instancia do professor a base de dados
	$p = Professor::getProfessor($idProfessor);

	// Buscar a instancia curso a base de dados
	$c = Curso::getCurso($idCurso);

	$msg = "O professor <b>".$p->nome."</b> foi adicionado com sucesso ao corpo docente do curso <b>".$c->designacao."</b>.";

	echo "<div class='alert alert-success alert-dismissible'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><h4>
	<i class='icon fa fa-check'></i>Ok!</h4>".$msg."</div><a href='../../index.html'>Painel Principal</a>";
	
?>