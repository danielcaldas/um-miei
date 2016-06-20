#ifndef _H_CATALOGO_AUTORES
#define _H_CATALOGO_AUTORES

/*
This modul is divided in 7 diferent sections:
 - Init function that initializes specific variables of the modul;
 - Store (insert) functions, that store in a specific data structure the relevant information for this modul;
 - AVL tree's manipulation functions;
 - Other functions;
 - Get functions, that allow the main modul printing data that is private
   to this modul;
 - Release functions, so that when the user exits GESTAUTS these functions release
   the memory used by the modul data structures while the API was running;
 - Close funtion, that closes the extern acess to the modul data structure;

   (signatures of private functions and data structure of this modul are in catalogo_autores.c)
*/


/*Abstract data type defenition*/
typedef struct publicxCAUTHORS* publxCAUTHORS_TAD;


/*Initialization functions*/
void CA_Init();

/*Insertion functions*/
void CA_Store_Authors (char *author_names[], int number_of_authors, int year_of_publication);


/*Get functions*/
int CA_getNumberOfPublcAuthor(char * author, int year_of_publication);
char** CA_getListCoAuthorsGivenName(char * name);
int CA_getNumberOfCoAuthorsGivenName(char * name);
int CA_getNumberOfSoloAuthors();
int* CA_getYearsArrayPublications(int * years, char * name);
int* CA_getPublicationsPerYear(int * publications, char * name);
int CA_getNumberDiferentYearsOfPublications(char * name);
char** CA_getMaxCoAuthories (char * name , int * number_of_publications);
char** CA_AuthorsYearsInterval(int minYEAR, int maxYEAR, int * size);
char** CA_getNAuthorsInYear(int year_of_publication, int * N, int * values);
float CA_getPercentilsInYear(char * name, int year_of_publication);
char** CA_getListCoAuthorsGivenName(char * name);
int CA_getNumberOfCoAuthorsGivenName(char * name);
char* CA_getMaximumPublications(int * n);
int* CA_getPublicationsInYear(int index);
int CA_getNumberOfAuthorsInYear(int index);
char** CA_getNamesList(int index);
int* CA_getCoAutsPublications(char * name);


/*Relase memory functions*/
void CA_ReleaseAuthorsCatalog();
void CA_ReleaseYears();


/*Close modul*/
void CA_CloseInsertions();


#endif