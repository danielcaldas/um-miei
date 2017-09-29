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
	$idAudicao = $_REQUEST["idAudicao"];
	$titulo = $_REQUEST["titulo"];
	$subtitulo = $_REQUEST["subtitulo"];
	$tema = $_REQUEST["tema"];
	$data = $_REQUEST["data"];
	$horainicio = $_REQUEST["horaInicio"];
	$horafim = $_REQUEST["horaTermino"];
	$duracao = $_REQUEST["duracao"];
	$local = $_REQUEST["local"];

	$audAtual = Audicao::getAudicao($idAudicao);
	$audNovo = new Audicao($idAudicao, $titulo, $subtitulo, $tema, $data, $horainicio, $horafim, $duracao, $local);
	$audNovo->duracao = Audicao::converterEmMinutos($duracao);

	if($duracao!=null && $duracao!="") {
		$duracao = Audicao::converterEmMinutos($duracao);
	}
	else if($horafim!=null && $horafim!=""){
		$duracao = Audicao::converterEmMinutos($horafim)-Audicao::converterEmMinutos($horainicio);
	}

	$atributosModificados = Array();

	if($audAtual->titulo!=$audNovo->titulo) {
		$atributosModificados[] = "titulo";
	}
	if($audAtual->subtitulo!=$audNovo->subtitulo) {
		$atributosModificados[] = "subtitulo";
	}
	if($audAtual->tema!=$audNovo->tema) {
		$atributosModificados[] = "tema";
	}
	if($audAtual->data!=$audNovo->data) {
		$atributosModificados[] = "data";
	}
	if($audAtual->horainicio!=$audNovo->horainicio) {
		$atributosModificados[] = "horainicio";
	}
	if($audAtual->horafim!=$audNovo->horafim) {
		$atributosModificados[] = "horafim";
	}
	if($audAtual->duracao!=$audNovo->duracao) {
		$atributosModificados[] = "duracao";
	}
	if($audAtual->local!=$audNovo->local) {
		$atributosModificados[] = "local";
	}

	$audNovo->update($atributosModificados);

	$msg = "As suas alterações na audição \"".$titulo."\" foram guardadas com sucesso.";
	$packet = new Packet($msg, $idAudicao);

	echo json_encode($packet);
	
?>
