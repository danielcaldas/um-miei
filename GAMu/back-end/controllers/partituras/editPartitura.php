<?php

	include '../../models/Partitura.php';

	// Recolher variaveis do pedido ajax
	$idPartitura = $_REQUEST["idPartitura"];
	$arranjo = $_REQUEST["arranjo"];
	$idObra = $_REQUEST["idObra"];
	$nomeInstrumento = $_REQUEST["nomeInstrumento"];

	// Teste
	/*$idPartitura = "PART340";
	$arranjo = "Daniel Caldas";
	$idObra = "O170";
	$nomeInstrumento = "Guitarra";*/

	$pAtual = Partitura::getPartitura($idPartitura);	

	// Apanhar extensao do ficheiro carregado
	$partes = explode(".", $_FILES['pauta']['name']);
	$numero_de_pontos = substr_count($_FILES['pauta']['name'], ".");
	$extensao = $partes[$numero_de_pontos];

	$pauta = "";
	$filename = $idObra."_".$nomeInstrumento."_".$arranjo;
	$filename .= ".".$extensao;

	$atributosModificados = Array();
	$msg="";
	$fileOK = 0;

	if(isset($_FILES['pauta']) ){
		if($_FILES['pauta']['error'] > 0){
			if($_FILES['pauta']['error']==1) {
				$msg = "Ficheiro demasiado grande. Por favor escolha um ficheiro <b>inferior a 10MB</b>.</p>";
			}
		} else {
			move_uploaded_file($_FILES['pauta']['tmp_name'], "../../datasets/partituras/".$filename);
			$pauta = $filename;
			$fileOK=1;
		}
	}

	if($msg=="") {
		// i$d, $pauta, $arranjo, $idObra, $idInstrumento
		$pNovo = new Partitura ($idPartitura, $pauta, $arranjo, Partitura::getChavePrimariaDaObra($idObra), Partitura::getChavePrimariaDoInstrumentoPeloNome($nomeInstrumento));

		if($pAtual->arranjo!=$pNovo->arranjo) {
			$atributosModificados[] = "arranjo";
		}
		if($pAtual->idObra!=$pNovo->idObra) {
			$atributosModificados[] = "idObra";
		}
		if($pAtual->idInst!=$pNovo->idInst) {
			$atributosModificados[] = "idInst";
		}
		if($fileOK==1 && $pAtual->pauta!=$pNovo->pauta) {
			$atributosModificados[] = "pauta";
		}

		$pNovo->update($atributosModificados);
		$msg = "As modificações foram guardadas com sucesso.";
	}

	echo $msg;
	
?>
