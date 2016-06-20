INSERT INTO Voluntarios
	(Nr, Nome, DataNascimento, Localidade, Rua, CodigoPostal, Habilitacoes, Telemovel, Telefone, Profissao, Email, Obs, DataInicioVoluntariado, Equipa)
	VALUES
		(1,"Afonso Rodrigues", '1990-05-25', "Porto","Rua Dr. Gomes dos Santos", "3712-202", "12º Ano", "919 288 999", "223 405 800", "Padeiro", "arod@sapo.pt", "Sem Obs", '2014-03-02', null),
		(2,"Filipe Mena", '1980-05-09', "Lisboa", "Rua Filipe Cruz", "1500-400", "Licenciatura em Ciências da Comunicação", "910 589 347", "213 300 800", "Jornalista", "mena@tvi.pt", "Sem Obs", '2014-10-01', null),
		(3,"Cristina Lopes", '1985-05-15',"Póvoa de Lanhoso","Avenida da Liberdade", "2510-305", "Mestrado em Gestão Desportiva - Licenciatura em Desporto", "920 384 713", "253 000 800", "Especialista em alto rendimento", "cl@fcdup.pt", "Sem Obs", '2014-02-02', null),
		(4,"Sara Filipa", '1980-02-11', "Esmeriz","Rua 25 de Abril", "4222-50", "4º Ano", "962493000", "223 405 011", "Mecânica", "Sem e-mail", "Sem Obs", '2014-02-10', null),
		(5,"Josefino Fernandes", '1950-03-23',"Faro","Avenida 31 de Janeiro", "3405-102", "Doutoramento em Análise do Movimento Migratório dos Javalis do Malawi", "Sem telemóvel", "223405800", "Biólogo", "Sem e-mail", "Sem Obs", '2013-02-02', null),
		(6,"Roberto Carlos", '1975-04-16',  "São Paulo", "Largo da Igreja","45200-05", "Sem escolaridade", "Sem telemóvel", "11 234 3333", "Comentador Desportivo", "Sem e-mail", "Sem Obs", '2014-04-13', null),
		(7,"Anderson Talisca", '1994-08-20', "Baía","Rua do Campo Jacinto", "150000-02", "Sem escolaridade", "Sem telemóvel", "Sem telefone", "Jogador de Futebol","manoTalisca@hotmail.com", "Sem Obs", '2014-09-15', null),
		(8,"Lula da Silva", '1960-01-01', "Brasília", "Avenida Filipe Menezes" "Belo Horizonte","12323 232", "Mestrado em Direito Político", "Sem telefone", "11 2323 232", "Político", "lulu@yahoo.com.br", "Sem Obs", '2014-07-25', null),
		(9,"Alex Sandro", '1986-12-22',  "Manaus", "Rua Luís Filipe Scolari","3712322-202", "Sem escolaridade", "912 233 933", "221 465 650", "Jogador de futebol", "alex85@gmail.com", "Sem Obs", '2014-08-12', null),
		(10,"Júlio César", '1990-05-23', "Rio de Janeiro","Largo da Vinha",  "3723212-202", "12º Ano", "912 323 222", "222 000 440", "Jogador de futebol", "juliocesar@gmail.com", "Sem Obs", '2014-08-25', null),
        (11, "Paul Pogba", '1993-03-15', "Turim", "Via Bisalta 11", "10121-10156", "12º Ano", "Sem telemóvel", "011-4421111", "Jogador de Futebol", "pp@yahoo.com", "Sem obs", '2013-12-01', null),
        (12, "Napoléon Bonaparte", '1769-04-15', "Santa Helena", null, null, null, null, null, "Imperador", "Sem e-mail", "Sem Obs", '2014-02-01', null),
        (13, "Zinedine Zidane", '1973-06-23', "Madrid", "Calle Valencia", "28012","12º Ano", null, "915 393 282", "Treinador de Futebol","zizou@yandex.com", "Sem Obs", '2014-01-01', null),
        (14, "Charle de Gaulle", '1890-11-20', "Paris",  "Avenue Jean-Lolive", "93500", null, null, null, "General", null, "Sem Obs", '2014-03-01', null),
        (15, "René Descartes", '1596-03-31', "Amesterdão", "Apollolaan 138", null, null, null, null, "Filósofo", null, "Sem Obs", '2014-02-01', null),
        (16, "Yakuzi Suzuka", '1977-02-01', "Tóquio", "Rua Chome-4-13 Kamiikebukuro", "171-8505", "Mestrado em Engenharia Aerodinâmica", null, null, "Engenheiro de Aerodinâmica", "Sem e-mail", "Sem obs", '2013-09-01', null),
        (17, "Shikaku Kaizo", '1985-01-01', "Quioto", "Rua Hasaki-3-2 Koora", "170-8582", "Licenciatura em Arquitetura", "75-712-1111", null,"Arquiteto", "shikka@yahoo.com", "Sem Obs", '2014-03-01', null),
        (18, "Kiriba Fernandes", '1990-07-23', "Braga", "Rua Ernesto Korrodi", "4715-300", "12º Ano", "913 232 123", "253 232 111", "Desempregado", null, "Sem Obs", '2014-02-23', null);
        

INSERT INTO Equipa 
	(Id,Nome, PaisOrigem, Obs, Chefe)
		VALUES
			(1, "Equipa CR7", "Portugal", "Sem Obs", 2),
            (2,"Tropa de Elite", "Brazil", "Sem Obs", 7),
            (3, "Les Bleus", "França", "Sem Obs", 14),
            (4, "Samurai Team", "Japão", "Sem Obs", 16);
            
INSERT INTO Linguas
	(Id, Nome, Voluntario)
		VALUES 
			(1,"Inglês", 1),
            (2,"Latim", 2),
            (3,"Francês", 2),
            (4,"Esperanto", 4),
            (5,"Alemão", 6),
            (6,"Mongol", 7),
			(7,"Coreano", 8),
            (8,"Francês", 11),
            (9,"Italiano", 11),
            (10,"Francês", 12),
            (11,"Francês", 13),
            (12,"Espanhol", 13),
            (13,"Francês", 14),
            (14,"Francês", 15),
            (15,"Japonês", 16),
            (16,"Japonês", 17),
            (17,"Japonês", 18),
            (18,"Português", 18);
            
INSERT INTO Funcionarios
	(Id, Nome, Comissao)
		VALUES 
			(1,"Jorge Daniel C. A. Caldas", "Comissão de construção"),
            (2, "Henrique Firmino", "Comissão de Famílias"),
            (3, "Luís Fábio", "Comissão de Angariação de Fundos"),
            (4, "José Lucas", "Comissão de Famílias"),
            (5, "Luísa José", "Comissão de Obras");
            




            
            

INSERT INTO Eventos
	(Nr, Nome, NrPessoas, DataRealizacao, Notas)
		VALUES 
			(1, "Gala I Habitat - Palácio de Cristal", 150, '2014-01-01', "Muito guito, sim senhor."),
            (2, "Gala II Habitat - Torre Belém", 500, '2014-07-07', "Sem observações.");

INSERT INTO Donativo
	(NrRecibo, Obs, DataEmissao, Evento, Valor, NomeMaterial, Quantidade, NomeServico)
		VALUES
			(1, "Donativo do Rei da Noruega",'2014-01-07' ,1, 50000, null,null,null),
            (2, "Donativo do Duque de Bragança",'2014-01-10',1, 75500, null,null,null),
            (3, "Mão-de-obra do Vietnam", '2014-02-10',1, null, null, null, "Hanoi WorkForce Ltd."),
            (4, "Donativo da Associação Nacional de Patinagem no Gelo", '2014-02-01', null, 25000, null, null, null),
            (5, "Donativo do Mike Tyson", '2014-07-30', 2, 30000,null,null,null);
            
INSERT INTO Doadores
	(Id, NIF, Nome, Telefone, Telemovel, Localidade, Rua, CodigoPostal, Obs, PessoaContato, Email, Website, Tipo)
		VALUES 
			(1, 123423, "Carlos XII, o Gordo", "Sem telefone","Sem telemovel", "Oslo", "Desconhecido", "Desconhecido", "Rei da Noruega.", "Desconhecido", "Desconhecido", "Desconhecido", "Parceria"),
            (2, 15002311, "Miguel Sancho", "912 888 999", "255 004 232", "Bragança", "Avenida da Alameda", "3333-222", "Duque de Bragança.", "912 888 999",null,null, "Pontual"),
            (3, 123111, "Hanoi WorkForce Ltd.", null, null, "Hanoi", "City Wok Av.", "Desconhecido", "Sem observações", null, "Desconhecido", "Desconhecido", "Pontual"),
            (4, 1223, "Associação Nacional de Patinagem no Gelo", "912 323 222", "221 223 221", "Lisboa", "Parque Eduardo VII", "1500-220", "Sem observações", "912 323 222", "anpg@sapo.pt", "www.anpg.pt", "Pontual"),
            (5, 155232232, "Mike Tyson", null, null, null, null, null, null, null, null,null, "Pontual");
            
            
INSERT INTO Tarefas
	(Id, Designacao, Descricao, DataInicio, DataFinal)
		VALUES 
			(1, "Projectar colunas da casa", "Criar as colunas que irão servir de suporte à casa", '2014-02-02', '2014-02-09'),
			(2, "Estruturar os pisos da casa", "Sem Descrição", '2014-02-10', '2014-02-14'),
            (3, "Preencher a casa com tijolos e betão", "Sem descrição" ,'2014-02-16', '2014-02-23'),
            (4, "Pintar a casa", "Pintura total da casa utilizando tintas do LIDL", '2014-02-24', '2014-03-02'),
            (5, "Completar os lados interiores da casa", "Preencher o interior da casa (paredes e tecto) com soalho", '2014-03-06', '2014-03-19'),
            (6, "Mobilar a casa", "Sem descrição", '2014-03-23', '2014-03-31'),
            (7, "Envernizar o chão", "Sem descrição", '2014-04-05', '2014-04-25'),
            (8, "Demolir o piso", "Demolir o piso superior da casa", '2014-08-10', '2014-08-30'),
            (9, "Reestruturar o piso", "Sem descrição", '2014-09-15', null);

            
INSERT INTO Material
	(Id, Nome, Descricao, Quantidade)
		VALUES		
			(1, "Cimento", "Cimento categoria 3 da CimenPOR", 2000),
            (2, "Betão Concreto", "Sem descrição", 1000),
            (3, "Tijolo", "Sem descrição", 500),
            (4, "Argamassa", "Sem descrição", 50),
            (5, "Barro", "Sem descrição", 1500),
            (6, "Metal", "Sem descrição", 500),
            (7, "Madeira", "Sem descrição", 600);
            
INSERT INTO TarefasMaterial
	(Tarefa, Material, QuantidadeGasta)
		VALUES
			(1, 1, 100),
            (1, 2, 50),
            (2, 1, 50),
            (3, 2, 200),
            (3, 3, 150),
            (5, 7, 150),
            (9, 4, 150),
            (9, 6, 250);
            
            
INSERT INTO Representante
	(Nr, Nome, DataNascimento, EstadoCivil, Profissao, Localidade, Rua, CodigoPostal, Naturalidade, Nacionalidade, Escolaridade, Telefone, Telemovel, RendimentoAgregado)
		VALUES
			(1, "Joel Filipe", '1975-05-29', "Casado", "Pintor", "Braga", "Avenida 25 de Fevereiro", "4715-300", "Kingstown", "Etíope", "12º Ano","Sem telefone", "912 222 2222", 700),
			(2,"Gabriel Santana", '1990-02-02', "Casado", "Pescador", "Póvoa de Lanhoso", "Rua da República do Congo", "3440-220", "Porto", "Portuguesa", "4º Ano", "Sem telefone","911 232 222", 550),
			(3,"Joana Lopo", '1985-01-02', "Casada", "Professora", "Santo Tirso", "Lugar da Igreja", "4700-200", "Braga", "Portuguesa", "Licenciatura em Educação", "253 233 211", "962 222 231", 1000);
            

            
INSERT INTO Projetos
	(Nr,Orcamento, ValorFinal, Prestacao, DataInicio, DataEncerramento, Obs, Estado, FuncionarioEncerrou, FuncionarioRegistou)
		VALUES
			(1, 100000, 150000, 15000, '2014-02-01', '2014-05-02', "Correu tudo conforme o planeado", "Terminado", 1, 1),
			(2, 30000, null, 1500, '2014-07-09', null, "Cambada de preguiçosos", "Em construção", null, 1);
            
INSERT INTO Candidaturas
	(Nr, Descricao, DataDecisao, FuncionarioAprovou, FuncionarioRegistou, Representante,Projeto, Estado)
		VALUES
			(1, "Sem Descrição", '2014-01-20', 2, 2, 1, 1, "Aprovada"),
			(2, "Sem Descrição", null, null, 2, 2, null, "Rejeitada"),
			(3, "Sem Descrição", '2014-07-20', 2, 2, 3, 2, "Aprovada");
            

            
INSERT INTO ProjetosVoluntarios
	(Projeto, Voluntario, HorasVoluntariado)
		VALUES 	
			(1, 1, 100),
            (1, 2, 150),
            (1, 3, 25),
            (1, 4, 56),
            (1, 5, 66),
            (2, 4, 13),
            (2, 3, 23),
            (2, 2, 150),
            (2, 8, 2),
            (2, 9, 35),
            (2, 10, 2),
            (2, 11, 15),
            (2, 12, 60),
            (2, 13, 55),
            (2, 14, 133),
            (2, 15, 5),
            (2, 16, 3),
            (2, 17, 8),
            (2, 18, 13);
            
            
INSERT INTO ProjetoDoadoresDonativos
	(Donativo,Projeto,Doador)
		VALUES
			(1, 1, 1),
            (2, 1, 2),
            (3, 1, 3),
            (4, 1, 4),
            (5, 2, 5);
            
            
INSERT INTO TarefasFuncionariosProjetos
	(Tarefa, Projeto, FuncionarioRegistou)
		VALUES 
			(1,1,1),
            (2,1,1),
            (3,1,5),
            (4,1,5),
            (5,1,5),
            (6,1,1),
            (7,1,5),
            (8,2,1),
            (9,2,1);
		
        
INSERT INTO Membros
	(Id, Nome, Parentesco, EstadoCivil, Escolaridade, DataNascimento, Profissao, Candidatura)
		VALUES
			(1, "Filomena Fernandes", "Esposa", "Casada", "12º Ano", '1980-05-06', "Desempregada", 1),
            (2, "Armindo de Jesus", "Filho", "Solteiro", "12º Ano", '1996-02-03', "Estudante", 1),
            (3, "Bob Construtor", "Marido", "Casado", "12º Ano", '1986-03-02', "Carpinteiro", 3),
            (4, "Capitão Moura", "Filho", "Solteiro", "4º Ano", '2006-02-01', "Estudante", 3);


             
            
            
UPDATE Voluntarios
	SET Equipa = 1
    WHERE Nr < 6;
    
UPDATE Voluntarios
	SET Equipa = 2
    WHERE Nr>6;
    
UPDATE Voluntarios
	SET Equipa = 3	
		Where Nr BETWEEN 11 AND 15;
        
UPDATE VOLUNTARIOS
	SET Equipa = 4
		WHERE Nr>15;
 
    
    

