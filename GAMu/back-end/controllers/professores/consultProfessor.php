<?php

	include '../../models/Professor.php';

	$idProfessor = $_REQUEST["idProfessor"];


	// Gerar pagina pessoal do compositor
	Professor::criarPaginaDoProfessor($idProfessor);
	
?>