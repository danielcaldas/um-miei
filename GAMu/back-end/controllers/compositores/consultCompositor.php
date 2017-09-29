<?php

	include '../../models/Compositor.php';

	$idComp = $_REQUEST["idComp"];

	// Gerar pagina pessoal do compositor
	Compositor::criarPaginaDoCompositor($idComp);
	
?>
