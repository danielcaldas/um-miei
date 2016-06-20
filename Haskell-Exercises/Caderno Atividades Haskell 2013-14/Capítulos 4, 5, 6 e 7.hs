import Data.Char
import Data.List
import Data.Char (ord)
import Data.Char (chr)

----------------------------------------------------Parte 4 - FUNÇÕES RECURSIVAS QUE DEVOLVEM TUPLOS -----------------------------------------------------------

--29. Função que devolve dupla em que a primeira componente é o resultado da divisão inteira de dois número e a segunda é o resto dessa divisão
my_divMod :: Int -> Int -> (Int,Int)
my_divMod x y | x<y = (0,x)
              | x>=y = (1+r,q)
            where (r,q) = my_divMod (x-y) y

--30. Função que devolve um tuplo de tres inteiros em que a primeira componente são os números positivos na lista a segunda o númeor de zeros e a terceira os números negativos
nzp :: [Int] -> (Int,Int,Int)
nzp [] = (0,0,0)
nzp (x:xs) | x>0 = (1+p,z,n)
           | x<0 = (p,z,1+n)
           | otherwise = (p,1+z,n)
         where (p,z,n) = nzp xs

--31. Retirar os espaços existentes numa String no final devolver um par em que a primeira componente é a nova String e a segunda o comprimento dessa String
semSep :: String -> (String,Int)
sempSep [] = ([],0)
semSep [c] = if isSpace c then ([],0) else ([c],1)
semSep (c:cs) | isSpace c = (s,n)
              | otherwise = (c:s,1+n)
           where (s,n) = semSep cs

--32. Parte uma string em duas em que a primeira são as letras dessa string e a segunda os algarismos
digitAlpha :: String -> (String,String)
digitAlpha [] = ([],[])
digitAlpha [c] = if isAlpha c then ([c],[]) else if isDigit c then ([],[c]) else ([],[])
digitAlpha (c:cs) | isAlpha c = (c:l,n)
                  | isDigit c = (l,c:n)
                  | otherwise = (l,n)
               where (l,n) = digitAlpha cs

--TESTE: digitAlpha "danielcaldas1994@sapo_0.com" --> ("danielcaldassapocom","19940")

----------------------------------------------------Parte 5 - LISTAS POR COMPREENSÃO --------------------------------------------------------------------

--33.(a) Elementos de 1 a 20 que são simultaneamente divisíveis por 2 e por 3, são eles o 6, 12 e 18
div2e3 :: [Int] -> [Int]
div2e3 [] = []
div2e3 (x:xs) = if (mod x 2)==0 && (mod x 3)==0 then x:div2e3 xs else div2e3 xs

--TESTE: div2e3 [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20] --> [6,12,18]

--33.(b) Faz exatamente o mesmo que a função da alínea anterior mas com a seguinte ordem de acontecimentos: primeiro calculam-se os elementos pares de uma
--lista de 1 a 20 e depois aplica-se sobre essa lista a condição "mod x 3" que vai determinar dessa lista quais os divisíveis por três.

--33.(c) Quais as combinações possíveis existentes quando escolhidos dois numeros entre 1 e 20 de o resultado da sua soma ser 30
somaDa30 :: [(Int,Int)] -> [(Int,Int)]
somaDa30 [] = []
somaDa30 ((a,b):l) = if a+b==30 then (a,b):somaDa30 l else somaDa30 l

--Se o input desta função fossem todas as combinações possíveis de dois números entre 1 e 20 o output seria:
-- >[(10,20),(11,19),(12,18),(13,17),(14,16),(15,15),(16,14),(17,13),(18,12),(19,11),(20,10)]

--33.(d) Somatório dos números ímpares compreendidos entre 1 e x em que x vai incrementando uma unidade começando em 1 e terminando em 10
somas :: [Int] -> Int -> [Int]
somas _ 10 = []
somas l x = (somaImpares l x):somas l (x+1)

--Função auxiliar para resolução do exercício 33.(d)
somaImpares :: [Int] -> Int -> Int
somaImpares [] _ = 0
somaImpares (x:xs) k = if (x<=k) && (odd x) then x+somaImpares xs k else somaImpares xs k

--TESTE: somas [1,2,3,4,5,6,7,8,9,10] 1 --> [1,1,4,4,9,9,16,16,25]

--34.(a) Potências de base 2 com expoente compreendido entre 0 e 10 inclusive
--RESPOSTA: [power 2 y | y <- [1..10]] --> [2,4,8,16,32,64,128,256,512,1024]

--Funções auxiliares para resolução do exercício 34.(a)
--RELEMBRAR ...
--16.(c) A mesma função aplicada a uma lista de inteiros que são os diferentes expoentes, (não vamos necessitar dela para a resolução doeste exercício).
powerList :: Int -> [Int] -> [Int]
powerList _ [] = []
powerList x (e:es) = (power x e):powerList x es

--A função power calcula o expoente de um valor dado esse valor e o valor do expoente
power :: Int -> Int -> Int
power _ 0 = 1
power x e = x * power x (e-1)

--34.(b) Todas as combinações possíveis de dois números entre 1 e 6 cuja a sua soma seja 6
--RESPOSTA: [(x,y) | x <- [1..5], y <- [1..5], x+y==6] --> [(1,5),(2,4),(3,3),(4,2),(5,1)]

--34.(c) Criar uma lista de listas em que se efetua uma "contagem" de 1 a 5
--RESPOSTA: [criarLista 1 x | x <- [1..5]]

--Função auxiliar para o exercício 34.(c)
criarLista :: Int -> Int -> [Int]
criarLista a b = if a<=b then a:criarLista (a+1) b else []

--34.(d) Criar lista de listas com um número crescente de uns (1's)
--RESPOSTA: [listaUns 1 x | x <- [1..5]]

--Função auxiliar para resolução do exercício 34.(d)
--O argumento a é sempre 1
listaUns :: Int -> Int -> [Int]
listaUns a b = if a<=b then a:listaUns a (b-1) else []

--34.(e) A "regra de formação" desta lista é: x*i em que x é o atual elemento da litsa e i começa em 1 e incrementa uma unidade a cada inserção de um novo elem.
-- 1*1=1   1*2=2   2*3=6   6*4=24   24*5=120   120*6=720
--RESPOSTA: [lista_e x y | x <- [1..1], y <- [1..1]] !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

--Esta seria a função para criar a lista pedida mas neste caso pedem-nos a lista por compreensão pelo que a resposta é a de cima
lista_e :: Int -> Int -> [Int]
lista_e 0 _ = []
lista_e _ 0 = []
lista_e r 7 = []
lista_e r x = r*x:lista_e (r*x) (x+1)
--TESTE: lista_e 1 1


----------------------------------------------------------Parte 6 - FUNÇÕES DE ORDEM SUPERIOR ---------------------------------------------------------------

--35.(a) Função zipwith aplica uma dada função a dois elementos das mesmas posições de duas listas dadas
my_zipWith :: (a->b->c) -> [a] -> [b] -> [c]
my_zipWith _ [] _ = []
my_zipWith _ _ [] = []
my_zipWith f (a:as) (b:bs) = f a b:my_zipWith f as bs

--35.(b) Cria uma lista com os primeiros elementos dessa mesma lista que satisfazem um dado predicado
my_takeWhile :: (a->Bool) -> [a] -> [a]
my_takeWhile _ [] = []
my_takeWhile f (a:as) = if (f a) then a:my_takeWhile f as else []

--35.(c) Eliminar os primeiros elementos da lista que satisfazem um dado predicado
my_dropWhile :: (a->Bool) -> [a] -> [a]
my_dropWhile _ [] = []
my_dropWhile f (x:xs) = if (f x) then my_dropWhile f xs else (x:xs)

--35.(d) Devolver um tuplo de listas com os resultados das duas funções anteriores --!??!??!"!?"=!?!?!?!?!?!??!!?="?!="?!!?!?="?!="!?!="?!=?!?!=?!!?!?"
my_span :: (a->Bool) -> [a] -> ([a],[a]) 
my_span f [] = ([],[])
my_span f (x:xs) | f x = (x:c,d)
                 | otherwise = (c,(x:xs)++d)
                where (c,d) = my_span f xs

--36. Contar numa string as ocorrências consecutivas de cada carater e devolver uma lista de tuplos (carater,ocorrências)
agrupa :: String -> [(Char,Int)]
agrupa [] = []
agrupa (x:xs) = (x,length(takeWhile (==x) xs)+1):agrupa (dropWhile (==x) xs)

--37.(a).i Criar versão explicitamente recusriva da função criar linhas
criaLinhas :: [a] -> [b] -> [[(a,b)]]
criaLinhas _ [] = []
criaLinhas [] _ = []
criaLinhas (x:xs) y = listaLinha x y:criaLinhas xs y

--Função auxiliar para exercício 37.(a).i
listaLinha :: a -> [b] -> [(a,b)]
listaLinha _ [] = []
listaLinha x (y:ys) = (x,y):listaLinha x ys

--37.(a).ii versão de criar linhas com a função de ordem superior map
--crialinhas l1 l2 = map (f l2) l1
--where f l x = 

--37.(a).iii Função concat juntas um dado número de listas numa só
my_concat :: [[a]] -> [a]
my_concat [[]] = []
my_concat [x,y] = x++y
my_concat (l:ls) = l++my_concat ls

--37.(b).i Lista de pares em que primeira componente é sempre a mesma e a segunda é retirada de uma dada lista
criaPares :: a -> [b] -> [(a,b)]
criaPares _ [] = []
criaPares x (y:ys) = (x,y):criaPares x ys

--37.(b).ii Versão da função anterior com a função de ordem superior map
criaPares2 :: a -> [b] -> [(a,b)]
criaPares2 _ [] = []
criaPares2 a bs = map (acrescenta a) bs

acrescenta :: a -> b -> (a,b)
acrescenta a b = (a,b)

--37.(b).iii
--prod' l1 l2 = concat (map acrescenta l2 l1)!?!?!=?"=!??!=?"=

--38. Implementar a função indicativo sem recurso ao filter
indicativo :: String -> [String] -> [String]
indicativo _ [] = []
indicativo ind (t:telefs) = if concorda ind t then t:indicativo ind telefs else indicativo ind telefs

--Função auxiliar já definida no enunciado
concorda :: String -> String -> Bool
concorda [] _ = True
concorda (x:xs) (y:ys) = (x==y) && (concorda xs ys)
concorda (x:xs) [] = False

--39. Dado um número calcular a lista dos dígitos que compõem esse número
toDigits :: Int -> [Int]
toDigits x = if x>=10 then (mod x 10):toDigits (div x 10) else [x]

--40.(a) Dada uma lista de dígitos juntá-los formando um número. A lista é dada na ordem inversa ao número que queremos obter. Basicamente é o contrário da função
--anterior em 39.
fromDigits :: [Int] -> Int
fromDigits l = sum (my_zipWith (*) l h)
                    where h = listaBase10 (length l)

--Função auxiliar para resolução do exercício 40.(a)
listaBase10 :: Int -> [Int]
listaBase10 0 = []
listaBase10 1 = [1]
listaBase10 x = 10^(x-1):listaBase10 (x-1)

--40.(b) A mesma função usando recursividade explícita
fromDigits2 :: [Int] -> Int
fromDigits2 [] = 0
fromDigits2 [x] = x
fromDigits2 (x:xs) = (last xs)*10^(length xs)+fromDigits2 (reverse(tail(reverse (x:xs))))

--40.(c) A mesma função usando foldr
-- foldr (\x y -> 10*y+x) 0 l , onde l é a lista de dígitos que queremos transformar num número

--41.(a) Converter um número inteiro numa string
intStr :: Int -> String
intStr x = map intToDigit (reverse (toDigits x))

--41.(b) Converter a representação de um número, i.e uma string num valor decimal
strInt :: String -> Int
strInt [] = 0
strInt [x] = (digitToInt x)
strInt l = fromDigits (map digitToInt l)  

--42. Criar uma lista com todas as sub-listas possíveis derivadas de uma dada lista
subLists :: [a] -> [[a]]
subLists [] = [[]]
subLists (x:xs) = [x:subList | subList <- subLists xs] ++ subLists xs


type Jornada = [Jogo]
type Jogo = ((Equipa,Golos),(Equipa,Golos)) -- (eq. casa, eq. visitante)
type Equipa = String
type Golos = Int

--43.(a) Calcular o número total de golos de uma jornada
totalGolos :: Jornada -> Int
totalGolos [] = 0
totalGolos (((a,g1),(b,g2)):jor) = g1+g2+totalGolos jor

--43.(b) Listar os jogos de uma jornada com mais de um X dado número de golos marcados
numGolos :: Int -> Jornada -> [Jogo]
numGolos _ [] = []
numGolos x (j:jor) = if ((golosX j)>x) then j:numGolos x jor else numGolos x jor

--Função auxiliar para resolução do exercício 43.(b)
golosX :: Jogo -> Int
golosX ((a,g1),(b,g2)) = g1+g2

--43.(c) Completar a função dada de modo a que venceCasa calcule a lista das equipas que venceram em casa numa dada jornada
venceCasa :: Jornada -> [Equipa]
venceCasa j = map casa (filter vc j)

--Funções auxiliares que temos nós de implementar
--Testa se num dado jogo foi vencedora a equipa da casa
vc :: Jogo -> Bool
vc ((a,g1),(b,g2)) = if g1>g2 then True else False 

--A função casa casa devolve a equipa que jogou em casa de um dado jogo
casa :: Jogo -> Equipa
casa ((a,_),(_,_)) = a 

--43.(d) Calcular os pontos que cada equipa obteve numa jornada
pontos :: Jornada -> [(Equipa,Int)]
pontos [] = []
pontos (((a,g1),(b,g2)):jor) | g1>g2 = (a,3):(b,0):pontos jor
                             | g2>g1 = (a,0):(b,3):pontos jor
                             | otherwise = (a,1):(b,1):pontos jor

--43.(e) Selecionar os jogos da jornada que resultaram em empate
empates :: Jornada -> [Jogo]
empates [] = []
empates j = filter empatou j
              where empatou :: Jogo -> Bool
                    empatou ((a,g1),(b,g2)) = if g1==g2 then True else False

--44.(f) Completar a função de forma a que esta calcule o número de golos marcados numa jornada
golosMarcados :: Jornada -> Int
golosMarcados j = sum (map soma j)

--Função soma que temos de implementar
soma :: Jogo -> Int
soma ((a,g1),(b,g2)) = g1+g2

--44.(a) Apresentar versão alternativa usando recursividade explícita
--Conta o número de sub-listas vazias de uma lista dando como resultado uma lista com o n zeros sendo n o número de sub-listas vazias
func1 :: [[a]] -> [Int]
func1 l = map length (filter null l)

func1B :: Eq a => [[a]] -> [Int]
func1B [] = []
func1B [[]] = [0]
func1B (x:xs) = if x==[] then 0:func1B xs else func1B xs

--44.(b) Apresentar versão alternativa usando recursividade explícita
--Testa se duas listas têm exatamente os mesmos elementos, i.e são iguais
func2 :: Eq a => [a] -> [a] -> Bool
func2 l m = and (zipWith (==) l m)

func2B :: Eq a => [a] -> [a] -> Bool
func2B [] [] = True
func2B (x:xs) (y:ys) = if x==y then func2B xs ys else False

--44.(c) Apresentar versão alternativa usando recursividade explícita
--Calcula o comprimento da lista cujos elementos são maiores que um dado número x de uma dada lista l
func3 :: Ord a => a -> [a] -> Int
func3 x l = length (filter (>= x) l)

func3B :: Ord a => a -> [a] -> Int
func3B _ [] = 0
func3B x (y:ys) = if (y>=x) then 1+func3B x ys else func3B x ys


type Radar = [(Hora,Matricula,VelAutor,VelCond)]
type Hora = (Int,Int)
type Matricula = String -- matricula do carro em infraccao
type VelAutor = Float -- velocidade autorizada
type VelCond = Float -- velocidade do condutor

--45.(a) Aplicar a tolerância de 10% a todas as velocidades registadas pelo radar
toler10per :: Radar -> Radar
toler10per [] = []
toler10per rad = map aux rad
                   where aux :: (Hora,Matricula,VelAutor,VelCond) -> (Hora,Matricula,VelAutor,VelCond)
                         aux (h,m,va,vc) = (h,m,va,vc-(vc*0.1))

--45.(b) Infrações cometidas por um dado carro (dada uma matrícula) num dado dia
infCarro :: Matricula -> Radar -> Radar
infCarro _ [] = []
infCarro m rad = filter (igualMatricula m) rad
                   where igualMatricula :: Matricula -> (Hora,Matricula,VelAutor,VelCond) -> Bool
                         igualMatricula m (h,m1,va,vc) = if (m==m1) then True else False 

--45.(c) Calcular a lista com a matrícula e respetivo valor do excesso de velocidade
excessos :: Radar -> [(Matricula,Float)]
excessos [] = []
excessos rad = (map aux45c rad)
                 where aux45c :: (Hora,Matricula,VelAutor,VelCond) -> (Matricula,Float)
                       aux45c (h,m,va,vc) = (m,(vc-va))

--45.(d) Calcular o valor das multas acumulado dos excessos de velocidade nesse dia

--60Eur para excessos não superiores 20Km/h;
--120Eur entre 21 e 40 Km/h;
--300Eur entre 41 e 60 Km/h;
--500Eur superiores a 60Km/h;

valorMultas :: Radar -> Int
valorMultas [] = 0
valorMultas rad = sum (map darPrecoAosExcessos (excessos rad))
                       where darPrecoAosExcessos :: (Matricula,Float) -> Int
                             darPrecoAosExcessos (m,ex) | ex<=20          = 60
                                                        | ex>20 && ex<=40 = 120
                                                        | ex>40 && ex<=60 = 300
                                                        | ex>60           = 500

type RegAlcool = [(Nome,Sexo,Idade,NA)]
type Nome = String
type Sexo = Char -- 'M': Masculino 'F': Feminino
type Idade = Int
type NA = Float -- Nivel de Alcool

--46.(a) Devolver a lista de testes realizados apenas a pessoas com idades inferiores a 21 anos
menores21 :: RegAlcool -> RegAlcool
menores21 [] = []
menores21 ra = filter idade21 ra
                 where idade21 :: (Nome,Sexo,Idade,NA) ->Bool
                       idade21 (_,_,idade,_) = if (idade<21) then True else False

--46.(b) Calclular a partir de um dado registo o nome e o valor a pagar da multa consoante a gravidade da mesma (NA*100) isto se superior a 0.5
valoresMultas :: RegAlcool -> [(Nome,Float)]
valoresMultas [] = []
valoresMultas ra = map calularMulta (filter superiorAzeroPontoCinco ra)

--Funções auxiliares para exercício 46.(b)
superiorAzeroPontoCinco :: (Nome,Sexo,Idade,NA) -> Bool
superiorAzeroPontoCinco (_,_,_,na) = if na>0.5 then True else False

calularMulta :: (Nome,Sexo,Idade,NA) -> (Nome,Float)
calularMulta (n,_,_,na) = (n,(100*na))

--46.(c) Calcular média das idades das pessoas que fizeram o teste de alcoolemia
mediaIdades :: RegAlcool -> Int
mediaIdades ra = (div (sum (map extairIdade ra)) (length ra))
                     where extairIdade :: (Nome,Sexo,Idade,NA) -> Int
                           extairIdade (_,_,idade,_) = idade

--46.(d) Criar lista com os testes realizados apenas a mulheres (função de ordem superior)
apenasMulheres :: RegAlcool -> RegAlcool
apenasMulheres [] = []
apenasMulheres ra = filter serMulher ra
                       where serMulher :: (Nome,Sexo,Idade,NA) -> Bool
                             serMulher (_,s,_,_) = if (s=='F') then True else False

--46.(e) Produzir lista com nome da pessoa e uma string que indica o estado da condução "legal" ou "ilegal"
estadoCond :: RegAlcool -> [(Nome,String)]
estadoCond [] = []
estadoCond ra = map marcar ra
                 where marcar :: (Nome,Sexo,Idade,NA) -> (Nome,String)
                       marcar (n,s,i,na) = if na>0.5 then (n,"ilegal") else (n,"legal")

type Polinomio = [Monomio]
type Monomio = (Float,Float)

--47.(a) Indicar quantos monómios de um dado grau existem
conta :: Float -> Polinomio -> Float
conta _ [] = 0
conta n ((x,exp):pol) = if (n==exp) then 1+conta n pol else conta n pol

--47.(b) Selecionar os monómios de um dado polinómio superiores a um dado grau n
selgrau :: Float -> Polinomio -> Polinomio
selgrau _ [] = []
selgrau n pol = filter (maiorQueN n) pol
                   where maiorQueN :: Float -> Monomio -> Bool
                         maiorQueN  n (x,exp) = if exp>n then True else False

--47.(c) Calcular a derivada de um dado polinómio
deriv :: Polinomio -> Polinomio
deriv p = map deriva p
           where deriva :: Monomio -> Monomio
                 deriva (val,exp) = ((val*exp),(exp-1))

--47.(d) Calcular o valor do polinómio para um dado valor x
calcula :: Float -> Polinomio -> Float
calcula _ [] = 0
calcula x ((c,exp):pol) = (power2 x exp) + calcula x pol

--Função auxiliar para exercício 47.(d)
power2 :: Float -> Float -> Float
power2 _ 0 = 1
power2 x e = x * power2 x (e-1)

--47.(e) Retirar de um polinómio os monómios de grau 0 (com funções de ordem superior) 
simp :: Polinomio -> Polinomio
simp [] = []
simp pol = filter coefcDif0 pol
              where coefcDif0 :: Monomio -> Bool
                    coefcDif0 (c,_) = if (c==0) then True else False

--47.(f) Completar a função mult de forma a que esta multiplique um dado polinómio por um dado monómio
mult :: Monomio -> Polinomio -> Polinomio
mult (c,e) p = map (multiplicar (c,e)) p
                 where multiplicar :: Monomio -> Monomio -> Monomio
                       multiplicar (c1,e1) (c2,e2) | (c1==c2) = (c1,(e1+e2))
                                                   | (e1==e2) = ((c1*c2),e1)
                                                   | otherwise = (c2,e2)



---------------------------------------------------------------Parte 7 - PROBLEMAS NUMÈRICOS ----------------------------------------------------------------

--O seguinte tipo representa uma matriz
type Mat a = [[a]]

--48.(a) Testar se uma matriz está bem construída, i.e todas as linhas tem o mesmo número de elementos
dimOK :: Mat a -> Bool
dimOK [] = True
dimOK [l1] = True
dimOK (l1:l2:ls) = if (length l1)==(length l2) then dimOK (l2:ls) else False

--48.(b) Calcular a dimensão de uma matriz
dimMat :: Mat a -> (Int,Int)
dimMat [] = (0,0)
dimMat (l1:ls) = (((length ls)+1), (length l1)) --(linhas,colunas)

--48.(c) Adicionar duas matrizes
addMat :: Num a => Mat a -> Mat a -> Mat a
addMat x [] = x
addMat [] y = y
addMat (x:xs) (y:ys) = (zipWith (soma2) x y):addMat xs ys
                            where soma2 :: Num a => a -> a -> a
                                  soma2 x y = (x+y)

--48.(d) Calcular a transporta de uma matriz
transposta :: Mat a -> Mat a
transposta [] = []
transposta [[]] = []
transposta [[x,y,z]] = [[x],[y],[z]]
transposta [[x],[y],[z]] = [[x,y,z]] 
transposta mat = (pegaColuna mat):transposta (restantesColunas mat)

pegaColuna :: Mat a -> [a]
pegaColuna [] = []
pegaColuna [[x,y,z]] = [x]
pegaColuna (x:xs) = (head x):pegaColuna xs
-- HEAD!!!!!!!!!!=!=)|?=)|!?)|!
restantesColunas :: Mat a -> [[a]]
restantesColunas [] = []
restantesColunas [[x,y,z]] = [[y,z]]
restantesColunas (x:xs) = (tail x):restantesColunas xs

--48.(e) Calcular o produto entre duas matrizes
multMat :: Mat a -> Mat a -> Mat a
multMat _ [] = []
multMat [] _ = []
--multMat (l:ls) c = (linhaColunas l c):multMat ls c ?!=?"=!?"=

linhaColunas :: Num a => [a] -> Mat a -> [a]
linhaColunas _ [] = []
linhaColunas [] _ = []
linhaColunas x c = (sum (zipWith (*) x (pegaColuna c))):linhaColunas x (restantesColunas c) 

--49. O Crivo de Eratóstenes (Algoritmo):
-- Encontrar o primeiro número da lista, que será o número primo 2;
-- Remover da lista todos os múltiplos do número primo encontrado.
-- VER GIF. na wikipedia:    http://pt.wikipedia.org/wiki/Crivo_de_Erat%C3%B3stenes
crvE :: [Int] -> [Int]
crvE [] = []
crvE [x] = if primo x then [x] else [] 
crvE l = (head l): crvE (tail ((filter (naoMultiplo (head l)) l)))
             where naoMultiplo :: Int -> Int -> Bool
                   naoMultiplo x y = if (mod y x)/=0 then True else False

--Função auxiliar para exercício 49. (Vais nos ser útil para o testar a lista que contem um só elemento)
--16.(e) Verifica se um dado número é ou não primo
primo :: Int -> Bool
primo 1 = True
primo x
    | x>2 && (mod x 2) == 0 = False
    | x>3 && (mod x 3) == 0 = False
    | x>4 && (mod x 4) == 0 = False
    | x>5 && (mod x 5) == 0 = False
    | x>6 && (mod x 6) == 0 = False
    | x>7 && (mod x 7) == 0 = False
    | x>8 && (mod x 8) == 0 = False
    | x>9 && (mod x 9) == 0 = False
    | otherwise = True


type MSet a = [(a,Int)]
--50.(a) Acrescentar um elemento a um multiconjunto
add :: Eq a => a -> MSet a -> MSet a
add x [] = [(x,1)]
add x [(k,n)] = if (x==k) then [(k,(n+1))] else [(k,n),(x,1)]
add x ((y,n):m) = if (x==y) then (y,(n+1)):add x m else (y,n):add x m

--50.(b) Construír o multiconjunto dos elementos de uma lista
toMSet :: (Eq a) => [a] -> MSet a
toMSet [] = []
toMSet (x:xs) = (x,(conta x xs)):toMSet (filter (/=x) xs)
                 where conta :: Eq a => a -> [a] -> Int
                       conta _ [] = 0
                       conta x (y:ys) = if (x==y) then 1+conta x ys else conta x ys 

--50.(c) Determinar qual é o elemento mais frequente num multiconjunto
moda :: Ord a => MSet a -> a
moda [(x,n)] = x
moda mst = maior mst
              where maior :: Ord a => MSet a -> a
                    maior [(x,n)] = x
                    maior ((x,n):(y,m):ms) = if (x>y) then maior ((x,n):ms) else maior ((y,m):ms) 


--51. Esta é a reolução do ano anterior que está na drop relativa ao exercício 33. do respetivo caderno de atividades
--a
factoriza :: Integer -> [Integer]
factoriza 1 = []
factoriza n = multiple : (factoriza $ div n multiple) 
    where multiple = head $ filter ((==0).(mod n)) $ eratosthenesSieve n 


--b
mdcF :: Integer -> Integer -> Integer
mdcF x y = product (map (\c -> c ^ min (count (fromIntegral c) factX) (count (fromIntegral c) factY)) coefs)
    where factX = factoriza x
          factY = factoriza y
          coefs = map fromIntegral (nub $ factX ++ factY)

mmcF :: Integer -> Integer -> Integer 
mmcF x y = product (map (\c -> c ^ max (count (fromIntegral c) factX) (count (fromIntegral c) factY)) coefs)
    where factX = factoriza x
          factY = factoriza y
          coefs = map fromIntegral (nub $ factX ++ factY)

count x = length . filter (==x)


--c
mdc :: Integer -> Integer -> Integer
mdc x y 
    | x == y = x
    | x > y  = mdc (x - y) y
    | x < y  = mdc x (y - x)

mmc :: Integer -> Integer -> Integer
mmc x y = (x * y) `div` (mdc x y)



type Polinomio2 = [Coeficiente]
type Coeficiente = Float

--52.(a) Calcular o valor de um polinómio para um dado valor x
valorPolinomio :: Float -> Int -> Polinomio2 -> Float
valorPolinomio _ _ [] = 0.0
valorPolinomio x exp (c:cs) = (c*(x^exp))+valorPolinomio x (exp+1) cs 

--52.(b) Calcular a derivada de um dado polinómio
derivate :: Polinomio2 -> Polinomio2
derivate [] = []
derivate pol = tail (multiplicarExp pol 0.0)

--Função auxiliar para resolução do exercício 52.(b)
multiplicarExp :: Polinomio2 -> Float -> Polinomio2
multiplicarExp [] _ = []
multiplicarExp (c:cs) exp = (c*exp):multiplicarExp cs (exp+1)

--52.(c) Adição de Polinómios
adicaoPol :: Polinomio2 -> Polinomio2 -> Polinomio2
adicaoPol x [] = x
adicaoPol [] y = y
adicaoPol p1 p2 = (zipWith (+) p1 p2)++(restantesCoef p1 p2)

--Função auxiliar para resolução do exercício 52.(c)
restantesCoef :: Polinomio2 -> Polinomio2 -> Polinomio2
restantesCoef x [] = x
restantesCoef [] y = y
restantesCoef x y = restantesCoef (tail x) (tail y)
