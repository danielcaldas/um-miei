#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <sys/types.h>
#include <unistd.h>
#include <string.h>
#include <fcntl.h>
#include <signal.h>
#include <sys/file.h>

#include "biblioteca.h"
#include "megastring.h"



/*----------------------------------------------------------------------------------------------------------------------------------------------*/
/*-------------------------------------------FUNÇÕES DE GESTÃO DE MEMÓRIA/INICIAÇIZAÇÃO DA ESTRUTURA DE DADOS-----------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------------------------*/


/*Função que inicializa estrutura de dados onde é guardada a informação do serviço de contagem*/
void init()
{
	int i, j;


	flagEMPTY=1; //flag que sinaliza que estrutura de dados está vazia
	dists=-1;//estrutura de dados vazia
	for(i=0; i<18; i++){
		distritos[i].concelhos = (CON) malloc(10*sizeof(struct Concelho)); //mínimo 10, Viana de Castelo o distsrito de portugal com menor número de freguesias
		distritos[i].contador=0;
		distritos[i].capmaxconcelhos=10;

		//para cada concelho do distsrito (i)
		for(j=0; j<10; j++){
			distritos[i].concelhos[j].freguesias = (FREG) malloc(sizeof(struct Freguesia)); //mínimo 1 freguesia num concelho, exemplo: Barrancos
			distritos[i].concelhos[j].capmaxfreguesias=1;
			distritos[i].concelhos[j].contador=0;
			distritos[i].concelhos[j].freguesias[0].contador=0;	
		}
	}
}



/*Função que coloca contadores a 0 a quando o processo de realocação de memória*/
/*Nivel 1 - hash de concelhos   Nível 2 - hash de freguesias*/
void initCamposHash(int id, int ic, int ifrg, int oldmaxsize, int newmaxsize, int nivel)
{
	int i;

	if(nivel==1){//inicializar uma hash de concelhos
		for(i=oldmaxsize; i<newmaxsize; i++){
			distritos[id].concelhos[i].contador=0;
			distritos[id].concelhos[i].freguesias = (FREG) malloc(sizeof(struct Freguesia));
			distritos[id].concelhos[i].capmaxfreguesias=1;
			distritos[id].concelhos[i].freguesias[0].contador=0;
		}
		distritos[id].capmaxconcelhos = newmaxsize;
	}

	else{//inicializar uma hash de freguesias

			for(i=oldmaxsize; i<newmaxsize; i++){
				distritos[id].concelhos[ic].freguesias[i].contador=0;
			}
			distritos[id].concelhos[ic].capmaxfreguesias=newmaxsize;
	}
}



/*Função liberta memória ocupada pela estrutura de dados enquanto está a ligado o servidor*/
void freeMem()
{
	int i, j;

	for(i=0; i<(dists+1); i++){
		//para cada concelho apagar hashtable das suas freguesias
		for(j=0; j<distritos[i].capmaxconcelhos; j++){
			free(distritos[i].concelhos[j].freguesias);
		}
		//apagar hashable de concelhos do distsrito
		free(distritos[i].concelhos);

		//distsrito[i] apagado
	}

}



/*----------------------------------------------------------------------------------------------------------------------------------------------*/
/*-------------------------------------------------------------------\FIM-----------------------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------------------------*/








/*----------------------------------------------------------------------------------------------------------------------------------------------*/
/*--------------------------------------FUNÇÕES PARA LER/ESCREVER ESTRUTURA DE DADOS EM FICHEIRO------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------------------------*/
/*Duas funções que permitem salvaguardar a estrutura de dados a quando da interrupção do servidor*/


/*Carrega toda a informação presente na estrutura de dados em suporte de ficheiro*/
/*Formato de uma linha ->  Distrito:Concelho:Freguesia:valor    */
void escreveLinhaEmFicheiro(char * linha)
{
	int fd;
	struct flock fl;
	int modes = S_IRUSR | S_IWUSR;

	fl.l_type = F_WRLCK; /* F_RDLCK, F_WRLCK, F_UNLCK */
	fl.l_whence = SEEK_SET; /* SEEK_SET, SEEK_CUR, SEEK_END */
	fl.l_start = 0; /* Offset from l_whence */
	fl.l_len = 0; /* length, 0 = to EOF */
	fl.l_pid = getpid(); /* our PID */

	strcat(linha,"\n");


	fd = open("basededados.txt", O_CREAT | O_WRONLY | O_APPEND, modes);

	fcntl(fd, F_SETLKW, &fl); /* Set da Fechadura! Agora se necessário esperamos... */

	write(fd,linha,strlen(linha));
	close(fd);

	fl.l_type = F_UNLCK;
	fcntl(fd, F_SETLK, &fl); /* destrancar a região */

	write(1,"$WRITE TO DB SUCESS$: ",22);

}




/*Ler e carregar em memória um ficheiro texto com o estado que o programa
continha antes de por qualquer motivo ter sido interrompida a sua execução*/
void lerFicheiroECarregarEmMem()
{

	FILE* fd;

	char* line;
	char* distrito;
	char* concelho;
	char* freguesia;
	char numero[10];
	int valor;

	int i, j, k;

	int hashcon;
	int hashfreg;

	int flagfound;
	int id;

	char **nome;

	char file[30];


	fd = fopen("basededados.txt","r");

	line = (char*) malloc(MAX_LINE_SIZE*sizeof(char));

	id=0;


	while( NULL != fgets(line,MAX_LINE_SIZE,fd) ){


			/*ignorar comando incrementar + ' '*/
			i=12;


			/*DISTRITO*/
			distrito = (char*) malloc(MAX_NAME_SIZE*sizeof(char));
			for(j=0; line[i]!=':'; i++, j++){
				distrito[j]=line[i];
			}
			distrito[j] = '\0';

			/*CONCELHO*/
			i++; /*evitamos o carater ' '*/
			concelho = (char*) malloc(MAX_NAME_SIZE*sizeof(char));
			for(j=0; line[i]!=':'; i++, j++){
				concelho[j]=line[i];
			}
			concelho[j]='\0';

			/*FREGUESIA*/
			i++;/*evitamos o carater ' '*/
			freguesia = (char*) malloc(MAX_NAME_SIZE*sizeof(char));
			for(j=0; line[i]!=':';i++, j++){
				freguesia[j]=line[i];
			}
			freguesia[j]='\0';


			/*VALOR*/
			i++;
			for(j=0; line[i]!='\n'; i++, j++){
				numero[j]=line[i];
			}
			numero[j]='\0';

			valor = atoi(numero);

			nome = (char**) malloc(4*sizeof(char*));

			nome[0] = (char*) malloc(MAX_NAME_SIZE*sizeof(char));
			strcpy(nome[0],distrito);

			nome[1] = (char*) malloc(MAX_NAME_SIZE*sizeof(char));
			strcpy(nome[1],concelho);

			nome[2] = (char*) malloc(MAX_NAME_SIZE*sizeof(char));
			strcpy(nome[2],freguesia);

			nome[3] = NULL;



			incrementar(nome,(unsigned)valor);


			for(j=0; j<4; j++)
				free(nome[j]);
			free(nome);

			free(distrito);;
			free(concelho);
			free(freguesia);
			free(line);
			numero[0]='\0';
			line = (char*) malloc(MAX_LINE_SIZE*sizeof(char));

			flagEMPTY=0; //após inserirmos qq dado na estrutura esta deixa de estar vazia;

	}
	fclose(fd);
	
	write(1,"\n$DATA BASE LOAD SUCESS$\n",25);
}




/*----------------------------------------------------------------------------------------------------------------------------------------------*/
/*-------------------------------------------------------------------\FIM-----------------------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------------------------*/









/*----------------------------------------------------------------------------------------------------------------------------------------------*/
/*--------------------------------------FUNÇÕES QUE TRATAM LINHAS DE COMANDOS INSERIDAS PELOS CLIENTES------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------------------------*/



/*Conta o número de argumentos (nomes) na linha escrita pelo cliente*/
int coutArgs(char * linha)
{
	int c=0;
	int i;

	for(i=0; linha[i]!='\0'; i++)
		if(linha[i]==':') c++;
	return c;
}


/*ex.: "incrementar Braga:Braga:S. Vitor:7"*/
char** parseIncrementar (char* linha, int * valor)
{
	int i, j;
	int nnomes;
	char numero[10];
	char** nome;

	if(i=coutArgs(linha)!=3) return NULL; /*nº de argumentos inferiores a 4*/
	
	nome = (char**) malloc(4*sizeof(char*));
	for(i=0; i<3; i++)
		nome[i] = (char*) malloc(MAX_NAME_SIZE*sizeof(char));

	i=12;

	/*DISTRITO*/
	for(j=0; linha[i]!=':'; i++, j++)
		nome[0][j]=linha[i];
	nome[0][j]='\0';

	i++;

	/*CONCELHO*/
	for(j=0; linha[i]!=':'; i++, j++)
		nome[1][j]=linha[i];
	nome[1][j]='\0';


	i++;

	/*FREGUESIA*/
	for(j=0; linha[i]!=':'; i++, j++)
		nome[2][j]=linha[i];
	nome[2][j]='\0';

	nome[3] = NULL;

	i++;
	/*VALOR DO INCREMENTO*/
	for(j=0; isdigit(linha[i]); i++, j++)
		numero[j]=linha[i];
	numero[2]='\0';

	*valor = atoi(numero);

	return nome;
}




/* agregar Porto:Amarante:0:file.txt*/
/* agregar Porto:0:file.txt*/
char** parseAgregar(char * linha, int * nivel, char * path)
{
	int i;
	char** prefixo;
	int nnomes=0;
	char numero[2];
	char** aux;
	int size;
	linha+= 8; 


	aux = tokenize(linha,':',MAX_NAME_SIZE);

	prefixo = (char**) malloc(sizeof(char*));

	for(i=0; !isdigit(aux[i][0]); i++){

		prefixo = (char**) realloc(prefixo, (i+1)*sizeof(char*));
		prefixo[i] = (char*) malloc(MAX_NAME_SIZE*sizeof(char));

		strcpy(prefixo[i],aux[i]);
		//printf("i:%d\n", i);

	}
	prefixo = (char**) realloc(prefixo, (i+1)*sizeof(char*));
	prefixo[i]=NULL;

	//printf("numero i:%d\n", i);	
	*nivel = atoi(aux[i]);
	i++;

	strcpy(path,aux[i]);


	return prefixo;

}


/*Função que retira comando da linha inserida pelo utilizador*/
char* parseComando(char * linha)
{
	char* nova;

	nova = (char*) malloc(MAX_COMAND_SIZE*sizeof(char));

	int i;

	for(i=0; linha[i]!=' '; i++)
		nova[i]=linha[i];
	nova[i]='\0';

	return nova;
}




/*----------------------------------------------------------------------------------------------------------------------------------------------*/
/*-------------------------------------------------------------------\FIM-----------------------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------------------------------------*/







/*--------Handlers para sinais SIGINT, SIGCHLD e SIGUSR1----------*/
int fd;
void sigint_handler (int sig)
{
	write(1,"\n$SERVIDOR FECHOU$\n",19);
	freeMem();
	exit(0);
}


void sigchld_handler(int sig)
{
	while(waitpid(-1, 0, WNOHANG) > 0);
	return;
}

void carregar(int sig)
{
	lerFicheiroECarregarEmMem();
}



/*---------------Função main do servidor, onde são lidos os pedidos e delegados para processos filhos-----------------------*/
void Servidor()
{

	int size, i;
	char c;
	char buff[1024];
	char *comando;
	char* tokens[MAX_NAME_SIZE];
	int pid;


	signal(SIGCHLD, sigchld_handler);/*Não precisamos de esperar que o processo filho morra*/
	signal(SIGUSR1, carregar);
	while(1){


			/*-------------Ler comando do pipe----------------------------------------*/
			size=0;

			/*Abertura do pipe em modo leitura*/
			fd = open("fifo", O_RDONLY);

			while( read(fd,&c,1) > 0 ){
				if(c=='\n') break;
				buff[size]=c;
				size++;
			}
			close(fd);

			buff[size]='\0';
			size++;
			/*------------------------------------------------------------------------*/

			comando = parseComando(buff);

			write(1,"REQUEST: ",9);
			write(1,buff,strlen(buff));
			write(1,"\n",1);

			if(pid=fork()==0){


				/*----INCREMENTAR-----*/
				if( strcmp(comando,"incrementar")==0 ){

						escreveLinhaEmFicheiro(buff);

						kill(getppid(),SIGUSR1);
						exit(0);						
				}


				/*----------AGREGAR------------*/
				else if( strcmp(comando,"agregar")==0 ){

						char** prefixo;
						int nivel;
						char* path;
						int r;

						path = (char*) malloc(MAX_NAME_SIZE*sizeof(char));
						prefixo = parseAgregar(buff,&nivel,path);

						/*---Chamar função agregar---*/
						r = agregar(prefixo,nivel,path);

						if(r==0){
							write(1,"$AGREG SUCESS$\n",15);
						}
						else{
							write(1,"$AGREG FAILED$\n",15);
						}
						/*---------------------------*/

						/*Libertar memória alocada dinamicamente*/
						for(i=0; prefixo[i]!=NULL; i++)
							free(prefixo[i]);
						free(prefixo);

						free(path);

						exit(0);
				}

				else{exit(0);}

			}

			else if(pid==-1){
				write(1,"\nfork() error!\n", 16);
				exit(1);
			}

	}



}


int main()
{
	struct sigaction handler;
	handler.sa_handler = sigint_handler; /*associar o handler que definimos*/

	/*Inicializar estrutura de dados*/
	init();

	/*Ler base de dados ou criar ficheiro vazio caso não exista*/
	if( open("basededados.txt",O_RDONLY) > 0){
		lerFicheiroECarregarEmMem();
	}

	/*Criação do pipe com nome para comunicação Cliente-Servidor*/
	mkfifo("fifo",0666);

	sigaction(SIGINT, &handler, NULL);/*Invocar rotina sigint_handler a quando término do processo pai do servidor*/

	Servidor();
}