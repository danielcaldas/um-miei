
#ifndef Triangulo_h
#define Triangulo_h

#include "Formas.h"
#include <vector>
#include <string>
#include "GL/glew.h"
#include <GLUT/glut.h>

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

#endif
