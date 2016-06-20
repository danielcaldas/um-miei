/*
 *	Autores: Daniel Caldas, JosÈ Cortez, Marcelo GonÁalves, Ricardo Silva
 *	Data: 2015.05.04
 *	Vers„o: 3.0v
 *
 */

#ifndef _formas_h_included_
#define _formas_h_included_

#include "const.h"
#include <vector>
#include <string>

using namespace std;

void init(int nformas);

class Ponto3D {
public:
	Ponto3D();
	Ponto3D(float xx, float yy, float zz)
	{
	x = xx; y = yy; z = zz;
	}
	float x, y, z;
	void printP() const; // debug
	string tostring();
};

// Uma rotação 3D È uma extenão de um ponto 3D com o acrÈscimo de um ângulo
class Rotacao3D : public Ponto3D {
public:
	Rotacao3D();
	float tempo; // Tempo para se complete a rotação (em segundos)
	float ang; // ângulo
	float timebase;
	void printR() const; // debug
	void rotate();
};

class Translacao3D {
public:
	Ponto3D first; // Valores para efetuar primeiro translate
	float tempo; // Tempo que leva a ser completada a translaÁ„o (em segundos)
	float b; // Ponto da curva
	float timebase;
	vector<Ponto3D> pontos; // Pontos que definem a curva da translaÁ„o
	void printT();
	void translate();
};

// Um objecto TransformsWrapper representa opcionalmente
class TransformsWrapper {
public:
	string nome;
	Ponto3D escala;
	Translacao3D translacao;
	Rotacao3D rotacao;
	void printTW() {
		if (nome=="SCALE"){
			escala.printP();
		}
		else if (nome=="ROTATE"){
			rotacao.printR();
		}
		else if (nome=="TRANSLATE"){
			translacao.printT();
		}
	}
};

class Grupo {
public:
	Grupo(){};
	Grupo(vector<TransformsWrapper> fromfather){ transfs = fromfather; }
	int id; // noÁ„o de profundidade no ficheiro
	vector<TransformsWrapper> transfs;
};

class Forma {
protected:
	vector<Ponto3D> tgls;
	string nome;
	vector<TransformsWrapper> transforms; // RotaÁıes e TranslaÁıes
	
public:
	GLuint buffers[3];
	GLuint vertexCount;
	int *aIndex;
	unsigned int textura ;
	string astro;
	
public:
	Ponto3D color; // Cor da forma
	virtual ~Forma(){};
	virtual void read3DfromFile(string filename);
	virtual void draw()=0;
	void setTransformacoes(vector<TransformsWrapper> ts);
	void applyTransforms();
	void printAllTransforms();
	void desenhar_curva();
	virtual void criarVBO(string filename);
	virtual void drawVBO();
	void carregarImagem();
	
protected:
	virtual void write3DtoFile(string filename)=0;
};

#endif