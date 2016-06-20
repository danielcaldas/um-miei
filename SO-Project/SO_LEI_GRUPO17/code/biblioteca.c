#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <stdlib.h>

#include "biblioteca.h"
#include "megastring.h"




/*Função hashcode String (int 16 bits)*/
int hashCode(char * val)
{

	int hashcode = 0;
	int i; 
	int n = strlen(val);

	for(i=0;i<n;i++) {
		hashcode += val[i]*(15^(n-1-i));
	}

 	return hashcode;
}


/**
*	@param um array de strings indicando qual o contador a incrementar, e recebe também o valor a incrementar
*	@return int. Ao contador designado a nome incrementa o inteiro valor
*/
int incrementar(char *nome[], unsigned valor)
{

	int i, k;
	int id, ic, ifreg;
	int hashcon, hashfreg;
	int flagfound=0;
	char* num;

	id=0;

			/*Procurar distrito na estrutura de dados*/
			if(flagEMPTY==0){
					flagfound=0;
					//achar índice do distrito
					for(k=0; k<18; k++)
						if( strcmp(distritos[k].nome,nome[0])==0 ){
							flagfound=1;
							break;
						}

					if(flagfound==0){ //surge um novo concelho
						dists++;
						id=dists;
						strcpy(distritos[id].nome,nome[0]);
					}
					else{
						id=k;
					}

			}


			hashcon = hashCode(nome[1]);

			if(hashcon > distritos[id].capmaxconcelhos){
				distritos[id].concelhos = (CON) realloc (distritos[id].concelhos,(hashcon+1)*sizeof(struct Concelho));
				initCamposHash(id,-1,-1,distritos[id].capmaxconcelhos,hashcon+1,1);
				strcpy(distritos[id].concelhos[hashcon].nome,nome[1]);
				distritos[id].capmaxconcelhos = (hashcon+1);
			}
			else{
				strcpy(distritos[id].concelhos[hashcon].nome,nome[1]);
			}




			hashfreg = hashCode(nome[2]);

			if(hashfreg > distritos[id].concelhos[hashcon].capmaxfreguesias){
				distritos[id].concelhos[hashcon].freguesias = (FREG) realloc (distritos[id].concelhos[hashcon].freguesias,(hashfreg+1)*sizeof(struct Freguesia));
				initCamposHash(id,hashcon,-1,distritos[id].concelhos[hashcon].capmaxfreguesias,hashfreg+1,2);
				strcpy(distritos[id].concelhos[hashcon].freguesias[hashfreg].nome,nome[2]);
				distritos[id].concelhos[hashcon].capmaxfreguesias = (hashfreg+1);
			}
			else{
				strcpy(distritos[id].concelhos[hashcon].freguesias[hashfreg].nome,nome[2]);		
			}


		
			//incrementar contadores
			distritos[id].contador+=valor;
			distritos[id].concelhos[hashcon].contador+=valor;
			distritos[id].concelhos[hashcon].freguesias[hashfreg].contador+=valor;
			

	return 0;
}





/**
*	@param um prefixo com no máximo 3 strings, o nível de queremos consultar, e o nome do ficheiro onde devemos guardar a informação
*	@return um inteiro
*/
int agregar(char* prefixo[], unsigned nivel, char* path)
{

	int fd;
	int id;
	int ic;
	int ifreg;
	char *num;
	int i;

	int hashcon;
	int hashfreg;

	int argc;

	int flag=0;

	int modes = S_IROTH | S_IWOTH | S_IXOTH | S_IRUSR | S_IWUSR;


	char* numero;

	char linha[1024] ="";

	linha[0]='\0';

	for(argc=0; prefixo[argc]!=NULL; argc++);



	/*procurar distrito ...*/
	for(id=0; id<=dists; id++)
		if( strcmp(distritos[id].nome,prefixo[0])==0 ){
			flag=1;
			break;
		}
	if(flag==0) return -1;
		

	/*Nível 0 com argc=1*/
	if(argc==1 && nivel==0){
		strcat(linha,distritos[id].nome);
		strcat(linha,":");
		numero = intToString(distritos[id].contador);
		strcat(linha,numero);

		fd = open(path, O_CREAT | O_WRONLY, modes);
		write(fd,linha,strlen(linha));
		close(fd);

		return 0;
	}


	/*Nível 1 argc 1*/
	if(argc==1 && nivel==1){
		fd = open(path, O_CREAT | O_WRONLY, modes);

		for(ic=0; ic<distritos[id].capmaxconcelhos; ic++){
			if(distritos[id].concelhos[ic].contador > 0){

				strcat(linha,distritos[id].nome);
				strcat(linha,":");
				strcat(linha,distritos[id].concelhos[ic].nome);
				strcat(linha,":");
				numero = intToString(distritos[id].concelhos[ic].contador);
				strcat(linha,numero);
				strcat(linha,"\n\0");
				write(fd,linha,strlen(linha));
				linha[0]='\0';
			}
		}

		close(fd);

		return 0;
	}


	/*Nível 2 argc 1*/
	if(argc==1 && nivel==2){

		fd = open(path, O_CREAT | O_WRONLY, modes);

		for(ic=0; ic<distritos[id].capmaxconcelhos; ic++)
			if(distritos[id].concelhos[ic].contador > 0)
				for(ifreg=0; ifreg<distritos[id].concelhos[ic].capmaxfreguesias; ifreg++){
					if(distritos[id].concelhos[ic].freguesias[ifreg].contador > 0){

						strcat(linha,distritos[id].nome);
						strcat(linha,":");
						strcat(linha,distritos[id].concelhos[ic].nome);
						strcat(linha,":");
						strcat(linha,distritos[id].concelhos[ic].freguesias[ifreg].nome);
						strcat(linha,":");
						numero = intToString(distritos[id].concelhos[ic].freguesias[ifreg].contador);
						strcat(linha,numero);
						strcat(linha,"\n");
						strcat(linha,"\0");
						write(fd,linha,strlen(linha));

						linha[0]='\0';		
					}
				}
		close(fd);

		return 0;
	}


	/*Nível 0 argc 2*/
	if(argc==2 && nivel==0){
		hashcon = hashCode(prefixo[1]);

		strcat(linha,distritos[id].nome);
		strcat(linha,":");
		strcat(linha,distritos[id].concelhos[hashcon].nome);
		strcat(linha,":");
		numero = intToString(distritos[id].concelhos[hashcon].contador);
		strcat(linha,numero);
		strcat(linha,"\n");
		strcat(linha,"\0");


		fd = open(path, O_CREAT | O_WRONLY, modes);
		write(fd,linha,strlen(linha));
		close(fd);
		return 0;
	}


	/*Nível 1 argc 2*/
	if(argc==2 && nivel==1){
		hashcon = hashCode(prefixo[1]);

		fd = open(path, O_CREAT | O_WRONLY, modes);

		for(ifreg=0; ifreg<distritos[id].concelhos[hashcon].capmaxfreguesias; ifreg++)
				if(distritos[id].concelhos[hashcon].freguesias[ifreg].contador > 0){
				
					strcat(linha,distritos[id].nome);
					strcat(linha,":");
					strcat(linha,distritos[id].concelhos[hashcon].nome);
					strcat(linha,":");
					strcat(linha,distritos[id].concelhos[hashcon].freguesias[ifreg].nome);
					strcat(linha,":");
					numero = intToString(distritos[id].concelhos[hashcon].freguesias[ifreg].contador);
					strcat(linha,numero);

					strcat(linha,"\n");
					strcat(linha,"\0");

					write(fd,linha,strlen(linha));

					linha[0]='\0';


				}
		close(fd);	
		return 0;
	}


	/*Nivel 0 argc 3*/
	if(argc==3 && nivel==0){
		hashcon = hashCode(prefixo[1]);
		hashfreg = hashCode(prefixo[2]);

		strcat(linha,distritos[id].nome);
		strcat(linha,":");
		strcat(linha,distritos[id].concelhos[hashcon].nome);
		strcat(linha,":");
		strcat(linha,distritos[id].concelhos[hashcon].freguesias[hashfreg].nome);
		strcat(linha,":");

		numero = intToString(distritos[id].concelhos[hashcon].freguesias[hashfreg].contador);
		strcat(linha,numero);
		strcat(linha,"\n");
		strcat(linha,"\0");


		fd = open(path, O_CREAT | O_WRONLY, modes);
		write(fd,linha,strlen(linha));
		close(fd);

		return 0;
	}

	return -1;
}