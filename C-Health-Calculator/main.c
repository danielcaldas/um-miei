#include<stdio.h>
#include<stdlib.h>
#include<math.h>
#include<float.h>

#include"imc_calc.c"
#include"img_calc.c"
#include"gasto_calorico.c"
#include"plano_dieta.c"

int main ()
{
   FILE *ficha;
   char nome[5000];
   int idade;
   int peso;
   float altura;
   char sexo;
   float imc; /*índice de massa corporal*/
   int img; /*percentagem de gordura corporal*/
   int fatorS;
 
   ficha = fopen ("ficha pessoal.txt", "w");
   
   fprintf(ficha, "\\documentclass[a4paper]{article}\n");
   fprintf(ficha, "\\usepackage[portuguese]{babel}\n");
   fprintf(ficha, "\\usepackage[utf8]{inputenc}\n");
   fprintf(ficha, "\\title{Contas  Saudaveis}\n");
   fprintf(ficha, "\\begin{document}\n\\maketitle\n\\thispagestyle{empty}\n\\begin{flushright}\n");
   fprintf(ficha, "\\scriptsize \\textbf{Aplicacao desenvolvida em C por \\textit{Daniel Caldas}}\n\\end{flushright}\n");
 
   putchar('\n');
   printf("##############################REGISTO##########################################\n");
 
   printf("Nome: ");
   fgets(nome, 5000, stdin);
   fprintf(ficha, "\\textbf{Nome}: %s \\\\ \n", nome);
 
   printf("Idade: ");
   scanf("%d", &idade);
   fprintf(ficha, "\\textbf{Idade}: %d \\\\ \n", idade);
 
   printf("Sexo (M ou F): ");
   scanf("%c", &sexo);
   scanf("%c", &sexo);
   if(sexo=='M')
     fprintf(ficha, "\\textbf{Sexo}: Masculino \\\\ \n");
   else fprintf(ficha, "\\textbf{Sexo}: Feminino \\\\ \n");
 
   printf("Peso: ");
   scanf("%d", &peso);
   fprintf(ficha, "\\textbf{Peso}: %dkg \\\\ \n", peso);
 
   printf("Altura: ");
   scanf("%f", &altura);
   fprintf(ficha, "\\textbf{Altura}: %.2fm \\\\ \\\\ \n", altura);
 
   imc=peso/(altura*altura);
   fprintf(ficha, "\\textbf{Indice de massa corporal (IMC)}: %.2f \\\\ \n", imc);
   imprime_classificacao(ficha, imc, sexo, idade);
   
 
   if(idade<18||idade>65) fprintf(ficha, "\\textbf{Taxa de Gordura Corporal (IMG)}: IMPOSSIVEL CALCULAR POR EXCESSO OU DEFEITO DE IDADE \\\\ \n");
   else{
        if(sexo=='M') fatorS=1;
        else fatorS=0;
        img= (1.2*imc) - (10.8*fatorS)+(0.23*idade)-5.4;
        fprintf(ficha, "\\textbf{Taxa de Gordura Corporal (IMG)}: %d\\%% \\\\ \n", img);
	    imprime_classificacao_img(ficha, img, sexo, idade);
   }
   
   gasto_calorico(ficha, sexo, peso, idade);      
    
   fprintf(ficha, "\\end{document}");
	
   fclose(ficha);
   return 0;
}