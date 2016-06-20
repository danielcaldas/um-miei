void gasto_calorico_atividades(FILE * ficha, int gcb, int peso);
void distribuicao_calorica (FILE * ficha, int total, int peso);
void previsao_plano(FILE * ficha, int peso);

void gasto_calorico (FILE * ficha, char sexo, int peso, int idade)
{
   int gcb; /*gasto calorico basal*/
   
   if(sexo=='M'){
     if(idade>=18 && idade<=30)
	    gcb=(0.063*peso+2.896)*239;
	 else if(idade>=30)
	    gcb=(0.048*peso+3.653)*239;
     else fprintf(ficha, "IMPOSSIVEL CALCULAR DEVIDO A IDADE \\\\ \n");
   }
   else{
     if(idade>=18 && idade<=30)
	    gcb=(0.062*peso+2.036)*239;
	 else if(idade>=30)
	    gcb=(0.034*peso+3.538)*239;
     else fprintf(ficha, "IMPOSSIVEL CALCULAR DEVIDO A IDADE \\\\ \n");
   }
   fprintf(ficha, "\\textbf{Gasto Calorico Basal}: %dkcal \\\\ \n", gcb);
   gasto_calorico_atividades(ficha, gcb, peso);
}

void gasto_calorico_atividades(FILE * ficha, int gcb, int peso)
{
   int i=0; /*numero de atividades*/
   int j=1;
   int calorias[1000];
   int atividades[5];
   int total;
   int sum=0;
   
   calorias[0]=70; calorias[1]=75; calorias[2]=35; calorias[3]=126;
   calorias[4]=55; calorias[5]=70; calorias[6]=310; calorias[7]=330;
   calorias[8]=200; calorias[9]=330; calorias[10]=105; calorias[11]=290;
   calorias[12]=255; calorias[13]=175; calorias[14]=280;
   

   printf("Selecione uma atividade: \n");
   printf("1-Tocar Piano => 70 calorias\n");
   printf("2-Tocar Guitarra => 75 calorias\n");
   printf("3-Apanhar Sol => 35 calorias\n");
   printf("4-Andar de bicicleta =>126 calorias\n");
   printf("5-Cantar => 55 calorias\n");
   printf("6-Compras no supermercado =>70 calorias\n");
   printf("7-Correr em terreno plano => 310 calorias\n");
   printf("8-Correr em terreno irregular =>330 calorias\n");
   printf("9-Dancar =>200 calorias\n");
   printf("10-Jogar Futebol =>330 calorias\n");
   printf("11-Jogar Volei => 105 calorias\n");
   printf("12-Lutar Karate =>290 calorias\n");
   printf("13-Nadar => 255 calorias\n");
   printf("14-Aspirar =>175 calorias\n");
   printf("15-Remar =>280 calorias\n");
   
   
   printf("Atividade(s)(max 3): ");
   
   scanf("%d", &atividades[i]); i++;
   while(i<3){
        scanf("%d", &atividades[i]);
		i++;
   }
   atividades[i]=0;
   i=0;
   while(atividades[i]!=0){
        sum+=calorias[atividades[i]-1];
		i++;
   }
   
   fprintf(ficha, "\\textbf{Gasto Calorico em Atividade Fisica}: %dkcal \\\\ \n", sum);
   total=sum+gcb;
   fprintf(ficha, "\\textbf{Gasto Calorico Total}: %dkcal \\\\ \n", total);
   distribuicao_calorica(ficha, total, peso);
}

void distribuicao_calorica (FILE * ficha, int total, int peso)
{
   int z;
   char r;
   
   /*5refeições diárias 1-15%, 2-5%, 3-35%, 4-15%, 5-30%*/
   fprintf(ficha, "\\begin{center} \\Large Distribuicao Calorica \\end{center}\n");
   z=0.15*total;
   fprintf(ficha, "\\textbf{Pequeno-almoco}: %dkcal \\\\ \n", z);
   z=0.05*total;
   fprintf(ficha, "\\textbf{Meio da manha}: %dkcal \\\\ \n", z);
   z=0.35*total;
   fprintf(ficha, "\\textbf{Almoco}: %dkcal \\\\ \n", z);
   z=0.15*total;
   fprintf(ficha, "\\textbf{Meio da Tarde}: %dkcal \\\\ \n", z);
   z=0.30*total;
   fprintf(ficha, "\\textbf{Jantar}: %dkcal \\\\ \n", z);
   
   printf("Inserir S para uma nova distribuicao calorica para perda de peso: ");
   scanf("%c", &r);
   scanf("%c", &r);
   if(r=='S'){
     plano_dieta(ficha, total);
	 previsao_plano(ficha, peso);
   }
   else return;
}

void previsao_plano(FILE * ficha, int peso)
{
   int peso_p;
   int obj;
   int semanas;
   int meses; int dias;

   printf("O seu peso atual e de %d, insira o peso que pretende atingir : ", peso);
   scanf("%d", &peso_p);
   obj=peso-peso_p;
   semanas=obj/0.5;
   fprintf(ficha, "\\textbf{Peso Pretendido}: %dkg \\\\ \\\\ \n", peso_p);
   dias=semanas*7;
   meses=semanas/4;
   fprintf(ficha, "\\small Com o atual plano atingira o peso pretendido em %d semanas. \\\\ (%d dias, aprox. %d mes(es))\n", semanas, dias, meses);
}  