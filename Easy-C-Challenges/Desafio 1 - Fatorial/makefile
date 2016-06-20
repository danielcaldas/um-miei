#########################Makefile###################################
all: fatorial
fatorial: fatorial.o main.o
	gcc -o fatorial fatorial.o main.o
fatorial.o: fatorial.c
	gcc -o fatorial.o -c fatorial.c -Wall -O3
main.o: main.c fatorial.h
	gcc -o main.o -c main.c -Wall -O3
clean:
	rm -rf *.o
mrproper: clean
	rm -rf teste
