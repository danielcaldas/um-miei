#include<stdio.h>
#include<stdlib.h>

void fatorial(int x, int N){
   double r=1;

   for(N=x; N>=1; N--)
      r=r*N;
   printf("%lf\n", r);
}
