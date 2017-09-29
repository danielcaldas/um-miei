<?php

	include '../../models/Curso.php';

	// Recolher variaveis do pedido ajax
	$idCurso = $_REQUEST["idCurso"];
	$designacao = $_REQUEST["designacao"];
	$duracao = $_REQUEST["duracao"];
	$instr = $_REQUEST["instr"];
	
	$cAtual = Curso::getCurso($idCurso);
	
	$idInstr = Curso::getIdInstrumento($instr);
	$cNovo = new Curso($idCurso, $designacao, $duracao, $idInstr);

	$atributosModificados = Array();

	if($cAtual->designacao!=$cNovo->designacao) {
		$atributosModificados[] = "designacao";
	}
	if($cAtual->duracao!=$cNovo->duracao) {
		$atributosModificados[] = "duracao";
	}
	if($cAtual->idInstrumento!=$cNovo->idInstrumento) {
		$atributosModificados[] = "idInst";
	}

	$cNovo->update($atributosModificados);

	$msg = "As suas alterações no curso <b>".$designacao."</b> foram guardadas com sucesso.";

	echo "<div class='alert alert-success alert-dismissible'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><h4>
	<i class='icon fa fa-check'></i>Ok!</h4>".$msg."</div><a href='../../index.html'>Painel Principal</a>";
	
?>
