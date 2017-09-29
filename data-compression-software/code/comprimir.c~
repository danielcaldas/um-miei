#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "comprimir.h"

int comprimir ()
{
   FILE * ficheiro_origem;
   char nome_ficheiro[15];
   size_t resultado;
   unsigned long int tamanho, n_bits;
   int n_bytes_diferentes=0;

   flag_estatistica=0;

   printf(" >Nome do ficheiro que deseja comprimir: ");
   n_bits=scanf("%s", nome_ficheiro);
   n_bits=system("clear");
   ficheiro_origem=fopen (nome_ficheiro , "rb");
   if (ficheiro_origem==NULL) {
       fputs (" >Erro Ficheiro\n",stderr); 
       return 0;
   }
   fseek(ficheiro_origem, 0, SEEK_END);
   tamanho=ftell(ficheiro_origem);
   rewind(ficheiro_origem);
   buffer_ficheiro=(char*) malloc(tamanho*sizeof(char));
   resultado=fread (buffer_ficheiro, sizeof(char), tamanho, ficheiro_origem);
   if(resultado!=tamanho){
      printf(" >Erro de Leitura\n");
      return 0;
   }
   fclose (ficheiro_origem);

   printf("\n *RELATÓRIO*\n >A realizar estatística do ficheiro...\n");
   n_bytes_diferentes=fazer_estatistica(tamanho);

   printf(" (Número de bytes diferentes no ficheiro: %d)\n", n_bytes_diferentes);

   n_bits=tamanho*2;//numero de bytes necessarios para codificar o ficheiro (atribuir valor por excesso 2x o tamanho do fich original)
   bin_comprimido=(char*) malloc(n_bits*sizeof(char));

   my_bubbleSort(n_bytes_diferentes);  

   calcula_probabilidade(n_bytes_diferentes, tamanho);

   n_bits=0;
   shannon_fano(n_bits, n_bytes_diferentes-1);

   printf(" >A criar e codificar buffer com ficheiro comprimido...\n");
   n_bits=criar_buffer_ficheiro_comprimido(tamanho, n_bytes_diferentes);

   free(buffer_ficheiro);

   escrever_ficheiro_comprimido(tamanho, n_bits, n_bytes_diferentes);

   free(bin_comprimido);
   free(blocos);
   return 0;
}


int fazer_estatistica (unsigned long int tamanho)
{
   unsigned long int aux, i, j;
   int n_bytes_diferentes=0;
   int ii;
   char *buffer_intermedio;

   for(aux=0, i=0, j=0; aux<tamanho; aux+=10000){
       buffer_intermedio=(char*) malloc(10000*sizeof(char));
       j=aux+10000;
       for(i=aux, ii=0; i<j&&i<tamanho; i++, ii++){
           buffer_intermedio[ii]=buffer_ficheiro[i];
       }
       ii=estatistica_ficheiro(buffer_intermedio, n_bytes_diferentes, ii);
       n_bytes_diferentes+=ii;
       free(buffer_intermedio);
   }
   return n_bytes_diferentes;
}


int estatistica_ficheiro (char * buffer, int bytes_difs, int bytes_lidos)
{
   int i;
   long int r;
   int returned_bytes_difs;
   unsigned long int size;
   Bloco* p;
   Bloco* t;

   returned_bytes_difs=bytes_difs;


   for(i=0, size=0; i<bytes_lidos; i++){
       if(flag_estatistica==0){//primeiro byte do ficheiro nunca existe no array de bytes
          blocos = (Bloco*) malloc(sizeof(struct simbolo));
          bytes_difs=1;
          blocos[0].byte=buffer[0];
          blocos[0].ocorrencias=1;
          flag_estatistica=1;
       }
       else{
            r=consulta_blocos(buffer[i], bytes_difs);
            if(r==-1){//criar uma estrutura para um novo byte que nao existe no array blocos
               bytes_difs++;
               size=bytes_difs+1;
               t=blocos;
               p = (Bloco*) realloc(t, size*sizeof(struct simbolo));
               if(p!=NULL) blocos=p;
               blocos[bytes_difs].byte=buffer[i];
               blocos[bytes_difs].ocorrencias=1;
            }               
            else blocos[r].ocorrencias++;  
       }
   }
   returned_bytes_difs = bytes_difs - returned_bytes_difs;

   return returned_bytes_difs;
}


int consulta_blocos (char c, int bytes_difs){
   int i;

   for(i=0;i<bytes_difs; i++){
       if(c==blocos[i].byte) return i;
   }
   return -1;
}

/*----------------------------------------------------------------------------------------------------------------------*/
void my_swap(unsigned long int a, unsigned long int b)
{
   Bloco *tmp;

   tmp = (Bloco*) malloc(sizeof(struct simbolo));

   //tmp = blocos[a]
   tmp->byte=blocos[a].byte; tmp->ocorrencias=blocos[a].ocorrencias; tmp->codigo=blocos[a].codigo; tmp->bit=blocos[a].bit;
   //blocos[a] = blocos[b]
   blocos[a].byte=blocos[b].byte; blocos[a].ocorrencias=blocos[b].ocorrencias; blocos[a].codigo=blocos[b].codigo; blocos[a].bit=blocos[b].bit;
   //blocos[b] = tmp
   blocos[b].byte=tmp->byte; blocos[b].ocorrencias=tmp->ocorrencias; blocos[b].codigo=tmp->codigo; blocos[b].bit=tmp->bit;
    
   free(tmp);
}
 
void my_bubbleSort (int N)
{
   int i, j;

   for (i=N-1; (i>0); i--)
        for (j=0; (j<i); j++)
             if (blocos[j].ocorrencias < blocos[j+1].ocorrencias)
                 my_swap (j,j+1);
}
/*----------------------------------------------------------------------------------------------------------------------------------*/

void calcula_probabilidade (int n, unsigned long int t)
{
   int i;

   for(i=0; i<n; i++){
       blocos[i].probabilidade=(blocos[i].ocorrencias/t);
       blocos[i].bit=-1;
       blocos[i].codigo = (int*) malloc(25*sizeof(int));
   }
}

/*-----------------------------------------------Codificação Shannon-Fano--------------------------------------------------------*/

void shannon_fano(unsigned long int l, int h)
{
   float pack1=0,pack2=0,diff1=0,diff2=0;
   int i,d,k,j;

   if((l+1)==h || l==h || l>h){
      if(l==h || l>h) return;
      blocos[h].codigo[++(blocos[h].bit)]=1;
      blocos[l].codigo[++(blocos[l].bit)]=0;
      return;
   }
   else{
        for(i=l;i<=h-1;i++)
            pack1=pack1+blocos[i].probabilidade;
        pack2=pack2+blocos[h].probabilidade;
        diff1=pack1-pack2;
        if(diff1<0)
           diff1=diff1*-1;
        j=2;
        while(j!=h-l+1){
              k=h-j;
              pack1=pack2=0;
              for(i=l;i<=k;i++)
                  pack1=pack1+blocos[i].probabilidade;
              for(i=h;i>k;i--)
                  pack2=pack2+blocos[i].probabilidade;
              diff2=pack1-pack2;
              if(diff2< 0)
                 diff2=diff2*-1;
              if(diff2>=diff1)
                 break;
              diff1=diff2;
              j++;
        }
        k++;
        for(i=l;i<=k;i++)
            blocos[i].codigo[++(blocos[i].bit)]=0;
        for(i=k+1;i<=h;i++)
            blocos[i].codigo[++(blocos[i].bit)]=1;
        shannon_fano(l,k);
        shannon_fano(k+1,h);
   }
}
/*-------------------------------------------------------------------------------------------------------------------------------*/

unsigned long int criar_buffer_ficheiro_comprimido (unsigned long int n_fich, int n)
{
   unsigned long int i, indice_bin;
   int j, k, l, ll, valor_inteiro_byte;
   int * byte;
   int i_byte=0;
   char byte_para_escrever;
   unsigned long int ind_bin_comp=0;

   int repetidos;


   for(i=0, indice_bin=0; i<n_fich; i++){//(1)
       for(j=0;j<n;j++){//(2)
           if(buffer_ficheiro[i]==blocos[j].byte){
                   
                
                for(k=0; k<=blocos[j].bit; k++, indice_bin++){//(3)
                    if(i_byte==0) byte=(int*) malloc(8*sizeof(int));
                    byte[i_byte]=blocos[j].codigo[k];
                    i_byte++;


                    if(i_byte==8 || (i_byte>0&&(i+1)==n_fich&&k==blocos[j].bit)){//temos um byte vamos despeja-lo no buffer
                       if(i_byte<8&&(i+1)==n_fich&&k==blocos[j].bit){
                          for(;i_byte<8;i_byte++){
                              byte[i_byte]=0;
                          }
                       }
                       valor_inteiro_byte=bin_to_int(byte);
                       byte_para_escrever=valor_inteiro_byte;
                       bin_comprimido[ind_bin_comp]=byte_para_escrever;
                       ind_bin_comp++;
                       i_byte=0;
                       free(byte);
                    }
                }
                j=n;//obriga a abandonar (2) e voltar a (1)
           }
       }
   }
   ind_bin_comp--;

   bin_comprimido=(char*) realloc(bin_comprimido, (ind_bin_comp)*(sizeof(char))); 
   aux_comp=ind_bin_comp;

   return indice_bin;
}


void escrever_ficheiro_comprimido (unsigned long int tam, unsigned long int n_bits, int bytes_difs)
{
   FILE * ficheiro_comprimido;
   unsigned long int i=0;
   int j, k;
   char *buffer;
   unsigned long int ind_buffer;
   char n_bits_char[4];


   for(j=0;j<bytes_difs;j++){
       i+=2;//byte da struct mais o '\0' carater
       i+=blocos[j].bit+1;//n bits da codificação shannon_fano +1 <=...
   }
   i+=4+(n_bits/8);

   buffer = (char*) malloc(i*sizeof(char));

   ind_buffer=0;


   //colocar "tamanho em bits" do bloco com codificação do ficheiro
   int_to_bin_32bits(n_bits,n_bits_char);
   for(j=0; j<4; j++, ind_buffer++){
       buffer[ind_buffer]=n_bits_char[j];
   }

   for(i=0; ind_buffer<aux_comp+4; ind_buffer++, i++){
       buffer[ind_buffer]=bin_comprimido[i];
   }

   printf(" >A copiar tabela para buffer...\n");
   //colocar byte seguido de respetiva codificação no buffer (A tabela estatistica para descompressão)
   for(j=0; j<bytes_difs; j++){
       buffer[ind_buffer]=blocos[j].byte;
       ind_buffer++;
       for(k=0;k<=blocos[j].bit; k++, ind_buffer++){ //<= crucial para apanhar todo o codigo shannon-fano
           if(blocos[j].codigo[k]==1)
              buffer[ind_buffer]='1';
            else buffer[ind_buffer]='0';
       }
       buffer[ind_buffer]='\0'; ind_buffer++;
   }


   printf(" >A escrever buffer no ficheiro de destino...\n");
   ficheiro_comprimido = fopen ("compress.bin" , "wb");
   fwrite(buffer, sizeof(char), ind_buffer, ficheiro_comprimido);
   fclose(ficheiro_comprimido);
   free(buffer);

   printf("\n#####################FICHEIRO COMPRIMIDO COM SUCESSO!####################\n");
   printf(" Tamanho do ficheiro comprimido(em bytes): %lu\n", ind_buffer);
   printf(" Tamanho do ficheiro original(em bytes):   %lu\n\n\n", tam);
}


int bin_to_int (int * byte)
{
   int i, j, k, b;
   int flag_negativo=0;
   int valor_decimal=0;

   if(byte[0]==1){
      flag_negativo=1;
      for(i=7;i>=0;i--){
          if(byte[i]==0) byte[i]=1;
           else{
                (byte[i]=0);
                break;
           }
      }
      for(i=7;i>=0;i--){
          if(byte[i]==0) byte[i]=1;
           else byte[i]=0;
      }
   }
   for(i=7, j=0;i>=0; i--, j++){
       if(byte[i]==1)
          if(j==0) valor_decimal=1;
           else{
                for(k=0, b=1;k<j;k++) b=b*2;
                valor_decimal+=b;
           }
   }

   if(flag_negativo==1) valor_decimal=valor_decimal*-1;  

   return valor_decimal;
}


void int_to_bin_32bits (unsigned long int l, char * v)
{
   int rep_binaria[32];
   int c1[8];
   int c2[8];
   int c3[8];
   int c4[8];
   int i, j;
   char a, b, c, d;
   unsigned long int x;

   x=l;
   if(x<0) x=-x;
   for(i=31;x>=1;x=(x/2), i--){
       if((x%2)!=0) rep_binaria[i]=1;
        else rep_binaria[i]=0;
   }
   if(i>=0)
      for(;i>=0;i--)
          rep_binaria[i]=0;
   if(l<0){    
      for(i=31;i>=0;i--){
          if(rep_binaria[i]==0) rep_binaria[i]=1;
           else rep_binaria[i]=0;
      }
      for(i=31;i>=0;i--){
          if(rep_binaria[i]==0){
             rep_binaria[i]=1;
             break;
           }
           else rep_binaria[i]=0;
      }
   }
   for(j=0, i=0; i<8; i++, j++)
       c1[i]=rep_binaria[i];
   for(j=0; i<16; i++, j++)
       c2[j]=rep_binaria[i];
   for(j=0; i<24; i++, j++)
       c3[j]=rep_binaria[i];
   for(j=0;i<32; i++, j++)
       c4[j]=rep_binaria[i];

   a=bin_to_int(c1);
   b=bin_to_int(c2);
   c=bin_to_int(c3);
   d=bin_to_int(c4);
   v[0]=a; v[1]=b; v[2]=c; v[3]=d;
}





/*-------------------------------------DESCOMPRIMIR--------------------------------------------------------------------------*/





int descomprimir (){
   FILE * ficheiro_comprimido;
   FILE * ficheiro_original;
   char nome_ficheiro[20];
   unsigned long int resultado;
   unsigned long int tamanho;
   unsigned long int i;
   unsigned long int tamanho_conteudo_ficheiro_bits;
   unsigned long int tamanho_conteudo_ficheiro_bytes;
   unsigned long int n_bytes_diferentes;
   unsigned long int ind_binario;
   int codigo_comprimento_minimo=100;//valor atribuído por excesso
   char nome_ficheiro_destino[20];


   printf(" >Nome do ficheiro que deseja descomprimir: ");
   resultado=scanf("%s", nome_ficheiro);
   printf(" >Nome do ficheiro após descompressão (atenção à extenção do ficheiro): ");
   resultado=scanf("%s", nome_ficheiro_destino);
   i=system("clear");
   ficheiro_comprimido=fopen (nome_ficheiro , "rb");
   if(ficheiro_comprimido==NULL){
      printf(" >Erro Ficheiro\n");
      return 0;
   }
   fseek(ficheiro_comprimido, 0, SEEK_END);
   tamanho = ftell(ficheiro_comprimido);
   rewind(ficheiro_comprimido);//voltar ao início do ficheiro após termos feito fseek
   buffer_ficheiro = (char*) malloc(tamanho*sizeof(char));
   resultado = fread (buffer_ficheiro, sizeof(char), tamanho, ficheiro_comprimido); // ler ficheiro para buffer
   if(resultado!=tamanho){
      printf(" >Erro de Leitura\n");
      return 0;
   }
   fclose(ficheiro_comprimido);

   tamanho_conteudo_ficheiro_bits=extrair_tamanho_conteudo_ficheiro();
 
   tamanho_conteudo_ficheiro_bytes=(tamanho_conteudo_ficheiro_bits/8);

   n_bytes_diferentes=colocar_tabela_em_struct(&codigo_comprimento_minimo, tamanho_conteudo_ficheiro_bytes, tamanho);

   //alocar memória para buffer que contem conteudo codificado do ficheiro em que cada bit é um inteiro
   buffer_codificado = (int*) malloc(tamanho_conteudo_ficheiro_bits*sizeof(int));

   i=tamanho_conteudo_ficheiro_bytes*2;
   buffer_ficheiro_original=(char*) malloc(i*sizeof(char));

   //eliminar tabela do buffer
   buffer_ficheiro=(char*) realloc (buffer_ficheiro, tamanho_conteudo_ficheiro_bytes*sizeof(char));

   descodificar_ficheiro_para_buffer(n_bytes_diferentes,codigo_comprimento_minimo,tamanho_conteudo_ficheiro_bytes,tamanho_conteudo_ficheiro_bits, nome_ficheiro_destino);

   free(xblocos);
   free(buffer_ficheiro);

   return 0;
}


void descodificar_ficheiro_para_buffer (unsigned long int n_bytes_diferentes, int codigo_comprimento_minimo, unsigned long int tamanho_conteudo_ficheiro_bytes, unsigned long int tamanho_conteudo_ficheiro_bits, char * nome_ficheiro_destino){

   FILE * ficheiro_original;
   int *byte;
   int k;
   unsigned long int i, j;
   int *codificacao;
   int flag_encontrado=-1;
   int jj;  


   //construir buffer de inteiros com conteudo (codificado) do ficheiro binario em bits (cada bit é um inteiro)
   for(i=4, j=0; i<tamanho_conteudo_ficheiro_bytes+4; i++){
       k=buffer_ficheiro[i];
       byte = (int*) malloc(8*sizeof(int));
       int_to_bin_8bits(k, byte);
       for(k=0; k<8; k++, j++){
           buffer_codificado[j]=byte[k];
       }
       free(byte);
   }

   printf(" >A descodificar ficheiro... \n");
   //j --> indice para percorrer buffer_codificado   

   for(i=0, j=0; j<tamanho_conteudo_ficheiro_bits; i++){
       codificacao = (int*) malloc(20*sizeof(int));//20 bits para codificação Shannon-Fano por excesso
       for(k=0; k<codigo_comprimento_minimo && j<tamanho_conteudo_ficheiro_bits; k++, j++){
           codificacao[k]=buffer_codificado[j];
       }
       //verificar se existe este código na tabela
       while(flag_encontrado==-1){//ira conter o indice na tabela do codigo que procuravamos
             flag_encontrado=procura_na_tabela(codificacao, n_bytes_diferentes, k);
             if(flag_encontrado==-1){//caso nao encontremos a codificacao acrescentamos-lhe mais um bit
                if(j<tamanho_conteudo_ficheiro_bits){
                   codificacao[k]=buffer_codificado[j];
                   k++; j++;
                }
             }
       }

       buffer_ficheiro_original[i]=xblocos[flag_encontrado].byte; //->escrever o byte a que corresponde a codificação no buffer
       flag_encontrado=-1;
       free(codificacao);
   }

   printf(" >A escrever no ficheiro de destino ... \n");          
   ficheiro_original = fopen (nome_ficheiro_destino , "wb");
   fwrite(buffer_ficheiro_original, sizeof(char), i, ficheiro_original);
   fclose(ficheiro_original);

   printf(" >Terminado.\n\n"); 

   free(buffer_ficheiro_original);
   free(buffer_codificado);
}


unsigned long int extrair_tamanho_conteudo_ficheiro()
{
   unsigned long int i;
   int j, k;
   int byte_aux[8];
   char conteudo_ficheiro[4];
   int conteudo_ficheiro_bin[32];

   //extrair tamanho do conteudo do ficheiro expresso em bits armazenado em 4 bytes (32 bits unsigned long int)
   for(i=0; i<4; i++, j++){
       conteudo_ficheiro[j]=buffer_ficheiro[i];
   }

   //passar de uma string para um unsigned long int o tamanho do conteudo do ficheiro
   for(j=0, i=0; i<4; i++){
       k=conteudo_ficheiro[i];
       int_to_bin_8bits(k, byte_aux);
       for(k=0;k<8;k++, j++)
           conteudo_ficheiro_bin[j]=byte_aux[k];
   }   
   i=bin_to_int_32bits(conteudo_ficheiro_bin); //transformar a string de 4 carateres no unsigned long int

   return i;
}

int colocar_tabela_em_struct (int * codigo_comprimento_minimo, unsigned long int tamanho_conteudo_ficheiro_bytes, unsigned long int tamanho)
{
   unsigned long int i;
   int j, k;
   int n_bytes_diferentes;
   

   //contar o número de bytes da tabela para alocar memória
   for(j=0, i=tamanho_conteudo_ficheiro_bytes+4 ; i<tamanho; i++)
       if(buffer_ficheiro[i]=='\0') j++;
   n_bytes_diferentes=j-1;

   xblocos=(xBloco*) malloc(n_bytes_diferentes*(sizeof(struct xsimbolo)));

   //ler para xblocos bytes e respetivas codificações
   for(i=tamanho_conteudo_ficheiro_bytes+4, j=0; i<tamanho; j++){
       xblocos[j].byte=buffer_ficheiro[i]; i++;
       xblocos[j].codigo = (int*) malloc(20*sizeof(int));
       for(k=0; buffer_ficheiro[i]!='\0'; i++, k++){
           if(buffer_ficheiro[i]=='0')
              xblocos[j].codigo[k]=0;
            else xblocos[j].codigo[k]=1;
       }
       xblocos[j].bit=k; //para consultar codigo <xblocos.bit e não <=xblocos.bit
       if(k<*codigo_comprimento_minimo)
          *codigo_comprimento_minimo=k;
       i++;
   }
   return n_bytes_diferentes;
}

int procura_na_tabela (int * codificacao, unsigned long int n, int tamanho_codificacao){
   int i, k;
   int flag=0;

   for(i=0; i<n; i++, flag=0){
       for(k=0; k<xblocos[i].bit; k++){
           if(codificacao[k]==xblocos[i].codigo[k]){
              flag++;
           }
           if(flag==tamanho_codificacao&&flag==xblocos[i].bit){
              return i;
           }
       }
   }
   return -1;
}
   
unsigned long int bin_to_int_32bits (int * byte){
   int i, j, k, b;
   unsigned long int valor_decimal=0;

   for(i=31, j=0;i>=0; i--, j++){
       if(byte[i]==1)
          if(j==0) valor_decimal=1;
           else{
                for(k=0, b=1;k<j;k++) b=b*2;
                valor_decimal+=b;
           }
   } 

   return valor_decimal;
}

void int_to_bin_8bits (int l, int * rep_binaria){
   int i, x;

   x=l;
   if(x<0) x=-x;
   for(i=7;x>=1;x=(x/2), i--){
       if((x%2)!=0) rep_binaria[i]=1;
        else rep_binaria[i]=0;
   }
   if(i>=0)
      for(;i>=0;i--)
          rep_binaria[i]=0;
   if(l<0){    
      for(i=7;i>=0;i--){
          if(rep_binaria[i]==0) rep_binaria[i]=1;
           else rep_binaria[i]=0;
      }
      for(i=7;i>=0;i--){
          if(rep_binaria[i]==0){
             rep_binaria[i]=1;
             break;
           }
           else rep_binaria[i]=0;
      }
   }
}
