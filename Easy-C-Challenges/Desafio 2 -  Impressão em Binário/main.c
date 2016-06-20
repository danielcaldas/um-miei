#include<stdio.h>
#include<stdlib.h>
#include"binario.h"

/*Restrições do enunciado:
  -> as representações binárias são de 16 bits;
  -> n é um inteiro sem sinal e pode variar entre 0 e 40.000 inclusive;
  -> Xi(1<=i<=N) é um inteiro sem sinal e pode variar de -32768 até 32767 (inclusive);
*/

int main(){
   int *l;
   int n;
   int i;
   int j=0;

   i=scanf("%d", &n);
   l = (int*) malloc (sizeof(n));
   for(i=0; i<n; i++)
       j=scanf("%d", &l[i]);
   for(i=0;i<n;i++)
       binario(l[i], j);

   return 0;
}
