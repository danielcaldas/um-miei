<?php

	include '../../models/Grupo.php';

	// Recolher variaveis do pedido ajax
	$idGrupo = $_REQUEST["idGrupo"];
	$nome = $_REQUEST["nome"];



	$gAtual = Grupo::getGrupo($idGrupo);
	$gNovo = new Grupo($idGrupo, $nome);


	$atributosModificados = Array();

	if($gAtual->nome!=$gNovo->nome) {
		$atributosModificados[] = "nome";
	}
	
	

	$gNovo->update($atributosModificados);

	$msg = "As suas alterações no grupo ".$nome." foram guardadas com sucesso.";

	echo "<div class='alert alert-success alert-dismissible'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><h4>
	<i class='icon fa fa-check'></i>Ok!</h4>".$msg."</div><a href='../../index.html'>Painel Principal</a>";
	
?>
