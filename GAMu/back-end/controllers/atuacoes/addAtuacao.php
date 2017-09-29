<?php

	require_once '../../models/Atuacao.php';
	
	// Recolher variaveis do pedido ajax	
	/*$idAtuacao = $_REQUEST["idAtuacao"];
	$profs = $_REQUEST["idsProfs"];
	$alunos = $_REQUEST["idsAlunos"];
	$obras = $_REQUEST["idsObras"];
	$auds = $_REQUEST["idsAuds"];*/

	$idAudicao = $_REQUEST["idAudicao"];

	$a = new Atuacao("", 0);
	$a->insertParcial();

	// Associar a audicao em causa
	Atuacao::associarAudicaoAtuacao($a->idAtuacao, $idAudicao);

	$msg = "Nova Atuação \"".$a->idAtuacao."\" registada com sucesso.";
	echo $msg;

?>