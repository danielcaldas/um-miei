<?php

    require_once 'AbstractPDO.php';
    require_once 'TabelaPaginada.php';
    require_once 'Curso.php';

    class Professor {
        const ID_PREFIX = 'P';
        const DATASET = '../datasets/professores.xml';

        public static $db;

        public $idProfessor;
        public $nome;
        public $dataNasc;
        public $habilitacoes;
        public $idCursos; // Array com referencias para cursos lecionados por este professor

        public function __construct($id, $nome, $dataNasc, $habilitacoes, $idCurso) {
            if($id=="") {
                $n = Professor::gerarIdentificador();
                $this->idProfessor = "";
                $this->idProfessor .= Professor::ID_PREFIX.$n;
            } else {
                $this->idProfessor = $id;
            }
            $this->nome = $nome;
            $this->dataNasc = $dataNasc;
            $this->habilitacoes = $habilitacoes;

            $this->idCursos = array();
            if($idCurso!="") {
                $this->idCursos[] = $idCurso;
            }
        }


        // Métodos de classe

        /*
        * Este metodo gera um identificador unico para uma instancia.
        */
        public static function gerarIdentificador() {
            $statement = Professor::$db->prepare("select max(id) from Professor;");
            $statement->execute();
            $result = $statement->fetch();
            return $result[0]+1;
        }
        

        /*
        * Este metodo permite obter uma instancia de um Professor atraves do seu identificador.
        */
        public static function getProfessor($idProfessor) {
            $statement = Professor::$db->prepare("select * from Professor where idProfessor='".$idProfessor."';");
            $statement->execute();
            $row = $statement->fetch(); // Converter $row num array standard

            $pkProfessor = $row[0];
            $p = new Professor($row[1], $row[2], $row[3], $row[4], "");

            // Buscar chaves primarias de cursos lecionados pelo professor
            foreach(Professor::$db->query("select * from ProfessorCurso where idProfessor=".$pkProfessor.";") as $row) {
                $p->idCursos[] = $row[1]; // idCurso (chave primaria)
            }

            return $p;
        }

        /*
        * Acede a bd para encontrar a chave primaria de um determinado professor, pelo seu identificador.
        */
        public static function getChavePrimariaDoProfessor ($idProf) {
            $statement = Professor::$db->prepare("select id from Professor where idProfessor='".$idProf."';");
            $statement->execute();
            $result = $statement->fetch();
        
            return $result[0];
        }

        /*
        * Acede a bd para encontrar identificador de um determinado professor, pela sua chave primaria.
        */
        public static function getIdentificadorPelaChavePrimaria ($id) {
            $statement = Professor::$db->prepare("select idProfessor from Professor where id=".$id.";");
            $statement->execute();
            $result = $statement->fetch();
        
            return $result[0];
        }

        /*
        * Obter valor do total de Professores na base de dados.
        */
        public static function getTotalProfessores() {
            // Usar prepared statements para evitar SQL Injection
            $statement = Professor::$db->prepare("select count(*) from Professor;");
            $statement->execute();
            $result = $statement->fetch(); // Converter $row num array standard
            return $result[0];
        }

        /*
        * Apagar um Professor pelo seu identificador.
        */
        public static function delete($idProfessor) {
            $statement = Professor::$db->prepare("delete from Professor where idProfessor='".$idProfessor."';");
            $statement->execute();
        }

        /*
        * Migrar dados de um data set em XML com diversos Professores para
        * uma base de dados relacional MySQL.
        */
        public static function migrateXMLToMySQL() {
            
            $professores = simplexml_load_file(Professor::DATASET);
            $profs =  $professores->xpath("//professor");           
          

            foreach ($profs as $p) {
                $dataNasc = $p->dataNasc;
                $habilitacoes = $p->habilitacoes;

                $anonasc=-1;           
                $dnfinal = "";
                $nomeprofessor = "";
                foreach (explode(' ',$p->nome) as $n) {
                  $nomeprofessor .= ucfirst(strtolower($n));
                  $nomeprofessor .= " ";
                }
                $nomeprofessor=trim($nomeprofessor);

                $dn = explode("-", $dataNasc);
                if(!empty($dn[2])){
                    $dnfinal = $dn[2]."/".$dn[1]."/".$dn[0];
                    $anonasc = (int)$dn[0];
                } else {
                    $dnfinal = $dataNasc;
                    $anonasc = (int)$dnfinal;
                }
                $curso = $p->curso;
   
                $id = (string)$p[@id];
                $novocomp = new Professor($id, $nomeprofessor, $dnfinal, $habilitacoes, $curso);
                $novocomp->insert();
            }
        }

        /*----------------------------------------------------------------------
            Metodos que geram HTML
        ----------------------------------------------------------------------*/

        /*
        * Metodo que gera todo o catalogo de Professores.
        */
        public static function criarCatalogoDeProfessores() {
            echo "\t<div class='box-body no-padding' style='margin: 2em;'>\n";
            echo "\t\t <table class='table table-striped'>\n";
            echo "\t\t\t<tbody><tr>\n";
            echo "\t\t\t\t<th style='width: 10px'>ID</th>\n";
            echo "\t\t\t\t<th>Nome</th>\n";
            echo "\t\t\t\t<th>Habilitações</th>\n";
            echo "\t\t\t\t<th style='width: 40px'>Acções</th>\n";
            echo "\t\t\t</tr>\n";
            foreach(Professor::$db->query('select * from Professor order by nome;') as $row) {
                $p = new Professor($row[1], $row[2], $row[3], $row[4]);
                
                echo "\t\t\t<tr>\n";
                echo "\t\t\t\t<td>".$p->idProfessor."</td>\n";
                echo "\t\t\t\t<td>".$p->nome."</td>\n";
                echo "\t\t\t\t<td>".$p->habilitacoes."</td>\n";
                echo "\t\t\t\t<td><a href='consult_professor.html?idProfessor=".$p->idProfessor."'><i class='fa fa-eye' style='margin-right: 2px;'></i></a>
                    <a href='edit_professor.html?idProfessor=".$p->idProfessor."'><i class='fa fa-edit'></i></a>
                   "/*<a onclick='deleteProf()'><i class='fa fa-trash'></i></a></td>\n"*/;
            }
                echo "\t\t\t</tr>\n";

            echo "\t\t\t</tbody>\n";
            echo "\t\t</table>\n";
            echo "\t</div>\n";
           
        }

        public static function criarPaginaDoProfessor($idProfessor) {
            $p = Professor::getProfessor($idProfessor);

            echo "\t\t<a href='list_professores.html'><i class='fa fa-arrow-left'></i>  Ir para a lista de professores</a>\n";
            echo "\t\t<div class='box box-solid' style='margin-top: 0.5em;'>\n";
            echo "\t\t\t<div class='box-header with-border'>\n";
            echo "\t\t\t\t<h3 id='nome1' style='font: bold;'>".$p->nome."</h2>\n";
            echo "\t\t\t\t<a href='edit_professor.html?idProfessor=".$p->idProfessor."'><i class='fa fa-pencil-square-o'></i> Editar</a>\n";
            echo "\t\t\t\t<br/><hr/>\n";
            echo "\t\t\t\t<h3 id='habilitacoes1' style='font: bold;'>Habilitações</h3><h4>".$p->habilitacoes."</h4>\n";
            echo "\t\t\t\t<h3 id='dataNasc1' style='font: bold;'>Data de Nascimento</h3><h4>".$p->dataNasc."</h4>\n";

            // Cursos lecionados pelo professor
            echo "\t\t\t\t<h3 style='font: bold;'>Cursos que Leciona</h3>\n";
            echo "\t\t\t\t<ul>\n";
            foreach ($p->idCursos as $idC) {
                $identC = Curso::getIdentificadorDoCurso($idC);
                $designC = Curso::getDesignacaoDoCursoPelaChavePrimaria($idC);
                echo "\t\t\t\t\t<li><a href='../cursos/consult_curso.html?idCurso=".$identC."'>".$designC."</a></li>\n";
            }
            echo "\t\t\t\t</ul>\n";

            echo "\t\t\t</div>\n";

            echo "\t\t<a class='btn btn-danger' onclick='deleteProfessor()' style='float:right; margin-top: 1em;'>\n";
            echo "\t\t\t<i class='fa fa-trash-o fa-lg'></i> Apagar Professor\n";
            echo "\t\t</a>\n";
        }

        /*
        * Metodo que gera toda a tabela de professores, com paginacao.
        */
        public static function criarTabelaDeProfessoresPaginada() {
            $tpaginada = new TabelaPaginada(12, Professor::getTotalProfessores());

            $pagina=1;
            $save_i=0;
            $i=0;
            $novaTabela = ""; // nova tabela da pagina $i+1
            $tabelaTerminada = true;

            foreach(Professor::$db->query('select * from Professor order by nome;') as $row) {
                $p = new Professor($row[1], $row[2], $row[3], $row[4]);
                
                if( $tabelaTerminada==true ) {
                    // Se e divisivel pelo n de linhas da tabela, entao cria-se nova tabela
                    $tabelaTerminada=false;
                    $novaTabela = ""; // reniciar string da tabela

                    $novaTabela.= "\t<div class='box-body no-padding' style='margin: 2em;'>\n";
                    $novaTabela.= "\t\t <table class='table table-striped'>\n";
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
                $novaTabela.= "\t\t\t\t<td>".$p->nome."</td>\n";
                $novaTabela.= "\t\t\t\t<td>".$p->habilitacoes."</td>\n";
                $novaTabela.= "\t\t\t\t<td>
                <a href='consult_professor.html?idProfessor=".$p->idProfessor."'><i class='fa fa-eye' style='margin-right: 2px;'></i></a>
                <a href='edit_professor.html?idProfessor=".$p->idProfessor."'><i class='fa fa-edit' style='margin-right: 2px;'></i></a>\n";
                
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

        /*
        * Metodo que permite listar professores que lecionam um dado curso passado como parâmetro.
        */
        public static function listarProfessoresQueLecionamCurso($idChavePrimaria) {
            echo "\t\t\t\t\t<ul style='margin-top: 1em;'>\n";

            $i=0;
            foreach(Professor::$db->query("select Professor.idProfessor, Professor.nome from Professor inner join ProfessorCurso on Professor.id=ProfessorCurso.idProfessor where idCurso=".$idChavePrimaria." order by nome;") as $row) {
                $idProfessor = $row[0];
                $nomeProfessor = $row[1];
                echo "\t\t\t\t\t\t<li><a id='nome_prof_".$idProfessor."' href='../professores/consult_professor.html?idProfessor=".$idProfessor."'>".$nomeProfessor."</a>
                <a id='".$idProfessor."' href='#' class='delete-professor'>        <i class='fa fa-user-times' style='padding-left: 1em;'></i></a>
                </li>\n";
                $i++;
            }

            if($i==0) {
                echo "\t\t\t\t\t\t<li>De momento não existem professores a lecionar o curso.</li>\n";
            }

            echo "\t\t\t\t\t</ul>\n";
        }

        /*
        * Metodo que permite listar os professores com a coluna de accoes para se possibilitar associacao a um determinado curso.
        * A query da base de dados deve retorna todos os professores excepto aqueles que lecionam o curso.
        */
        public static function listarProfessoresParaSeAssociarACurso($idCurso) {
            $tpaginada = new TabelaPaginada(12, Professor::getTotalProfessores());

            $pagina=1;
            $save_i=0;
            $i=0;
            $novaTabela = ""; // nova tabela da pagina $i+1
            $tabelaTerminada = true;

            $cursoObj = Curso::getCurso($idCurso);
            $cursoPK = Curso::getChavePrimariaDoCurso($cursoObj->designacao);

            // Primeiro calcular número total de professores diferentes que nao lecionam no curso
            $statement = Professor::$db->query("select count(*)
                                                from
                                                (select distinct id, Professor.idProfessor, nome, dataNasc, habilitacoes
                                                from Professor
                                                left join ProfessorCurso on Professor.id=ProfessorCurso.idProfessor
                                                where ProfessorCurso.idProfessor not in (select idProfessor from ProfessorCurso where idCurso=".$cursoPK.")
                                                union
                                                select id, Professor.idProfessor, nome, dataNasc, habilitacoes
                                                from Professor
                                                left join ProfessorCurso
                                                on Professor.id=ProfessorCurso.idProfessor
                                                where ProfessorCurso.idProfessor is NULL) as TotPG;");
            $statement->execute();
            $nprofs = $statement->fetch();

            $tpaginada = new TabelaPaginada(12, $nprofs[0]);

            foreach(Professor::$db->query("select distinct id, Professor.idProfessor, nome, dataNasc, habilitacoes
                                            from Professor
                                            left join ProfessorCurso on Professor.id=ProfessorCurso.idProfessor
                                            where ProfessorCurso.idProfessor not in (select idProfessor from ProfessorCurso where idCurso=".$cursoPK.")
                                            union
                                            select id, Professor.idProfessor, nome, dataNasc, habilitacoes
                                            from Professor
                                            left join ProfessorCurso
                                            on Professor.id=ProfessorCurso.idProfessor
                                            where ProfessorCurso.idProfessor is NULL") as $row) {
                $p = new Professor($row[1], $row[2], $row[3], $row[4]);
                
                if( $tabelaTerminada==true ) {
                    // Se e divisivel pelo n de linhas da tabela, entao cria-se nova tabela
                    $tabelaTerminada=false;
                    $novaTabela = ""; // reniciar string da tabela

                    $novaTabela.= "\t<h2 style='margin: 1em;'>Adicionar professores ao ".$cursoObj->designacao."</h2>\n";
                    $novaTabela.= "\t<p id='nome_curso' style='display: none;'>".$cursoObj->designacao."</p>\n";
                    $novaTabela.= "\t<a href='consult_curso.html?idCurso=".$idCurso."' style='margin-left: 2em;'><i class='fa fa-arrow-left'></i>Voltar à página do curso</a>\n";
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

        /*----------------------------------------------------------------------*/
        /*----------------------------------------------------------------------*/


        // Métodos de instância


        /*
        * Inserir uma instancia de Professor na base de dados.
        */
        public function insert() {
            $query = "insert into Professor(idProfessor, nome, dataNasc, habilitacoes) values(?, ?, ?, ?);";
            $statement = Professor::$db->prepare($query);
            $statement->bindParam(1, $this->idProfessor);
            $statement->bindParam(2, $this->nome);
            $statement->bindParam(3, $this->dataNasc);
            $statement->bindParam(4, $this->habilitacoes);
            $statement->execute();

            if(!empty($this->idCursos)) {
                $statement = Professor::$db->prepare("select id from Professor where idProfessor='".$this->idProfessor."';");
                $statement->execute();
                $pkProfessor = $statement->fetch();

                // Inserir os varios cursos na tabela ProfessorCurso
                foreach ($this->idCursos as $idC) {
                    $statement = Professor::$db->prepare("select id from Curso where idCurso='".$idC."';");
                    $statement->execute();
                    $pkCurso = $statement->fetch();

                    $query = "insert into ProfessorCurso(idProfessor, idCurso) values(?, ?);";
                    $statement = Professor::$db->prepare($query);
                    $statement->bindParam(1, $pkProfessor[0]);
                    $statement->bindParam(2, $pkCurso[0]);

                    $statement->execute();
                }
            }
        }

        /*
        * Metodo que permite atualizar os dados de um Professor consoante os campos alterados.
        */
        public function update($atributos) {
            $query = "update Professor set ";

            // Inserir na query os atributos modificados
            // !Atencao aos tipos de dados no caso do Professor sao todos strings senao tem de se fazer
            // cast para verificar se atributo leva ou nao aspas
            foreach ($atributos as $atr) {
                $query.=$atr."='".$this->$atr."',";
            }
            $query = rtrim($query, ","); // Remover ultima virgula que fica na string $query

            $query.=" where idProfessor='".$this->idProfessor."';";

            $statement = Professor::$db->prepare($query);
            $statement->execute();
        }

        /*
        * Criar string com toda a informacao de um Professor, esta string esta pronta a ser
        * introduzida numa lista html <ul>.
        */
        public function li() {
            return "<li><b>ID</b>: ".$this->idProfessor.", <b>Nome</b>: ".$this->nome.", <b>Data de Nascimento</b>: ".$this->dataNasc.", <b>Habilitacoes</b>: ".$this->habilitacoes."</li>";
        }

    }
    // Instanciar variavel de acesso a base de dados apenas uma vez, guardar interface de acesso
    // em variavel de classe
    Professor::$db = AbstractPDO::getInstance();

?>
