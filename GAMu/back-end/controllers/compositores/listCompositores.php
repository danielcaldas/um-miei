<?php

	include '../../models/Compositor.php';

	if(Compositor::getTotalCompositores()>0) {
		// Gerar o catalogo de compositores
		Compositor::criarCatalogoDeCompositores();
	} else {
		echo "<div class='callout callout-info' style='margin: 3em;'><h4>:( !</h4><p>Infelizmente ainda não registaste nenhum compositor.</p></div>";
	}
	
?>
