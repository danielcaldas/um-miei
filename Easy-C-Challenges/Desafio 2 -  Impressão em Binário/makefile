#########################Makefile###################################
all: binario
binario: binario.o main.o
	gcc -o binario binario.o main.o
binario.o: binario.c
	gcc -o binario.o -c binario.c -W -Wall -ansi -pedantic
main.o: main.c binario.h
	gcc -o main.o -c main.c -W -Wall -ansi -pedantic
clean:
	rm -rf *.o
mrproper: clean
	rm -rf teste
