#include<stdio.h>
#include<stdlib.h>

void binario (int l, int j){
   int rep_binaria[16];
   int i, x;

   x=l;
   if(x<0) x=-x;
   for(i=15;x>=1;x=(x/2), i--){
       if((x%2)!=0) rep_binaria[i]=1;
        else rep_binaria[i]=0;
   }
   if(i>=0)
      for(;i>=0;i--)
          rep_binaria[i]=0;
   if(l<0){    
      for(i=15;i>=0;i--){
          if(rep_binaria[i]==0) rep_binaria[i]=1;
           else rep_binaria[i]=0;
      }
      for(i=15;i>=0;i--){
          if(rep_binaria[i]==0){
             rep_binaria[i]=1;
             break;
           }
           else rep_binaria[i]=0;
      }
   }
   for(i=0; i<16; i++)
       printf("%d", rep_binaria[i]);
   putchar('\n');
}
