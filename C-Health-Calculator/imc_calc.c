/*0-Magreza Grave, 1-Magreza Moderada, 2-Magreza Leve, 3-Saudavel, 4-Excesso de Peso,
5-Obesidade Grau I, 6-Obesidade Grau II (Obesidade Severa), 7-Obesidade Grau III (Obesidade Morbida)
9-idade inferior a 6 anos*/

int classifica_imc (float imc, char sexo, int idade)
{
   if(idade<6) return 9;
   //HOMENS//
   if(sexo=='M'){
     if(idade==6){
	   if(imc<14.50) return 0; //Apenas magreza//
	   if(imc>=14.50 && imc<16.60) return 3;
	   if(imc>=16.60 && imc<18.00) return 4;
	   if(imc>=18.00) return 5; //Apenas Obesidade//
	 }
	 if(idade==7){
	   if(imc<15.00) return 0; //Apenas magreza//
	   if(imc>=15.00 && imc<17.30) return 3;
	   if(imc>=17.30 && imc<19.10) return 4;
	   if(imc>=19.10) return 5; //Apenas Obesidade//
	 }
	  if(idade==8){
	   if(imc<15.60) return 0; //Apenas magreza//
	   if(imc>=15.60 && imc<16.70) return 3;
	   if(imc>=16.70 && imc<20.30) return 4;
	   if(imc>=20.30) return 5; //Apenas Obesidade//
	  }
	  if(idade==9){
	   if(imc<16.10) return 0; //Apenas magreza//
	   if(imc>=16.10 && imc<18.80) return 3;
	   if(imc>=18.80 && imc<21.40) return 4;
	   if(imc>=21.40) return 5; //Apenas Obesidade//
	  }
	  if(idade==10){
	   if(imc<16.70) return 0; //Apenas magreza//
	   if(imc>=16.70 && imc<19.60) return 3;
	   if(imc>=19.60 && imc<22.50) return 4;
	   if(imc>=22.50) return 5; //Apenas Obesidade//
	  }
	  if(idade==11){
	   if(imc<17.20) return 0; //Apenas magreza//
	   if(imc>=17.20 && imc<20.30) return 3;
	   if(imc>=20.30 && imc<23.70) return 4;
	   if(imc>=23.70) return 5; //Apenas Obesidade//
	  }
	  if(idade==12){
	   if(imc<17.80) return 0; //Apenas magreza//
	   if(imc>=17.80 && imc<21.10) return 3;
	   if(imc>=21.10 && imc<24.80) return 4;
	   if(imc>=24.80) return 5; //Apenas Obesidade//
	  }
	  if(idade==13){
	   if(imc<18.50) return 0; //Apenas magreza//
	   if(imc>=18.50 && imc<21.90) return 3;
	   if(imc>=21.90 && imc<25.90) return 4;
	   if(imc>=25.90) return 5; //Apenas Obesidade//
	  }
	  if(idade==14){
	   if(imc<19.20) return 0; //Apenas magreza//
	   if(imc>=19.20 && imc<22.70) return 3;
	   if(imc>=22.70 && imc<26.90) return 4;
	   if(imc>=26.90) return 5; //Apenas Obesidade//
	  }
	  if(idade==15){
	   if(imc<19.90) return 0; //Apenas magreza//
	   if(imc>=19.90 && imc<23.60) return 3;
	   if(imc>=23.60 && imc<27.70) return 4;
	   if(imc>=27.70) return 5; //Apenas Obesidade//
	  }
	  if(idade==16){
	   if(imc<18.50) return 0; //Apenas magreza//
	   if(imc>=18.50 && imc<24.80) return 3;
	   if(imc>=24.80 && imc<25.90) return 4;
	   if(imc>=25.90) return 5; //Apenas Obesidade//
	  }
	  if(idade==17){
	   if(imc<18.40) return 0; //Apenas magreza//
	   if(imc>=18.40 && imc<24.90) return 3;
	   if(imc>=24.90 && imc<26.10) return 4;
	   if(imc>=26.10) return 5; //Apenas Obesidade//
	  }
	  if(idade>17 && idade<65){
	   if(imc<20.70) return 0;
	   if(imc>=20.70 && imc<26.40) return 3;
	   if(imc>=26.40 && imc<27.80) return 4;
	   if(imc>=27.80 && imc<31.10) return 5;
	   if(imc>=31.10 && imc<40.00) return 6;
	   if(imc>=40.00) return 7;
	  }
	  if(idade>=65){
	     if(imc<21.90) return 0;
         if(imc>=22.00 && imc<27.00) return 3;
         if(imc>=27.1 && imc<30.00) return 4; 
         if(imc>=30.00 && imc<35.00) return 5; 
         if(imc>=35.00 && imc<40.00) return 6;
         if(imc>=40.00) return 7;
	 }
	}
	 
	//MULHERES// 
	if(sexo=='F'){
     if(idade==6){
	   if(imc<14.30) return 0; //Apenas magreza//
	   if(imc>=14.30 && imc<16.10) return 3;
	   if(imc>=16.10 && imc<17.40) return 4;
	   if(imc>=17.40) return 5; //Apenas Obesidade//
	 }
	 if(idade==7){
	   if(imc<14.90) return 0; //Apenas magreza//
	   if(imc>=14.90 && imc<17.10) return 3;
	   if(imc>=17.10 && imc<18.90) return 4;
	   if(imc>=18.90) return 5; //Apenas Obesidade//
	 }
	  if(idade==8){
	   if(imc<15.60) return 0; //Apenas magreza//
	   if(imc>=15.60 && imc<18.10) return 3;
	   if(imc>=18.10 && imc<20.30) return 4;
	   if(imc>=20.30) return 5; //Apenas Obesidade//
	  }
	  if(idade==9){
	   if(imc<16.30) return 0; //Apenas magreza//
	   if(imc>=16.30 && imc<19.10) return 3;
	   if(imc>=19.10 && imc<21.70) return 4;
	   if(imc>=21.70) return 5; //Apenas Obesidade//
	  }
	  if(idade==10){
	   if(imc<17.00) return 0; //Apenas magreza//
	   if(imc>=10.00 && imc<20.10) return 3;
	   if(imc>=20.10 && imc<23.20) return 4;
	   if(imc>=23.20) return 5; //Apenas Obesidade//
	  }
	  if(idade==11){
	   if(imc<17.60) return 0; //Apenas magreza//
	   if(imc>=17.60 && imc<21.10) return 3;
	   if(imc>=21.10 && imc<24.50) return 4;
	   if(imc>=24.50) return 5; //Apenas Obesidade//
	  }
	  if(idade==12){
	   if(imc<18.30) return 0; //Apenas magreza//
	   if(imc>=18.30 && imc<22.10) return 3;
	   if(imc>=22.10 && imc<25.90) return 4;
	   if(imc>=25.90) return 5; //Apenas Obesidade//
	  }
	  if(idade==13){
	   if(imc<18.90) return 0; //Apenas magreza//
	   if(imc>=18.90 && imc<23.00) return 3;
	   if(imc>=23.00 && imc<27.70) return 4;
	   if(imc>=27.70) return 5; //Apenas Obesidade//
	  }
	  if(idade==14){
	   if(imc<19.30) return 0; //Apenas magreza//
	   if(imc>=19.30 && imc<23.80) return 3;
	   if(imc>=23.80 && imc<27.90) return 4;
	   if(imc>=27.90) return 5; //Apenas Obesidade//
	  }
	  if(idade==15){
	   if(imc<19.60) return 0; //Apenas magreza//
	   if(imc>=19.60 && imc<24.20) return 3;
	   if(imc>=24.20 && imc<28.80) return 4;
	   if(imc>=28.80) return 5; //Apenas Obesidade//
	  }
	  if(idade==16){
	   if(imc<18.30) return 0; //Apenas magreza//
	   if(imc>=18.30 && imc<25.70) return 3;
	   if(imc>=25.70 && imc<26.80) return 4;
	   if(imc>=26.80) return 5; //Apenas Obesidade//
	  }
	  if(idade==17){
	   if(imc<17.90) return 0; //Apenas magreza//
	   if(imc>=17.90 && imc<25.70) return 3;
	   if(imc>=25.70 && imc<26.20) return 4;
	   if(imc>=26.20) return 5; //Apenas Obesidade//
	  }
	  if(idade>17 && idade<65){
	   if(imc<19.10) return 0;
	   if(imc>=19.10 && imc<25.80) return 3;
	   if(imc>=25.80 && imc<27.30) return 4;
	   if(imc>=27.30 && imc<32.30) return 5;
	   if(imc>=32.30 && imc<40.00) return 6;
	   if(imc>=40.00) return 7;
	  }
      if(idade>=65){
	     if(imc<21.90) return 0;
         if(imc>=22.00 && imc<27.00) return 3;
         if(imc>=27.1 && imc<30.00) return 4; 
         if(imc>=30.00 && imc<35.00) return 5; 
         if(imc>=35.00 && imc<40.00) return 6;
         if(imc>=40.00) return 7;
	 }
	}

}

void imprime_classificacao (FILE * ficha, float imc, char sexo, int idade)
{
 if(classifica_imc(imc, sexo, idade)==0) fprintf(ficha, "\\textbf{Classificacao (IMC)}: Magreza Grave \\\\ \\\\ \n");
 else if(classifica_imc(imc, sexo, idade)==1) fprintf(ficha, "\\textbf{Classificacao (IMC)}: Magreza Moderada \\\\ \\\\ \n");
 else if(classifica_imc(imc, sexo, idade)==2) fprintf(ficha, "\\textbf{Classificacao (IMC)}: Magreza Leve \\\\ \\\\ \n");
 else if(classifica_imc(imc, sexo, idade)==3) fprintf(ficha, "\\textbf{Classificacao (IMC)}: Saudavel \\\\ \\\\ \n");
 else if(classifica_imc(imc, sexo, idade)==4) fprintf(ficha, "\\textbf{Classificacao (IMC)}: Excesso de Peso \\\\ \\\\ \n");
 else if(classifica_imc(imc, sexo, idade)==5) fprintf(ficha, "\\textbf{Classificacao (IMC)}: Obesidade Grau I \\\\ \\\\ \n");
 else if(classifica_imc(imc, sexo, idade)==6) fprintf(ficha, "\\textbf{Classificacao (IMC)}: Obesidade Grau II (Obesidade Severa) \\\\ \\\\ \n");
 else if(classifica_imc(imc, sexo, idade)==7) fprintf(ficha, "\\textbf{Classificacao (IMC)}: Obesidade Grau III (Obesidade Morbida) \\\\ \\\\ \n");
 else if(classifica_imc(imc, sexo, idade)==9) fprintf(ficha, "\\textbf{Classificacao (IMC)}: IDADE INFERIOR A 6 ANOS \\\\ \\\\\n");
}