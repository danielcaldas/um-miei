#########################Makefile###################################
all: funcoes
funcoes: funcoes.o main.o
	gcc -o funcoes funcoes.o main.o
funcoes.o: funcoes.c
	gcc -o funcoes.o -c funcoes.c -W -Wall -ansi -pedantic
main.o: main.c funcoes.h
	gcc -o main.o -c main.c -W -Wall -ansi -pedantic
clean:
	rm -rf *.o
mrproper: clean
	rm -rf teste
