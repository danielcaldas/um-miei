<?php

	include '../../models/Aluno.php';

	$idAluno = $_REQUEST["idAluno"];

	// Apagar instancia do aluno
	Aluno::delete($idAluno);
	
	$msg = "Aluno apagado com sucesso. Este ato é irreverssível.";

    echo "<a href='list_alunos.html'><i class='fa fa-arrow-left'></i> Voltar ao catálogo de alunos </a>\n";
	echo "<div class='alert alert-success alert-dismissible'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><h4>
	<i class='icon fa fa-check'></i>Ok!</h4>".$msg."</div><a href='../../index.html'>Painel Principal</a>";
?>
