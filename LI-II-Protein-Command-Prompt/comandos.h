#ifndef ___COMANDOS_H___
#define ___COMANDOS_H___

/*Librarias*/
#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<ctype.h>
#include"erro.h"

/*macros*/
#define MAX_SIZE 5000
#define PASSOU 1 
#define NAO_PASSOU 2
#define EXISTE 1
#define NAO_EXISTE 2
#define SIM 1
#define NAO 2

/*structs*/
struct proteina { /*estrutura que declara o tamanho e a sequencia atual*/
	int tamanho;
	char seq[MAX_SIZE];
        int tem_coordenadas;/*Se for 1 imprime, caso contrário ou não existe seq de A's e B's ou a mesma foi atualizada*/
        int tem_dobra;/*assim que uma seq sofre operações de dobragem a variavel toma valor SIM*/
        int num_coords;/*dá o número de coordenadas atuais*/
        int esta_colocada;/*uma flag que quando os dados passam nos testes do comando validar seq_esta_bem_colocada toma valor 1*/
        char compactada[MAX_SIZE]; /*string que contem uma sequencia de A's e B's na forma compactada*/
        int conta_conteudo_entre_parentesis[MAX_SIZE]; /*guarda os valores dos conteudos de parentesis de uma seq pela ordem que estes aparecem na seq*/
}proteina;

typedef struct COORDS{ /*declaração global da estrutura das coordenadas*/
        int x;
        int y;
}COORDS;

COORDS coord[15000];

struct pos{
      int ortogonal[MAX_SIZE]; /*guarda a soma de dois indices que formam uma ligação ortogonal*/
      int tamanho;
}pos;

/*Protótipos das funções*/


/**
   @param recebe uma string que contem as coordenadas
   @return guarda os argumentos da sring excluindo os espaços
*/
char * strsep(char **stringp, const char *delim);

/*prot.c*/
void interpretador();
int interpretar(char *linha);

/*cmd_seq*/
int ja_existe_sequencia();
char *obtem_sequencia();
int nao_tem_so_A_B (char * args);
int numero_de_argumentos(char * arg);
int seq_nao_tem_letras (char * args);
int erro_numero_de_parentesis (char * args);
int conteudo_entre_parentesis (char * args);
int seguinte_e_numero (char * args);
int testa_compactada_valida (char * args);
void descompactar_e_guardar (char * args);
int cmd_seq(char *args);

/*cmd_coords*/
int conta_coordenadas (char * arg);
void guarda_coordenadas (char *argc);
char *imprime_coordenadas ();
int verifica_coordenadas(char *argc);
int coordenadas_validas(char *argc);
int cmd_coords (char * args);

/*cmd_dobrar*/
int instrucoes_dobragem_validas(char * arg);
void dobra_e_guarda_coordenadas (char * arg);
int cmd_dobrar (char * args);

/*cmd_validar*/
int testa_coordenadas_sao_validas ();
int cmd_validar (char * args);

/*cmd_compactar*/
char *obtem_seq_compactada();
int conta_pares_extra (int i);
int compacta_nivel_um ();
int compacta_nivel_dois ();
int conta_triplas_extra (int i);
int compacta_nivel_tres ();
void compactar_sequencia();
int cmd_compactar (char * args);

/*cmd_gravar*/
void gravar (char * args);
int cmd_gravar (char * args);

/*cmd_latex*/
int x_minimo ();
int x_maximo ();
int y_minimo ();
int y_maximo ();
void criar_latex(char * nome_fich);
int cmd_latex (char * args);

/*cmd_energia*/
int testa_argumentos (char * args);
int testa_posicao (int i, int j);
int verifica_ortogonalidade (int X, int Y, int j);
void grava_indices_ortogonais (int i, int j);
void procura_ligacoes_ortogonais (int i, int * conta_ligacoes);
int calcula_imprime_energia (char * args);
int cmd_energia (char * args);

/*cmd_reparar*/
int c_validar (char * args);
int procura_coordenada_sobreposta(char * dob);
void guarda_pedaco_a_partir_ind_err(char * dob, char * pedaco, int dob_err);
int procura_erro (char * args);
int cmd_reparar (char * dobragens);

#endif
