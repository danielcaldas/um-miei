/*
 *	Autores: Daniel Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 *	Data: 2015.03.15
 *	Versão: 1.0v
 *
 */

#include "const.h"

// Variável globa para armazenamento dos modelos da cena a desenhar
vector<Forma*> Formas;

// Variáveis globais para controlo apropriado da câmera
float px, py, pz;
float alpha, beta, radius;
float height;

// Variável global opção do menu
int option;


/*----------------------- CÓDIGO REFERENTE AO GLUT ------------------------------*/

/**Ajusta tamanho da janela (esqueleto openGL)*/
void changeSize(int w, int h)
{

	// Prevenir divisão por 0 quando janela é demasiado pequena
	// (Não podemos criar uma janela com altura 0)
	if (h == 0)
		h = 1;

	float ratio = w * 1.0 / h;
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();

	glViewport(0, 0, w, h);
	gluPerspective(45.0f, ratio, 1.0f, 1000.0f);

	glMatrixMode(GL_MODELVIEW);
}

void sphericalToCartesian()
{
	pz = radius * cosf(beta) * cosf(alpha);
	px = radius * cosf(beta) * sinf(alpha);
	py = radius * sinf(beta);
}

/**Desenha a cena (esqueleto openGL)*/
void renderScene(void)
{
	// Limpar buffer
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	// Conversão de coordenadas esféricas para coordenadas cartesianas da posição da câmera
	sphericalToCartesian();

	// Atualizar câmara
	glLoadIdentity();
	gluLookAt(px, py, pz,
		0.0, 0.0, 0.0,
		0.0f, 1.0f, 0.0f);

	glEnable(GL_CULL_FACE);
	glPolygonMode(GL_FRONT_AND_BACK, option);

	// Loop para desenhar formas
	for (std::vector<Forma*>::iterator it = Formas.begin(); it != Formas.end(); ++it){
		(*it)->draw();
	}

	glutSwapBuffers();
}

/**Função que processa ações do teclado (movimento da câmera)*/
void keyPressed(unsigned char key, int x, int y)
{
	switch (key){
	case 'a':
		alpha -= 0.1;
		break;
	case 'd':
		alpha += 0.1;
		break;
	case 'w':
		if (beta < (M_PI / 2) - 0.1) beta += 0.1;
		break;
	case 's':
		if (beta > -(M_PI / 2) + 0.1) beta -= 0.1;
		break;
	case'q':
		if (radius>0) radius -= 0.1;
		break;
	case 'e':
		radius += 0.1;
		break;
	default:
		break;
	}
	glutPostRedisplay();
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

/**Função que constrói o menu*/
void menu()
{
	glutCreateMenu(processMenuEvents);

	// Definir entradas do menu
	glutAddMenuEntry("Ver com cores", 1);
	glutAddMenuEntry("Ver apenas linhas", 2);
	glutAddMenuEntry("Ver apenas pontos", 3);

	glutAttachMenu(GLUT_RIGHT_BUTTON);
}

/**Inicializa parâmetros relativos à camera*/
void init_camera_parameters()
{
	px = 0;
	py = 3.0;
	pz = 5.0;
	alpha = 0.5;
	beta = (M_PI / 4);
	radius = 2;
}

/**Função que inicializa os parâmetros do glut criando a respetiva janela para desenhar a cena*/
int prepare_glut()
{
	init_camera_parameters();
	option = GL_LINE;
	height = 1;

	// Inicialização
	char *myargv[1];
	int myargc = 1;
	myargv[0] = "motor3d";
	glutInit(&myargc, myargv);
	glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
	glutInitWindowPosition(100, 100);
	glutInitWindowSize(800, 800);
	glutCreateWindow("Mini Motor3D");

	// Registo de funções 
	glutDisplayFunc(renderScene);
	glutIdleFunc(renderScene);
	glutReshapeFunc(changeSize);

	// Interações Rato/Teclado
	glutDisplayFunc(renderScene);
	glutReshapeFunc(changeSize);
	glutIdleFunc(renderScene);
	glutKeyboardFunc(keyPressed);

	// Criação do menu
	menu();

	// Settings OpenGL
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_CULL_FACE);

	printf("end.\n\n");
	// Entrar no ciclo do GLUT
	glutMainLoop();

	return -1;
}
/*---------------------------------------------------------------------------------------*/



/**Parser xml extrai nomes de ficheiros que conteem os triângulos das figuras a desenhar*/
vector<string> readFromXML(string filename)
{
	vector<string> figuras;

	const char * nomeFich = filename.c_str();

	const char* circum;
	TiXmlDocument doc(nomeFich);
	doc.LoadFile();
	TiXmlHandle docHandle(&doc);

	TiXmlElement * ficheiro = docHandle.FirstChild("imagem").FirstChild("modelo").ToElement();
	while (ficheiro)
	{
		circum = ficheiro->Attribute("ficheiro");
		figuras.push_back(string(circum));
		ficheiro = ficheiro->NextSiblingElement("modelo");
	}

	return figuras;
}

/**Função que testa se um dado ficheiro possuí extensão .xml*/
bool isXML(string filename)
{
	if (regex_match(filename, regex(".+\.xml"))) return true;
	else{
		cout << ERROR_FORMAT_EXCEPTION;
		return false;
	}
}

/**Verifica se um dado ficheiro existe na diretoria do projeto*/
bool lookUpFile(string filename)
{
	ifstream file(filename);
	if (file){
		return true;
	} else {
		cout << ERROR_FILE_NF;
		return false;
	}
}

/**Função que prepara as figuras para desenhar a partir de uma fonte .xml e invoca o ciclo glut*/
int gerar_cena(vector<string> args)
{
	string filename;
	string form;

	if (args.size() < 2){
		cout << ERROR_NUMBER_ARGS;
		return -1;
	}
	try{
		filename = args.at(1);
		if (isXML(filename) && lookUpFile(filename)){
			printf("a recolher dados para desenhar cena ...\n");
			vector<string> files = readFromXML(filename);
			for (std::vector<string>::iterator it = files.begin(); it != files.end(); ++it){
				ifstream f((*it));
				f >> form;

				Forma* s;
				if (form.compare(FORMA_RECTANGULO) == 0){
					s = new Rectangulo();
				}
				else if (form.compare(FORMA_TRIANGULO) == 0){
					s = new Triangulo();
				}
				else if (form.compare(FORMA_CIRCULO) == 0){
					s = new Circulo();
				}
				else if (form.compare(FORMA_PARALEL) == 0){
					s = new Paralelepipedo();
				}
				else if (form.compare(FORMA_ESFERA)==0){
					s = new Esfera();
				}
				else if (form.compare(FORMA_CONE)==0){
					s = new Cone();
				}
				s->read3DfromFile((*it));
				Formas.push_back(s);
			}
			// Chamada explícita para desenhar cena
			prepare_glut();
		}
	}
	catch (invalid_argument ia){
		cout << ERROR_INVALID_ARGS;
	}
	return -1;
}

/** Função que remove os espaços em branco de uma string*/
string removeSpaces(string input){
	input.erase(remove(input.begin(), input.end(), ' '), input.end());
	return input;
}

/**Comando gerador*/
int gerador(vector<string> args)
{
	string file;

	if (args.size() < 3){
		cout << ERROR_NUMBER_ARGS;
		return -1;
	}
	try{
		if ((args.at(1).compare("rec") == 0) && args.size() == 5){
			file = args.at(4);
			float c = stof(args.at(2));
			float l = stof(args.at(3));

			// Gerar ficheiro com os triângulos
			cout << "a gerar rectangulo ...\n";
			Rectangulo* p = new Rectangulo();
			p->gerarRectangulo(l, c, file);
			cout << "end.\n\n";
		}
		else if ((args.at(1).compare("circ") == 0) && args.size() == 5){
			file = args.at(4);
			float raio = stof(args.at(2));
			float nlados = stof(args.at(3));

			// Gerar ficheiro com os triângulos
			cout << "a gerar circulo ...\n";
			Circulo* c = new Circulo();
			c->gerarCirculo(raio, nlados, file);
			cout << "end.\n\n";
		}
		else if ((args.at(1).compare("tri") == 0) && args.size() == 4){
			file = args.at(3);
			float lado = stof(args.at(2));

			// Gerar ficheiro com os triângulos
			cout << "a gerar triangulo ...\n";
			Triangulo* p = new Triangulo();
			p->gerarTriangulo(lado, file);
			cout << "end.\n\n";
		}
		else if ((args.at(1).compare("paralel") == 0) && args.size() == 6){
			file = args.at(5);
			float l = stof(args.at(2));
			float c = stof(args.at(3));
			float h = stof(args.at(4));

			// Gerar ficheiro com os triângulos
			cout << "a gerar parelelepipedo ...\n";
			Paralelepipedo* p = new Paralelepipedo();
			p->gerarParalelepipedo(l, c, h, file);
			cout << "end.\n\n";
		}
		else if ((args.at(1).compare("esfera") == 0) && args.size() == 6){
			file = args.at(5);
			float raio = stof(args.at(2));
			int fatias = stof(args.at(3));
			int camadas = stoi(args.at(4));

			// Gerar ficheiro com os triângulos
			cout << "a gerar esfera ...\n";
			Esfera* esfera = new Esfera();
			esfera->gerarEsfera(raio, fatias, camadas, file);
			cout << "end.\n\n";
		}
		else if ((args.at(1).compare("cone") == 0) && args.size() == 7){
			file = args.at(6);
			float h = stof(args.at(2));
			float r = stof(args.at(3));
			float nlados = stof(args.at(4));
			float ncamadas = stof(args.at(5));

			// Gerar ficheiro com os triângulos
			cout << "a gerar cone ...\n";
			Cone* c = new Cone();
			c->gerarCone(h, r, nlados, ncamadas, file);
			cout << "end.\n\n";
		}
		else { cout << ERROR_INVALID_ARGS; }
	}
	catch (invalid_argument ia){
		cout << ERROR_INVALID_ARGS;
	}
	return -1;
}

/**Funcão interpretar*/
int interpretar(string linha){
	istringstream ss(linha);
	string token;
	int cont = 0;
	vector<string> playerInfoVector;

	while (std::getline(ss, token, ' ')) {
			token = removeSpaces(token);
			if (token.compare("") != 0){
				playerInfoVector.push_back(token);
				cont++;
			}
	}

	// Comando gerar
	if (playerInfoVector.at(0).compare("gerador") == 0 && cont != 0){
		return gerador(playerInfoVector);
	}
	else if (playerInfoVector.at(0).compare("desenhar") == 0 && cont != 0){
		return gerar_cena(playerInfoVector);
	}
	else if (playerInfoVector.at(0).compare("help") == 0 && cont != 0){
		cout << MESSAGE_HELP;
	}
	else if (playerInfoVector.at(0).compare("exit") == 0 && cont != 0){
		return 0;
	}
	else {
		// Como o comando invocado não é nenhum dos anteriores, devolve o erro correspondente
		cout << ERROR_COMMAND_NO_EXISTS;
		return -1;
	}
}

/**Função interpretador*/
void interpretador()
{
	int resultado = 0;
	string input = "";
	int ciclo = 1;

	for (printf("$motor3D > "); ciclo && getline(cin, input); printf("$motor3D > ")){
		if (input.compare("") != 0){
			resultado = interpretar(input);
			if (resultado == 0)
				ciclo = 0;
		}
	}
}

/**Função main*/
int main() {
	interpretador();
	return 0;
}