<?php

	include '../../models/Compositor.php';

	// Recolher variaveis do pedido ajax
	$nome = $_REQUEST["nome"];
	$dataNasc = $_REQUEST["data_nasc"];
	$dataMorte = $_REQUEST["data_morte"];
	$periodo = $_REQUEST["periodo"];
	$biografia = $_REQUEST["biografia"];

	// Fazer com que a primeira letra do primeiro nome seja maiuscula
	$nomeCamelCase = ucfirst($nome);

	$c = new Compositor("", $nomeCamelCase, $dataNasc, $dataMorte, $periodo, $biografia, "");
	$c->insert();

	$msg = "Novo compositor <b>".$nome."</b> registado com sucesso.";

	echo "<div class='alert alert-success alert-dismissible'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><h4>
	<i class='icon fa fa-check'></i>Ok!</h4>".$msg."</div><a href='../../index.html'>Painel Principal</a>";
	
?>
