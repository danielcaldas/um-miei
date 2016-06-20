#ifndef _GEREALBUM_H_
#define _GEREALBUM_H_

char* code; 
char* currentdir;

// Funções visíveis para fora do módulo
void inserePessoa(char* nome, char* foto, char* facto, char* legenda, char* local,  int d, int m, int a);
void freeMem();
void gerarPaginaDeEntrada();
void gerarAlbuns();
void printEstruturas();

#endif
