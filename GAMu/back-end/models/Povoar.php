<?php

	// Ler tabela cujo nome vem como argumento no comando
    $tabela=$argv[1];
    require_once "Aluno.php";
    require_once "Atuacao.php";
    require_once "Audicao.php";
    require_once "Compositor.php";
	require_once "Curso.php";
    require_once "Grupo.php";
    require_once "Instrumento.php";
    require_once "Obra.php";
    require_once "Partitura.php";
    require_once "Professor.php";

    switch($tabela) {
        case "Aluno":
            echo "\nA povoar tabela Aluno . . .\n";
            Aluno::migrateXMLToMySQL();
            break;
        case "Atuacao":
            echo "\nA povoar tabela Atuacao . . .\n";
            Atuacao::migrateXMLToMySQL();
            break;
        case "Audicao":
            echo "\nA povoar tabela Audicao . . .\n";
            Audicao::migrateXMLToMySQL();
            break;
        case "Compositor":
            echo "\nA povoar tabela Compositor . . .\n";
            Compositor::migrateXMLToMySQL();
            break;
        case "Curso":
            echo "\nA povoar tabela Curso . . .\n";
            Curso::migrateXMLToMySQL();
            break;
        case "Grupo":
            echo "\nA povoar tabela Grupo . . .\n";
            Grupo::migrateXMLToMySQL();
            break;
        case "Instrumento":
            echo "\nA povoar tabela Instrumento . . .\n";
            Instrumento::migrateXMLToMySQL();
            break;
        case "Obra":
            echo "\nA povoar tabela Obra . . .\n";
            Obra::migrateXMLToMySQL();
            break;
        case "Partitura":
            echo "\nA povoar tabela Partitura . . .\n";
            Partitura::migrateXMLToMySQL();
            break;
        case "Professor":
            echo "\nA povoar tabela Professor . . .\n";
            Professor::migrateXMLToMySQL();
            break;
        case "ALLDB":
            // -------------------- Repositorio Digital
            echo "\nA povoar tabela Instrumento . . .\n";
            Instrumento::migrateXMLToMySQL();
            echo "\nA povoar tabela Compositor . . .\n";
            Compositor::migrateXMLToMySQL();
            echo "\nA povoar tabela Obra . . .\n";
            Obra::migrateXMLToMySQL();
            echo "\nA povoar tabela Partitura . . .\n";
            Partitura::migrateXMLToMySQL();

            // -------------------- Recursos Humanos
            echo "\nA povoar tabela Grupo . . .\n";
            Grupo::migrateXMLToMySQL();
            echo "\nA povoar tabela Professor . . .\n";
            Professor::migrateXMLToMySQL();
            echo "\nA povoar tabela Curso . . .\n";
            Curso::migrateXMLToMySQL();
            echo "\nA povoar tabela Aluno . . .\n";
            Aluno::migrateXMLToMySQL();

            // -------------------- Audicoes
            echo "\nA povoar tabela Audicao . . .\n";
            Audicao::migrateXMLToMySQL();
            echo "\nA povoar tabela Atuacao . . .\n";
            Atuacao::migrateXMLToMySQL();

            break;
            
    	default:
    		echo "\nAlgo correu mal ou a tabela [".$tabela."] nao existe.\n";
    		break;
    }

    echo "\nend.\n\n";
?>
