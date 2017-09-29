<?php

	include '../../models/Grupo.php';

	$idGrupo = $_REQUEST["idGrupo"];


	// Gerar pagina pessoal do compositor
	Grupo::criarPaginaDoGrupo($idGrupo);
	
?>