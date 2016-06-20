//
//  Etapa 4
//
//  Created by Marcelo Gonçalves on 18/05/15.
//  Copyright (c) 2015 Marcelo Gonçalves. All rights reserved.
//

#ifndef Plano_h
#define Plano_h

#include "Formas.h"
#include <vector>
#include <string>
#include "GL/glew.h"
#include <GLUT/glut.h>

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

#endif
