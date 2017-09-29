<?php

	require_once '../../models/Audicao.php';

	$idAudicao = $_REQUEST["idAudicao"];

	// Apagar instancia do Audicao
	Audicao::delete($idAudicao);
	
	$msg = "Audição apagada com sucesso. Este ato é irreverssível.";

    echo $msg;

?>
