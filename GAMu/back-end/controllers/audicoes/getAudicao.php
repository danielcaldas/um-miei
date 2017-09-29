<?php

	require_once '../../models/Audicao.php';

	// Recolher variaveis do pedido ajax
	$idAudicao = $_REQUEST["idAudicao"];
	//$idAudicao= "AUD1";
	
	$aud = Audicao::getAudicao($idAudicao);

	// Devolver ao pedido ajax a audicao serializado num objecto JSON
	echo json_encode($aud);
?>