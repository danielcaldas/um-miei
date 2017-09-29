<?php

	include '../../models/Grupo.php';

	if(Grupo::getTotalGrupos()>0) {
		// Gerar o catalogo de Grupos
		Grupo::criarCatalogoDeGrupos();
	} else {
		echo "<div class='callout callout-info' style='margin: 3em;'><h4>:( !</h4><p>Infelizmente ainda não registaste nenhum Grupo.</p></div>";
	}
	//teste
?>