#ifndef _H_INDICE_AUTORES
#define _H_INDICE_AUTORES

/*
This modul is divided in 7 diferent sections:
 - The initialization function;
 - The store function, trough which we store data in the proper local from leitura modul;
 - The AVL tree's manipulation functions;
 - Release dinamic allocated memory functions;
 - Get functions that allows main modul printing relevant names related informaton;
 - Other auxiliar functions;
 - Close insertions in the data structure function;

 (signatures of private functions and data structure of this modul are in indice_autores.c)
*/

/*Abstract data type defenition*/
typedef struct publxAuthorTREE *publxAUTREE_TAD;


/*Inicialization functions*/
void IA_Init();

/*Store function*/
void IA_Store_Authors (char *author_names[], int number_of_authors);

/*Get functions*/
int IA_getCountAUTHORS();
char* IA_getSmallest();
char* IA_getBiggest();
unsigned int IA_getSmallestSize();
unsigned int IA_getBiggestSize();
int IA_getNamesMediumLength();
int IA_getTotalLetter(char c); /*Gets the total of names that start with the passing argument char c*/
char** IA_getAuthorsListNames(char c);

/*Release memory functions*/
void IA_ReleaseAuthorsTree();

/*Close funtion*/
void IA_CloseInsertions();


#endif