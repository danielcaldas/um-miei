#include<stdio.h>
#include<stdlib.h>
#include"fatorial.h"

/*compilado com -O3 pois -W -Wall -ansi -pedantic não aceita o formato %lf que usei para doubles*/
/*os valores de input variam entre 0 e 20*/

int main() {
   double l[1000];
   int n;
   int i, j;

   
   j=scanf("%d", &n);
   for(i=0;i<n;i++)
       j=scanf("%lf", &l[i]);
   for(i=0;i<n;i++)
       fatorial(l[i], j);

   return 0;
}
      
