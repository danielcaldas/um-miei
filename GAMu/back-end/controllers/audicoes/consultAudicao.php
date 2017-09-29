<?php

	require_once '../../models/Audicao.php';

	$idAudicao = $_REQUEST["idAudicao"];

	// Gerar pagina do audicao
	Audicao::criarPaginaDaAudicao($idAudicao);
	
?>
