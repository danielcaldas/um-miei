<?php

    require_once 'AbstractPDO.php';
    require_once 'Instrumento.php';

    class Partitura {
    	const DATASET = '../datasets/partituras.xml';
        const SOURCE_PREFIX = '../../../../back-end/datasets/partituras/';

        const ID_PREFIX = 'PART';
        public static $db;

        public $idPartitura;
        public $pauta;
        public $arranjo; // Nome de compositor/musico que fez arranjo especifico
        public $idObra; // Chave estrangeira valor real inteiro
        public $idInst; // Chave estrangeira valor real inteiro


        public function __construct($id, $pauta, $arranjo, $idObra, $idInst) {
            if($id=="") {
                $n = Partitura::gerarIdentificador();
                $this->idPartitura = "";
                $this->idPartitura .= Partitura::ID_PREFIX.$n;
            } else {
                $this->idPartitura = $id;
            }
            $this->pauta = $pauta;
            $this->arranjo = $arranjo;
            $this->idObra = $idObra;
            $this->idInst = $idInst;
        }


        // Métodos de classe

        /*
        * Este metodo gera um identificador unico para uma instancia.
        */
        public static function gerarIdentificador() {
            $statement = Partitura::$db->prepare("select max(id) from Partitura;");
            $statement->execute();
            $result = $statement->fetch();
            return $result[0]+1;
        }

        /*
        * Este metodo permite obter uma instancia de uma partitura atraves do seu identificador.
        */
        public static function getPartitura($idPartitura) {
            $statement = Partitura::$db->prepare("select * from Partitura where idPartitura='".$idPartitura."';");
            $statement->execute();
            $row = $statement->fetch(); // Converter $row num array standard

            $i = new Partitura($row[1], $row[2], $row[3], $row[4], $row[5]);

            return $i;
        }

        /*
        * Obter valor do total de partituras na base de dados.
        */
        public static function getTotalPartituras() {
            // Usar prepared statements para evitar SQL Injection
            $statement = Partitura::$db->prepare("select count(*) from Partitura;");
            $statement->execute();
            $result = $statement->fetch();
            return $result[0];
        }

        /*
        * Apagar um partitura pelo seu identificador.
        */
        public static function delete($idPartitura) {
            $statement = Partitura::$db->prepare("delete from Partitura where idPartitura='".$idPartitura."';");
            $statement->execute();
        }

        /*
        * Este metodo permite obter a chave primaria da base de dados de uma Partitura pelo seu identificador.
        */
        public static function getChavePrimariaDaPartitura($idPartitura) {
            $statement = Partitura::$db->prepare("select id from Partitura where idPartitura='".$idPartitura."';");
            $statement->execute();
            $row = $statement->fetch();
            return $row[0];
        }

        /*
        * Este metodo permite obter a chave primaria da base de dados de um Instrumento pelo seu identificador.
        */
        public static function getChavePrimariaDoInstrumento($idInst) {
            $statement = Partitura::$db->prepare("select id from Instrumento where idInst='".$idInst."';");
            $statement->execute();
            $row = $statement->fetch();
            return $row[0];
        }

        /*
        * Este metodo permite obter a chave primaria da base de dados de um Instrumento pelo seu nome.
        */
        public static function getChavePrimariaDoInstrumentoPeloNome($nome) {
            $statement = Partitura::$db->prepare("select id from Instrumento where nome='".$nome."';");
            $statement->execute();
            $row = $statement->fetch();
            return $row[0];
        }

        /*
        * Este metodo permite obter a chave primaria da base de dados de uma Obra pelo seu identificador.
        */
        public static function getChavePrimariaDaObra($idObra) {
            $statement = Partitura::$db->prepare("select id from Obra where idObra='".$idObra."';");
            $statement->execute();
            $row = $statement->fetch();
            return $row[0];
        }

        /*
        * Este metodo permite obter o nome de uma dada obra, uma vez fornecido o seu identificador.
        */
        public static function getNomeDaObra($idObra) {
            $statement = Partitura::$db->prepare("select nome from Obra where idObra='".$idObra."';");
            $statement->execute();
            $row = $statement->fetch();
            return $row[0];
        }

        /*
        * Metodo que gera dataset de partituras em XML
        */
        public static function gerarDatasetXML() {
			$bd = simplexml_load_file(Partitura::DATASET);

            $nomes = array();
            $nomes[] = "Alfredo Moura"; $nomes[] = "Adam Messinger";
            $nomes[] = "Duca Tambasco"; $nomes[] = "Aubrey Drake Graham";
            $nomes[] = "Duke Ellington"; $nomes[] = "Bob Ezrin";
            $nomes[] = "Emerson Pinheiro"; $nomes[] = "Brian Wilson";
            $nomes[] = "George Martin"; $nomes[] = "Bruno Bizarro";
            $nomes[] = "Glenn Miller"; $nomes[] = "Carlinhos Patriolino";

			// Adicionar 2 partituras para cada obra para partituras aleatorios
			for($i=1, $j=1; $i < 175; $i++, $j+=2) {
				// Selecionar partitura aleatorio de entre os 22 existentes
				$idPartitura1 = "INST".(string)rand(1,10);
				$idPartitura2 = "INST".(string)rand(11,22);

				$idObra = "O".$i;

				$pauta1 = "p".(string)rand(1,5).".pdf";
				$pauta2 = "p".(string)rand(6,10).".pdf";

                $arranjo2 = $nomes[rand(0,5)];
                $arranjo1 = $nomes[rand(6,11)];
                
				$partitura = $bd->addChild("partitura");
				$partitura->addAttribute("id", "PART".$j);
				$partitura->addChild("obra", $idObra);
				$partitura->addChild('instrumento', $idPartitura1);
				$partitura->addChild('pauta', $pauta1);
                $partitura->addChild('arranjo', $arranjo1);

				$partitura = $bd->addChild("partitura");
				$partitura->addAttribute("id", "PART".($j+1));
				$partitura->addChild("obra", $idObra);
				$partitura->addChild('instrumento', $idPartitura2);
				$partitura->addChild('pauta', $pauta2);
                $partitura->addChild('arranjo', $arranjo2);
			}

			$dom = new DOMDocument('1.0');
			$dom->preserveWhiteSpace = false;
			$dom->formatOutput = true;
			$dom->loadXML($bd->asXML());
			$dom->save(Partitura::DATASET);
		}


        /*
        * Migrar dados de um data set em XML com diversas partituras para
        * uma base de dados relacional MySQL.
        */
        public static function migrateXMLToMySQL() {
            $partituras = simplexml_load_file(Partitura::DATASET);
            $parts =  $partituras->xpath("//partitura");

            foreach ($parts as $p) {
                $id = (string)$p[@id];
                $pauta = (string)$p->pauta;
                $arranjo = (string)$p->arranjo;
                $idObra = Partitura::getChavePrimariaDaObra((string)$p->obra);
            	$idInst = Partitura::getChavePrimariaDoInstrumento((string)$p->instrumento);
            	
            	// public function __construct($id, $pauta, $idObra, $idInst)
                $novaPart = new Partitura($id, $pauta, $arranjo, $idObra, $idInst);
                //$novaPart->print_me();
                $novaPart->insert();
            }
        }

        /*----------------------------------------------------------------------
            Metodos que geram HTML
        ----------------------------------------------------------------------*/

        /*
        * Metodo que gera listangens de partituras disponíveis para uma determinada obra.
        */
        public static function criarListagensDePartiturasDaObra($identObra, $nomeObra) {
            $letraAtual="";
            $isFirstRow=true;

            $idObra = Partitura::getChavePrimariaDaObra($identObra); // Chave primaria

            echo "\t\t<div class='box box-solid'>\n";
            echo "\t\t\t<div class='box-header with-border'>\n";
            echo "\t\t\t\t<h2 style='font: bold;'>Partituras para ".$nomeObra."</h2>\n";
            echo "\t\t\t\t<a href='../partituras/add_partitura.html?idObra=".$identObra."' style='font-size: 16px;'><i class='fa fa-plus' style='font-size: 14px;'></i>   Adicionar Partitura</a>\n";
            echo "\t\t\t</div>\n";
            echo "\t\t\t<div style='padding-top: 1em;'>\n";
            echo "\t\t\t\t<ul>\n";

            $j=0;
            foreach(Partitura::$db->query("select * from Partitura where idObra=".$idObra." order by pauta;") as $row) {
                $idPartitura = $row[1];
                $pauta = $row[2];
                $arranjo = $row[3];
                $idObra = $row[4];
                $idInst = $row[5];
                $nomeInstrumento = Instrumento::getNomeDoInstrumentoPelaChavePrimaria($idInst);

                // Referencia de pauta na web
                if(strpos($pauta,'http') !== false) {
                    echo "\t\t\t\t\t<li style='margin-bottom: 0.5em;'>
                    <a style='font-size: 16px;' target='_blank' href='"
                    .$pauta."'>".$nomeInstrumento."</a><a id='edit_".$j."' href='#' onclick='editPartitura()'><i style='margin-left: 0.5em;' class='fa fa-pencil-square-o'></i></a>\n";
                } else {
                    // Referencia da pauta local
                    echo "\t\t\t\t\t<li style='margin-bottom: 0.5em;'>
                    <a id='a_".$idPartitura."' style='font-size: 16px;' target='_blank' href='"
                    .Partitura::SOURCE_PREFIX.$pauta."'>".$nomeInstrumento."</a>     ";
                    if($arranjo!="" && $arranjo!=null) {
                        echo "(<i>Arranjo de ".$arranjo."</i>)";
                    }
                    echo "<br/><a class='a-edit-partituras' id='".
                    $idPartitura."' href='#'>Editar <i class='fa fa-pencil-square-o'></i></a><a  id='".
                    $idPartitura."' class='a-delete-partituras' style='padding-left: 1em;' href='#' onclick='deletePartitura()'>Apagar <i class='fa fa-trash-o'></i></a>\n";
                }
                

                // Criar div com seccao escondida para subsituir pauta do instrumento
                echo "\t\t\t\t\t\t<div id='div_".$idPartitura."' style='margin-top: 0.5em; margin-bottom: 2em; display: none; padding: 1em;'>\n";
                echo "\t\t\t\t\t\t\t<label>Arranjo</label>\n";

                // Verificar se esta ou nao definido um arranjo
                if($arranjo!="" && $arranjo!=null) {
                    echo "\t\t\t\t\t\t\t<input id='arranjo_".$idPartitura."' type='text' class='form-control' style='width: 40%;' value='".$arranjo."'/><br/>\n";
                } else {
                    echo "\t\t\t\t\t\t\t<input id='arranjo_".$idPartitura."' type='text' class='form-control' style='width: 40%;' placeholder='Arranjo de ...'/><br/>\n";
                }

                echo "\t\t\t\t\t\t\t<label>Instrumento</label>\n";
                echo "\t\t\t\t\t\t\t<div id='instrumentos_select_wrapper' style='margin-bottom: 2em;'>\n";
                echo "\t\t\t\t\t\t\t\t<select id='instrumentos_select_".$idPartitura."' name='nomeInstrumento' class='form-control' style='width: 40%;'>\n";

                // Obter nomes dos instrumentos na base de dados
                $nomesInstrumentos = Instrumento::getNomesInstrumentos();

                // Adicionar nomes de instrumentos a drop down
                for($i=0; $i < count($nomesInstrumentos); $i++) {
                    if($nomesInstrumentos[$i]==$nomeInstrumento) {
                        // Instrumento atual fica selecionado
                        echo "\t\t\t\t\t\t\t\t\t<option value=\"".$nomesInstrumentos[$i]."\" selected>".$nomesInstrumentos[$i]."<option/>";
                    } else {
                        echo "\t\t\t\t\t\t\t\t\t<option value=\"".$nomesInstrumentos[$i]."\">".$nomesInstrumentos[$i]."<option/>";
                    }
                }

                echo "\t\t\t\t\t\t\t\t</select>\n";
                echo "\t\t\t\t\t\t\t</div>\n";

                echo "\t\t\t\t\t\t\t<label>Escolher uma nova partitura para ".$nomeInstrumento."</label>\n";
                echo "\t\t\t\t\t\t\t<input id='pauta_".$idPartitura."' type='file'/>\n";
                echo "\t\t\t\t\t\t\t<div style='margin-top: 1em;'>\n";
                echo "\t\t\t\t\t\t\t\t<a href='#' style='padding-right: 1em;' onclick='location.reload()'>\n";
                echo "\t\t\t\t\t\t\t\t\t<i class='fa fa-times'></i> Cancelar Edição\n";
                echo "\t\t\t\t\t\t\t\t</a>\n";
                echo "\t\t\t\t\t\t\t\t<a id='".$idPartitura."' href='#' class='a-submit-partituras'>\n";
                echo "\t\t\t\t\t\t\t\t\t<i class='fa fa-save'></i> Guardar Alterações\n";
                echo "\t\t\t\t\t\t\t\t</a>\n";
                echo "\t\t\t\t\t\t\t</div>\n";
                echo "\t\t\t\t\t\t</div>\n";

                echo "</li>\n"; // Fechar list item

                $j++;
            }

            echo "\t\t\t\t</ul>\n";
            echo "\t\t\t</div>\n";

            echo "\t\t<input id='npartituras' type='hidden' value='".$j."'\n"; // Para sabermos quantas partituras existem
            echo "\t\t</div>\n"; // Fechar a última row
        }

        /*----------------------------------------------------------------------*/
        /*----------------------------------------------------------------------*/


        // Métodos de instância

        /*
        * Metodo de auxilio em debug.
        */
        public function print_me() {
        	echo "idPartitura: [".$this->idPartitura."] pauta: [".$this->pauta."] arranjo: [".$this->arranjo."] idObra: [".$this->idObra."] idInst: [".$this->idInst."]\n";
        }

        /*
        * Inserir uma instancia de Partitura na base de dados.
        */
        public function insert() {
            $query = "insert into Partitura(idPartitura, pauta, arranjo, idObra, idInst) values(?, ?, ?, ?, ?);";
            $statement = Partitura::$db->prepare($query);
            $statement->bindParam(1, $this->idPartitura);
            $statement->bindParam(2, $this->pauta);
            $statement->bindParam(3, $this->arranjo);
            $statement->bindParam(4, $this->idObra);
            $statement->bindParam(5, $this->idInst);
            $statement->execute();
        }

        /*
        * Metodo que permite atualizar os dados de um partitura consoante os campos alterados.
        */
        public function update($atributos) {
            $query = "update Partitura set ";

            foreach ($atributos as $atr) {
                switch ($atr) {
                    case 'idObra':
                        $query.=$atr."=".$this->$atr.",";
                        break;
                    case 'idInst':
                        $query.=$atr."=".$this->$atr.",";
                        break;
                    default:
                        $query.=$atr."='".$this->$atr."',";
                        break;
                }
            }
            $query = rtrim($query, ","); // Remover ultima virgula que fica na string $query

            $query.=" where idPartitura='".$this->idPartitura."';";

            $statement = Partitura::$db->prepare($query);
            $statement->execute();
        }
    }
    // Instanciar variavel de acesso a base de dados apenas uma vez, guardar interface de acesso
    // em variavel de classe
    Partitura::$db = AbstractPDO::getInstance();

?>
