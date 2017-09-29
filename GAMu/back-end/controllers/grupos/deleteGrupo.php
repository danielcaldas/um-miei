<?php

	include '../../models/Grupo.php';

	$idGrupo = $_REQUEST["idGrupo"];

	// Gerar pagina pessoal do compositor
	Grupo::delete($idGrupo);
	
	$msg = "Grupo apagado com sucesso. Este ato é irreverssível.";

	echo "<div class='alert alert-success alert-dismissible'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><h4>
	<i class='icon fa fa-check'></i>Ok!</h4>".$msg."</div><a href='list_grupos.html'>Catálogo de Grupos</a>";
?>
