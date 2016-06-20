#include"comandos.h"

/**
   @param toma como argumento a sequência armazenada na struct proteina.seq
   @return retorna EXISTE caso exista e NAO_EXISTE caso contrário
   */
int ja_existe_sequencia()
{
   if ((strlen(proteina.seq)) != 0) return EXISTE;
    else return NAO_EXISTE;
}


/**
   @return retorna a sequencia atual armazenada em proteina.seq
   */
char *obtem_sequencia()
{
   return proteina.seq;
}


/**
   @param recebe como argumento a seq que supostamente só tem A's, B's (, ) ou numeros que o utilizador acaba de introduzir
   @return devolve PASSOU caso só tenha os carateres mencionados anteriormente, e NAO_PASSOU caso contrário
   */
int nao_tem_so_A_B (char * args)
{
   int i=0;
   char letra_da_seq;

   letra_da_seq=args[i];

   while (letra_da_seq!='\0'){
         if((letra_da_seq=='A')||(letra_da_seq=='B')||(isdigit(letra_da_seq))||(letra_da_seq=='(')||(letra_da_seq==')')){
           i++;
           letra_da_seq=args[i];
         }
         else return NAO_PASSOU;
   }
   return PASSOU;
}


/**
   @param recebe os argumentos inseridos pelo utilizador
   @return devolve o numero total de argumento (total)
   */
int numero_de_argumentos(char * arg)
{
   int j, total=0, flag=0;
    
   for(j=0;arg[j]!='\0';j++) {
      if(flag && arg[j]==' ')
        flag=0;
      else if(!flag && arg[j]!=' '){
             flag=1;
             total++;
      }
   }
   return total;
}


/**
   @param recebe a sequencia introduzida e verifica se existem carateres extra para além das letras (os (,) e dígitos)
   @return devolve PASSOU caso contenha apenas as letras A e B, NAO_PASSOU caso contrário
   */
int seq_nao_tem_letras (char * args)
{
   int i;
  
   for(i=0;args[i]!='\0'||args[i]!='A'||args[i]!='B';i++)
      if(args[i]=='A'||args[i]=='B') return PASSOU;

   return NAO_PASSOU;
}


/**
   @param recebe a sequencia (args) verifica se o número de parentesis esquerdos é igual ao numero de parentesis direitos
   @retunr retorna PASSOU caso o nº de ')' seja igual ao numero de '(', retorna NAO_PASSOU caso contrario*/
int erro_numero_de_parentesis (char * args)
{
   int i;
   int conta_parentesis_direitos=0;
   int conta_parentesis_esquerdos=0;

   for(i=0;args[i]!='\0';i++){
      if(args[i]==')') conta_parentesis_direitos++;
      if(args[i]=='(') conta_parentesis_esquerdos++;
   }
   if(conta_parentesis_direitos==conta_parentesis_esquerdos) return PASSOU;
    else return NAO_PASSOU;
}



/**
   @param recebe a sequencia (args) e verifica se o que está entre parentesis são A's e B's
   @return devolve PASSOU se for válido o conteúdo dos parentesis, retorna NAO_PASSOU caso contrário
*/
int conteudo_entre_parentesis (char * args)
{
   int i;
   int j=0; /**indice para o array dos conteudos*/

   for(i=0;args[i]!='\0';i++){
      if(args[i]=='(' && args[i+1]==')') return NAO_PASSOU; /* ..().. devolve erro*/
      else if(args[i]=='(' && args[i+2]==')') return NAO_PASSOU; /* ...(A).. devolve erro*/
      else if(args[i]=='('){ /* dois argumentos ou mais dentro de parentesis*/
             i++;/* senao vai retornar logo erro pois '(' é diferente de A e de B*/
             while(args[i]!=')'){ /* testar se o conteúdo dos parentesis são A's e B's*/
                  if(args[i]=='A'){i++; proteina.conta_conteudo_entre_parentesis[j]++;}
                  else if(args[i]=='B'){i++; proteina.conta_conteudo_entre_parentesis[j]++;}
                   else return NAO_PASSOU;
             }
             j++;
      }
   }
   return PASSOU;
}


/**
   @param recebe como argumento a sequenica e testa se a seguir a uma parentesis vem um numero
   @return caso seja numero devolve PASSOU, caso contrário retorna NAO_PASSOU */
int seguinte_e_numero (char * args)
{
   int i=0;

   while(args[i]!='\0'){
        if(args[i]==')'){
          if(isdigit(args[i+1])) i++;
          else return NAO_PASSOU;
        }
        else i++;
   }
   return PASSOU;
}


/**
   @recebe como argumeto uma sequencia que para alem de letras contem carateres adicionais como numero e parentesis
   @return devolve passou se a sequecia passar em todos os testas, devolve NAO_PASSOU caso contrário*/
int testa_compactada_valida (char * args)
{
   if(seq_nao_tem_letras(args)==NAO_PASSOU) return NAO_PASSOU;
   else if(args[0]==')'||args[1]==')'||args[2]==')'||isdigit(args[0])) return NAO_PASSOU;/*posições 0 1 2 nunca podem seq ')'e o 1º dif de num*/
   else{
        if(erro_numero_de_parentesis(args)==NAO_PASSOU) return NAO_PASSOU;/*testa se nº de '(' = nº de ')'*/
        if(conteudo_entre_parentesis(args)==NAO_PASSOU) return NAO_PASSOU;/*se o num de carateres entre parentesis é valido(>1) e se são A's ou B's*/
        if(seguinte_e_numero(args)==NAO_PASSOU) return NAO_PASSOU;/*testa se depois de ) vem um numero*/
   }  	 
 return PASSOU;
}


/**
   @param recebe como argumento uma sequencia que para alem de letras contem carateres adicionais como numero e parentesis
   @return nao retorna nada, guarda em proteina.seq a sequencia inserida na sua forma descompactada, isto é, apenas contendo A's e B's*/
void descompactar_e_guardar (char * args)
{
   int i;/*indice que percorre a seq compactada inserida*/
   int j=0;/*indice da proteina.seq que vamos guardar*/
   int rep;/*guarda o numero que um dado grupo de repete (ABA)2-> rep=2*/
   int h=0;/*controla o ciclo em que guardamos h elementos que estavam dentro de parentesis*/
   int k;/*valor que representa o indice do primeiro carater dentro de parentesis, valor fixo, para podermos voltar a ele rep vezes*/
   int l=0;/*valor para o array conteudo_entre_parentesis*/
   int f=0;/*valor para guardar f vezes um determinado grupo que se repete rep vezes*/
   int primeiro_carater_dentro_parentesis;/*guarda o indice deste carater*/
   char numero[100];
   int z=0;
   int ind_carater_a_repetir;
   char *ponte_para_proteina; /*sofre as operações de descompactação para posteriormente ser copiado para proteina.seq e guardado*/
   int tamanho=5000;

   ponte_para_proteina = (char*)malloc(sizeof(int) * tamanho);

   for(i=0;args[i]!='\0';){
      if((args[i]=='A'||args[i]=='B') && !(isdigit(args[i+1]))){ponte_para_proteina[j]=args[i]; j++; i++;}
      else if((args[i]=='A'||args[i]=='B') && isdigit(args[i+1])){/*pedaço de codigo para apenas letras repetidas*/
             ind_carater_a_repetir=i;
             i++;
             while(isdigit(args[i]) && args[i]!='\0'){numero[z]=args[i]; i++; z++;}
             rep=atoi(numero);
             z=0;
             while(numero[z]!='\0'){numero[z]='\0'; z++;}/*para que nao haja conflito com numeros com mais dígitos*/
             z=0; f=0;
             while(f<rep){ponte_para_proteina[j]=args[ind_carater_a_repetir]; j++; f++;}
             rep=0;
             ind_carater_a_repetir=0;
      }	
      else if(args[i]=='('){
             primeiro_carater_dentro_parentesis=i+1;
             while(args[i]!=')') i++;
             i++;/*para apanhar o numero a seguir a ')'*/
             while(isdigit(args[i])){numero[z]=args[i]; i++; z++;}
             rep=atoi(numero);
             h=0; f=0; z=0;
             while(numero[z]!='\0'){numero[z]='\0'; z++;}
             z=0;
             while(f<rep){
                  k=primeiro_carater_dentro_parentesis;
                  h=0;
                   while(h<proteina.conta_conteudo_entre_parentesis[l]){
                        ponte_para_proteina[j]=args[k];
                        h++;k++;j++;
                   }
                   f++;
             }
             l++; /*avançamos para o valor dos conteudos de seguintes parentesis*/
      }
   }
   strcpy(proteina.seq, ponte_para_proteina);
   ponte_para_proteina=NULL; /*liberta ponte_para_proteina para ser usada novamente na ligação da seq com proteina.seq*/
   proteina.tamanho=j;
   proteina.tem_coordenadas=NAO;
   proteina.tem_dobra=NAO;
   proteina.esta_colocada=NAO;
}


/**
   @param recebe uma sequencia de A's e B's na forma extensa ou compactada se esta esta compactada guarda-a na forma mais extensa
   @return quando utilizado sem argumentos imprime a sequencia na forma mais extensa
*/
int cmd_seq(char *args)
{
   if (args!=(NULL)){
      if(numero_de_argumentos(args)!=1)
         return mensagem_de_erro(E_ARGS);
      else{
            if(nao_tem_so_A_B(args)==NAO_PASSOU) return mensagem_de_erro(E_SEQ_INV);
            else if(testa_compactada_valida(args)==PASSOU) descompactar_e_guardar(args);
            else return mensagem_de_erro(E_SEQ_INV);                   
            return 1;
      }            
   }
   else{
        if(ja_existe_sequencia()==EXISTE)
          return printf("%s\n", obtem_sequencia());
        else return mensagem_de_erro(E_NO_SEQ);
   }
}



/**
   @param recebe a string que contém as coordenadas
   @return devolve o numero de coordenadas presentes na string
*/
int conta_coordenadas (char * arg)
{
   proteina.num_coords=numero_de_argumentos(arg);
   return proteina.num_coords;/*numero de coordenadas validas atuais*/
}


/**
   @param recebe argc, a string que contem as coordenadas e guarda as mesmas no formato int
   @return nao devolve nada
*/
void guarda_coordenadas (char *argc)
{
   char *p;
   int i=0;

   while((p=strsep(&argc," "))!=(NULL)){
        coord[i].x = atoi(p);
        p=strsep(&argc," ");
        coord[i].y = atoi(p);
        i++;
   }
   proteina.tem_coordenadas=SIM;
}


/**
   @param nao recebe nada, vai à struct coords buscar as coordenadas guardadas
   @return imprime no ecrã as coordenadas na forma (x, y)*/
char *imprime_coordenadas ()
{
   int i;

   for(i=0;i<proteina.num_coords/2;i++){
        if(i!=0) printf(" ");
        printf("(%d, %d)", coord[i].x, coord[i].y);
   }
   putchar('\n');
   return 0;
}


/**
  @param recebe a string que contém as coordenadas
  @return devolve 0 caso as coordenadas sejam válidas
*/
int verifica_coordenadas(char *argc)
{
   int i = 0;
   int c = strlen(argc);
   int r;

   for (;i<c;i++){
       if (isdigit(argc[i]) || argc[i]==' ' || argc[i] == '+'|| argc[i] == '-' ) r=PASSOU;
       else return NAO_PASSOU;
   }
   return r;
}


/**
  @param o comando coords quando utilizado com argumentos guarda as coordenadas na forma de interiros caso sejam válidas
  @return quando utilizado sem argumentos imprime no ecrã as coordenadas na forma (x, y)
*/
int cmd_coords (char * args)
{
   if (args!=(NULL)) {
      if ((ja_existe_sequencia())!=1)
         return mensagem_de_erro(E_NO_SEQ);
      else{
           if (conta_coordenadas(args)!=(2*proteina.tamanho))
              return mensagem_de_erro (E_ARGS);
           else if (verifica_coordenadas(args)==PASSOU) guarda_coordenadas(args);
           else mensagem_de_erro(E_COORDS);
      }
   }
   else if ((ja_existe_sequencia())==NAO_EXISTE)
           return mensagem_de_erro (E_NAO_COLOC);
   else if (proteina.tem_coordenadas==SIM)
           imprime_coordenadas();
   return 1;
}



/**
  @param recebe uma string que são as instruções de dobragem
  @return devolve uma mensagem de erro caso sejam inválidas, retorna 1 caso contrário
*/
int instrucoes_dobragem_validas(char * arg)
{
   int i;
   int s=strlen(arg);
   int t=proteina.tamanho;

   if(s<t){
           for(i=0;arg[i]!='\0';i++){
              if(arg[i]=='F'||arg[i]=='D'||arg[i]=='E');
              else return mensagem_de_erro(E_INVARGS);
           }     
   }
   else return mensagem_de_erro(E_ARGS); /*caso o tamanho das instruções exceda a sequencia de A's e B's*/

   return PASSOU;
}


/**
  @param recebe dois indices
  @return nao devolve nada, apenas efetua as operações de dobragens e salvaguarda os novos valores das coordenadas
*/
void efetua_dobragens(int i, int j, char orientacao, char * arg)
{
   int k=1;

   for(k=0;k<=proteina.num_coords;i++, j++, k++){
       if(orientacao=='e'){
                           if(arg[j]=='F'){coord[i].x=coord[j].x+1; coord[i].y=coord[j].y;}
                           if(arg[j]=='D'){coord[i].x=coord[j].x; coord[i].y=coord[j].y-1; orientacao='s';}
                           if(arg[j]=='E'){coord[i].x=coord[j].x; coord[i].y=coord[j].y+1; orientacao='n';}
       }
       else if(orientacao=='s'){
                                if(arg[j]=='F'){coord[i].x=coord[j].x; coord[i].y=coord[j].y-1;}
                                if(arg[j]=='D'){coord[i].x=coord[j].x-1; coord[i].y=coord[j].y; orientacao='o';}
                                if(arg[j]=='E'){coord[i].x=coord[j].x+1; coord[i].y=coord[j].y; orientacao='e';}
       }
       else if(orientacao=='n'){
                                if(arg[j]=='F'){coord[i].x=coord[j].x;coord[i].y=coord[j].y+1;}
                                if(arg[j]=='D'){coord[i].x=coord[j].x+1;coord[i].y=coord[j].y;orientacao='e';}
                                if(arg[j]=='E'){coord[i].x=coord[j].x-1;coord[i].y=coord[j].y;orientacao='o';}
       }
       else if(orientacao=='o'){
                                if(arg[j]=='F'){coord[i].x=coord[j].x-1; coord[i].y=coord[j].y;}
                                if(arg[j]=='D'){coord[i].x=coord[j].x; coord[i].y=coord[j].y+1; orientacao='n';}
                                if(arg[j]=='E'){coord[i].x=coord[j].x; coord[i].y=coord[j].y-1; orientacao='s';}
                         
       }
   }
}


/**
  @param recebe uma string que são instrucoes de dobragem F's D's e E's
  @return nao devolve nada
*/
void dobra_e_guarda_coordenadas (char * arg)
{
   int i=1;
   int j=0;
   int n;
   char orientacao;/*diz nos para que lado do gráfico Norte(n), Sul(s), Este(e), Oeste(o) esta orientada a "cabeça" da proteína*/

   n=strlen(arg);
   if(n!=proteina.tamanho-1){ /*Enquanto a sequencia de instruções não tiver tamanho máximo permitido acrescentamos F's*/
     while(n+i!=proteina.tamanho){
          arg[n+j]='F';
          i++; j++;
     }
   }
   j=0; i=0;
          
   proteina.num_coords=2*proteina.tamanho;
   coord[i].x=0; coord[i].y=0; i++;
   if(arg[j]=='F'){coord[i].x=coord[j].x+1;coord[i].y=coord[j].y;orientacao='e';}
   if(arg[j]=='D'){coord[i].x=coord[j].x;coord[i].y=coord[j].y-1;orientacao='s';}
   if(arg[j]=='E'){coord[i].x=coord[j].x;coord[i].y=coord[j].y+1;orientacao='n';}
   j++;i++;

   efetua_dobragens(i, j, orientacao, arg);

   proteina.tem_coordenadas=SIM;
   proteina.tem_dobra=SIM;
   proteina.esta_colocada=SIM;
}


/**
  @param recebe instrucoes de dobragem
  @return quando utilizado com argumentos nao devolve nada, sem argumentos devolve as coordenadas alteradas
*/
int cmd_dobrar (char * args)
{
   if(args!=NULL){
     if(ja_existe_sequencia()==NAO_EXISTE) return mensagem_de_erro(E_NO_SEQ);
     else{
          if(instrucoes_dobragem_validas(args)==PASSOU) dobra_e_guarda_coordenadas(args);
     }
   }
   else if(ja_existe_sequencia()==NAO_EXISTE) return mensagem_de_erro(E_NO_SEQ);
   else return mensagem_de_erro(E_ARGS);

   return 1;
}



/**
  @param testa se existem ou não coordenadas sobrepostas (com os mesmos valores de x e y)
  @return devolve SIM caso sejam validas, devolve NAO caso contrário
*/
int testa_coordenadas_sao_validas ()
{
   int i;
   int j=1;
 
   for(i=0;i<proteina.tamanho-1;i++, j++){
      if( (coord[i].x+1 == coord[j].x && coord[i].y == coord[j].y) || (coord[i].x-1 == coord[j].x && coord[i].y == coord[j].y)
          || (coord[i].y+1 == coord[j].y && coord[i].x == coord[j].x)
          || (coord[i].y-1 == coord[j].y && coord[i].x == coord[j].x) );
      else return NAO_PASSOU; 
   } 
   for(i=0;i<proteina.tamanho-1;i++){
      for(j=0;j<proteina.tamanho;j++){
         if(j!=i)
           if(coord[i].x==coord[j].x && coord[i].y==coord[j].y) return NAO_PASSOU;
      }
   }

   return PASSOU;
}


/**
  @param nao recebe argumentos
  @return retorna SIM caso a sequencia esteja bem colocada, retorna NAO caso contrário
*/
int cmd_validar (char * args)
{
   if(args==NULL){
     if(ja_existe_sequencia()==NAO_EXISTE) return mensagem_de_erro(E_NO_SEQ);
     else if(proteina.tem_coordenadas==SIM && testa_coordenadas_sao_validas()==PASSOU && proteina.tamanho>1){
             proteina.esta_colocada=SIM;
             return printf("SIM\n");
          }
     else if(proteina.tem_dobra==SIM && testa_coordenadas_sao_validas()==PASSOU){
             proteina.esta_colocada=SIM;
             return printf("SIM\n");
          }
     else return printf("NAO\n");
   }
   else return mensagem_de_erro(E_ARGS);
}


/**
   @param nao recebe nada
   @return imprime no ecra a sequencia na forma mais compactada*/
char *obtem_seq_compactada()
{
   return proteina.compactada;
}


/**
   @param nao recebe nada, opera com proteina.compactada. Nivel 1 encontra letras repetidas
   @return devolve 1 se tudo correr bem e guarda em proteina.compactada a sequencia na forma mais compactada possível.
*/
int compacta_nivel_um ()
{
   int i;/*percorre o array ponte_para_compactada*/
   int j=0;/*indice de ponte_para_compactada*/
   int conta_letras_repetidas=1;
   char numero[100];
   int z=0;

   char *ponte_para_compactada;
   int tamanho=5000;
   ponte_para_compactada = (char*)malloc(sizeof(int) * tamanho);

   for(i=0;proteina.compactada[i]!='\0';i++){
       if(proteina.compactada[i]=='('){
          while(proteina.compactada[i]!=')'){
               ponte_para_compactada[j]=proteina.compactada[i];
               i++; j++;
          }
          ponte_para_compactada[j]=proteina.compactada[i];
          i++; j++;
       }
       if(proteina.compactada[i]==proteina.compactada[i+1] && proteina.compactada[i+1]==proteina.compactada[i+2]){
         while(proteina.compactada[i]==proteina.compactada[i+1]){conta_letras_repetidas++, i++;}
         ponte_para_compactada[j]=proteina.compactada[i];
         j++;
         /*colocar o conta_letras_repetidas em ponte_para_compactada*/
         sprintf(numero, "%d", conta_letras_repetidas);
         while(numero[z]!='\0'){ponte_para_compactada[j]=numero[z]; z++; j++;}
         z=0;
         while(numero[z]!='\0'){numero[z]='\0';z++;}
         z=0;
         conta_letras_repetidas=1;
       }
       else{ponte_para_compactada[j]=proteina.compactada[i];j++;}
   }
   strcpy(proteina.compactada, ponte_para_compactada);
   ponte_para_compactada=NULL;

   return 1;
}

/*__________________________________________________NIVEL DOIS____________________________________________________________________________*/


/**
  @param nao recebe nada, opera com seq compactada e acha padões de pares de letras repetidas
  @return nao devolve nada, guarda em proteina.compactada a sequencia na forma mais compactada possível mas ainda incompleta
*/
int compacta_nivel_dois ()
{
   int i;/*percorre o proteina.compactada*/
   int ii=2;
   int k=1;
   int kk=3;
   int j=0;/*indice de ponte_para_compactada*/
   int conta_pares=1;
   char numero[100];
   int z=0;
   char conteudo_um;
   char conteudo_dois;

   char *ponte_para_compactada; /*sofre as operações de compactação para posteriormente ser copiado para proteina.compactada e guardado, e finalmente libertado*/
   int tamanho=5000;
   ponte_para_compactada = (char*)malloc(sizeof(int) * tamanho);

   for(i=0;proteina.compactada[i]!='\0';){
       if(proteina.compactada[i]=='('){
         while(proteina.compactada[i]!=')'){
              ponte_para_compactada[j]=proteina.compactada[i];
              i++; j++;
         }
         ponte_para_compactada[j]=proteina.compactada[i];
         i++; j++;
         k=i+1; ii=i+2; kk=i+3;      
       }
       if(proteina.compactada[i]==proteina.compactada[ii] && proteina.compactada[k]==proteina.compactada[kk] && proteina.compactada[i]!=proteina.compactada[i+1] && proteina.compactada[i]!=proteina.compactada[i-1]){

         conteudo_um=proteina.compactada[i]; conteudo_dois=proteina.compactada[i+1];

         while(proteina.compactada[i]==proteina.compactada[ii] && proteina.compactada[k]==proteina.compactada[kk]){conta_pares++; i+=2; ii+=2; k+=2; kk+=2;}
          if(conta_pares>1){/*existindo dois ou mais pares de letras*/
            ponte_para_compactada[j]='('; j++;/*abrir parentesis*/
            ponte_para_compactada[j]=conteudo_um; j++;
            ponte_para_compactada[j]=conteudo_dois; j++;/*inserir conteudo entre parentesis*/
            ponte_para_compactada[j]=')'; j++;/*fechar parentesis*/
            /*colocar o conta_letras_repetidas em ponte_para_compactada*/
            sprintf(numero, "%d", conta_pares);
            while(numero[z]!='\0'){ponte_para_compactada[j]=numero[z]; z++; j++;}
            z=0;
            while(numero[z]!='\0'){numero[z]='\0';z++;}
            z=0;
            /*numero de vezes que se repete o conteudo entre parentesis CONTA_PARES*/
            i+=2;
            conta_pares=1;/*reset para contar novos pares de letras*/
          }           
      }
      else{ponte_para_compactada[j]=proteina.compactada[i]; j++; i++; ii++; k++; kk++;}
   }
   strcpy(proteina.compactada, ponte_para_compactada);
   ponte_para_compactada=NULL;
 
   return 1;
}

/*__________________________________________________NIVEL TRES____________________________________________________________________________*/

/**
  @param nao recebe nada, opera com seq compactada e acha padões de triplas de letras repetidas
  @return nao devolve nada, guarda em proteina.compactada a sequencia na forma mais compactada possível mas ainda incompleta
*/
int compacta_nivel_tres ()
{
   int i=0;/*percorre o proteina.seq*/
   int ii=3;
   int k=1;
   int kk=4;
   int h=2;
   int hh=5;
   int j=0;/*indice de ponte_para_compactada*/
   char numero[100];
   int z=0;
   int conta_triplas=1;
   char conteudo_um;
   char conteudo_dois;
   char conteudo_tres;

 char *ponte_para_compactada;/*sofre as operações de compactação para posteriormente ser copiado para proteina.compactada e guardado, e finalmente libertado*/
 int tamanho=5000;
 ponte_para_compactada = (char*)malloc(sizeof(int) * tamanho);

   for(i=0;proteina.seq[i]!='\0';){
      if(proteina.seq[i]==proteina.seq[ii] && proteina.seq[k]==proteina.seq[kk] && proteina.seq[h]==proteina.seq[hh]
         && (proteina.seq[i]!=proteina.seq[i+1] || proteina.seq[k]!=proteina.seq[k+1])){

         conteudo_um=proteina.seq[i]; conteudo_dois=proteina.seq[i+1]; conteudo_tres=proteina.seq[i+2];

         while(proteina.seq[i]==proteina.seq[ii] && proteina.seq[k]==proteina.seq[kk] && proteina.seq[h]==proteina.seq[hh]){
               conta_triplas++; i+=3; ii+=3; k+=3; kk+=3; h+=3; hh+=3;
         }
          if(conta_triplas>1){/*existindo duas ou mais triplas de letras*/
            ponte_para_compactada[j]='('; j++;/*abrir parentesis*/
            ponte_para_compactada[j]=conteudo_um; j++;
            ponte_para_compactada[j]=conteudo_dois; j++;
            ponte_para_compactada[j]=conteudo_tres; j++;  /*até aqui inserimos conteudo entre parentesis*/
            ponte_para_compactada[j]=')'; j++;/*fechar parentesis*/
            /*colocar o conta_letras_repetidas em ponte_para_compactada*/
            sprintf(numero, "%d", conta_triplas);
            while(numero[z]!='\0'){ponte_para_compactada[j]=numero[z]; z++; j++;}
            z=0;
            while(numero[z]!='\0'){numero[z]='\0';z++;}
            z=0;
            i+=3; k+=3; h+=3; ii+=3; kk+=3; hh+=3;
            conta_triplas=1;/*reset para contar novos pares de letras*/
          }
      }
      else{ponte_para_compactada[j]=proteina.seq[i]; j++; i++; ii++; k++; kk++; h++; hh++;}
   }
 strcpy(proteina.compactada, ponte_para_compactada);
 ponte_para_compactada=NULL;
 
 return 1;
}

/**
  @param nao recebe nada, apenas invoca os tres níveis de compactação tres, dois e um respectivamente
  @return nao devolve nada
*/
void compactar_sequencia()
{
 compacta_nivel_tres ();
 compacta_nivel_dois ();
 compacta_nivel_um ();
}


/**
 @param não recebe nada, opera com uma string de A's e B's na sua forma mais extensa
 @return salvo erros que possam surgir, imprime no ecrã a sequencia na forma mais compactada
*/
int cmd_compactar (char * args)
{
   if(args!=NULL) return mensagem_de_erro(E_ARGS);
   else if(ja_existe_sequencia()==NAO_EXISTE) return mensagem_de_erro(E_NO_SEQ);
   else{
        if(proteina.tamanho==1) return printf("%s\n", obtem_sequencia()); /*3 casos em que a seq é a mesma na forma compactada*/
        else if(proteina.tamanho==2 && proteina.seq[0]!=proteina.seq[1]) return printf("%s\n", obtem_sequencia());
        else if((proteina.tamanho==3 && proteina.seq[0]!=proteina.seq[1]) && (proteina.seq[1]!=proteina.seq[2])) return printf("%s\n", obtem_sequencia());
        else compactar_sequencia();
   }
   return printf("%s\n", obtem_seq_compactada());
}



/**
  @param recebe uma string que é o nome do ficheiro onde vamos guardar a sequencia e as respectivas coordenadas
  @return não devolve nada quando invocada com um argumento
*/
void gravar (char * args)
{
   int j;
   FILE *fg;
   fg = fopen(args, "w");

   fprintf(fg, "%s\n", proteina.seq);

   for (j = 0; j < proteina.tamanho; j++) {
       if (j != proteina.tamanho-1) fprintf(fg, "(%d, %d) ", coord[j].x, coord[j].y);
       else fprintf(fg, "(%d, %d)\n", coord[j].x, coord[j].y);
   }
   fclose(fg);
}


/**
  @param recebe uma string que é o nome do ficheiro
  @return retorna 1 se o ficheiro for guardado com sucesso
*/
int cmd_gravar (char * args)
{
   if(ja_existe_sequencia()==NAO_EXISTE) return mensagem_de_erro(E_NO_SEQ);
   else if(proteina.tem_coordenadas==NAO) return mensagem_de_erro(E_NAO_COLOC);
   else if(numero_de_argumentos(args)!=1) return mensagem_de_erro(E_ARGS);
   else gravar(args);
   return 1;
}



/**
  @param não recebe nada, opera com a struct coords onde estão armazenadas as coordenadas
  @return retorna o valor da coordenada x mínima presente na struct
*/
int x_minimo ()
{
   int i=0;
   int min=coord[i].x;
 
   for(;i<proteina.tamanho;i++)
      if(coord[i].x<min) min=coord[i].x;
   return min;
}


/**
  @param não recebe nada, opera com a struct coords onde estão armazenadas as coordenadas
  @return retorna o valor da coordenada x máxima presente na struct
*/
int x_maximo ()
{
   int max=0;
   int i;
 
   for(i=0;i<proteina.tamanho;i++)
      if(coord[i].x>max) max=coord[i].x;
   return max;
}


/**
  @param não recebe nada, opera com a struct coords onde estão armazenadas as coordenadas
  @return retorna o valor da coordenada y mínima presente na struct
*/
int y_minimo ()
{
   int i=0;
   int min=coord[i].y;
 
   for(;i<proteina.tamanho;i++)
     if(coord[i].y<min) min=coord[i].y;
   return min;
}


/**
  @param não recebe nada, opera com a struct coords onde estão armazenadas as coordenadas
  @return retorna o valor da coordenada y máxima presente na struct
*/
int y_maximo ()
{
   int max=0;
   int i;
 
   for(i=0;i<proteina.tamanho;i++)
      if(coord[i].y>max) max=coord[i].y;
   return max;
}


/**
  @param recebe uma string que é o nome do ficheiro onde vamos criar o documento latex que é o gráfico correspondente às coordenadas e sequência atuais
  @return não retorna nada
*/
void criar_latex(char * nome_fich)
{
   int i;
   int x_min = x_minimo();
   int x_max = x_maximo();
   int y_min = y_minimo();
   int y_max = y_maximo();

   FILE *fp;
   fp = fopen(nome_fich, "w");
   fprintf(fp, "\\documentclass[a4paper]{article}\n");
   fprintf(fp, "\\usepackage{tikz}\n");
   fprintf(fp, "\\usetikzlibrary{arrows}\n");
   fprintf(fp, "\\begin{document}\n");
   fprintf(fp, "\\begin{center}\n");
   fprintf(fp, "\\begin{tikzpicture}[axis/.style={very thick, ->, >=stealth'}]\n");
   fprintf(fp, "\\draw[axis] (%d, %d) -- (%d, %d) node(xline)[right] {x};\n", x_min - 1, y_min - 1, x_max + 1, y_min - 1);
   fprintf(fp, "\\draw[axis] (%d, %d) -- (%d, %d) node(yline)[above] {y};\n\n", x_min - 1, y_min - 1, x_min - 1, y_max + 1);

   fprintf(fp, "\\foreach \\x in {%d,...,%d} {\n\t\\draw[dashed] (\\x, %d) node[below]{\\x} -- (\\x, %d);\n}\n\n", x_min, x_max, y_min-1, y_max+1);
   fprintf(fp, "\\foreach \\y in {%d,...,%d} {\n\t\\draw[dashed] (%d, \\y) node[left]{\\y} -- (%d, \\y);\n}\n\n", y_min, y_max, x_min-1, x_max+1);
   fprintf(fp, "\\draw[very thick]\n");

   for (i=0;i<proteina.tamanho;i++) {
        if (i<proteina.tamanho-1)
            if(proteina.seq[i] == 'A'){
                fprintf(fp, "(%d, %d) node[circle, draw, fill]{} -- ", coord[i].x, coord[i].y);
            } else fprintf(fp, "(%d, %d) node[circle, draw]{} -- ", coord[i].x, coord[i].y);
        else if (proteina.seq[i] == 'A') {
            fprintf(fp, "(%d, %d) node[circle, draw, fill]{};\n", coord[i].x, coord[i].y);
        } else fprintf(fp, "(%d, %d) node[circle, draw]{};\n", coord[i].x, coord[i].y);
   }
   fprintf(fp, "\\end{tikzpicture}\n");
   fprintf(fp, "\\end{center}\n");
   fprintf(fp, "\\end{document}\n");

   fclose(fp);
}


/**
  @param recebe uma string que é o nome do ficheiro com o qual vamos invocar a função criar_latex
  @return retorna 1 se tudo correr bem
*/
int cmd_latex (char * args)
{
   if(ja_existe_sequencia()==NAO_EXISTE) return mensagem_de_erro(E_NO_SEQ);
   else if(proteina.esta_colocada==NAO) return mensagem_de_erro(E_NAO_COLOC);
   else if(numero_de_argumentos(args)!=1) return mensagem_de_erro(E_ARGS);
   criar_latex(args);
   return 1;
}



/**
 @param recebe as energias e1, e2, e3 instroduzidas
 @return PASSOU caso os argumentos sejam válidos, NAO_PASSOU caso contrário
*/
int testa_argumentos (char * args)
{
   int i=0;
    
   while(args[i]!='\0'){
        if(isdigit(args[i])||args[i]=='+'||args[i]=='-'||args[i]==' ') i++;
        else return NAO_PASSOU;
   }
   return PASSOU;
}


/**
  @param recebe dois índices e verifica se estes se repetem no array pos.ortogonal, caso ambos se repitam significa que já foram testados, logo estariamos a incrementar a energia tendo em conta coordenadas que já foram consideradas anteriormente
  @return retorna PASSOU caso estes indices nao coincidam com valores no array pos.ortogonal, NAO_PASSOU no caso contrário
*/
int testa_posicao (int i, int j)
{
   int h;

   if(i==0) return PASSOU;
   for(h=0;h<pos.tamanho;h++)
      if(pos.ortogonal[h]==i && pos.ortogonal[h+1]==j) return NAO_PASSOU;
   return PASSOU;
}


/**
   @param recebe os valores de coord[i].x e coord[i].y da função procura_ligacoes_ortogonais e o indice com o qual queremos verificar a ortogonalidade das coordenadas
   @return devolve PASSOU caso sejam ortogonais, NAO_PASSOU caso contrário
*/
int verifica_ortogonalidade (int X, int Y, int j)
{
   if(((X == (coord[j].x)+1||X == (coord[j].x)-1) && Y == (coord[j].y))||((Y == (coord[j].y)+1||Y == (coord[j].y)-1) && X == (coord[j].x)))
     return PASSOU;
   return NAO_PASSOU;
}


/**
   @param recebe dois valores que representam índices
   @return não retorna nada
*/
void grava_indices_ortogonais (int i, int j)
{
   pos.ortogonal[pos.tamanho]=j;
   pos.tamanho++;
   pos.ortogonal[pos.tamanho]=i;
   pos.tamanho++;
}


/**
   @param recebe um indice que vamos associar à struct das coordenadas
   @return nao retorna nada (apenas guarda num array o numero de cada tipo de ligações AA AB BB que encontramos na proteína)
*/
void procura_ligacoes_ortogonais (int i, int * conta_ligacoes)
{
  int X, Y;
  int j;/*percorre a seq de A's e B's*/
  int AA=0;/*contador para os contactos do tipo AA (-1)*/
  int BB=0;/*contador para os contactos do tipo BB (0)*/
  int AB=0;/*contador para os contactos do tipo BA ou AB (0)*/
  char letra;

  X=coord[i].x; /*temos o x a comparar com as restantes coordenadas*/
  Y=coord[i].y; /*temos o y a comparar com as restantes coordenadas*/
  letra=proteina.seq[i]; /*temos a letra que corresponde às coordenadas que queremos testar*/

   for(j=0;proteina.seq[j]!='\0';j++){
      if(j==i||j==i+1||j==i-1);/*verifica se exite vizinhança nos índices*/
      else{
           if(testa_posicao(i,j)==PASSOU){

             if(proteina.seq[j]=='A' && verifica_ortogonalidade(X,Y,j)==PASSOU && letra=='A')
                   {AA++; grava_indices_ortogonais (i,j);}

             else if(proteina.seq[j]=='A' && verifica_ortogonalidade(X,Y,j)==PASSOU && letra=='B')
                   {AB++; grava_indices_ortogonais (i,j);}

             else if(proteina.seq[j]=='B' && verifica_ortogonalidade(X,Y,j)==PASSOU && letra=='A')
                   {AB++; grava_indices_ortogonais (i,j);}

             else if(proteina.seq[j]=='B' && verifica_ortogonalidade(X,Y,j)==PASSOU && letra=='B')
                   {BB++; grava_indices_ortogonais (i,j);}
           }
      }
   }
   conta_ligacoes[0]+=AA; conta_ligacoes[1]+=BB; conta_ligacoes[2]+=AB;
}


/**
   @param pode receber três valores que posteriormente são atribuídos às variáveis energéticas (e1, e2, e3), ou não receber nada, eventualmente consideramos os valores pré-defenidos para as variáveis energéticas
   @return retorna a energia final de uma proteína
*/
int calcula_imprime_energia (char * args)
{
   int i;
   int energia=0;
   int e1=0,e2=0,e3=0;
   char *c;
   int conta_ligacoes[3]; /*quantidades de ligacoes AA pos 0, BB pos 1, AB pos 2*/

   conta_ligacoes[0]=0; conta_ligacoes[1]=0; conta_ligacoes[2]=0;
   energia=0;
    
   if (args!=NULL) {
       c = &args[0]; e1 = atoi(c);
       c = &args[2]; e2 = atoi(c);
       c = &args[4]; e3 = atoi(c);
   }
   else{
        e1 = -1;
        e2 = e3 = 0;
   }

   for(i=0; i<proteina.tamanho; i++)
      procura_ligacoes_ortogonais(i,conta_ligacoes);

   energia+=(conta_ligacoes[0]*e1);
   energia+=(conta_ligacoes[1]*e2);
   energia+=(conta_ligacoes[2]*e3);

   return printf("%d\n", energia);
}  


/**
   @param pode receber três valores que posteriormente são atribuídos às variáveis energéticas (e1, e2, e3), ou não receber nada, eventualmente consideramos os valores pré-defenidos para as variáveis energéticas
   @return retorna o valor da energia da proteína considerada ou pode circunstancialmente retornar uma mensagem de erro
*/
int cmd_energia (char * args)
{
   if(args==NULL){
     if(ja_existe_sequencia()==NAO_EXISTE) return mensagem_de_erro(E_NO_SEQ);
     else if(proteina.esta_colocada==SIM){
            if(proteina.tem_dobra==NAO) return printf("0\n");      
            else calcula_imprime_energia(args);
     }
     else return mensagem_de_erro(E_NAO_COLOC);
   }
   else if(args!=NULL){
          if(ja_existe_sequencia()==0) return mensagem_de_erro(E_NO_SEQ);
          else if(proteina.esta_colocada==SIM){
                  if(proteina.tem_dobra==NAO) return printf("0\n");
                  else if(numero_de_argumentos(args)!=3) return mensagem_de_erro(E_ARGS);
                  else if(testa_argumentos(args)==NAO_PASSOU) return mensagem_de_erro(E_INVARGS);
                  else calcula_imprime_energia(args);
          }
   }
   return 1;
}


/**
  @param nao recebe argumentos
  @return retorna SIM caso a sequencia esteja bem colocada, retorna NAO caso contrário
*/
int c_validar (char * args){
   if(args==NULL){
     if(ja_existe_sequencia()!=1) return NAO;
     else if(proteina.tem_coordenadas==SIM && testa_coordenadas_sao_validas()==SIM && proteina.tamanho>1){
             proteina.esta_colocada=1;
             return SIM;
          }
     else if(proteina.tem_dobra==1 && testa_coordenadas_sao_validas()==SIM){
            proteina.esta_colocada=1;
            return SIM;
          }
     else return NAO;
   }
   else return NAO;
}


/**
   @param recebe as instruções inválidas
   @return devolve o índice da coordenada sobreposta que torna a configuração inválida
*/
int procura_coordenada_sobreposta(char * dob)
{
   int i=0, jsave=1, j, err=0;

   for(i=0;i!=proteina.tamanho;i++){
      j=jsave;
      for(;j!=proteina.tamanho;j++){
         if(coord[i].x==coord[j].x && coord[i].y==coord[j].y){
           err=j;                  
           j=strlen(dob);
           i=strlen(dob);
          }
      }
      jsave++;
   }
   return err;
}


/**
   @param recebe as instruções de dobragens erradas, o array pedaco onde vamos guardar o pedaço errado, e o indíce da instrução errada
   @return nao devolve nada, apenas guarda o pedaço errado no array pedaco
*/
void guarda_pedaco_a_partir_ind_err(char * dob, char * pedaco, int dob_err)
{
   int j;

   for(j=0; dob[dob_err]!='\0'; dob_err++, j++){
       pedaco[j]=dob[dob_err];
   }
   j++;
   pedaco[j]='\0';
}


/**
  @param recebe as instruções de dobragem e um índice que corresponde à posição da instrução errada
  @return não devolve nada
*/
void repara_um (char * dob, int dob_err)
{
   if(dob[dob_err]=='F') dob[dob_err]='D';
   else if(dob[dob_err]=='D') dob[dob_err]='E';
   else if(dob[dob_err]=='E') dob[dob_err]='F'; 
}  


/**
   @param recebe as instruções de dobragem inváldias
   @return repara a sequencia por vários processos no final retorna as instruções reparadas
*/
int procura_erro_repara (char * dobragens)
{
   int i;
   int ind_coord_errada;
   int dob_err;
   int j=0;
   char pedaco[MAX_SIZE];
   char dob[MAX_SIZE];/*array que vai sofrendo as alterações ate que no final é valido*/

   strcpy(dob, dobragens);
   cmd_dobrar(dob);
   ind_coord_errada=procura_coordenada_sobreposta(dob); /*procura o indice da primeira coordenada sobreposta*/

   dob_err=ind_coord_errada-1;

   while(c_validar(NULL)==NAO){/*O ciclo nao acaba até que a configuração da proteina seja válida*/

        guarda_pedaco_a_partir_ind_err(dob, pedaco, dob_err);

        /*Agora podemos proceder às reparações pela ordem que o enunciado propões F->D->E->F*/
        if(strlen(pedaco)==1) repara_um(dob, dob_err);
        else{
             for(i=dob_err;dob[i]!='\0';i++){
                dob[i]='F'; i++;
                if(dob[i]!='\0'){dob[i]='D'; i++;}
                if(dob[i]!='\0'){dob[i]='E'; i++;}
                if(dob[i]!='\0') dob[i]='F';
             }
        }      

        cmd_dobrar(dob);

        if(c_validar(NULL)==NAO){
          for(j=0, i=dob_err; pedaco[j]!='\0'; j++, i++){
              dob[i]=pedaco[j];
          }
          i++;
          dob[i]='\0';
          dob_err--;
        }
        else{
             strcpy(dobragens, dob);
             return printf("%s\n", dob);
        }
   }
   return 1;
}

  
/**
  @param recebe instruçõe de dobragem inválidas (F, D, E)
  @return repara as instruções de dobragem de modo a obtermos uma configuração válida próxima da introduzida
*/  
int cmd_reparar (char * dobragens)
{
   if(dobragens==NULL) return mensagem_de_erro(E_INVARGS);
   else if(ja_existe_sequencia()!=1) return mensagem_de_erro(E_NAO_COLOC);
   else{
        if(c_validar(NULL)==SIM) return printf("%s\n", dobragens);/*dobragens validas devolve as mesmas*/
         else if(instrucoes_dobragem_validas(dobragens)==1){
                 procura_erro_repara(dobragens);
                 cmd_dobrar(dobragens);
         }
         else return mensagem_de_erro(E_INVARGS);
   }
 return 1;
}
