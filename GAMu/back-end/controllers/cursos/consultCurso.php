<?php

	include '../../models/Curso.php';

	$idCurso = $_REQUEST["idCurso"];

	// Gerar pagina do curso
	Curso::criarPaginaDoCurso($idCurso);
	
?>
