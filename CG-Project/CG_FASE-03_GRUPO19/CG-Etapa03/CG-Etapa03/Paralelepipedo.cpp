
#include "Paralelepipedo.h"
#include "const.h"

using namespace std;

void Paralelepipedo::gerarParalelepipedo(float largura, float comprimento, float altura, string filename) {
	l = largura; c = comprimento; a = altura;
	write3DtoFile(filename);
}

/*FunÁ„o que internamente ‡ classe gera o ficheiro os tri‚ngulos*/
void Paralelepipedo::write3DtoFile(string filename) {
	ofstream file;
	file.open(filename);
	
	file << "PARALELEPIPEDO\n";
	file << "108\n";
	
	//topo
	file << -l / 2.0f << " " << a / 2.0f << " " << c / 2.0f << "\n";
	file << l / 2.0f << " " << a / 2.0f << " " << c / 2.0f << "\n";
	file << l / 2.0f << " " << a / 2.0f << " " << -c / 2.0f << "\n";
	
	file << l / 2.0f << " " << a / 2.0f << " " << -c / 2.0f << "\n";
	file << -l / 2.0f << " " << a / 2.0f << " " << -c / 2.0f << "\n";
	file << -l / 2.0f << " " << a / 2.0f << " " << c / 2.0f << "\n";
	
	//base
	file << l / 2.0f << " " << -a / 2.0f << " " << c / 2.0f << "\n";
	file << -l / 2.0f << " " << -a / 2.0f << " " << c / 2.0f << "\n";
	file << -l / 2.0f << " " << -a / 2.0f << " " << -c / 2.0f << "\n";
	
	file << -l / 2.0f << " " << -a / 2.0f << " " << -c / 2.0f << "\n";
	file << l / 2.0f << " " << -a / 2.0f << " " << -c / 2.0f << "\n";
	file << l / 2.0f << " " << -a / 2.0f << " " << c / 2.0f << "\n";
	
	//frente
	file << -l / 2.0f << " " << -a / 2.0f << " " << c / 2.0f << "\n";
	file << l / 2.0f << " " << -a / 2.0f << " " << c / 2.0f << "\n";
	file << l / 2.0f << " " << a / 2.0f << " " << c / 2.0f << "\n";
	
	file << l / 2.0f << " " << a / 2.0f << " " << c / 2.0f << "\n";
	file << -l / 2.0f << " " << a / 2.0f << " " << c / 2.0f << "\n";
	file << -l / 2.0f << " " << -a / 2.0f << " " << c / 2.0f << "\n";
	
	//atras
	file << -l / 2.0f << " " << a / 2.0f << " " << -c / 2.0f << "\n";
	file << l / 2.0f << " " << a / 2.0f << " " << -c / 2.0f << "\n";
	file << l / 2.0f << " " << -a / 2.0f << " " << -c / 2.0f << "\n";
	
	file << l / 2.0f << " " << -a / 2.0f << " " << -c / 2.0f << "\n";
	file << -l / 2.0f << " " << -a / 2.0f << " " << -c / 2.0f << "\n";
	file << -l / 2.0f << " " << a / 2.0f << " " << -c / 2.0f << "\n";
	
	//lado direito
	file << -l / 2.0f << " " << a / 2.0f << " " << -c / 2.0f << "\n";
	file << -l / 2.0f << " " << -a / 2.0f << " " << -c / 2.0f << "\n";
	file << -l / 2.0f << " " << a / 2.0f << " " << c / 2.0f << "\n";
	
	file << -l / 2.0f << " " << -a / 2.0f << " " << c / 2.0f << "\n";
	file << -l / 2.0f << " " << a / 2.0f << " " << c / 2.0f << "\n";
	file << -l / 2.0f << " " << -a / 2.0f << " " << -c / 2.0f << "\n";
	
	//lado esquerdo
	file << l / 2.0f << " " << -a / 2.0f << " " << -c / 2.0f << "\n";
	file << l / 2.0f << " " << a / 2.0f << " " << -c / 2.0f << "\n";
	file << l / 2.0f << " " << a / 2.0f << " " << c / 2.0f << "\n";
	
	file << l / 2.0f << " " << a / 2.0f << " " << c / 2.0f << "\n";
	file << l / 2.0f << " " << -a / 2.0f << " " << c / 2.0f << "\n";
	file << l / 2.0f << " " << -a / 2.0f << " " << -c / 2.0f << "\n";
	
	
	//Normais
	file << "108\n";
	
	//topo
	file << 0 << " " << 1 << " " << 0 << "\n";
	file << 0 << " " << 1 << " " << 0 << "\n";
	file << 0 << " " << 1 << " " << 0 << "\n";
	
	file << 0 << " " << 1 << " " << 0 << "\n";
	file << 0 << " " << 1 << " " << 0 << "\n";
	file << 0 << " " << 1 << " " << 0 << "\n";
	
	//base
	file << 0 << " " << -1 << " " << 0 << "\n";
	file << 0 << " " << -1 << " " << 0 << "\n";
	file << 0 << " " << -1 << " " << 0 << "\n";
	
	file << 0 << " " << -1 << " " << 0 << "\n";
	file << 0 << " " << -1 << " " << 0 << "\n";
	file << 0 << " " << -1 << " " << 0 << "\n";
	
	//frente
	file << 0 << " " << 0 << " " << 1 << "\n";
	file << 0 << " " << 0 << " " << 1 << "\n";
	file << 0 << " " << 0 << " " << 1 << "\n";
	
	file << 0 << " " << 0 << " " << 1 << "\n";
	file << 0 << " " << 0 << " " << 1 << "\n";
	file << 0 << " " << 0 << " " << 1 << "\n";
	
	//atras
	file << 0 << " " << 0 << " " << -1 << "\n";
	file << 0 << " " << 0 << " " << -1 << "\n";
	file << 0 << " " << 0 << " " << -1 << "\n";
	
	file << 0 << " " << 0 << " " << -1 << "\n";
	file << 0 << " " << 0 << " " << -1 << "\n";
	file << 0 << " " << 0 << " " << -1 << "\n";
	
	//lado direito
	file << -1 << " " << 0 << " " << 0 << "\n";
	file << -1 << " " << 0 << " " << 0 << "\n";
	file << -1 << " " << 0 << " " << 0 << "\n";
	
	file << -1 << " " << 0 << " " << 0 << "\n";
	file << -1 << " " << 0 << " " << 0 << "\n";
	file << -1 << " " << 0 << " " << 0 << "\n";
	
	//lado esquerdo
	file << 1 << " " << 0 << " " << 0 << "\n";
	file << 1 << " " << 0 << " " << 0 << "\n";
	file << 1 << " " << 0 << " " << 0 << "\n";
	
	file << 1 << " " << 0 << " " << 0 << "\n";
	file << 1 << " " << 0 << " " << 0 << "\n";
	file << 1 << " " << 0 << " " << 0 << "\n";
	
	//Textura
	file << "72\n";
	
	//topo
	file << 0 << " " << 1 << "\n";
	file << 1 << " " << 1 << "\n";
	file << 1 << " " << 0 << "\n";
	
	file << 1 << " " << 0 << "\n";
	file << 0 << " " << 0 << "\n";
	file << 0 << " " << 1 << "\n";
	
	//base
	file << 1 << " " << 0 << "\n";
	file << 0 << " " << 0 << "\n";
	file << 0 << " " << 1 << "\n";
	
	file << 0 << " " << 1 << "\n";
	file << 1 << " " << 1 << "\n";
	file << 1 << " " << 0 << "\n";
	
	//frente
	file << 0 << " " << 1 << "\n";
	file << 1 << " " << 1 << "\n";
	file << 1 << " " << 0 << "\n";
	
	file << 1 << " " << 0 << "\n";
	file << 0 << " " << 0 << "\n";
	file << 0 << " " << 1 << "\n";
	
	//atras
	file << 0 << " " << 1 << "\n";
	file << 1 << " " << 1 << "\n";
	file << 1 << " " << 0 << "\n";
	
	file << 1 << " " << 0 << "\n";
	file << 0 << " " << 0 << "\n";
	file << 0 << " " << 1 << "\n";
	
	//lado direito
	file << 0 << " " << 0 << "\n";
	file << 0 << " " << 1 << "\n";
	file << 1 << " " << 0 << "\n";
	
	file << 1 << " " << 1 << "\n";
	file << 1 << " " << 0 << "\n";
	file << 0 << " " << 1 << "\n";
	
	//lado esquerdo
	file << 1 << " " << 1 << "\n";
	file << 1 << " " << 0 << "\n";
	file << 0 << " " << 0 << "\n";
	
	file << 0 << " " << 0 << "\n";
	file << 0 << " " << 1 << "\n";
	file << 1 << " " << 1 << "\n";
	
	file.close();
}

void Paralelepipedo::draw() {
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