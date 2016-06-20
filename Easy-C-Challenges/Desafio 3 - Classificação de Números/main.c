#include<stdio.h>
#include<stdlib.h>
#include"funcoes.h"

int main () {
   int v[1000];
   int i, n, save;
   int pares=0;
   int impares=0;
   int primos=0;
   int perfeitos=0;

   n=scanf("%d", &save);
   for(i=0; save!=0; i++){
       if(i==0) v[i]=save;
        else n=scanf("%d", &v[i]);
       save=v[i];
   }
   n=i-1;
   for(i=0;i<n;i++){
      if(par(v[i])==1) pares++;
       else impares++;
      if(v[i]==1||v[i]==2||primo(v[i])==1) primos++;
      if(perfeito(v[i])==1) perfeitos++;
   }
   imprimir(pares, impares, primos, perfeitos);
   return 0;
}
