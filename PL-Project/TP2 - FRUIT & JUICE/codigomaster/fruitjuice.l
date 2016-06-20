%{
   /*-------------------------------------
   Analisador Léxico (AL) para a
   linguagem de programação Fruit & Juice
   -------------------------------------*/
   #include <string.h>
   #include "y.tab.h"
%}

%%

[ \t\n] { ; }

(?i:FJBEGIN) { return (FJBEGIN); }
(?i:FRUIT) { return(FRUIT); }
(?i:JUICE) { return(JUICE); }
(?i:FJEND) { return(FJEND); }
(?i:IF) { return(IF); }
(?i:ELSE) { return(ELSE); }
(?i:WHILE) { return(WHILE); }
(?i:FOR) { return(FOR); }
(?i:WOUT) { return(WOUT); }
(?i:RINP) { return(RINP); }

"\{"    {return yytext[0];}
"\}"	{return yytext[0];}
"\("	{return yytext[0];}
"\)"	{return yytext[0];}
"\["	{return yytext[0];}
"\]"	{return yytext[0];}
"\="	{return yytext[0];}
"\<"	{return yytext[0];}
"\>"	{return yytext[0];}
"\,"	{return yytext[0];}
"\;"	{return yytext[0];}
"\""	{return yytext[0];}
"\+"	{return yytext[0];}
"\-"	{return yytext[0];}
"\*"	{return yytext[0];}
"\/"	{return yytext[0];}
"\%"	{return yytext[0];}
"\&"	{return yytext[0];}
"\|"	{return yytext[0];}
"\!"	{return yytext[0];}


[a-zA-Z][a-zA-Z0-9]* { yylval.vstr = strdup(yytext); return(NOME); }
[0-9]+ { yylval.vint = atoi(yytext); return(NUMERO); }

. { return(ERRO); }

%%

int yywrap() {
   return(1);
}

