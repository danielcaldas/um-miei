#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<ctype.h>
#include"erro.h"
#include"comandos.h"

/**
   @param funão main que chama o interpretador
*/
int main() {
    interpretador();
    return 0;
}

/**
   @param função interpretador que permite a execução de vários processos através dos comandos que defenimos, saimos do interpretador com o comando sair
*/
void interpretador() {
    int resultado = 0;
    char buffer[MAX_SIZE];
    int ciclo = 1;
 
    while(ciclo && fgets(buffer, MAX_SIZE, stdin) != NULL){
        resultado = interpretar(buffer);
        if(resultado == 0)
            ciclo = 0;
    }
}

/**
   @param função interpretar que identifica o comando que inserimos no interpretador de comandos
   @return retorna argumentos ou não em função das funcionalidas de cada comando que reconhece
*/
int interpretar(char *linha) {
    char comando[MAX_SIZE];
    char args[MAX_SIZE];
    int nargs;

    nargs = sscanf(linha, "%s %[^\n]", comando, args);

    /*comando seq*/
    if(strcmp(comando, "seq") == 0 && nargs == 2)
        return cmd_seq(args);
    else if(strcmp(comando, "seq") == 0 && nargs == 1)
        return cmd_seq(NULL);
    /*comando coords*/
    if(strcmp(comando, "coords") == 0 && nargs == 2)
        return cmd_coords(args);
    if(strcmp(comando, "coords") == 0 && nargs == 1)
        return cmd_coords(NULL);
    /*comando dobrar*/
    if(strcmp(comando, "dobrar") == 0 && nargs == 2)
        return cmd_dobrar(args);
    if(strcmp(comando, "dobrar") == 0 && nargs == 1)
        return cmd_dobrar(NULL);
    /*comando validar*/
    if(strcmp(comando, "validar") == 0 && nargs == 2)
        return cmd_validar(args);
    if(strcmp(comando, "validar") == 0 && nargs == 1)
        return cmd_validar(NULL);
    /*comando compactar*/
    if(strcmp(comando, "compactar") == 0 && nargs == 2)
        return cmd_compactar(args);
    if(strcmp(comando, "compactar") == 0 && nargs == 1)
        return cmd_compactar(NULL);
    /*comando gravar*/
    if(strcmp(comando, "gravar") == 0 && nargs == 2)
        return cmd_gravar(args);
    if(strcmp(comando, "gravar") == 0 && nargs == 1)
        return cmd_gravar(NULL);
    /*comando latex*/
    if(strcmp(comando, "latex") == 0 && nargs == 2)
        return cmd_latex(args);
    if(strcmp(comando, "latex") == 0 && nargs == 1)
        return cmd_latex(NULL);
    /*comando energia*/
    if(strcmp(comando, "energia") == 0 && nargs == 2)
        return cmd_energia(args);
    if(strcmp(comando, "energia") == 0 && nargs == 1)
        return cmd_energia(NULL);
    /*comando reparar*/
    if(strcmp(comando, "reparar") == 0 && nargs == 2)
        return cmd_reparar(args);
    if(strcmp(comando, "reparar") == 0 && nargs == 1)
        return cmd_reparar(NULL);
    /*comando sair*/
    else if(strcmp(comando, "sair") == 0)
        return 0;
    else return mensagem_de_erro(E_COMMAND);
}
