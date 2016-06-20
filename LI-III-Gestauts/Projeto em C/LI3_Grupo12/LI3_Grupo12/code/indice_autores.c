#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<ctype.h>
#include"indice_autores.h" /*modul description in indice_autores.h*/

/*Macros*/
#define MAX_NAME_SIZE 70
#define DIFERENT_LETTERS 27 /*Size 27 means 26 letters (A-Z) and names started with "special" characters*/


/*Data structure*/
typedef char TreeKey;
typedef enum balancefactor { LH , EH , RH } BalanceFactor;

/*concretizar estrutura de dados*/
typedef struct publxAuthorTREE{
	TreeKey *name;
	BalanceFactor bf;
	struct publxAuthorTREE *left;
	struct publxAuthorTREE *right;
}publxAUTREE_TCD;


/*Modul private variables*/
static int countAUTHORS;
static char* smallest_name;
static unsigned int smallest_size;
static char* biggest_name;
static unsigned int biggest_size;
static int sum_names_length;
static int flagFULL;

/*Array of pointers to AVL trees*/
static publxAUTREE_TCD** pointersToAVLtrees;
static int* numberAuthorsEachLetter;

         

/*Modul private functions*/

/*Insertion functions*/
static publxAUTREE_TCD* IA_Insert_AuthorsTree(publxAUTREE_TCD* authorsTREE, char * name , int *cresceu, int avlTreeIndex);

/*AVL tree's manipulation functions*/
static publxAUTREE_TCD* IA_insertRight(publxAUTREE_TCD* authorsTREE, char * name , int *cresceu, int avlTreeIndex);
static publxAUTREE_TCD* IA_insertLeft(publxAUTREE_TCD* t, char * name, int *cresceu, int avlTreeIndex);
static publxAUTREE_TCD* IA_rotateRight(publxAUTREE_TCD* authorsTREE);
static publxAUTREE_TCD* IA_rotateLeft(publxAUTREE_TCD* authorsTREE);
static publxAUTREE_TCD* IA_balanceRight(publxAUTREE_TCD* authorsTREE);
static publxAUTREE_TCD* IA_balanceLeft(publxAUTREE_TCD* authorsTREE);


/*Other auxiliar functions*/
static void IA_BiggerSmallerNames (char * name);
static int IA_checkIndex (char c);

/*A fuction that colects names into an array from a AVL tree*/
static char** IA_CopyTree(char** names, publxAUTREE_TCD* a);

/*Release memory auxiliar*/
static publxAUTREE_TCD* IA_DeallocateTREE (publxAUTREE_TCD* authorsTREE);



/*----------------------------------------------------------------------------------------------------------------------------*/
/*--------------------------------------------------Initialization Functions--------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------*/

/**
	@return nada. Quando chamada externamente inicializa as variáveis privadas ao módulo
*/
void IA_Init()
{
	int i;

	flagFULL=0;

	countAUTHORS=0;

	smallest_size=100;
	biggest_size=0;
	sum_names_length=0;

	biggest_name = (char*) malloc (MAX_NAME_SIZE*sizeof(char));
	smallest_name = (char*) malloc (MAX_NAME_SIZE*sizeof(char));

	pointersToAVLtrees = (publxAUTREE_TCD**) malloc(DIFERENT_LETTERS*sizeof(publxAUTREE_TCD*));
	for(i=0;i<27;i++)
		pointersToAVLtrees[i]=NULL;

	numberAuthorsEachLetter = (int*) malloc(DIFERENT_LETTERS*sizeof(int));
}

/*----------------------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------  \END   ---------------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------*/




/*----------------------------------------------------------------------------------------------------------------------------*/
/*--------------------------------------------------Insertion functions-------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------*/

/**
	@param um array de nomes de autores, o numero de autores nesse array
	@return nada. Faz chamadas à função que faz a inserção dos nomes na estrutura de dados do módulo atulizado sempre os apontadores
			para as raízes das respetivas árvores
*/
void IA_Store_Authors (char *author_names[], int number_of_authors)
{
	int i;
	int cresceu=0;
	int avlTreeIndex=0;

	if(flagFULL==0){

		for(i=0; i<number_of_authors; i++){
			/*Who do we find the index of the pointer to our proper AVL Tree of authors?...*/
			avlTreeIndex=IA_checkIndex(author_names[i][0]);
			pointersToAVLtrees[avlTreeIndex] = IA_Insert_AuthorsTree(pointersToAVLtrees[avlTreeIndex], author_names[i], &cresceu, avlTreeIndex); /*Insert a name in the authors tree*/
		}
	}

}


/**
	@param um apontador para a ráiz da árvore, um nome a inserir, um inteiro para sinalizar o crecimento da árvore (caso existente)
		  o índece do apontador para a ráiz desta árvore no array de árvores (estrutura pricipal do módulo)
	@return um apontador para a ráiz da árvore. Insere o nome na devida árvore caso ainda não exista
*/
static publxAUTREE_TCD* IA_Insert_AuthorsTree(publxAUTREE_TCD* authorsTREE, char * name , int *cresceu, int avlTreeIndex) {

	int alpha=0;
	*cresceu=0;

	if (authorsTREE==NULL){ /*A new name appears in the AVL tree...*/

	   	authorsTREE = (publxAUTREE_TCD*) malloc(sizeof(struct publxAuthorTREE));

	   	/*Allocating memory for new name in specific AVL tree*/
		authorsTREE->name = (char*) malloc(MAX_NAME_SIZE*sizeof(char));

		strcpy(authorsTREE->name,name);

		authorsTREE->left=authorsTREE->right=NULL;
		authorsTREE->bf=EH;
		*cresceu=1;

		countAUTHORS++;

		/*Increment number of authors started by specific letter*/
		numberAuthorsEachLetter[avlTreeIndex]++;

		IA_BiggerSmallerNames(name);
	}

	else{

		/*Find if name stays at right or left or eventualy is the same name in the node*/
		alpha=strcmp(name, authorsTREE->name);

		if(alpha==0) return(authorsTREE);

		else if (alpha>0)
		{		
				authorsTREE = IA_insertRight(authorsTREE,name,cresceu,avlTreeIndex);
		}

		else if (alpha<0)
		{
				authorsTREE = IA_insertLeft(authorsTREE,name,cresceu,avlTreeIndex);
		}

	}

	return(authorsTREE);
}

/*----------------------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------  \END   ---------------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------*/





/*----------------------------------------------------------------------------------------------------------------------------*/
/*---------------------------------------------AVL tree's manipulation functions----------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------*/

/*fonte: slides de Algoritmos e Complexidade 2013/2014 LEI 2ºAno*/

/**
	@param um apontador para um nodo da árvore, um nome a inserir, um inteiro para sinalizar o crecimento da árvore (caso existente)
		  o índece do apontador para a ráiz desta árvore no array de árvores (estrutura pricipal do módulo)
	@return um apontador para um nodo da árvore. Faz a respetiva inserção "à direita" e devidas operações de balanceamento 
*/
static publxAUTREE_TCD* IA_insertRight(publxAUTREE_TCD* authorsTREE, char * name , int *cresceu, int avlTreeIndex) {

		authorsTREE->right = IA_Insert_AuthorsTree(authorsTREE->right,name,cresceu,avlTreeIndex);

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
							authorsTREE = IA_balanceRight(authorsTREE);
							*cresceu = 0;
		}
		return authorsTREE;
}


/**
	@param um apontador para um nodo da árvore, um nome a inserir, um inteiro para sinalizar o crecimento da árvore (caso existente)
		  o índece do apontador para a ráiz desta árvore no array de árvores (estrutura pricipal do módulo)
	@return um apontador para um nodo da árvore. Faz a respetiva inserção "à esquerda" e devidas operações de balanceamento 
*/
static publxAUTREE_TCD* IA_insertLeft(publxAUTREE_TCD* authorsTREE, char * name, int *cresceu, int avlTreeIndex) {

		authorsTREE->left = IA_Insert_AuthorsTree(authorsTREE->left,name,cresceu,avlTreeIndex);

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
							authorsTREE = IA_balanceLeft(authorsTREE);
							*cresceu = 0;
		}
		return authorsTREE;
}


/**
	@param um apontador para um nodo da árvore
	@return um apontador para um nodo da árvore. Executa um rotação à direita.
*/
static publxAUTREE_TCD* IA_rotateRight(publxAUTREE_TCD* authorsTREE) {
	publxAUTREE_TCD* aux;

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


/**
	@param um apontador para um nodo da árvore
	@return um apontador para um nodo da árvore. Executa um rotação à esquerda.
*/
static publxAUTREE_TCD* IA_rotateLeft(publxAUTREE_TCD* authorsTREE) {
	publxAUTREE_TCD* aux;

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


/**
	@param um apontador para um nodo da árvore
	@return um apontador para um nodo da árvore. Faz as operações de balanceamento quando a árvore é mais "pesada" do seu lado direito
*/
static publxAUTREE_TCD* IA_balanceRight(publxAUTREE_TCD* authorsTREE) {

		if (authorsTREE->right->bf==RH) {
			/*Simple left rotation*/
			authorsTREE = IA_rotateLeft(authorsTREE);
			authorsTREE->bf = EH;
			authorsTREE->left->bf = EH;
		}
		else {
			/*Double rotation*/
			authorsTREE->right = IA_rotateRight(authorsTREE->right);
			authorsTREE = IA_rotateLeft(authorsTREE);

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


/**
	@param um apontador para um nodo da árvore
	@return um apontador para um nodo da árvore. Faz as operações de balanceamento quando a árvore é mais "pesada" do seu lado esquerdo
*/
static publxAUTREE_TCD* IA_balanceLeft(publxAUTREE_TCD* authorsTREE) {

		if (authorsTREE->left->bf==LH){
			/*Simple right rotation*/
			authorsTREE = IA_rotateRight(authorsTREE);
			authorsTREE->bf = EH;
			authorsTREE->right->bf = EH;
		}
		else {
			/*Double rotation*/
			authorsTREE->left = IA_rotateLeft(authorsTREE->left);
			authorsTREE = IA_rotateRight(authorsTREE);

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
/*-----------------------------------------------Release memory functions-----------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------*/

/*Realease memory used by authorsTREE requires a method tha invoques the deallocate function due to the pointer passed as argument*/
/**
	@return nada. Faz um ciclo de chamadas para libertar memória ocupada por cada árvore do array de árvores
*/
void IA_ReleaseAuthorsTree()
{
	int i;

	for(i=0; i<27; i++)
		IA_DeallocateTREE(pointersToAVLtrees[i]);

	free(biggest_name);
	free(smallest_name);
	free(pointersToAVLtrees);
	free(numberAuthorsEachLetter);
}


/**
	@param um apontador para a raíz de uma árvore
	@return um apontador para um nodo da árvore. Liberta memória de cada nodo da árvore atrvés de uma travessia
*/
static publxAUTREE_TCD* IA_DeallocateTREE (publxAUTREE_TCD* authorsTREE)
{
	if(authorsTREE != NULL)
    {
        IA_DeallocateTREE(authorsTREE->left);
        IA_DeallocateTREE(authorsTREE->right);
        free(authorsTREE);
    }
    return authorsTREE;
}

/*----------------------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------  \END   ---------------------------------------------------------------*/
/*--------------------------------------------------------------------------------------------------------------------------- */




/*----------------------------------------------------------------------------------------------------------------------------*/
/*-----------------------------------------------Get functions----------------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------*/

/**
	@return o número autores diferentes no ficheiro
*/
int IA_getCountAUTHORS()
{
	return countAUTHORS;
}


/**
	@return um apontador de uma string que é uma cópia do nome mais pequeno encontrado no ficheiro
*/
char* IA_getSmallest()
{
	char *s = (char*) malloc(MAX_NAME_SIZE*sizeof(char));
	
	strcpy(s,smallest_name);
	return s;
}


/**
	@return um apontador de uma string que é uma cópia do nome maior encontrado no ficheiro
*/
char* IA_getBiggest()
{
	char *s = (char*) malloc(MAX_NAME_SIZE*sizeof(char));

	strcpy(s,biggest_name);
	return s;
}


/**
	@return o tamanho do nome mais pequeno
*/
unsigned int IA_getSmallestSize()
{
	return smallest_size;
}

/**
	@return o tamanho do nome maior
*/
unsigned int IA_getBiggestSize()
{
	return biggest_size;
}


/**
	@return devolve o comprimento médio de todos os nomes diferentes encontrados
*/
int IA_getNamesMediumLength()
{
	int m;
	
	return (m=sum_names_length/countAUTHORS);
}


/**
	@param receve uma letra de A a Z ou o caratér '*' que significa "Nomes Estrangeiros"
	@return o total de nomes começados pela letra passada como parâmetro
*/
int IA_getTotalLetter(char c)
{
	int avlTreeIndex;

	if(c=='*') avlTreeIndex=26;
	 else avlTreeIndex = IA_checkIndex(c);

	return numberAuthorsEachLetter[avlTreeIndex];
}



/**
	@param receve uma letra de A a Z ou o caratér '*' que significa "Nomes Estrangeiros"
	@return um apontador para um array de nomes que contém a cópia dos nomes da árvore que corresponde à letra passada como parâmetro
*/
int index; /*allows to copy the names from the tree without conflits for the index where we want to insert the name*/
char** IA_getAuthorsListNames(char c)
{	
	int avlTreeIndex;
	char** names;
	publxAUTREE_TCD* a;
	
	index=0;

    if(c=='*') avlTreeIndex=26;
	 else avlTreeIndex=IA_checkIndex(c);

	names = (char**) malloc( (numberAuthorsEachLetter[avlTreeIndex]+1)*sizeof(char*));	 

	a=pointersToAVLtrees[avlTreeIndex];

	names=IA_CopyTree(names,a);

	return names;
}


/**
	@param um apontador para um array de nomes e um nodo de uma árvore
	@return o apontador para o array de nomes atualizado. Função que na realidade faz a travessia da árvore copiando nome a nome de cada nodo
			da árvore para o array de nomes passado sucessivamente como parâmetro
*/
static char** IA_CopyTree(char** names, publxAUTREE_TCD* a)
{

    if(a != NULL)
    {
        names[index] = (char*) malloc(MAX_NAME_SIZE*sizeof(char));
        strcpy(names[index], a->name);
        index++;

        names = IA_CopyTree(names,a->left);
        names = IA_CopyTree(names,a->right);
    }

    return names;

}

/*----------------------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------  \END   ---------------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------*/




/*----------------------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------Other auxiliar functions------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------*/

/*The implementation guarantees that he only compares one time each diferent name in the file*/

/**
	@param um nome (que foi inserido numa das árvoree AVL)
	@return nada. Efetua cálculos intermédios tais como: cálculo do maior e mais pequeno nome e média do comprimento dos nomes
*/
static void IA_BiggerSmallerNames (char * name)
{
	unsigned int t;

	/*Searching for the smaller name and the bigger name in the file*/
    t=strlen(name);

    /*For calculating the medium length of the diferent names of authors*/
    sum_names_length+=t;

    if(t>biggest_size){
       strcpy(biggest_name, name);
       biggest_size=t;
    }

    t=strlen(name);
    if(t<smallest_size){
       strcpy(smallest_name, name);
       smallest_size=t;
    }
}


/**
	@param uma letra (carater) de A a Z (KEY)
	@return o índice da árvore onde o nome começado pela letra passada como parâmetro deve ser inserido.
	(uma espécie de HashCode())
*/
static int IA_checkIndex (char c)
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
/*----------------------------------------------Close modul function----------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------*/


/**
	@return nada. Fecha as inserções ao módulo através de uma flag privada ao mesmo, reforçando a robustez do código
			e o encapsulamento
*/
void IA_CloseInsertions()
{
	flagFULL=1;
}

/*----------------------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------  \END   ---------------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------*/