<?php

	include '../../models/Grupo.php';

	// Recolher variaveis do pedido ajax
	$nome = $_REQUEST["nome"];
	
	
	// Fazer com que a primeira letra do primeiro nome seja maiuscula
	$nomeCamelCase = ucfirst($nome);

	$gr = new Grupo("", $nomeCamelCase, "");
	$gr->insert();
	//$res = $gr->insert();

	$msg = "Novo grupo <b>".$nome."</b> registado com sucesso.";

	echo "<div class='alert alert-success alert-dismissible'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><h4>
	<i class='icon fa fa-check'></i>Ok!</h4>".$msg."</div><a href='../../index.html'>Painel Principal</a>";
?>