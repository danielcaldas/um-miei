<?php

	include '../../models/Obra.php';

	$idObra = $_REQUEST["idObra"];

	// Apagar instancia de uma obra
	Obra::delete($idObra);
	
	$msg = "Obra apagada com sucesso. Este ato é irreverssível.";

	echo "<div class='alert alert-success alert-dismissible'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><h4>
	<i class='icon fa fa-check'></i>Ok!</h4>".$msg."</div><a href='../../index.html'>Painel Principal</a>";
?>
