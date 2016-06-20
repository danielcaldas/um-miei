import Data.Char
import Data.List

--EXAME 2012

--PARTE I

--1.
posneg :: [Int] -> (Int,Int)
posneg [] = (0,0)
posneg [x] = if (x>0) then (1,0) else if (x<0) then (0,1) else (0,0) -- caso o x seja 0
posneg (x:xs) | (x>0) = (1+p,n)
              | (x<0) = (p,1+n)
              | otherwise = (p,n)
            where (p,n) = posneg xs

--TESTE: posneg [1,2,5,4,0,9,0,(-2),(-1)]
--RESULTADO: (5,2)

--2.
tiraPrefixo :: String -> String -> Maybe String
tiraPrefixo _ [] = Nothing
tiraPrefixo [] s = Just s
tiraPrefixo (p:ps) (s:ss) = if (p==s) then tiraPrefixo ps ss else Nothing

--TESTE: tiraPrefixo "mal" "malcriado"
--RESULTADO: Just "criado"

--3.
--fun f l = product (map f (filter (>0) l))

fun :: (Int->Int) -> [Int] -> Int
fun _ [] = 0
fun f [x] = if (x>0) then (f x) else 0
fun f (x:xs) = if (x>0) then (f x)*fun f xs else fun f xs

--TESTE: product (map (*2) (filter (>0) [(-2),0,3,4,1]))
--RESULTADO: 96
--TESTE: fun (*2) [(-2),0,3,4,1]
--RESULTADO: 96

data ArvBin a = Vazia | Nodo a (ArvBin a) (ArvBin a)
        deriving (Eq,Ord)

--4.
insere :: (Ord a) => a -> ArvBin a -> ArvBin a
insere x Vazia = (Nodo x Vazia Vazia)
insere x (Nodo a esq dir) = if (x>a) then insere x dir else insere x esq


type Numero = Int
type Concorrentes = [(Numero, String)] -- numero e nome
type Prova = [(Numero, Float)] -- numero e tempo gasto na prova

--5.(a)
nomes :: Prova -> Concorrentes -> [(String,Float)]
nomes [] _ = []
nomes ((n,t):p) c = (fezProva (n,t) c)++nomes p c

fezProva :: (Numero,Float) -> Concorrentes -> [(String,Float)]
fezProva _ [] = []
fezProva (n,t) ((n',nome):c) = if (n==n') then [(nome,t)] else fezProva (n,t) c

--TESTE: nomes [(144,9.9),(69,10.1),(12,12.1),(77,9.1)] [(199,"Joao"),(12,"Armando"),(13,"Joaquina"),(144,"Josefino"),(77,"Calisto"),(11,"Fatima"),(69, "Marota"),(1,"Paulino")]
--RESULTADO: [("Josefino",9.9),("Marota",10.1),("Armando",12.1),("Calisto",9.1)]

--5.(b)
ordena :: Prova -> Prova
ordena [] = []
ordena [x] = [x]
ordena [(n,t),(n',t')] = if (n>n') then [(n',t'),(n,t)] else [(n,t),(n',t')]
ordena ((n,t):p) = ordenaAux (n,t) (ordena p)

ordenaAux :: (Int,Float) -> Prova -> Prova
ordenaAux x [] = [x]
ordenaAux (x,t) [(y,t')] = if (t>t') then [(y,t'),(x,t)] else [(x,t),(y,t')]
ordenaAux (x,t) ((y,t'):p) | t>t' = (y,t'):ordenaAux (x,t) p
                           | otherwise = ((x,t):(y,t'):p)

--TESTE: ordena [(144,9.9),(69,10.1),(12,12.1),(77,9.1),(133,9.2)]
--RESULTADO: [(77,9.1),(133,9.2),(144,9.9),(69,10.1),(12,12.1)]

--PARTE II

--1.(a)
camValido :: ArvBin a -> [Bool] -> Bool
camValido Vazia _ = False
camValido (Nodo x Vazia Vazia) [] = True
camValido (Nodo x esq dir) (b:bs) = if (b==False) then camValido esq bs else camValido dir bs

--TESTE: camValido (Nodo 10 (Nodo 5 (Nodo 4 Vazia Vazia) Vazia) (Nodo 3 (Nodo 2 Vazia Vazia) Vazia)) [True,False]
--RESULTADO: True
--TESTE: camValido (Nodo 10 (Nodo 5 (Nodo 4 Vazia Vazia) Vazia) (Nodo 3 (Nodo 2 Vazia Vazia) Vazia)) [False,True]
--RESULTADO: False

--1.(b)
--caminho :: (Eq a) => ArvBin a -> a -> Maybe [Bool]
--caminho Vazia _ = Nothing
--caminho a x = Just (caminhoAux (transformaProcura (a)) a)

listaArvore :: ArvBin a -> [a]
listaArvore Vazia = []
listaArvore (Nodo x Vazia Vazia) = [x]
listaArvore (Nodo x esq dir) = [x]++(listaArvore esq)++(listaArvore dir)

caminhoAux :: (Ord a) => ArvBin a -> a -> [Bool]
caminhoAux Vazia _ = []
caminhoAux (Nodo a Vazia Vazia) x = [] 
caminhoAux (Nodo a esq dir) x | x>a = (True:caminhoAux dir x)
                              | x<a = (False:caminhoAux esq x)
                              | otherwise = caminhoAux Vazia x --chegamos ao nodo destino

type Tabuleiro = [String]

--2.(a) Falha para testes na diagonal
rainhasOK :: Tabuleiro -> Bool
rainhasOK [] = True
rainhasOK (rainha:rainhas) = if (((contaColuna rainha)==1)&&((verificaLinhas rainha rainhas)==True) then rainhasOK rainhas else False
 
verificaLinhas :: String -> Tabuleiro -> Bool
verificaLinhas _ [] = True
verificaLinhas q (r:t) = if (q==r) then False else verificaLinhas q t

contaColuna :: String -> Int
contaColuna [] = 0
contaColuna (q:qs) = if (q=='Q') then 1+contaColuna qs else contaColuna qs

--TESTE: rainhasOK ["-Q--","---Q","Q---","--Q-"]
--RESULTADO: True
--TESTE: rainhasOK ["---Q---","-----Q-","Q------","--Q----","----Q--","------Q","-Q-----"]
--RESULTADO: True
--TESTE: rainhasOK ["---Q---","-----Q-","Q------","--Q----","----Q--","------Q","--Q----"]
--RESULTADO: False