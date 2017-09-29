<?php

	include '../../models/Atuacao.php';

	$idAudicao = $_REQUEST["idAudicao"];

	$tp = Atuacao::criarTabelaParaSeAdicionarAtuacaoExistente($idAudicao);
		
?>
