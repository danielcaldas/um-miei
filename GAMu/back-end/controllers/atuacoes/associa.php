<?php
	require_once '../../models/Audicao.php';

	// Recolher variaveis do pedido ajax
	$tipo = $_REQUEST["associa"];
	$idAtuacao = $_REQUEST["idAtuacao"];

	switch ($tipo) {
		case 'ALUNO':
			$idAluno = $_REQUEST["idAluno"];
			Atuacao::associarAlunoAtuacao($idAtuacao, $idAluno, 0);
			break;
		case 'OBRA':
			$idObra = $_REQUEST["idObra"];
			Atuacao::associarObraAtuacao($idAtuacao,$idObra);
			break;
		case 'PROFESSOR':
			$idProfessor = $_REQUEST["idProfessor"];
			Atuacao::associarProfessorAtuacao($idAtuacao, $idProfessor, 0);
			break;
		case 'ACOMPANHANTE_ALUNO':
			$idAluno = $_REQUEST["idAluno"];
			Atuacao::associarAlunoAtuacao($idAtuacao, $idAluno, 1);
			break;
		case 'ACOMPANHANTE_PROFESSOR':
			$idProfessor = $_REQUEST["idProfessor"];
			Atuacao::associarProfessorAtuacao($idAtuacao, $idProfessor, 1);
			break;
		case 'ATUACAO':
			$idAudicao = $_REQUEST["idAudicao"];
			Atuacao::associarAudicaoAtuacao($idAtuacao,$idAudicao);
			break;
		default:
			break;
	}
	
?>