#include <stdio.h>
#include <stdlib.h>
#include <string.h>



/***
**	Libraria de funções muito úteis para manipulação de arrays de caracteres na linguagem C.
**
**	@author jdc
**	@version 30/05/2014
**  1.0
**	
***/




/**
*	@param char* v: string to tokenize, char c: delimiter character, int size: a size for each token
*	@returns a pointer to new string array of tokens resultant from split v in tokens, taken char c as the delimiter.
*/
char** tokenize (char* v, char c, int size)
{
	int i, j;
	int toks=1;
	char** tokens;

	tokens = (char**) malloc (sizeof(char*));
	for(i=0; v[i]!='\n' && v[i]!='\0'; i++, toks++){
		tokens = (char**) realloc (tokens, toks*sizeof(char*));
		tokens[toks-1] = (char*) malloc(size*sizeof(char));

		for(j=0; v[i]!=c && v[i]!='\n' && v[i]!='\0';i++, j++)
			tokens[toks-1][j]=v[i];

		tokens[toks-1][j]='\0';
	}

	tokens = (char**) realloc (tokens, toks*sizeof(char*));
	tokens[toks-1]=NULL;


	return tokens;
}




/**
    @param a char array that may or not contains white spaces in the begining or the end
    @return a pointer to the new string from wich we remove all white spaces
*/
char* trim (char * token)
{
    int i;

    while(token[0]==' ') token++; /*Removing undesireble white-spaces in the begining of the string*/
    for(i=strlen(token); token[i-1]==' '; i--); /*Finding index of the last white space in the string*/
    token[i]='\0';

    return token;
}



//aux for intToString
 static char* reverse(char s[])
 {
     int i, j;
     char c;
 
     for (i = 0, j = strlen(s)-1; i<j; i++, j--) {
         c = s[i];
         s[i] = s[j];
         s[j] = c;
     }

     return s;
 }

/**
*	@param the int value that we want to convert to a String
*	@return a pointer to a String that is the int number that we received
*/
char* intToString(int n)
{
	 char *s;
     int i, sign;
     int size;

     /*count number of digits to allocate memory*/
     for(i=n, size=0; i>0; i=i/10) size++;

     s = (char*) malloc(size*sizeof(char));
 
     if ((sign = n) < 0){
         n = -n;
     }

     i = 0;
     
     do{
         s[i++] = n % 10 + '0';
     }
     while ((n /= 10) > 0);

     if (sign < 0){
         s[i++] = '-';
     }
     s[i] = '\0';
     return reverse(s);
 }

 



//Main teste function
/*int main()
{
	char* v = "This*lib*is*super*cool*love*megastring.h             \n";
	char** result;


	//Testing tokenize
	result = tokenize(v,'*',100);

	printf("token0: %s\n", result[0]);
	printf("token1: %s\n", result[1]);
	printf("token2: %s\n", result[2]);
	printf("token3: %s\n", result[3]);
	printf("token4: %s\n", result[4]);
	printf("token5: %s\n", result[5]);
	//Also testing trim!
	result[6] = trim(result[6]);
	printf("token6: [%s]\n", result[6]);


	//Testing intToString
	int x = 119;
	char* buff;
    buff=intToString(x);
    printf("%s\n", buff);


    for(x=0; NULL!=result[x]; x++)
    	free(result[x]);
    free(result);
	

	return 0;

}*/