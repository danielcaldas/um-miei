<?php

	class TabelaPaginada {
        public $totalLinhas;
        public $nLinhasPorTabela;
        public $totalPaginas; // sera o mesmo que numero total de linhas
        public $tabelas; // array em que cada posicao contem o html de uma tabela

        public function __construct($lpt, $telems) {
            $this->nLinhasPorTabela = $lpt;
            $this->totalLinhas = $telems;
            $this->totalPaginas = floor($telems/$lpt);
            $this->tabelas = Array();
        }
    }

?>