<?php

	include '../../models/Partitura.php';

	// Recolher variaveis do pedido ajax
	$arranjo = $_REQUEST["arranjo"];
	$idObra = $_REQUEST["idObra"];
	$nomeInstrumento = $_REQUEST["nomeInstrumento"];

	// $pauta pode ser um ficheiro ou um URL, portanto deve ser tratado em conformidade
	// nome da pauta por defeito sera IDPartitura + Instrumento + Arranjo

	// Por defeito redirecionamos o link da pauta para uma pagina de erro 
	// $pauta="pauta_nao_encontrada.html?idObra=".$idObra."";
	$pauta = "";

	$filename = $idObra."_".$nomeInstrumento."_".$arranjo;

	$msg="";

	// Apanhar extensao do ficheiro carregado
	$partes = explode(".", $_FILES['pauta']['name']);
	$numero_de_pontos = substr_count($_FILES['pauta']['name'], ".");
	$extensao = $partes[$numero_de_pontos];

	$filename .= ".".$extensao;

	if($_FILES['pauta']['error'] > 0){
		if($_FILES['pauta']['error']==1) {
			$msg = "Ficheiro demasiado grande. Por favor escolha um ficheiro <b>inferior a 10MB</b>.</p>";
		}
	} else {
		if(file_exists("../../datasets/partituras/".$filename)){
			// A pauta ja existe, submissao e abortada e utilizador notificado
			$msg = "Já foi registada uma pauta para o instrumento <b>".$nomeInstrumento."</b> e arranjo <b>".$arranjo."</b>.<br/>
			Verifique a versão da pauta que está a submeter não se trata da já existente, caso seja acrescente algo ou modifique o nome do arranjo, e.g arranjo <i>x</i> <b>versão 2</b>";
		} else{
			// DEBUG
			// echo "<p>".is_uploaded_file ( $_FILES['pauta']['tmp_name'] )."</p>";
			// echo "<p>file_exists: ".file_exists($_FILES['pauta']['tmp_name'])."</p>";

			move_uploaded_file($_FILES['pauta']['tmp_name'], "../../datasets/partituras/".$filename);
			echo "<p><a target='_blank' href='../../../../back-end/datasets/partituras/".$filename."'>Partitura</a> gravada com sucesso (pode confirmar neste link).</p>";
			$pauta = $filename;
		}
	}

	if($msg=="") {
		$idInstrumento = Partitura::getChavePrimariaDoInstrumentoPeloNome($nomeInstrumento);
		$p = new Partitura("", $pauta, $arranjo, Partitura::getChavePrimariaDaObra($idObra), $idInstrumento);
		$p->insert();

		$msg = "Nova partitura <b>".$filename."</b> registada com sucesso.";

		echo "<div class='alert alert-success alert-dismissible'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><h4>
		<i class='icon fa fa-check'></i>Ok!</h4>".$msg."</div><a href='../../index.html'>Painel Principal</a>";
	} else {
		echo "<div class='alert alert-warning alert-dismissible'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><h4>
		<i class='icon fa fa-ban'></i>Oops!</h4>".$msg."</div><a href='../../index.html'>Painel Principal</a>";
	}

?>
