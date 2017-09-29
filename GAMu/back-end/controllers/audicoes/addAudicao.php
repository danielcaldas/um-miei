<?php

	require_once '../../models/Audicao.php';
	
	class Packet {
		public $msg;
		public $idAudicao;

		public function __construct($m, $id) {
			$this->msg = $m;
			$this->idAudicao = $id;
		}
	}

	// Recolher variaveis do pedido ajax
	$titulo = $_REQUEST["titulo"];
	$subtitulo = $_REQUEST["subtitulo"];
	$tema = $_REQUEST["tema"];
	$data = $_REQUEST["data"];
	$horainicio = $_REQUEST["horaInicio"];
	$horafim = $_REQUEST["horaTermino"];
	$duracao = $_REQUEST["duracao"];
	$local = $_REQUEST["local"];


	if($duracao!=null && $duracao!="") {
		$duracao = Audicao::converterEmMinutos($duracao);
	}
	else if($horafim!=null && $horafim!=""){
		$duracao = Audicao::converterEmMinutos($horafim)-Audicao::converterEmMinutos($horainicio);
	}

	$a = new Audicao("", $titulo, $subtitulo, $tema, $data, $horainicio, $horafim, $duracao, $local);
	$a->insert();

	$msg = "Nova Audição \"".$titulo."\" registada com sucesso. Pode agora começar a contruír o programa para a Audição, reutilizando atuações existentes ou criando novas versões. Pode também a qualquer momento alterar os dados da audição.";
	$packet = new Packet($msg, $a->idAudicao);

	echo json_encode($packet);

?>