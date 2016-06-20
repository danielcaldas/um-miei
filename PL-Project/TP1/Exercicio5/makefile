CFLAGS= -O2 -g -Wall -Wextra

main:Latex.out

Latex.out: lex.yy.o List.o
	gcc lex.yy.o List.o -o Latex.exe -ll $(CFLAGS)

lex.yy.o: lex.yy.c
	gcc lex.yy.c -c

lex.yy.c: filtro.l List.h List.c 
	flex filtro.l

List.o: List.c List.h 
	gcc List.c -c $(CFLAGS)	

clean:
	-rm *.o
	-rm lex.yy.c
	-rm *.exe
	-rm *.tex
	-rm *.log

