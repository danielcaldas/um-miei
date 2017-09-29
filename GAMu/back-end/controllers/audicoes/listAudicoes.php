<?php

	include '../../models/Audicao.php';

	// Diversas cores para entradas no calendario
	$cores = array(
		0 => ["#00a65a", "#00a65a"], // verde
		1 => ["#f39c12", "#f39c12"], // amarelo
		2 => ["#f56954", "#f56954"], // vermelho
		3 => ["#00c0ef", "#00c0ef"], // aqua
		4 => ["#3c8dbc", "#3c8dbc"], // azul flurescente
		5 => ["#0073b7", "#0073b7"]  // azul
	);

	// Modelo de dados de uma entrada no calendario
	class Entrada {
		public $title;
        public $start;
        public $end;
        public $backgroundColor;
        public $borderColor;
        public $url;

        public function __construct($idAudicao, $title, $sdate, $horaini, $horafim, $cores) {
        	$this->title = $title;

        	// Passar data para o formato do JavaSript (e.g. Sat Jan 01 2000 00:00:00 +0100)
        	$date = explode("/", $sdate);
        	$hi = explode(":", $horaini);
    		$this->start = array();
    		$this->start[] = (int)$date[2];
    		$this->start[] = (int)$date[1];
    		$this->start[] = (int)$date[0];
    		$this->start[] = (int)$hi[0];
    		$this->start[] = (int)$hi[1];
    		if($horafim!=null) {
    			$hf = explode(":", $horafim);
    			$this->end = array();
	        	$this->end[] = (int)$date[2];
	    		$this->end[] = (int)$date[1];
	    		$this->end[] = (int)$date[0];
	    		$this->end[] = (int)$hi[0];
	    		$this->end[] = (int)$hi[1];
    		}

    		$i=0;
    		if($date[1]>6 && $date[1]<12) {
    			$i = floor($date[1]/2);
    		} else if($date[1]==12){
    			$i=2;
    		} else {
    			$i = $date[1]-1;
    		}

        	$this->backgroundColor = $cores[$i][0];
        	$this->borderColor = $cores[$i][1];

        	// Link para entrar na audicao
        	$this->url = "consult_audicao.html?idAudicao=".$idAudicao;
        }
	}

	$request = $_REQUEST["request"];

	// Comentar linha anterior e desmarcar esta para testar
	// $request = "LIST";

	if(Audicao::getTotalAudicoes()>0) {
		if($request=="LIST") {
			// Criar entradas para calendario em objectos Entrada
			$a = array();

			foreach (Audicao::$db->query("select * from Audicao;") as $row) {
				$aud = new Audicao($row[1], $row[2], $row[3], $row[4], $row[5], $row[6], $row[7], $row[8], $row[9]);
				$audjs = new Entrada($row[1], $row[2], $row[5], $row[6], $row[7], $cores);
				$a[] = $audjs;
			}

			echo json_encode($a);
		}
	} else {
		echo "<div class='callout callout-info' style='margin: 3em;'><h4>:( !</h4><p>Infelizmente ainda criaste nenhuma audição.</p></div>";
	}
	
?>
