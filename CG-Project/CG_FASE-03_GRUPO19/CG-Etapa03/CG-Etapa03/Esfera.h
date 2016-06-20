

#ifndef Esfera_h
#define Esfera_h

#include "Formas.h"
#include <vector>
#include <string>
#include "GL/glew.h"
#include <GLUT/glut.h>

class Esfera : public Forma {
	private:
		float r; //r = raio
		int c;   //c = camadas
		int f;   //f = fatias
		
	public:
		Esfera(){}
		void gerarEsfera(float raio, int camadas, int fatias, string filename);
		void draw();
		void drawVBO() override;
		void criarVBO(string filename) override;
	
	private:
		void write3DtoFile(string filename);
};

#endif
