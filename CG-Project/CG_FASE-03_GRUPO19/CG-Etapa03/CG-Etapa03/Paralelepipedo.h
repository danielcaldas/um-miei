//
//  Etapa 4
//
//  Created by Marcelo Gonçalves on 18/05/15.
//  Copyright (c) 2015 Marcelo Gonçalves. All rights reserved.
//

#ifndef Paralelepipedo_h
#define Paralelepipedo_h

#include "Formas.h"
#include <vector>
#include <string>
#include "GL/glew.h"
#include <GLUT/glut.h>

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

#endif
