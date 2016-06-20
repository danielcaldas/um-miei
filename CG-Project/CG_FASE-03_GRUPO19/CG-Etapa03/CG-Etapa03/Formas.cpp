/*
 *	Autores: Daniel Caldas, JosÈ Cortez, Marcelo GonÁalves, Ricardo Silva
 *	Data: 2015.05.04
 *	Versão: 3.0v
 *
 */

#include "Formas.h"
#include "const.h"

using namespace std;

# define M_PI 3.14159265358979323846 /*Pi*/

// Vari·veis globais para armazenamento de tempos e ‚ngulos relativos a animaÁıes
int N; // n˙mero total de figuras a desenhar numa cena

float *global_b; // Array de tempos globais para cada figura
int global_index; // Õndice da figura atual que se est· a desenhar

float *global_timeb;
float *global_timer;
float *global_ang;

/**FunÁıes que cada sÛlido herda diretamente da super classe sÛlido
 ou implementa uma vers„o s:
 
 - write3DtoFile(string filename): gera pontos automaticamente armazenando-os diretamente num ficheiro;
 
 - read3DfromFile(string filename): lÍ um conjunto de pontos previamente gerado pela aplicaÁ„o armazenando-os
 na estrutura de dados adequada;
 
 - draw(): mÈtodo da forma implementado em conformidade que È usado para desenhar a cena no ciclo glut.
 */

/*------------ FunÁıes auxiliares para desenhar Catmull-Rom Curves ---------------*/

void getCatmullRomPoint(float t, int *indices, float *res, float p[][3]) {
	int i;
	float res_aux[4];
	// catmull-rom matrix
	float m[4][4] = { { -0.5f, 1.5f, -1.5f, 0.5f },
		{ 1.0f, -2.5f, 2.0f, -0.5f },
		{ -0.5f, 0.0f, 0.5f, 0.0f },
		{ 0.0f, 1.0f, 0.0f, 0.0f } };
	
	// Calcular o ponto res = T * M * P
	// sendo Pi = p[indices[i]]
	
	//Sem derivada
	for (i = 0; i<4; i++)
		res_aux[i] = pow(t, 3) * m[0][i] + pow(t, 2) * m[1][i] + t * m[2][i] + m[3][i];
	
	//Calculo do RES
	for (i = 0; i<3; i++){
		res[i] = res_aux[0] * p[indices[0]][i] + res_aux[1] * p[indices[1]][i] + res_aux[2] * p[indices[2]][i] + res_aux[3] * p[indices[3]][i];
	}
}

// Dado o t global, retorna o ponto na curva
void getGlobalCatmullRomPoint(float gt, float *res, float p[][3], int pc) {
	
	float t = gt * pc; // this is the real global t
	int index = floor(t);  // which segment
	t = t - index; // where within  the segment
	
	// indices store the points
	int indices[4];
	indices[0] = (index + pc - 1) % pc;	indices[1] = (indices[0] + 1) % pc;
	indices[2] = (indices[1] + 1) % pc; indices[3] = (indices[2] + 1) % pc;
	
	getCatmullRomPoint(t, indices, res, p);
}

void renderCatmullRomCurve(float p[][3], int pc) {
	int i;
	glBegin(GL_LINE_LOOP);
	for (i = 0; i < pc; i++){
		glVertex3f(p[i][0],p[i][1],p[i][2]);
	}
	glEnd();
}

/*---------------------------------------------------------------------*/

void init(int nformas)
{
	N = nformas;
	
	global_b = (float*)malloc(N * sizeof(float));
	global_timeb = (float*)malloc(N * sizeof(float));
	global_ang = (float*)malloc(N * sizeof(float));
	global_timer = (float*)malloc(N * sizeof(float));
	
	global_index = 0;
	
	for (int i = 0; i < N; i++){
		global_b[i] = 0;
		global_timeb[i] = 0;
		global_ang[i] = 360; // ¬ngulo de translaÁ„o de um planeta
		global_timer[i] = 0;
	}
	
	cout << "NUMERO DE PLANETAS: " << N << "\n";
}

/*------------------ Class - Translacao3D -----------------------------*/
void Translacao3D::translate()
{
	if (tempo == 0){
		glTranslatef(first.x, first.y, first.z);
	}
	else {
		float res[3];
		int pc = pontos.size();
		float p1[100][3];
		// prencher com os pontos de control
		for (int i = 0; i < pc; i++){
			p1[i][0] = pontos[i].x;
			p1[i][1] = pontos[i].y;
			p1[i][2] = pontos[i].z;
		}
		
		renderCatmullRomCurve(p1, pc);
		getGlobalCatmullRomPoint(global_b[global_index], res, p1, pc);
		
		glTranslatef(res[0], res[1], res[2]);
		
		float inc = (0.025) / tempo;
		
		// 0.025 È o incremento que temos que dar para percorrer o caminho em 1 seg
		// Sabemos que o valor m·ximo de t vai ser 1 logo para 1 seg precisamos de incrementar 1
		// È facil concluir que para 25 ms precisa de incrementar 0.025 (regra de 3 simples)
		float time = glutGet(GLUT_ELAPSED_TIME);
		if (time - global_timeb[global_index] >= 25){
			// Assim executa-se de 25 em 25 ms para n„o haver problemas com pcs mais fracos
			global_timeb[global_index] = time;
			global_b[global_index] += inc;
		}
		if (global_index == N-1) global_index = 0;
		else global_index++;
	}
}

void Translacao3D::printT()
{
	cout << "TRANSLACAO\n";
	first.printP();
	cout << "B: " << b << "\n";
	for (int i = 0; i < pontos.size(); i++){
		pontos[i].printP();
	}
	cout << "Tempo: " << tempo << "\n";
}

/*---------------------------------------------------------------------*/

/*--------------------- Class - Rotacao3D -----------------------------*/
Rotacao3D::Rotacao3D()
{
	tempo = 0.0f;  x = 0.0f; y = 0.0f; z = 0.0f;
}

void Rotacao3D::printR() const
{
	cout << "ROTACAO\n" << "Tempo:" << tempo << "Angulo: " << ang << " X:" << x << " Y:" << y << " Z:" << z << "\n";
}

void Rotacao3D::rotate()
{
	if (tempo == 0){
		glRotatef(global_ang[global_index], x, y, z);
	}
	else {
		glRotatef(global_ang[global_index], x, y, z);
		float inc = (9) / tempo;
		
		// 9 È o valor do incremento para se percorrer o caminho em 1 seg, facilmente vemos isso
		// porque se precisa de 1 seg para incrementar 360 graus logo È necess·rio incrementar 9 a cada 25 ms
		float time = glutGet(GLUT_ELAPSED_TIME);
		if (time - global_timer[global_index] > 25){ // assim executa de 25 em 25 ms
			global_timer[global_index] = time;
			global_ang[global_index] += inc;
		}
	}
}
/*---------------------------------------------------------------------*/


/*---------------------- Class - Ponto3D -----------------------------*/
Ponto3D::Ponto3D()
{
	x = 0.0f; y = 0.0f; z = 0.0f;
}

/**Imprime ponto3d para stdout*/
void Ponto3D::printP() const
{
	cout << "X: " << x << " Y: " << y <<  "Z: " << z << "\n";
}

/**Converte ponto3d em string*/
string Ponto3D::tostring()
{
	stringstream ss;
	ss << x << " " << y << " " << z;
	return ss.str();
}
/*---------------------------------------------------------------------*/


/*---------------------- Class - Forma -------------------------------*/

/**FunÁ„o que lÍ um conjunto de tri‚ngulos de um dado ficheiro, cada subclasse poder·
 eventualmente ter a necessidade de implementar a sua funÁ„o de leitura mas esta È a base*/
void Forma::read3DfromFile(string filename)
{
	std::fstream file(filename, std::ios_base::in); // Nome do ficheiro supostamente temos de ir busc·-lo a um ficheiro .xml?
	
	file >> nome;
	float x, y, z;
	while (file >> x >> y >> z)
		{
		Ponto3D t;
		t.x = x; t.y = y; t.z = z;
		tgls.push_back(t); // Adicionar triangulo ao vector de triangulos da esfera
		}
	file.close();
}

void Forma::setTransformacoes(vector<TransformsWrapper> ts) {
	transforms = ts;
}

/**MÈtodo que aplica transformaÁıes geomÈtricas ‡s formas (mÈtodo genÈrico)*/
void Forma::applyTransforms() {
	int i, n;
	
	n = transforms.size();
	glPushMatrix();
	for (i = 0; i < n; i++){
		TransformsWrapper tw = transforms[i];
		const char* op = tw.nome.c_str();
		
		if (strcmp(op, "ROTATE") == 0){
			tw.rotacao.rotate();
		}
		else if (strcmp(op, "TRANSLATE") == 0){
			tw.translacao.translate();
		}
		else if (strcmp(op, "SCALE") == 0){
			glScalef(tw.escala.x, tw.escala.y, tw.escala.z);
		}
	}
}

/*Imprimir todas as transformaÁıes para debug*/
void Forma::printAllTransforms() {
	int i;
	int n = transforms.size();
	
	for (i = 0; i < n; i++){
		transforms.at(i).printTW();
	}
}

/*FunÁ„o que desenhar curva (trajetÛria elÌptica de um planeta)*/
void Forma::desenhar_curva() {
	int n = transforms.size();
	for (int i = 0; i < n; i++){
		TransformsWrapper tw = transforms[i];
		
		if (tw.nome.compare("TRANSLATE") == 0){
			float res[3];
			int pc = tw.translacao.pontos.size();
			float p1[100][3];
			// prencher com os pontos de control
			for (int i = 0; i < pc; i++){
				p1[i][0] = tw.translacao.pontos[i].x;
				p1[i][1] = tw.translacao.pontos[i].y;
				p1[i][2] = tw.translacao.pontos[i].z;
			}
			renderCatmullRomCurve(p1, pc);
		}
	}
}

void Forma::criarVBO(string filename) {
	std::fstream file(filename, std::ios_base::in);

	file >> nome;

	int size;
	file >> size;
	float* vertexB, *vertexN, *vertexT;
	
	vertexB = (float*)malloc((size)* sizeof(float));
	int indice = 0;
	
	glEnableClientState(GL_VERTEX_ARRAY);
	glEnableClientState(GL_NORMAL_ARRAY);
	glEnableClientState(GL_TEXTURE_COORD_ARRAY);
	
	float x, y, z;
	int i = 0;

	while (i < size) {
		file >> x >> y >> z;
		vertexB[indice++] = x;
		vertexB[indice++] = y;
		vertexB[indice++] = z;
		i+=3;
	}
	int n_size;
	file >> n_size;
	vertexN = (float*)malloc((n_size)* sizeof(float));

	i = indice = 0;
	while (i < n_size) {
		file >> x >> y >> z;
		vertexN[indice++] = x;
		vertexN[indice++] = y;
		vertexN[indice++] = z;
		i+=3;
	}
	
	int t_size;
	file >> t_size;
	vertexT = (float*)malloc((t_size)* sizeof(float));
	
	i = indice = 0;
	while (i < t_size) {
		file >> x >> y;
		vertexT[indice++] = x;
		vertexT[indice++] = y;
		i+=2;
	}

	file.close();
	vertexCount = size;
	glGenBuffers(3, buffers);
	glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);
	glBufferData(GL_ARRAY_BUFFER, sizeof(float) * size, vertexB, GL_STATIC_DRAW);
	glBindBuffer(GL_ARRAY_BUFFER,buffers[1]);
	glBufferData(GL_ARRAY_BUFFER, sizeof(float) * n_size, vertexN, GL_STATIC_DRAW);
	glBindBuffer(GL_ARRAY_BUFFER,buffers[2]);
	glBufferData(GL_ARRAY_BUFFER, sizeof(float) * t_size, vertexT, GL_STATIC_DRAW);
	
	free(vertexB);
	free(vertexN);
	free(vertexT);
}

unsigned int te, tw, th, u;
unsigned char *imageData;
float v_terreno[4]={0.7, 1, 0.7,1};

void Forma::carregarImagem(){
	ilGenImages(1,&te);
	ilBindImage(te);
	ilLoadImage((ILstring)"/Users/Marcelo/Desktop/Etapa 4/terra.jpg");
	tw = ilGetInteger(IL_IMAGE_WIDTH);
	th = ilGetInteger(IL_IMAGE_HEIGHT);
	ilConvertImage(IL_RGBA, IL_UNSIGNED_BYTE);
	imageData = ilGetData();
	
	glGenTextures(1,&textura);
	glBindTexture(GL_TEXTURE_2D,textura);
	
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	
	glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, tw, th, 0, GL_RGBA, GL_UNSIGNED_BYTE, imageData);
}

void Forma::drawVBO() {
	applyTransforms();
	
	glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);
	glVertexPointer(3, GL_FLOAT, 0, 0);
	
	glBindBuffer(GL_ARRAY_BUFFER,buffers[1]);
	glNormalPointer(GL_FLOAT,0,0);
	
	glBindBuffer(GL_ARRAY_BUFFER,buffers[2]);
	glTexCoordPointer(2,GL_FLOAT,0,0);

	//glDrawElements(GL_TRIANGLES, vertexCount ,GL_UNSIGNED_INT, &buffers[0]);
	glBindTexture(GL_TEXTURE_2D, textura);
	glDrawArrays(GL_TRIANGLES, 0, vertexCount);
	glBindTexture(GL_TEXTURE_2D, 0);
	
	glPopMatrix();
}


/*-------------------------------------------------
	Patches de Bézier
--------------------------------------------------*/

vector<Teapot> patchs;
vector<Ponto3D> vertices;

void readTeapot(string path) {
	int i, j, pos, ind, np, nv;
	float ponto[3], n;
	string line, token;

	ifstream ifile;
	ifile.open(path, ios::in);

	if (ifile.is_open()) {
		ifile >> np; getline(ifile, line);

		for (i = 0; i < np && getline(ifile, line); i++) {
			Teapot pa = Teapot::Teapot();
			for (j = 0; j < 16; j++) {
				pos = line.find(",");
				token = line.substr(0, pos);
				ind = atof(token.c_str());
				line.erase(0, pos + 1);

				pa.adicionaIndice(ind);
			}
			patchs.push_back(pa);
		}

		ifile >> nv; getline(ifile, line);

		for (i = 0; i < nv && getline(ifile, line); i++) {
			for (j = 0; j < 3; j++) {
				pos = line.find(",");
				token = line.substr(0, pos);
				n = atof(token.c_str());
				line.erase(0, pos + 1);

				ponto[j] = n;
			}
			vertices.push_back(Ponto3D::Ponto3D(ponto[0], ponto[1], ponto[2]));
		}
		ifile.close();
	}
	else {
		cout << "Ficheiro *.patch nao encontrado!" << endl;
	}
}


Ponto3D bernstein(float t, float *p1, float *p2, float *p3, float *p4) {
	float res[3];

	float it = 1.0 - t;

	float b0 = it*it*it;
	float b1 = 3 * t*it*it;
	float b2 = 3 * t*t*it;
	float b3 = t*t*t;

	res[0] = b0*p1[0] + b1*p2[0] + b2*p3[0] + b3*p4[0];
	res[1] = b0*p1[1] + b1*p2[1] + b2*p3[1] + b3*p4[1];
	res[2] = b0*p1[2] + b1*p2[2] + b2*p3[2] + b3*p4[2];

	return Ponto3D::Ponto3D(res[0], res[1], res[2]);
}


Ponto3D bezier(float u, float v, vector<int> pat) {
	float bz[4][3], res[4][3];
	int i, j = 0, k = 0;

	for (i = 0; i < 16; i++) {
		bz[j][0] = vertices[pat[i]].x;
		bz[j][1] = vertices[pat[i]].y;
		bz[j][2] = vertices[pat[i]].z;

		j++;

		if (j % 4 == 0) {
			Ponto3D p = bernstein(u, bz[0], bz[1], bz[2], bz[3]);
			res[k][0] = p.x;
			res[k][1] = p.y;
			res[k][2] = p.z;

			k++;
			j = 0;
		}
	}
	return bernstein(v, res[0], res[1], res[2], res[3]);
}


void escreveTriangulos(Ponto3D p1, Ponto3D p2, Ponto3D p3, ofstream& file) {
	file << p1.x << " " << p1.y << " " << p1.z << endl;
	file << p2.x << " " << p2.y << " " << p2.z << endl;
	file << p3.x << " " << p3.y << " " << p3.z << endl;
}

//Função que trata de cada linha do patch e converte para triangulos a respectiva linha
void patchBezier(int tess, int ip, ofstream& file) {
	float inc = 1.0 / tess, u, v, u2, v2;

	for (int i = 0; i < tess; i++) {
		for (int j = 0; j < tess; j++) {
			u = i*inc;
			v = j*inc;
			u2 = (i + 1)*inc;
			v2 = (j + 1)*inc;

			Ponto3D p0 = bezier(u, v, patchs[ip].getPatch());
			Ponto3D p1 = bezier(u, v2, patchs[ip].getPatch());
			Ponto3D p2 = bezier(u2, v, patchs[ip].getPatch());
			Ponto3D p3 = bezier(u2, v2, patchs[ip].getPatch());

			escreveTriangulos(p0, p2, p3, file);
			escreveTriangulos(p0, p3, p1, file);
		}
	}
}


//Transforma um ficheiro com todos os patch num ficheiro .3d com os pontos respectivos
void initSupBezier(int tess, string nameFile) {
	ofstream file;
	file.open(nameFile);

	file << "TEAPOT\n";
	int num = patchs.size();

	file << (num*tess*tess * 6 * 3) << endl;

	for (int i = 0; i < num; i++)
		patchBezier(tess, i, file); //Trata de cada patch de Bezier
	file.close();
}

void Teapot::write3DtoFile(string filename){}
void Teapot::read3DfromFile(string filename){}
Teapot::Teapot(){}
void Teapot::adicionaIndice(int i) {
	indices.push_back(i);
}
Teapot::~Teapot(){}
void Teapot::draw(){}

void Teapot::criarVBO(string filename)
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