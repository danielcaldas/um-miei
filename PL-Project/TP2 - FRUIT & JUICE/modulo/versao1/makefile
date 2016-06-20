CC=gcc -g

all :
	@echo "\tCompilando Hashs"
	make hash.o

hash.o : hash.c hash.h const.h
	@echo "\t\tEstruturas de dados"
	$(CC) -c hash.c

clean :
	rm -f hash.o

test :  hash.o
	gcc test.c -o test hash.o

