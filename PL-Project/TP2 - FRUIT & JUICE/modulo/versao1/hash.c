#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "hash.h"
#include "const.h"

#define MIN_SIZE 10
#define BASE 10
#define FALSE 0
#define TRUE 1

// Estrutura de dados para a tabela de simbolos
typedef struct Variavel{
	int type;
	char *nome;
	int valor;
	int size;
	int endereco;
	int *array;
} *Var;


Var *hash; // Variavel global da tabela de simbolos
int size = 0; // Tamanho da hash ou numero de simbolos na tabela de simbolos
int sp = 0; // Apontador para o proximo endereco livre da stack

/**
* Função de inicialização da tabela de símbolos
*/
void initHash() {
	int i;

	size = MIN_SIZE;

	hash = (Var*) malloc(MIN_SIZE * sizeof(Var));
	
	for (i = 0; i<MIN_SIZE; i++){
		hash[i] = NULL;
	}
}

/**
* Função que calcula posicao de variavel na hash e devolve inteiro
* @param nome, string com nome da variavel
* @return inteiro, posicao da variavel na hash
*/
int getPos(char *nome) {
	
	int n = strlen(nome);
	int i, hash = 0;
	
	for(i = 0; i < n; i++){
		hash += nome[i] * BASE^(n-1-i);
	}
	
	return hash;
}

/**
* Insere na hash uma variavel array sem inicializacao
* @param type, código que representa o tipo da variável
* @param nome, nome da varivel (a ser)
* @param tam, tamanho com que foi declarado o array
*/
void insertHashASI(int type, char *nome, int tam) {
	int i=0;
	int p;
	
	p = getPos(nome);
	
	if(p < size){
		if(hash[p] == NULL){
			hash[p] = (Var)malloc(sizeof(struct Variavel));
		} else {
			while (hash[p] != NULL) {
				p++;
			}
			hash[p] = (Var)malloc(sizeof(struct Variavel));
		}
	} else {
		hash = (Var *)realloc(hash, (p+1) * sizeof(Var));
		for (i = size+1; i <= (p+1); i++){
			hash[i] = NULL;
		}
		hash[p] = (Var)malloc(sizeof(struct Variavel));
		size = p+1;
	}
	
	hash[p]->type = type;
	hash[p]->nome = nome;
	hash[p]->size = tam;
	hash[p]->endereco = sp;
	sp = sp + hash[p]->size;
	hash[p]->array = NULL;
}

/*
* Insere na Hash uma variavel array com inicializacao
* @param type, tipo da variavel
* @param nome, nome da variavel
* @param tam, tamanho com que foi declarado o array (porque pode diferir de nelems!)
* @param a, o array
* @param nelems, numero de elementos inseridos no array 
*/
void insertHashACI(int type, char *nome, int tam, int *a, int nelems)
{
	int i = 0;
	int p;
	
	p = getPos(nome);
	
	if(p < size){
		if(hash[p] == NULL){
			hash[p] = (Var)malloc(sizeof(struct Variavel));
		} else {
			while (p < size && hash[p] != NULL) {
				p++;
			}
			if (p>size) {
				hash = (Var *)realloc(hash, (p+1) * sizeof(Var));
				for (i = size+1; i <= (p+1); i++){
					hash[i] = NULL;
				}
				hash[p] = (Var)malloc(sizeof(struct Variavel));
				size = p+1;
			} else {
				hash[p] = (Var)malloc(sizeof(struct Variavel));
			}
		}
	} else {
		hash = (Var *)realloc(hash, (p+1) * sizeof(Var));
		for (i = size+1; i <= (p+1); i++){
			hash[i] = NULL;
		}
		hash[p] = (Var)malloc(sizeof(struct Variavel));
		size = p+1;
	}
	
	hash[p]->type = type;
	hash[p]->nome = nome;
	hash[p]->size = tam;
	hash[p]->endereco = sp;
	sp = sp + hash[p]->size;
	
	hash[p]->array = (int *)malloc(size * sizeof(int));
	for(i = 0; i < nelems; i++){
		hash[p]->array[i] = a[i];
	}
}

/**
 * Insere uma varivel sem inicializacao
 * @param type, o tipo de dados
 * @param nome, nome da variavel
 */
void insertHashVSI(int type, char * nome) {
	int i = 0;
	int p = getPos(nome);
	
	if(p < size){
		if(hash[p] == NULL){
			hash[p] = (Var)malloc(sizeof(struct Variavel));
		} else {
			while (hash[p] != NULL) {
				p++;
			}
			hash[p] = (Var)malloc(sizeof(struct Variavel));
		}
	} else {
		hash = (Var *)realloc(hash, (p+1) * sizeof(Var));
		for (i = size+1; i <= (p+1); i++){
			hash[i] = NULL;
		}
		hash[p] = (Var)malloc(sizeof(struct Variavel));
		size = p+1;
	}
	
	hash[p]->type = type;
	hash[p]->nome = nome;
	hash[p]->valor = 0;
	hash[p]->size = 1;
	hash[p]->endereco = sp;
	hash[p]->array = NULL;

	sp = sp + hash[p]->size;
}

/**
 * Insere uma varivel com inicializacao
 * @param type, tipo de dados
 * @param nome, o nome da variavel
 * @param valor, o valor atribuido a variavel
*/
void insertHashVCI(int type, char *nome, int valor) {
	
	int i = 0;
	int p = getPos(nome);
	
	if(p < size){
		if(hash[p] == NULL){
			hash[p] = (Var)malloc(sizeof(struct Variavel));
		} else {
			while (hash[p] != NULL) {
				p++;
			}
			hash[p] = (Var)malloc(sizeof(struct Variavel));
		}
	} else {
		hash = (Var *)realloc(hash, (p+1) * sizeof(Var));
		for (i = size+1; i <= (p+1); i++){
			hash[i] = NULL;
		}
		hash[p] = (Var)malloc(sizeof(struct Variavel));
		size = p+1;
	}
	
	hash[p]->type = type;
	hash[p]->nome = nome;
	hash[p]->valor = valor;
	hash[p]->size = 1;
	hash[p]->endereco = sp;
	hash[p]->array = NULL;
	sp = sp + hash[p]->size;
}

/**
 * Dado um array gera o código para a VM????????????????????????????????????
 * @param lista, array de valores
 * @param posicao, endereco base do array
 * @param size, tamanho do array
 * @param fp, descritor do ficheiro de destino
*/
void writeToFile(int *lista, int posicao, int size, FILE *fp){
	int nconcats = 0;
	fprintf(fp,"PUSHS \"]\"\n"); nconcats++;
	int i = posicao + size-1;
	
	while(i > posicao){
		fprintf(fp,"PUSHG %d\n",i); nconcats++;
		fprintf(fp,"STRI\n");
		fprintf(fp,"PUSHS \",\"\n"); nconcats++;
		i--;
	}
	fprintf(fp,"PUSHG %d\n",i); nconcats++;
	fprintf(fp,"STRI\n");
	fprintf(fp,"PUSHS \"[\"\n"); nconcats++;
	
	nconcats--;
	while(nconcats>0){
		fprintf(fp,"CONCAT\n");
		nconcats--;
	}
	fprintf(fp,"WRITES\n");
}

/*
 * Gera as instruções relativas à construção do array para a VM
 * @param nome, nome da variavel array
 * @param fp, descritor do ficheiro destino
*/
void generateCodeA(char *nome, FILE *fp){
	int key = getPos(nome);
	
	while(key < size && hash[key] != NULL) {
		if(strcmp(hash[key]->nome,nome) == 0)
			writeToFile(hash[key]->array, hash[key]->endereco, hash[key]->size, fp);
		key++;
	}
}

/**
 * Verifica se uma dada variavel esta declarada na hash
 * @param nome, nome da variavel
 * @return FALSE se não existir TRUE se existir
*/
int containsValue(char *nome){
	int key = getPos(nome);
	int res = FALSE;
	
	if(hash[key] == NULL) { return FALSE; }
	else {
		while( res == 0 && key < size && hash[key] != NULL) {
			if(strcmp(hash[key]->nome,nome) == 0)
				res = TRUE;
			key++;
		}
	}
	return res;
}

/*
 * Verifica se a variavel e simples
 * @param nome, nome da variavel
 * @return TRUE caso seja simples, FALSE caso contrario
*/
int varSimple(char *nome){
	int key = getPos(nome);
	
	while(key < size && hash[key] != NULL){
		if(strcmp(hash[key]->nome,nome)==0)
			return (hash[key]->array == NULL);
		key++;
	}
	return FALSE;
}

/*
 * Dado o nome de uma variavel array vai buscar o endereco de um elemento seu,  na pilha
 * @param nome, nome da variavel array
 * @param pos, posicao sobre a qual queremos obter endereco
 * @return endereco da variavel
 */
int findEnderecoA(char* nome, int pos){
	int key = getPos(nome);
	
	while(key < size && hash[key] != NULL){
		if(strcmp(hash[key]->nome,nome)==0)
			return (hash[key]->endereco + pos - 1);
		key++;
	}
	return 0;
}

/*
 * Dado o nome de uma variavel vai buscar o seu tamanho
 * @param nome, nome da variavel
 * @return tamanho da variavel (1 caso seja variavel simples, o tamanho do array caso a var seja array)
 */
int getVarSize(char *nome){
	int key = getPos(nome);
	int res = 0;
	
	while(res == 0 && key < size && hash[key] != NULL){
		if(strcmp(hash[key]->nome,nome)==0)
			res = hash[key]->size;
		key++;
	}
	return res;
}

/*
 * Dado o nome de uma variavel vai buscar o seu endereco na pilha
 * @param nome, nome da variavel
 * @return endereco da variavel
 */
int findEnderecoV(char *nome){
	int key = getPos(nome);
	
	while(key < size && hash[key] != NULL){
		if(strcmp(hash[key]->nome,nome)==0)
			return hash[key]->endereco;
		key++;
	}
	return 0;
}

/*
 * Sabendo que a variavel existe e que a posicao que estamos a tentar aceder existe vai buscar o valor do array nessa posicao
 * @param nome, nome do array
 * @param posicao, posicao do array sobre a qual queremos obter o valor
*/
int getArrValue(char *nome, int posicao) {
	int key = getPos(nome);

	while(key < size && hash[key] != NULL){
		if(strcmp(hash[key]->nome,nome)==0)
			return hash[key]->array[posicao];
		key++;
	}
	return 0;
}

/*
 * Verifica se a posicao do array que estamos a tentar aceder e valida
 * @param nome, nome da variavel
 * @param TRUE, caso posicao seja valida FALSE caso contrario
 */
int validPos(char *nome, int posicao) {
	int key = getPos(nome);

	while(key < size && hash[key] != NULL){
		if(strcmp(hash[key]->nome,nome)==0)
			return (posicao < hash[key]->size);
		key++;
	}
	return FALSE;
}

/*
 * Sabendo que a variavel existe vai buscar o valor da variavel
 * @param nome, nome da variavel
 * @return valor da variavel
 */
int getVarValue(char *nome) {
	int key = getPos(nome);

	while(key < size && hash[key] != NULL){
		if(strcmp(hash[key]->nome,nome)==0)
			return hash[key]->valor;
		key++;
	}
	return 0;
}

