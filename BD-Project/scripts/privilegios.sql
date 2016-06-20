/* -------------------------------Observador--------------------------------*/

CREATE USER 'Observador'@'host';
SET PASSWORD FOR 'Observador'@'host' = PASSWORD ('guest');

GRANT SELECT ON *.* TO 'Observador'@'host';





/* -------------------------------Funcionario--------------------------------*/

CREATE USER 'Funcionario'@'host';
SET PASSWORD FOR 'Funcionario'@'host' = PASSWORD ('1234');

GRANT SELECT, INSERT, UPDATE ON *.* TO 'Funcionario'@'host';


/* -------------------------------Administrador--------------------------------*/

CREATE USER 'Admin'@'host';
SET PASSWORD FOR 'Admin'@'host' = PASSWORD ('admin');

GRANT ALL PRIVILEGES ON *.* TO 'Admin'@'host';


SHOW GRANTS FOR 'Admin'@'host';


FLUSH PRIVILEGES;