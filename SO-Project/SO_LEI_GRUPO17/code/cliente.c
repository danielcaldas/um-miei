#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <sys/types.h>
#include <unistd.h>
#include <string.h>
#include <fcntl.h>
#include <signal.h>


#define MAX_NAME_SIZE 1000
#define MAX_COMAND_SIZE 1024


int fd;

void sigint_handler (int sig)
{
	write(0,"\n$CLIENTE SAIU$\n",16);
	exit(0);
}


/*Cliente*/
int main(){

	char buff[MAX_COMAND_SIZE];
	char c;
	int size;


	signal(SIGINT,sigint_handler);

	while(1){

		system("clear");

		write(1,"**************************- SERVIÇO DE CONTAGEM -***************************\n",88);
		write(1,"@by: J. Daniel Caldas a67691, José Cortez a67716, Simao Dias a61006\n",69);
		write(1,"Opcoes para comunicar com o servidor:\n",38);
		write(1," - incrementar dist:con:freg:inc --> para incrementar\n",54);
		write(1," - agregar dist:0:fich           --> total agregado do distrito dist\n",69);
		write(1," - agregar dist:1:fich           --> total agregado discriminado por condelho\n",78);
		write(1," - agregar dist:2:fich           --> total agregado discriminado por freguesias\n",80);
		write(1," - agregar dist:con:0:fich       --> total agregado do concelho con\n",68);
		write(1," - agregar dist:con:1:fich       --> total agregado do concelho por freguesias\n",79);
		write(1," - agregar dist:con:freg:0:fich  --> total agregado da fregesia freg\n",69);
        write(1,"                                 fich é um nome de um ficheiro exemplo.txt\n\n",77);


		write(1,"> ",2);

		//LER & ESCREVER COMANDO DO CLIENTE NO PIPE
		size=0;
		while( read(0,&c,1) > 0){
			if(c=='\n') break;
			buff[size]=c;
			size++;
		}
		buff[size]='\n';
		size++;

		fd = open("fifo",O_WRONLY);
		write(fd,buff,size);
		close(fd);

		buff[0]='\0';
	} 
	
}