%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SIST. REPR. CONHECIMENTO E RACIOCINIO - LEI/3

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% EXERCÍCIO PRATICO 2

%Autores: Jorge Caldas, José Cortez, Susana Mendes, Xavier Rodrigues 

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Programacao em logica estendida
%
% Representacao de conhecimento imperfeito

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SICStus PROLOG: Declaracoes iniciais

:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).
:- set_prolog_flag( unknown,fail ).

:- op( 900,xfy,'::' ).
:- dynamic proprietario/2.
:- dynamic automovel/3.
:- dynamic dataFabrico/4.
:- dynamic cor/2.
:- dynamic estado/2.
:- dynamic preco/2.


% De seguida seguem-se alguns datasets para enriquecer em termos de,
% exemplos disponíveis a nossa base de conhecimento. Estes estão organizados por propietário

%--------------------------------- - - - - - - - - - -  -  -  -  -   - 
% Maria

proprietario('03-02-FF',maria).
automovel('03-02-FF',volfswagen,golf).
dataFabrico('03-02-FF',20,08,2014).
cor('03-02-FF',preto).
preco('03-02-FF',25000).
estado('03-02-FF',bom).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Josué

proprietario('55-42-AA',josue).
automovel('55-42-AA',nissan, gtr).
dataFabrico('55-42-AA',05,09,2009).
cor('55-42-AA',laranja).
preco('55-42-AA',127350).
estado('55-42-AA',bom).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Fernando

proprietario('01-33-GH', fernando).
automovel('01-33-GH',renault, 5).
dataFabrico('01-33-GH',06,07,1988).
cor('01-33-GH',cinzento).
preco('01-33-GH',4500).
estado('01-33-GH',mau).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Cristiano

proprietario('15-64-FN', cristiano).
automovel('15-64-FN',lancia, deltaIntegrale).
dataFabrico('15-64-FN',01,02,1980).
cor('15-64-FN',branco).
preco('15-64-FN',70000).
estado('15-64-FN',medio).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Alice

proprietario('15-MM-22', alice).
automovel('15-MM-22', fiat500, abarth).
dataFabrico('15-MM-22',17,08,2013).
cor('15-MM-22',branco).
preco('15-MM-22',55500).
estado('15-MM-22',medio).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Armando

proprietario('30-22-PE',armando).
automovel('30-22-PE', koenigsegg, ageraR).
dataFabrico('30-22-PE',20,11,2012).
cor('30-22-PE',branco).
preco('30-22-PE',3000000).
estado('30-22-PE',bom).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Artur

proprietario('07-88-KK',artur).
automovel('07-88-KK',pagani,zonda).
dataFabrico('07-88-KK',20,12,2006).
cor('07-88-KK',cinzento).
preco('07-88-KK',1500000).
estado('07-88-KK',bom).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Joana

proprietario('04-64-EF',joana).
automovel('04-64-EF',peugeot,205).
dataFabrico('04-64-EF',12,10,1994).
cor('04-64-EF',preto).
preco('04-64-EF', 2000).
estado('04-64-EF',razoavel).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Filipe

proprietario('99-AK-47',filipe).
automovel('99-AK-47',mercedes,c220).
dataFabrico('99-AK-47',11,11,2014).
%cor('99-AK-47',azul).
preco('99-AK-47', 25000).
estado('99-AK-47',bom).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Nesta secção encontram-se implementados alguns predicados auxiliares

evolucao( Termo ) :-
    solucoes( Invariante,+Termo::Invariante,Lista ),
    insercao( Termo ),
    teste( Lista ).


insercao( Termo ) :-
    assert( Termo ).
insercao( Termo ) :-
    retract( Termo ),!,fail.

teste( [] ).
teste( [R|LR] ) :-
    R,
    teste( LR ).

remocao( Termo ) :-
    solucoes( Invariante,-Termo::Invariante,Lista ),
    teste( Lista ),
    retract( Termo ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado demo: Questao,Resposta -> {V,F}

demo( Questao,verdadeiro ) :-
    Questao.
demo( Questao, falso ) :-
    -Questao.
demo( Questao,desconhecido ) :-
    nao( Questao ),
    nao( -Questao ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado demoL: Lista de Questoes, Lista de Respostas -> {V,F}
demoL(  [],[] ).
demoL( [Q|QS],[X|L] ) :- demo(Q,X), demoL(QS,L). 


% Tabela de inferência de valores lógicos de conhecimento (explicados no relatorio)
% baseada na análise do conhecimento representado.

% VV->V
% VD->D
% VF->F
% DF->F
% DD->D
% FF->F
% DV->D
% FV->F
% FD->F
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado demo2: Questao1, Questao2, Resposta -> {V,F}
demo2( Q1,Q2,verdadeiro ):- demo(Q1,R1), demo(Q2,R2), R1==verdadeiro, R2==verdadeiro.
demo2( Q1,Q2,desconhecido ):- demo(Q1,R1), demo(Q2,R2), R1==verdadeiro, R2==desconhecido.
demo2( Q1,Q2,desconhecido ):- demo(Q1,R1), demo(Q2,R2), R1==desconhecido, R2==desconhecido.
demo2( Q1,Q2,desconhecido ):- demo(Q1,R1), demo(Q2,R2), R1==desconhecido, R2==verdadeiro.
demo2( Q1,Q2,falso ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado nao: Questao -> {V,F}

nao( Questao ) :-
    Questao, !, fail.
nao( Questao ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -

solucoes( X,Y,Z ) :-
    findall( X,Y,Z ).

comprimento( S,N ) :-
    length( S,N ).
	
% --------------------------------------------------------

%Extensao do predicado automovel: Matricula,Marca,Modelo -> {V,F}
%Extensao do predicado preço: Matricula,Preco -> {V,F}
%Extensao do predicado cor:Matricula,Cor
%Extensao do predicado dataFabrico: Matricula, Dia, Mes, Ano -> {V,F}
%Extensao do predicado estado: Matricula,Estado
%Extensao do predicado proprietario:Matricula, NProprietario -> {V,F}

-automovel(X,Y,Z) :- nao(automovel(X,Y,Z)), nao(excecao(automovel(X,Y,Z))).
-cor(X,Y) :- nao(cor(X,Y)), nao(excecao(cor(X,Y))).
-preco(X,Y) :- nao(preco(X,Y)), nao(excecao(preco(X,Y))).
-proprietario(X,Y) :- nao(proprietario(X,Y)), nao(excecao(proprietario(X,Y))).
-estado(X,Y) :- nao(estado(X,Y)), nao(excecao(estado(X,Y))).
-dataFabrico(M,DD,MM,AA) :- nao(dataDatabrico(M,DD,MM,AA)), nao(excecao(dataFabrico(M,DD,MM,AA))).




%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%Invariantes Estruturais


% ---------- Invariante Automovel --------------
%% Inserção
%Só se pode admitir uma e so uma matricula por cada extensao(automovel,cor,dataFabrico,proprietario,preco)
+automovel( M,MARCA,MODELO ) :: (solucoes( (M1,M2),automovel( M,M1,M2 ),S ),
							comprimento( S,N ), N == 1
						  ).   

						  
%% Remoção
%Só se pode remover um automóvel que exista na base de conhecimento						  
-automovel( M,MARCA,MODELO ) :: (solucoes((M1,M2),automovel( M,M1,M2 ),S ),
							comprimento( S,N ), N == 1 ).  
						  
-automovel( M,MARCA,MODELO ) :: (solucoes((C),cor( M,C),S ),
							comprimento( S,N), N == 0 ).  
						  
-automovel( M,MARCA,MODELO ) :: (solucoes((P),preco( M,P),S ),
							comprimento( S,N), N == 0 ).  

-automovel( M,MARCA,MODELO ) :: (solucoes((DD,MM,AA),dataFabrico( M,DD,MM,AA),S ),
							comprimento( S,N), N == 0 ).  
							
-automovel( M,MARCA,MODELO ) :: (solucoes((E),estado( M,E),S ),
							comprimento( S,N), N == 0 ).  
							
-automovel( M,MARCA,MODELO ) :: (solucoes((P),proprietario( M,P),S ),
							comprimento( S,N), N == 0 ).  
										
						 


% -------- Invariante Cor -------------
%%% Inserção
%Um carro tem uma e uma só cor.
+cor( M,C ) :: (solucoes( (Cs),(cor( M,Cs )),S ),
                comprimento( S,N ), N == 1
                ).

%Só pode ser atribuída cor a carro se automovel existir na base.
+cor( M,C ) :: (solucoes( (MARCAS,MODS),(automovel( M,MARCAS,MODS )), S),
                comprimento( S,N ), N==1
                ).

				
%%% Remoção
%Só se pode remover uma cor dum automóvel se esse mesmo existir
-cor( M,C ) :: (solucoes((M1,M2),automovel( M,M1,M2 ),S ),
							comprimento( S,N ), N == 1
						  ).  

% --------- Invariante DataFabrico ----------
%Só pode ser atribuída data de fabrico se o automóvel já existir na base.
%%%% Inserção
+dataFabrico( M, D, MM, A ) :: (solucoes( (MARCA,MOD),(automovel( M,MARCA,MOD )),S ),
                  comprimento( S,N ), N == 1
                  ).
				  
% Um carro só pode ter uma data de fabrico
+dataFabrico( M,D,MM,A ) :: (solucoes( (D1,MM1,A1),(dataFabrico( M,D1,MM1,A1 )),S ),
                comprimento( S,N ), N == 1
                ).
				
%%%% Remoção
%Só se pode remover a data de fabrico dum automóvel se esse mesmo existir
-dataFabrico( M,D,MM,A ) :: (solucoes((M1,M2),automovel( M,M1,M2 ),S ),
							comprimento( S,N ), N == 1
						  ).  
				  
				  
% -------- Invariante Preco ------------------
%%%% Insersão
%Só pode ser atribuído preço se o automóvel já existir na base
+preco( M,C ) :: (solucoes( (MARCAS,MODS),(automovel( M,MARCAS,MODS )), S),
                comprimento( S,N ), N==1
                ).
				
% Um automóvel só pode ter um preço
+preco( M,P ) :: (solucoes( (P2),(preco( M,P2 )),S ),
                comprimento( S,N ), N == 1
                ).
				
%%%%% Remoção			
%Só se pode remover o preço dum automóvel se esse mesmo existir
-preco( M,P ) :: (solucoes((M1,M2),automovel( M,M1,M2 ),S ),
							comprimento( S,N ), N == 1
						  ).  
				


% --------- Invariante Estado ----------------	
%%%% Inserção
% Só pode ser atribuído um estado a um automóvel se este mesmo existir na base			
+estado( M,E ) :: (solucoes( (MARCAS,MODS),(automovel( M,MARCAS,MODS )), S),
                comprimento( S,N ), N==1
                ).
				
% Um automóvel só pode ter um estado
+estado( M,E ) :: (solucoes( (E2),(estado( M,E2 )),S ),
                comprimento( S,N ), N == 1
                ).
				
				
%%%% Remoção
%Só se pode remover o estado dum automóvel se esse mesmo existir
-estado( M,E ) :: (solucoes((M1,M2),automovel( M,M1,M2 ),S ),
							comprimento( S,N ), N == 1
						  ).  
				
% ----------- Invariante Proprietario ----------
%%%% Inserção
% Só pode ser atribuído um proprietário a um automóvel se este mesmo existir na base			
+proprietario( M,P ) :: (solucoes( (MARCAS,MODS),(automovel( M,MARCAS,MODS )), S),
                comprimento( S,N ), N==1
                ).
				
% Um carro só pode ter um proprietário
+proprietario( M,P ) :: (solucoes( (P2),(proprietario( M,P2 )),S ),
                comprimento( S,N ), N == 1
                ).
				

%%%% Remoção
%Só se pode remover um proprietário dum automóvel se esse mesmo existir
-proprietario( M,P ) :: (solucoes((M1,M2),automovel( M,M1,M2 ),S ),
							comprimento( S,N ), N == 1
						  ).  

				  

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% CASOS DE ESTUDO

% -------------------------------- Caso 1 --------------------------------

%Caso 1: O Sr. João chegou ao Stand Vitoria com uma Toyota Corolla, com data de fabrico de 14/04/93,
%matricula ,09-87-CF. Porem e devido ao desgaste, e ao seu estado ser mal não soubemos com precisao se a cor era azul ou cinza.
%% Conhecimento impreciso

proprietario('09-87-CF',joao).
automovel('09-87-CF',toyota,corolla).
dataFabrico('09-87-CF',14,04,93).
preco('09-87-CF',2000).
estado('09-87-CF',mal).
 
%Não se sabe ao certo se cor é cinza ou azul

excecao( cor( '09-87-CF',cinza ) ).
excecao( cor( '09-87-CF',azul ) ). 


% -------------------------------- Caso 2 --------------------------------

%Caso 2: A Dona Rosa chegou ao Stand Vitoria com o carro de seu pai, ja falecido. Um Datsun,de cor verde, mas devido ao estado ja nao se consegui averiguar o modelo.
%E por nao saber do livrete nao se consegui saber o data de fabrico.
%% Conhecimento nulo

automovel('67-34-PT',datsun,xptop).
excecao( automovel( M,M,M ) ) :-
    automovel( M,M,xptop ).
+automovel('67-34-PT',A,Q) :: (solucoes(X, (automovel('67-34-PT',datsun,X), nao(nulo(X))),S),
					comprimento(S,N), N==0).
					

proprietario('67-34-PT',rosa).
cor('67-34-PT',verde).
preco('67-34-PT',2000).

dataFabrico('67-34-PT',ddd,mmm,aaa).
excecao( dataFabrico( M,D,M,A ) ) :-
    dataFabrico( M,ddd,mmm,aaa ).


% -------------------------------- Caso 3 --------------------------------

%Caso 3: O Sr. Cesar viu, de relance,  um Lamborghini no Stand Vitória, porem ele nao sabe se é um huracan ou um gallardo mas tem a certeza que nao é um
%aventador. O carro é branco, matricula, 08-07-XP, ano de fabrico 30/6/2014
% Conhecimento negativo / Conhecimento impreciso

proprietario('08-07-XP',cesar).
dataFabrico('08-07-XP',30,6,2014). 
cor('08-07-XP',branco).
preco('08-07-XP',345000).	


excecao(automovel('08-07-XP',lamborghini,huracan)).
excecao(automovel('08-07-XP',lamborghini,gallardo)).
-automovel('08-07-XP',lamborghini,aventador).


% -------------------------------- Caso 4 --------------------------------

% Caso 4:  O Júlio Mendes avistou um Audi R8 em bom estado no Stand Vitoria. Estava tão atento a todos os pormenores : reparou na matrícula ('22-33-FX') , da data de fabrico do carro 
%  (5/6/2009)  no preço exorbitante de 230 000 € mas, quando chegou a casa, já não se lembrava da cor. 

automovel('22-23-FX',audi,r8).
dataFabrico('22-23-FX', 05,06,2009).
cor('22-23-FX', xpto23).
preco('22-23-FX', 230000).
estado('22-23-FX', bom).

excecao( cor( M,C) ) :-
    cor( M,xpto23 ).



% Exemplo geral com recurso ao predicado demo2 e explicado com paralelismo a uma banda-desenhada.
% -------------------------------- Caso 5 (Interligação com caso 1) --------------------------------
% Caso 5: O Sr. Fabiano visitou o stand à procura de um Toyota Corolla azul. No stand informaram que era de momento
% impossível dar a confirmação da existência do veículo. No entanto existe a possibilidade de o carro do Sr. João (CASO 1)
% ser o veículo procurado pelo Sr. Fabiano uma vez que a cor desse carro poderá ser azul ou cinza.

%EXECUTAR DIÁLOGO DE QUERYS NO SICSTUS

% Vida Real: Sr. Fabiano: ´Têm algum toyota corolla azul de momento?´
% PROLOG: - demo2( automovel( MAT,toyota,corolla ), cor( MAT,azul ), R ).
% VD->D, segunda regra da tabela de inferências

% Vida Real: Funcionário do stand: ´De momento não lhe posso confirmar, apenas lhe consigo dizer que se um veículo com essas características
% existir terá a matrícula 09-87-CF.´

% PROLOG:
% MAT='09-87-CF'
% R = desconhecido


% Vida Real: Um dia depois o Sr. João ligou para o stand a confirmar que defacto o seu toyota era azul e deu ordem para ser
% feita uma pintura com o mesmo azul de antigamente.

% PROLOG: evolucao( cor( '09-87-CF',azul ) ).
% yes

% Vida Real: O funcionário do stand tinha registado o pedido do Sr. Fabiano portanto ligou-lhe de imediato, após
% receber a informação do Sr. João. O Sr. Fabiano ficou muito satisfeito por ter sido informado tão rápido e elogiou
% o sistema de informação do Stand Vitória.

% PROLOG: demo2( automovel( MAT,toyota,corolla ), cor( MAT,azul ), R ).
% MAT='09-87-CF'
% R = verdadeiro - - > (é agora verdade que existe um toyota corolla azul no stand)

%Vida Real: O Sr. Fabiano acabou por comprar o veículo.
% remocao( proprietario('09-87-CF',joao) ).
% remocao( dataFabrico('09-87-CF',14,04,93) ).
% remocao( preco('09-87-CF',2000) ).
% remocao( estado('09-87-CF',mal) ).
% remocao( cor( '09-87-CF',azul ) ).
% remocao( automovel('09-87-CF',toyota,corolla) ).