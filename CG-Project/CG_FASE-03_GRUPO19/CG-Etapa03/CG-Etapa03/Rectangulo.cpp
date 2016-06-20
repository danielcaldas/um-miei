#include "Rectangulo.h"
#include "const.h"

using namespace std;

void Rectangulo::gerarRectangulo(float largura, float comprimento, string filename){
	l = largura;
	c = comprimento;
	write3DtoFile(filename);
}

/*FunÁ„o que internamente ‡ classe gera o ficheiro os tri‚ngulos*/
void Rectangulo::write3DtoFile(string filename) {
	std::ofstream file;
	file.open(filename);
	
	//Imprimir os vertices, indices, normais e coordenadas de textura
	
	file << "RECTANGULO" << "\n";
	file << 36 << "\n";
	
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
	
	file << 36 << "\n";
	
	file << 0 << " " << 1 << " " << 0 << "\n";
	file << 0 << " " << 1 << " " << 0 << "\n";
	file << 0 << " " << 1 << " " << 0 << "\n";
	
	file << 0 << " " << 1 << " " << 0 << "\n";
	file << 0 << " " << 1 << " " << 0 << "\n";
	file << 0 << " " << 1 << " " << 0 << "\n";
	
	file << 0 << " " << -1 << " " << 0 << "\n";
	file << 0 << " " << -1 << " " << 0 << "\n";
	file << 0 << " " << -1 << " " << 0 << "\n";
	
	file << 0 << " " << -1 << " " << 0 << "\n";
	file << 0 << " " << -1 << " " << 0 << "\n";
	file << 0 << " " << -1 << " " << 0 << "\n";
	
	file << 24 << "\n";
	
	file << 0 << " " << 1 << "\n";
	file << 1 << " " << 0 << "\n";
	file << 0 << " " << 0 << "\n";
	
	file << 0 << " " << 1 << "\n";
	file << 1 << " " << 1 << "\n";
	file << 1 << " " << 0 << "\n";
	
	file << 1 << " " << 0 << "\n";
	file << 0 << " " << 1 << "\n";
	file << 1 << " " << 1 << "\n";
	
	file << 1 << " " << 0 << "\n";
	file << 0 << " " << 0 << "\n";
	file << 0 << " " << 1 << "\n";
	
	file.close();

}

/*Função que desenhar um conjunto de triângulos armazenados de um modo sequencial */
void Rectangulo::draw() {
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