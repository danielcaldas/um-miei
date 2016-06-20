#!/bin/bash
cp pessoa.exe AntonioMachado/
cp pessoa.exe TabernaDoFausto/
cp pessoa.exe NecaChamine/
cp pessoa.exe FaustoGomes/

mkdir site

cd AntonioMachado/
cat legenda.xml | ./pessoa.exe AM
rm pessoa.exe
mv home.html antonio.html
mv antonio.html ~/Desktop/Exercicio1/site
cd ..

cd TabernaDoFausto/
cat legenda.xml | ./pessoa.exe TF
rm pessoa.exe
mv home.html taberna.html
mv taberna.html ~/Desktop/Exercicio1/site
cd ..

cd NecaChamine/
cat legenda.xml | ./pessoa.exe NC
rm pessoa.exe
mv home.html neca.html
mv neca.html ~/Desktop/Exercicio1/site
cd ..

cd FaustoGomes/
cat legenda.xml | ./pessoa.exe FG
rm pessoa.exe
mv home.html fausto.html
mv fausto.html ~/Desktop/Exercicio1/site
