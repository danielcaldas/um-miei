foto [0-9]{3}\-[A-Z]\-[0-9]{2}\.((?i:png)|(?i:jpg)|(?i:gif))
espaco [ \t]
data [0-9]+(\-|\.)[0-9]+(\-|\.)[0-9]+

%{
	#include <ctype.h>
	#include "gerealbum.h"

	char* foto=NULL;
	char* nome=NULL;
	char* facto=NULL;
	char* legenda=NULL;
	char* local=NULL;
	char param[5];
	int d, m, a;


	// Algumas funções úteis para o tramamento de strings

	/**
	 *Função que retira espaços do início e fim de uma string.
	 *@param recebe uma string.
	 *@return retorna apontador para string modificada.
	 */
	char* trim(char* token)
	{
		int i, n;

		for(i=0; token[i]==' ' || token[i]=='\t'; i++)
		;
		token+=i;
		n = strlen(token);
		for(i=n-1; token[i]==' ' || token[i]=='\t'; i--)
		;
		token[i+1]='\0';

		return token;
	}

	/**
	 *Função auxiliar para remoção de tags.
	 *@param string com elementos a retirar.
	 *@return apontador para nova string sem as tags.
	 */
	char* extract_atribute(char* token)
	{
		int i;

		for(i=0; token[i]!='"' && token[i]!='\0'; i++)
		;
		i++;
		token+=i;
		for(i=strlen(token); token[i]!='"' && i>=0; i--)
		;
		token[i]='\0';

		return strdup(token);
	}

	/**
	 *Função que verifica se frase em xml termina corretamente.
	 *@param frase em xml.
	 *@param marca de fecho dessa frase.
	 *@return 1 caso frase termine com tag de fecho, 0 caso contrário.
	 */
	int check_end(char* line, char* mark)
	{
		int flag_match=0;
		int i, j;

		for(i=0; line[i]!='\0' && line[i]!='\n';){
			if(line[i]==mark[0]){
				flag_match=1;
				for(j=0; mark[j]!='\0' && mark[j]!='\n' && line[i]!='\0' && line[i]!='\n'; j++, i++){
					if(line[i]!=mark[j]){flag_match=0; break;}
				}
				if(flag_match==1) break;
			}
			else {
				i++;
			}
		}

		return flag_match;
	}   
%}

%x FACTO_NO_END
%x NOME_NO_END

%%

.*\<foto{espaco}?ficheiro{espaco}?\=\"{foto}\"\> {
		foto = extract_atribute(yytext);
}

\<quando{espaco}?(data{espaco}?\=\"{data}\")?(\/)?\>(.*\<\/quando\>)? {
	int i, j, k;	
	for(i=0; yytext[i]!='>'; i++)
	;
	i++;

	if(isdigit(yytext[i])){
	   for(k=0; yytext[i]!='<'; i++, k++){
		   param[k]=yytext[i];
	   }
	   param[k]='\0';
	   a=atoi(param);
	   d=-1; m=-1;
	}
	else {
		yytext = extract_atribute(yytext);

		i=0;
		for(j=0; j<3; j++){
			for(k=0; yytext[i]!='-' && yytext[i]!='.' && yytext[i]!='\0'; i++, k++){
			   param[k]=yytext[i];
			}
			param[k]='\0';
			k = atoi(param);
			switch(j) {
				case 0:
				   a=k;
				   break;
				case 1:
				   m=k;
				   break;
				case 2:
				   d=k;
				   break;
			}
			i++;
		}
	}
}

\<quem{espaco}?\>.+ {
	int i;

	for(i=0; yytext[i]!='>'; i++)
	;
	i++;
	yytext+=i;

	if(check_end(yytext, "</quem>")==1){	
		for(i=strlen(yytext)-1; yytext[i]!='<' && yytext[i+1]!='/' && yytext[i+2]!='q'; i--)
		;
		yytext[i]='\0';

		nome = strdup(yytext);
		nome = trim(nome);
	}
	else {
		nome = strdup(yytext);
		BEGIN(NOME_NO_END);
	}

}

<NOME_NO_END>.*\<\/quem\> {
	int i;
	nome = trim(nome);
	yytext = trim(yytext);
	// Bloco para realocar e concatenar nome
	unsigned int size = strlen(nome)+strlen(yytext)+2;
	char* aux = strdup(nome);
	nome = (char*) malloc(size*sizeof(char));
	nome = strcat(nome,aux);
	nome = strcat(nome," ");
	nome = strcat(nome,yytext);

	for(i=strlen(nome)-1; nome[i]!='<' && nome[i+1]!='/' && nome[i+1]!='q'; i--)
	;
	nome[i-1]='\0';

	BEGIN(INITIAL);
}

\<facto{espaco}?\>.+ {
	int i;

	for(i=0; yytext[i]!='\n' && yytext[i]!='\0' && yytext[i]!='>'; i++)
	;
	i++;
	yytext+=i;

	if(check_end(yytext, "</facto>")==1){
		for(i=strlen(yytext); yytext[i]!='<'; i--)
		;
		yytext[i]='\0';
		facto = strdup(yytext);
	}
	else {
		facto = strdup(yytext);
		BEGIN(FACTO_NO_END);
	}
}

<FACTO_NO_END>.*\<\/facto\> {
	int i;

	facto = trim(facto);
	yytext = trim(yytext);

	// Bloco para realocar e concatenar facto
	unsigned int size = strlen(facto)+strlen(yytext)+2;
	char* aux = strdup(facto);
	facto = (char*) malloc(size*sizeof(char));
	facto = strcat(facto,aux);
	facto = strcat(facto," ");
	facto=strcat(facto,yytext);

	for(i=strlen(facto)-1; facto[i]!='<'; i--)
	;
	facto[i]='\0';

	BEGIN(INITIAL);
}

\<legenda{espaco}?\>.+\<\/legenda\> {
	int i;

	for(i=0; yytext[i]!='\n' && yytext[i]!='\0' && yytext[i]!='>'; i++)
	; 
	i++;
	yytext+=i;

	for(i=strlen(yytext); yytext[i]!='<'; i--)
	;
	yytext[i]='\0';
	legenda = strdup(yytext);
	legenda = trim(legenda);
}

\<onde{espaco}?\>.+\<\/onde\> {
	int i;

	for(i=0; yytext[i]!='\n' && yytext[i]!='\0' && yytext[i]!='>'; i++)
	;
	i++;
	yytext+=i;

	for(i=strlen(yytext); yytext[i]!='<'; i--)
	;
	yytext[i]='\0';
	local = strdup(yytext);
	local = trim(local);
}


<*>.*\<\/foto> {
	inserePessoa(nome,foto,facto,legenda,local,d,m,a);
	foto=nome=facto=legenda=local=NULL;
	d=-1; m=-1; a=-1;
}


<*>.|\n { ; }

%%

int yywrap()
{
	//printEstruturas();
	gerarPaginaDeEntrada();
	gerarAlbuns();
	freeMem();
	free(code);
	free(currentdir);
	return 1;
}

int main(int argc, char* argv[])
{
	if(argc==1) return 0;
	code = strdup(argv[1]); /*Vai permitir-nos colocar o path certo para as diversas imagem do Site Museu da Pessoa*/
	currentdir = strdup(getenv("PWD"));
	yylex();
}
	

