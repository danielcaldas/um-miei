/*0-Pessima, 1-Ma, 2-Media Baixa, 3-Media, 4-Media Alta, 5-Boa,
6-Excelente*/

int classifica_img (int img, char sexo, int idade)
{
   if(sexo=='M'){
      if(idade>=18 && idade<=25){
	    if(img>=4 && img<=6) return 6;
		else if(img>=8 && img<=10) return 5;
		else if(img>=12 && img<=13) return 4;
		else if(img>=14 && img<=16) return 3;
		else if(img>=17 && img<=20) return 2;
		else if(img>=20 && img<=24) return 1;
		else if(img>=26 && img<=36) return 0;
	  }
      if(idade>=26 && idade<=35){
	    if(img>=8 && img<=11) return 6;
		else if(img>=12 && img<=15) return 5;
		else if(img>=16 && img<=18) return 4;
		else if(img>=18 && img<=20) return 3;
		else if(img>=22 && img<=24) return 2;
		else if(img>=24 && img<=28) return 1;
		else if(img>=28 && img<=36) return 0;
	  }
      if(idade>=36 && idade<=45){
	    if(img>=10 && img<=14) return 6;
		else if(img>=16 && img<=18) return 5;
		else if(img>=19 && img<=21) return 4;
		else if(img>=21 && img<=23) return 3;
		else if(img>=24 && img<=25) return 2;
		else if(img>=27 && img<=29) return 1;
		else if(img>=30 && img<=39) return 0;
	  }
      if(idade>=45 && idade<=55){
	    if(img>=12 && img<=16) return 6;
		else if(img>=18 && img<=20) return 5;
		else if(img>=21 && img<=23) return 4;
		else if(img>=24 && img<=25) return 3;
		else if(img>=26 && img<=27) return 2;
		else if(img>=28 && img<=30) return 1;
		else if(img>=32 && img<=38) return 0;
	  }
      if(idade>=56 && idade<=65){
	    if(img>=13 && img<=18) return 6;
		else if(img>=20 && img<=21) return 5;
		else if(img>=22 && img<=23) return 4;
		else if(img>=24 && img<=25) return 3;
		else if(img>=26 && img<=27) return 2;
		else if(img>=28 && img<=30) return 1;
		else if(img>=32 && img<=38) return 0;
	  }
	}
   if(sexo=='F'){
      if(idade>=18 && idade<=25){
	    if(img>=13 && img<=16) return 6;
		else if(img>=17 && img<=19) return 5;
		else if(img>=20 && img<=22) return 4;
		else if(img>=23 && img<=25) return 3;
		else if(img>=26 && img<=28) return 2;
		else if(img>=29 && img<=31) return 1;
		else if(img>=33 && img<=43) return 0;
	  }
      if(idade>=26 && idade<=35){
	    if(img>=14 && img<=16) return 6;
		else if(img>=18 && img<=20) return 5;
		else if(img>=21 && img<=23) return 4;
		else if(img>=24 && img<=25) return 3;
		else if(img>=27 && img<=29) return 2;
		else if(img>=31 && img<=33) return 1;
		else if(img>=36 && img<=49) return 0;
	  }
      if(idade>=36 && idade<=45){
	    if(img>=16 && img<=19) return 6;
		else if(img>=20 && img<=23) return 5;
		else if(img>=24 && img<=26) return 4;
		else if(img>=27 && img<=29) return 3;
		else if(img>=30 && img<=32) return 2;
		else if(img>=33 && img<=36) return 1;
		else if(img>=38 && img<=48) return 0;
	  }
      if(idade>=45 && idade<=55){
	    if(img>=17 && img<=21) return 6;
		else if(img>=23 && img<=25) return 5;
		else if(img>=26 && img<=28) return 4;
		else if(img>=29 && img<=31) return 3;
		else if(img>=32 && img<=34) return 2;
		else if(img>=35 && img<=38) return 1;
		else if(img>=39 && img<=50) return 0;
	  }
      if(idade>=56 && idade<=65){
	    if(img>=18 && img<=22) return 6;
		else if(img>=24 && img<=26) return 5;
		else if(img>=27 && img<=29) return 4;
		else if(img>=30 && img<=32) return 3;
		else if(img>=33 && img<=35) return 2;
		else if(img>=36 && img<=38) return 1;
		else if(img>=39 && img<=49) return 0;
	  }
	}
}	  

void imprime_classificacao_img (FILE * ficha, float img, char sexo, int idade)
{
 if(classifica_img(img, sexo, idade)==0) fprintf(ficha, "\\textbf{\\textbf{Classificacao (IMG)}: Pessima \\\\ \\\\ \n");
 else if(classifica_img(img, sexo, idade)==1) fprintf(ficha, "\\textbf{Classificacao (IMG)}: Ma \\\\ \\\\ \n");
 else if(classifica_img(img, sexo, idade)==2) fprintf(ficha, "\\textbf{Classificacao (IMG)}: Media Baixa \\\\ \\\\ \n");
 else if(classifica_img(img, sexo, idade)==3) fprintf(ficha, "\\textbf{Classificacao (IMG)}: Media \\\\ \\\\ \n");
 else if(classifica_img(img, sexo, idade)==4) fprintf(ficha, "\\textbf{Classificacao (IMG)}: Media Alta \\\\ \\\\ \n");
 else if(classifica_img(img, sexo, idade)==5) fprintf(ficha, "\\textbf{Classificacao (IMG)}: Boa \\\\ \\\\ \n");
 else if(classifica_img(img, sexo, idade)==6) fprintf(ficha, "\\textbf{Classificacao (IMG)}: Excelente \\\\ \\\\ \n");
} 
 