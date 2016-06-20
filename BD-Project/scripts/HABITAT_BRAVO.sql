-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema Habitat
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Habitat
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Habitat` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `Habitat` ;

-- -----------------------------------------------------
-- Table `Habitat`.`Representante`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Habitat`.`Representante` ;

CREATE TABLE IF NOT EXISTS `Habitat`.`Representante` (
  `Nr` INT NOT NULL,
  `Nome` VARCHAR(75) NOT NULL,
  `DataNascimento` DATE NULL DEFAULT NULL,
  `EstadoCivil` VARCHAR(15) NULL,
  `Profissao` VARCHAR(50) NULL,
  `Localidade` VARCHAR(75) NOT NULL,
  `Rua` VARCHAR(75) NOT NULL,
  `CodigoPostal` VARCHAR(15) NOT NULL,
  `Naturalidade` VARCHAR(30) NULL,
  `Nacionalidade` VARCHAR(30) NULL,
  `Escolaridade` VARCHAR(75) NULL,
  `Telefone` VARCHAR(20) NULL,
  `Telemovel` VARCHAR(20) NULL,
  `RendimentoAgregado` DECIMAL(10,2) NULL DEFAULT 0.00,
  PRIMARY KEY (`Nr`),
  CHECK (EstadoCivil = 'Casado' OR EstadoCivil = 'Casada' OR EstadoCivil = 'Viúvo' OR EstadoCivil = 'Viúva' OR EstadoCivil = 'Solteiro'
			OR EstadoCivil = 'Solteira' OR EstadoCivil = 'Divorciado' OR EstadoCivil = 'Divorciada'))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Habitat`.`Funcionarios`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Habitat`.`Funcionarios` ;

CREATE TABLE IF NOT EXISTS `Habitat`.`Funcionarios` (
  `Id` INT NOT NULL,
  `Nome` VARCHAR(75) NOT NULL,
  `Comissao` VARCHAR(50) NULL,
  PRIMARY KEY (`Id`),
  CHECK (Comissao = 'Famílias' OR Comissao='Construção' OR Comissao ='Angariação de Fundos'))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Habitat`.`Projetos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Habitat`.`Projetos` ;

CREATE TABLE IF NOT EXISTS `Habitat`.`Projetos` (
  `Nr` INT NOT NULL,
  `Orcamento` DECIMAL(10,2) NULL DEFAULT 0.00,
  `ValorFinal` DECIMAL(10,2) NULL DEFAULT 0.00,
  `Prestacao` DECIMAL(10,2) NULL DEFAULT 0.00,
  `DataInicio` DATE NULL DEFAULT NULL,
  `DataEncerramento` DATE NULL DEFAULT NULL,
  `Obs` VARCHAR(145) NULL DEFAULT 'Sem observações',
  `Estado` VARCHAR(30) NULL,
  `FuncionarioEncerrou` INT NULL,
  `FuncionarioRegistou` INT NOT NULL,
  PRIMARY KEY (`Nr`),
  INDEX `fk_Projetos_Funcionarios1_idx` (`FuncionarioRegistou` ASC),
  INDEX `fk_Projetos_Funcionarios2_idx` (`FuncionarioEncerrou` ASC),
  CONSTRAINT `fk_Projetos_Funcionarios1`
    FOREIGN KEY (`FuncionarioRegistou`)
    REFERENCES `Habitat`.`Funcionarios` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Projetos_Funcionarios2`
    FOREIGN KEY (`FuncionarioEncerrou`)
    REFERENCES `Habitat`.`Funcionarios` (`Id`)
    ON DELETE SET NULL
    ON UPDATE NO ACTION,
    CHECK (Estado = 'Em construção' OR Estado = 'Finalizado' OR Estado = 'Em espera'))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Habitat`.`Candidaturas`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Habitat`.`Candidaturas` ;

CREATE TABLE IF NOT EXISTS `Habitat`.`Candidaturas` (
  `Nr` INT NOT NULL,
  `Descricao` VARCHAR(145) NULL DEFAULT 'Sem descrição',
  `DataDecisao` DATE NULL DEFAULT NULL,
  `DataSubmissao` DATE NULL DEFAULT NULL,
  `FuncionarioAprovou` INT NULL,
  `FuncionarioRegistou` INT NOT NULL,
  `Representante` INT NOT NULL,
  `Projeto` INT NULL,
  `Estado` VARCHAR(30) NULL,
  PRIMARY KEY (`Nr`),
  INDEX `fk_Candidaturas_Representante1_idx` (`Representante` ASC),
  INDEX `fk_Candidaturas_Funcionarios1_idx` (`FuncionarioAprovou` ASC),
  INDEX `fk_Candidaturas_Funcionarios2_idx` (`FuncionarioRegistou` ASC),
  INDEX `fk_Candidaturas_Projetos1_idx` (`Projeto` ASC),
  CONSTRAINT `fk_Candidaturas_Representante1`
    FOREIGN KEY (`Representante`)
    REFERENCES `Habitat`.`Representante` (`Nr`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Candidaturas_Funcionarios1`
    FOREIGN KEY (`FuncionarioAprovou`)
    REFERENCES `Habitat`.`Funcionarios` (`Id`)
    ON DELETE SET NULL
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Candidaturas_Funcionarios2`
    FOREIGN KEY (`FuncionarioRegistou`)
    REFERENCES `Habitat`.`Funcionarios` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Candidaturas_Projetos1`
    FOREIGN KEY (`Projeto`)
    REFERENCES `Habitat`.`Projetos` (`Nr`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CHECK (Estado = 'Em análise' OR Estado = 'Aprovado' OR Estado = 'Reprovado'))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Habitat`.`Doadores`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Habitat`.`Doadores` ;

CREATE TABLE IF NOT EXISTS `Habitat`.`Doadores` (
  `Id` INT NOT NULL,
  `NIF` INT NULL,
  `Nome` VARCHAR(75) NOT NULL,
  `Telefone` VARCHAR(20) NULL,
  `Telemovel` VARCHAR(20) NULL,
  `Localidade` VARCHAR(75) NULL,
  `Rua` VARCHAR(75) NULL,
  `CodigoPostal` VARCHAR(15) NULL,
  `Obs` VARCHAR(145) NULL DEFAULT 'Sem observações',
  `PessoaContato` VARCHAR(50) NULL,
  `Email` VARCHAR(50) NULL,
  `Website` VARCHAR(75) NULL,
  `Tipo` VARCHAR(15) NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Habitat`.`Eventos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Habitat`.`Eventos` ;

CREATE TABLE IF NOT EXISTS `Habitat`.`Eventos` (
  `Nr` INT NOT NULL,
  `Nome` VARCHAR(75) NULL,
  `NrPessoas` INT NULL,
  `DataRealizacao` DATE NULL DEFAULT NULL,
  `Notas` VARCHAR(145) NULL DEFAULT 'Sem observações',
  PRIMARY KEY (`Nr`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Habitat`.`Donativo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Habitat`.`Donativo` ;

CREATE TABLE IF NOT EXISTS `Habitat`.`Donativo` (
  `NrRecibo` INT NOT NULL,
  `Obs` VARCHAR(145) NULL DEFAULT '\"Sem observações\"',
  `DataEmissao` DATE NULL DEFAULT NULL,
  `Evento` INT NULL,
  `Valor` DECIMAL(10,2) NULL,
  `NomeMaterial` VARCHAR(75) NULL,
  `Quantidade` INT NULL,
  `NomeServico` VARCHAR(75) NULL,
  PRIMARY KEY (`NrRecibo`),
  INDEX `fk_Donativo_Eventos1_idx` (`Evento` ASC),
  CONSTRAINT `fk_Donativo_Eventos1`
    FOREIGN KEY (`Evento`)
    REFERENCES `Habitat`.`Eventos` (`Nr`)
    ON DELETE SET NULL
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Habitat`.`Voluntarios`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Habitat`.`Voluntarios` ;

CREATE TABLE IF NOT EXISTS `Habitat`.`Voluntarios` (
  `Nr` INT NOT NULL,
  `Nome` VARCHAR(75) NOT NULL,
  `DataNascimento` DATE NOT NULL,
  `Localidade` VARCHAR(75) NULL,
  `Rua` VARCHAR(75) NULL,
  `CodigoPostal` VARCHAR(15) NULL,
  `Habilitacoes` VARCHAR(75) NULL,
  `Telemovel` VARCHAR(20) NULL,
  `Telefone` VARCHAR(20) NULL,
  `Profissao` VARCHAR(50) NULL,
  `Email` VARCHAR(50) NULL,
  `Obs` VARCHAR(500) NULL,
  `DataInicioVoluntariado` DATE NULL DEFAULT NULL,
  `Equipa` INT NULL,
  PRIMARY KEY (`Nr`),
  INDEX `fk_Voluntarios_Equipa_idx` (`Equipa` ASC),
  CONSTRAINT `fk_Voluntarios_Equipa`
    FOREIGN KEY (`Equipa`)
    REFERENCES `Habitat`.`Equipa` (`Id`)
    ON DELETE SET NULL
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Habitat`.`Equipa`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Habitat`.`Equipa` ;

CREATE TABLE IF NOT EXISTS `Habitat`.`Equipa` (
  `Id` INT NOT NULL,
  `Nome` VARCHAR(75) NULL,
  `PaisOrigem` VARCHAR(30) NULL,
  `Obs` VARCHAR(145) NULL DEFAULT 'Sem observações',
  `Chefe` INT NULL,
  PRIMARY KEY (`Id`),
  INDEX `fk_Equipa_Voluntarios1_idx` (`Chefe` ASC),
  CONSTRAINT `fk_Equipa_Voluntarios1`
    FOREIGN KEY (`Chefe`)
    REFERENCES `Habitat`.`Voluntarios` (`Nr`)
    ON DELETE SET NULL
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Habitat`.`Material`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Habitat`.`Material` ;

CREATE TABLE IF NOT EXISTS `Habitat`.`Material` (
  `Id` INT NOT NULL,
  `Nome` VARCHAR(75) NOT NULL,
  `Descricao` VARCHAR(75) NULL DEFAULT 'Sem descrição',
  `Quantidade` INT NOT NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Habitat`.`Membros`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Habitat`.`Membros` ;

CREATE TABLE IF NOT EXISTS `Habitat`.`Membros` (
  `Id` INT NOT NULL,
  `Nome` VARCHAR(75) NOT NULL,
  `Parentesco` VARCHAR(15) NULL,
  `EstadoCivil` VARCHAR(15) NULL,
  `Escolaridade` VARCHAR(75) NULL,
  `DataNascimento` DATE NULL DEFAULT NULL,
  `Profissao` VARCHAR(50) NULL,
  `Candidatura` INT NOT NULL,
  PRIMARY KEY (`Id`),
  INDEX `fk_Membros_Candidaturas1_idx` (`Candidatura` ASC),
  CONSTRAINT `fk_Membros_Candidaturas1`
    FOREIGN KEY (`Candidatura`)
    REFERENCES `Habitat`.`Candidaturas` (`Nr`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CHECK (EstadoCivil = 'Casado' OR EstadoCivil = 'Casada' OR EstadoCivil = 'Viúvo' OR EstadoCivil = 'Viúva' OR EstadoCivil = 'Solteiro'
			OR EstadoCivil = 'Solteira' OR EstadoCivil = 'Divorciado' OR EstadoCivil = 'Divorciada'))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Habitat`.`Tarefas`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Habitat`.`Tarefas` ;

CREATE TABLE IF NOT EXISTS `Habitat`.`Tarefas` (
  `Id` INT NOT NULL,
  `Designacao` VARCHAR(75) NOT NULL,
  `Descricao` VARCHAR(145) NULL DEFAULT 'Sem descrição',
  `DataInicio` DATE NULL DEFAULT NULL,
  `DataFinal` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Habitat`.`Linguas`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Habitat`.`Linguas` ;

CREATE TABLE IF NOT EXISTS `Habitat`.`Linguas` (
  `Id` INT NOT NULL,
  `Nome` VARCHAR(30) NOT NULL,
  `Voluntario` INT NOT NULL,
  PRIMARY KEY (`Id`),
  INDEX `fk_Linguas_Voluntarios1_idx` (`Voluntario` ASC),
  CONSTRAINT `fk_Linguas_Voluntarios1`
    FOREIGN KEY (`Voluntario`)
    REFERENCES `Habitat`.`Voluntarios` (`Nr`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Habitat`.`ProjetosVoluntarios`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Habitat`.`ProjetosVoluntarios` ;

CREATE TABLE IF NOT EXISTS `Habitat`.`ProjetosVoluntarios` (
  `Projeto` INT NOT NULL,
  `Voluntario` INT NOT NULL,
  `HorasVoluntariado` INT NOT NULL,
  PRIMARY KEY (`Voluntario`, `Projeto`),
  INDEX `fk_Projetos_has_Voluntarios_Voluntarios1_idx` (`Voluntario` ASC),
  INDEX `fk_Projetos_has_Voluntarios_Projetos1_idx` (`Projeto` ASC),
  CONSTRAINT `fk_Projetos_has_Voluntarios_Projetos1`
    FOREIGN KEY (`Projeto`)
    REFERENCES `Habitat`.`Projetos` (`Nr`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Projetos_has_Voluntarios_Voluntarios1`
    FOREIGN KEY (`Voluntario`)
    REFERENCES `Habitat`.`Voluntarios` (`Nr`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Habitat`.`ProjetoDoadoresDonativos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Habitat`.`ProjetoDoadoresDonativos` ;

CREATE TABLE IF NOT EXISTS `Habitat`.`ProjetoDoadoresDonativos` (
  `Donativo` INT NOT NULL,
  `Projeto` INT NOT NULL,
  `Doador` INT NOT NULL,
  PRIMARY KEY (`Donativo`, `Projeto`, `Doador`),
  INDEX `fk_Donativo_has_Projetos_Projetos1_idx` (`Projeto` ASC),
  INDEX `fk_Donativo_has_Projetos_Donativo1_idx` (`Donativo` ASC),
  INDEX `fk_DonativoProjetos_Doadores1_idx` (`Doador` ASC),
  CONSTRAINT `fk_Donativo_has_Projetos_Donativo1`
    FOREIGN KEY (`Donativo`)
    REFERENCES `Habitat`.`Donativo` (`NrRecibo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Donativo_has_Projetos_Projetos1`
    FOREIGN KEY (`Projeto`)
    REFERENCES `Habitat`.`Projetos` (`Nr`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_DonativoProjetos_Doadores1`
    FOREIGN KEY (`Doador`)
    REFERENCES `Habitat`.`Doadores` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Habitat`.`TarefasFuncionariosProjetos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Habitat`.`TarefasFuncionariosProjetos` ;

CREATE TABLE IF NOT EXISTS `Habitat`.`TarefasFuncionariosProjetos` (
  `Tarefa` INT NOT NULL,
  `Projeto` INT NOT NULL,
  `FuncionarioRegistou` INT NOT NULL,
  PRIMARY KEY (`Tarefa`, `Projeto`, `FuncionarioRegistou`),
  INDEX `fk_Tarefas_has_Projetos_Projetos1_idx` (`Projeto` ASC),
  INDEX `fk_Tarefas_has_Projetos_Tarefas1_idx` (`Tarefa` ASC),
  INDEX `fk_TarefasFuncionariosProjetos_Funcionarios1_idx` (`FuncionarioRegistou` ASC),
  CONSTRAINT `fk_Tarefas_has_Projetos_Tarefas1`
    FOREIGN KEY (`Tarefa`)
    REFERENCES `Habitat`.`Tarefas` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Tarefas_has_Projetos_Projetos1`
    FOREIGN KEY (`Projeto`)
    REFERENCES `Habitat`.`Projetos` (`Nr`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TarefasFuncionariosProjetos_Funcionarios1`
    FOREIGN KEY (`FuncionarioRegistou`)
    REFERENCES `Habitat`.`Funcionarios` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Habitat`.`TarefasMaterial`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Habitat`.`TarefasMaterial` ;

CREATE TABLE IF NOT EXISTS `Habitat`.`TarefasMaterial` (
  `Tarefa` INT NOT NULL,
  `Material` INT NOT NULL,
  `QuantidadeGasta` INT NULL,
  PRIMARY KEY (`Tarefa`, `Material`),
  INDEX `fk_Tarefas_has_Material_Material1_idx` (`Material` ASC),
  INDEX `fk_Tarefas_has_Material_Tarefas1_idx` (`Tarefa` ASC),
  CONSTRAINT `fk_Tarefas_has_Material_Tarefas1`
    FOREIGN KEY (`Tarefa`)
    REFERENCES `Habitat`.`Tarefas` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Tarefas_has_Material_Material1`
    FOREIGN KEY (`Material`)
    REFERENCES `Habitat`.`Material` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
