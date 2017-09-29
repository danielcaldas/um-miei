<?php
    
    require_once 'AbstractPDO.php';
    require_once 'Compositor.php';
    require_once 'TabelaPaginada.php';
    require_once 'Partitura.php';

    class Obra {
        const ID_PREFIX = 'O';
        const DATASET = '../datasets/obras.xml';
        public static $db;

        public $idObra;
        public $nome;
        public $descricao;
        public $idCompositor; // Chave estrangeira valor real inteiro
        public $data;
        public $periodo;
        public $duracao;

        public $nomeCompositor;

        public function __construct($id, $nome, $descricao, $idCompositor, $data, $periodo, $duracao) {
            if($id=="") {
                $n = Obra::gerarIdentificador();
                $this->idObra = "";
                $this->idObra .= Obra::ID_PREFIX.$n;
            } else {
                $this->idObra = $id;
            }
            $this->nome = $nome;
            $this->descricao = $descricao;
            $this->idCompositor = $idCompositor; // Chave estrangeira valor real inteiro
            $this->data = $data;
            $this->periodo = $periodo;
            $this->duracao = $duracao;
        }


        // Métodos de classe

        /*
        * Este metodo gera um identificador unico para uma instancia.
        */
        public static function gerarIdentificador() {
            $statement = Obra::$db->prepare("select max(id) from Obra;");
            $statement->execute();
            $result = $statement->fetch();
            return $result[0]+1;
        }

        /*
        * Este metodo permite obter uma instancia de uma obra atraves do seu identificador.
        */
        public static function getObra($idObra) {
            $statement = Obra::$db->prepare("select * from Obra where idObra='".$idObra."';");
            $statement->execute();
            $row = $statement->fetch(); // Converter $row num array standard

            $o = new Obra($row[1], $row[2], $row[3], $row[4], $row[5], $row[6], $row[7]); 

            return $o;
        }

        /*
        * Este metodo permite obter a chave primaria da obra pelo seu identificador
        */
        public static function getChavePrimariaDaObra($idObra) {
            $statement = Obra::$db->prepare("select id from Obra where idObra='".$idObra."';");
            $statement->execute();
            $row = $statement->fetch();
            
            return $row[0];
        }

        /*
        * Este metodo permite obter o identificador da obra pela sua chave primaria
        */
        public static function getIdentificadorPelaChavePrimaria($id) {
            $statement = Obra::$db->prepare("select idObra from Obra where id=".$id.";");
            $statement->execute();
            $row = $statement->fetch();
            
            return $row[0];
        }

        /*
        * Este metodo permite obter uma instancia de uma obra atraves do seu identificador,
        * a diferenca para o metodo de cima e que em vez do valor da chave primaria do compositor
        * vai retornar o nome do mesmo
        */
        public static function getObra2($idObra) {
            $statement = Obra::$db->prepare("select * from Obra where idObra='".$idObra."';");
            $statement->execute();
            $row = $statement->fetch(); // Converter $row num array standard

            $o = new Obra($row[1], $row[2], $row[3], $row[4], $row[5], $row[6], $row[7]);

            // Converter duracao para formato legivel
            $o->duracao = $o->getStringDuracao();

            // Buscar nome do compositor da obra
            $statement = Obra::$db->prepare("select nome from Compositor where id=".$o->idCompositor.";");
            $statement->execute();
            $row = $statement->fetch();

            $o->nomeCompositor = $row[0];

            return $o;
        }

        /*
        * Este metodo permite obter a duracao de uma obra pelo seu identificador
        */
        public static function getDuracaoDaObra($idObra) {
            $statement = Obra::$db->prepare("select duracao from Obra where idObra='".$idObra."';");
            $statement->execute();
            $row = $statement->fetch();
            
            return $row[0];
        }

        /*
        * Este metodo permite obter o nome do compositor da obra.
        */
        public static function getNomeCompositorDaObra($idObra) {
            $statement = Obra::$db->prepare("select Compositor.nome from Obra inner join Compositor on Obra.idCompositor=Compositor.id where Obra.idObra='".$idObra."';");
            $statement->execute();
            $row = $statement->fetch();
            return $row[0];
        }

        /*
        * Obter valor do total de obras na base de dados.
        */
        public static function getTotalObras() {
            // Usar prepared statements para evitar SQL Injection
            $statement = Obra::$db->prepare("select count(*) from Obra;");
            $statement->execute();
            $result = $statement->fetch(); // Converter $row num array standard
            return $result[0];
        }

        /*
        * Apagar uma obra pelo seu identificador.
        */
        public static function delete($idObra) {
            $statement = Obra::$db->prepare("delete from Obra where idObra='".$idObra."';");
            $statement->execute();
        }

        /*
        * Migrar dados de um data set em XML com diversos compositores para
        * uma base de dados relacional MySQL.
        */
        public static function migrateXMLToMySQL() {
            $obrasxml = simplexml_load_file(Obra::DATASET);
            $obras =  $obrasxml->xpath("//obra");
            
            $periodos[] = "Medieval (400-1450)";
            $periodos[] = "Renascentista (1450-1600)";
            $periodos[] = "Barroco (1600-1750)";
            $periodos[] = "Clássico (1750-1810)";
            $periodos[] = "Romântico (1810-1910)";
            $periodos[] = "Contemporâneo (a partir de 1900)";

            foreach ($obras as $o) {
                $id = (string)$o[@id];
            	$nome = $o->nome;
                $descricao = $o->desc;

                $nomeCompositorOk = preg_quote($o->compositor, '\'*\\.');
            	$compositor = Compositor::getChavePrimariaDoCompositor($nomeCompositorOk);

                $data = $o->anoCriacao;
                $periodo = $o->periodo;
                $duracao = $o->duracao;

                $novaobra = new Obra($id, $nome, $descricao, $compositor, $data, $periodo, $duracao);
                $novaobra->insert();
            }
        }

        /*----------------------------------------------------------------------
            Metodos que geram HTML
        ----------------------------------------------------------------------*/

        /*
        * Permite criar uma página de visualização da obra.
        */
        public static function criarPaginaDaObra($idObra) {
            $o = Obra::getObra($idObra);

            // Compositor da obra
            $statement = Obra::$db->prepare("select idComp, nome from Compositor where id=".$o->idCompositor.";");
            $statement->execute();
            $row = $statement->fetch(); // Converter $row num array standard

            $idComp = $row[0];
            $nomeComp = $row[1];

            echo "\t<a href='list_obras.html'><i class='fa fa-arrow-left'></i>  Ir para catálogo de Obras</a><br/>\n";
            echo "\t\t<a href='../compositores/consult_compositor.html?idComp=".$idComp."'><i class='fa fa-arrow-left'></i>  Ir para página de ".$nomeComp."</a>\n";
            echo "\t\t<div class='box box-solid' style='margin-top: 0.5em;'>\n";
            echo "\t\t\t<div class='box-header with-border'>\n";
            echo "\t\t\t\t<h2 id='nome1' style='font: bold;'>".$o->nome."</h2>\n";
            echo "\t\t\t\t<a href='edit_obra.html?idObra=".$o->idObra."'><i class='fa fa-pencil-square-o'></i> Editar</a>\n";
            echo "\t\t\t</div>\n";
            echo "\t\t\t<div class='box-body'>\n";
            echo "\t\t\t\t<div style='float:right;width:25%;'>\n";
            echo "\t\t\t\t\t<div class='thumbnail'>\n";
            echo "\t\t\t\t\t\t<center><i class='fa fa-4x fa-music'></i></center>\n";
            echo "\t\t\t\t\t\t<center><h3 id='nome2'>".$o->nome."</h3></center>\n";
            echo "\t\t\t\t\t\t<center><p id='datas'><b>".$o->data."</b></p></center>\n";
            echo "\t\t\t\t\t\t<center><i class='fa fa-clock-o'></i> ".$o->getStringDuracao()."</p></center>\n";
            echo "\t\t\t\t\t\t<center><p><b>Período</b><br/> ".$o->periodo."</p></center>\n";
            echo "\t\t\t\t\t</div>\n";
            echo "\t\t\t\t</div>\n";
            echo "\t\t\t\t<div style='width:70%;float:left;'>\n";
            echo "\t\t\t\t\t<p><h3>Descrição</h3></p>\n";
            echo "\t\t\t\t\t<p>".$o->descricao."</p>\n";
            echo "\t\t\t\t</div>\n";
            echo "\t\t\t</div>\n";
            echo "\t\t</div>\n";
            
            // Invocar metodo de Partituras para listar partituras da obra
            Partitura::criarListagensDePartiturasDaObra($o->idObra, $o->nome);

            echo "\t\t<a class='btn btn-danger' onclick='deleteObra()' style='float:right;'>\n";
            echo "\t\t\t<i class='fa fa-trash-o fa-lg'></i> Apagar Obra\n";
            echo "\t\t</a>\n";
        }

        /*
        * Metodo que gera toda a tabela de obras. [TODA A TABELA NA MESMA PAGINA]
        */
        public static function criarTabelaDeObras() {
            echo "\t<div class='box-body no-padding' style='margin: 2em;'>\n";
            echo "\t\t <table class='table table-striped'>\n";
            echo "\t\t\t<tbody><tr>\n";
            echo "\t\t\t\t<th>ID</th>\n";
            echo "\t\t\t\t<th>Nome</th>\n";
            echo "\t\t\t\t<th>Compositor</th>\n";
            echo "\t\t\t\t<th style='text-align: left;'><i class='fa fa-clock-o'></i></th>\n";
            echo "\t\t\t\t<th>Acções</th>\n";
            echo "\t\t\t</tr>\n";
            foreach(Obra::$db->query('select * from Obra order by nome;') as $row) {
                $o = new Obra($row[1], $row[2], $row[3], $row[4], $row[5], $row[6], $row[7]);
                
                echo "\t\t\t<tr>\n";
                echo "\t\t\t\t<td>".$o->idObra."</td>\n";
                echo "\t\t\t\t<td>".$o->nome."</td>\n";
                echo "\t\t\t\t<td>".Compositor::getNomeDoCompositorPelaChavePrimaria($o->idCompositor)."</td>\n";
                echo "\t\t\t\t<td>".$o->getStringDuracao()."</td>\n";
                echo "\t\t\t\t<td>
                <a href='consult_obra.html?idObra=".$o->idObra."'>consult<i class='fa fa-eye' style='margin-right: 2px;'></i></a>
                <a href='edit_obra.html?idObra=".$o->idObra."'>edit<i class='fa fa-edit' style='margin-right: 2px;'></i></a>
                <a href='#' id='".$o->idObra."' onclick='deleteObra()'>delete<i class='fa fa-trash'></i></a></td>\n";
            }
            echo "\t\t\t</tr>\n";
            echo "\t\t\t</tbody>\n";
            echo "\t\t</table>\n";
            echo "\t</div>\n";
           
        }

        /*
        * Metodo que gera toda a tabela de obras, com paginacao. [VARIAS TABELAS COM NAVEGACAO PARA DIREITA E ESQUERDA]
        */
        public static function criarTabelaDeObrasPaginada() {
            $tpaginada = new TabelaPaginada(12, Obra::getTotalObras());

            $pagina=1;
            $save_i=0;
            $i=0;
            $novaTabela = ""; // nova tabela da pagina $i+1
            $tabelaTerminada = true;

            foreach(Obra::$db->query('select * from Obra order by nome;') as $row) {
                $o = new Obra($row[1], $row[2], $row[3], $row[4], $row[5], $row[6], $row[7]);
                
                if( $tabelaTerminada==true ) {
                    // Se e divisivel pelo n de linhas da tabela, entao cria-se nova tabela
                    $tabelaTerminada=false;
                    $novaTabela = ""; // reniciar string da tabela

                    $novaTabela.= "\t<div class='box-body no-padding' style='margin: 2em;'>\n";
                    $novaTabela.= "\t\t <table class='table table-striped'>\n";
                    $novaTabela.= "\t\t\t<tbody><tr>\n";
                    $novaTabela.= "\t\t\t\t<th>ID</th>\n";
                    $novaTabela.= "\t\t\t\t<th>Nome</th>\n";
                    $novaTabela.= "\t\t\t\t<th>Compositor</th>\n";
                    $novaTabela.= "\t\t\t\t<th style='text-align: left;'><i class='fa fa-clock-o'></i></th>\n";
                    $novaTabela.= "\t\t\t\t<th>Acções</th>\n";
                    $novaTabela.= "\t\t\t</tr>\n";
                    // echo "ABRE TABELA: ".$i."\n";
                }

                // echo "LINHA DE UMA TABELA ".$i."\n";
                $novaTabela.= "\t\t\t<tr>\n";
                $novaTabela.= "\t\t\t\t<td>".$o->idObra."</td>\n";
                $novaTabela.= "\t\t\t\t<td>".$o->nome."</td>\n";
                $novaTabela.= "\t\t\t\t<td>".Compositor::getNomeDoCompositorPelaChavePrimaria($o->idCompositor)."</td>\n";
                $novaTabela.= "\t\t\t\t<td>".$o->getStringDuracao()."</td>\n";
                $novaTabela.= "\t\t\t\t<td>
                <a href='consult_obra.html?idObra=".$o->idObra."'><i class='fa fa-eye' style='margin-right: 2px;'></i></a>
                <a href='edit_obra.html?idObra=".$o->idObra."'><i class='fa fa-edit' style='margin-right: 2px;'></i></a>\n";
                
                if( $save_i!=$i && ($i % $tpaginada->nLinhasPorTabela) == 0 ) {
                    // Se e divisivel pelo n de linhas da tabela fecha-se a tabela
                    // echo "FECHA TABELA i: ".$i."  save_i: ".$save_i."\n";
                    $save_i=$i;
                    $tabelaTerminada=true;

                    $novaTabela.= "\t\t\t</tr>\n";
                    $novaTabela.= "\t\t\t</tbody>\n";
                    $novaTabela.= "\t\t</table>\n";
                    $novaTabela.= "\t</div>\n";
                    $novaTabela.= "\t<center><p><b>Página ".$pagina." de ".$tpaginada->totalPaginas."</b></p></center>\n";
                    if($pagina==1) {
                        $novaTabela.="\t<center><a href='#' onclick='proximaPagina()'>Próxima  <i class='fa fa-arrow-right'></i></a></center>\n";
                    } else if( $pagina==$tpaginada->totalPaginas ) {
                        $novaTabela.="\t<center><a href='#' onclick='paginaAnterior()'><i class='fa fa-arrow-left'></i>  Anterior</a></center>\n";
                    } else {
                        $novaTabela.="\t<center><a href='#' onclick='paginaAnterior()'><i class='fa fa-arrow-left'></i>  Anterior</a>   <a href='#' onclick='proximaPagina()'>  Próxima  <i class='fa fa-arrow-right'></i></a></center>\n";
                    }

                    // Escrever para um ficheiro html o codigo da tabela
                    $file = fopen("./.paginas/pagina_".$pagina.".html", "wr") or die("Unable to open file!");
                    fwrite($file, $novaTabela);
                    fclose($file);

                    $pagina++;
                }

                $i++;
            }

            $i--;
            if( ($i % $tpaginada->nLinhasPorTabela) != 0 ) {
                // Se e divisivel pelo n de linhas da tabela fecha-se a tabela
                // echo "FECHA TABELA QUE NAO FECHOU i: ".$i."  save_i: ".$save_i."\n";
                $save_i=$i;
                $tabelaTerminada=true;

                $novaTabela.= "\t\t\t</tr>\n";
                $novaTabela.= "\t\t\t</tbody>\n";
                $novaTabela.= "\t\t</table>\n";
                $novaTabela.= "\t</div>\n";
                $novaTabela.= "\t<center><p><b>Página ".($i+1)." de ".$tpaginada->totalPaginas."</b></p></center>";
                $tpaginada->tabelas[] = $novaTabela; // Adicionar tabela ao array de tabelas
            }

            return $tpaginada;
        }

        /*----------------------------------------------------------------------*/
        /*----------------------------------------------------------------------*/
        

        // Métodos de instância


        /*
        * Inserir uma instancia de Obra na base de dados.
        */
        public function insert() {
            $query = "insert into Obra(idObra, nome, descricao, idCompositor, data, periodo, duracao) values(?, ?, ?, ?, ?, ?, ?);";
            $statement = Obra::$db->prepare($query);
            $statement->bindParam(1, $this->idObra);
            $statement->bindParam(2, $this->nome);
            $statement->bindParam(3, $this->descricao);
            $statement->bindParam(4, $this->idCompositor);
            $statement->bindParam(5, $this->data);
            $statement->bindParam(6, $this->periodo);
            $statement->bindParam(7, $this->duracao);
            $statement->execute();
        }

        /*
        * Metodo que permite atualizar os dados de uma obra consoante os campos alterados.
        */
        public function update($atributos) {
            $query = "update Obra set ";

            foreach ($atributos as $atr) {
                switch ($atr) {
                    case 'idCompositor':
                        $query.=$atr."=".$this->$atr.",";
                        break;
                    case 'duracao':
                        $query.=$atr."=".$this->$atr.",";
                        break;                    
                    default:
                        $query.=$atr."='".$this->$atr."',";
                        break;
                }
            }
            $query = rtrim($query, ","); // Remover ultima virgula que fica na string $query

            $query.=" where idObra='".$this->idObra."';";
            $statement = Obra::$db->prepare($query);
            $statement->execute();
        }

        /*
        * Este metodo transforma um inteiro que representa a duracao de uma obra
        * numa string do tipo mm:ss mais legivel do que o numero de segundos.
        */
        public function getStringDuracao() {
            // PHP is awesome!
            if($this->duracao > 0) {
                return gmdate('i:s', $this->duracao);
            } else return "(duração indefinida)";
        }

        /*
        * Este metodo transforma uma string no formato hh:mm:ss
        * no correspondente valor em segundos
        */
        public function converterEmSegundos($str_tempo) {
            sscanf($str_tempo, "%d:%d:%d", $horas, $minutos, $segundos);
            $tempoSegundos = isset($segundos) ? $horas * 3600 + $minutos * 60 + $segundos : $horas * 60 + $minutos;   
            return $tempoSegundos;
        }
    }
    // Instanciar variavel de acesso a base de dados apenas uma vez, guardar interface de acesso
    // em variavel de classe
    Obra::$db = AbstractPDO::getInstance();
?>
