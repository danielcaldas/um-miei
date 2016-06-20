
#ifndef Circulo_h
#define Circulo_h

#include "const.h"
#include "Formas.h"

class Circulo : public Forma {
	private:
		float raio;
		int nlados;
		
	public:
		Circulo(){}
		void gerarCirculo(float raio, int nlados, string filename);
		void draw();
		virtual void drawVBO() override;
	private:
		void write3DtoFile(string filename);
};

#endif
