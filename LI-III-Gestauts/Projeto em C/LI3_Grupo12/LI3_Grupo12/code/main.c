#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<ctype.h>
#include<unistd.h>
#include"indice_autores.h"
#include"estatistica.h"
#include"catalogo_autores.h"


/*Macros*/
#define MAX_LINE_SIZE 1024
#define MAX_FILE_NAME_SIZE 20
#define MAX_NAME_SIZE 70



/*Auxiliar function for modul leitura*/
char* trim (char * token);



/*Functions for printing moduls operation results and Queries requests*/

/*Input/Output functions*/
void printWelcome();
void printRelatorio(char * file, int lines, int minYEAR, int maxYEAR, int countAuthors);
void printMenu();

 void CA_Querie3(int minYEAR, int maxYEAR);
 void CA_Querie4();
 void CA_Querie5();
 void IA_Querie6();
 void E_Querie7(int minYEAR, int maxYEAR);
 void CA_Querie8();
 void CA_Querie9(int minYEAR, int maxYEAR);
 void E_Querie10(int minYEAR, int maxYEAR);
 void E_Querie11();
 void CA_Querie12(int minYEAR, int maxYEAR);
 void CA_Querie13(int minYEAR, int maxYEAR);
 void IA_Querie14();
 void CA_Querie99();
 void CA_Querie101();

/*Estatistica modul*/
void E_Print_Publications_Per_Years(int number_of_publications);
void E_Print_Percentils_Per_Years(int total);
float round(float number);
void E_Value_Years_Interval (int inf_date, int sup_date);
void E_Authors_123 (int year, char * file_name);
void E_Max_Authors_inPublication();
void E_Generate_CSVfile ();

/*Indice de autores modul*/
void IA_Print_NamesStartedByLetter(char c);
void IA_PrintMinorCase (char** authors_names);
void IA_PrintPagesInTerminal(char** authors_names, int total_pages, char answ, int total_names, int flagPRINTS, int minYEAR, int maxYEAR);
void IA_Print_Foreign_Names();
void IA_PrintingNamesInFILE (char** names, FILE * f,int total_names);

/*Catalog de autores modul*/
void CA_TableOfPublicationsAuthor (char * name);
void CA_ListOfCoAuthors(char * name);
void CA_WhoMaxCoAuthories (char * name);
void CA_PrintPagesInTerminal(char** authors_names, int total_pages, char answ, int total_names, int * values, int year_of_publication);
void CA_ListAuthorsYearsInterval (int minYEAR, int maxYEAR);
void CA_PercentilsAuthorYear (char * name, int year_of_publication);
void CA_NAuthorsGivenYear(int year_of_publication, int N);
void CA_PrintYearsCatalog();
void CA_PrintfAuthorPersonalRecord(char * name);






int main ()
{
	FILE *publicx;
    int lines, i;
    int a=0;
    int countAuthors=0;
    int minYEAR, maxYEAR;
    char *line;
    char *token;
    char *file;
    int user_key;

    char **author_names;
    int year_of_publication;


    system("clear");
    printWelcome();

    scanf(" %d", &user_key);

    if(user_key==1)
        file="publicx.txt";
    else if(user_key==2)
        file="publicx_x4.txt";
    else if(user_key==3)
        file="publicx_x6.txt";
    else{
            file = (char*) malloc(MAX_FILE_NAME_SIZE*sizeof(char));
            printf("Nome do ficheiro: ");
            scanf("%s", file);
    }

	publicx = fopen(file, "r");
    
    /*Looking for any erros on input file*/
    if(publicx==NULL){
       perror("Error in opening file\n");
       return(-1);
    }

    printf("\nA carregar o ficheiro . . .\n\n");


    /*Functions to initialize modules*/
    /*------------------------------*/
    IA_Init();
    E_Init();
    CA_Init();
    /*------------------------------*/

    int sum=0;
    /*Allocating memory for string that contains a line of the file*/
    line = (char*) malloc(MAX_LINE_SIZE*sizeof(char));


    for(lines=0; NULL!=fgets(line,MAX_LINE_SIZE,publicx); lines++){ /*This cicle controls the line in which we are operating*/

            author_names = (char**) malloc(sizeof(char*));


            token = strtok(line, ",");

            for(a=0; token!=NULL; a++){

                author_names = (char**) realloc(author_names, (a+1)*sizeof(char*));

                author_names[a] = (char*) malloc(MAX_NAME_SIZE*sizeof(char));

                token=trim(token);

                strcpy(author_names[a], token);

                token = strtok(NULL,",");
            }

            a--; /*avoiding NULL in the array author_names*/

            year_of_publication = atoi(author_names[a]);

            countAuthors+=a;

            /*-----------------------------------------------------------------------------------*/

            /*AT THIS POINT YOU MAY HAVE AVAIBLE:
            - char **author_names: Array of strings that contains names of authors in a publication;
            - int year_of_publication: The year of that publication*/


            /*PLACE OF CALLING MODULES MAIN OPERATIONS*/           
            IA_Store_Authors (author_names,a); /*a stands for the number of authors*/
            E_Estatistica(year_of_publication,a);
            CA_Store_Authors (author_names,a,year_of_publication);

            /*-----------------------------------------------------------------------------------*/

            if(a==1) sum++;
            /*Freeing memory for the String array author_names and line*/
            for(i=0; i<a; i++)
                free(author_names[i]);
            free(author_names);

            free(line);
            line = (char*) malloc(MAX_LINE_SIZE*sizeof(char));

    }


    /*When exiting reading file cicle, free memory used for reading lines*/
    free(line);

    /*Closing Moduls*/
    E_CloseInsertions();
    IA_CloseInsertions();
    CA_CloseInsertions();

    system("clear");

    /*Getting data from modul estatistica (we will need this values for the queries)*/
    minYEAR=E_getMinYear(); maxYEAR=E_getMaxYear();

    printRelatorio(file,lines,minYEAR,maxYEAR,countAuthors);
    printf("COM UM AUTOR: %d\n", sum);

    /*-----------------------------------------------------------------------------------------------------*/
    /*CONSULTING THE WORK DONE BY THE MODULES TROUGH INTERACTIVE QUERIES*/
    /*-----------------------------------------------------------------------------------------------------*/
    
    user_key=-1;

    while(user_key!=0){

        /*Interactive Queries (the number of the queries corresponds to the task number in the project description*/
        printMenu();

        scanf("%d", &user_key); /*' ' on scanf makes sure that the scan ignores the earlier '\n' character*/
    
        /*Executing tasks demanded by the user*/
        switch(user_key){

                case 2:
                    E_Print_Publications_Per_Years(lines);
                    break;

                case 3:
                    CA_Querie3(minYEAR,maxYEAR);
                    break;

                case 4:
                    CA_Querie4();
                    break;

                case 5:
                    CA_Querie5();
                    break;

                case 6:                    
                    IA_Querie6();
                    break;

                case 7:
                    E_Querie7(minYEAR,maxYEAR);
                    break;

                case 8:
                    CA_Querie8();
                    break;

                case 9:
                    CA_Querie9(minYEAR,maxYEAR);
                    break;

                case 10:
                    E_Querie10(minYEAR,maxYEAR);
                    break;

                case 11:
                    E_Querie11();
                    break;

                case 12:
                    CA_Querie12(minYEAR,maxYEAR);
                    break;

                case 13:
                    CA_Querie13(minYEAR,maxYEAR);                    
                    break;

                case 14:
                    IA_Querie14();
                    break;

                case 22:
                    E_Print_Percentils_Per_Years(lines);
                    break;

                case 23:
                    system("clear");
                    E_Max_Authors_inPublication();
                    break;

                case 66:
                    system("clear");
                    IA_Print_Foreign_Names();
                    break;

                case 99:
                    CA_Querie99();
                    break;

                case 100:
                    CA_PrintYearsCatalog();
                    break;

                case 101:
                    CA_Querie101();
                    break;

                case 0:
                    break;

                default:
                    system("clear");
                    printf("\nOpção inserida é inválida!\nPor favor insira uma das opções válidas.\n\n");
                    break;

        }

    }

    /*Realising dinamic allocated memory used for the diferent moduls data structures*/
    E_Release_Mem();
    IA_ReleaseAuthorsTree();
    CA_ReleaseAuthorsCatalog();
    CA_ReleaseYears();

	return 0;
}


/**
    @param um token (um string que é um nome de um autor um ano de uma publicação)
    @return apontador para nova string após remoção de espaços em branco no início e no fim do token ("aparar" a string)
*/
char* trim (char * token)
{
    int i;

    while(token[0]==' ') token++; /*Removing undesireble white-spaces in the begining of the string*/
    for(i=strlen(token); token[i-1]==' '; i--); /*Finding index of the last white space in the string*/
    token[i]='\0';

    return token;
}






/*---------------------------------------- FUNCTIONS THAT PRINT RESULTS FROM MODULES ----------------------------------------------*/



/*---------------------------------------------------------------------------------------------------------------------------------*/
/*--------------------------------------------   INPUT/OUTPUT CONTROL FUNTIONS   --------------------------------------------------*/
/*---------------------------------------------------------------------------------------------------------------------------------*/


void printWelcome()
{
    printf("############################-BEM-VINDO AO GESTAUTS-#############################\n\n");
    printf("\nO GESTAUTS é uma aplicação interativa de criação, gestão e consulta de um catálogo de publicações.\n");
    printf("@by: Tiago Cunha a67741, Daniel Caldas a67691 e Xavier Rodrigues a67676 (Grupo 12)\n\n");

    printf("Por favor selecione uma das opções:\n\n");
    printf(" 1 - publicx.txt\n");
    printf(" 2 - publicx_x4.txt\n");
    printf(" 3 - publicx_x6.txt\n");
    printf(" 4 - Inserir manualmente nome do ficheiro (***.txt).\n\n");
    printf("> ");
}


/*Tarefa 1 do enunciado do projeto*/
void printRelatorio(char * file, int lines, int minYEAR, int maxYEAR, int countAuthors)
{
    FILE *f;
    char* smallest_name;
    char * biggest_name;

    smallest_name = IA_getSmallest();
    biggest_name = IA_getBiggest();

    f = fopen("relatorio.txt", "w");
    fprintf(f, "\n#############################RELATORIO#############################\n");
    fprintf(f, "Nome do ficheiro lido: %s\n", file); /*Task 1*/
    fprintf(f, "Numero de publicacoes listadas no ficheiro: %d\n", lines);
    fprintf(f, "Anos=[%d,%d]\n\n", minYEAR, maxYEAR);
    fprintf(f, "Número de autores no ficheiro (com repetições): %d\n",countAuthors);
    fprintf(f, "Número de autores no ficheiro (sem repetições): %d\n", IA_getCountAUTHORS());
    fprintf(f, "Nome mais pequeno no ficheiro: %s (Tamanho: %d)\n", smallest_name, IA_getSmallestSize()); /*Task 14.*/
    fprintf(f, "Nome maior no ficheiro: %s (Tamanho: %d)\n", biggest_name, IA_getBiggestSize());
    fprintf(f, "Valor médio do comprimento dos nomes dos diferentes autores:  %d\n", IA_getNamesMediumLength());
    fclose(f);

    /*Printing the report on terminal board for the first task of the project*/
    printf("\n#############################RELATORIO#############################\n");
    printf("Nome do ficheiro lido: %s\n", file); /*Task 1*/
    printf("Numero de publicacoes listadas no ficheiro: %d\n", lines);
    printf("Total de nomes lidos: %d\n",countAuthors);
    printf("Anos=[%d,%d]\n\n", minYEAR, maxYEAR);
    printf("(Faça consultas sem preocupações estes dados ficarão guardados em relatorio.txt (um relatório mais detalhado)\ncaso pretenda consultá-los.)\n\n");
}



void printMenu ()
{
        /*Interactive Queries (the number of the queries corresponds to the task number in the project description*/
        printf("\n\n###############################################################\n");
        printf("##                       GESTAUTS  Menu                      ##\n");
        printf("###############################################################\n");
        printf(" 2 - Consultar o número de publicações por ano\n");
        printf(" 3 - Consultar número de publicações de um autor num determinado ano\n");
        printf(" 4 - Consultar número de autores que apenas publicaram a solo\n");
        printf(" 5 - Consultar tabela de publicações de um dado autor\n");
        printf(" 6 - Consultar a lista de todos os autores começados por uma determinada letra\n");
        printf(" 7 - Consultar o número de publicações para um intervalo fechado de anos\n");
        printf(" 8 - Consultar para um dado autor o(s) nome(s) dos autor(es) com que mais publicou\n");
        printf(" 9 - Consultar para um intervalo fechado de anos a lista de autores que publicaram em cada um desses anos\n");
        printf(" 10 - Introduzir ano para consultar número de artigos com 1, 2 ou 3 autores\n");
        printf(" 11 - Gerar ficheiro CSV\n");
        printf(" 12 - Determinar para um dado ano a lista dos N autores que mais publicaram\n");
        printf(" 13 - Consultar para um ano e um autor a percentagem de publicações do autor nesse ano\n");
        printf(" 14 - Determinar média do comprimento dos nomes e nomes de maior e menor comprimento\n\n");


        printf(" (Extra Queries)\n");

        /*Extra querie allows to profile about publication years distribution in the file*/
        printf(" 22 - Consular a percentagem de publicações de cada ano\n");

        /*Allows to do profiling for set more precise value for the MAX_NUMBER_AUTHORS macro*/
        printf(" 23 - Consultar número máximo de autores numa publicação e respetivo ano\n");

        /*Extra querie allows to profile about unusual names of authors*/
        printf(" 66 - Consultar nomes dos autores que não começam com letras do nosso alfabeto\n");

        /*Extra querie allows to better profile about maximum number of publications of an author in the file*/
        printf(" 99 - Consultar número de publicações máximo entre todos os autores do ficheiro\n");
        printf(" 100 - Cria catálogo de publicações autores/anos\n");
        printf(" 101 - Dado o nome de um autor gerar a sua ficha pessoal em formato .pdf\n\n");


        printf(" 0 - Sair\n\n");

        printf(" > ");
}



void CA_Querie3 (int minYEAR, int maxYEAR)
{
    char  *name;
    int i,year_of_publication;

     
    system("clear");

    name = (char*) malloc(MAX_NAME_SIZE*sizeof(char));
    printf("\n\nInsira um autor: ");
    fgets(name,MAX_NAME_SIZE,stdin); /*just to ignore the previous printf*/
 
    if(NULL!=fgets(name,MAX_NAME_SIZE,stdin)){
        printf("Inserir ano: ");
        scanf("%d", &year_of_publication); i++;

        if(year_of_publication>maxYEAR || year_of_publication<minYEAR){
            system("clear");
            printf("\n\nERRO! Ano inserido é inválido.\nPor favor tente novamente introduzindo um ano entre %d e %d.\n\n", minYEAR,maxYEAR);
            free(name);
            return;
        }

        for(i=0;name[i]!='\n';i++); name[i]='\0'; /*eliminating '\n' from name*/
                    
        i=CA_getNumberOfPublcAuthor(name,year_of_publication); /*save the result in int i*/
        system("clear");

        if(i==-1) printf("\n\nERRO! AUTOR: [%s] não encontrado!\n\n", name);
            else printf("\n\n%s publicou %d artigos em %d.\n\n", name, i, year_of_publication);

    }
    free(name);
}



 void CA_Querie4()
 {
      int a;

      a = CA_getNumberOfSoloAuthors();
      system("clear");
      printf("\n\nNúmero de autores que somente publicaram a solo:   %d.\n\n", a);

}



 void CA_Querie5 ()
 {
    int i;
    char *name;

    system("clear");

    name = (char*) malloc(MAX_NAME_SIZE*sizeof(char));

    printf("\n\nInsira um autor: ");
    fgets(name,MAX_NAME_SIZE,stdin); /*just to ignore the previous printf*/

    if(NULL!=fgets(name,MAX_NAME_SIZE,stdin)){

        for(i=0;name[i]!='\n';i++); name[i]='\0'; /*eliminating '\n' from name*/

        CA_TableOfPublicationsAuthor(name);
    }
    free(name);
}



void IA_Querie6 ()
{
    char c;

    system("clear");

    printf("\n\nPor favor insira uma letra (maiúscula ou minúscula é indiferente): ");
    scanf(" %c", &c);
    system("clear");
    IA_Print_NamesStartedByLetter(c);
    return;
}


void E_Querie7 (int minYEAR,int maxYEAR)
{
    
    int i,inf_date,sup_date; i=0; i++;

    system("clear");

    printf("\nPor favor, insira um intervalo fechado de datas (Ex.: [1999,2000]): ");
    scanf(" [%d,%d]", &inf_date, &sup_date);

    while(inf_date<minYEAR || sup_date>maxYEAR || inf_date>sup_date){
        printf("\nERRO!!: Inseriu data(s) inválida(s)!\n");
        printf("\nPor favor, insira um intervalo fechado de datas (Ex.: [1999,2000]): ");
        scanf(" [%d,%d]", &inf_date, &sup_date);
    }

    system("clear");
    E_Value_Years_Interval(inf_date,sup_date);
}


                   
void CA_Querie8() 
{
    char *name;
    int i;

    system("clear");

    name = (char*) malloc(MAX_NAME_SIZE*sizeof(char));
    printf("\n\nInsira um autor: ");
    name=fgets(name,MAX_NAME_SIZE,stdin); /*just to ignore the previous printf*/

    if(NULL!=fgets(name,MAX_NAME_SIZE,stdin)){

        for(i=0;name[i]!='\n';i++); name[i]='\0'; /*eliminating '\n' from name*/

        CA_WhoMaxCoAuthories (name);
    }

    free(name);
}



void CA_Querie9(int minYEAR, int maxYEAR)
{
    int i,inf_date, sup_date;

    system("clear");

    printf("\nPor favor, insira um intervalo fechado de datas (Ex.: [1993,2007]): ");
    scanf(" [%d,%d]", &inf_date, &sup_date);

    while(inf_date<minYEAR || sup_date>maxYEAR || inf_date>sup_date){
        printf("\nERRO!!: Inseriu data(s) inválida(s)!\n");
        printf("\nPor favor, insira um intervalo fechado de datas (Ex.: [1993,2007]): ");
        scanf(" [%d,%d]", &inf_date, &sup_date); i++;
    }

    CA_ListAuthorsYearsInterval(inf_date,sup_date);
}


void  E_Querie10(int minYEAR,int maxYEAR)
{
    char fileQUERIES[20];
    int a=0;
    int i;

    system("clear");

    printf("\nPor favor introduza um ano (de %d até %d): ", minYEAR, maxYEAR);
    scanf("%d", &i); a++;
    while(i<minYEAR || i>maxYEAR){
        printf("\nERRO!: O ano inserido encontra-se fora do intervalo de anos das publicações.\n");
        printf("\nPor favor, insira um ano correto (de %d até %d): ", minYEAR, maxYEAR);
        scanf("%d", &i);
    }

    printf("Por favor insira o nome do ficheiro onde pretende guardar a informação (***.txt): ");
    scanf("%s", fileQUERIES);

    system("clear");
    E_Authors_123(i, fileQUERIES);
}




void E_Querie11()
{
    E_Generate_CSVfile();
    system("clear");
    printf("\n\nO ficheiro com extensao .csv foi gerado com sucesso.\n");
    printf("Por favor consulte etapa11.csv.\n\n");
}



void CA_Querie12(int minYEAR, int maxYEAR)
{
    int year_of_publication, N;

    system("clear");

    printf("\nInserir ano: ");
    scanf("%d", &year_of_publication);
    while(year_of_publication<minYEAR || year_of_publication>maxYEAR){
        printf("\nERRO!: O ano inserido encontra-se fora do intervalo de anos das publicações.\n");
        printf("\nPor favor, insira um ano correto (de %d até %d): ", minYEAR, maxYEAR);
        scanf("%d", &year_of_publication);
    }
    printf("Inserir um N (p/tamanho da lista de autores que mais publicaram em %d): ", year_of_publication);
    scanf("%d", &N);

    CA_NAuthorsGivenYear(year_of_publication,N);
}


void CA_Querie13(int minYEAR,int maxYEAR)
{
    char *name;
    int i,a,year_of_publication;

    system("clear");

    name = (char*) malloc(MAX_NAME_SIZE*sizeof(char));

    printf("\n\nInsira um autor: ");
    fgets(name,MAX_NAME_SIZE,stdin); /*just to ignore the previous printf*/

    if(NULL!=fgets(name,MAX_NAME_SIZE,stdin)){
        printf("Inserir ano: ");
        scanf("%d", &year_of_publication);

        if(year_of_publication>maxYEAR || year_of_publication<minYEAR){
        system("clear"); a++;
        printf("\n\nERRO! Ano inserido é inválido.\nPor favor tente novamente introduzindo um ano entre %d e %d.\n\n", minYEAR,maxYEAR);
        return;
        }

        for(i=0;name[i]!='\n';i++); name[i]='\0'; /*eliminating '\n' from name*/

        CA_PercentilsAuthorYear(name,year_of_publication);
    }
}

void IA_Querie14()
{
    char *smallest_name=NULL;
    char *biggest_name=NULL;

    smallest_name = IA_getSmallest();
    biggest_name = IA_getBiggest();
    system("clear");
    printf("\n\nValor médio do comprimento dos nomes dos diferentes autores:  %d.\n", IA_getNamesMediumLength());   
    printf("Nome mais pequeno no ficheiro: %s (Tamanho: %d).\n", smallest_name, IA_getSmallestSize());
    printf("Nome maior no ficheiro: %s (Tamanho: %d).\n\n", biggest_name, IA_getBiggestSize());
}

void CA_Querie99()
{
    int n=0;
    char* author_name;

    author_name=CA_getMaximumPublications(&n);
    system("clear");
    printf("\n\nO número máximo de publicações de um autor é de %d (Autor: %s).\n\n", n, author_name);

}


void CA_Querie101()
{
    char *name;
    int i;

    system("clear");

    name = (char*) malloc(MAX_NAME_SIZE*sizeof(char));
    printf("\n\nInsira um autor: ");
    fgets(name,MAX_NAME_SIZE,stdin); /*just to ignore the previous printf*/
    fgets(name,MAX_NAME_SIZE,stdin); /*just to ignore the previous printf*/
    for(i=0;name[i]!='\n';i++); name[i]='\0';

    CA_PrintfAuthorPersonalRecord(name);
}

/*---------------------------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------  \END   --------------------------------------------------------------------*/
/*---------------------------------------------------------------------------------------------------------------------------------*/





/*---------------------------------------------------------------------------------------------------------------------------------*/
/*---------------------------------------------   MODUL INDICE DE AUTORES   -------------------------------------------------------*/
/*---------------------------------------------------------------------------------------------------------------------------------*/



/*Querie 6.*/
/**
    @param uma letra (um carater) de A a Z (maiúscula ou mínuscula)
    @return nada. Imprime páginas e escreve num ficheiro a listagem dos nomes começados pela letra passada como parâmetro
*/
void IA_Print_NamesStartedByLetter(char c)
{
    FILE *f;
    char **list_authors=NULL;
    int t, i;
    int total_pages=0;
    char answ;
    int flag=0;

    if(islower(c)) c-=32;

    t=IA_getTotalLetter(c);
    
    list_authors=IA_getAuthorsListNames(c);

    if(list_authors!=NULL){
        f=fopen("nomes_comecados_por?.txt", "w");
        fprintf(f, "##################TABELA DE NOMES COMEÇADOS POR '%c'##################\n\n", c);
        IA_PrintingNamesInFILE(list_authors,f,t);
        fprintf(f, "\n\nTOTAL: %d nomes\n", t);
        fclose(f);
        printf("\n\nPor favor consulte nomes_comecados_por?.txt para visualizar lista de nomes começados por '%c'.\n", c);
        printf("Total de nomes começados por '%c': %d\n\n", c, t);
        printf("Caso pretenda listar os nomes no terminal insira 'n' caso contrário insira 's'  ");

        answ=getchar();
        answ=getchar();

        if(answ=='n'){
            system("clear");

            if(t<24){ /*mostly not true*/
                IA_PrintMinorCase(list_authors);
                return;
            }

            total_pages=(t/24);
            if(t%24!=0) total_pages++; /*in case we need one more page*/
            IA_PrintPagesInTerminal(list_authors,total_pages,answ,t,flag,0,0);
        }
    }

    else printf("\n\nNão existe nenhum nome começado com a letra %c.\n\n", c);

    for(i=0;i<t;i++)
        free(list_authors[i]);
    free(list_authors);
}


/*Just in case we don't find in the file more than 24 names started by the requested letter*/
/**
    @param um array de nomes de autores
    @return nada. Imprime a lista de nomes dos autores caso o comprimento da lista de autores seja menor que 24 (nunca acontece)
*/
void IA_PrintMinorCase (char** authors_names){
    int i;

    printf("################LISTA DE NOMES#################\n");
    for(i=0; authors_names[i]!=NULL; i++)
        printf(" - %s\n", authors_names[i]);
    printf("\n");
}



/*Função base de paginações no terminal*/
/*
flagPRINTS:
0 - a função chamadora é:    IA_Print_NamesStartedByLetter(char c)
1 - a função chamadora é:    IA_Print_Foreign_Names()
2 - a função chamadora é:    CA_ListAuthorsYearsInterval (int minYEAR, int maxYEAR)
*/
/**
    @param um array de nomes de autores, um total de páginas (limite), um carater para gravar feedback do utilizador, uma flagPRINTS
           que nos diz qual o contexto de execução da função (como mostra em cima), um intervalo fechado de anos caso o contexto de execução
           seja o 3º explicitado em cima
    @return nada. Gera páginas bem formatadas de nomes de autores e implementa uma navegação interativa sobre essa lista
*/
void IA_PrintPagesInTerminal(char** authors_names, int total_pages, char answ, int total_names, int flagPRINTS, int minYEAR, int maxYEAR)
{

    int page=1;
    int i=0;
    int index=0;
  
    int countnames=0;
    int N=0;

    while(countnames<=total_names){

        if(answ=='n' && countnames < total_names){
            system("clear");

            if(total_names-countnames<24) N=total_names-countnames;
                else N=24;


            /*Choosing "title" of terminal page*/
            if(flagPRINTS==0)
                printf("################LISTA DE NOMES COMEÇADOS POR -%c-################\n", authors_names[0][0]);
            else if(flagPRINTS==1)
                printf("################LISTA DE NOMES ESTRANGEIROS################\n");
            else if(flagPRINTS==2)
                printf("###################LISTA DE AUTORES QUE PUBLICARAM EM TODOS OS ANOS DESDE %d ATÉ %d###################\n\n", minYEAR, maxYEAR);


            for(i=0; i<N; i++, index++)
                printf(" - %s\n", authors_names[index]);
                        
            printf("\nPágina: %d/%d\n",page,total_pages);

            if(page==1)
                printf("\nPara consultar página seguinte insira 'n' caso pretenda saír insira 's'\n> ");
            else if(page>1 && page!=total_pages)
                printf("\nPágina anterior: 'p'      Página seguinte: 'n'      Saír: 's'\n> ");
            else if(page==total_pages)
                printf("Página anterior: 'p'   Saír: 's'\n> ");

            page++;
            countnames+=N;

            answ=getchar();
            answ=getchar(); /*just avoiding \n*/
        }
        else if(answ=='p' && page>2){
            system("clear");

            if(countnames==total_names) index-=24+N;
            else index-=48;
      
            countnames-=24+N;
            N=24;
            page-=2;
            

            /*Choosing "title" of terminal page*/
            if(flagPRINTS==0)
                printf("################LISTA DE NOMES COMEÇADOS POR -%c-################\n", authors_names[0][0]);
            else if(flagPRINTS==1)
                printf("################LISTA DE NOMES ESTRANGEIROS################\n");
            else if(flagPRINTS==2)
                printf("###################LISTA DE AUTORES QUE PUBLICARAM EM TODOS OS ANOS DESDE %d ATÉ %d###################\n\n", minYEAR, maxYEAR);


            for(i=0; i<N; i++, index++)
                printf(" - %s\n", authors_names[index]);
            printf("\nPágina: %d/%d\n",page,total_pages);

            if(page==1)
                printf("\nPara consultar página seguinte insira 'n' caso pretenda saír insira 's': ");
            else if(page>1)
                printf("\nPágina anterior: 'p'      Página seguinte: 'n'      Saír: 's'\n> ");
            else if(page==total_pages)
                printf("Página anterior:   'p' Saír: 's'\n> ");

            countnames+=N;
            page++;

            answ=getchar();
            answ=getchar();
        }
        else break;

    }


    system("clear");
    printf("\n\nConsulta terminada.\n");

}


/*Querie 66.*/
/**
    @return nada. Imprime no ecrã e escreve para um ficheiro a lista de nomes estrangeiros (cuja letra inicial não se encontra entre A e Z)
*/
void IA_Print_Foreign_Names()
{
    FILE *f;
    char** list_authors=NULL;
    int t, i;
    int total_pages=0;
    char answ;
    int flag=1;

    t=IA_getTotalLetter('*');
    
    list_authors=IA_getAuthorsListNames('*');


    if(list_authors!=NULL){
        f=fopen("nomes_estrangeiros.txt", "w");
        fprintf(f, "##################TABELA DE NOMES ESTRANGEIROS##################\n\n");
        IA_PrintingNamesInFILE(list_authors,f,t);
        fprintf(f, "\n\nTOTAL: %d nomes\n", t);
        fclose(f);
        printf("\n\nPor favor consulte o ficheiro nomes_estrangeiros.txt para visualizar lista de nomes estrangeiros.\n");
        printf("Total de nomes estrangeiros: %d\n\n", t);

        printf("Caso pretenda listar os nomes estrangeiros no terminal insira 'n' caso contrário insira 's'  ");

        answ=getchar();/*just to ignore previous \n*/
        answ=getchar();

        if(answ=='n'){
            system("clear");

            if(t<24){ /*mostly not true*/
                IA_PrintMinorCase(list_authors);
                return;
            }

            total_pages=(t/24);
            if(t%24!=0) total_pages++; /*in case that the number of pages pair*/
            IA_PrintPagesInTerminal(list_authors,total_pages,answ,t,flag,0,0);
        }
    }   

    else printf("\n\nNão existe nenhum nome estrangeiro.\n\n");


    for(i=0;i<t;i++)
        free(list_authors[i]);
    free(list_authors);
}


/**
    @param um array de nomes de autores, um descritor de um ficherio e o tamanho do array de nomes
    @return nada. Esta função apenas escreve no ficheiro passado como parâmetro a lista de nomes (também passada como parâmetro)
*/
void IA_PrintingNamesInFILE (char** names, FILE * f,int total_names)
{
    int i;

    fprintf(f,"\n");
    for(i=0; i<total_names; i++)
        fprintf(f, " - %s\n", names[i]);
}
/*---------------------------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------  \END   --------------------------------------------------------------------*/
/*---------------------------------------------------------------------------------------------------------------------------------*/







/*---------------------------------------------------------------------------------------------------------------------------------*/
/*------------------------------------------------   MODUL ESTATISTICA   ----------------------------------------------------------*/
/*---------------------------------------------------------------------------------------------------------------------------------*/


/*Querie 2.*/
/**
    @param o numero de publicações total (nº de linhas do ficheiro)
    @return não retorna nada. Imprime no ecrã uma tabela de publicações descriminando a quantidade por ano
*/
void E_Print_Publications_Per_Years(int number_of_publications)
{
    int i;
    int *years=NULL;
    int *publications=NULL;
    int N = E_getCountYEAR(); /*stands for the number of diferent years*/

    years = E_getYearsOfPublications();
    publications = E_getPublicationsPerYear();

    printf("\n\n##################TABELA########################\n");
    for(i=0; i<N; i++) /*counterYEAR gives the number of diferent years of publications*/
        printf("Ano: %d Número de publicações: %d\n", years[i], publications[i]);
    printf("\t\t\t  TOTAL: %d", number_of_publications);
    putchar('\n');

    free(years);
    free(publications);
}


/*Querie 7.*/
/**
    @param intervalo fechado (já validado) de anos
    @return nada. Determina o nº de publicações no intervalo fechado de anos passado como argumento
*/
void E_Value_Years_Interval (int inf_date, int sup_date)
{
    int i=0;
    int sum=0;
    int *years=NULL;
    int *publications=NULL;

    years = E_getYearsOfPublications();
    publications = E_getPublicationsPerYear();

    while(years[i]<inf_date) i++;

    /*i reach the index of position of a date iqual or bigger then inf_date*/

    for(;years[i]<=sup_date; sum+=publications[i], i++);

    printf("\n\nO número de publicações no intervalo [%d,%d] é de: %d.\n", inf_date, sup_date, sum);

    free(years);
    free(publications);
}


/*Querie 10.*/
/**
    @param um ano e um nome de um ficheiro
    @return nada. Escreve no ficheiro de nome passado como parâmetro p número de publicações com 1, 2 e 3 autores respetivamente
*/
void E_Authors_123 (int year, char * file_name)
{
    FILE *f;
    int i, j;
    int *years=NULL;
    int **counter_authors=NULL;
    int N = E_getCountYEAR();

    years = E_getYearsOfPublications();
    counter_authors = E_getAuthorsPublicationsTable();

    for(i=0; i<N && years[i]!=year; i++);

    f=fopen(file_name, "w");    

    /*Means that we find the year in the e_datesStore array*/
    if(i<N){

       printf("##################TABELA######################\n");
       fprintf(f, "##################TABELA######################\n");
       printf("Ano: %d\n", year);
       fprintf(f, "Ano: %d\n", year);

       for(j=0; j<3; j++){
           printf("Número de publicações com %d autor(es): %d\n", j+1, counter_authors[i][j]);
           fprintf(f, "Número de publicações com %d autor(es): %d\n", j+1, counter_authors[i][j]);
       }

    }

    fclose(f);

    free(years);
    for(i=0;i<N;i++)
        free(counter_authors[i]);
    free(counter_authors);
}


/*Querie 11.*/
/**
    @return nada. Gera um ficheiro que descrimina o número de publicações de um ano pelo número de autores
*/
void E_Generate_CSVfile ()
{
    FILE *f;
    int i, j;
    int *years=NULL;
    int **counter_authors=NULL;
    int e_countYEAR = E_getCountYEAR();

    years = E_getYearsOfPublications();
    counter_authors = E_getAuthorsPublicationsTable();

    f=fopen("etapa11.csv", "w");

    fprintf(f, "Anos #Autores #Artigos\n");

    for(i=0; i<e_countYEAR; i++){

        for(j=0; counter_authors[i][j]!=-1 ;j++){
            if(counter_authors[i][j]>0) /*Avoids printing years in which the number of pub is 0*/
                fprintf(f, "%d %d %d\n", years[i], j+1 ,counter_authors[i][j]);
        }
    }
    
    fclose(f);

    free(years);
    for(i=0;i<e_countYEAR;i++)
        free(counter_authors[i]);
    free(counter_authors);
}


/*Some extra Queries that allow us to do some more detailed profiling*/
/*Querie 22.*/
/**
    @param nº total de publicações
    @return nada. Imprime uma tabela de percentagens de publicações por ano para uma melhor visualização da dispersão do número
            de publicações pelo diferente número de anos
*/
void E_Print_Percentils_Per_Years(int total)
{   
    int i;
    int *years=NULL;
    int *publications=NULL;
    float r;
    float t=0;
    int N = E_getCountYEAR();

    years = E_getYearsOfPublications();
    publications = E_getPublicationsPerYear();

    printf("\n\n###########################TABELA DE PERCENTAGENS#################################\n");
    for(i=0; i<N; i++){ 
        r=((float)publications[i]/(float)total)*100;
        t+=r;
        printf("Ano: %d Percentagem: %.2f%%\n", years[i], r);
    }
    printf("\t        TOTAL: %.2f%%\n", round(t));
    putchar('\n');

    free(years);
    free(publications);
}


/*http://www.codeproject.com/Articles/58289/C-Round-Function*/
/**
    @param um valor do tipo float
    @return um valor arredondado do passado como parâmetro
*/
float round(float number)
{
    return (number >= 0) ? (int)(number + 0.5) : (int)(number - 0.5);
}


/*Querie 23*/
/**
    @return nada. Procura o valor e respetivo ano da publicação mais longa do ficheiro
*/
void E_Max_Authors_inPublication()
{   
    int year, number_of_authors;

    E_getLongestPublication(&year,&number_of_authors);

    printf("\n\nO número máximo de autores numa publicação é de: %d (ano: %d).\n", number_of_authors, year);

}

/*---------------------------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------  \END   --------------------------------------------------------------------*/
/*---------------------------------------------------------------------------------------------------------------------------------*/







/*---------------------------------------------------------------------------------------------------------------------------------*/
/*---------------------------------------------   MODUL CÁTALOGO DE AUTORES   -----------------------------------------------------*/
/*---------------------------------------------------------------------------------------------------------------------------------*/



/*Querie 5.*/ /*ficou acordado entre docente e aluno que não seriam impressas as publicações a 0*/
/**
    @param um nome de um autor
    @return nada. Escreve num ficheiro e imprime no terminal a tabela de publicações do autor cujo nome é passado como  parâmetro
*/
void CA_TableOfPublicationsAuthor (char * name)
{
    int *years=NULL;
    int *publications=NULL;
    int i;

    years = CA_getYearsArrayPublications(years,name);

    if(years==NULL){
        system("clear");
        printf("\n\nERRO! AUTOR: [%s] não encontrado!\n\n", name);
        return;
    }

    publications = CA_getPublicationsPerYear(publications,name);

    system("clear");

    printf("################TABELA DE PUBLICAÇÕES DE -%s-################\n", name);
    for(i=0;years[i]!=-1;i++)
        printf("Ano: %d        Publicações: %d\n", years[i], publications[i]);
    printf("TOTAL: %d\n", i);

    free(years);
    free(publications);
}



/*Querie 8.*/
/**
    @param um nome de um autor
    @return nada. Imprime no ecrã os autores com quem o autor passado como parâmetro mais publicou e descriminando o número de publicações
*/
void CA_WhoMaxCoAuthories (char * name)
{
    int i, j;
    char **authors_names;
    int number_of_publications;

    authors_names = CA_getMaxCoAuthories (name,&number_of_publications);

    if(authors_names==NULL){
        system("clear");
        printf("\n\nERRO! AUTOR: [%s] não encontrado!\n\n", name);
        return;
    }

    system("clear");
    printf("############AUTOR(ES) COM QUEM -%s- MAIS PUBLICOU############\n\n", name);
    for(i=0; authors_names[i]!=NULL; i++)
        printf(" - %s (nº de coautorias: %d)\n", authors_names[i], number_of_publications);
    printf("\n");

    for(j=0;j<i;j++)
        free(authors_names[j]);
    free(authors_names);
}



/*Querie 9.*/
/**
    @param um intervalo fechado de anos
    @return nada. Escre para um ficheiro e lista no termimal o nome dos autores que publicaram em todos os anos do intervalo fechado passado
            como parâmetro
*/
void CA_ListAuthorsYearsInterval (int minYEAR, int maxYEAR)
{
    int i, n;
    int total_pages=0;
    char answ='\0';
    FILE *f;
    char **authors_names;
    int flag=2;

    authors_names = CA_AuthorsYearsInterval(minYEAR,maxYEAR,&n);

    f = fopen("list_intv_anos.txt", "w");
    fprintf(f, "###################LISTA DE AUTORES QUE PUBLICARAM ENTRE %d e %d###################\n\n", minYEAR, maxYEAR);

    for(i=0; i<n;i++){
        fprintf(f, " - %s\n", authors_names[i]);
    }
    fprintf(f, "\nTOTAL: %d\n", i);
    fclose(f);

    system("clear");
    if(n!=0){
        printf("\n\nPor favor consulte .txt para visualizar lista de autores que publicaram entre %d e %d.\n(TOTAL: %d autores)\n\n", minYEAR, maxYEAR, n);
        printf("Caso pretenda listar no terminal os autores que publicaram entre %d e %d insira 'n' caso contrário insira 's' ", minYEAR, maxYEAR);

        answ=getchar();
        answ=getchar();/*just to avoid '\n'*/

        if(answ=='n'){
            system("clear");

            if(n<24){ /*mostly not true*/
                printf("###################LISTA DE AUTORES QUE PUBLICARAM ENTRE %d e %d###################\n\n", minYEAR, maxYEAR);
                for(i=0;i<n;i++)
                    printf(" - %s\n", authors_names[i]);

                for(i=0;i<n;i++)
                    free(authors_names[i]);
                free(authors_names);

                return;
            }

            total_pages=(n/24);
            if(n%24!=0) total_pages++; /*just in case we need one more page*/
            IA_PrintPagesInTerminal(authors_names,total_pages,answ,n,flag,minYEAR,maxYEAR);
        }
    }

    else printf("\n\nNão existe nenhum autor que tenha publicado em todos os anos desde %d até %d.\n\n", minYEAR, maxYEAR);

    for(i=0;i<n;i++)
        free(authors_names[i]);
    free(authors_names);

}


/*Querie 12.*/
/**
    @param um ano e um comprimento para a lista dos autores que mais publicaram nesse ano
    @return nada. Gera a lista dos N (passado como parâmetro) autores que mais publicaram no ano passado como parâmetro
*/
void CA_NAuthorsGivenYear(int year_of_publication, int N)
{
    int i;
    int total_pages=0;
    char answ='\0';
    int * values;
    FILE *f;
    char** authors_names=NULL;

    values = (int*) malloc(N*sizeof(int));


    for(i=0; i<N; i++)
        values[i]=0;


    authors_names = CA_getNAuthorsInYear(year_of_publication, &N, values);

    f = fopen("NAutores.txt", "w");

    fprintf(f, "####################### %d AUTORES QUE MAIS PUBLICARAM EM %d ########################\n\n", N, year_of_publication);
    for(i=0; authors_names[i]!=NULL; i++)
        fprintf(f, "%s\nPublicacoes:%d\n\n", authors_names[i], values[i]);
    fprintf(f, "\nTOTAL: %d", i);
    fclose(f);


    system("clear");


    printf("\n\nPor favor consulte NAutores.txt para visualizar a lista de %d autores que mais artigos publicaram em %d.\n\n", N, year_of_publication);
    printf("Caso pretenda listar os nomes dos autores insira 'n' caso contrário insira 's' ");

    answ=getchar();
    answ=getchar();/*just to avoid '\n'*/

    if(answ=='n'){
        system("clear");

        if(N<=10){

            printf("####################### %d AUTORES QUE MAIS PUBLICARAM EM %d ########################\n\n", N, year_of_publication);
            for(i=0; i<N; i++)
                printf("\nAutor: %s\nPublicações em %d: %d\n\n", authors_names[i], year_of_publication, values[i]);

            for(i=0;i<N+1;i++)
                free(authors_names[i]);
            free(authors_names);
            free(values);
            return;        
        }

        else{

            total_pages=(N/10);
            if(N%10!=0) total_pages++; /*just in case we need one more page*/
            CA_PrintPagesInTerminal(authors_names,total_pages,answ,N,values,year_of_publication);
        }
    }

    for(i=0;i<N+1;i++)
        free(authors_names[i]);
    free(authors_names);

    free(values);
}


/*Função especial que imprime nome seguido de número de publicações do autor num dado ano para querie 12.*/
/**
    @param um array de nomes de autores, um total de páginas (limite), um carater para gravar feedback do utilizador,
           um array com o número de publicações nesse ano de cada autor da lista de autores e um ano
    @return nada. Gera páginas bem formatadas de nomes de autores e quantidades de publicações e implementa
            uma navegação interativa sobre essa lista
*/
void CA_PrintPagesInTerminal(char** authors_names, int total_pages, char answ, int total_names, int * values, int year_of_publication)
{

    int page=1;
    int i=0;
    int index=0;
  
    int countnames=0;
    int N=0;

    while(countnames<=total_names){

        if(answ=='n' && countnames < total_names){
            system("clear");

            if(total_names-countnames<10) N=total_names-countnames;
                else N=10;

            printf("####################### %d AUTORES QUE MAIS PUBLICARAM EM %d ########################\n", total_names, year_of_publication);
            for(i=0; i<N; i++, index++)
                printf("\nAutor: %s\nPublicações em %d: %d\n", authors_names[index], year_of_publication, values[index]);
                        
            printf("\nPágina: %d/%d\n",page,total_pages);

            if(page==1)
                printf("\nPara consultar página seguinte insira 'n' caso pretenda saír insira 's'\n> ");
            else if(page>1 && page!=total_pages)
                printf("\nPágina anterior: 'p'      Página seguinte: 'n'      Saír: 's'\n> ");
            else if(page==total_pages)
                printf("Página anterior: 'p'   Saír: 's'\n> ");

            page++;
            countnames+=N;

            answ=getchar();
            answ=getchar(); /*just avoiding \n*/
        }
        else if(answ=='p' && page>2){
            system("clear");

            if(countnames==total_names) index-=10+N;
            else index-=20;
      
            countnames-=10+N;
            N=10;
            page-=2;
            
            printf("####################### %d AUTORES QUE MAIS PUBLICARAM EM %d ########################\n", total_names, year_of_publication);
            for(i=0; i<N; i++, index++)
                printf("\nAutor: %s\nPublicações em %d: %d\n", authors_names[index], year_of_publication, values[index]);
            printf("\nPágina: %d/%d\n",page,total_pages);

            if(page==1)
                printf("\nPara consultar página seguinte insira 'n' caso pretenda saír insira 's': ");
            else if(page>1)
                printf("\nPágina anterior: 'p'      Página seguinte: 'n'      Saír: 's'\n> ");
            else if(page==total_pages)
                printf("Página anterior:   'p' Saír: 's'\n> ");

            countnames+=N;
            page++;

            answ=getchar();
            answ=getchar();
        }
        else break;

    }

    system("clear");
    printf("\n\nConsulta terminada.\n");

}




/*Querie 13.*/
/**
    @param um nome de um autor e um ano
    @return nada. Imprime no terminal a percentagem de publicações do autor nesse ano
*/
void CA_PercentilsAuthorYear (char * name, int year_of_publication)
{
    float r;

    r = CA_getPercentilsInYear(name,year_of_publication);

    if(r==-1){
        system("clear");
        printf("\n\nERRO! AUTOR: [%s] não encontrado!\n\n", name);
        return;
    }

    system("clear");
    if(r!=0)
        printf("\n\n%s publicou %0.5f%% dos artigos do ano %d.\n\n", name, r, year_of_publication);
    else printf("\n\n%s não publicou nenhum artigo em %d.\n\n", name, year_of_publication);
}




/**
    @param o nome de um autor
    @return nada. Criar um ficha pessoal de um autor com a sua tabela de publicações (Querie 5.) e lista de coautores
            com descriminação do número de coautorias por autor (Querie 8. mais completa)
*/
void CA_PrintfAuthorPersonalRecord(char * name)
{
    FILE * f;
    int *years=NULL;
    int *publications=NULL;
    int i;
    int max=0;
    int total=0;

    years = CA_getYearsArrayPublications(years,name);
    if(years==NULL){
        system("clear");
        printf("\n\nERRO! AUTOR: [%s] não encontrado!\n\n", name);
        return;
    }

    publications = CA_getPublicationsPerYear(publications,name);

    char **co_authorslist = CA_getListCoAuthorsGivenName(name);
    int *co_authors_pubs = CA_getCoAutsPublications(name);
    int number_of_coauthors = CA_getNumberOfCoAuthorsGivenName(name);


    /*generate .pdf file with table for the author*/
    f=fopen("fichadoautor.txt", "w");
   
    fprintf(f, "\\documentclass[a4paper]{article}\n");
    fprintf(f, "\\usepackage[utf8]{inputenc}\n");
    fprintf(f, "\\title{%s}\n", name);
    fprintf(f, "\\begin{document}\n\\maketitle\n");

    fprintf(f, "\\begin{center} \\textsc{\\huge{Tabela de Publicações} }\\end{center}\n");
    for(i=0; years[i]!=-1; i++){
        fprintf(f, "\\textbf{Ano}: %d    \\textbf{Publicações}: \\large{%d} \\\\ \n", years[i], publications[i]);
        total+=publications[i];
    }
    fprintf(f,"\\\\\\textit{Total de publicações}: \\textbf{%d}\n", total);
    fprintf(f, "\\pagebreak");

    fprintf(f, "\\begin{center} \\textsc{\\huge{Tabela de Coautorias} }\\end{center}\n");
    max=co_authors_pubs[0];
    for(i=0; i<number_of_coauthors; i++){
        if(co_authors_pubs[i]==max)
            fprintf(f, "\\textbf{Coautor}: \\textbf{\\huge{%s}}    \\textbf{Coautorias}: \\textbf{\\huge{%d}} \\\\ \n", co_authorslist[i], co_authors_pubs[i]);
        else fprintf(f, "\\textbf{Coautor}: %s    \\textbf{Coautorias}: \\large{%d} \\\\ \n", co_authorslist[i], co_authors_pubs[i]);
    }
    fprintf(f,"\\\\\\textit{Total de Coautores}: \\textbf{%d}\n", number_of_coauthors);


    fprintf(f, "\\end{document}");
    fclose(f);


    for(i=0;i<number_of_coauthors;i++)
        free(co_authorslist[i]);
    free(co_authorslist);

    free(co_authors_pubs);
    free(years);
    free(publications);


    /*creating .pdf file and open it trough system calls*/
    system("pdflatex fichadoautor.txt");
    system("rm -f fichadoautor.txt");
    system("rm -f fichadoautor.log");
    system("rm -f fichadoautor.aux");
    system("clear");

    sleep(1); /*Giving some time so the system produces the correct dysplay*/

    printf("\n\nFicha pessoal gerada com sucesso, por favor consulte fichadoautor.pdf.\n\n");
}



/**
    @return nada. Cria um catálogo de anos. Para cada ano lista os autores que publicaram nesse ano por ordem decrescente do número
            de artigos publicados 
*/
void CA_PrintYearsCatalog()
{
    FILE *f;
    int i, N, j;
    int countYEAR = E_getCountYEAR();

    int *capublications=NULL;
    char** authors_names=NULL;

    int *years = E_getYearsOfPublications();
    int *epublications = E_getPublicationsPerYear();


    /*generate .pdf file with table for the author*/
    f=fopen("catalogo.txt", "w");


    for(i=0; i<countYEAR; i++){

        capublications = CA_getPublicationsInYear(i);
        authors_names = CA_getNamesList(i);
        N = CA_getNumberOfAuthorsInYear(i);

        fprintf(f, "ANO: %d\n", years[i]);
        for(j=0; j<N; j++)
            fprintf(f, "Autor: %s  Publicações:  %d\n", authors_names[j], capublications[j]);
        fprintf(f, "Total de publicacoes: %d \n", epublications[i]);
        fprintf(f,"\n\n");

        free(capublications);
    }

    fclose(f);

    free(years);
    free(epublications);

    system("clear");
    printf("\n\nCátalogo de publicações anual com listagem dos autores por ordem decrescente do número de publicações\ngerado com sucesso. Por favor consulte catalogo.txt.\n\n");
}

/*---------------------------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------  \END   --------------------------------------------------------------------*/
/*---------------------------------------------------------------------------------------------------------------------------------*/