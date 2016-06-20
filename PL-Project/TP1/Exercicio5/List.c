# include "List.h"

/*	Estrutura de dados	*/

typedef struct Element {
    
    char *title;
    char *from;
    char *author;
    char *lyrics;
    char *singer;
    char *music;
    char **song;
    int size;

    struct Element *next;
} LinkedList;

/* Função que cria um elemento novo para a lista */
List newElem() {
    LinkedList *new = (LinkedList *)malloc(sizeof(LinkedList));

    new->title = NULL;
    new->from = NULL;
    new->author = NULL;
    new->lyrics = NULL;
    new->singer = NULL;
    new->music = NULL;
    new->song = (char **) malloc(sizeof(char *));
    new->next = NULL;
    new->size = 0;

    return new;
}

/* Funções que retiram de um elemento a informação */
void *getTitle (List elem) {
    if (!elem) return NULL;
    return elem->title;
}

/* Retorna a origem da canção */
void *getFrom (List elem) {
    if (!elem) return NULL;
    return elem->from;
}

/* Retorna o Autor da canção */
void *getAuthor (List elem) {
    if (!elem) return NULL;
    return elem->author;
}

/* Retorna o autor da letra da canção */
void *getLyrics (List elem) {
    if (!elem) return NULL;
    return elem->lyrics;
}

/* Retorna o cantor da canção */
void *getSinger (List elem) {
    if (!elem) return NULL;
    return elem->singer;
}

/* Retorna o Autor da música */
void *getMusic (List elem) {
    if (!elem) return NULL;
    return elem->music;
}

/* Retorna a Letra da música */
void* getSong (List elem) {
    if (!elem) return NULL;
    return elem->song;
}

/* Retorna o tamanho da letra */
int getSize (List list){
    if (!list) return 0;
    return list->size;
}

/* Verifica se existe próximo elemento */
int hasNext (List list) {
    if (!list) return 0;
    return (list->next != NULL);
}

/* Função que insere um novo elemento na cauda da lista */
List insertElemTail(List list, char *title, char *from, char *author, char *lyrics, char *singer, char *music, char **song, int size) {
    LinkedList *elem = newElem();
    LinkedList *aux;
    int x;
    
    if(title != NULL)   elem->title = strdup(title);
    if(from != NULL)    elem->from = strdup(from);
    if(author != NULL)  elem->author = strdup(author);
    if(lyrics != NULL)  elem->lyrics = strdup(lyrics);
    if(singer != NULL)  elem->singer = strdup(singer);
    if(music != NULL)   elem->music = strdup(music);
    
    elem->song = (char **)realloc(elem->song,size*sizeof(char *));
    for(x = 0; x < size; x++){
        elem->song[x] = strdup(song[x]);
    }
    
    elem->size = size;

    if (!list) return elem;
    else {
        aux = list;
        while (aux->next) aux = aux->next;
        aux->next = elem;
    }
    return list;
}

/* Função que insere um novo elemento na cabeça da lista */
List insertElemHead(List list, char *title, char *from, char *author, char *lyrics, char *singer, char *music, char **song, int size) {
    LinkedList *elem = newElem();
    int x;
    
    if(title != NULL)   elem->title = strdup(title);
    if(from != NULL)    elem->from = strdup(from);
    if(author != NULL)  elem->author = strdup(author);
    if(lyrics != NULL)  elem->lyrics = strdup(lyrics);
    if(singer != NULL)  elem->singer = strdup(singer);
    if(music != NULL)   elem->music = strdup(music);
    
    elem->song = (char **)realloc(elem->song,size*sizeof(char *));
    for(x = 0; x < size; x++){
        elem->song[x] = strdup(song[x]);
    }
    
    elem->size = size;

    elem->next = list;
    return elem;
}

/* Retorna o proximo elemento da lista */
List nextElem (List elem) {
    if (!elem) return NULL;
    return elem->next;
}

/* Retorna o tamanho da lista */
int listSize (List list) {
    int n = 0;
    LinkedList *aux = list;
    while (aux) {
        aux = aux->next;
        n++;
    }
    return n;
}

/* Liberta a memoria ocupada pela lista */
void freeList (List list) {
    LinkedList *aux;

    while (list!=NULL) {
        aux = list;
        list = list->next;
        free(aux);
    }
}
