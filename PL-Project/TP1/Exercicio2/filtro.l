pessoa  \<ENAMEX\ TYPE\=\"PERSON\"\>.+\<\/ENAMEX\>
cidade \<ENAMEX\ TYPE\=\"LOCATION\"[ \t]*(SUBTYPE\=\"CITY\")?\>.+\<\/ENAMEX\>
pais \<ENAMEX\ TYPE\=\"LOCATION\"[ \t]*(SUBTYPE\=\"COUNTRY\")?\>.+\<\/ENAMEX\>
org  \<ENAMEX\ TYPE\=\"ORGANIZATION\"\>.+\<\/ENAMEX\>


%{
	#include <stdio.h>
	#include <string.h>

	char *nome;
	char* cidade;
	char* pais;
	char* org;
	
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

	char* extract_value (char* token)
	{
		int i, n;
		
	   	token = strchr(token,'>');
		token++;
		for(i=strlen(token)-1; token[i]!='<' && i>=0; i--)
		;
		token[i]='\0';
		token = trim(token);
	}

%}




%%

{pessoa} {
	nome = strdup(extract_value(yytext));
	printf("NOME: [%s]\n", nome);
}

{cidade} {
	cidade = strdup(extract_value(yytext));
	printf("CIDADE: [%s]\n", cidade);
}

{pais} {
	pais = strdup(extract_value(yytext));
	printf("PAIS: [%s]\n", pais);
}

{org} {
	org = strdup(extract_value(yytext));
	printf("ORG: [%s]\n", org);

}

<*>.|\n  { ; } 

%%


