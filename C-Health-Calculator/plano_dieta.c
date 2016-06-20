int plano_dieta (FILE * ficha, int total)
{
   /*temos de reduzir 500 calorias ao total*/
   
   int pa;
   int mm;
   int al;
   int mt;
   int jan;
   int cal_reduzidas=0;
   
   pa=0.15*total; mm=0.05*total; al=0.35*total; mt=0.15*total; jan=0.30*total;
   if(pa>250){pa=pa-25; cal_reduzidas+=25;}
   if(al>500){al=al-100; cal_reduzidas+=100;}
   if(jan>400){jan=jan-125; cal_reduzidas+=125;}
   
   if(cal_reduzidas==0)
     fprintf(ficha, "\\textbf{Nao cortar na alimentacao} \\\\ \n \\textbf{Exercicios diarios}: Andar acelerado ou Subir Escadas (minimo) \\\\ \n");
   else if(cal_reduzidas==250){
     fprintf(ficha, "\\begin{center} \\Large Nova Distribuicao Calorica \\end{center}\n");
     fprintf(ficha, "\\textbf{Pequeno-almoco}: %dkcal \\\\ \n", pa);
     fprintf(ficha, "\\textbf{Meio da manha}: %dkcal \\\\ \n", mm);
     fprintf(ficha, "\\textbf{Almoco}: %dkcal \\\\ \n", al);
     fprintf(ficha, "\\textbf{Meio da Tarde}: %dkcal \\\\ \n", mt);
     fprintf(ficha, "\\textbf{Jantar}: %dkcal \\\\ \n", jan);
	 fprintf(ficha, "\\textbf{Exercicios diarios}: Andar acelerado (minimo) \\\\ \\\\ \n");
   }
}