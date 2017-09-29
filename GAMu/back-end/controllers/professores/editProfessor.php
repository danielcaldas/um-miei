<?php

	include '../../models/Professor.php';

	// Recolher variaveis do pedido ajax
	$idProfessor = $_REQUEST["idProfessor"];
	$nome = $_REQUEST["nome"];
	$dataNasc = $_REQUEST["data_nasc"];
	$habilitacoes = $_REQUEST["habilitacoes"];


	/*
	// Teste
	$nome = "nome ALTERADO";
	$biografia = "biografia ALTERADA";
	*/

	$pAtual = Professor::getProfessor($idProfessor);
	$pNovo = new Professor($idProfessor, $nome, $dataNasc, $habilitacoes, "");

	/*
	// Teste
	$cAtual = Professor::getProfessor("COMP22");
	$cNovo = new Professor($cAtual->idComp, $nome, $cAtual->dataNasc, $cAtual->dataMorte, $cAtual->periodo, $biografia, "");
	*/

	$atributosModificados = Array();

	if($pAtual->nome!=$pNovo->nome) {
		$atributosModificados[] = "nome";
	}
	if($pAtual->dataNasc!=$pNovo->dataNasc) {
		$atributosModificados[] = "dataNasc";
	}
	if($pAtual->habilitacoes!=$pNovo->habilitacoes) {
		$atributosModificados[] = "habilitacoes";
	}
	

	$pNovo->update($atributosModificados);

	$msg = "As suas alterações no professor ".$nome." foram guardadas com sucesso.";

	echo "<div class='alert alert-success alert-dismissible'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><h4>
	<i class='icon fa fa-check'></i>Ok!</h4>".$msg."</div><a href='../../index.html'>Painel Principal</a>";
	
?>
