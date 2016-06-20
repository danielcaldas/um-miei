/*
*	Autores: Daniel Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
*	Data: 2015.04.08
*	Versão: 2.0v
*
*/

#include "formas.h"
#include "const.h"

#include <GL/glut.h>
#include <string>
#include <fstream>
#include <vector>
#include <math.h>
#include <iostream>
#include <sstream>

using namespace std;

# define M_PI 3.14159265358979323846 /*Pi*/

/**Funções que cada sólido herda diretamente da super classe sólido
ou implementa uma versão s:

- write3DtoFile(string filename): gera pontos automaticamente armazenando-os diretamente num ficheiro;

- read3DfromFile(string filename): lê um conjunto de pontos previamente gerado pela aplicação armazenando-os
na estrutura de dados adequada;

- draw(): método da forma implementado em conformidade que é usado para desenhar a cena no ciclo glut.
*/


/*---------------------- Class - Ponto3D -----------------------------*/
Ponto3D::Ponto3D()
{
	x = 0.0f; y = 0.0f; z = 0.0f;
}

/**Imprime ponto3d para stdout*/
void Ponto3D::printP() const
{
	cout << "X:" << x << " " << " Y:" << y << " " << "Z:" << z << "\n";
}

/**Converte ponto3d em string*/
string Ponto3D::tostring()
{
	stringstream ss;
	ss << x << " " << y << " " << z;
	return ss.str();
}
/*---------------------------------------------------------------------*/


/*--------------------- Class - Rotacao3D -----------------------------*/
Rotacao3D::Rotacao3D()
{
	ang = 0.0f;  x = 0.0f; y = 0.0f; z = 0.0f;
}

void Rotacao3D::printR() const
{
	cout << "Angulo:" << ang << " X:" << x << " Y:" << y << " Z:" << z << "\n";
}
/*---------------------------------------------------------------------*/


/*---------------------- Class - Forma -------------------------------*/

/**Função que lê um conjunto de triângulos de um dado ficheiro, cada subclasse poderá
eventualmente ter a necessidade de implementar a sua função de leitura mas esta é a base*/
void Forma::read3DfromFile(string filename)
{
	std::fstream file(filename, std::ios_base::in); // Nome do ficheiro supostamente temos de ir buscá-lo a um ficheiro .xml?

	file >> nome;
	float x, y, z;
	while (file >> x >> y >> z)
	{
		Ponto3D t;
		t.x = x; t.y = y; t.z = z;
		tgls.push_back(t); // Adicionar triangulo ao vector de triangulos da esfera
	}
	file.close();
}

void Forma::setTransformacoes(vector<TransformsWrapper> ts)
{
	transforms = ts;
}

/**Método que aplica transformações geométricas às formas (método genérico)*/
void Forma::applyTransforms()
{
	int i, n;

	n = transforms.size();
	glPushMatrix();
	for (i = 0; i < n; i++){
		TransformsWrapper tw = transforms.at(i);
		const char* op = tw.nome.c_str();

		if (strcmp(op, "ROTATE") == 0){
			glRotatef(tw.rotacao.ang, tw.rotacao.x, tw.rotacao.y, tw.rotacao.z);
		}
		else if (strcmp(op, "TRANSLATE") == 0){
			glTranslatef(tw.translacao.x, tw.translacao.y, tw.translacao.z);
		}
		else if (strcmp(op, "SCALE") == 0){
			glScalef(tw.escala.x, tw.escala.y, tw.escala.z);
		}
	}
}
/*---------------------------------------------------------------------*/


/*-------------- Class - Triângulo (Equilátero) -----------------------*/
void Triangulo::gerarTriangulo(float l, string filename)
{
	lado = l;
	write3DtoFile(filename);
}

void Triangulo::write3DtoFile(string filename)
{
	float h = sinf(M_PI / 3)*(lado / 2); // altura
	ofstream file(filename);

	file << "TRIANGULO\n";

	file << 0.0f << " " << 0.0f << " " << 0.0f << "\n";
	file << h << " " << 0.0f << " " << 0.0f << "\n";
	file << 0.0f << " " << 0.0f << " " << (-lado / 2) << "\n";

	file << 0.0f << " " << 0.0f << " " << 0.0f << "\n";
	file << 0.0f << " " << 0.0f << " " << (lado / 2) << "\n";
	file << h << " " << 0.0f << " " << 0.0f << "\n";

	file.close();
}

/*Função que desenhar um conjunto de triângulos armazenados de um modo sequencial*/
void Triangulo::draw()
{
	int n;

	
	applyTransforms();	

	n = tgls.size();
	for (int i = 0; i < n; i += 3){
		glBegin(GL_TRIANGLES);
		glColor3f(color.x, color.y, color.z);

		glVertex3f(tgls[i].x, tgls[i].y, tgls[i].z);
		glVertex3f(tgls[i + 1].x, tgls[i + 1].y, tgls[i + 1].z);
		glVertex3f(tgls[i + 2].x, tgls[i + 2].y, tgls[i + 2].z);
		glEnd();
	}
	
	glPopMatrix();
	
}
/*---------------------------------------------------------------------*/


/*------------------------- Class - Círculo ----------------------------*/
void Circulo::gerarCirculo(float r, int l, string filename)
{
	raio = r; nlados = l;
	write3DtoFile(filename);
}

void Circulo::write3DtoFile(string filename)
{
	float alpha = 2 * M_PI;
	float decAngulo = (2 * M_PI) / nlados;
	ofstream file(filename);

	file << "CIRCULO\n";
	file << 0.0f << " " << 0.0f << " " << 0.0f << "\n";
	for (int i = 0; i <= nlados; i++){
		file << raio*sinf(alpha) << " " << 0 << " " << raio*cosf(alpha) << "\n";
		alpha -= decAngulo;
	}
	file << 0.0f << " " << 0.0f << " " << 0.0f << "\n";
	for (int i = 0; i <= nlados; i++){
		file << raio*cosf(alpha) << " " << 0 << " " << raio*sinf(alpha) << "\n";
		alpha -= decAngulo;
	}

	file.close();
}

void Circulo::draw()
{
	int i, n;

	
	applyTransforms();	

	n = tgls.size();
	glBegin(GL_TRIANGLE_FAN);
	glColor3f(color.x, color.y, color.z);
	for (i = 0; i <= n; i++){
		glVertex3f(tgls[i].x, tgls[i].y, tgls[i].z);
	}
	glEnd();
	
	glPopMatrix();
	
}
/*---------------------------------------------------------------------*/



/*-------------------------- Class - Plano ----------------------------*/
void Rectangulo::gerarRectangulo(float largura, float comprimento, string filename)
{
	l = largura; c = comprimento;
	write3DtoFile(filename);
}

/*Função que internamente à classe gera o ficheiro os triângulos*/
void Rectangulo::write3DtoFile(string filename) {
	ofstream file;
	file.open(filename);

	file << "RECTANGULO\n";

	file << -l / 2.0f << " " << 0.0f << " " << c / 2.0f << "\n";
	file << l / 2.0f << " " << 0.0f << " " << -c / 2.0f << "\n";
	file << -l / 2.0f << " " << 0.0f << " " << -c / 2.0f << "\n";

	file << -l / 2.0f << " " << 0.0f << " " << c / 2.0f << "\n";
	file << l / 2.0f << " " << 0.0f << " " << c / 2.0f << "\n";
	file << l / 2.0f << " " << 0.0f << " " << -c / 2.0f << "\n";

	file << c / 2.0f << " " << 0.0f << " " << -l / 2.0f << "\n";
	file << -c / 2.0f << " " << 0.0f << " " << l / 2.0f << "\n";
	file << -c / 2.0f << " " << 0.0f << " " << -l / 2.0f << "\n";

	file << c / 2.0f << " " << 0.0f << " " << -l / 2.0f << "\n";
	file << c / 2.0f << " " << 0.0f << " " << l / 2.0f << "\n";
	file << -c / 2.0f << " " << 0.0f << " " << l / 2.0f << "\n";


	file.close();
}

/*Função que desenhar um conjunto de triângulos armazenados de um modo sequencial*/
void Rectangulo::draw()
{
	int n;

	
	applyTransforms();	

	n = tgls.size();
	for (int i = 0; i < n; i += 3){
		glBegin(GL_TRIANGLES);
		glColor3f(color.x, color.y, color.z);

		glVertex3f(tgls[i].x, tgls[i].y, tgls[i].z);
		glVertex3f(tgls[i + 1].x, tgls[i + 1].y, tgls[i + 1].z);
		glVertex3f(tgls[i + 2].x, tgls[i + 2].y, tgls[i + 2].z);
		glEnd();
	}	
	
	glPopMatrix();
	
}
/*---------------------------------------------------------------------*/


/*---------------------- Class - Paralelepípedo -----------------------*/
/* Função vísivel do exterior para gerar um ficheiro com triângulos que definem o paralelepípedo */
void Paralelepipedo::gerarParalelepipedo(float largura, float comprimento, float altura, string filename)
{
	l = largura; c = comprimento; a = altura;
	write3DtoFile(filename);
}

/*Função que internamente à classe gera o ficheiro os triângulos*/
void Paralelepipedo::write3DtoFile(string filename) {
	ofstream file;
	file.open(filename);

	file << "PARALELEPIPEDO\n";

	//topo
	file << -l / 2.0f << " " << a / 2.0f << " " << c / 2.0f << "\n";
	file << l / 2.0f << " " << a / 2.0f << " " << c / 2.0f << "\n";
	file << l / 2.0f << " " << a / 2.0f << " " << -c / 2.0f << "\n";

	file << l / 2.0f << " " << a / 2.0f << " " << -c / 2.0f << "\n";
	file << -l / 2.0f << " " << a / 2.0f << " " << -c / 2.0f << "\n";
	file << -l / 2.0f << " " << a / 2.0f << " " << c / 2.0f << "\n";

	//base
	file << l / 2.0f << " " << -a / 2.0f << " " << c / 2.0f << "\n";
	file << -l / 2.0f << " " << -a / 2.0f << " " << c / 2.0f << "\n";
	file << -l / 2.0f << " " << -a / 2.0f << " " << -c / 2.0f << "\n";

	file << -l / 2.0f << " " << -a / 2.0f << " " << -c / 2.0f << "\n";
	file << l / 2.0f << " " << -a / 2.0f << " " << -c / 2.0f << "\n";
	file << l / 2.0f << " " << -a / 2.0f << " " << c / 2.0f << "\n";

	//frente
	file << -l / 2.0f << " " << -a / 2.0f << " " << c / 2.0f << "\n";
	file << l / 2.0f << " " << -a / 2.0f << " " << c / 2.0f << "\n";
	file << l / 2.0f << " " << a / 2.0f << " " << c / 2.0f << "\n";

	file << l / 2.0f << " " << a / 2.0f << " " << c / 2.0f << "\n";
	file << -l / 2.0f << " " << a / 2.0f << " " << c / 2.0f << "\n";
	file << -l / 2.0f << " " << -a / 2.0f << " " << c / 2.0f << "\n";

	//atras
	file << -l / 2.0f << " " << a / 2.0f << " " << -c / 2.0f << "\n";
	file << l / 2.0f << " " << a / 2.0f << " " << -c / 2.0f << "\n";
	file << l / 2.0f << " " << -a / 2.0f << " " << -c / 2.0f << "\n";

	file << l / 2.0f << " " << -a / 2.0f << " " << -c / 2.0f << "\n";
	file << -l / 2.0f << " " << -a / 2.0f << " " << -c / 2.0f << "\n";
	file << -l / 2.0f << " " << a / 2.0f << " " << -c / 2.0f << "\n";

	//lado direito
	file << -l / 2.0f << " " << a / 2.0f << " " << -c / 2.0f << "\n";
	file << -l / 2.0f << " " << -a / 2.0f << " " << -c / 2.0f << "\n";
	file << -l / 2.0f << " " << a / 2.0f << " " << c / 2.0f << "\n";

	file << -l / 2.0f << " " << -a / 2.0f << " " << c / 2.0f << "\n";
	file << -l / 2.0f << " " << a / 2.0f << " " << c / 2.0f << "\n";
	file << -l / 2.0f << " " << -a / 2.0f << " " << -c / 2.0f << "\n";

	//lado esquerdo
	file << l / 2.0f << " " << -a / 2.0f << " " << -c / 2.0f << "\n";
	file << l / 2.0f << " " << a / 2.0f << " " << -c / 2.0f << "\n";
	file << l / 2.0f << " " << a / 2.0f << " " << c / 2.0f << "\n";

	file << l / 2.0f << " " << a / 2.0f << " " << c / 2.0f << "\n";
	file << l / 2.0f << " " << -a / 2.0f << " " << c / 2.0f << "\n";
	file << l / 2.0f << " " << -a / 2.0f << " " << -c / 2.0f << "\n";

	file.close();
}

void Paralelepipedo::draw()
{
	int n;

	
	applyTransforms();	

	n = tgls.size();
	for (int i = 0; i < n; i += 3){
		glBegin(GL_TRIANGLES);
		glColor3f(color.x, color.y, color.z);

		glVertex3f(tgls[i].x, tgls[i].y, tgls[i].z);
		glVertex3f(tgls[i + 1].x, tgls[i + 1].y, tgls[i + 1].z);
		glVertex3f(tgls[i + 2].x, tgls[i + 2].y, tgls[i + 2].z);
		glEnd();
	}
	
	glPopMatrix();
	
}
/*---------------------------------------------------------------------*/


/*---------------------- Class - Esfera -------------------------------*/
/*Função vísivel do exterior para gerar um ficheiro com triângulos que definem a esfera*/
void Esfera::gerarEsfera(float raio, int camadas, int fatias, string filename)
{
	r = raio; c = camadas; f = fatias;
	write3DtoFile(filename);
}

/*Função que internamente à classe gera o ficheiro os triângulos*/
void Esfera::write3DtoFile(string filename)
{
	ofstream file;
	file.open(filename);


	file << "ESFERA\n";
	int i, j;
	double passoH = (2 * M_PI) / f;
	double passoV = (M_PI) / c;
	double altura = r* sin((M_PI / 2) - passoV);
	double alturaCima = r;

	for (i = 0; i < f; i++) {

		double actualX = r*sin(i*passoH);
		double actualZ = r*cos(i*passoH);
		double nextX = r*sin((i + 1)*passoH);
		double nextZ = r*cos((i + 1)*passoH);
		double actX, actZ, nexX, nexZ, cimaActX, cimaActZ, cimaNexX, cimaNexZ;

		for (j = 1; j < c + 2; j++){


			double aux = cos(asin(altura / r));
			actX = actualX * aux;
			actZ = actualZ * aux;
			nexX = nextX * aux;
			nexZ = nextZ * aux;

			aux = cos(asin(alturaCima / r));
			cimaActX = actualX * aux;
			cimaActZ = actualZ * aux;
			cimaNexX = nextX * aux;
			cimaNexZ = nextZ * aux;

			file << actX << " " << altura << " " << actZ << "\n";
			file << cimaActX << " " << alturaCima << " " << cimaActZ << "\n";
			file << nexX << " " << altura << " " << nexZ << "\n";

			file << cimaActX << " " << alturaCima << " " << cimaActZ << "\n";
			file << cimaNexX << " " << alturaCima << " " << cimaNexZ << "\n";
			file << nexX << " " << altura << " " << nexZ << "\n";

			alturaCima = altura;
			altura = r* sin((M_PI / 2) - (passoV*j));

		}

		altura = r* sin((M_PI / 2) - passoV);
		alturaCima = r;

		actualX = nextX;
		actualZ = nextZ;

	}

	file.close();
}

void Esfera::draw()
{
	int n;

	
	applyTransforms();	

	n = tgls.size();
	for (int i = 0; i < n; i += 3){
		glBegin(GL_TRIANGLES);
		glColor3f(color.x, color.y, color.z);

		glVertex3f(tgls[i].x, tgls[i].y, tgls[i].z);
		glVertex3f(tgls[i + 1].x, tgls[i + 1].y, tgls[i + 1].z);
		glVertex3f(tgls[i + 2].x, tgls[i + 2].y, tgls[i + 2].z);
		glEnd();
	}
	
	glPopMatrix();
	
}
/*---------------------------------------------------------------------*/


/*------------------------ Class - Cone -------------------------------*/
/*Função vísivel do exterior para gerar um ficheiro com triângulos que definem a esfera*/
void Cone::gerarCone(float altura, float raio, int nlados, int ncamadas, string filename)
{
	this->raio = raio; this->altura = altura; this->ncamadas = ncamadas; this->nlados = nlados;
	write3DtoFile(filename);
}


void Cone::read3DfromFile(string filename)
{
	std::fstream file(filename, std::ios_base::in); // Nome do ficheiro supostamente temos de ir buscá-lo a um ficheiro .xml?
	bool flag = false;

	file >> nome;
	file >> altura;
	file >> raio;
	file >> nlados;
	file >> ncamadas;
	float x, y, z;
	while (file >> x >> y >> z){
		Ponto3D t;
		t.x = x; t.y = y; t.z = z;
		tgls.push_back(t);
	}
	file.close();
}

/*Função que internamente à classe gera o ficheiro os triângulos*/
void Cone::write3DtoFile(string filename)
{
	ofstream file;
	file.open(filename);

	int i;
	float incAngulo = (2 * M_PI) / (float)nlados;
	float incAltura = altura / (float)ncamadas;
	float incRaio = raio / (float)ncamadas;
	float alpha, h;
	h = 0; alpha = 2 * M_PI;

	file << "CONE\n";
	file << altura << "\n";
	file << raio << "\n";
	file << nlados << "\n";
	file << ncamadas << "\n";
	file << 0.0f << " " << 0.0f << " " << 0.0f << "\n";
	for (i = 0; i <= nlados; i++){
		file << raio*sinf(alpha) << " " << 0 << " " << raio*cosf(alpha) << "\n";
		alpha -= incAngulo;
	}

	alpha = 0;

	for (i = 0; i < ncamadas; i++){
		alpha = 0;
		file << 0 << " " << altura << " " << 0 << "\n";
		for (int j = 0; j <= nlados; j++) {
			file << raio*sinf(alpha) << " " << h << " " << raio*cosf(alpha) << "\n";
			alpha += incAngulo;
		}
		h += incAltura;
		raio = raio - incRaio;
	}

	file.close();
}


void Cone::draw()
{
	int n;
	int i;

	
	applyTransforms();	

	n = tgls.size();
	glBegin(GL_TRIANGLE_FAN);
	glColor3f(color.x, color.y, color.z);
	for (i = 0; i <= nlados+1; i++){
		glVertex3f(tgls[i].x, tgls[i].y, tgls[i].z);
	}
	glEnd();

	for (; i < n;){
		glBegin(GL_TRIANGLE_FAN);
		glColor3f(color.x, color.y, color.z);
		glVertex3f(tgls[i].x, tgls[i].y, tgls[i].z); i++;
		for(int j = 0; j <= nlados; j++) {
			glVertex3f(tgls[i].x, tgls[i].y, tgls[i].z); i++;
		}
		glEnd();
	}
	
	glPopMatrix();
	
}
/*---------------------------------------------------------------------*/