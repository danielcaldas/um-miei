#ifndef _H_BIBLIOTECA
#define _H_BIBLIOTECA

/*--------------------------------MACROS E ESTRUTURA DE DADOS---------------------------------------*/

#define MAX_NAME_SIZE 75
#define MAX_LINE_SIZE 1024
#define MAX_FILE_NAME 50
#define MAX_COMAND_SIZE 15 /*tamanho do comando incrementar por excesso de 2 bytes*/

//Uma freguesia
typedef struct Freguesia{
	char nome[MAX_NAME_SIZE]; 
	int contador;   
}Freguesia;

typedef Freguesia* FREG; 


//Um concelho
typedef struct Concelho{
	char nome[MAX_NAME_SIZE]; 
	int contador;
	FREG freguesias;
	int capmaxfreguesias;  
}Concelho;


typedef Concelho* CON;

//Um distsrito
typedef struct distsrito{
	char nome[MAX_NAME_SIZE]; 
	int contador;
	CON concelhos;
	int capmaxconcelhos;
}distrito;


distrito distritos[18]; //array de estruturas distrito
int dists; 				//índice do último distrito inserido na estrutura
int flagEMPTY;			//flag que sinaliza se estrutura de dados está ou não vazia para efeitos de inserções


int hashCode(char * val);
int incrementar(char *nome[], unsigned valor);
int agregar(char* prefixo[], unsigned nivel, char* path);

#endif