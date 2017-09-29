<?php

	include '../../models/Obra.php';

	$idObra = $_REQUEST["idObra"];

	// Gerar pagina que dispoe informacao acerca da obra selecionada
	Obra::criarPaginaDaObra($idObra);
	
?>
