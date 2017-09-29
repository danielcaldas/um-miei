<?php

	require_once '../../models/Grupo.php';
	require_once '../../models/Aluno.php';

	// Recolher variaveis do pedido ajax
	$idAluno = $_REQUEST["idAluno"];
	$idGrupo = $_REQUEST["idGrupo"];

	// Associar o aluno ao grupo
	Grupo::associarAlunoAoGrupo($idAluno, $idGrupo);

	// Buscar instancia do aluno a base de dados
	$a = Aluno::getAluno($idAluno);

	// Buscar a instancia grupo a base de dados
	$g = Grupo::getGrupo($idGrupo);

	$msg = "O aluno <b>".$a->nome."</b> foi adicionado com sucesso ao <b>".$g->nome."</b>.";

	echo "<div class='alert alert-success alert-dismissible'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><h4>
	<i class='icon fa fa-check'></i>Ok!</h4>".$msg."</div><a href='../../index.html'>Painel Principal</a>";
	
?>