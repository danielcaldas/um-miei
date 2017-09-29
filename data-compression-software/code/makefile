#########################Makefile###################################
all: comprimir
comprimir: comprimir.o main.o
	gcc -o compressor comprimir.o main.o
comprimir.o: comprimir.c
	gcc -o comprimir.o -c comprimir.c -O3
main.o: main.c comprimir.h
	gcc -o main.o -c main.c -O3
clean:
	rm -rf *.o
