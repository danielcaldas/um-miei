<?php
	require_once '../../models/Audicao.php';

	class Packet {
		public $erro;
		public $html;

		public function __construct($erro, $html) {
			$this->erro = $erro;
			$this->html = $html;
		}
	}

	$idAudicao = $_REQUEST["idAudicao"];

    // Obter ficheiro input para gramatica
    $inputfile = Audicao::gerarInputGramatica($idAudicao);

    // Correr gramatica
    chdir ("dsl");
	exec("./runAntLR.sh ../".$inputfile);
	chdir ("..");

	$rfile = fopen("result.txt", "r") or die("Incapaz de abrir o ficheiro!");
	$output="";
	$html="";
	$erros="";
	$xmls = array();
	if(filesize("result.txt") > 0) {
		while (($buffer = fgets($rfile, 4096)) !== false) {
			if(strpos($buffer, "ERRO")!==false || strpos($buffer, "AVISO")!==false) {
				$erros.="<li>".$buffer."</li>";
			}
			else {
				if(strpos($buffer, "html")!==false) {
					$aux = explode(" ", $buffer);
	    			$html = $aux[1];
	    			// echo $html;
				} else {
					$aux = explode(" ", $buffer);
					$xmls[] = $aux[1];
					// echo $aux[1];
				}
			}
    		
    	}
		fclose($rfile);
	}

	exec("rm ".$inputfile);

	if($html!="") {
		// Correu bem posso partilhar
		// -------------------------------------------------------------------------------------------
		// Desmarcar para utilizar link de ficheiro carregado na dropbox
		// Enviar o ficheiro criado para o servidor da dropbox e criar link de partilha
		require_once 'mydropbox.php';
		$url = uploadFileAndShare("dsl/FicheirosGerados/".$html);
		sleep(2);
		// -------------------------------------------------------------------------------------------

		// Tudo correu bem !
		$response="";
		$response.= "<div class='alert alert-success alert-dismissible' style='width: 40%; margin-top: 2em; text-align:left;'>";
		$response.= "<button type='button' class='close' data-dismiss='alert' aria-hidden='true' onclick='location.reload()'>×</button>";
		$response.= "<h4><i class='icon fa fa-check'></i> A Audição foi validada com sucesso! <br/> Pode agora gerar o PDF ou o Web site com o programa da audição.</h4>";
		$response.= "<p style='display: none;'><a id='flyer_link' href='".$url."' target='_blank'>neste link</a> para aceder ao programa da audição</p>";
		$response.= "<center><button type='button' class='btn btn-block btn-success' style='width: 10%;' onclick='location.reload()'>Ok</button></center>";
		$response.= "</div>";

		$packet = new Packet(false, $response);

		echo json_encode($packet);
	}
	else {
		// Temos erros! 0.o

		$response="";

		// Criação de mensagem de erro
		$response.= "<div class='alert alert-danger alert-dismissible' style='width: 40%; margin-top: 2em; text-align:left;'>";
		$response.= "<button type='button' class='close' data-dismiss='alert' aria-hidden='true' onclick='location.reload()'>×</button>";
		$response.= "<h4><i class='icon fa fa-warning'></i>Por favor, corrija os seguintes erros:</h4>";
		$response.= "<ul>".$erros."</ul>";
		$response.= "<center><button type='button' class='btn btn-block btn-danger btn-sm' style='width: 10%;' onclick='location.reload()'>Ok</button></center>";
		$response.= "</div>";
		$packet = new Packet(true, $response);
		sleep(2);
		echo json_encode($packet);
	}


?>
