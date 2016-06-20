/*
*	Autores: Daniel Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
*	Data: 2015.05.29
*	Versão: 4.0v
*
*/

#include "circulo.h"
#include "const.h"

using namespace std;

void Circulo::gerarCirculo(float r, int l, string filename) {
	raio = r; nlados = l;
	write3DtoFile(filename);
}

void Circulo::write3DtoFile(string filename) {
	float alpha = 2 * M_PI;
	float decAngulo = (2 * M_PI) / nlados;

	int size = (6 + 2 * (nlados + 1) * 3);
	int tsize = (4 + 2 * (nlados + 1) * 2);
	int v, t, n;
	v = t = n = 0;
	float texF = 1.0f / nlados;

	float *vertexB = (float*)malloc(size*sizeof(float));
	float *normalB = (float*)malloc(size*sizeof(float));
	float *texB = (float*)malloc(tsize*sizeof(float));

	vertexB[v++] = 0;
	vertexB[v++] = 0;
	vertexB[v++] = 0;

	normalB[n++] = 0;
	normalB[n++] = -1;
	normalB[n++] = 0;

	texB[t++] = 1;
	texB[t++] = 0;

	for (int i = 0; i <= nlados; i++){
		vertexB[v++] = raio*sinf(alpha);
		vertexB[v++] = 0;
		vertexB[v++] = raio*cosf(alpha);

		normalB[n++] = 0;
		normalB[n++] = -1;
		normalB[n++] = 0;

		texB[t++] = i*texF;
		texB[t++] = 1;

		alpha -= decAngulo;
	}

	vertexB[v++] = 0;
	vertexB[v++] = 0;
	vertexB[v++] = 0;

	normalB[n++] = 0;
	normalB[n++] = 1;
	normalB[n++] = 0;

	texB[t++] = 1;
	texB[t++] = 0;

	for (int i = 0; i <= nlados; i++){

		vertexB[v++] = raio*cosf(alpha);
		vertexB[v++] = 0;
		vertexB[v++] = raio*sinf(alpha);

		normalB[n++] = 0;
		normalB[n++] = 1;
		normalB[n++] = 0;

		texB[t++] = i*texF;
		texB[t++] = 1;

		alpha -= decAngulo;
	}

	ofstream file;
	file.open(filename);
	int i;
	file << "CIRCULO\n";
	file << size << "\n";
	for (i = 0; i < size; i += 3)
		file << vertexB[i] << " " << vertexB[i + 1] << " " << vertexB[i + 2] << " \n";

	file << size << "\n";
	for (i = 0; i < size; i += 3)
		file << normalB[i] << " " << normalB[i + 1] << " " << normalB[i + 2] << " \n";

	file << tsize << "\n";
	for (i = 0; i < tsize; i += 2)
		file << texB[i] << " " << texB[i + 1] << " \n";

	file.close();

	free(vertexB);
	free(normalB);
	free(texB);
}

void Circulo::draw() {
	int i, n;

	applyTransforms();

	n = tgls.size();
	glBegin(GL_TRIANGLE_FAN);
	glColor3f(color.x, color.y, color.z);
	for (i = 0; i <= n; i++){
		glVertex3f(tgls[i].x, tgls[i].y, tgls[i].z);
	}
	glEnd();

	glPopMatrix();

}

void Circulo::criarVBO(string filename)
{
	std::fstream file(filename, std::ios_base::in); // Nome do ficheiro supostamente temos de ir buscá-lo a um ficheiro .xml?

	int size;
	file >> size;
	cout << size;
	float* vertexB;

	vertexB = (float*)malloc((size)* sizeof(float));
	int indice = 0;
	glEnableClientState(GL_VERTEX_ARRAY);
	float x, y, z;
	while (file >> x >> y >> z) {
		vertexB[indice++] = x;
		vertexB[indice++] = y;
		vertexB[indice++] = z;
	}
	file.close();
	vertexCount = size;

	glGenBuffers(1, buffers);
	glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);
	glBufferData(GL_ARRAY_BUFFER, sizeof(float) * size, vertexB, GL_STATIC_DRAW);

	free(vertexB);
}

void Circulo::drawVBO() {
	applyTransforms();

	if (textfich.compare("EMPTY") == 0){
		glColor3f(color.x, color.y, color.z);
	}

	glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);
	glVertexPointer(3, GL_FLOAT, 0, 0);
	glBindBuffer(GL_ARRAY_BUFFER, buffers[1]);
	glNormalPointer(GL_FLOAT, 0, 0);
	glBindBuffer(GL_ARRAY_BUFFER, buffers[2]);
	glTexCoordPointer(2, GL_FLOAT, 0, 0);

	/* Bind da textura */
	glBindTexture(GL_TEXTURE_2D, textura);
	glDrawArrays(GL_TRIANGLE_FAN, 0, vertexCount);
	/* Unbind da textura */
	glBindTexture(GL_TEXTURE_2D, 0);

	glPopMatrix();
}
