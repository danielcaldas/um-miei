<?php

	include '../../models/Compositor.php';

	$idComp = $_REQUEST["idComp"];

	// Apagar instancia de um compositor
	Compositor::delete($idComp);
	
	$msg = "Compositor apagado com sucesso. Este ato é irreverssível.";

	echo "<div class='alert alert-success alert-dismissible'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><h4>
	<i class='icon fa fa-check'></i>Ok!</h4>".$msg."</div><a href='list_compositores.html'>Catálogo de Compositores</a>";
?>
