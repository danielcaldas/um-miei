/*1. Quem foram os 5 doadores que mais contribuíram em 2014?*/

SELECT DR.Nome, SUM(DN.Valor) AS TotalValor
	FROM ProjetoDoadoresDonativos as PDD
		INNER JOIN (
					SELECT NrRecibo, Valor
                    FROM Donativo
                    WHERE Valor IS NOT NULL AND YEAR(DataEmissao) = 2014
                    ) as DN
		ON PDD.Donativo = DN.NrRecibo
			INNER JOIN (
						SELECT Id, Nome
                        FROM Doadores 
                        ) as DR
			ON PDD.Doador = DR.Id
            GROUP BY (Doador)
            ORDER BY DN.Valor DESC;
            
/*2.Quais os 3 projectos que mais excederam o orçamento inicial em 2014?*/

SELECT Nr as Projeto, (ValorFinal*100/Orcamento)-100 as Percentagem
	FROM Projetos
    WHERE YEAR(DataEncerramento) = 2014
    LIMIT 3;
    
/*3.Quais foram os voluntários que mais trabalharam em 2014?*/

SELECT VO.Nr, Nome, SUM(HorasVoluntariado) AS TotalHoras
	FROM (
			SELECT Nr
            FROM Projetos
            WHERE YEAR(DataInicio) = 2014
            ) as PO
		INNER JOIN ProjetosVoluntarios as PV
        ON PO.Nr = PV.Projeto
			INNER JOIN (
						SELECT Nr, Nome
                        FROM Voluntarios
                        ) as VO
			ON PV.Voluntario = VO.NR
            GROUP BY (Voluntario)
            ORDER BY SUM(HorasVoluntariado) DESC;
            
/*4. Quais são os materiais mais utilizados?*/

SELECT Id, Nome, SUM(QuantidadeGasta) AS TotalGasto, Quantidade AS Stock
	FROM (
			SELECT Id, Nome, Quantidade
            FROM Material
		  ) as MT
		INNER JOIN TarefasMaterial as TM
        ON MT.Id = TM.Material
			GROUP BY (Id)
            ORDER BY TotalGasto DESC;
            
/*6. Mostre, para cada projecto, o dinheiro angariado em eventos.*/

SELECT Nr, SUM(Valor) AS ValorEvento
	FROM (
			SELECT Nr
            FROM Projetos
            ) as PO
		INNER JOIN ProjetoDoadoresDonativos as PDD
        ON PO.Nr = PDD.Projeto
			INNER JOIN (
						SELECT NrRecibo, Evento, Valor
                        FROM Donativo
                        WHERE Evento IS NOT NULL
                        ) as DN
			ON PDD.Donativo = DN.NrRecibo
				GROUP BY (Nr);
          
          
/*8a). Qual é a média de donativos monetários e o valor médio doado em todos os projetos?*/

SELECT (COUNT(Donativo)/COUNT(DISTINCT Projeto)) AS MediaDonativo, AVG(Valor) as MediaValor
	FROM (
		SELECT Nr
        FROM Projetos
        ) as PO
		INNER JOIN  ProjetoDoadoresDonativos as PDD
        ON Po.Nr = PDD.Projeto
			INNER JOIN (
						SELECT NrRecibo, Valor
                        FROM Donativo
                        WHERE Valor >0
                        ) AS DN
			ON PDD.Donativo = DN.NrRecibo;
            
/*8b). Qual é a média de donativos monetários e o valor médio doado por projeto?*/

SELECT Nr, (COUNT(Donativo)/COUNT(DISTINCT Projeto)) AS MediaDonativo, AVG(Valor) as MediaValor
	FROM (
		SELECT Nr
        FROM Projetos
        ) as PO
		INNER JOIN  ProjetoDoadoresDonativos as PDD
        ON Po.Nr = PDD.Projeto
			INNER JOIN (
						SELECT NrRecibo, Valor
                        FROM Donativo
                        WHERE Valor >0
                        ) AS DN
			ON PDD.Donativo = DN.NrRecibo
            GROUP BY (Nr);
            
/*9. Em média, são realizadas quantas tarefas por projeto?*/

SELECT (COUNT(Tarefa)/COUNT(DISTINCT Projeto)) as MediaTarefas
	FROM TarefasFuncionariosProjetos;
    
/*10. Quais os 10 doadores que estão há mais tempo sem fazer algum donativo?*/

SELECT DOA.Id, DOA.Nome, DN.DataEmissao
	FROM ProjetoDoadoresDonativos as PDD
    INNER JOIN (
		SELECT NrRecibo, DataEmissao
        FROM Donativo
        ) AS DN
	ON PDD.Donativo = DN.NrRecibo
		INNER JOIN (
				SELECT Id, Nome
                FROM Doadores
                ) AS DOA
		ON PDD.Doador = DOA.Id
        GROUP BY (DOA.Id)
        ORDER BY DataEmissao ASC
        LIMIT 10;
                

