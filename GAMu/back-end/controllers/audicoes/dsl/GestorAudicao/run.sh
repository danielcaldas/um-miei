#bin/bash

javac *.java
jar cf gestorAudicao.jar *.class
sudo chmod 777 gestorAudicao.jar
sudo cp gestorAudicao.jar ..