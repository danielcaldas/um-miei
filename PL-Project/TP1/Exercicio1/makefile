FLAGS= -O2 -g -Wall -Wextra

main:pessoa.out

pessoa.out: lex.yy.o gerealbum.o
	gcc lex.yy.o gerealbum.o -o pessoa.exe -ll $(FLAGS)

lex.yy.o: lex.yy.c
	gcc lex.yy.c -c

lex.yy.c: filtro.l gerealbum.h gerealbum.c 
	flex filtro.l

gerealbum.o: gerealbum.c gerealbum.h 
	gcc gerealbum.c -c $(FLAGS)	

clean:
	-rm *.o
	-rm lex.yy.c
	-rm *.exe
