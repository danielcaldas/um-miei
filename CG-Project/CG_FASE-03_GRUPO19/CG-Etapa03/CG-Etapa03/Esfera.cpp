
#include "Esfera.h"
#include "const.h"

using namespace std;

void Esfera::gerarEsfera(float raio, int nfatias, int ncamadas, string filename) {
	r = raio; c = ncamadas; f = nfatias;
	write3DtoFile(filename);
}

/*FunÁ„o que internamente ‡ classe gera o ficheiro os tri‚ngulos*/
void Esfera::write3DtoFile(string filename) {
	
	int arrayS = 3*(c*c*6)*sizeof(float);
	int tSize = 2*(c*c*6)*sizeof(float);
	
	float* aVertex = (float*)malloc(arrayS);
	float* aNormal = (float*)malloc(arrayS);
	float* aTexture = (float*)malloc(tSize);
	
	int index = 6 * ((f + c)*c);
	aIndex = (int*)malloc(sizeof(int)*index);
	
	double divH = (2*M_PI)/f;
	double divV = (M_PI)/c;
	float angv, angh;
	
	int pos = 0;
	int tpos = 0;
	for(int i=0; i<=c;i++){
		angv=divV*i;
		for(int j=0; j<=f;j++){
			angh=divH*j;
			
			aVertex[pos]=r*sin(angv)*sin(angh);
			aNormal[pos++]=sin(angv)*sin(angh);
			
			aVertex[pos]=r*cos(angv);
			aNormal[pos++]=cos(angv);
			
			aVertex[pos]=r*sin(angv)*cos(angh);
			aNormal[pos++]=sin(angv)*cos(angh);
			
			aTexture[tpos++] = 1+((float)j/(float)(f+1)) ;
			aTexture[tpos++] = (float)i/(float)(c+1) ;
		}
	}
	
	pos=0;
	for(int i=0; i<c;i++){
		for(int j=0; j<f;j++){
			aIndex[pos++]=j+(i*(f+1));
			aIndex[pos++]=j+((i+1)*(f+1));
			aIndex[pos++]=(j+1)+(i*(f+1));
			
			aIndex[pos++]=(j+1)+(i*(f+1));
			aIndex[pos++]=j+((i+1)*(f+1));
			aIndex[pos++]=(j+1)+((i+1)*(f+1));
		}
	}
	
	ofstream file;
	file.open(filename);
	
	file << "Esfera\n";
	file << arrayS << "\n";
	for (int i = 0; i < arrayS; i += 3)
		file << aVertex[i] << " " << aVertex[i + 1] << " " << aVertex[i + 2] << " \n";
	
	file << arrayS << "\n";
	for (int i = 0; i < arrayS; i += 3)
		file << aNormal[i] << " " << aNormal[i + 1] << " " << aNormal[i + 2] << " \n";
	
	file << tSize << "\n";
	for (int i = 0; i < tSize; i += 2)
		file << aTexture[i] << " " << aTexture[i + 1] << " \n";
	
	file << index << "\n";
	for (int i = 0; i < index; i += 3){
		file << aIndex[i] << " " << aIndex[i + 1] << " " << aIndex[i + 2] << " \n";
	}
	
	free(aVertex); free(aNormal);
	free(aTexture); free(aIndex);
	
	file.close();
}

void Esfera::draw() {
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

void Esfera::criarVBO(string filename) {
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
	
	free(vertexB); free(vertexN); free(vertexT);
}

void Esfera::drawVBO() {
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
	glBindTexture(GL_TEXTURE_2D, 0) ;
	
	glPopMatrix();
}

