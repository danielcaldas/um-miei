<?php

	require_once '../../models/Atuacao.php';

	$idAtuacao = $_REQUEST["idAtuacao"];

	// Apagar instancia do Atuacao
	Atuacao::delete($idAtuacao);
	
	$msg = "Atuação apagada com sucesso. Este ato é irreverssível.";

    echo $msg;

?>
