<?php

	include '../../models/Aluno.php';

	// Recolher variaveis do pedido ajax
	$nome = $_REQUEST["nome"];
	$dataNasc = $_REQUEST["dataNasc"];
	$anoCurso = $_REQUEST["anoCurso"];
	$instr = $_REQUEST["instr"];
	$curso = $_REQUEST["curso"];

    $idCurso = Aluno::getIdCursoByDesig($curso);
    
	// Fazer com que a primeira letra do primeiro nome seja maiuscula
	$nomeCamelCase = ucfirst($nome);

	$al = new Aluno("", $nomeCamelCase, $dataNasc, $anoCurso, $instr, $idCurso);
	$al->insert();

	$msg = "Novo aluno ".$nomeCamelCase." registado com sucesso.";

	echo "<div class='alert alert-success alert-dismissible'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><h4>
	<i class='icon fa fa-check'></i>Ok!</h4>".$msg."</div><a href='../../index.html'>Painel Principal</a>";
	
?>
