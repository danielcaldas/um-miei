#########################Makefile###################################
#Compilador CC e opções de compilação CFLAGS#
CC=gcc
CFLAGS=-ansi -Wall -Wextra -O3 -Wno-unused-result -g

all: gestauts


gestauts: main.o indice_autores.o estatistica.o catalogo_autores.o
	$(CC) main.o indice_autores.o estatistica.o catalogo_autores.o -o gestauts


indice_autores: indice_autores.o main.o
	$(CC) -o gestauts indice_autores.o main.o
indice_autores.o: indice_autores.c indice_autores.h
	$(CC) -o indice_autores.o -c indice_autores.c $(CFLAGS)


estatistica: estatistica.o main.o
	$(CC) -o gestauts estatistica.o main.o
estatistica.o: estatistica.c estatistica.h
	$(CC) -o estatistica.o -c estatistica.c $(CFLAGS)


catalogo_autores: catalogo_autores.o main.o
	$(CC) -o gestauts catalogo_autores.o main.o
catalogo_autores.o: catalogo_autores.c catalogo_autores.h
	$(CC) -o catalogo_autores.o -c catalogo_autores.c $(CFLAGS)


main.o: main.c indice_autores.h estatistica.h catalogo_autores.h                                                                                                                                                                                        
	$(CC) -o main.o -c main.c $(CFLAGS)


clean:
	rm -rf *.o
	rm -rf gestauts

