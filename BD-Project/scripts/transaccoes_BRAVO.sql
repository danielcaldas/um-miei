/A* -------------------------------Donativos--------------------------------*/
/* Inserir um donativo */
SELECT * FROM Donativo;
SELECT * FROM Doadores;
SELECT * FROM ProjetoDoadoresDonativos;

CALL tr_InsDonativo (6, "1 eurito do myke", '2014-09-09', null, 1, null, null, null, 2, 5);
DROP PROCEDURE tr_InsDonativo;


DELIMITER $$
CREATE PROCEDURE tr_InsDonativo (IN rec INT, IN obs VARCHAR (145), IN dataE DATE, IN e INT, IN v DECIMAL (10,2),
								 IN nomeM VARCHAR(75), IN quant INT, IN nomeS VARCHAR(75), IN proj INT, IN doador INT)
                                 
BEGIN
	
    DECLARE Erro BOOL DEFAULT 0;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET Erro = 1;
    SET autocommit=0;
    
    START TRANSACTION;
    
		INSERT INTO Donativo
			(NrRecibo, Obs, DataEmissao, Evento, Valor, NomeMaterial, Quantidade, NomeServico)
				VALUES 
					(rec, obs, dataE, e, v, nomeM, quant, nomeS);
                    
		IF Erro THEN
			ROLLBACK;
		END IF;
                    

		INSERT INTO ProjetoDoadoresDonativos
			(Donativo, Projeto, Doador)
				VALUES 
					(rec, proj, doador);
                    
		IF Erro THEN
			ROLLBACK;
		ELSE 
			COMMIT;
		END IF;
                    
END;








/* Inserir um donativo material */

SELECT * FROM Material;
SELECT * FROM Donativo;
SELECT * FROM ProjetoDoadoresDonativos;
SELECT * FROM TarefasMaterial;
    
CALL tr_InsDonativoMaterial (7, "50 kg de argamassa", '2014-10-10', null, null, "Argamassa", 50, null, 2, 5,4);
DROP PROCEDURE tr_InsDonativoMaterial;
    
DELIMITER $$
CREATE PROCEDURE tr_InsDonativoMaterial (IN rec INT, IN obs VARCHAR (145), IN dataE DATE, IN e INT, IN v DECIMAL (10,2),
								 IN nomeM VARCHAR(75), IN quant INT, IN nomeS VARCHAR(75), IN proj INT, IN doador INT, IN idMaterial INT)
                                 
BEGIN
	
    DECLARE Erro BOOL DEFAULT 0;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET Erro = 1;
    SET autocommit=0;
    
    START TRANSACTION;
    
		INSERT INTO Donativo
			(NrRecibo, Obs, DataEmissao, Evento, Valor, NomeMaterial, Quantidade, NomeServico)
				VALUES 
					(rec, obs, dataE, e, v, nomeM, quant, nomeS);
				
		IF Erro THEN
			ROLLBACK;
		END IF;

		INSERT INTO ProjetoDoadoresDonativos
			(Donativo, Projeto, Doador)
				VALUES 
					(rec, proj, doador);
                    
		IF Erro THEN
			ROLLBACK;
		END IF;
        
        UPDATE Material
			SET Quantidade = Quantidade + quant
            WHERE Id = idMaterial;
            
		IF Erro THEN
			ROLLBACK;
		ELSE 
			COMMIT;
		END IF;
                    
END;



/*-------------------------------PROJETOS--------------------------------------*/

/*Inserir Tarefa*/
SELECT * 
	FROM Tarefas;
    
SELECT * 
	FROM TarefasFuncionariosProjetos;
    
CALL tr_InsTarefa (10, "Montar Casa do Cão", "Sem descrição", '2014-10-15', null, 2, 5);

DELIMITER $$
CREATE PROCEDURE tr_InsTarefa (IN idTarefa INT,IN desig VARCHAR(75), IN descr VARCHAR(75), IN dataI DATE, IN dataF DATE
								, IN Proj INT, IN FuncR INT)
                                 
BEGIN
	
    DECLARE Erro BOOL DEFAULT 0;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET Erro = 1;
    SET autocommit=0;
    
    START TRANSACTION;
    
		INSERT INTO Tarefas
			(Id, Designacao, Descricao, DataInicio, DataFinal)
				VALUES 
					(idTarefa,desig,descr,dataI,dataF);
                    
		IF Erro THEN
			ROLLBACK;
		END IF;
                    

		INSERT INTO TarefasFuncionariosProjetos
			(Tarefa, Projeto, FuncionarioRegistou)
				VALUES 
					(idTarefa, proj, funcR);
                    
		IF Erro THEN
			ROLLBACK;
		ELSE 
			COMMIT;
		END IF;
                    
END;






/*---------------------------------Stock----------------------------------------*/

SELECT *
	FROM Tarefas;
    
SELECT * 
	FROM Material;
    
SELECT * 
	FROM TarefasMaterial;
    
CALL tr_FecharTarefa (10, 4, 50);

/*Fechar Tarefa*/

DELIMITER $$
CREATE PROCEDURE tr_FecharTarefa (IN idTarefa INT, IN idMaterial INT, IN quant INT)
                                 
BEGIN
	
    DECLARE Erro BOOL DEFAULT 0;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET Erro = 1;
    SET autocommit=0;
    
    START TRANSACTION;
    
		INSERT INTO TarefasMaterial
			(Tarefa, Material, QuantidadeGasta)
				VALUES 
					(idTarefa, idMaterial, quant);
                    
		IF Erro THEN
			ROLLBACK;
		END IF;
                    

		UPDATE Material
			SET Quantidade = Quantidade - quant
            WHERE Id = idMaterial;
                    
		IF Erro THEN
			ROLLBACK;
		END IF;
        
        UPDATE Tarefas
			SET  DataFinal = CURRENT_DATE()
            WHERE Id = idTarefa;
            
		IF Erro THEN
			ROLLBACK;
		ELSE 
			COMMIT;
		END IF;
                    
END;

