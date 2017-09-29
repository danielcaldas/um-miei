<?php

	include '../../models/Curso.php';

	$idCurso = $_REQUEST["idCurso"];

	// Apagar instancia de um curso
	Curso::delete($idCurso);
	
	$msg = "Curso apagado com sucesso. Este ato é irreverssível.";

    echo "<a href='list_cursos.html'><i class='fa fa-arrow-left'></i> Voltar ao catálogo de cursos </a>\n";
	echo "<div class='alert alert-success alert-dismissible'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><h4>
	<i class='icon fa fa-check'></i>Ok!</h4>".$msg."</div><a href='../../index.html'>Painel Principal</a>";
?>
