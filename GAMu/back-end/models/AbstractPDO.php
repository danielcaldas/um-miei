<?php
    class AbstractPDO {
        public static function getInstance() {
            // Mudar os atributos em baixo em função da base de dados criada
            $user="root";
            $pass="root";
            $dbname="gamudb";
            try {
                $db = new PDO('mysql:host=localhost;dbname='.$dbname, $user, $pass);
                $db->setAttribute(PDO::MYSQL_ATTR_INIT_COMMAND, "SET NAMES 'utf8'");
                return $db;
            } catch(PDOException $e) {
                echo "Server Error!: ".$e->getMessage()."\n";
                die();
            }
        }
    }
?>