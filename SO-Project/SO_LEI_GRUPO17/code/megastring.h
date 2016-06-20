#ifndef _H_MEGASTRING
#define _H_MEGASTRING

/***
**	Libraria de funções muito úteis para manipulação de arrays de caracteres na linguagem C.
**
**	@author jdc
**	@version 30/05/2014
**	
***/

char** tokenize (char* v, char c, int size);
char* trim (char * token);
char* intToString(int n);

#endif