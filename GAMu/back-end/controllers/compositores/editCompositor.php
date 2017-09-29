<?php

	include '../../models/Compositor.php';

	// Recolher variaveis do pedido ajax
	$idComp = $_REQUEST["idComp"];
	$nome = $_REQUEST["nome"];
	$dataNasc = $_REQUEST["data_nasc"];
	$dataMorte = $_REQUEST["data_morte"];
	$periodo = $_REQUEST["periodo"];
	$biografia = $_REQUEST["biografia"];

	/*
	// Teste
	$nome = "nome ALTERADO";
	$biografia = "biografia ALTERADA";
	*/

	$cAtual = Compositor::getCompositor($idComp);
	$cNovo = new Compositor($idComp, $nome, $dataNasc, $dataMorte, $periodo, $biografia, $cAtual->fotoURL);

	/*
	// Teste
	$cAtual = Compositor::getCompositor("COMP22");
	$cNovo = new Compositor($cAtual->idComp, $nome, $cAtual->dataNasc, $cAtual->dataMorte, $cAtual->periodo, $biografia, "");
	*/

	$atributosModificados = Array();

	if($cAtual->nome!=$cNovo->nome) {
		$atributosModificados[] = "nome";
	}
	if($cAtual->dataNasc!=$cNovo->dataNasc) {
		$atributosModificados[] = "dataNasc";
	}
	if($cAtual->dataMorte!=$cNovo->dataMorte) {
		$atributosModificados[] = "dataMorte";
	}
	if($cAtual->periodo!=$cNovo->periodo) {
		$atributosModificados[] = "periodo";
	}
	if($cAtual->biografia!=$cNovo->biografia) {
		$atributosModificados[] = "biografia";
	}

	$cNovo->update($atributosModificados);

	$msg = "As suas alterações no compositor <b>".$nome."</b> foram guardadas com sucesso.";

	echo "<div class='alert alert-success alert-dismissible'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><h4>
	<i class='icon fa fa-check'></i>Ok!</h4>".$msg."</div><a href='../../index.html'>Painel Principal</a>";
	
?>
