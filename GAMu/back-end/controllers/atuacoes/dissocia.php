<?php
	require_once '../../models/Atuacao.php';
	require_once '../../models/Aluno.php';

	// Recolher variaveis do pedido ajax
	$tipo = $_REQUEST["dissocia"];
	$idAtuacao = $_REQUEST["idAtuacao"];

	switch ($tipo) {
		case 'ALUNO':
			$idAluno = $_REQUEST["idAluno"];
			Atuacao::dissociarAlunoDaAtuacao($idAtuacao, $idAluno);
			break;
		case 'OBRA':
			$idObra = $_REQUEST["idObra"];
			Atuacao::dissociarObraDaAtuacao($idAtuacao,$idObra);
			break;
		case 'PROFESSOR':
			$idProfessor = $_REQUEST["idProfessor"];
			Atuacao::dissociarProfessorDaAtuacao($idAtuacao,$idProfessor);
			break;
		default:
			break;
	}
	
?>