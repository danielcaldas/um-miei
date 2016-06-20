/*
 *	Autores: Daniel Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 *	Data: 2015.03.15
 *	Versão: 1.0v
 *
 */

#ifndef _formas_h_included_
#define _formas_h_included_

#include <vector>
#include <string>

using namespace std;

class Ponto3D {
 public:
	Ponto3D();
	float x, y, z;
	void print() const; // debug
	string tostring();
};

class Forma {
 protected:
	vector<Ponto3D> tgls;
	string nome;

 public:
	virtual ~Forma(){};
	virtual void read3DfromFile(string filename);
	virtual void draw()=0;
 protected:
	virtual void write3DtoFile(string filename)=0;
};

class Triangulo : public Forma {
 private:
	 float lado; // comprimento do lado

 public:
	Triangulo(){}
	void gerarTriangulo(float l, string filename);
	void draw();
private:
	void write3DtoFile(string filename);
};

class Circulo : public Forma {
 private:
	float raio;
	int nlados;

 public:
	Circulo(){}
	void gerarCirculo(float raio, int nlados, string filename);
	void draw();
private:
	void write3DtoFile(string filename);
};

class Rectangulo : public Forma {
 private:
	float l; // largura
	float c; // comprimento

 public:
	Rectangulo(){}
	void gerarRectangulo(float largura, float comprimento, string filename);
	void draw();
private:
	void write3DtoFile(string filename);
};

class Paralelepipedo : public Forma {
private:
	float l;
	float c;
	float a;

public:
	Paralelepipedo(){}
	void gerarParalelepipedo(float largura, float comprimento, float altura, string filename);
	void draw();
private:
	void write3DtoFile(string filename);
};

class Esfera : public Forma {
 private:
	float r; //r = raio
	int c;   //c = camadas
	int f;   //f = fatias

 public:
	Esfera(){}
	void gerarEsfera(float raio, int camadas, int fatias, string filename);
	void draw();
 private:
	void write3DtoFile(string filename);
};

class Cone : public Forma {
private:
	float altura;
	float raio;
	int nlados;
	int ncamadas;
public:
	Cone(){}
	void read3DfromFile(string filename) override;
	void gerarCone(float altura, float raio, int nlados, int ncamadas, string filename);
	void draw();
private:
	void write3DtoFile(string filename);
};

#endif