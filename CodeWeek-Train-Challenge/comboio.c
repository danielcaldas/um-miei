#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<ctype.h>

int dicionario_esgotado (char linha[96044][25]);
void fazer_comboio (char linha[96044][25], int* comboio_indices, int *p_usadas, int *valor_comboio);
int maior_sufixo(char* anterior, char linha[96044][25], int* ind);
void verifica_sufixo(int j, int* k, int ipa, int comp_anterior,int comp_atual, char* anterior,char* palavra_atual);
int comprimento_sufixo (char* palavra_atual, int j, char* anterior, int ipa);
int maior_palavra (char* p1, char* p2);
void escrever_comboio_no_ficheiro (char dicio[96044][25], int* comboio_indices, int total);

char dicio[96044][25];

int main(){
   FILE *f;
   char linha[96044][25];
   int comboio_indices[96044];
   int valor_comboio;
   int i;
   char *c;
   int palavras_do_dicionario_usadas=0;

   f=fopen("dicio.txt","r");
   for(i=0;i<96045;i++){
       c=fgets(linha[i], 25, f);
       if(c==c)
          linha[i][strlen(linha[i])-1]='\0';
   }
   fclose(f);
   for(i=0;i<96045;i++){
       strcpy(dicio[i], linha[i]);
   }
   fazer_comboio(linha,comboio_indices, &palavras_do_dicionario_usadas, &valor_comboio);
   escrever_comboio_no_ficheiro(dicio,comboio_indices, palavras_do_dicionario_usadas);
   printf("valor do comboio: %d\n", valor_comboio);
   return 0;
}


void fazer_comboio (char linha[96044][25], int* comboio_indices, int *p_usadas, int *valor_comboio){
   int i, ind, letras_encaixam;
   char palavra_atual[25];

   printf("(aguardar cerca de 40 seg)\n...\n");
   *valor_comboio=0;

   strcpy(palavra_atual, linha[96044]);  
 
   comboio_indices[0]=96043;/*otorrinolaringologista*/
   linha[96043][0]='\0';

   comboio_indices[1]=96044;/*otorrinolaringologistas*/
   linha[96044][0]='\0';

   *valor_comboio=484;
   /*duas palavras de maior comprimento formam o topo do comboio*/

   for(i=2; dicionario_esgotado(linha)==0; i++, letras_encaixam=0){
       letras_encaixam=maior_sufixo(palavra_atual, linha, &ind); /*retorna indice da seguinte palavra no comboio*/
       if(letras_encaixam==0) break; /*impossivel dar continuidade ao comboio*/
       *valor_comboio+=(letras_encaixam*letras_encaixam);
       comboio_indices[i]=ind;
       strcpy(palavra_atual, linha[ind]);
       linha[ind][0]='\0';/*palavra indisponivel no dicio*/
   }
   *p_usadas=i;
}


int dicionario_esgotado (char linha[96044][25]){
   int i;

   for(i=0;i<96045;i++){
       if(linha[i][0]!='\0')
          return 0;
   }
   return 1;
}


int maior_sufixo(char* anterior, char linha[96044][25], int* ind){
   int j, k;
   int p, ipa;
   int maior_sufixo=0;
   int comp_anterior=strlen(anterior);
   int comp_atual;

   j=0;k=0;p=0;ipa=0; *ind=0;

   for(p=96042; p>=0; p--){/*ciclo controla palavra atual a comparar*/
       comp_atual=strlen(linha[p]);
       verifica_sufixo(j,&k,ipa,comp_anterior,comp_atual, anterior, linha[p]); /*k é o numero de letras que encaixam*/
       if(k>maior_sufixo){
          maior_sufixo=k;
          *ind=p;
       }
   }
   return maior_sufixo;
}

void verifica_sufixo(int j, int* k, int ipa, int comp_anterior,int comp_atual, char* anterior,char* palavra_atual){
   int flag=0;

   for(j=0, *k=0, ipa=0; ipa<comp_anterior; ipa++){
       if(comp_anterior<=comp_atual){
          if(palavra_atual[j]==anterior[ipa]){
             *k=comprimento_sufixo(palavra_atual, j, anterior, ipa);
             if(*k!=0) break;
          }
       }
       else{
            if(flag==0){
               ipa=(comp_anterior-comp_atual);
                flag++;
            }
            if(palavra_atual[j]==anterior[ipa]){
               *k=comprimento_sufixo(palavra_atual, j, anterior, ipa);
               if(*k!=0) break;
            }
       }
   }
}

int comprimento_sufixo (char* palavra_atual, int j, char* anterior, int ipa){
   int suf=0;

   for(;palavra_atual[j]!='\0'&&anterior[ipa]!='\0'; j++, ipa++){
       if(palavra_atual[j]==anterior[ipa]) suf++;
        else{suf=0; break;}
   }
   if(suf==0) return 0;
    else return suf;
}


int maior_palavra (char* p1, char* p2){
   int a, b;

   a=strlen(p1); b=strlen(p2);
   if(a>=b)
      return a;
   else return b;
}


void escrever_comboio_no_ficheiro (char dicio[96044][25], int* comboio_indices, int total){
   FILE *train;
   int i;

   train = fopen("train.txt", "wt");
   for(i=0;i<total;i++){
       fprintf(train, "%s\n", dicio[comboio_indices[i]]);
   }
   fclose(train);
}
