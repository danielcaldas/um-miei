<?php

	require_once '../../models/Audicao.php';

	$request = $_REQUEST["request"];

	if(Audicao::getTotalAudicoes()>0) {
		// Gerar o catalogo de Grupos
		Audicao::criarCatalogoDeAudicoes();
	} else {
		echo "<div class='callout callout-info' style='margin: 3em;'><h4>:( !</h4><p>Infelizmente ainda não registaste nenhuma Audicao.</p></div>";
	}
?>
