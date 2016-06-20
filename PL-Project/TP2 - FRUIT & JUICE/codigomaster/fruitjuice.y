%{
   #include <stdio.h>
   #include <stdlib.h>
   #include <string.h>
   #include "const.h"
   #include "hash.h"

   extern int yylex();
   int yyerror(char* s);

   // Variáveis globais
   FILE *f; // Ficheiro que irá conter o resultado final do programa, o assembly gerado
   
   char *array_nome; // Nome do array
   
   /* A quando da declaracao de um array com elementos, 
	esta variavel faz cache desses elementos para posterior 
	insercao na tabela de simbolos */
   int *array;
   
   /* A quando da declaracao de um array com elementos, 
	representa o numero de elementos no array */
   int nelems;
   
   /* Sempre que é inicializado um array com valores, 
	size_array fica com o tamanho desse array */
   int size_array;
   
   typedef struct stackCondicao{
	   int valor;
	   struct stackCondicao *prox;
   } *SC;
   
   //auxiliares para contagem dos if's
   int prof = 0;
   SC stackIf;
   
   //auxiliares para contagem dos ciclos
   int ciclo=1;
   int fciclo=1;

   //insere o nivel atual na cabeça da lista auxif
   void insertCond(){
	   prof++;
	   SC novo;
	   novo = (SC)malloc(sizeof(struct stackCondicao));
	   novo->valor = prof;
	   novo->prox = NULL;
	   
	   if( stackIf == NULL) stackIf = novo;
	   else {
		   novo->prox = stackIf;
		   stackIf = novo;
	   }
	   
   }
   
   // Remove a cabeça da stack de condicoes
   void removeHead(){
	   SC novo;
	   novo = stackIf->prox;
	   free(stackIf);
	   stackIf = novo;
   }
   
   // Obtem o nivel que estiver na cabeça da stack de condicoes
   int getHead(){
	   if(stackIf == NULL) return -1;
	   else return(stackIf->valor);
   }
   
%}

%union{
	int vint;
	char* vstr;
	char vchar;
}
%token NOME NUMERO FJBEGIN FJEND FRUIT JUICE IF ELSE WHILE FOR WOUT RINP ERRO
%type <vstr> NOME
%type <vint> NUMERO
%type <vchar> Oper
%type <vchar> OperAdicao
%type <vchar> OperMultiplicacao
%type <vint> Valor

%%
  Programa : FJBEGIN Linhas FJEND
	   ;
  Linhas : FRUIT { initHash(); } Declaracoes JUICE
			{ fprintf(f, "START\n"); } Codigo
	   |
	   ;
  Declaracoes :
	   | ListaDeclaracoes
	   ;
  ListaDeclaracoes : ListaDeclaracoes ListaVar ';'
	   | ListaVar ';'
	   ;
  ListaVar : Var
	   | ListaVar ',' Var
	   ;
  Var : NOME {
		if(containsValue($1)==1){
			fprintf(stderr, "%s %s\n", ERR_REDECLARED, $1);
		}
		else {
			insertHashVSI(INT,$1);
			fprintf(f, "PUSHI 0\n");
		}
	   }
		| NOME '=' NUMERO {
		if(containsValue($1)==1){
			fprintf(stderr, "%s %s\n", ERR_REDECLARED, $1);
		}
		else {
			insertHashVCI(INT,$1,$3);
			fprintf(f, "PUSHI %d\n", $3);
		}
	   }
	   | NOME '[' NUMERO ']' {
		if(containsValue($1)){
			fprintf(stderr, "%s %s\n", ERR_REDECLARED, $1);
		}
		else {
			insertHashASI(INT_ARRAY,$1,$3);
			fprintf(f, "PUSHN %d\n", $3);
		}
	   }
	   | NOME '[' NUMERO ']' '=' ListaNum {
		if(containsValue($1)){
			fprintf(stderr, "%s %s\n", ERR_REDECLARED, $1);
		}
		else {
		  array_nome=$1;
		  size_array=$3;

		  int i;
		  for(i=0; i<(size_array-nelems); i++){
			fprintf(f, "PUSHI 0\n");
			// Alocar espaço na stack para o array
		  }

		  if(nelems>size_array){
			fprintf(stderr, "%s %d %s %d\n", ERR_ARRAY_OVERFLOW_1, size_array, ERR_ARRAY_OVERFLOW_2, nelems);
		  }
		  else{
			if(nelems==0){
				insertHashASI(INT_ARRAY,array_nome,size_array);
			}
			else {
				insertHashACI(INT_ARRAY,array_nome,size_array,array,nelems);
				free(array);
				}
			}
			// (Re)inicializacao de variaveis auxiliares
			array=NULL;
			array_nome=NULL;
			size_array=0;
			nelems=0;
		}
	   }
	   ;
  ListaNum : '{''}'
	   | '{' ListaNums '}'
	   ;
  ListaNums : NUMERO {
	  // Alocar array para armazenar valores na produção ListaNums
		array = (int*) malloc(MAX_SIZE*sizeof(int));
		nelems=0;

		array[nelems] = $1;	
		nelems++;
		fprintf(f, "PUSHI %d\n", $1);
	   }
	   | ListaNums ',' NUMERO {
		array[nelems] = $3;
		nelems++;
		fprintf(f, "PUSHI %d\n", $3);
	   }
	   ;
  Codigo : Lines
		 |
		 ;
  Lines : Linha
		| Lines Linha
		;
  Linha :Print ';'
		|If
		|Ciclo
		|Atribuicao ';'
	   ;
  Atribuicao : NOME '=' Expr	{
	if(containsValue($1))
		fprintf(f,"STOREG %d\n",findEnderecoV($1));
	else
		fprintf(stderr, "%s %s\n", ERR_REDECLARED, $1);
	}
	| NOME '[' NUMERO ']' '=' {
	if(containsValue($1) && validPos($1,$3)){
		fprintf(f,"PUSHGP\n");
		fprintf(f,"PUSHI %d \n",findEnderecoA($1,$3));
		fprintf(f,"PADD\n");
		fprintf(f,"PUSHI %d\n",$3);
		fprintf(f,"LOADN\n");
	} else if(!containsValue($1)) fprintf(stderr, "%s %s\n", ERR_REDECLARED, $1);
	else fprintf(stderr, "erro. posição %d não existe no array %s\n",$3,$1);
	} Expr {fprintf(f,"STOREN\n");}
	| RINP '(' NOME ')'	{
	if(containsValue($3)) {
		fprintf(f,"READ\n");
		fprintf(f,"ATOI\n");
		fprintf(f,"STOREG %d\n",findEnderecoV($3));
	} else printf("ERRO : Variavel %s não declarada\n",$3);}
	| RINP '(' NOME '[' NUMERO ']' ')'	{
	if(containsValue($3) && validPos($3,$5)){
		fprintf(f,"PUSHGP\n");
		fprintf(f,"PUSHI %d\n",findEnderecoA($3,$5));
		fprintf(f,"PADD\n");
		fprintf(f,"PUSHI %d\n",$5);
		fprintf(f,"READ\n");
		fprintf(f,"ATOI\n");
		fprintf(f,"STOREN\n");}
	else if(!containsValue($3))
		printf("ERRO : Variável %s não declarada\n",$3);
	else
		printf("ERRO : Posição %d não existe no array %s\n",$5,$3);
	}
	;
  If : IF { fprintf(f,"\n\\\\if then else\n"); insertCond(); }
	   '(' Condicao ')'{ fprintf(f,"JZ senao%d \n",getHead());}
	   '{' Codigo '}'{ fprintf(f,"JUMP fse%d \n",getHead());
		   fprintf(f,"senao%d: NOP \n",getHead()); }
	   ELSE '{' Codigo '}' {
		   fprintf(f,"fse%d: NOP \n",getHead());
		   removeHead();
	   }
	  | IF {fprintf(f,"\n\\\\if then else\n"); insertCond();}
		'(' Condicao ')'{ fprintf(f,"JZ senao%d \n",getHead());}
		'{' Codigo '}' { fprintf(f,"JUMP fse%d \n",getHead());
			fprintf(f,"senao%d: NOP \n",getHead()); removeHead(); }
	   ;
  Condicao : Expr '<' Expr		{fprintf(f,"INF\n");}
	   | Expr '=' '<' Expr		{fprintf(f,"INFEQ\n");}
	   | Expr '>' Expr			{fprintf(f,"SUP\n");}
	   | Expr '>' '=' Expr		{fprintf(f,"SUPEQ\n");}
	   | Expr '=' '=' Expr		{fprintf(f,"EQUAL\n");}
	   | Expr '!' '=' Expr		{fprintf(f,"NOT EQUAL\n");}
	   | '!' '(' Condicao ')'	{fprintf(f,"NOT\n");}
	   ;
  Ciclo : { fprintf(f,"\n\\\\while\n");
	        fprintf(f,"ciclo%d: NOP \n",ciclo);
	      } WHILE '(' Condicao ')' {
				fprintf(f,"JZ fciclo%d \n",fciclo);
			}
			'{' Codigo '}' {
				fprintf(f,"JUMP ciclo%d \n",ciclo);
				ciclo++;
				fprintf(f,"fciclo%d: NOP \n",fciclo);
				fciclo++;
			}
		|{ fprintf(f,"\n\\\\for\n");
			fprintf(f,"ciclo%d: NOP \n",ciclo);
		} FOR '(' Atribuicao ';' Condicao {
			fprintf(f,"JZ fciclo%d \n",fciclo);
		}';' Atribuicao ')' '{' Codigo '}' {
			fprintf(f,"JUMP ciclo%d \n",ciclo);
			ciclo++;
			fprintf(f,"fciclo%d: NOP \n",fciclo);
			fciclo++;
		}
	   ;
  Expr : Valor
	   | Expr Oper Valor {
		switch($2){
			case '+' :
				fprintf(f,"ADD\n");
				break;
			case '-' :
				fprintf(f,"SUB\n");
				break;
			case '*' :
				fprintf(f,"MUL\n");
				break;
			case '/' :
				// Verificar tentativa de divisão por 0
				if($3==0){
					fprintf(stderr, "%s\n", ERR_DIVISION_BY_0);
				}
				else{
				   fprintf(f,"DIV\n");
				}
				break;
			case '%' :
				fprintf(f,"MOD\n");
				break;
			case '|' :
				fprintf(f,"OR\n");
				break;
			case '&' :
				fprintf(f,"AND\n");
				break;
		}
	   }
	   ;
  Valor : NUMERO { $$=$1;fprintf(f,"PUSHI %d\n",$1); }
	   | NOME	{
		   if(containsValue($1)){
			   fprintf(f,"PUSHG %d\n",findEnderecoV($1));
		   } else printf("ERRO : Variável %s não declarada\n",$1);}
	   | NOME '[' NUMERO ']'	{
		   if(containsValue($1) && validPos($1,$3)){
			   fprintf(f,"PUSHGP\n");
			   fprintf(f,"PUSHI %d\n",findEnderecoA($1,$3));
			   fprintf(f,"PADD\n");
			   fprintf(f,"PUSHI %d\n",$3);
			   fprintf(f,"LOADN\n");
		   } else if(!containsValue($1))
			   printf("ERRO : Variável %s não declarada\n",$1);
		   else
		       printf("ERRO : Posição %d não existe no array %s\n",$3,$1);
	   }
	   ;
  Oper : OperAdicao			{$$=$1;}
	   | OperMultiplicacao	{$$=$1;}
	   ;
  OperAdicao : '+'	{$$='+';}
	   | '-'		{$$='-';}
	   | '|'		{$$='|';}
	   ;
  OperMultiplicacao : '*'	{$$='*';}
	   | '/'				{$$='/';}
	   | '%'				{$$='%';}
	   | '&'				{$$='&';}
	   ;
Print : WOUT '(' NUMERO ')' {
			fprintf(f,"PUSHI %d\n",$3);
			fprintf(f,"WRITEI\n");
			fprintf(f,"PUSHS \"\\n\"\n");
			fprintf(f,"WRITES\n");
		}
		| WOUT '(' NOME ')'	{
			if(varSimple($3)==1 && containsValue($3)){
				fprintf(f,"PUSHG %d\n",findEnderecoV($3));
				fprintf(f,"WRITEI\n");
				fprintf(f,"PUSHS \"\\n\"\n");
				fprintf(f,"WRITES\n");
			} else if (varSimple($3)==0 && containsValue($3)){
				generateCodeA($3,f);
			} else printf("ERRO : Variável %s não declarada\n",$3);
		}
		| WOUT '(' NOME '[' NUMERO ']' ')'	{
			if(containsValue($3) && validPos($3,$5)){
				fprintf(f,"PUSHGP\n");
				fprintf(f,"PUSHI %d\n",findEnderecoA($3,$5));
				fprintf(f,"PADD\n");
				fprintf(f,"PUSHI %d\n",$5);
				fprintf(f,"LOADN\n");
				fprintf(f,"WRITEI\n");
				fprintf(f,"PUSHS \"\\n\"\n");
				fprintf(f,"WRITES\n");
			} else if(!containsValue($3))
				printf("ERRO : Variável %s não declarada\n",$3);
			else printf("ERRO : Posição %d não existe no array %s\n",$5,$3);
		}
	   ;

%%

int yyerror(char *s)
{
   fprintf(stderr, "ERRO: %s\n", s);
   return 0;
}

int main()
{
   f = fopen("assembly.vm", "w");

   yyparse();

   fprintf(f,"STOP\n");
   fclose(f);

   printHash();

   orderHashEnd();
   system("pdflatex stack.tex");
   system("open stack.pdf");
   system("clear");

   return(0);
}

