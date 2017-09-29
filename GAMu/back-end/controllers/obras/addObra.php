<?php

	include '../../models/Obra.php';

	// Recolher variaveis do pedido ajax
	$nomeObra = $_REQUEST["nome"];
	$descricao = $_REQUEST["descricao"];
	$nomeCompositor = $_REQUEST["compositor"]; // string que necessita de ser convertida para chave do compositor
	$data = $_REQUEST["data"];
	$periodo = $_REQUEST["periodo"];
	$duracao = $_REQUEST["duracao"]; // string mm:ss precisa de ser convertida para inteiro*/

	// Fazer escape de eventuais carateres especiais
	$nomeCompositorOK = preg_quote($nomeCompositor, '\'*\\.');
	$idComp = Compositor::getChavePrimariaDoCompositor($nomeCompositorOK); // Chave primaria do compositor
	$duracaoEmSegundos = Obra::converterEmSegundos($duracao);
	$o = null;

	$msg="";
	
	if($idComp!=null) {
		// Compositor existe na base de dados
		$msg = "Nova obra <b>".$nomeObra."</b> registado com sucesso.";
		$o = new Obra("", $nomeObra, $descricao, $idComp, $data, $periodo, $duracaoEmSegundos);
	} else {
		// Compositor não existe na base de dados, criar novo registo
		$c = new Compositor("", $nomeCompositorOK, "desc", "desc", "desc", "desc", "");
		$c->insert();

		// Buscar idComp do novo compositor
		$idComp = Compositor::getChavePrimariaDoCompositor($nomeCompositorOK);
		$o = new Obra("", $nomeObra, $descricao, $idComp, $data, $periodo, $duracaoEmSegundos);

		$msg = "Nova obra <b>".$nomeObra."</b> registada com sucesso. Como o compositor não se encontrava registado na sua base de dados, um registo de ".$nomeCompositorOK." foi criado automaticamente";
	}
	$o->insert();

	echo "<div class='alert alert-success alert-dismissible'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><h4>
		<i class='icon fa fa-check'></i>Ok!</h4>".$msg."</div><a href='../../index.html'>Painel Principal</a>";

?>
