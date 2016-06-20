#include <stdio.h>
#include <stdlib.h>
#include <string.h>


#define PREFIX "file://"
#define SITE_FOLDER "/site/"

int main()
{
	char* currentdir;
	currentdir = strdup(getenv("PWD"));

	FILE* file = fopen("home.html", "w");
	char* path;
	
	// Definir path para diretoria "site"
	path = (char*) malloc(100*sizeof(char));
	path = strcat(path,PREFIX);
	path = strcat(path,currentdir);
	path = strcat(path,SITE_FOLDER);

	fprintf(file, "<!DOCTYPE html>\n");
	fprintf(file, "<head>\n");
	fprintf(file, "\t<meta char-set=\"utf8\"/>\n");
	fprintf(file, "<title>Museu da Pessoa</title>\n");

	fprintf(file, "\t<style type=\"text/css\">\n");
	fprintf(file, "\t#wrapper{\n");
	fprintf(file, "\t\ttext-align:center;\n");
	fprintf(file, "\t\tmargin-top:0px;\n");
	fprintf(file, "\t\tmargin-bottom:0px;\n");
	fprintf(file, "\t\tpadding:0px;\n\t}");
	fprintf(file, "\t</style>\n");

	fprintf(file, "</head>\n");
	fprintf(file, "<body>\n");
	fprintf(file, "\t<div id=\"wrapper\">\n");
	fprintf(file, "\t\t<nav>\n");

	fprintf(file, "\t\t\t<center><font size=\"7\">&Iacutendice</font></center>\n");

	fprintf(file, "\t\t\t<ul>\n");
	fprintf(file, "\t\t\t\t<p align=\"center\" class=\"scroll\"><a href =\"%santonio.html\">Ant&oacutenio Machado</a></p>\n", path);
	fprintf(file, "\t\t\t\t<p align=\"center\" class=\"scroll\"><a href =\"%sfausto.html\">Fausto Gomes</a></p>\n", path);
	fprintf(file, "\t\t\t\t<p align=\"center\" class=\"scroll\"><a href =\"%staberna.html\">Taberna do Fausto</a></p>\n", path);
	fprintf(file, "\t\t\t\t<p align=\"center\" class=\"scroll\"><a href =\"%sneca.html\">Neca Chamin&eacute</a></p>\n", path);

	fprintf(file, "\t\t\t</ul>");
	fprintf(file, "\t\t</nav>\n\t\t<br><br>\n");
	fprintf(file, "\t\t<footer>\n");
	fprintf(file, "\t\t\t<p><b>Grupo de trabalho</b></p>\n");
	fprintf(file, "\t\t\t<p><i>Daniel Caldas a67691 / Marcelo Gon&ccedilalves a67736 / Ricardo Silva a67728</i></p>\n");
	fprintf(file, "\t\t</footer>\n");
	fprintf(file, "\t</div>\n");
	fprintf(file, "</body>\n</html>\n");

	fclose(file);
	
	return 0;
}
