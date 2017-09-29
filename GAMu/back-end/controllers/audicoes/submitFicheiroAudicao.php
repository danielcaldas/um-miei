<?php
	require_once '../../models/Audicao.php';
	require_once '../../models/Atuacao.php';

	class Packet {
		public $erro;
		public $html;

		public function __construct($erro, $html) {
			$this->erro = $erro;
			$this->html = $html;
		}
	}

	$file = $_FILES['ficheiroAudicao']['name'];
	$response = "";
	$isErro=false;

	// Obter ficheiro com gramatica submetido
	if($_FILES['ficheiroAudicao']['error'] > 0){
		if($_FILES['ficheiroAudicao']['error']==1) {
			$msg = "<p>Ficheiro demasiado grande. Por favor insira um ficheiro inferior a 10MB.</p>";
		} else {
			$msg = "<p>Erro!</p>";
		}
		echo $msg;
	} else {
		if(file_exists("ficheiros/".$file)){
			// Ficheiro ja existe
			unlink("ficheiros/".$file);
		}
		move_uploaded_file($_FILES['ficheiroAudicao']['tmp_name'], "ficheiros/".$file);
		$response.= "<p><a target='_blank' href='../../../../back-end/controllers/audicoes/ficheiros/".$file."'>Ficheiro </a> submetido.</p>";
	}

	// Mudar para a diretoria onde se encontra o modulo do AntLR
	chdir ("dsl");
	exec("./runAntLR.sh ../ficheiros/".$file);
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

	if ( $html=="" ) {
		// Criação de mensagem de erro
		$response.= "<div class='alert alert-danger alert-dismissible' style='width: 40%; margin-top: 2em; text-align:left;'>";
		$response.= "<button type='button' class='close' data-dismiss='alert' aria-hidden='true' onclick='location.reload()'>×</button>";
		$response.= "<h4><i class='icon fa fa-warning'></i>Por favor, corrija os seguintes erros:</h4>";
		$response.= "<ul>".$erros."</ul>";
		$response.= "<center><button type='button' class='btn btn-block btn-danger btn-sm' style='width: 10%;' onclick='location.reload()'>Ok</button></center>";
		$response.= "</div>";
		$isErro=true;
		$packet = new Packet($isErro, $response);
		sleep(2);
		echo json_encode($packet);
	} else {

		// -------------------------------------------------------------------------------------------
		// Desmarcar para utilizar link de ficheiro carregado na dropbox
		// Enviar o ficheiro criado para o servidor da dropbox e criar link de partilha
		require_once 'mydropbox.php';
		$url = uploadFileAndShare("dsl/FicheirosGerados/".$html);
		sleep(2);
		// -------------------------------------------------------------------------------------------

		// -------------------------------------------------------------------------------------------
		/*MODO DE TESTE (LINK LOCAL) SEM UPLOAD DA DROPBOX*/
		// Caminho desde GAMu/front-end/gamudash/pages/audicoes/add_dsl_audicao.html ate o ficheiro
		// $url = "../../../../back-end/controllers/audicoes/ficheiros/".$nomePanfleto;
		//--------------------------------------------------------------------------------------------

		// Tudo correu bem !
		$response.= "<div class='alert alert-success alert-dismissible' style='width: 40%; margin-top: 2em; text-align:left;'>";
		$response.= "<button type='button' class='close' data-dismiss='alert' aria-hidden='true' onclick='location.reload()'>×</button>";
		$response.= "<h4><i class='icon fa fa-check'></i> A Audição foi validada com sucesso!</h4>";
		$response.= "<p>Clique <a id='flyer_link' href='".$url."' target='_blank'>neste link</a> para aceder ao programa da audição</p>";
		$response.= "<center><button type='button' class='btn btn-block btn-success' style='width: 10%;' onclick='location.reload()'>Ok</button></center>";
		$response.= "</div>";

		$packet = new Packet($isErro, $response);

		echo json_encode($packet);
	}

?>
