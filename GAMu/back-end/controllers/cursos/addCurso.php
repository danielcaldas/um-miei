<?php

	include '../../models/Curso.php';

	// Recolher variaveis do pedido ajax
	$designacao = $_REQUEST["designacao"];
	$duracao = $_REQUEST["duracao"];
	$instr = $_REQUEST["instr"];

    $idInstr = Curso::getIdInstrumento($instr);
    
	// Fazer com que a primeira letra do primeiro nome seja maiuscula
	$desigCamelCase = ucfirst($designacao);

	$c = new Curso("", $desigCamelCase, $duracao, $idInstr);
	$c->insert();

	$msg = "Novo curso ".$desigCamelCase." registado com sucesso.";

	echo "<div class='alert alert-success alert-dismissible'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><h4>
	<i class='icon fa fa-check'></i>Ok!</h4>".$msg."</div><a href='../../index.html'>Painel Principal</a>";
	
?>
