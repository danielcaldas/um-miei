/*
 *	Autores: Daniel Caldas, JosÈ Cortez, Marcelo GonÁalves, Ricardo Silva
 *	Data: 2015.04.08
 *	Versão: 4.0v
 *
 */

#include "const.h"

Forma* e;
Cone* cone;
Rectangulo* r;
Triangulo* t;
Circulo* circulo;
Paralelepipedo* p;
Esfera* ef;

// Vari·vel globa para armazenamento dos modelos da cena a desenhar
vector<Forma*> formas;

// Vari·veis globais para controlo apropriado da c‚mera
float raio = 240, cam_h = 0, cam_v = 0.5, camh_x = 0, camh_y = 0, cir1 = 0, cir2 = 0;
float x_tela, y_tela; //Variaveis para guardar posiÁ„o da tela em que se carrega no rato

int estado_botao = 0;

// Vari·vel global opÁ„o do menu
int option;

// Vari·veis auxiliares para leitura de ficheiros XML e povoamento das estruturas de dados
vector<TransformsWrapper> transforms_atual;
vector<Grupo> grupos;
Ponto3D color;

// Frames
int frame = 0;
int timebase = 0;

/**FunÁ„o que gera automaticamente ficheiros com os palentas do modelo est·tico do sistema solar*/
vector<string> makeStaticSolarSystem()
{
	string p;
	vector<string> planetas;
	
	p = "gerador rec 512 512 plano.3d"; planetas.push_back(p);
	p = "gerador esfera 17 30 30 sol.3d"; planetas.push_back(p);
	p = "gerador esfera 1.5 30 30 mercurio.3d"; planetas.push_back(p);
	p = "gerador esfera 1.9 30 30 venus.3d"; planetas.push_back(p);
	p = "gerador esfera 2 30 30 terra.3d"; planetas.push_back(p);
	p = "gerador esfera 0.5 30 30 lua.3d"; planetas.push_back(p);
	p = "gerador esfera 1.6 30 30 marte.3d"; planetas.push_back(p);
	p = "gerador esfera 9 30 30 jupiter.3d"; planetas.push_back(p);
	p = "gerador esfera 8 30 30 saturno.3d"; planetas.push_back(p);
	p = "gerador esfera 4.7 30 30 urano.3d"; planetas.push_back(p);
	p = "gerador esfera 4.5 30 30 neptuno.3d"; planetas.push_back(p);
	
	return planetas;
}

/*---------------------------------------------------------------------------------------*/
/*---------------------------------------------------------------------------------------*/

/*CÛdigo referente ao GLUT*/

/*---------------------------------------------------------------------------------------*/
/*---------------------------------------------------------------------------------------*/

/**Ajusta tamanho da janela (esqueleto openGL)*/
void changeSize(int w, int h)
{
	
	// Prevenir divis„o por 0 quando janela È demasiado pequena
	// (N„o podemos criar uma janela com altura 0)
	if (h == 0)
		h = 1;
	
	float ratio = w * 1.0 / h;
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	
	glViewport(0, 0, w, h);
	gluPerspective(45.0f, ratio, 1.0f, 1000.0f);
	
	glMatrixMode(GL_MODELVIEW);
}

/**Desenha a cena (esqueleto openGL)*/
void renderScene(void)
{
	float fps;
	int time;
	char s[64];
	
	// Limpar buffer
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	
	// Atualizar c‚mara
	glLoadIdentity();
	//C‚mera em modo explorador (C‚mera move-se nas bordas de 1 esfera)
	gluLookAt(raio*sin(cam_h + camh_x)*cos(cam_v + camh_y), raio*sin(cam_v + camh_y), raio*cos(cam_h + camh_x)*cos(cam_v + camh_y),
			  0.0, 0.0, 0.0,
			  0.0f, 1.0f, 1.0f);
	
	glPolygonMode(GL_FRONT, option);
	
	// Loop para desenhar formas
	
	t->drawVBO();
	
	// TÌtulo da janela com as frames
	frame++;
	time = glutGet(GLUT_ELAPSED_TIME);
	if (time - timebase > 1000) {
		fps = frame*1000.0 / (time - timebase);
		timebase = time;
		frame = 0;
		sprintf(s, "FPS: %f6.2", fps);
		glutSetWindowTitle(s);
	}
	
	glutSwapBuffers();
}

/**FunÁıes que processa aÁıes do teclado (movimento da c‚mera)*/
void teclado_normal(unsigned char tecla, int x, int y){
	switch (tecla) {
		case 'a':
			raio -= 1;
			break;
		case 'd':
			raio += 1;
			break;
			
		default:
			break;
	}
}

void teclado_especial(int tecla, int x, int y){
	switch (tecla) {
		case GLUT_KEY_UP:
			if (cam_v + 0.05<M_PI_2)   //Para c‚mera n„o virar ao contr·rio
				cam_v += 0.05;
			break;
		case GLUT_KEY_DOWN:
			if (cam_v - 0.05>-M_PI_2)  //Para c‚mera n„o virar ao contr·rio
				cam_v -= 0.05;
			break;
			
		case GLUT_KEY_LEFT:
			cam_h -= 0.05;
			break;
		case GLUT_KEY_RIGHT:
			cam_h += 0.05;
			break;
			
		default:
			break;
	}
}

void rato(int botao, int estado, int x, int y){
	if (botao == GLUT_LEFT_BUTTON){
		if (estado == GLUT_DOWN){
			estado_botao = 1;
			x_tela = x;
			y_tela = y;
		}
		else{
			estado_botao = 0;
			cam_v += camh_y;
			cam_h += camh_x;
			camh_x = 0;
			camh_y = 0;
		}
	}
}

void mov_rato(int x, int y){
	float teste;
	if (estado_botao){
		if (x_tela != x)
			camh_x = (x_tela - x)*0.007;
		
		if (y_tela != y){
			teste = (y_tela - y)*0.002;
			if (teste + cam_v>-M_PI && teste + cam_v<M_PI_2)
				camh_y = teste;
		}
		
	}
}

/**Handler para eventos de escolha do menu*/
void processMenuEvents(int op)
{
	switch (op){
		case 1:
			option = GL_FILL;
			break;
		case 2:
			option = GL_LINE;
			break;
		case 3:
			option = GL_POINT;
			break;
		default:
			break;
	}
	glutPostRedisplay();
}

/**FunÁ„o que constrÛi o menu*/
void menu()
{
	glutCreateMenu(processMenuEvents);
	
	// Definir entradas do menu
	glutAddMenuEntry("Ver com cores", 1);
	glutAddMenuEntry("Ver apenas linhas", 2);
	glutAddMenuEntry("Ver apenas pontos", 3);
	
	glutAttachMenu(GLUT_RIGHT_BUTTON);
}

/**FunÁ„o que inicializa os par‚metros do glut criando a respetiva janela para desenhar a cena*/

int prepare_glut(int argc, char **argv) {
	option = GL_LINE;
	
	// InicializaÁ„o

	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
	glutInitWindowPosition(100, 100);
	glutInitWindowSize(800, 800);
	glutCreateWindow("Mini Motor3D");
	
	// Registo de funÁıes
	glutDisplayFunc(renderScene);
	glutIdleFunc(renderScene);
	glutReshapeFunc(changeSize);
	
	// InteraÁıes Rato/Teclado
	glutKeyboardFunc(teclado_normal);
	glutSpecialFunc(teclado_especial);
	glutMouseFunc(rato);
	glutMotionFunc(mov_rato);
	
	// CriaÁ„o do menu
	menu();
	
	// Settings OpenGL
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_CULL_FACE);
	glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	
	glewInit();
	ilInit();

	// Entrar no ciclo do GLUT
	
	t = new Triangulo();
	t->gerarTriangulo(100, "cone.3d");
	t->criarVBO("cone.3d");
	t->carregarImagem();
	
	// alguns settings para OpenGL
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_CULL_FACE);
	glEnable(GL_TEXTURE_2D);
	
	glClearColor(0.0f,0.0f,0.0f,0.0f);
	
	glutMainLoop();
	
	return -1;
}

/**FunÁ„o main*/
int main(int argc, char **argv){
	prepare_glut(argc, argv);
	//interpretador();
	return 0;
}