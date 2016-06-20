# ifndef HASH
# define HASH

/**Imprimir tabela de símbolos para stdout*/
void printHash();

/** Inicia uma hash com 10 posicoes */
void initHash();

/** Calcula a posicao na hash */
int getPos(char *nome);

/** Insere na Hash uma variavel array sem inicializacao */
void insertHashASI(int type, char *nome, int size);

/** Insere na Hash uma variavel array com inicializacao */
void insertHashACI(int type,char *nome, int size, int *a, int nelems);

/** Insere uma varivel sem inicializacao */
void insertHashVSI(int type, char * nome );

/* Insere uma varivel com inicializacao */
void insertHashVCI(int type, char *nome, int valor);

/** Gera codigo assembly para uma variavel array*/
void generateCodeA(char *nome, FILE *fp);

/** Verifica se uma dada variavel esta declarada na hash */
int containsValue(char *nome);

/** Verifica se a variavel e simples */
int varSimple(char *nome);

/** Dado o nome de uma variavel array vai buscar a posicao de um elemento seu na pilha */
int findEnderecoA(char* nome, int pos);

/** Dado o nome de uma variavel vai buscar o seu tamanho */
int getVarSize(char *nome);

/** Dado o nome de uma variavel vai buscar a sua posicao na pilha */
int findEnderecoV(char *nome);

/** Sabendo que a variavel existe e que a posiçao que estamos a tentar aceder existe vai buscar o valor do array na posicao */
int getArrValue(char *nome, int posicao);

/** Verifica se a posicao do array que estamos a tentar aceder e valida */
int validPos(char *nome, int posicao);

/** Sabendo que a variavel existe vai buscar o valor da variavel */
int getVarValue(char *nome);

void orderHashEnd();

# endif

