
#ifndef Cone_h
#define Cone_h

#include "Formas.h"
#include <vector>
#include <string>
#include "GL/glew.h"
#include <GLUT/glut.h>

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
