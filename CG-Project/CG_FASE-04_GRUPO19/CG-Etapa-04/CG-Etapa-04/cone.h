/*
*	Autores: Daniel Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
*	Data: 2015.05.29
*	Versão: 4.0v
*
*/

#ifndef _cone_h
#define _cone_h

#include "formas.h"
#include <vector>
#include <string>
#include <GL/glew.h>
#include <GL/glut.h>

class Cone : public Forma {
private:
	float altura;
	float raio;
	int nlados;
	int ncamadas;
	int *aIndex;
public:
	Cone(){}
	void read3DfromFile(string filename) override;
	void gerarCone(float altura, float raio, int nlados, int ncamadas, string filename);
	void draw();
	void drawVBO() override;
	void criarVBO(string filename) override;
private:
	void write3DtoFile(string filename);
};

#endif
