#ifndef _List
#define _List

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/** Estruturas de dados	 */
typedef struct Element *List;

/* Função que cria um elemento novo para a lista */
List newElem();

/* Funções que retiram de um elemento a informação */
void *getTitle (List elem);

/* Retorna a origem da canção */
void *getFrom (List elem);

/* Retorna o Autor da canção */
void *getAuthor (List elem);

/* Retorna o autor da letra da canção */
void *getLyrics (List elem);

/* Retorna o cantor da canção */
void *getSinger (List elem);

/* Retorna o Autor da música */
void *getMusic (List elem);

/* Retorna a Letra da música */
void*  getSong (List elem);

/* Retorna o tamanho da letra */
int getSize (List list);

/* Verifica se existe próximo elemento */
int hasNext (List list);

/* Função que insere um novo elemento na cauda da lista */
List insertElemTail(List list, char *title, char *from, char *author, char *lyrics, char *singer, char *music, char **song, int size);

/* Função que insere um novo elemento na cabeça da lista */
List insertElemHead(List list, char *title, char *from, char *author, char *lyrics, char *singer, char *music, char **song, int size);

/* Retorna o proximo elemento da lista */
List nextElem (List elem);

/* Retorna o tamanho da lista */
int listSize (List list);

/* Liberta a memoria ocupada pela lista */
void freeList (List list);

#endif
