<?php

	include '../../models/Obra.php';
	include '../../models/Atuacao.php';

	$request = $_REQUEST["request"];

	if(Obra::getTotalObras()>0) {
		switch ($request) {
			case 'LIST':
				$idAtuacao = $_REQUEST["idAtuacao"];
				$idAudicao = $_REQUEST["idAudicao"];

				$tp = Atuacao::listarObrasParaSeAssociarAtuacao($idAtuacao);
				$primeira_tabela = file_get_contents(".paginas_obras/pagina_1.html");
				echo $primeira_tabela;
				break;

			default:
				$page = $_REQUEST["pagina"];
				$tabela = file_get_contents(".paginas_obras/pagina_".$page.".html");
				echo $tabela;
				break;
		}
	} else {
		echo "<div class='callout callout-info' style='margin: 3em;'><h4>:( !</h4><p>Infelizmente ainda não registaste nenhuma obra.</p></div>";
	}
	
?>
