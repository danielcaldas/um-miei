<?php

	include '../../models/Professor.php';

	$idProfessor = $_REQUEST["idProfessor"];

	// Gerar pagina pessoal do compositor
	Professor::delete($idProfessor);
	
	$msg = "Professor apagado com sucesso. Este ato é irreverssível.";

	echo "<div class='alert alert-success alert-dismissible'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><h4>
	<i class='icon fa fa-check'></i>Ok!</h4>".$msg."</div><a href='list_professores.html'>Catálogo de Professores</a>";
?>
