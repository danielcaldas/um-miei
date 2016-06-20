#!/bin/bash
mkdir resultados
cat teste/Teste.lyr | ./Latex.exe
for file in *.tex; do pdflatex "$file"; done
mv *.pdf ./resultados/
