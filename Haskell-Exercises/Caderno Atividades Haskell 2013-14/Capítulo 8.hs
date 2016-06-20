import Data.Char
import Data.List

-------------------------------------------------------------PARTE 8 - DATA TYPES --------------------------------------------------------------
type Dia = Int
type Mes = Int
type Ano = Int
type Nome = String
data Data = D Dia Mes Ano
      deriving Show

type TabDN = [(Nome,Data)]

--53.(a) Devolve data de um dado nome caso este exista na tabela 
procura :: Nome -> TabDN -> Maybe Data
procura _ [] = Nothing
procura n ((n',d):td) = if (n==n') then Just d else procura n td

--53.(b) Calcular a idade de uma pessoa numa dada data
idade :: Data -> Nome -> TabDN -> Maybe Int
idade _ _ [] = Nothing
idade (D d m a) nom td = let Just (D d' m' a') = (procura nom td)
                       in if a<a' then Nothing
                       	          else if (a==a') || (a==a') && (m>m') then Nothing
                       	          else if (a==a') && (m==m') && (d<d') then Nothing
                       	          else Just (a-a')

--53.(c) Testar se uma data é anterior a uma outra dada data (anteior a b testa se a é anterior a b)
anterior :: Data -> Data -> Bool
anterior (D d m a) (D d' m' a')   | a>a' = False
                                  | (a>=a')&&(m>m') = False
                                  | (a>=a')&&(m>=m')&&(d>=d') = False
                                  | otherwise = True

--53.(d) Ordenar uma tabela de datas por ordem crescente das datas (Agoritmo insereOrd fácil implementação)
ordena :: TabDN -> TabDN
ordena [] = []
ordena [d] = [d]
ordena [(n1,d1),(n2,d2)] = if (anterior d1 d2) then [(n1,d1),(n2,d2)] else [(n2,d2),(n1,d1)]
ordena ((n,d):t) = ordenaAux (n,d) (ordena t)

ordenaAux :: (Nome,Data) -> TabDN -> TabDN
ordenaAux _ [] = []
ordenaAux (n,d) ((n1,d1):t) | (anterior d d1) = ((n,d):(n1,d1):t)
	                          | otherwise = (n1,d1):(ordenaAux (n,d) t)

--53.(e) Apresenta o nome e a idade das pessoas numa dada data por ordem crescente das idades
porIdade:: Data -> TabDN -> [(Nome,Maybe Int)]
porIdade _ [] = []
porIdade d tab = ordIdade (idades d tab)

--Funções auxiliares para resolução do exercício 53.(e)
idades :: Data -> TabDN -> [(Nome,Maybe Int)]
idades _ [] = []
idades d ((n,d'):t) = (n,(idade d n ((n,d'):t))):idades d t

ordIdade :: [(Nome,Maybe Int)] -> [(Nome,Maybe Int)]
ordIdade [] = []
ordIdade [x] = [x]
ordIdade [(n1,i1),(n2,i2)] = if (i1<i2) then [(n1,i1),(n2,i2)] else [(n2,i2),(n1,i1)] 
ordIdade ((n,i):t) = ordIdadeAux (n,i) (ordIdade t)

ordIdadeAux :: (Nome,Maybe Int) -> [(Nome,Maybe Int)] -> [(Nome,Maybe Int)]
ordIdadeAux _ [] = []
ordIdadeAux (n,i) ((n1,i1):t) | i<=i1 = ((n,i):(n1,i1):t)
                              | otherwise = (n1,i1):(ordIdadeAux (n,i) t)

type TabAbrev = [(Abreviatura,Palavra)]
type Abreviatura = String
type Palavra = String

--54.(a) Dada uma tabela e uma abreviatura devolve a palavra correspondente caso a abreviatura exista na tabela
daPal :: TabAbrev -> Abreviatura -> Maybe Palavra
daPal [] _ = Nothing
daPal ((ab1,p):t) ab = if (ab1==ab) then Just p else daPal t ab

--54.(b) Implementar a função trata (transforma substitui num texto todas as palavras pelas respetivas abreviaturas)
transforma :: TabAbrev -> String -> String
transforma t s = unwords (trata t (words s))

trata :: TabAbrev -> [String] -> [String]
trata [] _ = []
trata _ [] = []
trata t (p:ps) = (daAbrev p t):trata t ps
               where daAbrev :: String -> TabAbrev -> Abreviatura
                     daAbrev [] _ = []
                     daAbrev _ [] = []
                     daAbrev p ((a,pl):t) = if (p==pl) then a else daAbrev p t


data Movimento = Credito Float | Debito Float
data Extracto = Ext Float [(Data, String, Movimento)] --saldo inicial e lista de movimentos

--55.(a) Produzir uma lista com todos os movimentos superiores a um dado montante
extValor :: Extracto -> Float -> [Movimento]
extValor (Ext _ []) _ = []
extValor (Ext si (h:l)) valor = (extValorAux h valor)++extValor (Ext si l) valor 

extValorAux :: (Data,String,Movimento) -> Float -> [Movimento]
extValorAux (d,s,Credito x) val = if (x>val) then (Credito x):[] else []
extValorAux (d,s,Debito x) val = if (x>val) then (Debito x):[] else []

--55.(b) Criar uma lista com as informações dos movimentos apenas daqueles cuja descrição corresponde ao segundo elemento da lista que constitui o segundo argumento da função
filtro :: Extracto -> [String] -> [Maybe (Data,Movimento)]
filtro (Ext _ []) _ = []
filtro _ [] = []
filtro _ [x] = []
filtro (Ext si ((d,s,Credito x):l)) ps = (procuraDes (d,s,Credito x) ps):filtro (Ext si l) ps
filtro (Ext si ((d,s,Debito x):l)) ps = (procuraDes (d,s,Debito x) ps):filtro (Ext si l) ps

--Função auxiliar para exercício 55.(b)
procuraDes :: (Data,String,Movimento) -> [String] -> Maybe (Data,Movimento)
procuraDes (_,_,_) [] = Nothing
procuraDes (d,s,m) (p:ps) = if (s==p) then Just (d,m) else procuraDes (d,s,m) ps

--55.(c) Devolve um par de floats que corresponde respetivamente aos totais de créditos e débitos
creDeb :: Extracto -> (Float,Float)
creDeb (Ext _ []) = (0.0,0.0)
creDeb (Ext a b) = (totalCre (Ext a b), totalDeb (Ext a b))

totalCre :: Extracto -> Float
totalCre (Ext _ []) = 0.0
totalCre (Ext si ((d,s,Credito x):l)) = x+totalCre (Ext si l)

totalDeb :: Extracto -> Float
totalDeb (Ext _ []) = 0.0
totalDeb (Ext si ((d,s,Debito x):l)) = x+totalDeb (Ext si l)

--55.(d) Devolver o Saldo final de uma conta, i.e ao volar do saldo inicial acrescentar os creditos e debitar o valor dos débitos
saldo :: Extracto -> Float
saldo (Ext _ []) = 0.0
saldo (Ext si m) = si+(totalCre (Ext si m))-(totalDeb (Ext si m))


data BTree a = Empty
             | Node a (BTree a) (BTree a)
     deriving Show

--56.(a) Calcular a altura de uma árvore
altura :: (BTree a) -> Int
altura Empty = 0
altura (Node a esq dir) = 1+(max (altura esq) (altura dir))

--             2
--        3          1   
--    5       4
--TESTE: altura (Node 2 (Node 3 (Node 5 Empty Empty) (Node 4 Empty Empty)) (Node 1 Empty Empty)) --> 3

--56.(b) Conta o número de nodos de uma árvore
contaNodos :: (BTree a) -> Int
contaNodos Empty = 0
contaNodos (Node a esq dir) = 1+(contaNodos esq)+(contaNodos dir)

--TESTE: contaNodos (Node 2 (Node 3 (Node 5 Empty Empty) (Node 4 Empty Empty)) (Node 1 Empty Empty)) --> 5

--56.(c) Calcula o número de nodos sem descendentes de uma dada árvore (i.e o nº de folhas)
folhas :: (BTree a) -> Int
folhas Empty = 0
folhas (Node a Empty Empty) = 1
folhas (Node a esq dir) = (folhas esq + folhas dir)

--TESTE: folhas (Node 2 (Node 3 (Node 5 Empty Empty) (Node 4 Empty Empty)) (Node 1 Empty Empty)) --> 3

--56.(d) Remove todos os elementos de uma árvore a partir de uma dada profundidade (i.e altura)
prune :: Int -> (BTree a) -> BTree a
prune _ Empty = Empty
--prune h (Node a esq dir) =  

--56.(e) Dado um caminho devolve a lista dos nodos por onde este caminho nos fez passar (False esquerda e True direita)
path :: [Bool] -> (BTree a) -> [a]
path [] _ = []
path _ (Node x Empty Empty) = []
path (b:bs) (Node a (Node c esq dir) (Node d esq' dir')) = if (b==False) then c:path bs (Node c esq dir) else d:path bs (Node d esq' dir')

--TESTE: path [False,True,True,False] (Node 2 (Node 3 (Node 5 Empty Empty) (Node 4 Empty Empty)) (Node 1 Empty Empty)) --> [3,4]

--56.(f) Dada uma árvore calcula a sua árvore simétrica
mirror :: (BTree a) -> BTree a
mirror Empty = Empty
mirror (Node a Empty Empty) = (Node a Empty Empty)
mirror (Node a esq dir) = (Node a (mirror dir) (mirror esq)) 

-- mirror (Node 2 (Node 3 (Node 5 Empty Empty) (Node 4 Empty Empty)) (Node 1 Empty Empty)) --> Node 2 (Node 1 Empty Empty) (Node 3 (Node 4 Empty Empty) (Node 5 Empty Empty))

--56.(g) Alterar a função zipWith para árvores binárias
zipWithBT :: (a -> b -> c) -> (BTree a) -> (BTree b) -> BTree c
zipWithBT _ Empty Empty = Empty
zipWithBT f (Node a Empty Empty) (Node b Empty Empty) = (Node (f a b) Empty Empty)
zipWithBT f (Node a esq dir) (Node b esq' dir') = (Node (f a b) (zipWithBT f esq esq') (zipWithBT f dir dir'))

-- zipWithBT (*) (Node 2 (Node 3 (Node 5 Empty Empty) (Node 4 Empty Empty)) (Node 1 Empty Empty)) (Node 2 (Node 3 (Node 5 Empty Empty) (Node 4 Empty Empty)) (Node 1 Empty Empty))
-- --> Node 4 (Node 9 (Node 25 Empty Empty) (Node 16 Empty Empty)) (Node 1 Empty Empty)

--56.(h) Generalizar a função unzip para árvores binárias em que cada nodo é um tuplo
--unzipBT :: (BTree (a,b,c)) -> (BTree a,BTree b,BTree c)
--unzipBT (Node (a,b,c) Empty Empty) = (Node a Empty Empty) (Node b Empty Empty) (Node c Empty Empty)
--unzipBT (Node (a,b,c) esq dir) = (Node a (unzip esq) (unzip dir)) (Node b (unzip esq) (unzip dir)) (Node c (unzip esq) (unzip dir))

--Árvores Binárias de Procura
--57.(a).i Calcular o menor elemento de uma árvore binária de procura não vazia
minimo :: (Ord a) => BTree a -> a
minimo (Node a Empty Empty) = a
minimo (Node a esq dir) = (minimo esq)

--TESTE: minimo (Node 3 (Node 2 (Node 1 Empty Empty) (Node 4 Empty Empty)) (Node 1 Empty Empty))

--57.(a).ii Remover o menor elemento de uma árvore de procura binária não vazia
semMinimo :: (Ord a) => BTree a -> BTree a
semMinimo (Node a Empty Empty) = Empty
semMinimo (Node a esq dir) = (Node a (semMinimo esq) dir)

--57.(a).iii Calcular com uma única travessia da árvore uma par com os resultados das duas funções anteriores (Poderíamos definir assim a função caso não pedisse com uma única travessia)
minSmin :: (Ord a) => BTree a -> (a,BTree a)
minSmin (Node a Empty Empty) = (a,Empty)
minSmin (Node a esq dir) = (minimo esq,(Node a (semMinimo esq) dir))

--COM UM ÚNICA TRAVESSIA

--57.(b).i Calcular o maior elemento de uma árvore binária de procura não vazia
maximo :: (Ord a) => BTree a -> a
maximo (Node a Empty Empty) = a
maximo (Node a esq dir) = (maximo dir)

--57.(b).ii Remover o maior elemento
semMaximo :: (Ord a) => BTree a -> BTree a
semMaximo (Node a Empty Empty) = Empty
semMaximo (Node a esq dir) = (Node a esq (semMaximo dir))

--57.(b).iii Calcular o resultado das duas funções anteriores numa só (Poderíamos definir assim a função caso não pedisse com uma única travessia)
maxSmax :: (Ord a) => BTree a -> (a,BTree a)
maxSmax (Node a Empty Empty) = (a,Empty)
maxSmax (Node a esq dir) = (maximo dir,semMaximo (Node a esq dir))

--COM UMA ÚNICA TRAVESSIA


type Aluno = (Numero,Nome,Regime,Classificacao)
type Numero = Int
--type Nome = String
data Regime = ORD | TE | MEL
     deriving (Eq,Show)
data Classificacao = Aprov Float
                   | Rep
                   | Faltou
     deriving (Eq,Show)
type Turma = BTree Aluno -- árvore binária de procura (ordenada por número)

--58.(a) Verifica se um aluno um dado número está inscrito
inscNum :: Numero -> Turma -> Bool
inscNum _ Empty = False
inscNum x (Node (n,_,_,_) Empty Empty) = if (x==n) then True else False
inscNum x (Node (n,_,_,_) esq dir) | x==n = True
                                   | x>n = inscNum x dir
                                   | otherwise = inscNum x esq

--TESTE: inscNum 69688 (Node (67688,"Joao",ORD,Rep) (Node (66068,"Carlao",TE,Aprov 15) (Node (5555,"Joana",TE,Aprov 12) Empty Empty) Empty) (Node (69688,"Luigi",ORD,Faltou) (Node (69688,"Luis",ORD,Faltou) Empty Empty) (Node (69688,"Ernesto Capacete",ORD,Aprov 19) Empty Empty)))
--RESPOSTA: True
--                    João
--           Carlao           Luigi
--     Joana             Luis        Ernesto Capacete

--58.(b) Verifica se um aluno com um dado nome está inscrito
inscNome :: Nome -> Turma -> Bool
inscNome _ Empty = False
inscNome x (Node (_,nom,_,_) Empty Empty) = if (x==nom) then True else False
inscNome x (Node (_,nom,_,_) esq dir) | (x==nom) = True
                                      | otherwise = (inscNome x esq || inscNome x dir)


--58.(c) Listar o número e o nome dos alunos trabalhadores estudantes
trabEst :: Turma -> [(Numero,Nome)]
trabEst Empty = []
trabEst (Node (num,nom,TE,_) esq dir) = [(num,nom)]++trabEst esq++trabEst dir
trabEst (Node (num,nom,ORD,_) esq dir) = trabEst esq++trabEst dir
trabEst (Node (num,nom,MEL,_) esq dir) = trabEst esq++trabEst dir

--TESTE: trabEst (Node (67688,"Joao",ORD,Rep) (Node (66068,"Carlao",TE,Aprov 15) (Node (5555,"Joana",TE,Aprov 12) Empty Empty) Empty) (Node (69688,"Luigi",ORD,Faltou) (Node (69688,"Luis",ORD,Faltou) Empty Empty) (Node (69688,"Ernesto Capacete",ORD,Aprov 19) Empty Empty)))
--RESPOSTA: [(66068,"Carlao"),(5555,"Joana")]

--58.(d) Calcular a classificação de um aluno devolver Nothing caso o aluno não exista
nota :: Numero -> Turma -> Maybe Classificacao
nota _ Empty = Nothing
nota x (Node (num,_,_,clss) Empty Empty) = if (x==num) then Just clss else Nothing
nota x (Node (num,_,_,clss) esq dir) | x==num = Just clss
                                     | x>num = nota x dir
                                     | otherwise = nota x esq
--TESTE: nota 5555 (Node (67688,"Joao",ORD,Rep) (Node (66068,"Carlao",TE,Aprov 15) (Node (5555,"Joana",TE,Aprov 12) Empty Empty) Empty) (Node (69688,"Luigi",ORD,Faltou) (Node (69688,"Luis",ORD,Faltou) Empty Empty) (Node (69688,"Ernesto Capacete",ORD,Aprov 19) Empty Empty)))
--RESPOSTA: Just (Aprov 12)

--58.(e) Calcula a percentagem de alunos que faltaram à avaliação
percFaltas :: Turma -> Float
percFaltas Empty = 0.0
percFaltas t = ((contaFaltas t)/(contaAlunos t))*100

--TESTE: percFaltas (Node (67688,"Joao",ORD,Rep) (Node (66068,"Carlao",TE,Aprov 15) (Node (5555,"Joana",TE,Aprov 12) Empty Empty) Empty) (Node (69688,"Luigi",ORD,Faltou) (Node (69688,"Luis",ORD,Faltou) Empty Empty) (Node (69688,"Ernesto Capacete",ORD,Aprov 19) Empty Empty)))
--RESPOSTA: 33.333336

--Funções auxiliares para o exercício 58.(e)
contaAlunos :: Turma -> Float
contaAlunos Empty = 0.0
contaAlunos (Node a esq dir) = 1+(contaAlunos esq)+(contaAlunos dir)

contaFaltas :: Turma -> Float
contaFaltas Empty = 0.0
contaFaltas (Node (_,_,_,Faltou) esq dir) = 1.0+(contaFaltas esq)+(contaFaltas dir) 
contaFaltas (Node (_,_,_,Rep) esq dir) = (contaFaltas esq)+(contaFaltas dir)
contaFaltas (Node (_,_,_,Aprov x) esq dir) = (contaFaltas esq)+(contaFaltas dir)

--58.(f) Calcular a média dos alunos aprovados
mediaAprov :: Turma -> Float
mediaAprov Empty = 0.0
mediaAprov t = (somaNotas t)/(contaAprovados t)

--TESTE: mediaAprov (Node (67688,"Joao",ORD,Rep) (Node (66068,"Carlao",TE,Aprov 15) (Node (5555,"Joana",TE,Aprov 12) Empty Empty) Empty) (Node (69688,"Luigi",ORD,Faltou) (Node (69688,"Luis",ORD,Faltou) Empty Empty) (Node (69688,"Ernesto Capacete",ORD,Aprov 19) Empty Empty)))
--RESPOSTA: 15.333333

--Funções auxiliares para o exercício 58.(f)
somaNotas :: Turma -> Float
somaNotas Empty = 0.0
somaNotas (Node (_,_,_,Aprov x) esq dir) = x+(somaNotas esq)+(somaNotas dir)
somaNotas (Node (_,_,_,Faltou) esq dir) = (somaNotas esq)+(somaNotas dir)
somaNotas (Node (_,_,_,Rep) esq dir) = (somaNotas esq)+(somaNotas dir)

contaAprovados :: Turma -> Float
contaAprovados Empty = 0.0
contaAprovados (Node (_,_,_,Aprov x) esq dir) = 1.0+(contaAprovados esq)+(contaAprovados dir)
contaAprovados (Node (_,_,_,Faltou) esq dir) = (contaAprovados esq)+(contaAprovados dir)
contaAprovados (Node (_,_,_,Rep) esq dir) = (contaAprovados esq)+(contaAprovados dir)

--58.(g) Calcular o racio de alunos aprovados por avaliados, i.e média de aprovados num total de alunos avaliados
aprovAv :: Turma -> Float
aprovAv t = (contaAprovados t)/((contaAlunos t)-(contaFaltas t))

--Funções auxiliares para exercício 58.(g) já definidas em questões anteriores

--UMA SÓ TRAVESSIA

type Dakar = [Piloto]
data Piloto = Carro Numero Nome Categoria
            | Mota Numero Nome Categoria
            | Camiao Numero Nome
           deriving (Eq,Show)
--type Numero = Int
--type Nome = String
data Categoria = Competicao | Maratona
     deriving (Eq,Show)

--59.(a) Inserir um piloto num dado dakar (ordenadamente)
inserePil :: Piloto -> Dakar -> Dakar
inserePil p [] = [p]
inserePil p ((Carro num nom catg):ps) = if (anteriorPalavra (extrairNome p) nom) then (p:(Carro num nom catg):ps) else (Carro num nom catg):inserePil  p ps
inserePil p ((Mota num nom catg):ps) = if (anteriorPalavra (extrairNome p) nom) then (p:(Mota num nom catg):ps) else (Mota num nom catg):inserePil p ps
inserePil p ((Camiao num nome):ps) = if (anteriorPalavra (extrairNome p) nome) then (p:(Camiao num nome):ps) else (Camiao num nome):inserePil p ps

--TESTE: inserePil (Carro 123 "Mauricio" Maratona) [(Carro 23 "Ana" Maratona),(Carro 1 "Bruno" Maratona),(Mota 43 "Marta" Maratona),(Camiao 99 "Ze")]
--RESULTADO: [Carro 23 "Ana" Maratona,Carro 1 "Bruno" Maratona,Mota 43 "Marta" Maratona,Carro 123 "Mauricio" Maratona,Camiao 99 "Ze"]

--Funções auxiliares para o exercício 59.(a)
extrairNome :: Piloto -> String
extrairNome (Carro a b c) = b
extrairNome (Mota a b c) = b
extrairNome (Camiao a b) = b

--Testa se uma palavra é anterior a outra segundo o alfabeto
anteriorPalavra :: String -> String -> Bool
anteriorPalavra _ [] = False
anteriorPalavra [] _ = False
anteriorPalavra (p:ps) (w:ws) | p<w = True
                              | p==w = anteriorPalavra ps ws
                              | otherwise = False 

--59.(b) Testar se um dakar está bem ordenado
--NOTA: Certamente existe um modo de encurtar esta questão, o longo código deve-se aos diferentes tipos de pilotos
dakarOrdenado :: Dakar -> Bool
dakarOrdenado [] = True
dakarOrdenado [x] = True 
dakarOrdenado ((Carro num nom catg):(Mota num' nom' catg'):ps) = if (anteriorPalavra nom nom') then dakarOrdenado (Mota num' nom' catg':ps) else False
dakarOrdenado ((Carro num nom catg):(Carro num' nom' catg'):ps) = if (anteriorPalavra nom nom') then dakarOrdenado (Carro num' nom' catg':ps) else False
dakarOrdenado ((Mota num nom catg):(Carro num' nom' catg'):ps) = if (anteriorPalavra nom nom') then dakarOrdenado (Carro num' nom' catg':ps) else False
dakarOrdenado ((Mota num nom catg):(Mota num' nom' catg'):ps) = if (anteriorPalavra nom nom') then dakarOrdenado (Mota num' nom' catg':ps) else False
dakarOrdenado ((Carro num nom catg):((Camiao num' nom'):ps)) = if (anteriorPalavra nom nom') then dakarOrdenado ((Camiao num' nom'):ps) else False
dakarOrdenado ((Mota num nom catg):((Camiao num' nom'):ps)) = if (anteriorPalavra nom nom') then dakarOrdenado ((Camiao num' nom'):ps) else False
dakarOrdenado ((Camiao num nom):((Carro num' nom' catg):ps)) = if (anteriorPalavra nom nom') then dakarOrdenado ((Carro num' nom' catg):ps) else False
dakarOrdenado ((Camiao num nom):((Mota num' nom' catg):ps)) = if (anteriorPalavra nom nom') then dakarOrdenado ((Mota num' nom' catg):ps) else False
dakarOrdenado ((Camiao num nom):((Camiao num' nom'):ps)) = if (anteriorPalavra nom nom') then dakarOrdenado ((Camiao num' nom'):ps) else False

--TESTAR: dakarOrdenado [Carro 23 "Ana" Maratona,Carro 1 "Bruno" Maratona,Mota 43 "Marta" Maratona,Carro 123 "Mauricio" Maratona,Camiao 99 "Ze"] --> True
--PS.: Todos os nomes têm de começar por letra maiúscula! (Caso contrário a função anteriorPalavra vai dar um valor errado)

data BTreeDakar a = Vazia | Nodo a (BTreeDakar a) (BTreeDakar a)
type DakarTree = BTreeDakar Piloto

--59.(c).i Calcular o piloto com o maior número
maiorNumPiloto :: DakarTree -> Maybe Piloto
maiorNumPiloto Vazia = Nothing
maiorNumPiloto (Nodo p _ Vazia) = Just p
maiorNumPiloto (Nodo p esq dir) = maiorNumPiloto(dir)

--TESTE: maiorNumPiloto (Nodo (Carro 50 "Ana" Maratona) (Nodo (Carro 40 "Bruno" Maratona) Vazia Vazia) (Nodo (Mota 90 "Marta" Maratona) Vazia (Nodo (Camiao 99 "Ze") Vazia Vazia)))
--RESULTADO: Just (Camiao 99 "Ze")

--59.(c).ii Calcular o piloto com o menor número
menorNumPiloto :: DakarTree -> Maybe Piloto
menorNumPiloto Vazia = Nothing
menorNumPiloto (Nodo p Vazia _) = Just p
menorNumPiloto (Nodo p esq dir) = menorNumPiloto(esq)

--TESTE: menorNumPiloto (Nodo (Carro 50 "Ana" Maratona) (Nodo (Carro 40 "Bruno" Maratona) Vazia Vazia) (Nodo (Mota 90 "Marta" Maratona) Vazia (Nodo (Camiao 99 "Ze") Vazia Vazia)))
--RESULTADO: Just (Carro 40 "Bruno" Maratona)

--59.(c).iii Lista os respetivos numeros e nomes dos pilotos das motas (ordenados por ordem crescente do número)
listaMotas :: DakarTree -> [(Numero, Nome)]
listaMotas Vazia = []
listaMotas (Nodo (Mota num nome cat) Vazia Vazia) = [(num,nome)]
listaMotas (Nodo (Carro num nome cat) Vazia Vazia) = []
listaMotas (Nodo (Camiao num nome) Vazia Vazia) = []
listaMotas (Nodo (Mota num nome cat) esq dir) = [(num,nome)]++(listaMotas esq)++(listaMotas dir)
listaMotas (Nodo (Carro num nome cat) esq dir) = (listaMotas esq)++(listaMotas dir)
listaMotas (Nodo (Camiao num nome) esq dir) = (listaMotas esq)++(listaMotas dir)

--TESTE: listaMotas (Nodo (Carro 50 "Ana" Maratona) (Nodo (Carro 40 "Bruno" Maratona) Vazia Vazia) (Nodo (Mota 90 "Marta" Maratona) Vazia (Nodo (Camiao 99 "Ze") Vazia Vazia)))
--RESULTADO: [(90,"Marta")]


data Contacto = Casa Integer
              | Trab Integer
              | Tlm Integer
              | Email String
        deriving (Eq,Show)
--type Nome = String

type Agenda = [(Nome, [Contacto])]

--60.(a) Acrescentar um dado nome e um e-mail acrescenta essa informação a uma dada agenda
acrescEmail :: Nome -> String -> Agenda -> Agenda
acrescEmail nome em [] = [(nome,[(Email em)])]
acrescEmail nome em ((n,c):ag) | (nome==n)&&((temEmail c)==False) = (n,(Email em):c):ag
                               | otherwise = (n,c):acrescEmail nome em ag

--TESTE: acrescEmail "Tone" "toneze@sapo.pt" [("Joao",[(Tlm 912323456),(Email "joao@sapo.pt")]),("Silva",[(Casa 254323456),(Email "joao@homail.pt")]),("Tone",[(Tlm 912323456),(Casa 255342423)]),("Joana",[(Trab 912323456),(Email "t@sapo.pt")])]
--RESPOSTA: [("Joao",[Tlm 912323456,Email "joao@sapo.pt"]),("Silva",[Casa 254323456,Email "joao@homail.pt"]),("Tone",[Email "toneze@sapo.pt",Tlm 912323456,Casa 255342423]),("Joana",[Trab 912323456,Email "t@sapo.pt"])]

--Função auxiliar para o exercício 60.(a)
temEmail :: [Contacto] -> Bool
temEmail [] = False
temEmail ((Email x):cs) = True
temEmail ((Casa x):cs) = temEmail cs
temEmail ((Trab x):cs) = temEmail cs
temEmail ((Tlm x):cs) = temEmail cs

--60.(b) Devolve uma lista com os e-mails associados a esse nome numa dada agenda
verEmails :: Nome -> Agenda -> Maybe [String]
verEmails _ [] = Nothing
verEmails nome ((n,c):ag) | (nome==n) = Just (extrairEmails c)
                          | otherwise = verEmails nome ag

--TESTE: verEmails "Joana" [("Joao",[(Tlm 912323456),(Email "joao@sapo.pt")]),("Silva",[(Casa 254323456),(Email "joao@homail.pt")]),("Tone",[(Tlm 912323456),(Casa 255342423)]),("Joana",[(Trab 912323456),(Email "t@sapo.pt")])]
--RESULTADO: Just ["t@sapo.pt"]

--Função auxiliar para o exercício 60.(b)
extrairEmails :: [Contacto] -> [String]
extrairEmails [] = []
extrairEmails ((Email x):cs) = x:extrairEmails cs
extrairEmails ((Casa x):cs) = extrairEmails cs
extrairEmails ((Trab x):cs) = extrairEmails cs
extrairEmails ((Tlm x):cs) = extrairEmails cs

--60.(c) Dada uma lista de contactos retorna todos os números (casa,móvel,trabalho) dessa lista
consTelefs :: [Contacto] -> [Integer]
consTelefs [] = []
consTelefs ((Email x):cs) = consTelefs cs
consTelefs ((Casa x):cs) = x:consTelefs cs
consTelefs ((Trab x):cs) = x:consTelefs cs
consTelefs ((Tlm x):cs) = x:consTelefs cs

--60.(d) A partir de um dado nome retorna o número de casa correspondente a esse nome numa dada agenda
casa :: Nome -> Agenda -> Maybe Integer
casa _ [] = Nothing
casa nome ((n,c):ag) | (nome==n) = (numeroCasa c)
                     | otherwise = casa nome ag

--Função auxiliar para o exercício 60.(d)
numeroCasa :: [Contacto] -> Maybe Integer
numeroCasa [] = Nothing
numeroCasa ((Email x):cs) = numeroCasa cs
numeroCasa ((Casa x):cs) = Just x
numeroCasa ((Trab x):cs) = numeroCasa cs
numeroCasa ((Tlm x):cs) = numeroCasa cs


type BD = [Video]
data Video = Filme String Int -- título, ano
           | Serie String Int Int -- título, temporada, episódio
           | Show String Int -- título, ano
           | Outro String
      deriving (Eq,Show)

--61.(a) Dada uma base de dados devolve uma lista com o titulo e respetivo ano dos espetaculos           
espetaculos :: BD -> [(String,Int)]
espetaculos [] = []
espetaculos ((Filme t a):bd) = (t,a):espetaculos bd
espetaculos ((Serie t tm e):bd) = espetaculos bd
espetaculos ((Show t a):bd) = (t,a):espetaculos bd
espetaculos ((Outro t):bd) = espetaculos bd

--TESTE: espetaculos [(Filme "Hari Pota" 2004),(Filme "Super-Homem" 1999),(Serie "Game Of Thrones" 2 11),(Serie "HIMYM" 8 12),(Outro "Palha"),(Show "Preco Certo" 1988)]
--RESPOSTA: [("Hari Pota",2004),("Super-Homem",1999),("Preco Certo",1988)]

--61.(b) Indica os filmes de um dado ano existentes numa dada base de dados
filmesAno :: Int -> BD -> [String]
filmesAno _ [] = []
filmesAno x ((Filme t a):bd) = if (x==a) then t:filmesAno x bd else filmesAno x bd
filmesAno x ((Serie t tm e):bd) = filmesAno x bd
filmesAno x ((Show t a):bd) = filmesAno x bd
filmesAno x ((Outro t):bd) = filmesAno x bd

--TESTE: filmesAno 1999 [(Filme "Hari Pota" 2004),(Serie "HIMYM" 8 11),(Filme "Super-Homem" 1999),(Serie "Game Of Thrones" 2 11),(Serie "HIMYM" 8 12),(Outro "Palha"),(Show "Preco Certo" 1988)]
--RESPOSTA: ["Super-Homem"]

--61.(c) Selecionar todos os vídeos da base de dados que não são filmes, séries ou shows
outros :: BD -> BD
outros [] = []
outros ((Filme t a):bd) = outros bd
outros ((Serie t tm e):bd) = outros bd
outros ((Show t a):bd) = outros bd
outros ((Outro t):bd) = (Outro t):outros bd 

--TESTE: outros [(Filme "Hari Pota" 2004),(Filme "Super-Homem" 1999),(Serie "Game Of Thrones" 2 11),(Outro "homemade chocolate cake"),(Serie "HIMYM" 8 12),(Outro "Palha"),(Show "Preco Certo" 1988)]
--RESPOSTA: [Outro "homemade chocolate cake",Outro "Palha"]

--61.(d) Quantos episódios de uma dada série existem numa dada base de dados
totalEp :: String -> BD -> Int
totalEp _ [] = 0
totalEp serie ((Serie t tm e):bd) = if (serie==t) then 1+totalEp serie bd else totalEp serie bd
totalEp serie ((Filme t a):bd) = totalEp serie bd
totalEp serie ((Show t a):bd) = totalEp serie bd
totalEp serie ((Outro t):bd) = totalEp serie bd

--TESTE: totalEp "HIMYM" [(Filme "Hari Pota" 2004),(Filme "Super-Homem" 1999),(Serie "HIMYM" 2 11),(Outro "homemade chocolate cake"),(Serie "HIMYM" 8 12),(Outro "Palha"),(Show "Preco Certo" 1988)]
--RESPOSTA: 2


type Biblio = [Livro]
data Livro = Romance Titulo Autor Ano Lido
           | Ficcao Titulo Autor Ano Lido
     deriving (Eq,Show)
type Titulo = String
type Autor = String
--type Ano = Int
data Lido = Sim
          | Nao
    deriving (Eq,Show)

--62.(a) Escrever uma função que testa se existem ou não livros repetidos (devolve False caso existam livros repetidos)
livrosRepetidos :: Biblio -> Bool
livrosRepetidos [] = True
livrosRepetidos (l:bib) = if (existeLivro l bib) then False else livrosRepetidos bib

--TESTE: livrosRepetidos [(Romance "Os Maias" "Eca" 1000 Nao),(Ficcao "A Cabana" "Jose" 2000 Sim),(Romance "Abelha Maia" "Quim" 1999 Nao),(Ficcao "A Cabana" "Jose" 2000 Sim)]
--RESULTADO: False

--Função auxiliar para o exercício 62.(a)
existeLivro :: Livro -> Biblio -> Bool
existeLivro _ [] = False
existeLivro l (l':b) = if (l==l') then True else existeLivro l b

--62.(b) Marcar um dado livro como lido numa dada biblioteca
lido :: Biblio -> Titulo -> Biblio
lido [] _ = []
lido ((Romance t a ano l):bib) tit = if (t==tit) then ((Romance t a ano Sim):bib) else ((Romance t a ano l):lido bib tit)
lido ((Ficcao t a ano l):bib) tit = if (t==tit) then ((Ficcao t a ano Sim):bib) else ((Ficcao t a ano l):lido bib tit)

--TESTE: lido [(Romance "Os Maias" "Eca" 1000 Nao),(Ficcao "A Gaivota" "Jose" 2000 Nao),(Romance "Abelha Maia" "Quim" 1999 Nao),(Ficcao "A Cabana" "Jose" 2000 Sim)] "A Gaivota"
--RESULTADO: [Romance "Os Maias" "Eca" 1000 Nao,Ficcao "A Gaivota" "Jose" 2000 Sim,Romance "Abelha Maia" "Quim" 1999 Nao,Ficcao "A Cabana" "Jose" 2000 Sim]

--62.(c) Calcular o número de livros lidos
livrosLidos :: Biblio -> Int
livrosLidos [] = 0
livrosLidos ((Romance t a ano l):bib) = if (l==Sim) then 1+livrosLidos bib else livrosLidos bib
livrosLidos ((Ficcao t a ano l):bib) = if (l==Sim) then 1+livrosLidos bib else livrosLidos bib

--TESTE: livrosLidos [(Romance "Os Maias" "Eca" 1000 Nao),(Ficcao "A Gaivota" "Jose" 2000 Nao),(Romance "Abelha Maia" "Quim" 1999 Nao),(Ficcao "A Cabana" "Jose" 2000 Sim)]
--RESULTADO: 1

--62.(d) Insere um livro recentemente adquirido (comprado) na biblio por ordem alfabética do autor e marca o mesmo livro como não lido (consideremos que é um Romance)
compra :: Titulo -> Autor -> Ano -> Biblio -> Biblio
compra tit aut ano ((Romance t a an l):bib) = if (anteriorPalavra aut a) then (Romance tit aut ano Nao):(Romance t a an l):bib else (Romance t a an l):compra tit aut ano bib   
compra tit aut ano ((Ficcao t a an l):bib) = if (anteriorPalavra aut a) then (Romance tit aut ano Nao):(Ficcao t a an l):bib else (Ficcao t a an l):compra tit aut ano bib   

--TESTE: compra "Wind" "Rafael" 1965 [(Romance "Os Maias" "Eca" 1000 Nao),(Ficcao "A Gaivota" "Jose" 2000 Nao),(Romance "Abelha Maia" "Quim" 1999 Nao),(Ficcao "A Cabana" "Rui" 2000 Sim)]
--RESULTADO: [Romance "Os Maias" "Eca" 1000 Nao,Ficcao "A Gaivota" "Jose" 2000 Nao,Romance "Abelha Maia" "Quim" 1999 Nao,Romance "Wind" "Rafael" 1965 Nao,Ficcao "A Cabana" "Rui" 2000 Sim]

--Função auxiliar para o exercício 62.(d)
--RECORDAR ...
--Função auxiliar do exercício 59.(a)
--Testa se uma palavra é anterior a outra segundo o alfabeto
--anteriorPalavra :: String -> String -> Bool

--Árvore binária de procura ordenada pelo título
data BTreeBib a = Nada | Book a (BTreeBib a) (BTreeBib a)
type BiblioTree = BTreeBib Livro

--62.(e).i Indicar se um dado livro foi ou não lido caso exista
info :: BiblioTree -> Titulo -> Maybe Lido
info Nada _ = Nothing
info (Book (Romance t a an l) esq dir) tit = if (tit==t) then Just l else if (anteriorPalavra tit t) then info esq tit else info dir tit
info (Book (Ficcao t a an l) esq dir) tit = if (tit==t) then Just l else if (anteriorPalavra tit t) then info esq tit else info dir tit

--TESTE: info (Book (Romance "Maias" "Eca" 1000 Nao) (Book (Ficcao "A Gaivota" "Jose" 2000 Nao) Nada Nada) (Book (Romance "Ovelha" "Quim" 1999 Nao) (Book (Ficcao "Nada" "Rui" 2000 Sim) Nada Nada) Nada)) "Ovelha"
--RESULTADO: Just Nao

--62.(e).ii Devolver a lista de livros de um dado autor
livroAutor :: BiblioTree -> Autor -> [Livro]
livroAutor Nada _ = []
livroAutor (Book (Romance t a an l) esq dir) autor = if (a==autor) then [(Romance t a an l)]++(livroAutor esq autor)++(livroAutor dir autor) else (livroAutor esq autor)++(livroAutor dir autor)
livroAutor (Book (Ficcao t a an l) esq dir) autor = if (a==autor) then [(Ficcao t a an l)]++(livroAutor esq autor)++(livroAutor dir autor) else (livroAutor esq autor)++(livroAutor dir autor)


--TESTE: livroAutor (Book (Romance "Maias" "Eca" 1000 Nao) (Book (Ficcao "A Gaivota" "Jose" 2000 Nao) Nada Nada) (Book (Romance "Ovelha" "Quim" 1999 Nao) (Book (Ficcao "Nada" "Jose" 2000 Sim) Nada Nada) Nada)) "Jose"
--RESULTADO: [Ficcao "A Gaivota" "Jose" 2000 Nao,Ficcao "Nada" "Jose" 2000 Sim]


--62.(e).iii Dada uma biblio ordenada pelo título do livro (não é relevante para a pesquisa) devolve uma lista com os livros não lidos
naoLidos :: BiblioTree -> [Livro]
naoLidos Nada = []
naoLidos (Book (Romance t a an l) esq dir) = if (l==Nao) then [(Romance t a an l)]++(naoLidos esq)++(naoLidos dir) else (naoLidos esq)++(naoLidos dir)
naoLidos (Book (Ficcao t a an l) esq dir) = if (l==Nao) then [(Ficcao t a an l)]++(naoLidos esq)++(naoLidos dir) else (naoLidos esq)++(naoLidos dir)

--TESTE: naoLidos (Book (Romance "Maias" "Eca" 1000 Nao) (Book (Ficcao "A Gaivota" "Jose" 2000 Nao) Nada Nada) (Book (Romance "Ovelha" "Quim" 1999 Nao) (Book (Ficcao "Nada" "Jose" 2000 Sim) Nada Nada) Nada))
--RESULTADO: [Romance "Maias" "Eca" 1000 Nao,Ficcao "A Gaivota" "Jose" 2000 Nao,Romance "Ovelha" "Quim" 1999 Nao]

--TESTE: naoLidos (Book (Romance "Maias" "Eca" 1000 Sim) (Book (Ficcao "A Gaivota" "Jose" 2000 Sim) Nada Nada) (Book (Romance "Ovelha" "Quim" 1999 Nao) (Book (Ficcao "Nada" "Jose" 2000 Sim) Nada Nada) Nada))
--RESULTADO: [Romance "Ovelha" "Quim" 1999 Nao]