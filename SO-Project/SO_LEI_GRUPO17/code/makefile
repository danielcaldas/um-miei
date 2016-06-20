#########################Makefile###################################
#Compilador CC e opções de compilação CFLAGS#
CC=gcc
CFLAGS= -O3 -g -Wno-unused-result 

#TARGETS
all: servidor cliente


#SERVIDOR
servidor: servidor.o biblioteca.o megastring.o
	$(CC)  servidor.o biblioteca.o megastring.o -o servidor

biblioteca: biblioteca.o servidor.o
	$(CC) -o servidor biblioteca.o servidor.o
biblioteca.o: biblioteca.c biblioteca.h
	$(CC) -o biblioteca.o -c biblioteca.c $(CFLAGS)


servidor.o: servidor.c biblioteca.h megastring.h                                                                                                                                                                                    
	$(CC) -o servidor.o -c servidor.c $(CFLAGS)



#CLIENTE
cliente: cliente.o
	$(CC)  cliente.o -o cliente

cliente.o: cliente.c
	$(CC) -o cliente.o -c cliente.c $(CFLAGS)



clean:
	rm -rf *.o
	rm -rf servidor
	rm -rf cliente
