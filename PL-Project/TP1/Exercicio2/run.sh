#!/bin/bash
flex filtro.l
gcc lex.yy.c -ll
cat teste.txt | ./a.out

