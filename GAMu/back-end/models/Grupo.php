<?php

    require_once 'AbstractPDO.php';
    require_once 'Aluno.php';

    class Grupo {
        const ID_PREFIX = 'GR';
        const DATASET = '../datasets/grupos.xml';

        public static $db;

        public $idGrupo;
        public $nome;

        public function __construct($id, $nome) {
            if($id=="") {
                $n = Grupo::gerarIdentificador();
                $this->idGrupo = "";
                $this->idGrupo .= Grupo::ID_PREFIX.$n;
            } else {
                $this->idGrupo = $id;
            }
            $this->nome = $nome;     
        }


        // Métodos de classe

        /*
        * Este metodo gera um identificador unico para uma instancia.
        */
        public static function gerarIdentificador() {
            $statement = Grupo::$db->prepare("select max(id) from Grupo;");
            $statement->execute();
            $result = $statement->fetch();
            return $result[0]+1;
        }
        

        /*
        * Este metodo permite obter uma instancia de um Grupo atraves do seu identificador.
        */
        public static function getGrupo($idGrupo) {
            $statement = Grupo::$db->prepare("select * from Grupo where idGrupo='".$idGrupo."';");
            $statement->execute();
            $row = $statement->fetch(); // Converter $row num array standard

            $g = new Grupo($row[1], $row[2]);

            return $g;
        }

        /*
        * Este metodo permite obter uma instancia de um Grupo atraves da sua chave primaria
        */
        public static function getGrupoPelaChavePrimaria($idGrupo) {
            $statement = Grupo::$db->prepare("select * from Grupo where id=".$idGrupo.";");
            $statement->execute();
            $row = $statement->fetch(); // Converter $row num array standard

            $g = new Grupo($row[1], $row[2]);

            return $g;
        }

        /*
        * Este metodo permite obter identificador do grupo através da sua chave primaria.
        */
        public static function getIdentificadorDoGrupoPelaChavePrimaria($id) {
            $statement = Grupo::$db->prepare("select idGrupo from Grupo where idGrupo=".$id.";");
            $statement->execute();
            $row = $statement->fetch(); // Converter $row num array standard

            return $row[0];
        }

        /*
        * Este metodo permite obter a chave primaria do grupo atraves do seu identificador.
        */
        public static function getChavePrimariaPeloIdentificador($idGrupo) {
            $statement = Grupo::$db->prepare("select id from Grupo where idGrupo='".$idGrupo."';");
            $statement->execute();
            $row = $statement->fetch(); // Converter $row num array standard

            return $row[0];
        }

        /*
        * Obter valor do total de grupos na base de dados.
        */
        public static function getTotalGrupos() {
            // Usar prepared statements para evitar SQL Injection
            $statement = Grupo::$db->prepare("select count(*) from Grupo;");
            $statement->execute();
            $result = $statement->fetch(); // Converter $row num array standard
            return $result[0];
        }

        /*
        * Apagar um Grupo pelo seu identificador.
        */
        public static function delete($idGrupo) {
            $statement = Grupo::$db->prepare("delete from Grupo where idGrupo='".$idGrupo."';");
            $statement->execute();
        }

        /*
        * Metodo que permite associar um dado aluno a um determinado grupo, ambos
        * passados como parametro.
        */
        public static function associarAlunoAoGrupo($idAluno, $idGrupo) {
            $alunoPK = Aluno::getChavePrimariaDoAlunoPeloIdentificador($idAluno);
            $grupoPK = Grupo::getChavePrimariaPeloIdentificador($idGrupo);

            $query = "insert into AlunoGrupo(idAluno, idGrupo) values(?, ?);";
            $statement = Grupo::$db->prepare($query);
            $statement->bindParam(1, $alunoPK);
            $statement->bindParam(2, $grupoPK);
            
            $statement->execute();
        }

        /*
        * Metodo que permite dissociar um dado aluno de um determinado grupo, ambos
        * passados como parametro.
        */
        public static function dissociarAlunoDoGrupo($idAluno, $idGrupo) {
            $alunoPK = Aluno::getChavePrimariaDoAlunoPeloIdentificador($idAluno);
            $grupoPK = Grupo::getChavePrimariaPeloIdentificador($idGrupo);

            $query = "delete from AlunoGrupo where idAluno=".$alunoPK." and idGrupo=".$grupoPK.";";
            $statement = Grupo::$db->prepare($query);
            $statement->execute();
        }

        /*
        * Migrar dados de um data set em XML com diversos grupos para
        * uma base de dados relacional MySQL.
        */
        public static function migrateXMLToMySQL() {
            
            $grupos = simplexml_load_file(Grupo::DATASET);
            $grp =  $grupos->xpath("//grupo");           
          

            foreach ($grp as $gr) {
                   
                $nome = $gr->nome;
                $id = (string)$gr[@id];
                $novogrupo = new Grupo($id, $nome);
                $novogrupo->insert();
            }
        }

        /*----------------------------------------------------------------------
            Metodos que geram HTML
        ----------------------------------------------------------------------*/

        /*
        * Metodo que gera todo o catalogo de grupos.
        */
        public static function criarCatalogoDeGrupos() {
            echo "\t<div class='box-body no-padding' style='margin: 2em;'>\n";
            echo "\t\t <table class='table table-striped'>\n";
            echo "\t\t\t<tbody><tr>\n";
            echo "\t\t\t\t<th>ID</th>\n";
            echo "\t\t\t\t<th>Nome</th>\n";
            
            echo "\t\t\t\t<th>Acções</th>\n";
            echo "\t\t\t</tr>\n";
            foreach(Grupo::$db->query('select * from Grupo order by nome;') as $row) {
                $g = new Grupo($row[1], $row[2]);
                
                echo "\t\t\t<tr>\n";
                echo "\t\t\t\t<td>".$g->idGrupo."</td>\n";
                echo "\t\t\t\t<td>".$g->nome."</td>\n";
               
                echo "\t\t\t\t<td> <a href='consult_grupo.html?idGrupo=".$g->idGrupo."'><i class='fa fa-eye' style='margin-right: 2px;'></i></a>
                    <a href='edit_grupo.html?idGrupo=".$g->idGrupo."'><i class='fa fa-edit'></i></a>
                   ";
            }
                echo "\t\t\t</tr>\n";

            echo "\t\t\t</tbody>\n";
            echo "\t\t</table>\n";
            echo "\t</div>\n";
           
        }

        /*
        * Metodo que gera pagina do grupo.
        */
        public static function criarPaginaDoGrupo($idGrupo) {
            $g = Grupo::getGrupo($idGrupo);

            echo "\t\t<a href='list_grupos.html'><i class='fa fa-arrow-left'></i>  Voltar ao catálogo</a>\n";
            echo "\t\t<div class='box box-solid' style='margin-top: 1em;'>\n";
            echo "\t\t\t<div class='box-header with-border'>\n";
            echo "\t\t\t\t<h3 id='nome1' style='font: bold;'>".$g->nome."</h3>\n";
            
            
            echo "\t\t\t\t<a href='edit_grupo.html?idGrupo=".$g->idGrupo."'><i class='fa fa-pencil-square-o'></i> Editar nome do grupo</a>\n";
            echo "\t\t\t\t<hr/>\n";

            // Listar alunos que pertencem a este grupo
            echo "\t\t\t\t<h4>Listagem de alunos pertencentes ao grupo</h4>\n";
            echo "\t\t\t\t<div>\n";

            $statement = Grupo::$db->prepare("select id from Grupo where idGrupo='".$g->idGrupo."';");
            $statement->execute();
            $pkGrupo = $statement->fetch();

            Aluno::criarTabelaDeAlunosDoGrupo($pkGrupo[0]);

            echo "\t\t\t\t<a href='add_aluno_grupo.html?idGrupo=".$g->idGrupo."' style='font-size: 16px;'><i class='fa fa-user-plus' style='font-size: 22px;'></i>   Adicionar aluno ao grupo</a>\n";
            echo "\t\t\t\t</div>\n";
            // -----------------------------------------

            echo "\t\t\t</div>\n";
            echo "\t\t<a class='btn btn-danger' onclick='deleteGrupo()' style='float:right; margin-top: 1em;'>\n";
            echo "\t\t\t<i class='fa fa-trash-o fa-lg'></i> Apagar Grupo\n";
            echo "\t\t</a>\n";
        }

        /*----------------------------------------------------------------------*/
        /*----------------------------------------------------------------------*/
    

        // Métodos de instância


        /*
        * Inserir uma instancia de Grupo na base de dados.
        */
        public function insert() {
            $query = "insert into Grupo(idGrupo, nome) values(?, ?);";
            $statement = Grupo::$db->prepare($query);
            $statement->bindParam(1, $this->idGrupo);
            $statement->bindParam(2, $this->nome);
            
            
           
            $statement->execute();
        }

        /*
        * Metodo que permite atualizar os dados de um Grupo consoante os campos alterados.
        */
        public function update($atributos) {
            $query = "update Grupo set ";

            // Inserir na query os atributos modificados
            // !Atencao aos tipos de dados no caso do Grupo sao todos strings senao tem de se fazer
            // cast para verificar se atributo leva ou nao aspas
            foreach ($atributos as $atr) {
                $query.=$atr."='".$this->$atr."',";
            }
            $query = rtrim($query, ","); // Remover ultima virgula que fica na string $query

            $query.=" where idGrupo='".$this->idGrupo."';";

            $statement = Grupo::$db->prepare($query);
            $statement->execute();
        }

        /*
        * Criar string com toda a informacao de um Grupo, esta string esta pronta a ser
        * introduzida numa lista html <ul>.
        */
        public function li() {
            return "<li><b>ID</b>: ".$this->idGrupo.", <b>Nome</b>: ".$this->nome."</li>";
        }

    }
    // Instanciar variavel de acesso a base de dados apenas uma vez, guardar interface de acesso
    // em variavel de classe
    Grupo::$db = AbstractPDO::getInstance();

?>
