<?php

	include '../../models/Professor.php';

	// Recolher variaveis do pedido ajax
	$nome = $_REQUEST["nome"];
	$dataNasc = $_REQUEST["data_nasc"];
	$habilitacoes = $_REQUEST["habilitacoes"];
	
	// Fazer com que a primeira letra do primeiro nome seja maiuscula
	$nomeCamelCase = ucfirst($nome);

	$p = new Professor("", $nomeCamelCase, $dataNasc, $habilitacoes, "");
	$res = $p->insert();

	$msg = "Novo professor ".$nome." registado com sucesso.";

	echo "<div class='alert alert-success alert-dismissible'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><h4>
	<i class='icon fa fa-check'></i>Ok!</h4>".$msg."</div><a href='../../index.html'>Painel Principal</a>";
	
?>