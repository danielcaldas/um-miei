<?php

    require_once 'AbstractPDO.php';
    require_once 'Aluno.php';
    require_once 'Professor.php';
    require_once 'Obra.php';
    require_once 'Audicao.php';

    class Atuacao {
        const ID_PREFIX = 'AT';
        const DATASET = '../datasets/atuacoes.xml';

        public static $db;

        public $idAtuacao;
        public $duracao;

        // Arrays
        public $idsAlunos;
        public $idsProfessores;
        public $idsAcompanhantes;
        public $idsObras;

        public function __construct($id, $duracao) {
            if($id=="") {
                $n = Atuacao::gerarIdentificador();
                $this->idAtuacao = "";
                $this->idAtuacao .= Atuacao::ID_PREFIX.$n;
            } else {
                $this->idAtuacao = $id;
            }
            $this->duracao = $duracao;
        }


        // Métodos de classe

        /*
        * Este metodo gera um identificador unico para uma instancia.
        */
        public static function gerarIdentificador() {
            $statement = Atuacao::$db->prepare("select max(id) from Atuacao;");
            $statement->execute();
            $result = $statement->fetch();
            return $result[0]+1;
        }
        

        /*
        * Este metodo permite obter uma instancia de um Atuação atraves do seu identificador.
        */
        public static function getAtuacao($idAtuacao) {
            $statement = Atuacao::$db->prepare("select * from Atuacao where idAtuacao='".$idAtuacao."';");
            $statement->execute();
            $row = $statement->fetch(); // Converter $row num array standard

            $at = new Atuacao($row[1], $row[2]);
            $at->initMyArrays();
            $atuacaoPK = $row[0];

            // AtuacaoAluno
            foreach(Atuacao::$db->query("select * from AtuacaoAluno where idAtuacao=".$atuacaoPK.";") as $tuplo) {
                $idA = Aluno::getIdentificadorPelaChavePrimaria($tuplo[1]);
                if($tuplo[2]==1) {
                    $at->idsAcompanhantes[] = $idA;
                } else {
                    $at->idsAlunos[] = $idA;
                }
            }

            // AtuacaoProfessor
            foreach(Atuacao::$db->query("select * from AtuacaoProfessor where idAtuacao=".$atuacaoPK.";") as $tuplo) {
                $idP = Professor::getIdentificadorPelaChavePrimaria($tuplo[1]);
                if($tuplo[2]==1) {
                    $at->idsAcompanhantes[] = $idP;
                } else {
                    $at->idsProfessores[] = $idP;
                }
            }

            // AtuacaoObra
            foreach(Atuacao::$db->query("select * from AtuacaoObra where idAtuacao=".$atuacaoPK.";") as $tuplo) {
                $idO = Obra::getIdentificadorPelaChavePrimaria($tuplo[1]);
                $at->idsObras[] = $idO;
            }

            return $at;
        }

        /*
        * Este metodo permite obter uma instancia de um Atuação atraves da sua chave primaria
        */
        public static function getAtuacaoPelaChavePrimaria($idAtuacao) {
            $statement = Atuacao::$db->prepare("select * from Atuacao where id=".$idAtuacao.";");
            $statement->execute();
            $row = $statement->fetch(); // Converter $row num array standard

            $at = new Atuacao($row[1], $row[2]);

            return $at;
        }

        /*
        * Este metodo permite obter identificador do grupo através da sua chave primaria.
        */
        public static function getIdentificadorDaAtuacaoPelaChavePrimaria($id) {
            $statement = Atuacao::$db->prepare("select idAtuacao from Atuacao where idAtuacao=".$id.";");
            $statement->execute();
            $row = $statement->fetch(); // Converter $row num array standard

            return $row[0];
        }

        /*
        * Este metodo permite obter a chave primaria do grupo atraves do seu identificador.
        */
        public static function getChavePrimariaPeloIdentificador($idAtuacao) {
            $statement = Atuacao::$db->prepare("select id from Atuacao where idAtuacao='".$idAtuacao."';");
            $statement->execute();
            $row = $statement->fetch(); // Converter $row num array standard

            return $row[0];
        }

        /*
        * Obter valor do total de grupos na base de dados.
        */
        public static function getTotalAtuacoes() {
            // Usar prepared statements para evitar SQL Injection
            $statement = Atuacao::$db->prepare("select count(*) from Atuacao;");
            $statement->execute();
            $result = $statement->fetch(); // Converter $row num array standard
            return $result[0];
        }

        /*
        * Apagar um Grupo pelo seu identificador.
        */
        public static function delete($idAtuacao) {
            $statement = Atuacao::$db->prepare("delete from Atuacao where idAtuacao='".$idAtuacao."';");
            $statement->execute();
        }

        /*
        * Este metodo transforma um inteiro que representa a duracao de uma obra
        * numa string do tipo mm:ss mais legivel do que o numero de segundos.
        */
        public static function getStringDuracao($duracao) {
            // PHP is awesome!
            if($duracao > 0) {
                return gmdate('i:s', $duracao);
            } else return "(duração indefinida)";
        }

        /*
        * Calculo da duracao de uma dada atuacao ou pela chave primaria ou pelo identificador.
        * Retorna o valor em segundos.
        * $atuacaoPK - chave primaria [PASSAR VALOR -1 caso se queira usar identificador]
        * $idAtuacao - identificador
        */
        public static function calculaDuracaoDaAtuacao($atuacaoPK, $idAtuacao) {
            $duracaoDaAtuacao = 0;
            if($atuacaoPK!=-1) {
                // Usamos $atuacaoPK
                foreach(Atuacao::$db->query("select Obra.duracao from Obra inner join
                                         AtuacaoObra on AtuacaoObra.idObra=Obra.id where AtuacaoObra.idAtuacao=".$atuacaoPK.";") as $row) {

                    $duracaoDaAtuacao+=$row[0];
                }
            } else {
                // Usamos o $idAtuacao
                foreach(Atuacao::$db->query("select Obra.duracao from Atuacao inner join AtuacaoObra
                                         on Atuacao.id=AtuacaoObra.idAtuacao
                                         inner join Obra on Obra.id=AtuacaoObra.idObra where Atuacao.idAtuacao=".$idAtuacao.";") as $row) {
                    $duracaoDaAtuacao+=$row[0];
                }
            }
            return $duracaoDaAtuacao;
        }


        /*----------------------------------------------------------------------------------
            Metodos de associcao de FKs para tabelas com relacao N<->M com Atuacao
        ------------------------------------------------------------------------------------*/

        // --------------------------------- ALUNO
        /*
        * Metodo que permite associar um dado aluno a um determinado atuacao, ambos
        * passados como parametro.
        */
        public static function associarAlunoAtuacao($idAtuacao, $idAluno, $acompanhante) {
            $atuacaoPK = Atuacao::getChavePrimariaPeloIdentificador($idAtuacao);
            $alunoPK = Aluno::getChavePrimariaDoAlunoPeloIdentificador($idAluno);
            
            $query = "insert into AtuacaoAluno(idAtuacao, idAluno, acompanhante) values(?, ?, ?);";
            $statement = Atuacao::$db->prepare($query);
            $statement->bindParam(1, $atuacaoPK);
            $statement->bindParam(2, $alunoPK);
            $statement->bindParam(3, $acompanhante);
            
            $statement->execute();
        }

        /*
        * Metodo que permite dissociar um dado aluno de um determinado atuacao, ambos
        * passados como parametro.
        */
        public static function dissociarAlunoDaAtuacao($idAtuacao, $idAluno)  {
            $atuacaoPK = Atuacao::getChavePrimariaPeloIdentificador($idAtuacao);
            $alunoPK = Aluno::getChavePrimariaDoAlunoPeloIdentificador($idAluno);

            $query = "delete from AtuacaoAluno where idAtuacao=".$atuacaoPK." and idAluno=".$alunoPK.";";
            $statement = Atuacao::$db->prepare($query);
            $statement->execute();
        }


        // --------------------------------- PROFESSOR
        /*
        * Metodo que permite associar um dado professor a um determinado atuacao, ambos
        * passados como parametro.
        */
        public static function associarProfessorAtuacao($idAtuacao, $idProfessor, $acompanhante) {
            $atuacaoPK = Atuacao::getChavePrimariaPeloIdentificador($idAtuacao);
            $professorPK = Professor::getChavePrimariaDoProfessor($idProfessor);
            
            $query = "insert into AtuacaoProfessor(idAtuacao, idProfessor, acompanhante) values(?, ?, ?);";
            $statement = Atuacao::$db->prepare($query);
            $statement->bindParam(1, $atuacaoPK);
            $statement->bindParam(2, $professorPK);
            $statement->bindParam(3, $acompanhante);
            
            $statement->execute();
        }

        /*
        * Metodo que permite dissociar um dado professor de um determinado atuacao, ambos
        * passados como parametro.
        */
        public static function dissociarProfessorDaAtuacao($idAtuacao, $idProfessor) {
            $atuacaoPK = Atuacao::getChavePrimariaPeloIdentificador($idAtuacao);
            $professorPK = Professor::getChavePrimariaDoProfessor($idProfessor);

            $query = "delete from AtuacaoProfessor where idAtuacao=".$atuacaoPK." and idProfessor=".$professorPK.";";
            $statement = Atuacao::$db->prepare($query);
            $statement->execute();
        }


        // --------------------------------- OBRA
        /*
        * Metodo que permite associar um dado obra a um determinado atuacao, ambos
        * passados como parametro.
        */
        public static function associarObraAtuacao($idAtuacao, $idObra) {
            $atuacaoPK = Atuacao::getChavePrimariaPeloIdentificador($idAtuacao);
            $obraPK = Obra::getChavePrimariaDaObra($idObra);            

            $query = "insert into AtuacaoObra(idAtuacao, idObra) values(?, ?);";
            $statement = Atuacao::$db->prepare($query);
            $statement->bindParam(1, $atuacaoPK);
            $statement->bindParam(2, $obraPK);            
            
            $statement->execute();
        }

        /*
        * Metodo que permite dissociar um dado obra de um determinado atuacao, ambos
        * passados como parametro.
        */
        public static function dissociarObraDaAtuacao($idAtuacao, $idObra) {
            $atuacaoPK = Atuacao::getChavePrimariaPeloIdentificador($idAtuacao);
            $obraPK = Obra::getChavePrimariaDaObra($idObra);
            

            $query = "delete from AtuacaoObra where idAtuacao=".$atuacaoPK." and idObra=".$obraPK.";";
            $statement = Atuacao::$db->prepare($query);
            $statement->execute();
        }


        // --------------------------------- ATUACAO
        /*
        * Metodo que permite associar um dado audicao a um determinado atuacao, ambos
        * passados como parametro.
        */
        public static function associarAudicaoAtuacao($idAtuacao, $idAudicao) {
            $atuacaoPK = Atuacao::getChavePrimariaPeloIdentificador($idAtuacao);
            $audicaoPK = Audicao::getChavePrimariaPeloIdentificador($idAudicao);
            

            $query = "insert into AtuacaoAudicao(idAtuacao, idAudicao) values(?, ?);";
            $statement = Atuacao::$db->prepare($query);
            $statement->bindParam(1, $atuacaoPK);
            $statement->bindParam(2, $audicaoPK);
            
            
            $statement->execute();
        }

        /*
        * Metodo que permite dissociar um dado audição de um determinado atuacao, ambos
        * passados como parametro.
        */
        public static function dissociarAudicaoDaAtuacao($idAtuacao, $idAudicao) {
            $atuacaoPK = Atuacao::getChavePrimariaPeloIdentificador($idAtuacao);
            $audicaoPK = Audicao::getChavePrimariaDaAudicao($idAudicao);
            

            $query = "delete from AtuacaoAudicao where idAtuacao=".$atuacaoPK." and idAudicao=".$audicaoPK.";";
            $statement = Atuacao::$db->prepare($query);
            $statement->execute();
        }

        /*----------------------------------------------------------------------*/
        /*----------------------------------------------------------------------*/


        /*----------------------------------------------------------------------
            Metodos que importam/exportam XML
        ----------------------------------------------------------------------*/

        /*
        * Migrar dados de um data set em XML com diversas atuacoes para
        * uma base de dados relacional MySQL.
        */
        public static function migrateXMLToMySQL() {
            
            $atuacoes = simplexml_load_file(Atuacao::DATASET);
            $atc =  $atuacoes->xpath("//atuacao");           
          
            foreach ($atc as $at) {
                
                $duracao = 0;

                // Percorrer obras uma primeira vez para estimar duracao da atuacao
                $obras = $at->xpath("//atuacao[@id=\"".$at[@id]."\"]//obra");
                if($obras) {
                    foreach ($obras as $o) {
                        $duracao += Obra::getDuracaoDaObra($o);
                    }
                }

                $idAtuacao = (string)$at[@id];
                $novaatuacao = new Atuacao($idAtuacao, $duracao);
                $novaatuacao->insertParcial();

                $audicao = $at->xpath("//atuacao[@id=\"".$at[@id]."\"]//audicao");
                if($audicao) {
                    foreach ($audicao as $aud) {
                        Atuacao::associarAudicaoAtuacao($idAtuacao, $aud);
                    }
                }                

                $acompanhantes = $at->xpath("//atuacao[@id=\"".$at[@id]."\"]//acompanhante");
                if($acompanhantes) {
	                foreach ($acompanhantes as $acomp) {
	                	if (strpos($acomp,'P') !== false) {
	                		// Significa que tem um PX (X é um número), é um professor
	                		Atuacao::associarProfessorAtuacao($idAtuacao, $acomp, $acomp[@acompanhante]);
	                	} else {
	                		Atuacao::associarAlunoAtuacao($idAtuacao, $acomp, $acomp[@acompanhante]);
	                	}
	                }
	            }

                if($obras) {
                    foreach ($obras as $o) {
                        Atuacao::associarObraAtuacao($idAtuacao, $o);
                    }
                }
                
                $alunos = $at->xpath("//atuacao[@id=\"".$at[@id]."\"]//aluno");
                if($alunos) {
                    foreach ($alunos as $alu) {
                        Atuacao::associarAlunoAtuacao($idAtuacao, $alu, $alu[@acompanhante]);
                    }
                }

                $professores = $at->xpath("//atuacao[@id=\"".$at[@id]."\"]//professor");
                if($professores) {
                    foreach ($professores as $prof) {
                        Atuacao::associarProfessorAtuacao($idAtuacao, $prof, $prof[@acompanhante]);
                    }
                }

            }
        }

        /*
        * Metodo que permite extrair de XML uma ATUACAO e criar um objecto.
        */
        public static function extrairAtuacaoDeXML($ficheiro) {
            $at = simplexml_load_file($ficheiro);

            $duracao = 0;

            // Percorrer obras uma primeira vez para estimar duracao da atuacao
            $obras = $at->xpath("//obra");
            $idsObras = array();
            if($obras) {
                foreach ($obras as $o) {
                    $duracao += Obra::getDuracaoDaObra($o);
                    $idsObras[] = (string)$o;
                }
            }

            // Deixar que se gere um novo?
            $novaAtuacao = new Atuacao("", $duracao);
            // Ou dar o que vem do ficheiro de futuro é so descomentar linha
            // $novaAtuacao = new Atuacao($at[@id], $duracao);

            $novaAtuacao->initMyArrays();

            $novaAtuacao->idsObras = $idsObras;

            $acompanhantes = $at->xpath("//acompanhante");
            if($acompanhantes) {
                foreach ($acompanhantes as $acomp) {
                    $novaAtuacao->idsAcompanhantes[] = (string)$acomp;
                }
            }

            $alunos = $at->xpath("//aluno");
            if($alunos) {
                foreach ($alunos as $alu) {
                    $novaAtuacao->idsAlunos[] = (string)$alu;
                }
            }

            $professores = $at->xpath("//professor");
            if($professores) {
                foreach ($professores as $prof) {
                    $novaAtuacao->idsProfessores[] = (string)$prof;
                }
            }

            return $novaAtuacao;
        }

        /*----------------------------------------------------------------------*/
        /*----------------------------------------------------------------------*/


        /*----------------------------------------------------------------------
            Metodos que geram HTML
        ----------------------------------------------------------------------*/

        /*
        * E um metodo de instancia que gera o texto da Atuacao.
        */
        public function gerarTextoAtuacao() {
            $TEXTO = "<p>";

            $instrumentos=null;

            // Executantes
            if($this->idsAlunos!=null) {
                $nalunos = count($this->idsAlunos);
                $instrumentos = array();
                $i=1;
                foreach($this->idsAlunos as $idA) {
                    $a = Aluno::getAluno($idA);
                    $partes_do_nome = explode(" ", $a->nome);
                    if(count($partes_do_nome)<3) {
                        $TEXTO.=$a->nome;
                    } else {
                        $TEXTO.=$primeiro_e_ultimo = $partes_do_nome[0]." ".$partes_do_nome[count($partes_do_nome)-1];
                    }
                    if($i!=$nalunos) {
                        $TEXTO.=", ";
                        $i++;
                    }
                    if(!in_array($a->instrumento, $instrumentos)) $instrumentos[]=$a->instrumento;
                }

                $TEXTO.="</p>\n";
            }

            $TEXTO.="<p><b>Interpreta(m), </b>";

            // Obras
            if($this->idsObras!=null) {
                $nobras = count($this->idsObras);
                $i=1;
                foreach ($this->idsObras as $idO) {
                    $o = Obra::getObra($idO);
                    $nomeCompositor = Obra::getNomeCompositorDaObra($idO);
                    $TEXTO.=$o->nome." (<i>".$nomeCompositor."</i>)";
                    if($i!=$nobras) {
                        $TEXTO.=", ";
                        $i++;
                    }
                }

                $TEXTO.="</p>\n";
            }

            // Dirigentes
            if($this->idsProfessores!=null) {
                $nprofs = count($this->idsProfessores);
                if($nprofs>0) {
                    $TEXTO.="<p><b>A dirigir, </b>";
                    $i=1;
                    foreach ($this->idsProfessores as $idP) {
                        $p = Professor::getProfessor($idP);
                        $partes_do_nome = explode(" ", $p->nome);
                        if(count($partes_do_nome)<3) {
                            $TEXTO.="Professor ".$p->nome;
                        } else {
                            $TEXTO.="Professor ".$primeiro_e_ultimo = $partes_do_nome[0]." ".$partes_do_nome[count($partes_do_nome)-1];
                        }
                        if($i!=$nprofs) {
                            $TEXTO.=", ";
                            $i++;
                        }
                    }
                    $TEXTO.="</p>\n";
                }
            }

            // Acompanhantes
            if($this->idsAcompanhantes!=null) {
                $nacomp = count($this->idsAcompanhantes);
                if($nacomp>0) {
                    $TEXTO.="<p><b>A acompanhar, </b>";
                    $i=1;
                    foreach ($this->idsAcompanhantes as $idAc) {
                        $acm = null;
                        if(strpos($idAc, "P") !== false) {
                            $acm = Professor::getProfessor($idAc);
                            $TEXTO.="Professor ";
                        } else {
                            $acm = Aluno::getAluno($idAc);
                        }

                        $partes_do_nome = explode(" ", $acm->nome);
                        if(count($partes_do_nome)<3) {
                            $TEXTO.=$acm->nome;
                        } else {
                            $TEXTO.=$primeiro_e_ultimo = $partes_do_nome[0]." ".$partes_do_nome[count($partes_do_nome)-1];
                        }
                        if($i!=$nacomp) {
                            $TEXTO.=", ";
                            $i++;
                        }
                    }
                    $TEXTO.="</p>\n";
                }
            }

            // Instrumentos
            if($instrumentos!=null && (count($instrumentos) > 0) ) {
                $TEXTO.="<p>(";
                $ninsts = count($instrumentos);
                $i=1;
                foreach ($instrumentos as $inst) {
                    $TEXTO.=$inst;
                    if($i!=$ninsts) {
                        $TEXTO.=", ";
                        $i++;
                    }
                }
                $TEXTO.=")</p><br/><hr/>\n";
            }

            return $TEXTO;
        }

        /*
        * Metodo que gera todo a tabela de atuacoes de uma dada audicao.
        */
        public static function criarTabelaDeAtuacoesDaAudicao($audicaoPK) {
            echo "\t<div class='box-body no-padding'>\n";
            echo "\t\t<table id='table-1' class='table table-striped' width='100%' style='border:2px;'>\n";

            echo "\t\t\t<thead><tr>\n";
            echo "\t\t\t\t<th width=10%>ID</th>\n";
            echo "\t\t\t\t<th width=33%>Alunos</th>\n";
            echo "\t\t\t\t<th width=25%>Obras</th>\n";
            echo "\t\t\t\t<th width=15%>Professores <small>(Dirigentes)</small></th>\n";
            echo "\t\t\t\t<th width=15%>Acompanhantes</th>\n";
            echo "\t\t\t\t<th width=2%><i class='fa fa-clock-o'></i>Duração</th>\n";
            echo "\t\t\t</tr></thead><tbody>\n";

            $idAudicao = Audicao::getIdentificadorDaAudicaoPelaChavePrimaria($audicaoPK);
              
            foreach(Atuacao::$db->query("select Atuacao.id, Atuacao.idAtuacao, Atuacao.duracao from AtuacaoAudicao inner join
                                         Atuacao on AtuacaoAudicao.idAtuacao=Atuacao.id where AtuacaoAudicao.idAudicao=".$audicaoPK.";") as $row) {
                $atuacaoPK = $row[0];
                $idAtuacao = $row[1];
                $duracaoAtuacao = $row[2];
    
                echo "\t\t\t<tr>\n";

                echo "\t\t\t\t<td style='border: 1px;'>".$idAtuacao."  <a id='".$idAtuacao."' class='a-delete-atuacao' style='padding-left: 1em;'
                href='#'><i class='fa fa-trash-o'></i></a>\n";

                // Alunos da atuacao
                echo "\t\t\t\t<td style='border: 1px;'><ul>\n";
                foreach(Atuacao::$db->query("select Aluno.idAluno, Aluno.nome, Aluno.instrumento from AtuacaoAluno
                                             inner join Aluno on Aluno.id=AtuacaoAluno.idAluno
                                             where AtuacaoAluno.acompanhante=0 and AtuacaoAluno.idAtuacao=".$atuacaoPK." order by Aluno.nome;") as $aluno) {
                    echo "\t\t\t\t<li id='addaluno_".$aluno[0]."'><a href='../alunos/consult_aluno.html?idAluno=".$aluno[0]."' target='_blank'>".$aluno[1]."</a><small> (".$aluno[2].")</small><a id='".$idAtuacao."_".$aluno[0]."_0' href='#' class='a-dissociar-aluno'><i class='fa fa-times'></i></a></li>\n";
                }
                echo "\t\t\t\t</ul>\n";
                echo "\t\t\t\t<a id='addaluno_".$idAtuacao."' href='add_aluno_atuacao.html?id=".$idAudicao."_".$idAtuacao."'><i class='fa fa-plus' style='font-size: 16px;'></i>   Adicionar Aluno</a>";
                echo "\t\t\t\t</td>\n";

                // Obras da atuacao
                echo "\t\t\t\t<td style='border: 1px;'><ol>\n";
                foreach(Atuacao::$db->query("select Obra.idObra, Obra.nome, Compositor.idComp, Compositor.nome from
                                             AtuacaoObra inner join Obra on AtuacaoObra.idObra=Obra.id
                                             inner join Compositor on Obra.idCompositor=Compositor.id
                                             where AtuacaoObra.idAtuacao=".$atuacaoPK.";") as $obra) {
                    echo "\t\t\t\t<li id='addobra_".$obra[0]."'><a href='../obras/consult_obra.html?idObra=".$obra[0]."' target='_blank'><b>".$obra[1].
                    "</b></a><small><a href='../compositores/consult_compositor.html?idComp=".$obra[2]."' target='_blank'> (".$obra[3].")</a></small>  <a id='".$idAtuacao."_".$obra[0]."' href='#' class='a-dissociar-obra'><i class='fa fa-times'></i></a></li>\n";
                }
                echo "\t\t\t\t</ol>\n";
                echo "\t\t\t\t<a id='addobra_".$idAtuacao."' href='add_obra_atuacao.html?id=".$idAudicao."_".$idAtuacao."'><i class='fa fa-plus' style='font-size: 16px;'></i>   Adicionar Obra</a>";
                echo "\t\t\t\t</td>\n";

                // Professores da atuacao
                echo "\t\t\t\t<td style='border: 1px;'><ul>\n";
                foreach(Atuacao::$db->query("select Professor.idProfessor, Professor.nome from AtuacaoProfessor
                                             inner join Professor on Professor.id=AtuacaoProfessor.idProfessor
                                             where AtuacaoProfessor.acompanhante=0 and AtuacaoProfessor.idAtuacao=".$atuacaoPK."
                                             order by Professor.nome;") as $professor) {
                    echo "\t\t\t\t<li id='addprof_".$professor[0]."'><a href='../professores/consult_professor.html?idProfessor=".$professor[0]."' target='_blank'>".$professor[1]."</a>  <a id='".$idAtuacao."_".$professor[0]."_0' href='#' class='a-dissociar-professor'><i class='fa fa-times'></i></a></li>\n";
                }
                echo "\t\t\t\t</ul>\n";
                echo "\t\t\t\t<a id='addprof_".$idAtuacao."' href='add_professor_atuacao.html?id=".$idAudicao."_".$idAtuacao."'><i class='fa fa-plus' style='font-size: 16px;'></i>   Adicionar Professor</a>";
                echo "\t\t\t\t</td>\n";

                // Acompanhantes da atuacao
                echo "\t\t\t\t<td style='border: 1px;'><ul>\n";
                foreach(Atuacao::$db->query("select Aluno.idAluno, Aluno.nome, Aluno.instrumento from AtuacaoAluno
                                             inner join Aluno on Aluno.id=AtuacaoAluno.idAluno
                                             where AtuacaoAluno.acompanhante=1 and AtuacaoAluno.idAtuacao=".$atuacaoPK." order by Aluno.nome;") as $aluno) {
                    echo "\t\t\t\t<li id='addalunoac_".$aluno[0]."'>
                    <a href='../alunos/consult_aluno.html?idAluno=".$aluno[0]."' target='_blank'>".$aluno[1]."</a><small> (".$aluno[2].")</small>  <a id='".$idAtuacao."_".$aluno[0]."_1' href='#' class='a-dissociar-aluno'><i class='fa fa-times'></i></a></li>\n";
                }
                foreach(Atuacao::$db->query("select Professor.idProfessor, Professor.nome from AtuacaoProfessor
                                             inner join Professor on Professor.id=AtuacaoProfessor.idProfessor
                                             where AtuacaoProfessor.acompanhante=1 and AtuacaoProfessor.idAtuacao=".$atuacaoPK."
                                             order by Professor.nome;") as $professor) {
                    echo "\t\t\t\t<li id='addprofac_".$professor[0]."'><a href='../professores/consult_professor.html?idProfessor=".$professor[0]."' target='_blank'><i>Prof</i>. ".$professor[1]."</a>  <a id='".$idAtuacao."_".$professor[0]."_1' href='#' class='a-dissociar-professor'><i class='fa fa-times'></i></a></li>\n";
                }
                echo "\t\t\t\t</ul>\n";
                echo "\t\t\t\t<a id='addaluno_".$idAtuacao."' href='add_ac_aluno_atuacao.html?id=".$idAudicao."_".$idAtuacao."'><i class='fa fa-plus' style='font-size: 16px;'></i>   Adicionar Aluno</a><br/>";
                echo "\t\t\t\t<a id='addprof_".$idAtuacao."' href='add_ac_professor_atuacao.html?id=".$idAudicao."_".$idAtuacao."'><i class='fa fa-plus' style='font-size: 16px;'></i>   Adicionar Professor</a><br/>";
                echo "\t\t\t\t</td>\n";


                // Calcular a duracao da atuacao caso esta ainda nao o tenha sido
                if($duracaoAtuacao == 0) {
                    $duracaoAtuacao = Atuacao::calculaDuracaoDaAtuacao($atuacaoPK, $idAtuacao);

                    // Atualizar o valor dessa duracao da atuacao $atuacaoPK
                    $query = "update Atuacao set duracao=".$duracaoAtuacao." where id=".$atuacaoPK.";";
                    $statement = Atuacao::$db->prepare($query);
                    $statement->execute();
                }

                $durString = Atuacao::getStringDuracao($duracaoAtuacao);
                echo "\t\t\t\t<td style='border: 1px;'>".$durString."</td>\n";

            }
            
            echo "\t\t\t</tbody>\n";
            echo "\t\t</table>\n";
            echo "\t\t<table id='header-fixed'></table>\n";
            echo "\t</div>\n";
        }


        // ALUNOS ----------------
        /*
        * Metodo que permite listar os alunos com a coluna de accoes para se possibilitar associacao a uma determinada atuacao.
        * A query da base de dados deve retorna todos os alunos excepto aqueles que estao na atuacao.
        */
        public static function listarAlunosParaSeAssociarAtuacao($idAtuacao) {
            $pagina=1;
            $save_i=0;
            $i=0;
            $novaTabela = ""; // nova tabela da pagina $i+1
            $tabelaTerminada = true;

            $atuacaoPK = Atuacao::getChavePrimariaPeloIdentificador($idAtuacao);

            // Primeiro calcular número total de alunos que nao estao na atuacao
            $statement = Atuacao::$db->query("select count(*)
                                            from
                                            (select distinct Aluno.id, Aluno.idAluno, nome, dataNasc, anoCurso, instrumento, idCurso
                                            from Aluno
                                            left join AtuacaoAluno on Aluno.id=AtuacaoAluno.idAluno
                                            where AtuacaoAluno.idAluno not in (select idAluno from AtuacaoAluno where idAtuacao=".$atuacaoPK.")
                                            union
                                            select distinct Aluno.id, Aluno.idAluno, nome, dataNasc, anoCurso, instrumento, idCurso
                                            from Aluno
                                            left join AtuacaoAluno
                                            on Aluno.id=AtuacaoAluno.idAluno
                                            where AtuacaoAluno.idAluno is NULL) as TotA;");
            $statement->execute();
            $nalunos = $statement->fetch();

            $tpaginada = new TabelaPaginada(12, $nalunos[0]);

            foreach(Atuacao::$db->query("select distinct Aluno.id, Aluno.idAluno, nome, dataNasc, anoCurso, instrumento, idCurso
                                        from Aluno
                                        left join AtuacaoAluno on Aluno.id=AtuacaoAluno.idAluno
                                        where AtuacaoAluno.idAluno not in (select idAluno from AtuacaoAluno where idAtuacao=".$atuacaoPK.")
                                        union
                                        select distinct Aluno.id, Aluno.idAluno, nome, dataNasc, anoCurso, instrumento, idCurso
                                        from Aluno
                                        left join AtuacaoAluno
                                        on Aluno.id=AtuacaoAluno.idAluno
                                        where AtuacaoAluno.idAluno is NULL") as $row) {
                $al = new Aluno($row[1], $row[2], $row[3], $row[4], $row[5], $row[6]);
                
                if( $tabelaTerminada==true ) {
                    // Se e divisivel pelo n de linhas da tabela, entao cria-se nova tabela
                    $tabelaTerminada=false;
                    $novaTabela = ""; // reniciar string da tabela

                    $novaTabela.= "\t<h2 style='margin: 1em;'>Associar Alunos</h2>\n";
                    $novaTabela.= "\t<div class='box-body no-padding' style='margin: 2em;'>\n";
                    $novaTabela.= "\t\t <table class='table table-striped'>\n";
                    $novaTabela.= "\t\t\t<tbody><tr>\n";
                    $novaTabela.= "\t\t\t\t<th width=5%>ID</th>\n";
                    $novaTabela.= "\t\t\t\t<th width=30%>Nome</th>\n";
                    $novaTabela.= "\t\t\t\t<th width=20%>Instrumento</th>\n";
                    $novaTabela.= "\t\t\t\t<th width=20%>Curso</th>\n";
                    $novaTabela.= "\t\t\t\t<th width=15%>Ano</th>\n";
                    $novaTabela.= "\t\t\t\t<th width=10%>Acções</th>\n";
                    $novaTabela.= "\t\t\t</tr>\n";
                    // echo "ABRE TABELA: ".$i."\n";
                }

                // echo "LINHA DE UMA TABELA ".$i."\n";
                $novaTabela.= "\t\t\t<tr>\n";
                $novaTabela.= "\t\t\t\t<td>".$al->idAluno."</td>\n";
                $novaTabela.= "\t\t\t\t<td id='nome_aluno_".$al->idAluno."'>".$al->nome."</td>\n";
                $novaTabela.= "\t\t\t\t<td>".$al->instrumento."</td>\n";

                $designCurso = Curso::getDesignacaoDoCursoPelaChavePrimaria($al->idCurso);
                $novaTabela.= "\t\t\t\t<td>".$designCurso."</td>\n";

                $novaTabela.= "\t\t\t\t<td>".$al->anoCurso."&ordm;</td>\n";
                $novaTabela.= "\t\t\t\t<td>
                <a id='".$al->idAluno."' href='#' class='a-associar-aluno'><i class='fa fa-plus' style='margin-right: 2px;'></i> Adicionar</a>\n";
                
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
                    $file = fopen("./.paginas_alunos/pagina_".$pagina.".html", "wr") or die("Unable to open file!");
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


        // PROFS ------------
        /*
        * Metodo que permite listar os alunos com a coluna de accoes para se possibilitar associacao a uma determinada atuacao.
        * A query da base de dados deve retorna todos os alunos excepto aqueles que estao na atuacao.
        */
        /*
        * Metodo que permite listar os professores com a coluna de accoes para se possibilitar associacao a um determinado curso.
        * A query da base de dados deve retorna todos os professores excepto aqueles que lecionam o curso.
        */
        public static function listarProfessoresParaSeAssociarAtuacao($idAtuacao) {
            $pagina=1;
            $save_i=0;
            $i=0;
            $novaTabela = ""; // nova tabela da pagina $i+1
            $tabelaTerminada = true;


            $atuacaoPK = Atuacao::getChavePrimariaPeloIdentificador($idAtuacao);

            // Primeiro calcular número total de professores diferentes que nao lecionam no curso
            $statement = Atuacao::$db->query("select count(*)
                                            from
                                            (select distinct id, Professor.idProfessor, nome, dataNasc, habilitacoes
                                            from Professor
                                            left join AtuacaoProfessor on Professor.id=AtuacaoProfessor.idProfessor
                                            where AtuacaoProfessor.idProfessor not in (select idProfessor from AtuacaoProfessor where idAtuacao=".$atuacaoPK.")
                                            union
                                            select id, Professor.idProfessor, nome, dataNasc, habilitacoes
                                            from Professor
                                            left join AtuacaoProfessor
                                            on Professor.id=AtuacaoProfessor.idProfessor
                                            where AtuacaoProfessor.idProfessor is NULL) as TotP;");
            $statement->execute();
            $nprofs = $statement->fetch();

            $tpaginada = new TabelaPaginada(12, $nprofs[0]);

            foreach(Professor::$db->query("select distinct id, Professor.idProfessor, nome, dataNasc, habilitacoes
                                            from Professor
                                            left join AtuacaoProfessor on Professor.id=AtuacaoProfessor.idProfessor
                                            where AtuacaoProfessor.idProfessor not in (select idProfessor from AtuacaoProfessor where idAtuacao=".$atuacaoPK.")
                                            union
                                            select id, Professor.idProfessor, nome, dataNasc, habilitacoes
                                            from Professor
                                            left join AtuacaoProfessor
                                            on Professor.id=AtuacaoProfessor.idProfessor
                                            where AtuacaoProfessor.idProfessor is NULL;") as $row) {
                $p = new Professor($row[1], $row[2], $row[3], $row[4]);
                
                if( $tabelaTerminada==true ) {
                    // Se e divisivel pelo n de linhas da tabela, entao cria-se nova tabela
                    $tabelaTerminada=false;
                    $novaTabela = ""; // reniciar string da tabela

                    $novaTabela.= "\t<h2 style='margin: 1em;'>Associar professores</h2>\n";
                    $novaTabela.= "\t<div id='tabela_profs_wrapper' class='box-body no-padding' style='margin: 2em;'>\n";
                    $novaTabela.= "\t\t <table class='table table-striped' style='margin-top: 1em;'>\n";
                    $novaTabela.= "\t\t\t<tbody><tr>\n";
                    $novaTabela.= "\t\t\t\t<th>ID</th>\n";
                    $novaTabela.= "\t\t\t\t<th>Nome</th>\n";
                    $novaTabela.= "\t\t\t\t<th>Habilitações</th>\n";
                    $novaTabela.= "\t\t\t\t<th>Acções</th>\n";
                    $novaTabela.= "\t\t\t</tr>\n";
                    // echo "ABRE TABELA: ".$i."\n";
                }

                // echo "LINHA DE UMA TABELA ".$i."\n";
                $novaTabela.= "\t\t\t<tr>\n";
                $novaTabela.= "\t\t\t\t<td>".$p->idProfessor."</td>\n";
                $novaTabela.= "\t\t\t\t<td id='nome_prof_".$p->idProfessor."'>".$p->nome."</td>\n";
                $novaTabela.= "\t\t\t\t<td>".$p->habilitacoes."</td>\n";
                $novaTabela.= "\t\t\t\t<td>
                <a id='".$p->idProfessor."' href='#' class='a-associar-professor'><i class='fa fa-plus' style='margin-right: 2px;'></i> Adicionar Professor</a>\n";
                
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
                    $file = fopen("./.paginas_professores/pagina_".$pagina.".html", "wr") or die("Unable to open file!");
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

        // OBRAS--------------------
        /*
        * Metodo que gera toda a tabela de obras para se associar a grupo.
        */
        public static function listarObrasParaSeAssociarAtuacao($idAtuacao) {
            $atuacaoPK = Atuacao::getChavePrimariaPeloIdentificador($idAtuacao);
            
            $pagina=1;
            $save_i=0;
            $i=0;
            $novaTabela = ""; // nova tabela da pagina $i+1
            $tabelaTerminada = true;

            // Primeiro calcular numero total de obras
            $statement = Atuacao::$db->query("select count(*)
                                            from
                                            (select distinct id, Obra.idObra, nome, descricao, idCompositor, Obra.data, periodo, duracao
                                            from Obra
                                            left join AtuacaoObra on Obra.id=AtuacaoObra.idObra
                                            where AtuacaoObra.idObra not in (select idObra from AtuacaoObra where idAtuacao=21)
                                            union
                                            select id, Obra.idObra, nome, descricao, idCompositor, Obra.data, periodo, duracao
                                            from Obra
                                            left join AtuacaoObra
                                            on Obra.id=AtuacaoObra.idObra
                                            where AtuacaoObra.idObra is NULL) as TotO;");

            $statement->execute();
            $nobras = $statement->fetch();

            $tpaginada = new TabelaPaginada(12, $nobras[0]);

            foreach(Obra::$db->query("select distinct id, Obra.idObra, nome, descricao, idCompositor, Obra.data, periodo, duracao
                                        from Obra
                                        left join AtuacaoObra on Obra.id=AtuacaoObra.idObra
                                        where AtuacaoObra.idObra not in (select idObra from AtuacaoObra where idAtuacao=".$atuacaoPK.")
                                        union
                                        select id, Obra.idObra, nome, descricao, idCompositor, Obra.data, periodo, duracao
                                        from Obra
                                        left join AtuacaoObra
                                        on Obra.id=AtuacaoObra.idObra
                                        where AtuacaoObra.idObra is NULL") as $row) {
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
                <a id='".$o->idObra."' href='#' class='a-associar-obra'><i class='fa fa-plus' style='margin-right: 2px;'></i> Adicionar Obra</a>\n";
                
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
                    $file = fopen("./.paginas_obras/pagina_".$pagina.".html", "wr") or die("Unable to open file!");
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


         /*
        * Metodo que gera todo a tabela de atuacoes que nao pertencem a audicao para se adionar atuacao existente.
        * [A QUERY DEVE RETORNAR AS ATUACOES NAO UTILIZADAS]
        */
        public static function criarTabelaParaSeAdicionarAtuacaoExistente($idAudicao) {
            echo "<a href='consult_audicao.html?idAudicao=".$idAudicao."' ><i class='fa fa-arrow-left'></i> Voltar à página da Audição</a>";
            echo "\t<div class='box-body no-padding; margin-top: 2em;'>\n";
            echo "\t\t<table id='table-1' class='table table-striped' width='100%' style='border:2px;'>\n";

            echo "\t\t\t<thead><tr>\n";
            echo "\t\t\t\t<th width=10%>ID</th>\n";
            echo "\t\t\t\t<th width=3%>Alunos</th>\n";
            echo "\t\t\t\t<th width=25%>Obras</th>\n";
            echo "\t\t\t\t<th width=15%>Professores <small>(Dirigentes)</small></th>\n";
            echo "\t\t\t\t<th width=15%>Acompanhantes</th>\n";
            echo "\t\t\t\t<th width=2%><i class='fa fa-clock-o'></i>Duração</th>\n";
            echo "\t\t\t\t<th width=1%></i>Ações</th>\n";
            echo "\t\t\t</tr></thead><tbody>\n";

            $audicaoPK = Audicao::getChavePrimariaPeloIdentificador($idAudicao);
              
            foreach(Atuacao::$db->query("select distinct Atuacao.id, Atuacao.idAtuacao, Atuacao.duracao
                                        from Atuacao
                                        inner join AtuacaoAudicao on AtuacaoAudicao.idAtuacao=Atuacao.id
                                        where AtuacaoAudicao.idAtuacao not in (select idAtuacao from AtuacaoAudicao where idAtuacao=".$audicaoPK.");") as $row) {
                $atuacaoPK = $row[0];
                $idAtuacao = $row[1];
                $duracaoAtuacao = $row[2];
    
                echo "\t\t\t<tr>\n";

                echo "\t\t\t\t<td style='border: 1px;'>".$idAtuacao."  <a id='".$idAtuacao."' class='a-delete-atuacao' style='padding-left: 1em;'
                href='#'><i class='fa fa-trash-o'></i></a>\n";

                // Alunos da atuacao
                echo "\t\t\t\t<td style='border: 1px;'><ul>\n";
                foreach(Atuacao::$db->query("select Aluno.idAluno, Aluno.nome, Aluno.instrumento from AtuacaoAluno
                                             inner join Aluno on Aluno.id=AtuacaoAluno.idAluno
                                             where AtuacaoAluno.acompanhante=0 and AtuacaoAluno.idAtuacao=".$atuacaoPK." order by Aluno.nome;") as $aluno) {
                    echo "\t\t\t\t<li id='addaluno_".$aluno[0]."'><a href='../alunos/consult_aluno.html?idAluno=".$aluno[0]."' target='_blank'>".$aluno[1]."</a><small> (".$aluno[2].")</small></li>\n";
                }
                echo "\t\t\t\t</ul>\n";
                echo "\t\t\t\t</td>\n";

                // Obras da atuacao
                echo "\t\t\t\t<td style='border: 1px;'><ol>\n";
                foreach(Atuacao::$db->query("select Obra.idObra, Obra.nome, Compositor.idComp, Compositor.nome from
                                             AtuacaoObra inner join Obra on AtuacaoObra.idObra=Obra.id
                                             inner join Compositor on Obra.idCompositor=Compositor.id
                                             where AtuacaoObra.idAtuacao=".$atuacaoPK.";") as $obra) {
                    echo "\t\t\t\t<li id='addobra_".$obra[0]."'><a href='../obras/consult_obra.html?idObra=".$obra[0]."' target='_blank'><b>".$obra[1].
                    "</b></a><small><a href='../compositores/consult_compositor.html?idComp=".$obra[2]."' target='_blank'> (".$obra[3].")</a></small></li>\n";
                }
                echo "\t\t\t\t</ol>\n";
                echo "\t\t\t\t</td>\n";

                // Professores da atuacao
                echo "\t\t\t\t<td style='border: 1px;'><ul>\n";
                foreach(Atuacao::$db->query("select Professor.idProfessor, Professor.nome from AtuacaoProfessor
                                             inner join Professor on Professor.id=AtuacaoProfessor.idProfessor
                                             where AtuacaoProfessor.acompanhante=0 and AtuacaoProfessor.idAtuacao=".$atuacaoPK."
                                             order by Professor.nome;") as $professor) {
                    echo "\t\t\t\t<li id='addprof_".$professor[0]."'><a href='../professores/consult_professor.html?idProfessor=".$professor[0]."' target='_blank'>".$professor[1]."</a></li>\n";
                }
                echo "\t\t\t\t</ul>\n";
                echo "\t\t\t\t</td>\n";

                // Acompanhantes da atuacao
                echo "\t\t\t\t<td style='border: 1px;'><ul>\n";
                foreach(Atuacao::$db->query("select Aluno.idAluno, Aluno.nome, Aluno.instrumento from AtuacaoAluno
                                             inner join Aluno on Aluno.id=AtuacaoAluno.idAluno
                                             where AtuacaoAluno.acompanhante=1 and AtuacaoAluno.idAtuacao=".$atuacaoPK." order by Aluno.nome;") as $aluno) {
                    echo "\t\t\t\t<li id='addalunoac_".$aluno[0]."'>
                    <a href='../alunos/consult_aluno.html?idAluno=".$aluno[0]."' target='_blank'>".$aluno[1]."</a><small> (".$aluno[2].")</small></li>\n";
                }
                foreach(Atuacao::$db->query("select Professor.idProfessor, Professor.nome from AtuacaoProfessor
                                             inner join Professor on Professor.id=AtuacaoProfessor.idProfessor
                                             where AtuacaoProfessor.acompanhante=1 and AtuacaoProfessor.idAtuacao=".$atuacaoPK."
                                             order by Professor.nome;") as $professor) {
                    echo "\t\t\t\t<li id='addprofac_".$professor[0]."'><a href='../professores/consult_professor.html?idProfessor=".$professor[0]."' target='_blank'><i>Prof</i>. ".$professor[1]."</a> </li>\n";
                }
                echo "\t\t\t\t</ul>\n";
                echo "\t\t\t\t</td>\n";


                // Calcular a duracao da atuacao caso esta ainda nao o tenha sido
                if($duracaoAtuacao == 0) {
                    $duracaoAtuacao = Atuacao::calculaDuracaoDaAtuacao($atuacaoPK, $idAtuacao);

                    // Atualizar o valor dessa duracao da atuacao $atuacaoPK
                    $query = "update Atuacao set duracao=".$duracaoAtuacao." where id=".$atuacaoPK.";";
                    $statement = Atuacao::$db->prepare($query);
                    $statement->execute();
                }

                $durString = Atuacao::getStringDuracao($duracaoAtuacao);
                echo "\t\t\t\t<td style='border: 1px;'>".$durString."</td>\n";

                echo "\t\t\t\t<td style='border: 1px;'><a id='".$idAtuacao."' href='#' class='add-atuacao'><i class='fa fa-plus' style='font-size: 22px;'></i></a></td>\n";

            }
            
            echo "\t\t\t</tbody>\n";
            echo "\t\t</table>\n";
            echo "\t\t<table id='header-fixed'></table>\n";
            echo "\t</div>\n";
        }


        /*----------------------------------------------------------------------*/
        /*----------------------------------------------------------------------*/
    

        // Métodos de instância


        /*
        * Inserir uma instancia de uma Atuacao na base de dados.
        */
        public function insertParcial() {
            $query = "insert into Atuacao(idAtuacao, duracao) values(?, ?);";
            $statement = Atuacao::$db->prepare($query);
            $statement->bindParam(1, $this->idAtuacao);
            $statement->bindParam(2, $this->duracao);
           
            $statement->execute();
        }

        /*
        * Metodo que permite atualizar os dados de um Grupo consoante os campos alterados.
        */
        public function update($atributos) {
            $query = "update Atuacao set ";

            // Inserir na query os atributos modificados
            // !Atencao aos tipos de dados no caso do Grupo sao todos strings senao tem de se fazer
            // cast para verificar se atributo leva ou nao aspas
            foreach ($atributos as $atr) {
                if($atr=="duracao") {
                    $query.=$atr."=".$this->$atr.",";    
                } else {
                    $query.=$atr."='".$this->$atr."',";
                }
            }
            $query = rtrim($query, ","); // Remover ultima virgula que fica na string $query

            $query.=" where idAtuacao='".$this->idAtuacao."';";

            $statement = Atuacao::$db->prepare($query);
            $statement->execute();
        }

        /*
        * Metodo que permite iniciar arrays de uma instance de Atuacao.
        */
        public function initMyArrays() {
            $idsAlunos = Array();
            $idsProfessores = Array();
            $idsAcompanhantes = Array();
            $idsObras = Array();
        }

    }
    // Instanciar variavel de acesso a base de dados apenas uma vez, guardar interface de acesso
    // em variavel de classe
    Atuacao::$db = AbstractPDO::getInstance();

?>
