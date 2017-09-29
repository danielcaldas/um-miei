<?php

    require_once 'AbstractPDO.php';
    require_once 'Aluno.php';
    require_once 'Professor.php';

    class Curso {
        const ID_PREFIX_DEFAUT = 'CR';
        const ID_PREFIX_BASICO = 'CB';
        const ID_PREFIX_SUPLETIVO = 'CS';
        const ID_PREFIX_AVANCADO = 'CA';
        const DATASET = '../datasets/cursos.xml';
        public static $db;

        public $idCurso;
        public $designacao;
        public $duracao;
        public $idInstrumento;

        public function __construct($id, $designacao, $duracao, $idInstr) {
            if($id=="") {
                $n = Curso::gerarIdentificador();
                $this->idCurso = "";

                // Detectar qual o prefixo adequado do curso baseado na designacao do mesmo
                $design_aux = strtolower($designacao);

                if(strpos($design_aux, "básico")) {
                    $this->idCurso .= Curso::ID_PREFIX_BASICO.$n;
                } else if(strpos($design_aux, "supletivo")) {
                    $this->idCurso .= Curso::ID_PREFIX_SUPLETIVO.$n;
                } else if(strpos($design_aux, "avançado")) {
                    $this->idCurso .= Curso::ID_PREFIX_AVANCADO.$n;
                } else {
                    // Colocar nome do curso por defeito
                    $this->idCurso .= Curso::ID_PREFIX_DEFAUT.$n;
                }
            } else {
                $this->idCurso = $id;
            }
            $this->designacao = $designacao;
            $this->duracao = $duracao;
            $this->idInstrumento = $idInstr;
        }


        // Metodos de classe

        /*
        * Este metodo gera um identificador unico para uma instancia.
        */
        public static function gerarIdentificador() {
            $statement = Curso::$db->prepare("select max(id) from Curso;");
            $statement->execute();
            $result = $statement->fetch();
            return $result[0]+1;
        }

        /*
        * Este metodo permite obter uma instancia de um curso atraves do seu identificador.
        */
        public static function getCurso($idCurso) {
            $statement = Curso::$db->prepare("select * from Curso where idCurso='".$idCurso."';");
            $statement->execute();
            $row = $statement->fetch(); // Converter $row num array standard
    
            $statement = Curso::$db->prepare("select nome from Instrumento where id='".$row[4]."';");
            $statement->execute();
            $inst = $statement->fetch(); // Converter $inst num array standard

            $c = new Curso($row[1], $row[2], $row[3], $inst[0]);

            return $c;
        }

        /*
        * Obter valor do total de cursos na base de dados.
        */
        public static function getTotalCursos() {
            // Usar prepared statements para evitar SQL Injection
            $statement = Curso::$db->prepare("select count(*) from Curso;");
            $statement->execute();
            $result = $statement->fetch(); // Converter $row num array standard
            return $result[0];
        }

        /*
        * Apagar um curso pelo seu identificador.
        */
        public function delete($idCurso) {
            $statement = Curso::$db->prepare("delete from Curso where idCurso='".$idCurso."';");
            $statement->execute();
        }
        
        /*
        * Este metodo permite obter a chave primaria da base de dados de um curso pela sua designacao.
        */
        public static function getChavePrimariaDoCurso($desig) {
            $statement = Curso::$db->prepare("select id from Curso where designacao='".$desig."';");
            $statement->execute();
            $row = $statement->fetch(); // Converter $row num array standard
            return $row[0];
        }

        /*
        * Este metodo permite obter a chave primaria da base de dados de um curso pelo seu indentificador.
        */
        public static function getChavePrimariaDoCursoPeloIdentificador($idCurso) {
            $statement = Curso::$db->prepare("select id from Curso where idCurso='".$idCurso."';");
            $statement->execute();
            $row = $statement->fetch(); // Converter $row num array standard
            return $row[0];
        }

        /*
        * Este metodo permite obter o identificador de um curso pela sua chave primaria.
        */
        public static function getIdentificadorDoCurso($id) {
            $statement = Curso::$db->prepare("select idCurso from Curso where id=".$id.";");
            $statement->execute();
            $row = $statement->fetch();
            return $row[0];
        }
        
        /*
        * Este metodo permite obter a designacao de um curso uma vez fornecida a sua chave primaria.
        */
        public static function getDesignacaoDoCursoPelaChavePrimaria($id) {
            $statement = Curso::$db->prepare("select designacao from Curso where id=".$id.";");
            $statement->execute();
            $row = $statement->fetch();
            return $row[0];
        }

        /*
        * Este metodo permite obter o identificador de um curso uma vez fornecida a sua designacao.
        */
        public static function getIdentificadorDoCursoPelaDesignacao($design) {
            $statement = Curso::$db->prepare("select idCurso from Curso where designacao='".$design."';");
            $statement->execute();
            $row = $statement->fetch();
            return $row[0];
        }
        
        /*
        * Este metodo retorna um array com o nome de todos os instrumentos registados na base de dados.
        * Foi criado com o proposito de alimentar a drop down da criacao de um curso
        */
        public static function getNomesInstrumentos() {
            $nomes = array();
            foreach(Curso::$db->query("select nome from Instrumento order by nome;") as $row) {
                $nomes[] = $row[0];
            }
            return $nomes;
        }

        /*
        * Acede a bd para encontrar o id de um determinado instrumento
        */
        public static function getIdInstrumento ($nome) {
            $statement = Curso::$db->query('select id from Instrumento where nome="'.$nome.'";');
            $statement->execute();
            $result = $statement->fetch();
        
            return $result[0];
        }

        /*
        * Metodo que permite associar um dado professor a um determinado curso, ambos
        * passados como parametro.
        */
        public static function associarProfessorAoCurso($idProfessor, $idCurso) {
            $professorPK = Professor::getChavePrimariaDoProfessor($idProfessor);
            $cursoPK = Curso::getChavePrimariaDoCursoPeloIdentificador($idCurso);

            $query = "insert into ProfessorCurso(idProfessor, idCurso) values(?, ?);";
            $statement = Curso::$db->prepare($query);
            $statement->bindParam(1, $professorPK);
            $statement->bindParam(2, $cursoPK);
            
            $statement->execute();
        }

        /*
        * Metodo que permite dissociar um dado professor de um determinado curso, ambos
        * passados como parametro (remover professor do corpo docente).
        */
        public static function dissociarProfessorDoCurso($idProfessor, $idCurso) {
            $professorPK = Professor::getChavePrimariaDoProfessor($idProfessor);
            $cursoPK = Curso::getChavePrimariaDoCursoPeloIdentificador($idCurso);

            $query = "delete from ProfessorCurso where idProfessor=".$professorPK." and idCurso=".$cursoPK.";";
            $statement = Curso::$db->prepare($query);
            $statement->execute();
        }

        /**
        * Metodo que gera xml com identificadores dos cursos.
        */
        public function geraXMLIdsCursos() {
            $bd = simplexml_load_file("ids.xml");
            $cursos = $bd->xpath("//cursos");
            foreach(Curso::$db->query('select * from Curso order by designacao;') as $row) {
                $bd->addChild('id', $row[1]);
            }
            $dom = new DOMDocument('1.0');
            $dom->preserveWhiteSpace = false;
            $dom->formatOutput = true;
            $dom->loadXML($bd->asXML());
            $dom->save("ids.xml");
        }
                
        /*
        * Migrar dados de um data set em XML com diversos cursos para
        * uma base de dados relacional MySQL.
        */
        public static function migrateXMLToMySQL() {
            $cursosDS = simplexml_load_file(Curso::DATASET);
            $cursos =  $cursosDS->xpath("//curso");
            
            foreach ($cursos as $c) {
                $id = (string)$c[@id];
                
                $desig = "";
                foreach (explode(' ',$c->designacao) as $n) {
                  $desig .= ucfirst(strtolower($n));
                  $desig .= " ";
                }
                $designacao = trim($desig);
                
                $duracao = $c->duracao;
                $idInstrumento = Curso::getIdInstrumento($c->instrumento);
                
                $novocurso = new Curso($id, $designacao, $duracao, $idInstrumento);
                $novocurso->insert();
            }
        }

        /*----------------------------------------------------------------------
            Metodos que geram HTML
        ----------------------------------------------------------------------*/

        /*
        * Metodo que gera todo o catalogo de cursos.
        */
        public static function criarCatalogoDeCursos() {
            echo "\t<div class='box-body no-padding' style='margin: 2em;'>\n";
            echo "\t\t <table class='table table-striped'>\n";
            echo "\t\t\t<tbody><tr>\n";
            echo "\t\t\t\t<th width=10%>ID</th>\n";
            echo "\t\t\t\t<th width=50%>Designação</th>\n";
            echo "\t\t\t\t<th width=25%>Duração</th>\n";
            echo "\t\t\t\t<th width=25%>Acções</th>\n";
            echo "\t\t\t</tr>\n";
              
            foreach(Curso::$db->query('select * from Curso order by designacao;') as $row) {
                $c = new Curso($row[1], $row[2], $row[3], $row[4], $row[5]);
    
                echo "\t\t\t<tr>\n";
                echo "\t\t\t\t<td>".$c->idCurso."</td>\n";
                echo "\t\t\t\t<td>".$c->designacao."</td>\n";
                echo "\t\t\t\t<td>".$c->duracao." anos</td>\n";
                echo "\t\t\t\t<td><a href='consult_curso.html?idCurso=".$c->idCurso."'><i class='fa fa-eye' style='margin-right: 2px;'></i></a>
                                  <a href='edit_curso.html?idCurso=".$c->idCurso."'><i class='fa fa-edit'></i></a>\n";
                echo "\t\t\t</tr>\n";   
            }
            
            echo "\t\t\t</tbody>\n";
            echo "\t\t</table>\n";
            echo "\t</div>\n";
        }

        public static function criarPaginaDoCurso($idCurso) {
            $c = Curso::getCurso($idCurso);

            echo "\t\t<a href='list_cursos.html'><i class='fa fa-arrow-left'></i> Ir para listagem de Cursos </a>\n";
            echo "\t\t<div class='box box-solid' style='margin-top: 0.5em;'>\n";
            echo "\t\t\t<div class='box-header with-border'>\n";
            echo "\t\t\t\t<h2 id='nome_curso' style='font: bold;'>".$c->designacao."</h2>\n";
            echo "\t\t\t\t<a href='edit_curso.html?idCurso=".$c->idCurso."'><i class='fa fa-pencil-square-o'></i> Editar dados do Curso</a><hr/>\n";
            echo "\t\t\t\t<h3 style='font: bold;'>Código do Curso</h3><h4 id='idCurso'>".$c->idCurso."</h4>\n";
            echo "\t\t\t\t<h3 style='font: bold;'>Duração</h3><h4 id='duracao'>".$c->duracao." anos</h4>\n";
            echo "\t\t\t\t<h3 style='font: bold;'>Instrumento</h3><h4 id='idInstr'>".$c->idInstrumento."</h4>\n";

            // Buscar id (chave primaria do curso)
            $statement = Curso::$db->prepare("select id from Curso where idCurso='".$c->idCurso."';");
            $statement->execute();
            $res = $statement->fetch();

            $idChavePrimaria = (int)$res[0];

            // Listar professores que lecionam o curso
            echo "\t\t\t\t<br/><hr/><br/><h3 style='font: bold;'>Professores a lecionar o curso</h3>\n";
            echo "\t\t\t\t<a href='add_professor_curso.html?idCurso=".$c->idCurso."' style='font-size: 16px;'><i class='fa fa-user-plus' style='font-size: 22px;'></i>   Adicionar novo professor ao corpo docente</a>\n";
            Professor::listarProfessoresQueLecionamCurso($idChavePrimaria);

            // Listar Alunos deste curso
            echo "\t\t\t\t<hr/><br/><h3 style='font: bold;'>Alunos a frequentar o curso</h3>\n";
            Aluno::criarTabelaDeAlunosDoCurso($idChavePrimaria);

            echo "\t\t\t</div>\n";
            echo "\t\t<a class='btn btn-danger' onclick='deleteCurso()' style='float:right; margin-top: 1em;'>\n";
            echo "\t\t\t<i class='fa fa-trash-o fa-lg'></i> Apagar Curso\n";
            echo "\t\t</a>\n";
            
        }
 
        
        /*----------------------------------------------------------------------*/
        /*----------------------------------------------------------------------*/


        // Metodos de instancia


        /*
        * Inserir uma instancia de Curso na base de dados.
        */
        public function insert() {
            $query = "insert into Curso(idCurso, designacao, duracao, idInst) values(?, ?, ?, ?);";
            $statement = Curso::$db->prepare($query);
            $statement->bindParam(1, $this->idCurso);
            $statement->bindParam(2, $this->designacao);
            $statement->bindParam(3, $this->duracao);
            $statement->bindParam(4, $this->idInstrumento);
            
            $statement->execute();
        }

        /*
        * Metodo que permite atualizar os dados de um curso consoante os campos alterados.
        */
        public function update($atributos) {
            $query = "update Curso set ";

            // Inserir na query os atributos modificados
            // !Atencao aos tipos de dados no caso do compositor sao todos strings senao tem de se fazer
            // cast para verificar se atributo leva ou nao aspas
            foreach ($atributos as $atr) {
                if($atr=="idInst") {
                    $query.=$atr."=".$this->idInstrumento.",";
                } else if ($atr=="duracao") {
                    $query.=$atr."=".$this->$atr.",";
                } else {
                    $query.=$atr."='".$this->$atr."',";
                }
            }
            $query = rtrim($query, ","); // Remover ultima virgula que fica na string $query

            $query.=" where idCurso='".$this->idCurso."';";

            $statement = Curso::$db->prepare($query);
            $statement->execute();
        }
        
    }
    
    // Instanciar variavel de acesso a base de dados apenas uma vez, guardar interface de acesso
    // em variavel de classe
    Curso::$db = AbstractPDO::getInstance();

?>
