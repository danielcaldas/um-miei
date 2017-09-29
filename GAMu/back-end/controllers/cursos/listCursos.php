<?php

	include '../../models/Curso.php';
	
	if(Curso::getTotalCursos()>0) {
		// Gerar o catalogo de cursos
		Curso::criarCatalogoDeCursos();
	} else {
		echo "<div class='callout callout-info' style='margin: 3em;'><h4>:( !</h4><p>Infelizmente ainda não foi registado nenhum curso.</p></div>";
	}
	
?>
