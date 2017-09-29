<?php

    require_once 'AbstractPDO.php';
    require_once 'simple_html_dom.php';

    class Compositor {
        const ID_PREFIX = 'COMP';
        const DATASET = '../datasets/compositores.xml';
        const FOTO_DEFAULT = "http://www.lajesfss.com/public/common/images/_default_user_social.gif";
        public static $db;

        public $idComp;
        public $nome;
        public $dataNasc;
        public $dataMorte;
        public $periodo;
        public $biografia;
        public $fotoURL;

        public function __construct($id, $nome, $dataNasc, $dataMorte, $periodo, $biografia, $fotoURL) {
            if($id=="") {
                $n = Compositor::gerarIdentificador();
                $this->idComp = "";
                $this->idComp .= Compositor::ID_PREFIX.$n;
            } else {
                $this->idComp = $id;
            }
            $this->nome = $nome;
            $this->dataNasc = $dataNasc;
            $this->dataMorte = $dataMorte;
            $this->periodo = $periodo;
            $this->biografia = $biografia;
            $this->fotoURL = "";
            if($fotoURL=="") {
                $url = Compositor::goGoogle($nome);
                if(!empty($url)){
                    $this->fotoURL = $url;
                } else {
                    $this->fotoURL .= Compositor::FOTO_DEFAULT;
                }
            } else {
                $this->fotoURL = $fotoURL;
            }
            
        }


        // Métodos de classe

        /*
        * Este metodo gera um identificador unico para uma instancia.
        */
        public static function gerarIdentificador() {
            $statement = Compositor::$db->prepare("select max(id) from Compositor;");
            $statement->execute();
            $result = $statement->fetch();
            return $result[0]+1;
        }

        /*
        * Este metodo permite obter uma instancia de um compositor atraves do seu identificador.
        */
        public static function getCompositor($idComp) {
            $statement = Compositor::$db->prepare("select * from Compositor where idComp='".$idComp."';");
            $statement->execute();
            $row = $statement->fetch(); // Converter $row num array standard

            $c = new Compositor($row[1], $row[2], $row[3], $row[4], $row[5], $row[6], $row[7]);

            return $c;
        }

        /*
        * Obter valor do total de compositores na base de dados.
        */
        public static function getTotalCompositores() {
            // Usar prepared statements para evitar SQL Injection
            $statement = Compositor::$db->prepare("select count(*) from Compositor;");
            $statement->execute();
            $result = $statement->fetch(); // Converter $row num array standard
            return $result[0];
        }

        /*
        * Apagar um compositor pelo seu identificador.
        */
        public static function delete($idComp) {
            $statement = Compositor::$db->prepare("delete from Compositor where idComp='".$idComp."';");
            $statement->execute();
        }

        /*
        * Este metodo permite obter a chave primaria da base de dados de um Compositor pelo seu nome.
        */
        public static function getChavePrimariaDoCompositor($nome) {
            $statement = Compositor::$db->prepare("select id from Compositor where nome='".$nome."';");
            $statement->execute();
            $row = $statement->fetch();
            return $row[0];
        }

        /*
        * Este metodo permite obter o nome de um compositor uma vez fornecida a sua chave primaria.
        */
        public static function getNomeDoCompositorPelaChavePrimaria($id) {
            $statement = Compositor::$db->prepare("select nome from Compositor where id=".$id.";");
            $statement->execute();
            $row = $statement->fetch();
            return $row[0];
        }

        /*
        * Este metodo retorna um array com o nome de todos os compositores registados na base de dados.
        * Foi criado com o proposito de alimentar a drop down da criacao de um obra, onde se tem de escolher
        * um compositor.
        */
        public static function getNomesCompositores() {
            $nomes = array();
            foreach(Compositor::$db->query("select nome from Compositor order by nome;") as $row) {
                $nomes[] = $row[0];
            }
            return $nomes;
        }

        /*
        * Metodo que normaliza datas segundo o formato de apresentação da interface web.
        */
        public static function normalizaFormatoData($data) {
            $dn = explode("/", $data);
            $res="";
            switch(count($dn)) {
                case 1:
                    $res = "dd"."/mm/".$dn[0];
                    break;
                case 2:
                    $res = "dd/".$dn[0]."/".$dn[1];
                    break;
                default:
                    $res=$dn;
                    break;
            }
            return $res;
        }

        /*
        * Este metodo tenta procurar no google uma imagem relacionada com o parametro de pesquisa
        * $search_query.
        */
        public static function goGoogle($search_query){
            // $search_query = "Alessandro Marcello";
            $search_query = urlencode( $search_query );
            $html = file_get_html( "https://www.google.com/search?q=".$search_query."&tbm=isch" );
            if(!empty($html)){
                $image = $html->find('img', 0);
                for($i=1; $i<5; $i++){   
                    $img = $html->find('img', $i);
                    if(!empty($img)){
                        $url = $img->src;
                        
                        // Filtrar resultados para obter tamanho ideal de uma Thumbnail
                        if($img->width>=50 && $img->width<=250){
                            if($img->height>=100 && $img->height<=300){
                                return $url;
                                break;
                            }
                        }
                    }
                }
            }
        }

        /*
        * Migrar dados de um data set em XML com diversos compositores para
        * uma base de dados relacional MySQL.
        */
        public static function migrateXMLToMySQL() {
            $compositores = simplexml_load_file(Compositor::DATASET);
            $comps =  $compositores->xpath("//compositor");
            
            $periodos[] = "Medieval";
            $periodos[] = "Renascentista";
            $periodos[] = "Barroco";
            $periodos[] = "Clássico";
            $periodos[] = "Romântico";
            $periodos[] = "Contemporâneo";

            foreach ($comps as $c) {
                $dataNasc = $c->dataNasc;
                $dataMorte = $c->dataObito;

                $anonasc=-1;
                $anomorte=-1;
                $dnfinal = "";
                $dmfinal = "";
                $periodo = "";
                $foto = "";
                $foto = Compositor::goGoogle($c->nome);
                if(!empty($foto));
                else { $foto.=Compositor::FOTO_DEFAULT; }

                $dn = explode("-", $dataNasc);
                if(!empty($dn[2])){
                    $dnfinal = $dn[2]."/".$dn[1]."/".$dn[0];
                    $anonasc = (int)$dn[0];
                } else {
                    $dnfinal = $dataNasc;
                    $anonasc = (int)$dnfinal;
                }

                $dm = explode("-", $dataMorte);
                if(!empty($dm[2])){
                    $dmfinal = $dm[2]."/".$dm[1]."/".$dm[0];
                    $anomorte = (int)$dn[0];
                } else {
                    $dmfinal = $dataMorte;
                    $anomorte = (int)$dataMorte;
                }

                if($anonasc>400 && $anomorte<=1450) {
                    $periodo = $periodos[0];
                } else if($anonasc>1450 && $anomorte<=1600) {
                    $periodo = $periodos[1];
                } else if($anonasc>1600 && $anomorte<=1750) {
                    $periodo = $periodos[2];
                } else if($anonasc>1750 && $anomorte<=1810) {
                    $periodo = $periodos[3];
                } else if($anonasc>1810 && $anomorte<=1910) {
                    $periodo = $periodos[4];
                } else if($anonasc>1910) {
                    $periodo = $periodos[5];
                } else {
                    $periodo = $periodos[rand(1,5)];
                }
                
                $id = (string)$c[@id];
                $f = iconv(mb_detect_encoding($foto, mb_detect_order(), true), "UTF-8", $foto);
                $novocomp = new Compositor($id, $c->nome, $dnfinal, $dmfinal, $periodo, $c->bio, $f);

                $novocomp->insert();
            }
        }

        /*----------------------------------------------------------------------
            Metodos que geram HTML
        ----------------------------------------------------------------------*/

        /*
        * Metodo que gera todo o catalogo de compositores.
        */
        public static function criarCatalogoDeCompositores() {
            $letraAtual="";
            $isFirstRow=true;
            foreach(Compositor::$db->query('select * from Compositor order by nome;') as $row) {
                $c = new Compositor($row[1], $row[2], $row[3], $row[4], $row[5], $row[6], $row[7]);
                if($c->nome[0]!=$letraAtual) {
                    $letraAtual = $c->nome[0]; // Atualizar a primeira letra
                    if(!$isFirstRow){
                        echo "\t\t</div>\n";
                    }
                    echo "\t<div id='row' class='row' style='padding: 2em;'>\n";
                    echo "\t\t<h2 style='font-weight: bold; padding: 1em;'>".$letraAtual."</h2>\n";
                    $isFirstRow=false;
                }

                // Thumbnail do Compositor
                echo "\t\t<div class='col-md-3 col-sm-6 col-xs-12' style='padding:1em;'>\n";
                echo "\t\t\t<div class='thumbnail'>\n";
                echo "\t\t\t\t<center><h4 style='text-aling: center;'>".$c->nome."</h4></center>\n";
                echo "\t\t\t\t<a href='consult_compositor.html?idComp=".$c->idComp."'><img class='img-square' src='".$c->fotoURL."'></a>\n";
                echo "\t\t\t</div>\n";
                echo "\t\t</div>\n";
            }
            echo "\t\t</div>\n"; // Fechar a última row
        }

        public static function criarPaginaDoCompositor($idComp) {
            $c = Compositor::getCompositor($idComp);

            echo "\t\t<a href='list_compositores.html'><i class='fa fa-arrow-left'></i>  Voltar ao catálogo de Compositores</a>\n";
            echo "\t\t<div class='box box-solid' style='margin-top: 0.5em;'>\n";
            echo "\t\t\t<div class='box-header with-border'>\n";
            echo "\t\t\t\t<h2 id='nome1' style='font: bold;'>".$c->nome."</h2>\n";
            echo "\t\t\t\t<a href='edit_compositor.html?idComp=".$c->idComp."'><i class='fa fa-pencil-square-o'></i> Editar</a>\n";
            echo "\t\t\t</div>\n";
            echo "\t\t\t<div class='box-body'>\n";
            echo "\t\t\t\t<div style='float:right;width:25%;'>\n";
            echo "\t\t\t\t\t<div class='thumbnail'>\n";
            echo "\t\t\t\t\t\t<center><h3 id='nome2'>".$c->nome."</h3></center>\n";
            echo "\t\t\t\t\t\t<img src='".$c->fotoURL."'/>\n";
            echo "\t\t\t\t\t\t<center><p id='datas'>".$c->dataNasc;
            if($c->dataMorte!="") {
                echo " - ".$c->dataMorte."</p></center>\n";
            } else {
                echo "</p></center>\n";
            }
            echo "\t\t\t\t\t\t<center><p><b>Período</b><br/> ".$c->periodo."</p></center>\n";
            echo "\t\t\t\t\t</div>\n";
            echo "\t\t\t\t</div>\n";
            echo "\t\t\t\t<div style='width:70%;float:left;'>\n";
            echo "\t\t\t\t\t<p><h3>Biografia</h3></p>\n";
            echo "\t\t\t\t\t<p>".$c->biografia."</p>\n";
            echo "\t\t\t\t</div>\n";
            echo "\t\t\t</div>\n";
            echo "\t\t</div>\n";

            Compositor::criarListagensDeObrasDoCompositor($c->nome); // metodo definido logo de seguida

            echo "\t\t<a class='btn btn-danger' onclick='deleteCompositor()' style='float:right;'>\n";
            echo "\t\t\t<i class='fa fa-trash-o fa-lg'></i> Apagar Compositor\n";
            echo "\t\t</a>\n";
        }

        /*
        * Metodo que gera listangens de obras pertencentes a um determinado compositor.
        */
        public static function criarListagensDeObrasDoCompositor($nomeCompositor) {
            $letraAtual="";
            $isFirstRow=true;

            $id = Compositor::getChavePrimariaDoCompositor($nomeCompositor);

            echo "\t\t<div class='box box-solid'>\n";
            echo "\t\t\t<div class='box-header with-border'>\n";
            echo "\t\t\t\t<h2 style='font: bold;'>Obras</h2>\n";
            echo "\t\t\t\t\t<a href='../obras/add_obra.html?nome=".$nomeCompositor."' style='font-size: 16px;'><i class='fa fa-plus' style='font-size: 14px;'></i>   Adicionar Obra</a>\n";
            echo "\t\t\t</div>\n";
            echo "\t\t\t<div style='padding-top: 1em;'>\n";
            echo "\t\t\t\t<ul>\n";

            foreach(Compositor::$db->query("select * from Obra where idCompositor=".$id." order by nome;") as $row) {
                $idObra = $row[1];
                $nome = $row[2];
                $data = $row[5];
                if($o->data!="") {  
                    echo "\t\t\t\t\t<li style='margin-bottom: 0.5em;'><a style='font-size: 16px;' href='../obras/consult_obra.html?idObra=".$idObra."'>".$nome." (".$data.")</a></li>\n";
                } else {
                    echo "\t\t\t\t\t<li style='margin-bottom: 0.5em;'><a style='font-size: 16px;' href='../obras/consult_obra.html?idObra=".$idObra."'>".$nome."</a></li>\n";
                }
            }

            echo "\t\t\t\t</ul>\n";
            echo "\t\t\t</div>\n";
            echo "\t\t</div>\n"; // Fechar a última row
        }

        /*----------------------------------------------------------------------*/
        /*----------------------------------------------------------------------*/


        // Métodos de instância


        /*
        * Inserir uma instancia de Compositor na base de dados.
        */
        public function insert() {
            $query = "insert into Compositor(idComp, nome, dataNasc, dataMorte, periodo, biografia, fotoURL) values(?, ?, ?, ?, ?, ?, ?);";
            $statement = Compositor::$db->prepare($query);
            $statement->bindParam(1, $this->idComp);
            $statement->bindParam(2, $this->nome);
            $statement->bindParam(3, $this->dataNasc);
            $statement->bindParam(4, $this->dataMorte);
            $statement->bindParam(5, $this->periodo);
            $statement->bindParam(6, $this->biografia);
            $statement->bindParam(7, $this->fotoURL);
            $statement->execute();
        }

        /*
        * Metodo que permite atualizar os dados de um compositor consoante os campos alterados.
        */
        public function update($atributos) {
            $query = "update Compositor set ";

            // Inserir na query os atributos modificados
            // !Atencao aos tipos de dados no caso do compositor sao todos strings senao tem de se fazer
            // cast para verificar se atributo leva ou nao aspas
            foreach ($atributos as $atr) {
                $query.=$atr."='".$this->$atr."',";
            }
            $query = rtrim($query, ","); // Remover ultima virgula que fica na string $query

            $query.=" where idComp='".$this->idComp."';";

            $statement = Compositor::$db->prepare($query);
            $statement->execute();
        }
    }
    // Instanciar variavel de acesso a base de dados apenas uma vez, guardar interface de acesso
    // em variavel de classe
    Compositor::$db = AbstractPDO::getInstance();
    
?>
