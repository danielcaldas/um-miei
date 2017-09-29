<?php

	require_once '../models/Aluno.php';
	require_once '../models/Professor.php';
	require_once '../models/Curso.php';
	require_once '../models/Grupo.php';
	require_once '../models/Compositor.php';
	require_once '../models/Obra.php';
	require_once '../models/Partitura.php';


	// Class que agrega contadores em objecto
	class Contador {
		public $nAlunos;
		public $nProfessores;
		public $nCursos;
		public $nGrupos;
		public $nCompositores;
		public $nObras;
		public $nPartituras;

		public function __construct() {
			$this->nAlunos = Aluno::getTotalAlunos();
			$this->nProfessores = Professor::getTotalProfessores();
			$this->nCursos = Curso::getTotalCursos();
			$this->nGrupos = Grupo::getTotalGrupos();
			$this->nCompositores = Compositor::getTotalCompositores();
			$this->nObras = Obra::getTotalObras();
			$this->nPartituras = Partitura::getTotalPartituras();
		}
	}

	$c = new Contador();
	
	echo json_encode($c);
?>
