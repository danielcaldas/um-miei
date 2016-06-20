#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<ctype.h>
#include"catalogo_autores.h" /*modul description in catalogo_autores.h*/
#include"estatistica.h"


/*Macros*/
#define MAX_NAME_SIZE 70
#define DIFERENT_LETTERS 27


typedef enum balancefactor { LH , EH , RH } BalanceFactor;

/*AVL tree. Each node is an author with an array of pointers to structure that is of the same type*/
typedef struct publicxCAUTHORS{
	char *name;

	struct StoreCountCoAutRelations *co_authors;
	int number_of_co_authors;
	int maximus; /*usefull for querie 8.*/
	struct yearsNPUBLICATIONS *years_npublications; /*array that contains years in which the author publish articles and respective value of quantification*/
	int nyears; /* nyears/2 gives the diferent number of years in which the respective author published articles*/
	int total_publications;

	BalanceFactor bf;
	struct publicxCAUTHORS *left;
	struct publicxCAUTHORS *right;
}publxCAUTHORS_TCD;

typedef publxCAUTHORS_TCD *publxCA;


/*Secondary data struture*/
typedef struct StoreCountCoAutRelations{
	publxCA coaut;
	int counter;
}publxCOAUTR;


typedef struct yearsNPUBLICATIONS{
	int year;
	int counter;
}yearNPUBLX;


/*Struct resulted from optimization of querie 12.*/
typedef struct catalogYEARS
{
	int year;
	int number_of_authors;
	publxCOAUTR *auts;
}publxCYEARS;


/*Modul private variables*/
static int countYEAR;
static publxCA *pointersToCoAuthors; /*necessária para não alterar assinaturas das funções que operam sobre as AVL*/
static int pointersINDEX;
static int flagFULL;

/*Pointer to AVL tree of each letters same algorithm in modul indice_autores*/
static publxCA* authors_catalog;
static publxCYEARS* years_catalog;



/*Private functions to the modul*/

/*Insertion functions*/
static void CA_InsertPublicationAuthor (publxCA this, int year_of_publication);
static int CA_Already_Exists_Year (publxCA this, int year_of_publication);
static void CA_InsertCoAuthor(publxCA this, publxCA co_author);
static void CA_OrderCoAuthorsByPublications(publxCA this, int index);
static int CA_InsertionSort (publxCA this, int year_of_publication);
static int CA_checkIndex (char c);

/*AVL tree functions*/
static publxCA CA_Insert_CATREE(publxCA authorsTREE, char * name , int *cresceu);
static publxCA CA_insertRight(publxCA authorsTREE, char * name , int *cresceu);
static publxCA CA_insertLeft(publxCA authorsTREE, char * name, int *cresceu);
static publxCA CA_rotateRight(publxCA authorsTREE);
static publxCA CA_rotateLeft(publxCA authorsTREE);
static publxCA CA_balanceRight(publxCA authorsTREE);
static publxCA CA_balanceLeft(publxCA authorsTREE);

/*Years catalog related functions*/
static void CA_InsertAuthorInYearsCatalog(publxCA author, int year_of_publication);
static void CA_SortAutsByPublications(publxCOAUTR * auts, int index);
static int CA_InsertionYear_Sort (int year_of_publication);
static void CA_Copy_Structure (int a, int b);
static void CA_Copy_ArrayOfPointerToAuthors (int a, int b);

/*Get auxiliar functions*/
static publxCA CA_SearchSoloAuthors(publxCA aux);
static char** CA_searchInTree(publxCA author, int minYEAR, int maxYEAR, int number_of_years, char* authors_names[], int * size);
static publxCA CA_searchMaxInTree(publxCA author,int * max);

/*Release functions*/
static publxCA CA_Deallocate(publxCA tree);



/*----------------------------------------------------------------------------------------------------------------------------*/
/*--------------------------------------------------Initialization Function--------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------*/

/**
	@return nada. Quando chamada externamente inicializa as variáveis privadas ao módulo
*/
void CA_Init()
{
	int i;

	countYEAR=0;
	flagFULL=0;

	authors_catalog = (publxCA*) malloc (DIFERENT_LETTERS*sizeof(publxCA));


	for(i=0;i<27;i++)
		authors_catalog[i]=NULL;
}

/*----------------------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------  \END   ---------------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------*/




/*----------------------------------------------------------------------------------------------------------------------------*/
/*---------------------------------------------Insertions functions-----------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------*/

/**
	@param um array de nomes de autores, o tamanho desse array e o ano da publicação
	@return nada. Primeiro a função faz inserções de nomes numa árvore AVL à semelhança do módulo índice de autores
			criando assim a "base" do grafo em que consiste este módulo. De seguida a função itera sobre o array de nomes
			e um array de apontadores para nodos da AVL relacionando os autores (coautorias) ao mesmo tempo que cria uma estrutura
			dados um pouco diferente que para cada ano armazena por ordem decrescente do número de publicações a lista de autores que
			publicaram nesse ano
*/
void CA_Store_Authors (char *author_names[], int number_of_authors, int year_of_publication)
{
	int i, j, flagSAMENAME, avlTreeIndex;
	int cresceu=0;


	if(flagFULL==0){
	
		pointersToCoAuthors = (publxCA*) malloc(number_of_authors*(sizeof(publxCA)));

		for(i=0;i<number_of_authors;i++)
		pointersToCoAuthors[i]=NULL;
		pointersINDEX=0;


		/*We treat the names by the order that each one appears in the publication*/


		/*Cycle that inserts the author in the AVL tree (if non existent)*/
		for(i=0; i<number_of_authors; i++){
			avlTreeIndex=CA_checkIndex(author_names[i][0]);
			authors_catalog[avlTreeIndex] = CA_Insert_CATREE(authors_catalog[avlTreeIndex],author_names[i], &cresceu); /*Insert a name in the AVL tree*/
		}

		/*Cycle that ckecks for co_author relations in the given publication*/
		for(i=0; i<number_of_authors; i++){

			/*to make sure that we not co relate any author with itself*/
			flagSAMENAME=i;

			CA_InsertPublicationAuthor(pointersToCoAuthors[i],year_of_publication);
		
			CA_InsertAuthorInYearsCatalog(pointersToCoAuthors[i],year_of_publication);

			for(j=0; j<number_of_authors; j++){ /*insert co_authors for the author_names[i] author*/
				if(j!=flagSAMENAME) /*only co_relate authors if they are not the same*/
					CA_InsertCoAuthor(pointersToCoAuthors[i], pointersToCoAuthors[j]);
			}

		}

		free(pointersToCoAuthors);

	}

}


/*In the cycle of CA_Store_Authors line 159 we want to update certain number of publications of a certain author*/
/**
	@param um apontador para um nodo da AVL e um ano de uma publicação
	@return nada. Esta função ordena à medida que constroí um array de publicações para um autor "this" em que
			ou se atualiza o número de publicações do autor "this" no ano year_of_publication ou se aloca espaço para
			um novo ano em que esse mesmo autor tenha publicado
*/
static void CA_InsertPublicationAuthor (publxCA this, int year_of_publication)
{
	int size=0;
	int index=0;

	if(this->nyears==0){
		this->years_npublications[0].year=year_of_publication;
		this->years_npublications[0].counter=1;
		this->nyears=1;
		this->total_publications=1;
		return;
	}

	else if( (index=CA_Already_Exists_Year(this,year_of_publication))!=-1 ){
			this->years_npublications[index].counter++; /*increments counter of this especific year*/
			this->total_publications++;
			return;
	}

	else{

			/*Reaching this point means that this is the first time that the author this publish an article in the year year_of_publication*/

			size = this->nyears+1;

			this->years_npublications = (yearNPUBLX*) realloc(this->years_npublications, (size*sizeof(yearNPUBLX)) );

			index = CA_InsertionSort(this,year_of_publication);

			this->years_npublications[index].year=year_of_publication;
			this->years_npublications[index].counter=1;


			this->total_publications++;
			this->nyears++;

	}
}



/**
	@param um apontador para um nodo da AVL e um ano de uma publicação
	@return o índice onde deve ser inserido o novo ano em que o autor publicou, a função garante que o array fica sempre
			ordenado por ordem crescente do ano
*/
static int CA_InsertionSort (publxCA this, int year_of_publication)
{
	int i; /*index of last year value in array*/
	int flagOutFirstTime=0; /*consider presenting array draw to explain this non trivial flag*/

	for(i=this->nyears-1; this->years_npublications[i].year > year_of_publication && i>=0; i--){
		flagOutFirstTime=1;
		/*copy two positions foward*/
		this->years_npublications[i+1].year=this->years_npublications[i].year;  /* year_before=year i*/
		this->years_npublications[i+1].counter=this->years_npublications[i].counter; /* n publications in year before = n publications in year i+1*/
	}

	if(flagOutFirstTime==0 && i==0) return i+1;  

	else if(i==-1) return 0;

	else return i+1;
}




/**
	@param um apontador para um nodo da AVL e um ano de uma ppublicação
	@return -1 caso o autor ainda não tenha publicado nesse ano, ou o índice correspondente a esse ano no array de publicações do autor
			(years_npublications) para que na função chamadora se incremente o respetivo contador
*/
static int CA_Already_Exists_Year (publxCA this, int year_of_publication)
{
	int i;

	for(i=0; i<this->nyears; i++)
		if(year_of_publication==this->years_npublications[i].year) return i;

	return -1;
}



/*Insert co author int array of co_authors of this if co author non existant*/
/**
	@param um apontador para o nodo da AVL this e outro apontador para um nodo de um "recente encontrado" coautor de "this"
	@return nada associa o coautor ao autor this incrementando o contador de publicações em conjunto no array de coautorias de this
			ou caso estes ainda não tenham sido relacionados aloca memória para tal efeito
*/
static void CA_InsertCoAuthor(publxCA this, publxCA co_author)
{
	int size=0;
	int i;
	int N=this->number_of_co_authors;

	for(i=0; i<N; i++)
		if(this->co_authors[i].coaut==co_author){
			this->co_authors[i].counter++;

			CA_OrderCoAuthorsByPublications(this,i);

			/*finding the author that have more coauthories with this for Querie 8.*/
			if(this->co_authors[i].counter > this->maximus)
				this->maximus = this->co_authors[i].counter;

			return;
		}

	/*Reaching this point means that the authors were not yet co_related*/
	this->number_of_co_authors++;
	size=this->number_of_co_authors;
	this->co_authors = (publxCOAUTR*) realloc(this->co_authors, size*sizeof(publxCOAUTR));

	this->co_authors[this->number_of_co_authors-1].coaut = co_author;

	/*Means until now author X publish with author Y only 1 article*/
	this->co_authors[this->number_of_co_authors-1].counter = 1;

	/*finding the author that have more coauthories with this for Querie 8.*/
	if(this->co_authors[i].counter > this->maximus)
		this->maximus = this->co_authors[i].counter;


}




/*We provide that the coauthor with the bigger counter is always at the head of the array*/
/**
	@param um apontador para um nodo da AVL e um índice a partir do qual se originou a desordem
	@return nada. Ordena o array recentemente modificado de modo a que this tenha sempre um array de coautores ordenados
			por ordem decrescente do número de coautorias, i.e o autor com quem this mais publicou fica sempre à cabeça do array
			co_authors.
*/
static void CA_OrderCoAuthorsByPublications(publxCA this, int index)
{
	int i;
	publxCA aux = NULL;
	int c=0;

	for(i=index; i>0; i--)
		if(this->co_authors[i].counter>this->co_authors[i-1].counter){
			c=this->co_authors[i].counter;
			aux=this->co_authors[i].coaut;
			this->co_authors[i].counter = this->co_authors[i-1].counter;
			this->co_authors[i].coaut = this->co_authors[i-1].coaut;
			this->co_authors[i-1].counter = c;
			this->co_authors[i-1].coaut = aux;

		}

}




/*Funções em baixo resultam de optimizações feitas com propósito de melhorar o tempo de execução da querie 12.*/
/*----------------------------------------------------------------------------------------------------------------------------*/


/**
	@param um apontador para um nodo da AVL e um ano de uma publicação
	@return nada. Função responsável por criar um "catálogo de anos" i.e numa estrutura de dados própria armazena
			associado a um ano a lista de todos os autores que publicaram nesse ano por ordem decrescente do número
			de artigos publicados
*/
static void CA_InsertAuthorInYearsCatalog(publxCA author, int year_of_publication)
{
	int i, j;

	/*First author that appears in file, initialization*/
    if(countYEAR==0){
		years_catalog = (publxCYEARS*) malloc (sizeof(struct catalogYEARS));
		years_catalog[0].year=year_of_publication;

		years_catalog[0].auts = (publxCOAUTR*) malloc (sizeof(struct StoreCountCoAutRelations));
		years_catalog[0].number_of_authors=1; /*number of authors that published in this year*/

		years_catalog[0].auts[0].coaut = author;
		years_catalog[0].auts[0].counter = 1; /*number of publications of that author in this year*/

		countYEAR++; /*number of diferent years in which exists at least one publication*/

		return;
	}

	/*All possible cenarios after first insertion*/
	/*
	A)Year doesn't exists:
		i) create new year in array years_catalog;
		ii) creating new arrray of coauts put the author in that array

	B1)Year already exists and author found:
		i)increment the number of publications of the given author;
		ii)sort the array of coauts if necessary because of changes in the value by i);
	B2)Year already exists but author not found:
		i)put the new author in the last position of the array coauts with the inicialization of the respective counter;
	*/


	/*All cases except first publication falls in this else statement*/
	else{

		for(i=0;i<countYEAR;i++){
			if(years_catalog[i].year==year_of_publication){

			 	/*search the author in array coauts*/
			 	for(j=0; j<years_catalog[i].number_of_authors; j++){

			 		if(author==years_catalog[i].auts[j].coaut){ /* B1) year && author */
			 			years_catalog[i].auts[j].counter++;
			 			CA_SortAutsByPublications(years_catalog[i].auts, j); /*if changes position is always to the left*/

			 			return;
			 		}
			 	}

			 	/* B2) year && !author */
			 	years_catalog[i].number_of_authors++;
			 	years_catalog[i].auts = (publxCOAUTR*) realloc (years_catalog[i].auts, (years_catalog[i].number_of_authors)*sizeof(publxCOAUTR));

			 	years_catalog[i].auts[years_catalog[i].number_of_authors-1].coaut=author;
			 	years_catalog[i].auts[years_catalog[i].number_of_authors-1].counter=1;

				return;
			}
		}

		/*Reaching this point means that the year_of_publication was not found, then we need to allocate memory*/
		years_catalog = (publxCYEARS*) realloc(years_catalog, (countYEAR+1)*sizeof(struct catalogYEARS));

		/*Returns the index of the position where we write the new element of array datesStore*/
		i=CA_InsertionYear_Sort(year_of_publication);

    	years_catalog[i].year=year_of_publication;
    	years_catalog[i].number_of_authors=1; /*number of authors that published in this year*/

    	years_catalog[i].auts = (publxCOAUTR*) malloc (sizeof(struct StoreCountCoAutRelations));

		years_catalog[i].auts[0].coaut = author;
		years_catalog[i].auts[0].counter = 1; /*number of publications of that author in this year*/

    	countYEAR++;
	}
}



/**
	@param um apontador para um array de estruturas de dados que a cada autor tem associado o número de publicações do
		   autor nesse ano e um índice
	@return nada. Função que mantém ordem no array de estruturas de dados.
*/
static void CA_SortAutsByPublications(publxCOAUTR * auts, int index)
{
	int i;
	publxCA author;
	int cont;

	for(i=index; auts[i].counter>auts[i-1].counter && i>0; i--){ /*swap authors pointer while necessary*/
	
		author=auts[i].coaut;
		cont=auts[i].counter;

		auts[i].coaut=auts[i-1].coaut;
		auts[i].counter=auts[i-1].counter;

		auts[i-1].coaut=author;
		auts[i-1].counter=cont;

	}
}


/**
	@param um ano de uma publicação
	@return retorna o índice onde deve ser colocado o novo ano de modo a garantirmos ordem crescente pelo ano de publicação
*/
static int CA_InsertionYear_Sort (int year_of_publication)
{
	int i;

	/*The position countYear of the datesStore array is "empty"*/
	for(i=countYEAR-1; year_of_publication < years_catalog[i].year && i>=0;i--){
		CA_Copy_Structure(i, i+1);
	}

	if(i==-1) return 0;
		else return i+1;
}


/*Copy position a to position b b=a*/
/**
	@param dois índices.
	@return nada. Copia a estrutura de dados da posição a para a posição b
*/
static void CA_Copy_Structure (int a, int b)
{
	years_catalog[b].year=years_catalog[a].year; /*copy year*/
	years_catalog[b].number_of_authors=years_catalog[a].number_of_authors; /*copy number of authors in that year*/
	CA_Copy_ArrayOfPointerToAuthors(a,b);
}


/**
	@param dois índices
	@return nada. Apenas faz o swap de apontadores dos arrays de autores<->contador.
*/
static void CA_Copy_ArrayOfPointerToAuthors (int a, int b)
{
	years_catalog[b].auts=years_catalog[a].auts;
}

/*----------------------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------  \END   ---------------------------------------------------------------*/
/*--------------------------------------------------------------------------------------------------------------------------- */






/*----------------------------------------------------------------------------------------------------------------------------*/
/*---------------------------------------------AVL tree's manipulation functions----------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------*/
/*Todas as seguintes funções são cópias integrais das funções de manipulação de árvores AVL do módulo índice de autores
 à excepção desta primeira cujos campos de inicialização foram adaptados à estrutura de dados des módulo*/

static publxCA CA_Insert_CATREE(publxCA authorsTREE, char * name , int *cresceu)
{

	int alpha=0;
	*cresceu=0;

	if (authorsTREE==NULL) { /*A new name appears...*/

		/*initialize fields for new author*/

	   	authorsTREE = (publxCA) malloc(sizeof(struct publicxCAUTHORS));

	   	/*Allocating memory for new name in specific AVL tree*/
		authorsTREE->name = (char*) malloc(MAX_NAME_SIZE*sizeof(char));

		strcpy(authorsTREE->name,name);


		/*Co_authors array*/
		authorsTREE->number_of_co_authors=0;
		authorsTREE->co_authors = (publxCOAUTR*) malloc(sizeof(publxCOAUTR));
		authorsTREE->co_authors = NULL;
		authorsTREE->maximus = 0;

		/*Initialize years array*/
		authorsTREE->nyears=0;
		authorsTREE->years_npublications = (yearNPUBLX*) malloc(sizeof(yearNPUBLX));
		authorsTREE->total_publications=0;


		/*save temporarly the pointer to this node for further insert operations*/
	   	pointersToCoAuthors[pointersINDEX]=authorsTREE;
	   	pointersINDEX++;


		/*Initializations specific to AVL tree*/
		authorsTREE->left=authorsTREE->right=NULL;
		authorsTREE->bf=EH;
		*cresceu=1;
	}

	else{

		/*Find if name stays at right or left or eventualy is the same name in the node*/
		alpha=strcmp(name, authorsTREE->name);

		/*Case name already exists return tree, do not insert this name*/
		if(alpha==0){
			/*save temporarly the pointer to this node for further insert operations*/
	   		pointersToCoAuthors[pointersINDEX]=authorsTREE; 
	   		pointersINDEX++;
			return(authorsTREE);
		}

		else if (alpha>0)
				authorsTREE = CA_insertRight(authorsTREE,name,cresceu);

		else 	authorsTREE = CA_insertLeft(authorsTREE,name,cresceu);
	}

	return(authorsTREE);
}




static publxCA CA_insertRight(publxCA authorsTREE, char * name , int *cresceu)
{

		authorsTREE->right = CA_Insert_CATREE(authorsTREE->right,name,cresceu);

		if (*cresceu)
		switch (authorsTREE->bf) {
						case LH:
							authorsTREE->bf = EH;
							*cresceu = 0;
							break;
						case EH:
							authorsTREE->bf = RH;
							*cresceu = 1;
							break;
						case RH:
							authorsTREE = CA_balanceRight(authorsTREE);
							*cresceu = 0;
		}
		return authorsTREE;
}



static publxCA CA_insertLeft(publxCA authorsTREE, char * name, int *cresceu)
{

		authorsTREE->left = CA_Insert_CATREE(authorsTREE->left,name,cresceu);

		if (*cresceu)
		switch (authorsTREE->bf) {
						case RH:
							authorsTREE->bf = EH;
							*cresceu = 0;
							break;
						case EH:
							authorsTREE->bf = LH;
							*cresceu = 1;
							break;
						case LH:
							authorsTREE = CA_balanceLeft(authorsTREE);
							*cresceu = 0;
		}
		return authorsTREE;
}



static publxCA CA_rotateRight(publxCA authorsTREE)
{
	publxCA aux;

	if ( (!authorsTREE) || (!authorsTREE->left) ) {
		printf("ErroRightRotation!\n");
	}

	else {
			aux = authorsTREE->left;
			authorsTREE->left = aux->right;
			aux->right = authorsTREE;
			authorsTREE = aux;
	}
	return authorsTREE;
}



static publxCA CA_rotateLeft(publxCA authorsTREE)
{
	publxCA aux;

	if ( (!authorsTREE) || (!authorsTREE->right) ) {
		printf("ErroLeftRotation!\n");
	}

	else {
			aux = authorsTREE->right;
			authorsTREE->right = aux->left;
			aux->left = authorsTREE;
			authorsTREE = aux;
	}
	return authorsTREE;
}



static publxCA CA_balanceRight(publxCA authorsTREE)
{

		if (authorsTREE->right->bf==RH) {
			/*Simple left rotation*/
			authorsTREE = CA_rotateLeft(authorsTREE);
			authorsTREE->bf = EH;
			authorsTREE->left->bf = EH;
		}
		else {
			/*Double rotation*/
			authorsTREE->right = CA_rotateRight(authorsTREE->right);
			authorsTREE = CA_rotateLeft(authorsTREE);

			switch (authorsTREE->bf){
						case EH:
							authorsTREE->left->bf = EH;
							authorsTREE->right->bf = EH;
							break;
						case LH:
							authorsTREE->left->bf = EH;
							authorsTREE->right->bf = RH;
							break;
						case RH:
							authorsTREE->left->bf = LH;
							authorsTREE->right->bf = EH;
			}
			authorsTREE->bf=EH;
	    }	

		return authorsTREE;
}



static publxCA CA_balanceLeft(publxCA authorsTREE)
{

		if (authorsTREE->left->bf==LH){
			/*Simple right rotation*/
			authorsTREE = CA_rotateRight(authorsTREE);
			authorsTREE->bf = EH;
			authorsTREE->right->bf = EH;
		}
		else {
			/*Double rotation*/
			authorsTREE->left = CA_rotateLeft(authorsTREE->left);
			authorsTREE = CA_rotateRight(authorsTREE);

			switch (authorsTREE->bf){
						case EH:
							authorsTREE->left->bf = EH;
							authorsTREE->right->bf = EH;
							break;
						case LH:
							authorsTREE->left->bf = EH;
							authorsTREE->right->bf = RH;
							break;
						case RH:
							authorsTREE->left->bf = LH;
							authorsTREE->right->bf = EH;
			}
			authorsTREE->bf=EH;
			authorsTREE->bf=EH;
		}

		return authorsTREE;
}

/*----------------------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------  \END   ---------------------------------------------------------------*/
/*--------------------------------------------------------------------------------------------------------------------------- */






/*----------------------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------Other auxiliar functions------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------*/

/*mesma que em índice de autores*/
static int CA_checkIndex (char c)
{
	int i=0;
	int letter='A';
	
	for(i=0; i<26; i++, letter++)
		if(c==letter) break;

	return i;
}

/*----------------------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------  \END   ---------------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------*/






/*----------------------------------------------------------------------------------------------------------------------------*/
/*--------------------------------------------------Get functions-------------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------*/

/**
	@param um nome de um autor e um ano
	@return o número de publicações do autor passado como parâmetro no dado ano
*/
int CA_getNumberOfPublcAuthor(char * author, int year_of_publication){

	int r, i;
	int number_of_publications=0;
	int flag=0;
	publxCA aux;

	i=CA_checkIndex(author[0]); /*in which tree are we going to search?*/

	aux=authors_catalog[i];

	while(flag==0 && aux!=NULL){
		r=strcmp(author, aux->name);
		if(r==0)
			for(i=0; i<aux->nyears; i++){
				if(aux->years_npublications[i].year==year_of_publication){
					number_of_publications=aux->years_npublications[i].counter;
					flag=1;
					break;
				}
			flag=1;
			}

		else if(r>0) aux=aux->right;

		else 		 aux=aux->left;
	}
	if(aux==NULL) return -1; /*names doesn't exists or input error*/
 

	return number_of_publications;
}



/**
	@param um nome de um autor
	@return um apontador para um array de cópias dos nomes de todos os coautores do autor passado como parâmetro
*/
char** CA_getListCoAuthorsGivenName(char * name)
{
	int size, i, r;
	publxCA aux;
	char **co_authorslist;

	i=CA_checkIndex(name[0]); /*in which tree are we going to search?*/

	aux=authors_catalog[i];

	/*Searching name on AVL tree*/
	while(aux!=NULL){
		r=strcmp(name,aux->name);
		if(r==0)
			break;
		else if(r>0) aux=aux->right;

		else 		 aux=aux->left;
	}
	if(aux==NULL) return NULL;

	size = aux->number_of_co_authors;

	/*Allocating and copying names of the list of adjecense of the node to a new char* names vector*/
	co_authorslist = (char**) malloc(size*sizeof(char*));
	for(i=0;i<size;i++){
		co_authorslist[i] = (char*) malloc(MAX_NAME_SIZE*sizeof(char));
		strcpy(co_authorslist[i], aux->co_authors[i].coaut->name);
	}

	return co_authorslist;
}



/**
	@param um nome de um autor
	@return o número de diferentes coautores associados ao nome do autor passado como para parâmetro
*/
int CA_getNumberOfCoAuthorsGivenName(char * name)
{
	int number_of_co_authors=0;
	int r;
	publxCA aux;

	r=CA_checkIndex(name[0]); /*in which tree are we going to search?*/

	aux=authors_catalog[r];

	/*Searching name on AVL tree*/
	while(aux!=NULL){
		r=strcmp(name,aux->name);
		if(r==0)
			break;
		else if(r>0) aux=aux->right;

		else 		 aux=aux->left;
	}
	if(aux==NULL) return -1;

	/*Finally we reach the node, now we just need to return the value that represents the number of co_authors of the given author*/
	number_of_co_authors = aux->number_of_co_authors;

	return number_of_co_authors;
}



/**
	@return o número de autores que somente publicaram artigos a solo
*/
static int counterSOLO;
int CA_getNumberOfSoloAuthors()
{
	int i;
	publxCA aux;
	counterSOLO=0;

	/*For check solo authors we need to check all AVL trees (i.e the graph)*/
	for(i=0; i<27; i++){
		aux=authors_catalog[i];
		CA_SearchSoloAuthors(aux);
	}


	return counterSOLO;
}


/**
	@param um apontador para a ráiz de uma das àrvores AVL
	@return um apontador para um nodo da AVL (será NULL no final da pesquisa)
*/
static publxCA CA_SearchSoloAuthors(publxCA aux)
{
	if(aux!=NULL){
		if(aux->number_of_co_authors==0) counterSOLO++;
		CA_SearchSoloAuthors(aux->left);
		CA_SearchSoloAuthors(aux->right);
	}

	return aux;
}



/**
	@param um nome de um autor
	@return devolve o número de anos diferentes em que o autor passado como parâmetro publicou artigos
*/
int CA_getNumberDiferentYearsOfPublications(char * name)
{
	publxCA aux;
	int r=CA_checkIndex(name[0]); /*in which tree are we going to search?*/

	aux=authors_catalog[r];

	/*Searching name on AVL tree*/
	while(aux!=NULL){
		r=strcmp(name,aux->name);
		if(r==0)
			break;
		else if(r>0) aux=aux->right;

		else 		 aux=aux->left;
	}
	if(aux==NULL) return -1; 

	return aux->nyears;
}


/**
	@param um pontador (NULL no momento de recepção) para um array de anos e o nome de aum autor
	@return um apontador de um array que contém os diferentes anos em que o autor passado como parâmetro publicou artigos
*/
int* CA_getYearsArrayPublications(int * years, char * name)
{
	int i, r, size;
	publxCA aux;

	r=CA_checkIndex(name[0]);

	aux=authors_catalog[r];

	/*Searching name on AVL tree*/
	while(aux!=NULL){
		r=strcmp(name,aux->name);
		if(r==0)
			break;
		else if(r>0) aux=aux->right;

		else 		 aux=aux->left;
	}

	if(aux==NULL) return NULL; /*names doesn't exists or input error*/

	size = aux->nyears;

	/*Allocating memory for new table*/
	years = (int*) malloc((size+1)*sizeof(int));

	/*copying array of publications to new struct*/
	for(i=0; i<size; i++){
		years[i] = aux->years_npublications[i].year;
	}
	years[i]=-1; /*ficou acordado entre aluno e docente seria desnecessário imprimir anos em que número de publicações está a 0*/

	return years;

}



/**
	@param um apontador (NULL no momento de recepção) para um array de quantidades de publicações  e um nome de um autor
	@return um novo apontador para um array que contém as quantidades de publicações do autor passado como parâmetro posteriormente
			a serem associadas a um ano
*/
int* CA_getPublicationsPerYear(int * publications, char * name)
{
	int i, r, size;
	publxCA aux;

	r=CA_checkIndex(name[0]);

	aux=authors_catalog[r];

	/*Searching name on AVL tree*/
	while(aux!=NULL){
		r=strcmp(name,aux->name);
		if(r==0)
			break;
		else if(r>0) aux=aux->right;

		else 		 aux=aux->left;
	}

	if(aux==NULL) return NULL; /*names doesn't exists or input error*/

	size = aux->nyears;

	/*Allocating memory for new table*/
	publications = (int*) malloc((size+1)*sizeof(int));

	/*copying array of publications to new struct*/
	for(i=0; i<size; i++){
		publications[i] = aux->years_npublications[i].counter;
	}
	publications[i]=-1; /*ficou acordado entre aluno e docente seria desnecessário imprimir anos em que número de publicações está a 0*/

	return publications;

}



/**
	@param um nome e um apontador para um inteiro que guardará o maior número de publicações com um coautor a conhecer
	@return um apontador para uma lista de nomes (cópias) (a lista na maioria das situações contém só um nome) dos autores com quem
			o autor passado como parâmetro mais publicou
*/
char** CA_getMaxCoAuthories (char * name , int * number_of_publications){
	int i;
	int NAUTS=0;
	int size=1;
	char **authors_names;
	publxCA aux;

	i=CA_checkIndex(name[0]);

	aux=authors_catalog[i];

	/*Searching name on AVL tree*/
	while(aux!=NULL){
		i=strcmp(name,aux->name);
		if(i==0)
			break;
		else if(i>0) aux=aux->right;

		else 		 aux=aux->left;
	}
	if(aux==NULL) return NULL; /*names doesn't exists or input error*/

	authors_names = (char**) malloc(size*sizeof(char*));
	size++;

	/*Getting the maximum value for a co relation betwen the author and his co authors*/
	for(i=0; aux->co_authors[i].counter==aux->maximus; i++){ /*optimization*/
			authors_names = (char**) realloc(authors_names, size*sizeof(char*));
			authors_names[NAUTS] = (char*) malloc(MAX_NAME_SIZE*sizeof(char));
			strcpy(authors_names[NAUTS], aux->co_authors[i].coaut->name);
			size++; NAUTS++;
	}
	authors_names[NAUTS]=NULL;

	*number_of_publications=aux->maximus; /*passing to modul main value of max co authories*/
	return authors_names;
}



/**
	@param um intervalo fechado de anos e um inteiro size que explicamos em baixo
	@return um apontador para uma lista de cópias de nomes dos autores que publicaram em cada ano do intervalo fechado
			passado como parâmetro, adicionalmente guarda no inteiro size o tamanho dessa lista de autores
*/
char** CA_AuthorsYearsInterval(int minYEAR, int maxYEAR, int * size)
{
	int i;
	int number_of_years=(maxYEAR-minYEAR)+1;
	char **authors_names;
	publxCA aux;

	authors_names = (char**) malloc(sizeof(char*));
	*size=0;

	for(i=0; i<27; i++){
		aux=authors_catalog[i];
		authors_names = CA_searchInTree(aux,minYEAR,maxYEAR,number_of_years,authors_names,size);
	}

	return authors_names;
}


/**
	@param um apontador para um nodo da AVL, um intervalo fechado de anos um inteiro que contém o tamanho do intervalo fechado de anos
		   (number_of_years), um apontador para um array de nomes (cópias) a ser atualizado e o tamanho de array.
	@return um novo apontador para o array de nomes que é atualizado a cada chamada da função
*/
/*Querie 9.*/
/*OPTIMIZATION: requires that all yearNPUBLX structures are order from the minor year to the biggest... in each node of the AVL tree*/
static char** CA_searchInTree(publxCA author, int minYEAR, int maxYEAR, int number_of_years, char* authors_names[], int * size)
{
	int i=0;
	int first=0;
	int years_compared=0;
	int sizeArrayPointers=0;
	int inc_minYEAR=minYEAR;
	int flagFoundAuthor=1;

	if(author != NULL){
			/*if the number of diferent years in which the author published is minor that the number of years in the interval given he is automaticly exclued optimizes search time*/	
			if(author->nyears>=number_of_years){

				for(i=0; i<author->nyears && author->years_npublications[i].year!=minYEAR; i++); /*find minYEAR of interval*/
				first=i;

				if(author->years_npublications[i].year==minYEAR){

						/*comparing each year of interval with array of publications given a certain author*/
						for(inc_minYEAR=minYEAR; i<author->nyears && inc_minYEAR!=maxYEAR; i++, inc_minYEAR++){
							if(author->years_npublications[i].year != inc_minYEAR){
								flagFoundAuthor=-1; /*if one year doesn't exists the author is automaticly exclued*/
								break;
							}
						}

						years_compared=(i-first)+1;
						/*if we not found nothing diferent then we can conclude that the years are all equals*/
						if(years_compared==number_of_years && author->years_npublications[i].year==inc_minYEAR && inc_minYEAR==maxYEAR && flagFoundAuthor!=-1){
							/*then we add a nem author who published in every single year of the years interval*/
							i=(*size);
							sizeArrayPointers=(*size+1);

							authors_names = (char**) realloc(authors_names, sizeArrayPointers*sizeof(char*));
							authors_names[i] = (char*) malloc(MAX_NAME_SIZE*sizeof(char));

							strcpy(authors_names[i], author->name);

							(*size)++;
						}
				}

			}

			authors_names = CA_searchInTree(author->left,minYEAR,maxYEAR,number_of_years,authors_names,size);
			authors_names = CA_searchInTree(author->right,minYEAR,maxYEAR,number_of_years,authors_names,size);

	}

	return authors_names;
}


/**
	@param um ano de uma publicação um apontador para um valor N (tamanho da lista a produzir) e um apontador para um array que
		   guardará as quantidades de publicações associadas a um array de nomes de autores (explicado em baixo)
	@return um array de cópias de nomes que correspondem aos N autores que mais publicaram no ano passado como parâmetro
			as respetivas quantidades de publicações ficaram guardadas no array values
*/
/*Querie 12.*/
char** CA_getNAuthorsInYear(int year_of_publication, int * N, int * values)
{
	int index, i;
	char **authors_names;

	authors_names = (char**) malloc((*N+1)*sizeof(char*));

	/*first we search the given year*/
	for(index=0; years_catalog[index].year!=year_of_publication; index++);

	/*case N is bigger then the number of authors that published in that year we set the value*/
	if(*N>years_catalog[index].number_of_authors) *N=years_catalog[index].number_of_authors;

	/*years_catalog[index].number_of_authors: provides situations in which N>number_of_authos that have published in that year*/
	for(i=0; i<*N && i<years_catalog[index].number_of_authors; i++){
		authors_names[i] = (char*) malloc(MAX_NAME_SIZE*sizeof(char));
		strcpy(authors_names[i], years_catalog[index].auts[i].coaut->name);

		values[i]=years_catalog[index].auts[i].counter;
	}
	authors_names[i]=NULL;

	return authors_names;
}



/**
	@param um nome e um ano de publicações
	@return um float que representa a percentagem de publicações do autor passado como parâmetro relativamente ao total
			de publicações do ano (também passado como parâmetro)
*/
/*Querie 13.*/
float CA_getPercentilsInYear(char * name, int year_of_publication)
{
	float r=0;
	int i=0;
	int howmany=0;
	publxCA aux;

	i=CA_checkIndex(name[0]);
	aux=authors_catalog[i];

	/*Searching name on AVL tree*/
	while(aux!=NULL){
		i=strcmp(name,aux->name);
		if(i==0)
			break;
		else if(i>0) aux=aux->right;

		else 		 aux=aux->left;
	}
	if(aux==NULL) return -1;

 	/*getting the number of publications of the given author in the give year*/
	for(i=0; i<aux->nyears; i++)
		if(aux->years_npublications[i].year==year_of_publication){
			howmany=aux->years_npublications[i].counter;
			break;
		}

	i=E_getPublicationsInYear(year_of_publication); /*i is the total of publications for the given year*/


	if(i==0) r=0;
	 else r = (float)howmany/(float)i;

	return r;
}



/**
	@para um inteiro n onde guardaremos o valor máximo de publicações de um autor
	@return o nome do autor com maior total de publicações no ficheiro
*/
/*Extra querie 99.*/
static publxCA maxp=NULL;
char* CA_getMaximumPublications(int * n)
{
	int i;
	publxCA aux;

	maxp=NULL;
	*n=0;


	for(i=0; i<27; i++){
		aux=authors_catalog[i];
		CA_searchMaxInTree(aux,n);
	}

	*n=maxp->total_publications;

	return maxp->name;
}



/**
	@param um apontador para um nodo da AVL e um apontador para um inteiro que vai atualizando o máximo
	@return um nodo da AVL. Função que procura em todas as árvores o máximo valor para total de publicações de um autor
*/
static publxCA CA_searchMaxInTree(publxCA author, int * max)
{

	if(author!=NULL){
		if(author->total_publications>*max){
			maxp=author;
			*max=author->total_publications;
		}

		CA_searchMaxInTree(author->left,max);
		CA_searchMaxInTree(author->right,max);
	}

	return author;
}



/**
	@param um índice que corresponde a um ano de publicações
	@return um apontador para um array que contém os valores de publicações nesse ano a serem associados a um array de nomes de autores
*/
int* CA_getPublicationsInYear(int index)
{
	int j;
	int *v=NULL;
	int N = years_catalog[index].number_of_authors;

	v = (int*) malloc (N*sizeof(int));

	for(j=0; j<years_catalog[index].number_of_authors; j++)
		v[j]=years_catalog[index].auts[j].counter;

	return v;
}


/**
	@param um índice (corresponde a um ano) 
	@return o número diferente de autores que publicaram nesse ano
*/
int CA_getNumberOfAuthorsInYear(int index)
{
	return years_catalog[index].number_of_authors;
}



/**
	@param um índice (corresponde a um ano) 
	@return um array de nomes (cópias) complementa à função em cima CA_getPublicationsInYear
*/
char** CA_getNamesList(int index)
{
	char** names=NULL;
	int N = years_catalog[index].number_of_authors;
	int j;

	names = (char**) malloc(N*sizeof(char*));

	for(j=0; j<N; j++){
		names[j] = (char*) malloc(MAX_NAME_SIZE*sizeof(char));
		strcpy(names[j], years_catalog[index].auts[j].coaut->name);
	}

	return names;
}



/**
	@param um nome de um autor
	@return um apontador para um array que contém as vezes que o autor passado como parâmetro publicou associado
			a cada um dos seus coautores
*/
int* CA_getCoAutsPublications(char * name)
{
	int i=0;
	publxCA aux;
	int N=0;
	int *v;

	i=CA_checkIndex(name[0]);
	aux=authors_catalog[i];

	/*Searching name on AVL tree*/
	while(aux!=NULL){
		i=strcmp(name,aux->name);
		if(i==0)
			break;
		else if(i>0) aux=aux->right;

		else 		 aux=aux->left;
	}

	if(aux==NULL) return NULL; /*names doesn't exists or input error*/

	N=aux->number_of_co_authors;
	v = (int*) malloc(N*sizeof(int));

	for(i=0; i<N; i++)
		v[i]=aux->co_authors[i].counter;

	return v;
}

/*----------------------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------  \END   ---------------------------------------------------------------*/
/*--------------------------------------------------------------------------------------------------------------------------- */




/*----------------------------------------------------------------------------------------------------------------------------*/
/*--------------------------------------------Release memory functions--------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------*/

/**
	@return nada. Liberta memória para a estrutura de dados que arzameza as árvores do catálogo de autores
*/
void CA_ReleaseAuthorsCatalog()
{
	int i;

	for(i=0;i<27;i++)
		CA_Deallocate(authors_catalog[i]);
	free(authors_catalog);
}



/**
	@param um apontador para um nodo da árvore.
	@return nada. Liberta memória nodo a nodo da árvore AVL libertando também as respetivas variáveis de cada nodo
*/
static publxCA CA_Deallocate(publxCA tree){

	if(tree != NULL)
    {
    	free(tree->name);
    	free(tree->co_authors);
    	free(tree->years_npublications);

        CA_Deallocate(tree->left);
        CA_Deallocate(tree->right);

        free(tree);
    }

    return tree;
}



/**
	@return nada. Liberta memória para a estrutura de dados que arzameza o "catálogo de anos"
*/
void CA_ReleaseYears()
{
	int i;

	for(i=0; i<countYEAR; i++)
		free(years_catalog[i].auts);
	free(years_catalog);
}

/*----------------------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------  \END   ---------------------------------------------------------------*/
/*--------------------------------------------------------------------------------------------------------------------------- */




/*----------------------------------------------------------------------------------------------------------------------------*/
/*--------------------------------------------Close modul insertions function-------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------*/


/**
	@return nada. Fecha as inserções ao módulo através de uma flag privada ao mesmo, reforçando a robustez do código
			e o encapsulamento
*/
void CA_CloseInsertions()
{
	flagFULL=1;
}

/*----------------------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------  \END   ---------------------------------------------------------------*/
/*--------------------------------------------------------------------------------------------------------------------------- */