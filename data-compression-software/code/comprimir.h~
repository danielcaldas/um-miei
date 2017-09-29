#ifndef _H_COMPRIMIR
#define _H_COMPRIMIR


/*-------------------COMPRIMIR------------------------------*/
/*variáveis globais e estruturas de dados*/
typedef struct simbolo{
   char byte;
   float  ocorrencias;
   int* codigo;
   int bit;
   float probabilidade;
}Bloco;

Bloco* blocos;

char *buffer_ficheiro;
int flag_estatistica;
char *bin_comprimido;
unsigned long int aux_comp;


/*funções auxiliares*/
int comprimir ();
int fazer_estatistica (unsigned long int tamanho);
int estatistica_ficheiro (char * buffer, int bytes_difs, int bytes_lidos);
int consulta_blocos (char c, int bytes_difs);
void my_swap(unsigned long int a, unsigned long int b);
void my_bubbleSort (int N);
void shannon_fano(unsigned long int l, int h);
void calcula_probabilidade (int n, unsigned long int t);
unsigned long int criar_buffer_ficheiro_comprimido (unsigned long int n_fich, int n);
int bin_to_int (int * byte);
void int_to_bin_32bits (unsigned long int l, char * v);
void escrever_ficheiro_comprimido (unsigned long int tam, unsigned long int n_bits, int bytes_difs);

/*----------------------------DESCOMPRIMIR------------------------------*/
//variáveis globais e structs
typedef struct xsimbolo{
   char byte;
   int* codigo;
   int bit;
}xBloco;

xBloco* xblocos;

int *buffer_codificado;
char *buffer_ficheiro_original;

//funções auxiliares
void int_to_bin_8bits (int l, int * rep_binaria);
unsigned long int bin_to_int_32bits (int * byte);
int procura_na_tabela (int * codificao, unsigned long int n, int tamanho_codificacao);
unsigned long int extrair_tamanho_conteudo_ficheiro();
int colocar_tabela_em_struct (int * codigo_comprimento_minimo, unsigned long int tamanho_conteudo_ficheiro_bytes, unsigned long int tamanho);
void descodificar_ficheiro_para_buffer (unsigned long int n_bytes_diferentes, int codigo_comprimento_minimo, unsigned long int tamanho_conteudo_ficheiro_bytes, unsigned long int tamanho_conteudo_ficheiro_bits, char * nome_ficheiro_destino);

#endif
