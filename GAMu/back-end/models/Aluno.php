<?php

    require_once 'AbstractPDO.php';
    require_once 'TabelaPaginada.php';
    require_once 'Curso.php';
    require_once 'Grupo.php';
    
    class Aluno {
        const ID_PREFIX = 'A';
        const DATASET = '../datasets/alunos.xml';
        public static $db;

        public $idAluno;
        public $nome;
        public $dataNasc;
        public $idCurso;
        public $anoCurso;
        public $instrumento;
        public $idGrupos;
        
        public function __construct($id, $nome, $dataNasc, $anoCurso, $instrumento, $idCurso) {
            if($id=="") {
                $n = Aluno::gerarIdentificador();
                $this->idAluno = "";
                $this->idAluno .= Aluno::ID_PREFIX.$n;
            } else {
                $this->idAluno = $id;
            }
            $this->nome = $nome;
            $this->dataNasc = $dataNasc;
            $this->idCurso = $idCurso;
            $this->anoCurso = $anoCurso;
            $this->instrumento = $instrumento;
            $this->idCurso = $idCurso;
            $this->idGrupos = Array();          
        }


        // Metodos de classe

        /*
        * Este metodo gera um identificador unico para uma instancia.
        */
        public static function gerarIdentificador() {
            $statement = Aluno::$db->prepare("select max(id) from Aluno;");
            $statement->execute();
            $result = $statement->fetch();
            return $result[0]+1;
        }

        /*
        * Este metodo permite obter uma instancia de um aluno atraves do seu identificador.
        */
        public static function getAluno($idAluno) {
            $statement = Aluno::$db->prepare("select * from Aluno where idAluno='".$idAluno."';");
            $statement->execute();
            $row = $statement->fetch(); // Converter $row num array standard

            $statement = Aluno::$db->prepare("select designacao from Curso where id='".$row[6]."';");
            $statement->execute();
            $curso = $statement->fetch();

            $al = new Aluno($row[1], $row[2], $row[3], $row[4], $row[5], $curso[0]);

            $pkAluno = $row[0];

            // Buscar chaves primarias de grupos a que o aluno pertence
            foreach(Aluno::$db->query("select * from AlunoGrupo where idAluno=".$pkAluno.";") as $row) {
                $al->idGrupos[] = $row[1]; // [0] idProfessor [1] idGrupo
            }

            return $al;
        }

        /*
        * Obter valor do total de alunos na base de dados.
        */
        public static function getTotalAlunos() {
            // Usar prepared statements para evitar SQL Injection
            $statement = Aluno::$db->prepare("select count(*) from Aluno;");
            $statement->execute();
            $result = $statement->fetch();
            return $result[0];
        }

        /*
        * Apagar um aluno pelo seu identificador.
        */
        public static function delete($idAluno) {
            $statement = Aluno::$db->prepare("delete from Aluno where idAluno='".$idAluno."';");
            $statement->execute();
        }

        /*
        * Este metodo permite obter a chave primaria da base de dados de um aluno pelo seu nome.
        */
        public static function getChavePrimariaDoAluno($nome) {
            $statement = Aluno::$db->prepare("select id from Aluno where nome='".$nome."';");
            $statement->execute();
            $row = $statement->fetch(); // Converter $row num array standard
            return $row[0];
        }

        /*
        * Este metodo permite obter a chave primaria da base de dados de um aluno pelo seu identificador.
        */
        public static function getChavePrimariaDoAlunoPeloIdentificador($idAluno) {
            $statement = Aluno::$db->prepare("select id from Aluno where idAluno='".$idAluno."';");
            $statement->execute();
            $row = $statement->fetch(); // Converter $row num array standard
            return $row[0];
        }

        /*
        * Este metodo permite obter o identificador do aluno pela sua chave primaria.
        */
        public static function getIdentificadorPelaChavePrimaria($id) {
            $statement = Aluno::$db->prepare("select idAluno from Aluno where id=".$id.";");
            $statement->execute();
            $row = $statement->fetch();
            return $row[0];
        }
        
        /*
        * Este metodo permite obter o nome de um aluno uma vez fornecida a sua chave primaria.
        */
        public static function getNomeDoAlunoPelaChavePrimaria($id) {
            $statement = Aluno::$db->prepare("select nome from Aluno where id=".$id.";");
            $statement->execute();
            $row = $statement->fetch();
            return $row[0];
        }
        
        /*
        * Este metodo retorna um array com o nome de todos os cursos registados na base de dados.
        * Foi criado com o proposito de alimentar a drop down da criacao de um aluno
        */
        public static function getNomesCursos() {
            $nomes = array();
            foreach(Aluno::$db->query("select designacao from Curso;") as $row) {
                $nomes[] = $row[0];
            }
            return $nomes;
        }
        
        
        /*
        * Metodo que normaliza datas segundo o formato de apresentacao da interface web.
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
        * Acede a bd para encontrar o id de um determinado curso 
        * procurando pelo seu idCurso
        */
        public function getIdCurso ($idCurso) {
            $statement = Aluno::$db->query('select id from Curso where idCurso="'.$idCurso.'";');
            $statement->execute();
            $result = $statement->fetch();
        
            return $result[0];
        }

        /* 
        * Acede a bd para encontrar o id (CHAVE PRIMARIA) de um determinado curso 
        * procurando pela sua designacao
        */
        public function getIdCursoByDesig ($desig) {
            $statement = Aluno::$db->query('select id from Curso where designacao="'.$desig.'";');
            $statement->execute();
            $result = $statement->fetch();
        
            return $result[0];
        }

        /* 
        * Acede a bd para encontrar o IDENTIFICADOR/CODIGO de um determinado curso 
        * procurando pela sua designacao
        */
        public function getCodigoCursoByDesig ($desig) {
            $statement = Aluno::$db->query('select idCurso from Curso where designacao="'.$desig.'";');
            $statement->execute();
            $result = $statement->fetch();
        
            return $result[0];
        }
        
        /*
        * Migrar dados de um data set em XML com diversos compositores para
        * uma base de dados relacional MySQL.
        */
        public static function migrateXMLToMySQL() {
            $alunosDS = simplexml_load_file(Aluno::DATASET);
            $alunos =  $alunosDS->xpath("//aluno");
           
            foreach ($alunos as $a) {
                $idAluno = (string)$a[@id];
                
                $nomealuno = "";
                foreach (explode(' ',$a->nome) as $n) {
                  $nomealuno .= ucfirst(strtolower($n));
                  $nomealuno .= " ";
                }
                $nome = trim($nomealuno);
                
                $dataNasc = $a->dataNasc;
                $dnfinal = "";
                $dn = explode("-", $dataNasc);
                if(!empty($dn[2])){
                    $dnfinal = $dn[2]."/".$dn[1]."/".$dn[0];
                } else {
                    $dnfinal = $dataNasc;
                }
                
                $idCurso = Aluno::getIdCurso($a->curso);
                $anoCurso = $a->anoCurso;
                $instrumento = $a->instrumento;
                
                $novoal = new Aluno($idAluno, $nome, $dnfinal, $anoCurso, $instrumento, $idCurso);
                if(!empty($a->grupo)) {
                    $novoal->idGrupos[] = $a->grupo;
                }
                $novoal->insert();
            }
        }
 
        
        /*----------------------------------------------------------------------
            Metodos que geram HTML
        ----------------------------------------------------------------------*/

        /*
        * Metodo que gera todo o catalogo de alunos.
        */
        public static function criarCatalogoDeAlunos() {
            echo "\t<div class='box-body no-padding' style='margin: 2em;'>\n";
            echo "\t\t <table class='table table-striped'>\n";
            echo "\t\t\t<tbody><tr>\n";
            echo "\t\t\t\t<th width=5%>ID</th>\n";
            echo "\t\t\t\t<th width=43%>Nome</th>\n";
            echo "\t\t\t\t<th width=35%>Curso</th>\n";
            echo "\t\t\t\t<th width=10%>Ano</th>\n";
            echo "\t\t\t\t<th width=7%>Acções</th>\n";
            echo "\t\t\t</tr>\n";
              
            foreach(Aluno::$db->query('select * from Aluno order by nome;') as $row) {
                $al = new Aluno($row[1], $row[2], $row[3], $row[4], $row[5], $row[6]);
                $statement = Aluno::$db->query('select designacao from Curso where id = '.$al->idCurso.';');
                $statement->execute();
                $curso = $statement->fetch(); 
    
                echo "\t\t\t<tr>\n";
                echo "\t\t\t\t<td>".$al->idAluno."</td>\n";
                echo "\t\t\t\t<td>".$al->nome."</td>\n";
                echo "\t\t\t\t<td>".$curso[0]."</td>\n";
                echo "\t\t\t\t<td>".$al->anoCurso."&ordm;</td>\n";
                echo "\t\t\t\t<td><a href='consult_aluno.html?idAluno=".$al->idAluno."'><i class='fa fa-eye' style='margin-right: 2px;'></i></a>
                        <a href='edit_aluno.html?idAluno=".$al->idAluno."'><i class='fa fa-edit'></i></a>\n";
                echo "\t\t\t</tr>\n";   
            }
            
            echo "\t\t\t</tbody>\n";
            echo "\t\t</table>\n";
            echo "\t</div>\n";
        }

        public static function criarPaginaDoAluno($idAluno) {
            $al = Aluno::getAluno($idAluno);
            
            echo "\t\t<a href='list_alunos.html'><i class='fa fa-arrow-left'></i> Voltar à listagem de Alunos </a><br/>\n";
            // ?? Remover
            // echo "\t\t<a href='../cursos/consult_curso.html?idCurso=".Aluno::getCodigoCursoByDesig($al->idCurso)."'><i class='fa fa-arrow-left'></i> Ir para página do ".$al->idCurso." </a>\n";
            echo "\t\t<div class='box box-solid' style='margin-top: 0.5em;'>\n";
            echo "\t\t\t<div class='box-header with-border'>\n";
            echo "\t\t\t\t<h3 id='nome'>".$al->nome."</h3>\n";
            echo "\t\t\t\t<a href='edit_aluno.html?idAluno=".$al->idAluno."'><i class='fa fa-pencil-square-o'></i> Editar</a><hr/>\n";
            echo "\t\t\t\t<h3 style='font: bold;'>Número de Aluno</h3><h4 id='data_nasc'>".$al->idAluno."</h4>\n";
            echo "\t\t\t\t<h3 style='font: bold;'>Data de Nascimento</h3><h4 id='data_nasc'>".$al->dataNasc."</h4>\n";
            echo "\t\t\t\t<h3 style='font: bold;'>Instrumento</h3><h4 id='instr'>".$al->instrumento."</h4>\n";

            $identCurso = Curso::getIdentificadorDoCursoPelaDesignacao($al->idCurso);
            echo "\t\t\t\t<h3 style='font: bold;'>Curso</h3><h4 id='curso'><a href='../cursos/consult_curso.html?idCurso=".$identCurso."'>".$al->idCurso."</a></h4>\n";

            echo "\t\t\t\t<h3 style='font: bold;'>Ano do Curso</h3><h4 id='ano_curso'>".$al->anoCurso."&ordm;</h4>\n";


            echo "\t\t\t\t<h3 style='font: bold;'>Grupos a que pertence</h3>\n";
            echo "\t\t\t\t<ul>\n";
            if(count($al->idGrupos) > 0) {
                foreach ($al->idGrupos as $grupoPK) {
                    $g = Grupo::getGrupoPelaChavePrimaria($grupoPK);
                    echo "\t\t\t\t\t<li><a href='../grupos/consult_grupo.html?idGrupo=".$g->idGrupo."'>".$g->nome."</a></li>\n";
                }
            } else {
                echo "\t\t\t\t\t<li>De momento o aluno não pertence a nenhum grupo.</li>\n";
            }
            echo "\t\t\t\t</ul>\n";


            echo "\t\t\t</div>\n";
            echo "\t\t<a class='btn btn-danger' onclick='deleteAluno()' style='float:right; margin-top: 1em;'>\n";
            echo "\t\t\t<i class='fa fa-trash-o fa-lg'></i> Apagar Aluno\n";
            echo "\t\t</a>\n";
        }

        /*
        * Metodo que gera toda a tabela de alunos, com paginacao.
        */
        public static function criarTabelaDeAlunosPaginada() {
            $tpaginada = new TabelaPaginada(12, Aluno::getTotalAlunos());

            $pagina=1;
            $save_i=0;
            $i=0;
            $novaTabela = ""; // nova tabela da pagina $i+1
            $tabelaTerminada = true;

            foreach(Aluno::$db->query('select * from Aluno order by nome;') as $row) {
                $statement = Aluno::$db->prepare("select designacao from Curso where id='".$row[6]."';");
                $statement->execute();
                $curso = $statement->fetch();

                $al = new Aluno($row[1], $row[2], $row[3], $row[4], $row[5], $curso[0]);
                
                if( $tabelaTerminada==true ) {
                    // Se e divisivel pelo n de linhas da tabela, entao cria-se nova tabela
                    $tabelaTerminada=false;
                    $novaTabela = ""; // reniciar string da tabela

                    $novaTabela.= "\t<div class='box-body no-padding' style='margin: 2em;'>\n";
                    $novaTabela.= "\t\t <table class='table table-striped'>\n";
                    $novaTabela.= "\t\t\t<tbody><tr>\n";
                    $novaTabela.= "\t\t\t\t<th width=5%>ID</th>\n";
                    $novaTabela.= "\t\t\t\t<th width=43%>Nome</th>\n";
                    $novaTabela.= "\t\t\t\t<th width=35%>Curso</th>\n";
                    $novaTabela.= "\t\t\t\t<th width=10%>Ano</th>\n";
                    $novaTabela.= "\t\t\t\t<th width=7%>Acções</th>\n";
                    $novaTabela.= "\t\t\t</tr>\n";
                    // echo "ABRE TABELA: ".$i."\n";
                }

                // echo "LINHA DE UMA TABELA ".$i."\n";
                $novaTabela.= "\t\t\t<tr>\n";
                $novaTabela.= "\t\t\t\t<td>".$al->idAluno."</td>\n";
                $novaTabela.= "\t\t\t\t<td>".$al->nome."</td>\n";
                $novaTabela.= "\t\t\t\t<td>".$al->idCurso."</td>\n";
                $novaTabela.= "\t\t\t\t<td>".$al->anoCurso."&ordm;</td>\n";
                $novaTabela.= "\t\t\t\t<td>
                <a href='consult_aluno.html?idAluno=".$al->idAluno."'><i class='fa fa-eye' style='margin-right: 2px;'></i></a>
                <a href='edit_aluno.html?idAluno=".$al->idAluno."'><i class='fa fa-edit' style='margin-right: 2px;'></i></a>\n";
                
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
        * Metodo que gera toda a tabela de alunos que frequentam um dado
        * curso passsacom paginacao.
        */
        public static function criarTabelaDeAlunosDoCurso($idChavePrimaria) {

            echo "\t<div style='overflow-y:scroll; height:90%; width=100%;'>";
            echo "\t\t<div class='box-body no-padding' style='margin: 2em;'>\n";
            echo "\t\t <table class='table table-striped'>\n";
            echo "\t\t\t\t<tbody><tr>\n";
            echo "\t\t\t\t\t<th width=5%>ID</th>\n";
            echo "\t\t\t\t\t<th width=43%>Nome</th>\n";
            echo "\t\t\t\t\t<th width=10%>Ano</th>\n";
            echo "\t\t\t\t\t<th width=7%>Acções</th>\n";
            echo "\t\t\t\t</tr>\n";
              
            foreach(Aluno::$db->query("select * from Aluno where idCurso=".$idChavePrimaria." order by nome;") as $row) {
                $al = new Aluno($row[1], $row[2], $row[3], $row[4], $row[5], $row[6]);
    
                echo "\t\t\t\t<tr>\n";
                echo "\t\t\t\t\t<td>".$al->idAluno."</td>\n";
                echo "\t\t\t\t\t<td>".$al->nome."</td>\n";
                echo "\t\t\t\t\t<td>".$al->anoCurso."&ordm;</td>\n";
                echo "\t\t\t\t\t<td><a href='../alunos/consult_aluno.html?idAluno=".$al->idAluno."'><i class='fa fa-eye' style='margin-right: 2px;'></i></a>\n";
                echo "\t\t\t\t</tr>\n";   
            }
            
            echo "\t\t\t\t</tbody>\n";
            echo "\t\t\t</table>\n";
            echo "\t\t</div>\n";
            echo "\t</div>\n";
        }

        /*
        * Método que permite criar listagem dos alunos que pertencem a um determinado grupo passado
        * como argumento.
        */
        public static function criarTabelaDeAlunosDoGrupo($idGrupo) {
            echo "\t<div style='overflow-y:scroll; height:90%; width=100%;'>";
            echo "\t\t<div class='box-body no-padding' style='margin: 2em;'>\n";
            echo "\t\t <table class='table table-striped'>\n";
            echo "\t\t\t\t<tbody><tr>\n";
            echo "\t\t\t\t\t<th width=5%>ID</th>\n";
            echo "\t\t\t\t\t<th width=33%>Nome</th>\n";
            echo "\t\t\t\t\t<th width=20%>Instrumento</th>\n";
            echo "\t\t\t\t\t<th width=7%>Acções</th>\n";
            echo "\t\t\t\t</tr>\n";

            foreach(Aluno::$db->query("select * from Aluno inner join AlunoGrupo on Aluno.id=AlunoGrupo.idAluno where AlunoGrupo.idGrupo=".$idGrupo." order by nome;") as $row) {
                $al = new Aluno($row[1], $row[2], $row[3], $row[4], $row[5], $row[6]);
    
                echo "\t\t\t\t<tr>\n";
                echo "\t\t\t\t\t<td>".$al->idAluno."</td>\n";
                echo "\t\t\t\t\t<td id='nome_aluno_".$al->idAluno."'>".$al->nome."</td>\n";
                echo "\t\t\t\t\t<td>".$al->instrumento."</td>\n";
                echo "\t\t\t\t\t<td>
                <a href='../alunos/consult_aluno.html?idAluno=".$al->idAluno."'><i class='fa fa-eye' style='margin-right: 2px;'></i>
                <a id='".$al->idAluno."' href='#' class='delete-aluno'><i class='fa fa-user-times' style='margin-right: 2px;'></i>
                </a>\n";
                echo "\t\t\t\t</tr>\n";
            }
            
            echo "\t\t\t\t</tbody>\n";
            echo "\t\t\t</table>\n";
            echo "\t\t</div>\n";
            echo "\t</div>\n";
        }

        /*
        * Metodo que permite listar os alunos com a coluna de accoes para se possibilitar associacao a um determinado grupo.
        * A query da base de dados deve retorna todos os alunos excepto aqueles que estao o grupo.
        */
        public static function listarAlunosParaSeAssociarAGrupo($idGrupo) {
            $pagina=1;
            $save_i=0;
            $i=0;
            $novaTabela = ""; // nova tabela da pagina $i+1
            $tabelaTerminada = true;

            $grupoObj = Grupo::getGrupo($idGrupo);
            $grupoPK = Grupo::getChavePrimariaPeloIdentificador($idGrupo);

            // Primeiro calcular número total de alunos que nao pertencem ao grupo
            $statement = Aluno::$db->query("select count(*)
                                            from
                                            (select distinct Aluno.id, Aluno.idAluno, nome, dataNasc, anoCurso, instrumento, idCurso
                                            from Aluno
                                            left join AlunoGrupo on Aluno.id=AlunoGrupo.idAluno
                                            where AlunoGrupo.idAluno not in (select idAluno from AlunoGrupo where idGrupo=".$grupoPK.")
                                            union
                                            select Aluno.id, Aluno.idAluno, nome, dataNasc, anoCurso, instrumento, idCurso
                                            from Aluno
                                            left join AlunoGrupo
                                            on Aluno.id=AlunoGrupo.idAluno
                                            where AlunoGrupo.idAluno is NULL) as TotAG;");
            $statement->execute();
            $nalunos = $statement->fetch();

            $tpaginada = new TabelaPaginada(12, $nalunos[0]);

            foreach(Aluno::$db->query("select distinct Aluno.id, Aluno.idAluno, nome, dataNasc, anoCurso, instrumento, idCurso
                                        from Aluno
                                        left join AlunoGrupo on Aluno.id=AlunoGrupo.idAluno
                                        where AlunoGrupo.idAluno not in (select idAluno from AlunoGrupo where idGrupo=".$grupoPK.")
                                        union
                                        select Aluno.id, Aluno.idAluno, nome, dataNasc, anoCurso, instrumento, idCurso
                                        from Aluno
                                        left join AlunoGrupo
                                        on Aluno.id=AlunoGrupo.idAluno
                                        where AlunoGrupo.idAluno is NULL") as $row) {
                $al = new Aluno($row[1], $row[2], $row[3], $row[4], $row[5], $row[6]);
                
                if( $tabelaTerminada==true ) {
                    // Se e divisivel pelo n de linhas da tabela, entao cria-se nova tabela
                    $tabelaTerminada=false;
                    $novaTabela = ""; // reniciar string da tabela

                    $novaTabela.= "\t<h2 style='margin: 1em;'>Adicionar alunos a ".$grupoObj->nome."</h2>\n";
                    $novaTabela.= "\t<p id='nome_grupo' style='display: none;'>".$grupoObj->nome."</p>\n";
                    $novaTabela.= "\t<a href='consult_grupo.html?idGrupo=".$idGrupo."' style='margin-left: 2em;'><i class='fa fa-arrow-left'></i>Voltar à página do grupo</a>\n";
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

        /*----------------------------------------------------------------------*/
        /*----------------------------------------------------------------------*/


        // Metodos de instancia


        /*
        * Inserir uma instancia de Aluno na base de dados.
        */
        public function insert() {
            $query = "insert into Aluno(idAluno, nome, dataNasc, idCurso, anoCurso, instrumento) values(?, ?, ?, ?, ?, ?);";
            $statement = Aluno::$db->prepare($query);
            $statement->bindParam(1, $this->idAluno);
            $statement->bindParam(2, $this->nome);
            $statement->bindParam(3, $this->dataNasc);
            $statement->bindParam(4, $this->idCurso);
            $statement->bindParam(5, $this->anoCurso);
            $statement->bindParam(6, $this->instrumento);
            $statement->execute();

            if(!empty($this->idGrupos)) {
                $statement = Aluno::$db->prepare("select id from Aluno where idAluno='".$this->idAluno."';");
                $statement->execute();
                $pkAluno = $statement->fetch();

                // Inserir os varios grupos na tabela AlunoGrupo
                foreach ($this->idGrupos as $idG) {
                    $statement = Aluno::$db->prepare("select id from Grupo where idGrupo='".$idG."';");
                    $statement->execute();
                    $pkGrupo = $statement->fetch();

                    $query = "insert into AlunoGrupo(idAluno, idGrupo) values(?, ?);";
                    $statement = Aluno::$db->prepare($query);
                    $statement->bindParam(1, $pkAluno[0]);
                    $statement->bindParam(2, $pkGrupo[0]);

                    $statement->execute();
                }
            }
        }

        /*
        * Metodo que permite atualizar os dados de um aluno consoante os campos alterados.
        */
        public function update($atributos) {
            $query = "update Aluno set ";

            // Inserir na query os atributos modificados
            // !Atencao aos tipos de dados no caso do aluno sao todos strings senao tem de se fazer
            // cast para verificar se atributo leva ou nao aspas
            foreach ($atributos as $atr) {
                if ($atr=="anoCurso" || $atr=="idCurso") {
                    $query.=$atr."=".$this->$atr.",";
                } else {
                    $query.=$atr."='".$this->$atr."',";
                }
            }
            $query = rtrim($query, ","); // Remover ultima virgula que fica na string $query

            $query.=" where idAluno='".$this->idAluno."';";

            $statement = Aluno::$db->prepare($query);
            $statement->execute();
        }
         
    }
    // Instanciar variavel de acesso a base de dados apenas uma vez, guardar interface de acesso
    // em variavel de classe
    Aluno::$db = AbstractPDO::getInstance();

?>
