%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SIST. REPR. CONHECIMENTO E RACIOCINIO - LEI/3

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Exercicio 1

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% AUTORES: Xavier Rodrigues, Afonso Caldas, José Cortez, Susana Mendes

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SICStus PROLOG: Declaracoes iniciais

:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).
:- set_prolog_flag( unknown,fail ).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SICStus PROLOG: definicoes iniciais

:- op( 900,xfy,'::' ).
:- dynamic filho/2.
:- dynamic pai/2.
:- dynamic mae/2.
:- dynamic irmao/2.
:- dynamic tio/2.
:- dynamic sobrinho/2.
:- dynamic primo/2.
:- dynamic naturalidade/2.
:- dynamic sexo/2.
:- dynamic id/3.


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Base de Conhecimento


filho(teresa,afonsoVI).
filho(teresa,ximena).
filho(urraca,henrique).
filho(urraca,teresa).
filho(sancha,henrique).
filho(sancha,teresa).
filho(afonso,henrique).
filho(afonso,teresa).
filho(afonsoI,henrique).
filho(afonsoI,teresa).
filho(sancho,afonsoI).
filho(sancho,mafalda).
filho(dulce,ramon).
filho(dulce,petronila).
filho(raimundo,sancho).
filho(raimundo,dulce).
filho(constanca,sancho).
filho(constanca,dulce).
filho(afonsoII,sancho).
filho(afonsoII,dulce).
filho(berengaria,sancho).
filho(berengaria,dulce).
filho(pedro,sancho).
filho(pedro,dulce).
filho(fernando,sancho).
filho(fernando,dulce).
filho(branca,sancho).
filho(branca,dulce).
filho(erico,valdemar).
filho(erico,berengaria).
filho(joao,raimundo).
filho(joao,ana).


sexo(afonsoVI,masculino).
sexo(ximena,feminino).
sexo(henrique,masculino).
sexo(teresa,feminino ).
sexo(mafalda,feminino).
sexo(afonsoI,masculino ).
sexo(sancha,feminino).
sexo(afonso,masculino).
sexo(urraca,feminino).
sexo(sancho,masculino).
sexo(dulce,feminino).
sexo(ramon,masculino).
sexo(petronila,feminino).
sexo(raimundo,masculino).
sexo(constanca,feminino).
sexo(afonsoII,masculino).
sexo(pedro,masculino).
sexo(fernando,masculino).
sexo(branca,feminino).
sexo(berengaria,feminino).
sexo(valdemar,masculino).
sexo(erico,masculino).
sexo(joao,masculino).
sexo(ana,feminino).


id(afonsoVI,1047,1109).
id(ximena,1060,1128).
id(henrique,1035,1074).
id(teresa,1080,1130).
id(mafalda,1125,1157).
id(afonsoI,1109,1185).
id(sancha,1095,1163).
id(afonso,1106,1110).
id(urraca,1096,1173).
id(sancho,1154,1211).
id(dulce, 1160,1198).
id(ramon,1113,1162).
id(petronila,1136,1173).
id(raimundo,1195,1200).
id(constanca,1182,1202).
id(afonsoII,1185,1223).
id(pedro,1187,1258).
id(fernando,1188,1233).
id(branca,1192,1240).
id(berengaria,1196,1221).
id(valdemar,1170,1241).
id(erico,1216,1250).
id(ana,1200,1245).
id(joao,1216,1260).


naturalidade(afonsoVI,toledo).
naturalidade(ximena,toledo).
naturalidade(henrique,borgonha).
naturalidade(teresa,povoalanhoso).
naturalidade(mafalda,saboia).
naturalidade(afonsoI,guimaraes).
naturalidade(sancha,guimaraes).
naturalidade(afonso,guimaraes).
naturalidade(urraca,guimaraes).
naturalidade(sancho,coimbra).
naturalidade(dulce,coimbra).
naturalidade(ramon,barcelona).
naturalidade(petronila,huesca).
naturalidade(raimundo,coimbra).
naturalidade(constanca,coimbra).
naturalidade(afonsoII,coimbra).
naturalidade(pedro,coimbra).
naturalidade(fernando,coimbra).
naturalidade(branca,coimbra).
naturalidade(berengaria,coimbra).
naturalidade(valdemar,copenhaga).
naturalidade(erico,copenhaga).
naturalidade(ana,guimaraes).
naturalidade(joao,guimaraes).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado pai: Pai,Filho -> {V,F}

pai( P,F ) :- filho( F,P ), sexo( P,masculino ).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado mae: mae,Filho -> {V,F}

mae( M,F ) :- filho( F,M ), sexo( M,feminino ).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado irmao: Irmao1,Irmao2 -> {V,F}

irmao( I1,I2 ) :- pai( X,I1 ), pai( X,I2 ), mae( Y,I1), mae( Y,I2 ), I1\=I2 .


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado irmaos que determina a lista dos irmaos de um dado indivíduo: I,L -> {V,F}

irmaos( I,L ) :- solucoes( X,irmao(I,X), L).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado tio que testa se um indivíduo T é tio (ou tia) de um indivíduo S: Tio,Lista -> {V,F}

tio( T,S ) :- pai( X,S ), irmao( T,X ).
tio( T,S ) :- mae( X,S ), irmao( T,X ).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado tios que testa se um indivíduo T é tios (e tias) de um indivíduo S: Tio,Lista -> {V,F}

tios( I,TS ) :- solucoes( T,tio(T,I), TS).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado sobrinho: Sobrinho,Tio -> {V,F}
sobrinho( S,T ) :- tio( T,S ).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado sobrinhos: indivíduo,Sobrinho -> {V,F}
sobrinhos( I,SS ):- solucoes( S,sobrinho(S,I),SS ).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado primo: Primo1,Primo2 -> {V,F}

primo( P1,P2 ) :- tio( X,P1 ), filho( P2,X ).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado primos: Individuo,Primos -> {V,F}
primos( I,PS ) :- solucoes( X,primo(I,X),PS ).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado descendente: Descendente,Ascendente -> {V,F}

descendente( D,A ):- filho( D,A ).
descendente( D,A ):- filho( D,X ) , descendente( X,A ).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado descendentes: Individuo,Descendentes -> {V,F}

descendentes( I,DS ) :- solucoes( X,descendente(X,I),DS ).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado ascendente: Ascendente,Descendente -> {V,F}
ascendente( A,D ) :- descendente( D,A ).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado ascendentes: Individuo,Descendente -> {V,F}
ascendentes( I,AS ) :- solucoes( A,ascendente(A,I),AS ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado concatenar que concatena duas listas: L1,L2,LF -> {V,F}

concatenar([],L,L).
concatenar([H|T],L,[H|Res]) :- concatenar(T,L,Res).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado grau que determina o grau de parentesco entre dois indivíduos: D,A,G -> {V,F}

grau( D,A,1 ) :- filho( D,A ).
grau( D,A,N ) :- filho( D,X ) , grau( X,A,R ), N is R+1.


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado ngrau que determina todos os familiares de um determinado grau de um indíviduo: L1,L2,LF -> {V,F}

ngrau(I,0,[]).
ngrau( I,N,L) :- findall( (X),grau(I,X,N),L ).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado graus que determina todos os familiares anteriores (ou do mesmo) dum indivído até um
% dado grau: I,G,L -> {V,F}

graus(I,0,[]).
graus(I,0,L).
graus(I,N,L) :- concatenar(L1,L2,L), ngrau(I,N,L1), M is N-1, graus(I,M,L2).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado relacao que determina a relacão familiar entre dois indivíduos: X,Y,R -> {V,F}

relacao( X,Y,'filho' ) :- filho(X,Y).
relacao( X,Y,'pai' ) :- pai(X,Y).
relacao( X,Y,'mae' ) :- mae(X,Y).
relacao( X,Y,'pai' ) :- pai(X,Y).
relacao( X,Y,'tio' ) :- tio(X,Y).
relacao( X,Y,'sobrinho' ) :- sobrinho(X,Y).
relacao( X,Y,'primo' ) :- primo(X,Y).


%Predicados extra
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado idade que calcula a idade com que um dado indíviudo da base de conhecimento morreu: Nome,Idade -> {V,F}

idade( N,I ) :- id( N,X,Y ), I is Y-X.



%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% 						Invariantes

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Invariante Estrutural: não permitir a inserção da mesma naturalidade para o mesmo indivíduo (conhecimento repetido)
% (Repetidos)

+naturalidade( I,X ) :: (solucoes( (X),(naturalidade( I,X )),S ),
						comprimento( S,N ), 
						N == 1
                  		).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Invariante Estrutural: Não admitir mais do que uma naturalidade para o mesmo indivíduo
% (Dupla naturalidade)

+naturalidade( I,X ) :: (solucoes( ( I,X ),(naturalidade( I,F )),S ),
            comprimento( S,N ), 
            N =< 1
                      ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Invariante Estrutural: não permitir a inserção de conhecimento repetido para o predicado id.
% (identidade repetida)

+id( I,X,Z ) :: (solucoes( (X,Z),(id( I,F,D )),S ),
            comprimento( S,N ), 
            N =< 1
                      ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Invariante Referencial: Não admitir que um indivíduo tenha um par (Data nascimento - Data morte) diferente.
% (dupla identidade)
+id( I,X,Z ) :: (solucoes( (I,X,Z),(id( I,F,D )),S ),
            comprimento( S,N ), 
            N == 1
                      ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Invariante Estrutural:  nao permitir a insercao de conhecimento epetido relativamente a um progenitor de um dado indivíduo

+filho( F,P ) :: (solucoes( (F,P),(filho( F,P )),S ), 
                  comprimento( S,N ), 
				  N == 1
                  ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Invariante Referencial: nao admitir mais do que 2 progenitores para um mesmo individuo

+filho( F,P ) :: (solucoes( (Pais),(filho( F,Pais )),S ),
                  comprimento( S,N ), N =< 2
                  ).      


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Invariante Estrutural: Não permitir a inserção de conhecimento repetido para o predicado sexo

+sexo( I,X ) :: (solucoes( (X),(sexo( I,X )),S ),
            comprimento( S,N ), 
            N =< 1
                      ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Invariante Referencial: Não admir mais do que 1 sexo por indivíduo

+sexo( I,X ) :: (solucoes( (I,X),(sexo( I,F )),S ),
            comprimento( S,N ), 
            N == 1
                      ).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensão do predicado que permite a evolucao do conhecimento

evolucao( Termo ) :- 
	solucoes( Invariante,+Termo::Invariante,Lista ),
	insercao( Termo ),
	teste( Lista ).

insercao( T ) :- assert( T ).
insercao( T ) :- retract( T ),! ,fail.

remocao( T ) :- retract( T ).	

teste( [] ).
teste( [H|T] ) :-
    H,
    teste( T ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensão do predicado que permite a evolucao do conhecimento e a sua remocao

 rem( Termo ) :- 
  solucoes( Invariante,-Termo::Invariante,Lista ),
  remocao( Termo ),
  teste( Lista ).  

  remocao( T ) :- retract( T ).                   		                                      

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Predicados Auxiliares

comprimento( [],0 ).
comprimento( [H|T],X ) :-  comprimento( T,C ), X is C + 1 .

solucoes(X,Y,Z) :- findall(X,Y,Z).




