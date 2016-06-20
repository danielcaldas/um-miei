#include<stdio.h>
#include<stdlib.h>

int par (int x){
   if((x%2)==0) return 1;
    else return 0;
}

int primo (int x){
   int i;

   for(i=x-1; i>1; i--){
      if((x%i)!=0);
       else return 0;
   }
   return 1;
}

int perfeito (int x){
   int sum=0;
   int x_save=x;

   if(x==1||x==2) return 0;
   while(x>1){
        if((x%2)==0){x=x/2; sum+=x;}
         else if(x==x_save) return 0;
          else{x--; sum+=x;}
   }
   if(sum==x_save)
     return 1;
    else return 0;
}

void imprimir (int pares, int impares, int primos, int perfeitos){
   printf("Pares: %d\n", pares);
   printf("Impares: %d\n", impares);
   printf("Primos: %d\n", primos);
   printf("Perfeitos: %d\n", perfeitos);
   putchar('\n');
}
