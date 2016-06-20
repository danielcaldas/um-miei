espaco       [ \t]*
novaMus      (\#\#)(\-)+
titulo       {espaco}(?i:Title){espaco}:.+
from         {espaco}(?i:From){espaco}:.+
autor        {espaco}(?i:Author){espaco}:.+
musica       {espaco}(?i:Music){espaco}:.+
cantor       {espaco}(?i:Singer){espaco}:.+
letra        {espaco}(?i:Lyrics){espaco}:.+

%{
    #include "List.h"
    #include <stdlib.h>
    #include <string.h>
    
    List stack = NULL;
    int i=0;
    int  z;

    char *title = NULL, *author = NULL, *from = NULL;
    char *lyrics = NULL, *singer = NULL, *music = NULL;
    char **song = NULL;
    
    char* trim(char* token);
    
%}

/* --------------------------- ESTADOS ------------------------- */

%x  HEADER
%x  TEXT
%x  ESP

/* ------------------------------------------------------------- */


%%
    BEGIN INITIAL;

{titulo}    {   
		// A cada início de canção é necessário alocar memória inicial para nova letra
		song = (char**) malloc(10*sizeof(char*));
                BEGIN HEADER;
                title = strdup(strchr(yytext,':') + 1);
                title = trim(title);
            }

<HEADER>{autor}     if(!author ) {
                        author = strdup(strchr(yytext,':') + 1);
                        author = trim(author);
                    }

<HEADER>{musica}    if(!lyrics) {
                        lyrics = strdup(strchr(yytext,':') + 1);
                        lyrics = trim(lyrics);
                    }

<HEADER>{cantor}    if(!singer) {
                        singer = strdup(strchr(yytext,':') + 1);
                        singer = trim(singer);
                    }

<HEADER>{letra}     if(!music) {
                        music = strdup(strchr(yytext,':') + 1);
                        music = trim(music);
                    }

<HEADER>{from}      { BEGIN TEXT; }

<ESP>{novaMus}   {
		// Estado termina - é filtrado ##----, inserimos nova canção na estrutura de dados e renicializamos variáveis e condição de contexto.

                    stack = insertElemTail(stack, title, from, author, lyrics, singer, music, song, i);
                    
                    for(z=0; z<i; z++){
			free(song[z]);
		    }
		    free(song);

                    i=0;
                    BEGIN(INITIAL);
    
                    title = author = from = lyrics = singer = music = NULL;
                }

<TEXT>.+        {
                    song = (char **)realloc(song,(i+1)*sizeof(char *));
                    song[i] = trim(strdup(yytext));
                    i++;
                    BEGIN ESP;
                }

<ESP>.+         {
                    song = (char **)realloc(song,(i+1)*sizeof(char *));
                    song[i] = trim(strdup(yytext));
                    i++;
                }

<ESP>\n         {
                    if(i!=0){
                        song = (char **)realloc(song,(i+1)*sizeof(char *));
                        song[i] = trim(strdup(yytext));
                        i++;
                    }
                }

<*>.|\n     { ; }

%%

/**
 * Gera Latex a partir da estrutura de dados que resultou de filtar o ficheiro
 */
void latex(char *title, char *author, char *lyrics, char *singer, char *music, char **song, int size) {
    
    char *aux;
    aux = strdup(title);
    
    FILE* f = fopen(strcat(aux,".tex"), "w");
    
    fprintf(f, "\\documentclass[12pt,twoside,a4paper]{article}\n\n");
    fprintf(f, "\\usepackage[portuguese]{babel}\n");
    fprintf(f, "\\usepackage[utf8]{inputenc}\n");
    fprintf(f, "\\usepackage[T1]{fontenc}\n");
    fprintf(f, "\\usepackage[margin=3cm,nohead]{geometry}\n");
    fprintf(f, "\\newcommand{\\tab}[1]{\\hspace{.1\\textwidth}\\rlap{#1}}\n");
    fprintf(f, "\\renewcommand{\\baselinestretch}{1.2}\n\n");

    fprintf(f, "\\begin{document}\n\n");

    fprintf(f, "\\title{%s}\n", title);
    
    if(author){
        fprintf(f, "\\author{%s}\n\n", author);
    }
    else{
        if(!lyrics && music){
            fprintf(f, "\\author{%s}\n\n", music);
        }
        else if(lyrics && !music){
            fprintf(f, "\\author{%s}\n\n", lyrics);
        }
        else { fprintf(f, "\\author{%s \\and %s}\n\n", music, lyrics); }
    }
    
    fprintf(f, "\\date{}\n");

    fprintf(f, "\\maketitle\n\n");
    
    fprintf(f, "\\section*{Letra}\n\n");
    for(z = 0; z < size; z++){
        if(strcmp(song[z],"\\\\")!=0) { fprintf(f, "%s\n", song[z]); }
        else { fprintf(f, "%s", song[z]); }
        //free(song[z]); //---------------------------------------> Este free está a causar double free corruption...
    }
    
    if(singer){
        fprintf(f, "\\begin{flushright}\n");
        fprintf(f, "%s\n", singer);
        fprintf(f, "\\end{flushright}\n");
    }

    fprintf(f, "\n \\end{document}\n");
    if(f) fclose(f);
}

/**
 * Gera Latex a partir da estrutura de dados que resultou de filtar o ficheiro
 */
void gerarLatex()
{
    int size = 0;
    List aux;
    aux=stack;

    while(aux){
        title = getTitle(aux); author = getAuthor(aux); lyrics = getLyrics(aux);
        singer = getSinger(aux); music = getMusic(aux); song = getSong(aux);
        size = getSize(aux);
        
        latex(title, author, lyrics, singer, music, song, size);
        title=author=lyrics=singer=music=NULL;
	song=NULL;
	size=0;
        aux = nextElem(aux);
    }
}

char* trim(char* token) {
    int i=0, n;
    if(token[i]=='\n'){
	n=strlen(token)+2;
	token = (char*)realloc(token,n*sizeof(char));
        token[i]='\\';
        token[i+1]='\\';
	token[i+2]='\0';
    } else {
        for(i=0; token[i]==' ' || token[i]=='\t'; i++)    ;
        token+=i;
        
        n = strlen(token);
        
        for(i=n-1; token[i]==' ' || token[i]=='\t'; i--)  ;
        token[i+1]='\0';
    }
    return token;
}

int yywrap() {
    gerarLatex();
    freeList(stack);
    return 1;
}
