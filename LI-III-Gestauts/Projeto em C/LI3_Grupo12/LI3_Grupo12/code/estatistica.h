#ifndef _H_ESTATISTICA
#define _H_ESTATISTICA

/*
This modul is divided in 5 diferent sections:
 - Init funtion that initializes specific variables of the modul;
 - Store (insert) functions, that store in a specific data structure the years
   of publications and adicional relevant information;
 - Get functions, that allow the main modul printing data that is private
   to this modul;
 - Release functions, so that when the user exits GESTAUTS these functions release
   the memory used by the modul data structures while the API was running;
 - Close funtion, that closes the extern acess to the modul data structure;

   (signatures of private functions and data structure of this modul are in estatistica.c)
*/

/*Abstract data type defenition*/
typedef struct publxYEAR_TCD *publxYEAR_TAD;

/*Initialize modul*/
void E_Init();

/*Store (insert) functions*/
void E_Estatistica (int year_of_publication, int number_of_authors);

/*Get functions*/
int E_getMaxYear ();
int E_getMinYear();
int E_getCountYEAR();
int* E_getYearsOfPublications();
int* E_getPublicationsPerYear();
int** E_getAuthorsPublicationsTable();
void E_getLongestPublication(int * year, int * number_of_authors);
int E_getPublicationsInYear(int year_of_publication); /*use for CA modul querie 13.*/

/*Release memory function*/
void E_Release_Mem ();

/*Close modul funtion*/
void E_CloseInsertions();

#endif