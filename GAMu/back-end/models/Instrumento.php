<?php

    require_once 'AbstractPDO.php';
    require_once 'simple_html_dom.php';

    class Instrumento {
        const ID_PREFIX = 'INST';
        const DATASET = '../datasets/instrumentos.xml';
        public static $db;

        public $idInst;
        public $nome;

        public function __construct($id, $nome) {
            if($id=="") {
                $n = Instrumento::gerarIdentificador();
                $this->idInst = "";
                $this->idInst .= Instrumento::ID_PREFIX.$n;
            } else {
                $this->idInst = $id;
            }
            $this->nome = $nome;          
        }


        // Métodos de classe

        /*
        * Este metodo gera um identificador unico para uma instancia.
        */
        public static function gerarIdentificador() {
            $statement = Instrumento::$db->prepare("select max(id) from Instrumento;");
            $statement->execute();
            $result = $statement->fetch();
            return $result[0]+1;
        }

        /*
        * Este metodo permite obter uma instancia de um instrumento atraves do seu identificador.
        */
        public static function getInstrumento($idInst) {
            $statement = Instrumento::$db->prepare("select * from Instrumento where idInst='".$idInst."';");
            $statement->execute();
            $row = $statement->fetch(); // Converter $row num array standard

            $i = new Instrumento($row[1], $row[2]);

            return $i;
        }

        /*
        * Obter valor do total de instrumentos na base de dados.
        */
        public static function getTotalInstrumentoes() {
            // Usar prepared statements para evitar SQL Injection
            $statement = Instrumento::$db->prepare("select count(*) from Instrumento;");
            $statement->execute();
            $result = $statement->fetch();
            return $result[0];
        }

        /*
        * Apagar um instrumento pelo seu identificador.
        */
        public static function delete($idInst) {
            $statement = Instrumento::$db->prepare("delete from Instrumento where idInst='".$idInst."';");
            $statement->execute();
        }

        /*
        * Este metodo permite obter a chave primaria da base de dados de um Instrumento pelo seu nome.
        */
        public static function getChavePrimariaDoInstrumento($nome) {
            $statement = Instrumento::$db->prepare("select id from Instrumento where nome='".$nome."';");
            $statement->execute();
            $row = $statement->fetch();
            return $row[0];
        }

        /*
        * Metodo que permite obter nome do instrumento a partir da chave primaria
        */
        public static function getNomeDoInstrumentoPelaChavePrimaria($id) {
            $statement = Instrumento::$db->prepare("select nome from Instrumento where id=".$id.";");
            $statement->execute();
            $row = $statement->fetch();
            return $row[0];
        }

        /*
        * Este metodo retorna um array com o nome de todos os instrumentos registados na base de dados.
        */
        public static function getNomesInstrumentos() {
            $nomes = array();
            foreach(Instrumento::$db->query("select nome from Instrumento order by nome;") as $row) {
                $nomes[] = $row[0];
            }
            return $nomes;
        }

        /*
        * Migrar dados de um data set em XML com diversos compositores para
        * uma base de dados relacional MySQL.
        */
        public static function migrateXMLToMySQL() {
            $instrumentos = simplexml_load_file(Instrumento::DATASET);
            $insts =  $instrumentos->xpath("//instrumento");

            $j=1;
            foreach ($insts as $i) {
                $nome = $i->nome;
                $id="";
                $id .= Instrumento::ID_PREFIX.$j;
                
                $novoinst = new Instrumento($id, $i->nome);
                $novoinst->insert();
                $j++;
            }
        }

        /*----------------------------------------------------------------------
            Metodos que geram HTML
        ----------------------------------------------------------------------*/

        /*
        * Metodo que gera listangens de pautas do instrumentos disponíveis para uma determinada obra.
        */
        public static function criarListagensDeObrasDoInstrumento($nomeInstrumento) {
            $letraAtual="";
            $isFirstRow=true;

            $id = Instrumento::getChavePrimariaDoInstrumento($nomeInstrumento);

            echo "\t\t<div class='box box-solid'>\n";
            echo "\t\t\t<div class='box-header with-border'>\n";
            echo "\t\t\t\t<h2 style='font: bold;'>Obras para ".$nomeInstrumento."</h2>\n";
            echo "\t\t\t</div>\n";
            echo "\t\t\t<div style='padding-top: 1em;'>\n";
            echo "\t\t\t\t<ul>\n";

            foreach(Instrumento::$db->query("select * from Partitura where idInst=".$id." order by nome;") as $row) {
                $idPartitura = $row[1];
                $idInst = $row[2];
                $partitura = $row[3];
                echo "\t\t\t\t\t<li style='margin-bottom: 0.5em;'><a style='font-size: 16px;' href='".$partitura."'>".$nome."</a></li>\n";
            }

            echo "\t\t\t\t</ul>\n";
            echo "\t\t\t</div>\n";
            echo "\t\t</div>\n"; // Fechar a última row
        }

        /*----------------------------------------------------------------------*/
        /*----------------------------------------------------------------------*/


        // Métodos de instância


        /*
        * Inserir uma instancia de Instrumento na base de dados.
        */
        public function insert() {
            $query = "insert into Instrumento(idInst, nome) values(?, ?);";
            $statement = Instrumento::$db->prepare($query);
            $statement->bindParam(1, $this->idInst);
            $statement->bindParam(2, $this->nome);
            $statement->execute();
        }

        /*
        * Metodo que permite atualizar os dados de um instrumento consoante os campos alterados.
        */
        public function update($atributos) {
            $query = "update Instrumento set ";

            foreach ($atributos as $atr) {
                $query.=$atr."='".$this->$atr."',";
            }
            $query = rtrim($query, ","); // Remover ultima virgula que fica na string $query

            $query.=" where idInst='".$this->idInst."';";

            $statement = Instrumento::$db->prepare($query);
            $statement->execute();
        }
    }
    // Instanciar variavel de acesso a base de dados apenas uma vez, guardar interface de acesso
    // em variavel de classe
    Instrumento::$db = AbstractPDO::getInstance();

?>
