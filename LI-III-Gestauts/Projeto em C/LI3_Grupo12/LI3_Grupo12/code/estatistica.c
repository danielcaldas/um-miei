#include<stdio.h>
#include<stdlib.h>
#include"estatistica.h" /*modul description in estatistica.h*/


/*macros*/
#define MAX_NUMBER_AUTHORS 50


/*conretizar o tipo abstrato de dados*/
typedef struct publicxYEAR{
	int year;
	int *counter_authors; /*A very special array*/
	int max_number_authors;
	int counter_total;
}publxYEAR_TCD;
/* note: counter_authors[i]=number of publications with i+1 authors*/


/*Modul private variables*/
 static publxYEAR_TCD *datesStore;
 static int countYEAR; /*Stands for the diferent number of years that we find in the file*/
 static int min_year;
 static int max_year;
 static int flagFULL;

/*Modul private functions*/
static int E_Insertion_Sort (int year_of_publication);
static void E_Copy_Structure (int a, int b);
static void E_Copy_Array (int a, int b);



/*----------------------------------------------------------------------------------------------------------------------------------*/
/*-------------------------------------------------Init function--------------------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------------*/

/**
	@return nada. Quando chamada externamente inicializa as variáveis privadas ao módulo
*/
void E_Init()
{
	countYEAR=0;
	min_year=3000;
	max_year=0;
	flagFULL=0;
}

/*----------------------------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------  \END   ---------------------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------------*/



/*----------------------------------------------------------------------------------------------------------------------------------*/
/*-------------------------------------------------Store functions------------------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------------*/

/**
	@param um ano de uma publicação e um número de autores dessa mesma publicação (comprimento da publicação)
	@return nada. Armazena na estrutura de dados o ano da publicação e inicializa os campos desse elemento da estrutura de dados
			caso o ano ainda não existe, caso contrário atualiza os devidos campos da estrutura de dados correspondentes ao ano
			passado como parâmetro
*/
void E_Estatistica (int year_of_publication, int number_of_authors)
{

	int i, a;

	if(flagFULL==0){

		/*First year that appears in file, initialization*/
    	if(countYEAR==0){
			datesStore = (publxYEAR_TCD*) malloc (sizeof(struct publicxYEAR));
			datesStore[0].year=year_of_publication;
			datesStore[0].max_number_authors=number_of_authors;
			datesStore[0].counter_total=1;

			datesStore[0].counter_authors = (int*) malloc(MAX_NUMBER_AUTHORS*sizeof(int));

			/*Inicialize array of specific counter for diferent number of authors*/
   			for(a=0;a<MAX_NUMBER_AUTHORS;a++)
    		datesStore[0].counter_authors[a]=0;

    		/*Increment the value of an specific value of array counter_authors*/
    		datesStore[0].counter_authors[number_of_authors-1]++;

			min_year=year_of_publication;
			max_year=year_of_publication;

			countYEAR++;
			return;
		}

		/*All cases except first publication falls in this else statement*/
		else{

			for(i=0;i<countYEAR;i++)
				if(datesStore[i].year==year_of_publication){
					datesStore[i].counter_total++;
					datesStore[i].counter_authors[number_of_authors-1]++;

					/*Setting the maximum value for number of authors in a publication for a certain year*/
					if(number_of_authors>datesStore[i].max_number_authors)
			   		datesStore[i].max_number_authors=number_of_authors;

					return;
				}

			/*Finding the lower and higher year of a publication*/
    		if(year_of_publication<min_year) min_year=year_of_publication;
    		if(year_of_publication>max_year) max_year=year_of_publication;
	
			/*Reaching this point means that the year_of_publication was not found, then we need to allocate memory*/
			datesStore = (publxYEAR_TCD*) realloc(datesStore, (countYEAR+1)*sizeof(struct publicxYEAR));

			/*Initialize array that specifies number of publications per number of authors*/
			datesStore[countYEAR].counter_authors = (int*) malloc(MAX_NUMBER_AUTHORS*sizeof(int));

			/*Returns the index of the position where we write the new element of array datesStore*/
			i=E_Insertion_Sort(year_of_publication);

    		datesStore[i].year=year_of_publication;

    		datesStore[i].counter_total=1;

    		datesStore[i].max_number_authors=number_of_authors;


    		/*Inicialize array of specific counter for diferent number of authors*/
    		for(a=0;a<MAX_NUMBER_AUTHORS;a++)
    		datesStore[i].counter_authors[a]=0;

    		/*Increment the value of an specific value of array counter_authors*/
    		datesStore[i].counter_authors[number_of_authors-1]++;

    		countYEAR++;
		}
	}
}



/**
	@param um ano de uma publicação
	@return o índice onde deve ser inserido o novo campo da estrutura de dados. A função de inserção percorre o array de estruturas
	a partir do fim fazendo shifts à direita enquanto o ano que queremos inserir for menor que o da estrutura de dados que estamos a
	comparar num determinado momento
*/
static int E_Insertion_Sort (int year_of_publication)
{
	int i;

	for(i=countYEAR-1; year_of_publication < datesStore[i].year && i>=0;i--){
		E_Copy_Structure(i, i+1);
	}

	if(i==-1) return 0;
		else return i+1;
}


/*Copy position a to position b b=a*/
/**
	@param dois índices.
	@return nada. Copia a estrutura de dados da posição a para a posição b
*/
static void E_Copy_Structure (int a, int b)
{
	datesStore[b].year=datesStore[a].year;
	E_Copy_Array(a,b);
	datesStore[b].max_number_authors=datesStore[a].max_number_authors;
	datesStore[b].counter_total=datesStore[a].counter_total;
}


/**
	@param dois índices
	@return nada. Auxiliar da função acima definida, apenas fas a cópia do array counter_authors (campo dos elementos do array de estruturas)
*/
static void E_Copy_Array (int a, int b)
{
	int i;

	for(i=0;i<MAX_NUMBER_AUTHORS;i++)
	    datesStore[b].counter_authors[i]=datesStore[a].counter_authors[i];

}

/*----------------------------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------  \END   ---------------------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------------*/


/*----------------------------------------------------------------------------------------------------------------------------------*/
/*-------------------------------------------------------Get functions--------------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------------*/


/**
	@return o máximo ano em que foi publicado um artigo
*/
int E_getMaxYear ()
{
	return max_year;
}


/**
	 @return o mínimo ano em que foi publicado um artigo
*/
int E_getMinYear()
{
	return min_year;
}


/**
	@return o número diferente de anos em que foram publicados artigos (tamanho do array de estruturas de dados)
*/
int E_getCountYEAR()
{
	return countYEAR;
}


/**
	@return um apontador para um array que recolhe da estrutura de dados todos os anos diferentes em que foram publicados artigos (por ordem crescente do ano)
*/
int* E_getYearsOfPublications()
{
	int i;
	int *years = (int*) malloc(countYEAR*sizeof(int));

	for(i=0;i<countYEAR;i++)
		years[i]=datesStore[i].year;

	return years;
}


/**
	@return um apontador para uma array que recolhe da estrutura de dados a quantidade de publicações ano a ano
*/
int* E_getPublicationsPerYear()
{
	int i;
	int *publications = (int*) malloc(countYEAR*sizeof(int));

	for(i=0;i<countYEAR;i++)
		publications[i]=datesStore[i].counter_total;

	return publications;
}


/**
	@return um apontador para arrays de inteiros em que cada array é a cópia de counter_authors, array de cada estrutura de dados
			que descrimina quantidades de publicações de artigos pelo número de autores
*/
int** E_getAuthorsPublicationsTable()
{
	int i,j;
	int **counter_authors = (int**) malloc(countYEAR*sizeof(int*));

	for(i=0;i<countYEAR;i++){
		counter_authors[i] = (int*) malloc( (datesStore[i].max_number_authors+1)*sizeof(int));
		for(j=0;j<datesStore[i].max_number_authors;j++)
			counter_authors[i][j]=datesStore[i].counter_authors[j];
		counter_authors[i][j]=-1;
	}

	return counter_authors;
}


/**
	@param dois apontadores para dois inteiros, um ano e um número de autores numa publicação
	@return nada. Atualiza os inteiros cujo endereço foi passado como parâmetro colocando nesses interiros
			informação relatica à publicação mais longa do ficheiro, i.e o ano dessa publicação e o número de autores
*/
void E_getLongestPublication(int * year, int * number_of_authors){

	int max=0;
	int theyear=0;
	int i;

	for(i=0; i<countYEAR; i++)
		if(datesStore[i].max_number_authors>max){
			max=datesStore[i].max_number_authors;
			theyear=datesStore[i].year;
		}

	*year=theyear;
	*number_of_authors=max;
}


/**
	@param um ano
	@return o número de publicações desse ano
*/
int E_getPublicationsInYear(int year_of_publication){
	int i;
	int publications=0;

	for(i=0; i<countYEAR; i++)
		if(datesStore[i].year==year_of_publication){
			publications = datesStore[i].counter_total;
			break;
		}
	return publications;
}

/*---------------------------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------  \END   --------------------------------------------------------------------*/
/*---------------------------------------------------------------------------------------------------------------------------------*/



/*---------------------------------------------------------------------------------------------------------------------------------*/
/*------------------------------------------------Release memory functions---------------------------------------------------------*/
/*---------------------------------------------------------------------------------------------------------------------------------*/

/**
	@return nada. Liberta a memória da estrutrura de dados e respetivos campos alocados dinamicamente
*/
void E_Release_Mem ()
{
	int i;

	for(i=0;i<countYEAR;i++)
		free(datesStore[i].counter_authors);

	/*Releasing memory of data structure used in estatistica module*/
    free(datesStore);
}

/*---------------------------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------  \END   --------------------------------------------------------------------*/
/*---------------------------------------------------------------------------------------------------------------------------------*/


/*---------------------------------------------------------------------------------------------------------------------------------*/
/*------------------------------------------------Close Modul Function-------------------------------------------------------------*/
/*---------------------------------------------------------------------------------------------------------------------------------*/

/**
	@return nada. Fecha as inserções ao módulo através de uma flag privada ao mesmo, reforçando a robustez do código
			e o encapsulamento. Faz também operações adicionais de "aparar" o tamanho dos arrays conter_authors dos diferentes elementos
			do array de estruturas.
*/
void E_CloseInsertions()
{
	flagFULL=1;

	int i;
	int size=0;
	/*Treat exceptions*/
	for(i=0; i<countYEAR; i++){
		size=datesStore[i].max_number_authors+1;
		datesStore[i].counter_authors = realloc (datesStore[i].counter_authors, size*sizeof(int));
	}

}

/*---------------------------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------  \END   --------------------------------------------------------------------*/
/*---------------------------------------------------------------------------------------------------------------------------------*/