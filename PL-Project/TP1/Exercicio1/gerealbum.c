#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>

#include "gerealbum.h"

#define PREFIX "file://"

// Estruturas de dados do módulo
typedef struct Foto {
	char* foto;
	char* facto;
	char* legenda;
	char* local;
	int d, m, a;
	struct Foto* prox;
}FOTO;

typedef struct Pessoa {
	char* nome;
	struct Foto* album; // Coleção de fotos de uma determinada pessoa
	struct Pessoa* prox;
}PESSOA;

// Variável global para armazenamento de pessoas e respetivos albuns
struct Pessoa* pessoas;


/**
 *Comparar duas data cronologicamente.
 *@param FOTO* f1, apontador para primeira estrutura foto.
 *@param FOTO* f2, apontador para estrutura correspondente à segunda foto.
 *@return int, 0 caso datas sejam iguais, 1 caso f1>f2, -1 caso contrário.
 */
int comparaDatas(struct Foto* f1, struct Foto* f2)
{
	if(f1->a > f2->a) return 1;
	if(f1->a == f2->a){
		if(f1->m > f2->m) return 1;
		else if(f1->m == f2->m){
			if(f1->d > f2->d) return 1;
			else if(f1->d == f2->d) return 0;
		}
	}
	return -1;
}


/**
 *Inserção de fotos num albúm de uma dada pessoa.
 *@param album, apontador para o álbum de fotos da pessoa.
 *@param foto, nome da nova foto a inserir.
 *@param facto, é o título da foto.
 *@param legenda, possível legenda da foto. (parametro EXTRA)
 *@param local, local onde foi tirada a foto. (parametro EXTRA)
 *@param d, m, a, data da foto.
 */
struct Foto* insereFoto(struct Foto* album, char* foto, char* facto, char* legenda, char* local, int d, int m, int a)
{
	if(album==NULL){
		album = (FOTO*) malloc(sizeof(struct Foto));
		album->foto=strdup(foto);
		if(facto!=NULL){
			album->facto = (char*) malloc(strlen(facto)*sizeof(char));
			strcpy(album->facto,facto);
		}
		else album->facto="(sem t&iacutetulo)";
		if(legenda!=NULL) album->legenda=strdup(legenda);
		if(local!=NULL) album->local=strdup(local);
		album->d=d;
		album->m=m;
		album->a=a;
		album->prox=NULL;
		return album;
	}

	struct Foto* nova = (FOTO*) malloc(sizeof(struct Foto));

	// Preencher nova estrutura
	nova->foto=strdup(foto);
	if(facto!=NULL)	nova->facto=strdup(facto);
	else nova->facto="(sem t&iacutetulo)";
	if(legenda!=NULL) nova->legenda=strdup(legenda);
	if(local!=NULL) nova->local=strdup(local);
	nova->d=d;
	nova->m=m;
	nova->a=a;
	nova->prox=NULL;

	int r;
	int flag=0;
	struct Foto* x;
	struct Foto* ant;
	struct Foto* aux;

	for(ant=album, x=album; x!=NULL && flag!=1; ant=x, x=x->prox){
		r=comparaDatas(x,nova);
		switch(r){
			case 0: // As datas são iguais
				aux=x->prox;
				x->prox=nova;
				nova->prox=aux;
				flag=1;
				break;
			case -1: // Data de nova é posterior à data de x
				if(x->prox==NULL){
					x->prox=nova;
					nova->prox=NULL;
					flag=1;
				}
				break;
			case 1: // Data de nova é anterior à data de x
				if(ant!=x){
					ant->prox=nova;
					nova->prox=x;
				} else {
					album=nova;
					nova->prox=x; // Inserção à cabeça da lista
				}
				flag=1;		      
				break;
		}
	}

	return album;
}

/**
 *Inserir uma pessoa na estrutura de dados com atualização ou criação do respetivo álbum de fotos.
 *@param nome, o nome da pessoa-
 *@param foto, nome do ficheiro/foto.
 *@param facto, título da foto.
 *@param legenda, uma legenda da fotografia. (parametro EXTRA)
 *@param local, local onde foi tirada a fota o (parametro EXTRA)
 *@param d, m, a, são a data da foto, dia, mês e ano (respetivamente).
 */
void inserePessoa(char* nome, char* foto, char* facto, char* legenda, char* local, int d, int m, int a)
{
	// Só são aceites fotos com a marca <quem>
	if(nome!=NULL){
		// Caso inicial, álbum de pessoas vazio
		if(pessoas==NULL){
			pessoas = (PESSOA*) malloc(sizeof(struct Pessoa));
			pessoas->nome = strdup(nome);
			pessoas->album=NULL;
			pessoas->prox=NULL;

			pessoas->album = insereFoto(pessoas->album,foto,facto,legenda,local,d,m,a);
		}
		else { // Inserção de uma nova pessoa OU atualização do álbum de uma já existente
			struct Pessoa* pnova;
			struct Pessoa* atual;
			struct Pessoa* ant;
			int r;
			int flag=0;
			for(atual=pessoas, ant=pessoas; atual!=NULL && flag!=1; atual=atual->prox){
				r=strcmp(nome,atual->nome);
				if(r==0){ // Atualizar álbum de uma pessoa
					atual->album = insereFoto(atual->album,foto,facto,legenda,local,d,m,a);
					flag=1;
				}
				else if(r<0){ // Inserção à esquerda
					pnova = (PESSOA*) malloc(sizeof(struct Pessoa));
					pnova->nome = strdup(nome);
					pnova->album=NULL;
					pnova->prox=NULL;
					if(ant!=atual){
						ant->prox=pnova;
						pnova->prox=atual;
					} else {
						pessoas=pnova; // Inserção à cabeça da lista
						pessoas->prox=atual;		      
					}
					pnova->album = insereFoto(pnova->album,foto,facto,legenda,local,d,m,a);
					flag=1;
				}
				else if(atual->prox==NULL){ // Próximo elemento nulo, inserção na cauda (Caso contrário continuar a procurar)
					pnova = (PESSOA*) malloc(sizeof(struct Pessoa));
					pnova->nome = strdup(nome);
					pnova->album=NULL;
					pnova->prox = NULL;
					pnova->album = insereFoto(pnova->album,foto,facto,legenda,local,d,m,a);
					atual->prox=pnova;
					flag=1;
				}
			}
		}
	}
}

/**
 *Função que liberta memória ocupada pela lista ligada.
 */
void freeMem()
{
	struct Foto* faux;
	struct Foto* f;
	struct Pessoa* paux;
	struct Pessoa* p;

	for(p=pessoas; p->prox!=NULL; p=paux){
		for(f=p->album; f->prox!=NULL; f=faux){
			faux=f->prox;
			free(f);
		}
		free(f);
		paux=p->prox;
		free(p);
	}

	// Libertar última estrutura que aponta para NULL
	for(f=p->album; f->prox!=NULL; f=faux){
		faux=f->prox;
		free(f);
	}
	free(f);
	free(p);
}

/**
 *Gerar página de acesso aos albuns, cujo respetivo link fica na home page do site Museu da Pessoa.
 */
void gerarPaginaDeEntrada()
{
	struct Pessoa* p;
	FILE* file = fopen("home.html", "w");
	char* path;

	// Defenir path, para criar link, para respetivos albuns
	path = (char*) malloc(100*sizeof(char));
	path = strcat(path,PREFIX);
	path = strcat(path,currentdir);
	path = strcat(path,"/"); // Para que no final do path exista / (..../)

	fprintf(file, "<!DOCTYPE html>\n");
	fprintf(file, "<head>\n");
	fprintf(file, "\t<meta char-set=\"utf8\"/>\n");
	fprintf(file, "<title>Museu da Pessoa</title>\n");

	fprintf(file, "\t<style type=\"text/css\">\n");
	fprintf(file, "\t#wrapper{\n");
	fprintf(file, "\t\ttext-align:center;\n");
	fprintf(file, "\t\tmargin-top:0px;\n");
	fprintf(file, "\t\tmargin-bottom:0px;\n");
	fprintf(file, "\t\tpadding:0px;\n\t}");
	fprintf(file, "\t</style>\n");

	fprintf(file, "</head>\n");
	fprintf(file, "<body>\n");
	fprintf(file, "\t<div id=\"wrapper\">\n");
	fprintf(file, "\t\t<nav>\n");

	// Pequeno título de início da página
	if(strcmp(code,"AM")==0){
		fprintf(file, "\t\t\t<center><font size=\"10\">Ant&oacutenio Machado</font></center>\n");
	}
	else if(strcmp(code,"TF")==0){
		fprintf(file, "\t\t\t<center><font size=\"10\">Taberna do Fausto</font></center>\n");
	}
	else if(strcmp(code,"NC")==0){
		fprintf(file, "\t\t\t<center><font size=\"10\">Neca Chamin&eacute</font></center>\n");
	}
	else if(strcmp(code,"FG")==0){
		fprintf(file, "\t\t\t<center><font size=\"10\">Fausto Gomes</font></center>\n");
	}


	fprintf(file, "\t\t\t<center><font size=\"6\">&Iacutendice</font></center>\n");

	fprintf(file, "\t\t\t<ul>\n");

	// Percorrer lista ligada para criar índice com nomes
	for(p=pessoas; p!=NULL; p=p->prox){
		fprintf(file, "\t\t\t\t<p align=\"center\" class=\"scroll\"><a href=\"%s%p.html\">%s</a></p>\n", path, p, p->nome);
	}
	fprintf(file, "\t\t\t</ul>");
	fprintf(file, "\t\t</nav>\n\t\t<br><br>\n");
	fprintf(file, "\t</div>\n");
	fprintf(file, "</body>\n</html>\n");
}

/**
 *Gerar para uma dada pessoa o seu álbum HTML.
 *@param struct Pessoa* p, apontador para estrutura com dados e álbum de fotos da pessoa.
 */
void gerarPaginaPessoal(struct Pessoa* p)
{
	struct Foto* x;

	// Nome da página html
	char* filename = malloc((strlen(p->nome))*sizeof(char));
	sprintf(filename,"%p.html",p);

	FILE* file = fopen(filename, "w");

	fprintf(file, "<!DOCTYPE html>\n");
	fprintf(file, "<head>\n");
	fprintf(file, "\t<meta char-set=\"utf8\"/>\n");
	fprintf(file, "<title>Museu da Pessoa - %s</title>\n", p->nome); // Dar título apropriado à página

	fprintf(file, "\t<style type=\"text/css\">\n");
	fprintf(file, "\t#wrapper{\n");
	fprintf(file, "\t\ttext-align:center;\n");
	fprintf(file, "\t\tmargin-top:0px;\n");
	fprintf(file, "\t\tmargin-bottom:0px;\n");
	fprintf(file, "\t\tpadding:0px;\n\t}");
	fprintf(file, "\t</style>\n");

	fprintf(file, "</head>\n");
	fprintf(file, "<body>\n");
	fprintf(file, "\t<div id=\"wrapper\">\n");

	fprintf(file, "\t\t\t<p align=\"center\"><font size=6>%s</font></p>", p->nome);
	fprintf(file, "\t\t\t<br><br>\n");
	fprintf(file, "\t\t\t<hr color=\"black\" size=\"3\" width=\"80%%\">\n");


	//Percorrer lista ligada para criar secções com as fotos e respetiva informação
	for(x=p->album; x!=NULL; x=x->prox){
	   fprintf(file, "\t\t<div id=\"%s\">\n", x->foto);
	   fprintf(file, "\t\t\t<ul>\n");
	   fprintf(file, "\t\t\t\t<p><b>%s</b></p>\n", x->facto);
	   fprintf(file, "\t\t\t\t<img src=\"%s\" style=\"width:420px;height:328px\">\n", x->foto);
	   if(x->a!=-1 && x->m!=-1 && x->d!=-1){
	   	fprintf(file, "\t\t\t\t<p><i>%d/%d/%d</i></p>\n", x->d, x->m, x->a);
	   }
	   else if(x->a!=-1){
		fprintf(file, "\t\t\t\t<p><i>%d</i></p>\n", x->a);
	   }
	   else{
		fprintf(file, "\t\t\t\t<p><i>(sem data)</i></p>\n");
	   }
	   if(x->legenda!=NULL){
		fprintf(file, "\t\t\t\t<p>%s</p>\n", x->legenda);
	   }
	   if(x->local!=NULL){
		fprintf(file, "\t\t\t\t<p><i><b>Local</b>: %s</i></p>\n", x->local);
	   }
	   fprintf(file, "\t\t\t</ul>\n");
	   fprintf(file, "\t\t\t<hr color=\"black\" size=\"3\" width=\"80%%\">\n");
	   fprintf(file, "\t\t</div>\n");
	}

	fprintf(file, "\t\t</div>\n");
	fprintf(file, "</body>\n");
	fprintf(file, "</html>\n");

	fclose(file);
}

/**
 *Gerar álbuns html individuais para cada pessoa.
 */
void gerarAlbuns()
{
	struct Pessoa* p;
	// Criar uma página html para cada pessoa diferente com o respetivo álbum
	for(p=pessoas; p!=NULL; p=p->prox){
		gerarPaginaPessoal(p);
	}
}

/**
 *Pequena função que imprime as estruturas de dados para stdout, para efeitos de teste.
 */
void printEstruturas()
{
	struct Foto* f;
	struct Pessoa* p;

	for(p=pessoas; p!=NULL; p=p->prox){
		printf("----------%s-----------\n", p->nome);
		for(f=p->album; f!=NULL; f=f->prox){
		   printf("FOTO: [%s]\n", f->foto);
		   if(f->facto!=NULL) printf("%s\n", f->facto);
		   printf("%d/%d/%d\n", f->d, f->m, f->a);
		   if(f->legenda!=NULL) printf("%s\n", f->legenda);
	 	   if(f->local!=NULL) printf("%s\n", f->local);
	  	}
	  	printf("----------------------\n");
	}
}
