#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "hash.h"
#include "const.h"

# define SIZE 10

char *v1 = "sfwef";
char *v2 = "asd";
char *v3 = "sfddsag";
char *v4 = "sadas";
char *v5 = "axc";
char *v6 = "scx";
char *v7 = "q";
char *v8 = "ssfa";


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

int main () {
	initHash();
	int i=1;
	int a[3] = {1,2,3};
	
	
	insertHashVSI(INT, "v1");
	insertHashVSI(INT, "v2");
	insertHashVSI(INT, "v3");
	insertHashVSI(INT, "v4");
	insertHashVSI(INT, "v5");
	insertHashVSI(INT, "v6");
	insertHashVSI(INT, "v7");
	insertHashVCI(INT, "v8", 12);
	//
	insertHashACI(INT,"v9",3,a,3);

	printf("%d\n", containsValue("v1"));
	printf("%d\n", containsValue("v2"));
	printf("%d\n", containsValue("v3"));
	printf("%d\n", containsValue("v4"));
	printf("%d\n", containsValue("v5"));
	printf("%d\n", containsValue("v6"));
	printf("%d\n", containsValue("v7"));
	printf("%d\n", containsValue("v8"));
	printf("%d\n", containsValue("v9"));

	printf("\n\n");

	printf("%d\n", findEnderecoV("v1"));
	printf("%d\n", getVarSize("v2"));
	printf("%d\n", getVarValue("v8"));
	printf("%d\n", getArrValue("v9",2));
	printf("%d\n", findEnderecoA("v9",2));

	return 0;
}
