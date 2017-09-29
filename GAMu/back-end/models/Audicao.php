<?php

    require_once 'AbstractPDO.php';
    require_once 'Atuacao.php';

    class Audicao {
        const ID_PREFIX = 'AUD';
        const DATASET = '../datasets/audicoes.xml';

        public static $db;

        public $pk; // Atencao! so usar a quando do use de Audicao::getAudicao($idAudicao)
        public $idAudicao;
        public $titulo;
        public $subtitulo;
        public $tema;
        public $data;
        public $horainicio;
        public $horafim;
        public $duracao;
        public $local;
        public $idsAtuacoes; // array

        public function __construct($id, $titulo, $subtitulo, $tema, $data, $horainicio, $horafim, $duracao, $local) {
            if($id=="") {
                $n = Audicao::gerarIdentificador();
                $this->idAudicao = "";
                $this->idAudicao .= Audicao::ID_PREFIX.$n;
            } else {
                $this->idAudicao = $id;
            }
            $this->titulo = $titulo; 
            $this->subtitulo = $subtitulo;
            $this->tema = $tema;
            $this->data = $data; 
            $this->horainicio = $horainicio;
            $this->horafim = $horafim;
            $this->duracao = $duracao;
            $this->local = $local;  

            // Inicializar array
            $this->idsAtuacoes = array();
        }


        // Métodos de classe

        /*
        * Este metodo gera um identificador unico para uma instancia.
        */
        public static function gerarIdentificador() {
            $statement = Audicao::$db->prepare("select max(id) from Audicao;");
            $statement->execute();
            $result = $statement->fetch();
            return $result[0]+1;
        }
        

        /*
        * Este metodo permite obter uma instancia de um Audicao atraves do seu identificador.
        */
        public static function getAudicao($idAudicao) {
            $statement = Audicao::$db->prepare("select * from Audicao where idAudicao='".$idAudicao."';");
            $statement->execute();
            $row = $statement->fetch(); // Converter $row num array standard

            $ad = new Audicao($row[1], $row[2], $row[3], $row[4], $row[5], $row[6], $row[7], Audicao::getStringDuracao($row[8]), $row[9]);
            $ad->pk = $row[0];

            return $ad;
        }

        /*
        * Este metodo permite obter uma instancia de um Audicao atraves da sua chave primaria
        */
        public static function getAudicaoPelaChavePrimaria($idAudicao) {
            $statement = Audicao::$db->prepare("select * from Audicao where id=".$idAudicao.";");
            $statement->execute();
            $row = $statement->fetch(); // Converter $row num array standard

            $ad = new Audicao($row[1], $row[2], $row[3], $row[4], $row[5], $row[6], $row[7], $row[8], $row[9]);

            return $ad;
        }

        /*
        * Este metodo permite obter identificador do Audicao através da sua chave primaria.
        */
        public static function getIdentificadorDaAudicaoPelaChavePrimaria($id) {
            $statement = Audicao::$db->prepare("select idAudicao from Audicao where id=".$id.";");
            $statement->execute();
            $row = $statement->fetch(); // Converter $row num array standard
            return $row[0];
        }

        /*
        * Este metodo permite obter a chave primaria do Audicao atraves do seu identificador.
        */
        public static function getChavePrimariaPeloIdentificador($idAudicao) {
            $statement = Audicao::$db->prepare("select id from Audicao where idAudicao='".$idAudicao."';");
            $statement->execute();
            $row = $statement->fetch(); // Converter $row num array standard

            return $row[0];
        }

        /*
        * Obter valor do total de Audicoes na base de dados.
        */
        public static function getTotalAudicoes() {
            // Usar prepared statements para evitar SQL Injection
            $statement = Audicao::$db->prepare("select count(*) from Audicao;");
            $statement->execute();
            $result = $statement->fetch(); // Converter $row num array standard
            return $result[0];
        }

        /*
        * Este metodo transforma um inteiro que representa a duracao de uma obra
        * numa string do tipo mm:ss mais legivel do que o numero de segundos.
        */
        public static function getStringDuracao($duracao) {
            // PHP is awesome!
            if($duracao > 0) {
                return gmdate('i:s', $duracao);
            } else return "";
        }

        /*
        * Apagar um Audicao pelo seu identificador.
        */
        public static function delete($idAudicao) {
            $statement = Audicao::$db->prepare("delete from Audicao where idAudicao='".$idAudicao."';");
            $statement->execute();
        }

        /*
        * Metodo que permite associar um dado atuacao a um determinado audicao, ambos
        * passados como parametro.
        */
        public static function associarAudicaoAtuacao($idAtuacao, $idAudicao) {
            $atuacaoPK = Atuacao::getChavePrimariaPeloIdentificador($idAtuacao);
            $audicaoPK = Audicao::getAudicaoPelaChavePrimaria($idAudicao);
            
            $query = "insert into AtuacaoAudicao(idAtuacao, idAudicao) values(?, ?);";
            $statement = Atuacao::$db->prepare($query);
            $statement->bindParam(1, $atuacaoPK);
            $statement->bindParam(2, $audicaoPK);      
            
            $statement->execute();
        }

        /*
        * Metodo que permite dissociar um dado audicao de um determinado atuacao, ambos
        * passados como parametro.
        */
        public static function dissociarAudicaoDaAtuacao($idAtuacao, $idAudicao) {
            $atuacaoPK = Atuacao::getChavePrimariaPeloIdentificador($idAtuacao);
            $audicaoPK = Audicao::getAudicaoPelaChavePrimaria($idAudicao);
            

            $query = "delete from AtuacaoAudicao where idAtuacao=".$atuacaoPK." and idAudicao=".$audicaoPK.";";
            $statement = Atuacao::$db->prepare($query);
            $statement->execute();
        }

        /*
        * Migrar dados de um data set em XML com diversos audições para
        * uma base de dados relacional MySQL.
        */
        public static function migrateXMLToMySQL() {

            $audicoes = simplexml_load_file(Audicao::DATASET);
            $auds =  $audicoes->xpath("//audicao");           

            foreach ($auds as $ad) {                 
                $titulo = $ad->titulo;
                $id = (string)$ad[@id];
                $subtitulo = $ad->subtitulo;
                $tema = $ad->tema;
                $data = $ad->data;
                $horainicio = $ad->horaini;
                $horafim = $ad->horafim;
                $duracao = Audicao::converterEmMinutos($horafim)-Audicao::converterEmMinutos($horainicio);
                $local = $ad->local;
                $novaaudicao = new Audicao($id, $titulo, $subtitulo, $tema, $data, $horainicio, $horafim, $duracao, $local);
                $novaaudicao->insert();
            }
        }

        /*
        * Metodo que permite extrair de XML uma AUDICAO e criar um objecto.
        */
        public static function extrairAudicaoDeXML($ficheiro) {
            $aud = simplexml_load_file($ficheiro);

            $titulo = (string)$aud->titulo;

            $subtitulo = (string)$aud->subtitulo;
            if($subtitulo==null) $subtitulo="";

            $tema = (string)$aud->tema;
            if($tema==null) $tema="";

            $data = (string)$aud->data;
            $horaini = (string)$aud->horaini;

            $horafim = (string)$aud->horafim;
            if($horafim==null) $horafim="";

            $duracao = (string)$aud->duracao;
            if($duracao==null) $duracao="";

            $local = (string)$aud->hora;

            //              construct($id, $titulo, $subtitulo, $tema, $data, $horainicio, $horafim, $duracao, $local)
            $novaAudicao = new Audicao("", $titulo, $subtitulo, $tema, $data, $horaini, $horafim, $duracao, $local);

            // Atuacoes da audicao
            $atuacoes = $aud->xpath("//atuacao");
            if($atuacoes) {
                foreach ($atuacoes as $at) {
                    $novaAudicao->idsAtuacoes[] = (string)$at;
                }
            }

            return $novaAudicao;
        }

        /*
        * Metodo para criar ficheiro parcial da audicao isto e, apenas colocando o ID das atuacoes.
        */
        public static function exportarAudicaoParcialParaXML($idAudicao) {
            $aud = Audicao::getAudicao($idAudicao);

            // Criar ficheiro com elemento na raiz
            $date = new DateTime();
            $timestamp = $date->getTimestamp();
            $outputfilename = "dsl/FicheirosGerados/".$timestamp."_export_mysql.xml";
            $rfile = fopen($outputfilename, "w") or die("Incapaz de abrir o ficheiro para exportar audicao!");
            fwrite($rfile, "<audicao>\n</audicao>");

            $audicao = simplexml_load_file($outputfilename);

            // Exportar Meta Dados
            $audicao->addAttribute("id", $aud->idAudicao);
            $audicao->addChild("titulo", $aud->titulo);
            $audicao->addChild("subtitulo", $aud->subtitulo);
            $audicao->addChild("tema", $aud->tema);
            $audicao->addChild("data", $aud->data);
            $audicao->addChild("horainicio", $aud->horainicio);
            $audicao->addChild("horafim", $aud->horafim);
            $audicao->addChild("duracao", $aud->duracao);
            $audicao->addChild("local", $aud->local);

            // Atuacoes
            $atuacoes = $audicao->addChild("atuacoes");

            foreach (Audicao::$db->query("select Atuacao.idAtuacao from Atuacao
                                          inner join AtuacaoAudicao on AtuacaoAudicao.idAtuacao=Atuacao.id
                                          where AtuacaoAudicao.idAudicao=".$aud->pk.";") as $row) {
                $idAtuacao = $row[0];
                $nodoAtuacao = $atuacoes->addChild("atuacao", $idAtuacao);
            }

            // Fecho
            $dom = new DOMDocument('1.0');
            $dom->preserveWhiteSpace = false;
            $dom->formatOutput = true;
            $dom->loadXML($audicao->asXML());
            $dom->save($outputfilename);

            return $timestamp."_export_mysql.xml";
        }

        /*
        * Metodo que permite carregar da base de dados todas a informacao relativa a uma audicao
        * num ficheiro XML.
        */
        public static function exportarAudicaoParaXML($idAudicao) {
            $aud = Audicao::getAudicao($idAudicao);

            // Criar ficheiro com elemento na raiz
            $date = new DateTime();
            $timestamp = $date->getTimestamp();
            $outputfilename = "ficheiros/".$timestamp."_export_mysql.xml";
            $rfile = fopen($outputfilename, "w") or die("Incapaz de abrir o ficheiro para exportar audicao!");
            fwrite($rfile, "<audicao>\n</audicao>");

            $audicao = simplexml_load_file($outputfilename);

            // Exportar Meta Dados
            $audicao->addAttribute("id", $aud->idAudicao);
            $audicao->addChild("titulo", $aud->titulo);
            $audicao->addChild("subtitulo", $aud->subtitulo);
            $audicao->addChild("tema", $aud->tema);
            $audicao->addChild("data", $aud->data);
            $audicao->addChild("horainicio", $aud->horainicio);
            $audicao->addChild("horafim", $aud->horafim);
            $audicao->addChild("duracao", $aud->duracao);
            $audicao->addChild("local", $aud->local);

            // Atuacoes
            $atuacoes = $audicao->addChild("atuacoes");

            foreach (Audicao::$db->query("select Atuacao.idAtuacao from Atuacao
                                          inner join AtuacaoAudicao on AtuacaoAudicao.idAtuacao=Atuacao.id
                                          where AtuacaoAudicao.idAudicao=".$aud->pk.";") as $row) {
                $idAtuacao = $row[0];
                $atuacaoAtual = Atuacao::getAtuacao($idAtuacao);

                $nodoAtuacao = $atuacoes->addChild("atuacao");

                // Executantes
                if($atuacaoAtual->idsAlunos!=null) {
                    $nalunos = count($atuacaoAtual->idsAlunos);
                    $nodoAlunos = $nodoAtuacao->addChild("alunos");
                    $instrumentos = array();
                    foreach($atuacaoAtual->idsAlunos as $idA) {
                        $a = Aluno::getAluno($idA);
                        $partes_do_nome = explode(" ", $a->nome);
                        if(count($partes_do_nome)<3) {
                            $nodoAlunos->addChild("aluno", $a->nome);
                        } else {
                            $primeiro_e_ultimo = $partes_do_nome[0]." ".$partes_do_nome[count($partes_do_nome)-1];
                            $nodoAlunos->addChild("aluno", $primeiro_e_ultimo);
                        }
                        if(!in_array($a->instrumento, $instrumentos)) $instrumentos[]=$a->instrumento;
                    }
                }

                // Obras
                if($atuacaoAtual->idsObras!=null) {
                    $nobras = count($atuacaoAtual->idsObras);
                    $nodoObras = $nodoAtuacao->addChild("obras");
                    foreach ($atuacaoAtual->idsObras as $idO) {
                        $o = Obra::getObra($idO);
                        $nomeCompositor = Obra::getNomeCompositorDaObra($idO);

                        $nodoO = $nodoObras->addChild("obra", $o->nome);
                        $nodoO->addAttribute("compositor", $nomeCompositor);
                    }
                }

                // Dirigentes
                if($atuacaoAtual->idsProfessores!=null) {
                    $nprofs = count($atuacaoAtual->idsProfessores);
                    $nodoProfessores = $nodoAtuacao->addChild("professores");
                    if($nprofs>0) {
                        foreach ($atuacaoAtual->idsProfessores as $idP) {
                            $p = Professor::getProfessor($idP);
                            $partes_do_nome = explode(" ", $p->nome);
                            if(count($partes_do_nome)<3) {
                                $nodoProfessores->addChild("professor",$p->nome);
                            } else {
                                $nodoProfessores->addChild("professor", "Professor ".$primeiro_e_ultimo = $partes_do_nome[0]." ".$partes_do_nome[count($partes_do_nome)-1]);
                            }
                        }
                    }
                }

                // Acompanhantes
                if($atuacaoAtual->idsAcompanhantes!=null) {
                    $nacomp = count($atuacaoAtual->idsAcompanhantes);
                    if($nacomp>0) {
                        $nodoAcomps = $nodoAtuacao->addChild("acompanhantes");
                        foreach ($atuacaoAtual->idsAcompanhantes as $idAc) {
                            $isProf = false;
                            if(strpos($idAc, "P") !== false) {
                                $acm = Professor::getProfessor($idAc);
                                $isProf = true;
                            } else {
                                $acm = Aluno::getAluno($idAc);
                            }

                            $partes_do_nome = explode(" ", $acm->nome);
                            if(count($partes_do_nome)<3) {
                                if($isProf==true) {
                                    $nodoAcomps->addChild("acompanhante", "Professor ".$acm->nome);                                
                                } else {
                                    $nodoAcomps->addChild("acompanhante", $acm->nome);
                                }
                            } else {
                                $primeiro_e_ultimo = $partes_do_nome[0]." ".$partes_do_nome[count($partes_do_nome)-1];
                                if($isProf==true) {
                                    $nodoAcomps->addChild("acompanhante", "Professor ".$primeiro_e_ultimo);                                
                                } else {
                                    $nodoAcomps->addChild("acompanhante", $primeiro_e_ultimo);
                                }
                            }
                        }
                    }
                }
            }

            // Fecho
            $dom = new DOMDocument('1.0');
            $dom->preserveWhiteSpace = false;
            $dom->formatOutput = true;
            $dom->loadXML($audicao->asXML());
            $dom->save($outputfilename);

            return $outputfilename;
        }
        

        /*----------------------------------------------------------------------
            Metodos que geram HTML
            ----------------------------------------------------------------------*/

        /*
        * Metodo que permite que se gere o flyer da audicao depois da mesma ser validada
        * pelo processador em AntLR.
        * $ficheiros - Array de ficheiros XML gerados pelo processador AntLR que contem a informacao
        *              acerca da audicao e eventuais novas atuacoes;
        * $armazenarInfo - Valor booleano que nos informa se o utilizador pretende ou nao que a informacao seja
        *                  persistida na base de dados
        */
        public static function gerarFlyerDaAudicao($ficheiros) {
            // Path para os ficheiros XML gerados pelo processador AntLR
            $PATH = "dsl/FicheirosGerados/";

            // atuacao_AT2.xml  audicao_AUDNATAL2015.xml (Para teste...)
            $nfiles = count($ficheiros);
            $fAudicao = $ficheiros[($nfiles-1)];

            $novasAtuacoes = null; // Eventualmente ser um array de instancias de atuacao
            $audicao = null;

            if($nfiles>1) {
                $novasAtuacoes = array();
                // Temos ficheiros com atuacoes novas (que nao existem na bd)
                for($i=0; $i < ($nfiles-1); $i++) {
                    $novaAtuacao = Atuacao::extrairAtuacaoDeXML($PATH.$ficheiros[$i]);
                    // #DEBUG#
                    // echo json_encode($novaAtuacao);
                    $novasAtuacoes[] = $novaAtuacao;
                }
            }

            // Vamos extrair a Audicao do XML
            $audicao = Audicao::extrairAudicaoDeXML($PATH.$fAudicao);

            $conteudo="";
            $conteudo.="<title>".$audicao->titulo."</title>\n";
            $conteudo.= "<meta charset='UTF-8' />\n";
            $conteudo.= "</head>\n";
            $conteudo.= "<body>\n";
            $conteudo.= "<div id='container'>\n";
            $conteudo.= "<div id='page_title'><h1>".$audicao->titulo."</h1></div>\n";
            $conteudo.= "<div id='left_column'><div id='page_image'>&nbsp;</div></div>\n";

            $conteudo.= "<div id='right_column' style='overflow-y: auto;'>";
            $conteudo.= "<div id='audicao' style='text-align: center;'>";

            if(!empty($audicao->subtitulo)) {
                $conteudo.= "\t\t<h2>".$audicao->subtitulo."</h2><br/>\n";
            }
            if(!empty($audicao->tema)) {
                $conteudo.= "\t\t<h2>".$audicao->tema."</h2><br/>\n";
            }
            $conteudo.= "\t\t<h3>".$audicao->data." às ";
            $conteudo.= $audicao->horainicio;
            if(!empty($audicao->horafim)) {
                $conteudo.= " <small>(final previsto ".$audicao->horafim.")</small>";
            }
            $conteudo.= "</h3><br/>\n";
            if(!empty($audicao->local)) {
                $conteudo.= "\t\t<h2>".$audicao->local."</h2><br/><br/>\n";
            }
            $conteudo.="\t\t<br/>\n";

            $conteudo.="\t\t<h2>Programa</h2>\n";
            $conteudo.="\t\t</div>\n";

            $conteudo.="\t\t<div id='atuacao' style='margin-left: 2em;'>\n";
            $conteudo.="\t\t\t<ul>\n";

            // Listar atuacoes
            foreach ($audicao->idsAtuacoes as $idAt) {
                $at = Atuacao::getAtuacao($idAt);
                $conteudo.="\t\t\t\t<li>".$at->gerarTextoAtuacao()."</li>\n";                  
            }

            if($novasAtuacoes!=null && $nfiles>1) {
                foreach($novasAtuacoes as $at) {
                    $conteudo.="\t\t\t\t<li>".$at->gerarTextoAtuacao()."</li>\n";
                }
            }

            $conteudo.="\t\t\t</ul>\n";
            $conteudo.="\t\t</div>\n\t</div>\n</div>\n";

            $date = new DateTime();
            $timestamp = $date->getTimestamp();

            $partes = explode(".", $fAudicao);
            $outputfilename = $partes[0]."_".$timestamp.".html";
            $flyer = fopen("ficheiros/".$outputfilename, "w+");

            // template_audicoes/inicio.txt
            $inifile = fopen("template_audicoes/inicio.txt", "r") or die("Incapaz de abrir o ficheiro template_audicoes/inicio.txt!");
            $inicio_flyer = fread($inifile,filesize("template_audicoes/inicio.txt"));

            $fimfile = fopen("template_audicoes/fim.txt", "r") or die("Incapaz de abrir o ficheiro template_audicoes/fim.txt!");
            $fim_flyer = fread($fimfile,filesize("template_audicoes/fim.txt"));

            fwrite($flyer, $inicio_flyer);
            fwrite($flyer, $conteudo);
            fwrite($flyer, $fim_flyer);

            fclose($flyer);

            return $outputfilename;
        }

        /*
        * Metodo que gera pagina do audicao.
        */
        public static function criarPaginaDaAudicao($idAudicao) {
            $a = Audicao::getAudicao($idAudicao);

            echo "\t\t<a href='list_audicoes.html'><i class='fa fa-arrow-left'></i>  Voltar ao calendário</a>\n";
            echo "\t\t<div class='box box-solid' style='margin-top: 1em;'>\n";
            echo "\t\t\t<div class='box-header with-border'>\n";

            echo "\t\t\t\t<div style='width:55%; float:left; margin-top: 3em; margin-left: 3em;'>\n";
            echo "\t\t\t\t\t<h2 id='titulo' style='font: bold;'>".$a->titulo."</h2>\n";
            echo "\t\t\t\t\t<h4><b>".$a->idAudicao."</b></h4>\n";
            echo "\t\t\t\t\t<a href='edit_audicao.html?idAudicao=".$a->idAudicao."'><i class='fa fa-pencil-square-o'></i> Editar informação</a><br/>\n";
            // DELETE BUTTON HERE
            echo "\t\t<a class='btn btn-danger' onclick='deleteAudicao()' style='margin-top: 1em; margin-bottom: 2em;'>\n";
            echo "\t\t\t<i class='fa fa-trash-o fa-lg'></i> Apagar Audição\n";
            echo "\t\t</a>\n";
            echo "\t\t\t\t</div>\n";


             // Meta dados da audicao ----------------------------------------------------------
            echo "\t\t\t\t<div style='float:right;width:40%;'>\n";
            echo "\t\t\t\t\t<div class='thumbnail'>\n";
            echo "\t\t\t\t\t\t<div id='floater-div' style='float: left; height: 50%; width: 100%; margin-bottom: -50px;'></div>\n";
            echo "\t\t\t\t\t\t\t<center><i class='fa fa-4x fa-university'></i></center>\n";


            if($a->subtitulo!=null && $a->subtitulo!="") {
                echo "\t\t\t\t\t\t\t<center><h4><b>".$a->subtitulo."</b></h4></center>\n";
            }
            if($a->tema!=null && $a->tema!="") {
                echo "\t\t\t\t\t\t\t<p style='margin-left: 2em;'><b>Tema</b>: ".$a->tema."</p>\n";
            }

            if($a->data!=null && $a->data!="") {
                echo "\t\t\t\t\t\t\t<p style='margin-left: 2em;'><b>Data</b>: ".$a->data."</p>\n";
            }
            if($a->horainicio!=null && $a->horainicio!="") {
                echo "\t\t\t\t\t\t\t<p style='margin-left: 2em;'><i class='fa fa-clock-o'></i><b>Horário</b>: ".$a->horainicio;
            }

            if($a->horafim!=null && $a->horafim!="") {
                echo " - ".$a->horafim;
            }
            echo "</p>\n";

            if($a->horafim==null || $a->horafim=="") {
                if($a->duracao!=null && $a->duracao!=0) {
                    echo "\t\t\t\t\t\t\t<p style='margin-left: 2em;'><i class='fa fa-clock-o'></i><b>Duração prevista</b>: ".$a->duracao."</p>\n";
                }
            }

            if($a->local!=null && $a->local!="") {
                echo "\t\t\t\t\t\t\t<p style='margin-left: 2em;'><i class='fa fa-globe'></i><b>Local</b>: ".$a->local."</p>\n";
            }
            echo "\t\t\t\t\t\t</div>\n";
            echo "\t\t\t\t\t</div>\n";
            echo "\t\t\t\t</div>\n";
            echo "\t\t\t</div>\n";
            // ---------------------------------------------------------------------------------
            // ---------------------------------------------------------------------------------

            // Listar as atuacoes desta audicao
            echo "\t\t\t<div class='box box-solid'>\n";
            echo "\t\t\t\t<div class='box box-solid'>\n";

            echo "\t\t\t\t\t<div class='box-header'>\n";
            echo "\t\t\t\t\t\t<div style='width:55%; float:left;'>\n";
            echo "\t\t\t\t\t\t\t<a href='add_atuacao_existente.html?idAudicao=".$a->idAudicao."' style='font-size: 16px;'><i class='fa fa-recycle' style='font-size: 20px;'></i>   Adicionar uma atuação existente</a><br/>\n";
            echo "\t\t\t\t\t\t\t<a id='add_nova_atuacao' href='#' style='font-size: 16px;' onclick='addNovaAtuacao()'><i class='fa fa-plus' style='font-size: 20px;'></i>   Criar nova atuação</a><br/>\n";
            echo "\t\t\t\t\t</div>\n";

            echo "\t\t\t\t\t<div style='float:right; margin-right: 8em; margin-bottom: 6em;'>\n";
            echo "\t\t\t\t\t\t\t<a href='#' style='font-size: 16px;' onclick='validaAudicao()'><i class='fa fa-check-circle' style='font-size: 20px;'></i>   Validar esta audição</a><br/><br/>\n";

            // Spinner
            echo "\t\t\t\t\t\t\t<div id='spinner' style='display: none;'>
                  <h2>A validar audição ...</h2>
                  <i class='fa fa-circle-o-notch fa-spin' style='font-size:44px'></i>
                </div>";

            echo "\t\t\t\t\t\t<a id='gera_pdf' href='#' style='font-size: 16px; display: none;' onclick='gerarPDFAudicao()'><i class='fa fa-file-pdf-o' style='font-size: 20px;'></i>   Gerar PDF com programa</a><br/>\n";
            echo "\t\t\t\t\t\t<a id='gera_web_site' href='#' style='font-size: 16px; display: none;' onclick='gerarWebSite()'><i class='fa fa-internet-explorer' style='font-size: 20px;'></i>   Gerar Web Site com programa</a><br/>\n";
            echo "\t\t\t\t\t</div>\n";
            echo "\t\t\t\t</div>\n";
            echo "\t\t\t\t<div id='share_button' class='fb-share-button' style='margin-top: 1em; display: none;' data-layout='button_count'></div>\n";

            echo "\t\t\t\t<center><div id='resultado_validacao'></div></center>\n";

            echo "\t\t\t\t\t\t<center><h3><b>Programa</b></h3></center>\n";
            echo "\t\t\t\t\t</div>\n";
            echo "\t\t\t\t\t<div height='40%'>\n";

            Atuacao::criarTabelaDeAtuacoesDaAudicao($a->pk);

            echo "\t\t\t\t\t</div>\n";
            echo "\t\t\t\t</div>\n";
            echo "\t\t\t</div>\n";
            echo "\t\t</div>\n";
        }

        public static function gerarInputGramatica($idAudicao) {
            $a = Audicao::getAudicao($idAudicao);

            $filename = $idAudicao.".txt";
            $f = fopen($filename, 'w');
            $conteudo = "";

            $conteudo.= "BD \"localhost/gamudb\" \"root\" \"root\"\n";

            $conteudo.= "AUDICAO\n";

            $conteudo.= "\nCODIGO AUDVALIDA, TITULO \"".$a->titulo."\", SUBTITULO \"".$a->subtitulo."\", TEMA \"".$a->tema."\",
            DATA ".$a->data.", HORAINICIO ".$a->horainicio.", DURACAO ".$a->duracao.", LOCAL \"".$a->local."\"\n";

            $conteudo.="ATUACOES\n";

            foreach (Audicao::$db->query("select Atuacao.idAtuacao from Atuacao
                                          inner join AtuacaoAudicao on AtuacaoAudicao.idAtuacao=Atuacao.id
                                          where AtuacaoAudicao.idAudicao=".$a->pk.";") as $row) {
                $conteudo.="\t\tATUACAO ".$row[0].".\n";
            }

            fwrite($f, $conteudo);

            return $filename;
        }

        /*----------------------------------------------------------------------*/
        /*----------------------------------------------------------------------*/


        // Métodos de instância


        /*
        * Inserir uma instancia de Audicao na base de dados.
        */
        public function insert() {
            $query = "insert into Audicao(idAudicao, titulo, subtitulo, tema, data, horainicio, horafim, duracao, local) values(?, ?, ?, ?, ?, ?, ?, ?, ?);";
            $statement = Audicao::$db->prepare($query);
            $statement->bindParam(1, $this->idAudicao);
            $statement->bindParam(2, $this->titulo);
            $statement->bindParam(3, $this->subtitulo);
            $statement->bindParam(4, $this->tema);
            $statement->bindParam(5, $this->data);
            $statement->bindParam(6, $this->horainicio);
            $statement->bindParam(7, $this->horafim);
            $statement->bindParam(8, $this->duracao);
            $statement->bindParam(9, $this->local);

            $statement->execute();
        }

        /*
        * Metodo que permite atualizar os dados de um Audicao consoante os campos alterados.
        */
        public function update($atributos) {
            $query = "update Audicao set ";

            // Inserir na query os atributos modificados
            // !Atencao aos tipos de dados no caso do Audicao sao todos strings senao tem de se fazer
            // cast para verificar se atributo leva ou nao aspas
            foreach ($atributos as $atr) {
                if ($atr=="duracao") {
                    $query.=$atr."=".$this->$atr.",";
                } else {
                    $query.=$atr."='".$this->$atr."',";
                }
            }
            $query = rtrim($query, ","); // Remover ultima virgula que fica na string $query

            $query.=" where idAudicao='".$this->idAudicao."';";

            $statement = Audicao::$db->prepare($query);
            $statement->execute();
        }

        /*
        * Este metodo transforma uma string com o formato hh:mm:ss
        * no correspondente valor em minutos
        */
        public function converterEmMinutos($str_tempo) {
            sscanf($str_tempo, "%d:%d:%d", $horas, $minutos, $segundos);
            $tempoEmMinutos = isset($segundos) ? $horas * 3600 + $minutos * 60 + $segundos : $horas * 60 + $minutos;
            return ceil($tempoEmMinutos);
        }
    }
    // Instanciar variavel de acesso a base de dados apenas uma vez, guardar interface de acesso
    // em variavel de classe
    Audicao::$db = AbstractPDO::getInstance();

?>
