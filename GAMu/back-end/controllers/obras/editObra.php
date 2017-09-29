<?php

	include '../../models/Obra.php';

	// Recolher variaveis do pedido ajax
	$idObra = $_REQUEST["idObra"];
	$nomeObra = $_REQUEST["nome"];
	$descricao = $_REQUEST["descricao"];
	$nomeCompositor = $_REQUEST["nomeCompositor"];
	$data = $_REQUEST["data"];
	$periodo = $_REQUEST["periodo"];
	$duracao = $_REQUEST["duracao"];

	// Teste OK
	/*$idObra = "idObra";
	$nomeObra = "nome";
	$descricao = "descricao";
	$nomeCompositor = "Bach, Wilhelm Friedemann";
	$data = "data";
	$periodo = "periodo";
	$duracao = "duracao";*/

	$oAtual = Obra::getObra($idObra);

	/*--- Prepara instancia de nova obra ---*/
	$nomeCompositorOK = preg_quote($nomeCompositor, '\'*\\.');
	$idComp = Compositor::getChavePrimariaDoCompositor($nomeCompositorOK);
	$duracaoEmSegundos = Obra::converterEmSegundos($duracao);

	$oNovo = new Obra($idObra, $nomeObra, $descricao, $idComp, $data, $periodo, $duracaoEmSegundos);
	/*--------------------------------------*/

	$atributosModificados = Array();

	if($oAtual->nome!=$oNovo->nome) {
		$atributosModificados[] = "nome";
	}
	if($oAtual->descricao!=$oNovo->descricao) {
		$atributosModificados[] = "descricao";
	}
	if($oAtual->idCompositor!=$oNovo->idCompositor) {
		$atributosModificados[] = "idCompositor";
	}
	if($oAtual->data!=$oNovo->data) {
		$atributosModificados[] = "data";
	}
	if($oAtual->periodo!=$oNovo->periodo) {
		$atributosModificados[] = "periodo";
	}
	if($oAtual->duracao!=$oNovo->duracao) {
		$atributosModificados[] = "duracao";
	}

	$oNovo->update($atributosModificados);

	$msg = "As suas alterações na obra <b>".$nomeObra."</b> foram guardadas com sucesso.";

	echo "<div class='alert alert-success alert-dismissible'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><h4>
	<i class='icon fa fa-check'></i>Ok!</h4>".$msg."</div><a href='../../index.html'>Painel Principal</a>";
	
?>
