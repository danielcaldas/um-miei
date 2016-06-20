
#include "Cone.h"
#include "const.h"

using namespace std;

void Cone::gerarCone(float altura, float raio, int nlados, int ncamadas, string filename) {
	this->raio = raio; this->altura = altura;
	this->ncamadas = ncamadas; this->nlados = nlados;
	write3DtoFile(filename);
}

void Cone::read3DfromFile(string filename) {
	std::fstream file(filename, std::ios_base::in); // Nome do ficheiro supostamente temos de ir busc·-lo a um ficheiro .xml?
	bool flag = false;
	
	file >> nome;
	file >> altura;
	file >> raio;
	file >> nlados;
	file >> ncamadas;
	float x, y, z;
	while (file >> x >> y >> z){
		Ponto3D t;
		t.x = x; t.y = y; t.z = z;
		tgls.push_back(t);
	}
	file.close();
}

void Cone::draw() {
	int n;
	int i;
	
	applyTransforms();
	
	n = tgls.size();
	glBegin(GL_TRIANGLE_FAN);
	glColor3f(color.x, color.y, color.z);
	for (i = 0; i <= nlados+1; i++){
		glVertex3f(tgls[i].x, tgls[i].y, tgls[i].z);
	}
	glEnd();
	
	for (; i < n;){
		glBegin(GL_TRIANGLE_FAN);
		glColor3f(color.x, color.y, color.z);
		glVertex3f(tgls[i].x, tgls[i].y, tgls[i].z); i++;
		for(int j = 0; j <= nlados; j++) {
			glVertex3f(tgls[i].x, tgls[i].y, tgls[i].z); i++;
		}
		glEnd();
	}
	
	glPopMatrix();
}

void Cone::write3DtoFile(string filename) {
	ncamadas++;
	nlados++;
	
	int arraySize = (3 * ((nlados + ncamadas)*ncamadas))*sizeof(float);
	int tSize = (2 * ((nlados + ncamadas)*ncamadas))*sizeof(float);

	float* aVertex = (float*)malloc(arraySize);
	float* aNormal = (float*) malloc(arraySize);
	float* aTexture = (float*) malloc(tSize);
	
	int index = 6 * ((nlados + ncamadas)*ncamadas);
	aIndex = (int*)malloc(sizeof(int)*index);
	
	float ang = 0.0f;
	float ang_inc = 2 * M_PI / ((float)(ncamadas - 1));
	float r_inc = raio / ((float)(ncamadas - 1));
	float fr_inc = raio / ((float)(nlados - 1));
	float alt_dec = altura / ((float)(nlados - 1));
	
	int tpos = 0;
	int pos = 0;
	
	for (int i = 0; i<ncamadas; i++){
		float fr = 0.0;
		float alt = altura;
		for (int f = 0; f<nlados; f++){
			fr = fr_inc*((float)f);
			alt = alt_dec*((float)((nlados - 1) - f));
			
			aVertex[pos] = fr*sinf(ang);
			aNormal[pos++] = sinf(ang);
			aVertex[pos] = alt;
			aNormal[pos++] = 0;
			aVertex[pos] = fr*cosf(ang);
			aNormal[pos++] = cosf(ang);
			
			aTexture[tpos++] = (float)i/(float)ncamadas ;
			aTexture[tpos++] = (float)f/(float)nlados ;
		}
		ang += ang_inc;
	}
	ang = 0.0f;
	for (int i = 0; i<ncamadas; i++){
		float r = 0;
		for (int ri = 0; ri<ncamadas; ri++){
			r = r_inc*((float)ri);
			
			aVertex[pos] = r*sinf(ang);
			aNormal[pos++] = sinf(ang);
			aVertex[pos] = 0;
			aNormal[pos++] = -1;
			aVertex[pos] = r*cosf(ang);
			aNormal[pos++] = cosf(ang);
			
			aTexture[tpos++] = 1-(sinf(ang)*((r/raio)*0.5) + 0.5) ;
			aTexture[tpos++] = cosf(ang)*((r/raio)*0.5) + 0.5 ;
		}
		ang += ang_inc;
	}
	
	pos = 0;
	ang = 0.0f;
	for (int i = 0; i<ncamadas - 1; i++){
		for (int f = 0; f<nlados - 1; f++){
			aIndex[pos++] = f + (nlados*i);
			aIndex[pos++] = (f + 1) + (nlados*i);
			aIndex[pos++] = (f + 1) + (nlados*(i + 1));
			
			aIndex[pos++] = f + (nlados*i);
			aIndex[pos++] = (f + 1) + (nlados*(i + 1));
			aIndex[pos++] = f + (nlados*(i + 1));
		}
	}
	int inc = ncamadas*nlados;

	for (int i = 0; i<ncamadas - 1; i++){
		for (int ri = 0; ri<ncamadas - 1; ri++){
			aIndex[pos++] = ri + (ncamadas*i) + inc;
			aIndex[pos++] = (ri + 1) + (ncamadas*(i + 1)) + inc;
			aIndex[pos++] = (ri + 1) + (ncamadas*i) + inc;
			
			aIndex[pos++] = ri + (ncamadas*i) + inc;
			aIndex[pos++] = ri + (ncamadas*(i + 1)) + inc;
			aIndex[pos++] = (ri + 1) + (ncamadas*(i + 1)) + inc;
		}
	}
	
	ofstream file;
	file.open(filename);
	
	file << "CONE\n";
	file << arraySize << "\n";
	for (int i = 0; i < arraySize; i += 3)
		file << aVertex[i] << " " << aVertex[i + 1] << " " << aVertex[i + 2] << " \n";
	
	file << arraySize << "\n";
	for (int i = 0; i < arraySize; i += 3)
		file << aNormal[i] << " " << aNormal[i + 1] << " " << aNormal[i + 2] << " \n";
	
	file << tSize << "\n";
	for (int i = 0; i < tSize; i += 2)
		file << aTexture[i] << " " << aTexture[i + 1] << " \n";
	
	file << index << "\n";
	for (int i = 0; i < index; i += 3){
		file << aIndex[i] << " " << aIndex[i + 1] << " " << aIndex[i + 2] << " \n";
	}
	file.close();
	
	free(aVertex);
	free(aNormal);
	free(aTexture);
	free(aIndex);
}

void Cone::criarVBO(string filename) {
	std::fstream file(filename, std::ios_base::in);
	
	float x, y, z;
	int i = 0;
	int indice = 0;
	int sizeV, sizeN, sizeT;
	float* vertexB, *vertexN, *vertexT;
	
	glEnableClientState(GL_VERTEX_ARRAY);
	glEnableClientState(GL_NORMAL_ARRAY);
	glEnableClientState(GL_TEXTURE_COORD_ARRAY);

	file >> nome;
	
	file >> sizeV;
	vertexB = (float*)malloc((sizeV)* sizeof(float));
	while ( i < sizeV) {
		file >> x >> y >> z;
		vertexB[indice++] = x;
		vertexB[indice++] = y;
		vertexB[indice++] = z;
		i += 3;
	}
	
	file >> sizeN;
	vertexN = (float*)malloc((sizeN)* sizeof(float));
	i = indice = 0;
	while ( i < sizeN) {
		file >> x >> y >> z;
		vertexN[indice++] = x;
		vertexN[indice++] = y;
		vertexN[indice++] = z;
		i += 3;
	}
	
	file >> sizeT;
	vertexT = (float*)malloc((sizeT)* sizeof(float));
	i = indice = 0;
	while ( i < sizeT) {
		file >> x >> y;
		vertexT[indice++] = x;
		vertexT[indice++] = y;
		i += 2;
	}
	
	i = indice = 0;
	file >> vertexCount;
	aIndex = (int*)malloc(sizeof(int)*vertexCount);
	while (i < vertexCount) {
		file >> x >> y >> z;
		aIndex[indice++] = x;
		aIndex[indice++] = y;
		aIndex[indice++] = z;
		i += 3;
	}
	file.close();
	
	glGenBuffers(3, buffers);
	glBindBuffer(GL_ARRAY_BUFFER,buffers[0]);
	glBufferData(GL_ARRAY_BUFFER, sizeV, vertexB, GL_STATIC_DRAW);
	
	glBindBuffer(GL_ARRAY_BUFFER,buffers[1]);
	glBufferData(GL_ARRAY_BUFFER, sizeN, vertexN, GL_STATIC_DRAW);
	
	glBindBuffer(GL_ARRAY_BUFFER,buffers[2]);
	glBufferData(GL_ARRAY_BUFFER, sizeT, vertexT, GL_STATIC_DRAW);
	
	free(vertexB);
	free(vertexN);
	free(vertexT);
}

void Cone::drawVBO() {
	applyTransforms();
	//glColor3f(color.x, color.y, color.z);
	
	glBindBuffer(GL_ARRAY_BUFFER,buffers[0]);
	glVertexPointer(3,GL_FLOAT,0,0);
	glBindBuffer(GL_ARRAY_BUFFER,buffers[1]);
	glNormalPointer(GL_FLOAT,0,0);
	glBindBuffer(GL_ARRAY_BUFFER,buffers[2]);
	glTexCoordPointer(2,GL_FLOAT,0,0);
	
	/* Bind da textura */
	glBindTexture(GL_TEXTURE_2D, textura) ;
	glDrawElements(GL_TRIANGLES, vertexCount ,GL_UNSIGNED_INT, aIndex);
	/* Unbind da textura */
	glBindTexture(GL_TEXTURE_2D, 0);
	
	glPopMatrix();
}