<?php

	include '../../models/Aluno.php';

	// Recolher variaveis do pedido ajax
	$idAluno = $_REQUEST["idAluno"];
	$nome = $_REQUEST["nome"];
	$dataNasc = $_REQUEST["dataNasc"];
	$anoCurso = $_REQUEST["anoCurso"];
	$instr = $_REQUEST["instr"];
	$curso = $_REQUEST["curso"];
	
	$alAtual = Aluno::getAluno($idAluno);
	
	$idCurso = Aluno::getIdCursoByDesig($curso);
	$alNovo = new Aluno($idAluno, $nome, $dataNasc, $anoCurso, $instr, $idCurso);

	$atributosModificados = Array();

	if($alAtual->nome!=$alNovo->nome) {
		$atributosModificados[] = "nome";
	}
	if($alAtual->dataNasc!=$alNovo->dataNasc) {
		$atributosModificados[] = "dataNasc";
	}
	if($alAtual->anoCurso!=$alNovo->anoCurso) {
		$atributosModificados[] = "anoCurso";
	}
	if($alAtual->instrumento!=$alNovo->instrumento) {
		$atributosModificados[] = "instrumento";
	}
	if($alAtual->idCurso!=$alNovo->idCurso) {
		$atributosModificados[] = "idCurso";
	}

	$alNovo->update($atributosModificados);

	$msg = "As suas alterações no aluno ".$nome." foram guardadas com sucesso.";

	echo "<div class='alert alert-success alert-dismissible'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><h4>
	<i class='icon fa fa-check'></i>Ok!</h4>".$msg."</div><a href='../../index.html'>Painel Principal</a>";
	
?>
