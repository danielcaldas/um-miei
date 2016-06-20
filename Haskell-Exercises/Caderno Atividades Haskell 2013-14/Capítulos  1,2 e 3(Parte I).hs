import Data.Char
import Data.List
import Data.Char (ord)
import Data.Char (chr)

----------------------------------------------------------Parte 1 - FUNÇÕES NÃO RECURSIVAS ---------------------------------------------------------------------

--1.(a) Calcular perimetro de uma circunferência
perimetro :: Float -> Float
perimetro 0 = 0
perimetro r = 3.14*r*r

--1.(b) Calcular distancia entre dois pontos no plano
dist :: (Float, Float) -> (Float, Float) -> Float
dist (a1, a2) (b1, b2) = sqrt((a1-a2)^2 + (b1-b2)^2)

--1.(c) Devolver par com último e primeiro de uma lista
primUlt :: [Int]->(Int, Int)
primUlt [] = (0,0)
primUlt l = (head l, last l)

--1.(d) Testar se um numero é múltiplo de outro 
multiplo :: Int -> Int -> Bool
multiplo 0 _ = False
multiplo a b = if (mod b a)==0 then True else False

--1.(e) Retirar primeiro elemento caso comprimento da lista for ímpar
truncaImpar :: [Int] -> [Int]
truncaImpar [] = []
truncaImpar [x] = []
truncaImpar xs = if (mod (length xs) 2)==0 then xs else tail xs

--1.(f) Maior de dois elementos inteiros
max2 :: Int -> Int -> Int
max2 a b = if a>=b then a else b

--1.(g) Maior de tres elementos inteiros
max3 :: Int -> Int -> Int -> Int
max3 a b c = if (a>=b && a>=c) then a else
	                                  if (b>a && b>=c) then b else c
--2.Testar desigualdade triangular
desTri :: Int -> Int -> Int -> Bool
desTri a b c = if (a+b==c || a+c==b || b+c==a) then True else False

type Ponto = (Float, Float)
--3.(a)Determinar comprimento dos lados de um triângulo
comprimentos :: Ponto -> Ponto -> Ponto -> (Float, Float, Float)
comprimentos a b c = (dist a b, dist b c, dist c a)

--3.(b)Calcular perimetro de um triangulo
pTri :: Ponto -> Ponto -> Ponto -> Float
pTri a b c = (dist a b) + (dist b c) + (dist c a)

--3.(c) Dados dois pontos correspondentes à diagonal de um triângulo devolver lista dos 4 pontos que formam o retângulo
const_ret :: Ponto -> Ponto -> [Ponto]
const_ret (x1, y1) (x2, y2) = if (x1<x2 && y1<y2) then (x1, y1):(x1, y2):(x2, y2):(x2, y1):[]
		                                          else if (x1<x2 && y1>y2) then (x1, y2):(x1, y1):(x2, y1):(x2, y2):[]
                                                  else if (x2<x1 && y2<y1) then (x2, y2):(x2, y1):(x1, y1):(x1, y2):[]
                                                  else if (x2<x1 && y2>y1) then (x2, y1):(x2, y2):(x1, y2):(x1, y1):[]
                                                  else []

--4. Calcular o número de raízes reais de um polinómio
raizes :: Int -> Int -> Int -> Int
raizes a b c = if r==0 then 1 else if r>0 then 2 else 0
               where r = (b^2)-4*a*c

--5. Usar função de 4. para calcular o valor das raízes reais, evolver valores numa lista
raizes_aux :: Float -> Float -> Float -> Float
raizes_aux a b c = if (r==0||r>0) then r else 0
               where r = sqrt((b^2)-4*a*c)

calc_raizes :: Float -> Float -> Float -> [Float]
calc_raizes a b c = let r = (raizes_aux a b c)
                     in ((-b+r)/(2*a)):((-b-r)/(2*a)):[]

--6. Versão alternativa do ex.4 as funções recebem tuplos em vez de receberem os elementos separadamente
raizes_aux_vers2 :: (Float,Float,Float) -> Float
raizes_aux_vers2 (a,b,c) = if (r==0||r>0) then r else 0
               where r = sqrt((b^2)-4*a*c)

calc_raizes_vers2 :: (Float,Float,Float) -> [Float]
calc_raizes_vers2 (a,b,c) = let r = (raizes_aux_vers2 (a,b,c))
                     in ((-b+r)/(2*a)):((-b-r)/(2*a)):[]

--7.(a)
my_isLower :: Char -> Bool
my_isLower c = if (c>='a' && c<='z') then True else False

--7.(b)
my_isDigit :: Char -> Bool
my_isDigit x = if (x>='0' && x<='9') then True else False

--7.(c)
my_isAlpha :: Char -> Bool
my_isAlpha c = if (c>='A' && c<='Z') then True else False 

--7.(d)
--Muito Importante!: funções ord e chr pré-definidas para manipulação de valores do ASCII
my_toUpper :: Char -> Char
my_toUpper c = if my_isLower(c) then chr(ord(c)-32) else c

my_toLower :: Char -> Char
my_toLower c = if my_isAlpha(c) then chr(ord(c)+32) else c

--7.(e)
my_intToDigit :: Int -> Char
my_intToDigit x = if x==0 then '0' else if x==1 then '1' else if x==2 then '2' else if x==3 then '3'
	              else if x==4 then '4' else if x==5 then '5' else if x==6 then '6' else if x==7 then '7'
	              else if x==8 then '8' else '9'

--7.(f)
my_digitToInt :: Char -> Int
my_digitToInt x = if x=='0' then 0 else if x=='1' then 1 else if x=='2' then 2 else if x=='3' then 3
	              else if x=='4' then 4 else if x=='5' then 5 else if x=='6' then 6 else if x=='7' then 7
	              else if x=='8' then 8 else 9 

--8.(a) Recebe uma string como argumento e testa se o primeiro carater é uma letra maiúscula
primMai :: [Char] -> Bool
primMai [] = False
primMai (x:xs) = if (isUpper x)==True then True else False 

--8.(b) Testa se o segundo carater é uma letra maiúscula 
segMai :: [Char] -> Bool
segMai [] = False
segMai l = if isUpper(head (tail l)) then True else False

type Hora = (Int, Int)
--9.(a) Testa se um par de inteiros é uma hora válida
timeValid :: Hora -> Bool
timeValid (h,m) = if (h>=0 && h<24 && m>=0 && m<60) then True else False

--9.(b) Testa se uma hora é ou não depois de outra (se a hora B é seguinte à hora A)
timeNext :: Hora -> Hora -> Bool 
timeNext (h1,m1) (h2,m2) = if h2>h1 then True else if (h2==h1 && m2>m1) then True else False

--9.(c) Converter horas para minutos
horasParaMin :: Hora -> Int
horasParaMin (h,m) = h*60+m

--9.(d) Converter minutos em horas
minParaHoras :: Int -> Hora
minParaHoras m = validateTime ((div m 60), (mod m 60))

--9.(e) Calcular a diferença entre duas horas e apresentar o resultadoem minutos
difTime :: Hora -> Hora -> Int
difTime (h1,m1) (h2,m2) = if timeNext (h1,m1) (h2,m2) then horasParaMin((h2-h1), (m2-m1)) else horasParaMin((h1-h2), (m1-m2))

--9.(f) Adicionar um dado numero de minutos a uma dada hora
addTime :: Hora -> Int -> Hora
addTime hora min = let hora_em_min = horasParaMin hora
                   in validateTime (minParaHoras(hora_em_min+min))

--Função auxiliar para exercício 9. resolve "out-of-bounds" em valores para horas
validateTime :: Hora -> Hora
validateTime (h,m) = if h>24 then validateTime (h-24,m) else (h,m) 

------------------------------------------------------------Parte 2 - FUNÇÕES RECURSIVAS -------------------------------------------------------------------

--11.(a) A funA percorre uma lista de Floats elemento a elemento verificando se cada um deles é um número não negativo caso seja insere-o na lista de destino,
       --i.e a lista de Floats com todos os elementos não negativos da lista original

--11.(b) A funB dá como resultado o produtório dos elementos de uma lista de floats

--11.(c) A funC resulta no somatório do quadrado e cada elemento da lista i.e [x, y, ... n] = x^2+y^2+...+n^2

--11.(d) A funD cria um nova lista a partir de uma original com os elementos pares da primeira

--11.(e) O resultado é false pois as chamadas recursivas de p subtraem a cada chamada da função 2 ao valor de x até este ser igual a 0 ou 1, no caso do 5,
       -- I.5-2=3  II. 3-2=1--> FALSE

--11.(f) O resultado é "certo", a função inverte uma cadeia de carateres "otrec":  o (t:rec) -> (t:o) "rec" -> r (e:"c") -> (r:"to") "ce" ... certo

--12.(a) Produz uma lista em que cada elemento é o dobro do inserido inicialmente
dobros :: [Float] -> [Float]
dobros [] = []
dobros (x:xs) = x*2:dobros(xs)

--12.(b) Calcular o número de vezes que um carater ocorre numa string
ocorre :: Char -> String -> Int
ocorre _ [] = 0
ocorre c (l:h) = if c==l then 1+ocorre c h else ocorre c h

--12.(c) Devolve o primeiro numero de uma lista de inteiros que é maior que um dado número n
pmaior :: Int -> [Int] -> Int
pmaior n [] = n
pmaior n (x:xs) = if x>n then x else pmaior n xs

--12.(d) Teste se uma lista de inteiros tem elementos repetidos
repetidos :: [Int] -> Bool
repetidos [x] = True
repetidos (x:xs) = if (ocorreInt x xs)>0 then False else repetidos xs

--Auxiliar para a função do exercício 12.(d)
ocorreInt :: Int -> [Int] -> Int
ocorreInt _ [] = 0
ocorreInt c (l:h) = if c==l then 1+ocorreInt c h else ocorreInt c h

--12.(e) Recebe uma string e devolve uma lista de inteiros com os algarismos que ocorrem na string pela ordem que foram encontrados
nums :: String -> [Int]
nums [] = []
nums (l:ls) = if (my_isDigit l) then (digitToInt l):nums(ls) else nums ls

--12.(f) Devolve os últimos três elementos de uma lista se a lista tiver menos de três elementos devolve a própria lista
tresUlt :: [a] -> [a]
tresUlt [] = []
tresUlt [a] = [a]
tresUlt [a,b] = [a,b]
tresUlt [a,b,c] = [a,b,c]
tresUlt l = tresUlt (tail l)

--12.(g) Calcula a lista com os elementos que ocorrem nas posições ímpares
posImpares :: [a] -> [a]
posImpares [] = []
posImpares [x] = [x]
posImpares (x:xs) = if (mod (length xs) 2)/=0 then x:posImpares xs else posImpares xs

--12.(h) Soma todos os elementos negativos de uma lista
somaNeg :: [Int] -> Int
somaNeg [] = 0
somaNeg (x:xs) = if (x<0) then x+somaNeg xs else somaNeg xs

--12.(i) Recebe uma string e seleciona dessa string os caratéres que são algarismos
soDigitos :: [Char] -> [Char]
soDigitos [] = []
soDigitos (l:h) = if my_isDigit l then l:soDigitos h else soDigitos h

--12.(j) Conta quantos caratéres de uma string são letras minúsculas
minusculas :: [Char] -> Int
minusculas [] = 0
minusculas (l:h) = if my_isLower l then 1+minusculas h else minusculas h


--12.(k) Ordenar uma lista (ordem crescente) (Algoritmo de ordenação Quicksort)
quicksort :: (Ord a) => [a] -> [a]  
quicksort [] = []  
quicksort (x:xs) =   
    let smallerSorted = quicksort [a | a <- xs, a <= x]  
        biggerSorted = quicksort [a | a <- xs, a > x]  
    in  smallerSorted ++ [x] ++ biggerSorted


type Jogo = (String,Int,String,Int) --(Equipa da Casa, Golos, Equipa Visitante, Golos)
type Equipa = String
--13.(a) Ver quantos golos marcou uma equipa num jogo
golosEquipa :: Jogo -> Equipa -> Int
golosEquipa (a,g1,b,g2) e 
          | e==a && g1/=0 = g1
          | e==b && g2/=0 = g2
          | otherwise = -1

--13.(b) Devolver um carater consoante o resultado do jogo: '1': ganha a equipa da casa, '2': ganha a equipa visitante, 'x': empate
resJogo :: Jogo -> Char
resJogo (a,g1,b,g2)
      | g1>g2 = '1'
      | g2>g1 = '2'
      | otherwise = 'x'

--13.(c) Versão diferente da 13.(b) devolve uma frase para informar o resultado do jogo
resJogo2 :: Jogo -> String
resJogo2 (a,g1,b,g2)
       | g1>g2 = "Ganhou a Equipa da Casa"
       | g2>g1 = "Ganhou a Equipa Visitante"
       | otherwise = "O resultado foi um empate"

--13.(d) Dada uma lista de jogos (diga-se jornada) devolve o número de empates
contaEmpates :: [Jogo] -> Int
contaEmpates [] = 0
contaEmpates (j:js)
           | resJogo(j)=='x' = 1+contaEmpates(js)
           | otherwise = contaEmpates(js)

--13.(e) Devolve o número de jogos em que foram marcados X golos (introduzidos pelo utilizador)
jogosComXGolos :: [Jogo] -> Int -> Int
jogosComXGolos [] _ = 0
jogosComXGolos (j:js) x
             | totalGolos(j)==x = 1+jogosComXGolos(js) x
             | otherwise = jogosComXGolos(js) x

--Função auxiliar contadora de golos para exercício 13.(e)
totalGolos :: Jogo -> Int
totalGolos (a,g1,b,g2) = g1+g2

--13.(f) Conta o numero de jogos em que saíu vitoriosa a equipa visitante
contaVitVis :: [Jogo] -> Int
contaVitVis [] = 0
contaVitVis (j:js) = if resJogo2(j)=="Ganhou a Equipa Visitante" then 1+contaVitVis(js) else contaVitVis(js)

--Ponto já definido em cima (Abcissa, Ordenada)
type Circulo = (Ponto,Float) --(Centro, Raio)

--14.(a) Testa se um ponto se encontra fora de um círculo
--Função dist do exercício 1.(b) calcula a distância entre dois pontos no plano
fora :: Ponto -> Circulo -> Bool
fora p (c,r) = if (dist p c)>r then True else False

--14.(b) Dada uma lista de pontos conta o número de pontos que se encontram fora de um dado círculo 
filtraFora :: Circulo -> [Ponto] -> Int
filtraFora _ [] = 0
filtraFora c (p:ps) = if (fora p c) then 1+filtraFora c ps else filtraFora c ps

--14.(c) Testa se um ponto se encontra dentro de um círculo
dentro :: Ponto -> Circulo -> Bool
dentro p (c,r) = if (dist p c)<r then True else False

--14.(d) Conta o número de pontos que se encontram dentro de um dado círculo
filtraDentro :: Ponto -> [Circulo] -> Int
filtraDentro _ [] = 0
filtraDentro p (c:cs) = if (dentro p c) then 1+filtraDentro p cs else filtraDentro p cs

--15.(a) Testa se um dado retângulo é um quadrado
type Retangulo = (Ponto,Ponto)

quadrado :: Retangulo -> Bool
quadrado ((x1,y1),(x2,y2)) = if (dist (x1,y1) (x1,y2))==(dist (x1,y1) (x2,y1)) then True else False

--Conta o numero de quadrados dada uma lista de retângulos
contaQuadrados :: [Retangulo] -> Int
contaQuadrados [] = 0
contaQuadrados (r:rs) = if (quadrado r) then 1+contaQuadrados rs else contaQuadrados rs

--15.(b) Roda um retângulo 90º (tentar projetar em papel os retângulos num referencial)
roda :: Retangulo -> Retangulo
roda ((x1,y1),(x2,y2)) = ((y1, x1),(y2,x2))

--Aplica a função de cima a uma lista de retângulos
rodaTudo :: [Retangulo] -> [Retangulo]
rodaTudo [] = []
rodaTudo (r:rs) = (roda r):rodaTudo rs

--15.(c) Calcula a área de um retângulo
area :: Retangulo -> Float
area ((x1,y1),(x2,y2)) = (abs(x1-x2)*abs(y1-y2))

--Aplica a função de cima a uma lista de retângulos
areaTotal :: [Retangulo] -> Float
areaTotal [] = 0.0
areaTotal (r:rs) = (area r) + areaTotal rs

--15.(d) (Penso que seja isto mas não tenho a certeza do que se pretende obter quando se "escala")
escala :: Float -> Retangulo -> Retangulo
escala e (p1, (x,y)) = (p1, (x*e, y*e))

--Aplica a função de cima a uma lista de retângulos
escalaTudo :: Float -> [Retangulo] -> [Retangulo]
escalaTudo _ [] = []
escalaTudo e (r:rs) = (escala e r):escalaTudo e rs

--16.(a) Multiplicar dois números inteiros através de somas sucessivas
(><) :: Int -> Int -> Int
(><) a b = if b==1 then a else a + (><) a (b-1)

--16.(b) Faz a divisão inteira entre dois números a e b através de subtrações sucessivas
my_div :: Int -> Int -> Int
my_div a b
     | b==0 = 0
     | b>a = 0
     | b==a = 1
     | otherwise = if a<=0 then 0 else 1+my_div (a-b) b

--Resto da divisão inteira pelo mesmo processo da função my_div
my_mod :: Int -> Int -> Int
my_mod a b
     | b==0 = 0
     | b>a = 0
     | b==a = 0
     | otherwise = a-(b*(my_div a b)) 

--16.(c) Calcula o expoente de um dado inteiro e dado também o respetivo expoente através de multiplicações sucessivas
power :: Int -> Int -> Int
power _ 0 = 1
power x e = x * power x (e-1)

--16.(d) Calcular quantos bits 1 são necessários para representar um dado número (i.e contar o número de uns da representação binária do número dado)
uns :: Int -> Int
uns 0 = 0
uns x = (contaUns (intToBin x))

--TESTES: uns 131 --> 3
--        uns 1025 --> 2
--        uns 31 --> 5

--Funções auxiliares para o exercício 16.(d)
contaUns :: [Int] -> Int
contauns [] = 0
contaUns [b] = if (b==1) then 1 else 0
contaUns (b:bs) = if (b==1) then 1+contaUns bs else contaUns bs

--Converter um número decimal para um número binário (com número de bits variável)
intToBin :: Int -> [Int]
intToBin 0 = [0]
intToBin x
       | x>0 = reverse (binarioPositivo x)
       | otherwise = reverse (complementoPara2 (complementoPara1 (binarioPositivo (abs x))))

--TESTE: intToBin 131 --> [0,1,0,0,0,0,0,1,1]
--       intToBin 7   --> [0,1,1,1] 

binarioPositivo :: Int -> [Int]
binarioPositivo 0 = []
binarioPositivo x = if (mod x 2)==0 then 0:binarioPositivo (div x 2) else 1:binarioPositivo (div x 2)


--O Complemento para 1 consiste em somar 1 à representação binária (MÉTODO: até encontrar o primeiro bit a 0 mudar todos os bits que estão a 1 para 0, quando
--encontrarmos o primeiro bit a 0 alterámos o seu valor para 1 e os restantes bits permanecem inalterados)
--Algumas destas funções tratam o problema um pouco na perspetiva da programação imperativa pois a certos momentos tratamos a lista como um array e o comprimento
--de uma sub-lista como um índice de consulta a uma certa posição do array, estas não são práticas comuns na programação funcional!
complementoPara1 :: [Int] -> [Int]
complementoPara1 [] = []
complementoPara1 (0:bs) = (1:bs)
complementoPara1 bin = (mudarBitsAte (detetaPrimeiroZero bin) bin)

--Deteta a primeira ocorrência de um 0 na lista devolvendo o comprimento da sub-lista precedente que funciona como o "índice do array"
detetaPrimeiroZero :: [Int] -> Int
detetaPrimeiroZero [] = (-1)
detetaPrimeiroZero (b:bs) = if (b==0) then (length bs) else detetaPrimeiroZero bs

--Até um certo "índice" altera os valores dos bits a 1 para 0
mudarBitsAte :: Int -> [Int] -> [Int]
mudarBitsAte _ [] = []
mudarBitsAte i (b:bs) = if (length bs)/=i then 0:mudarBitsAte i bs else (1:bs)

--O Complemento para 2 consiste apenas em trocar os bits a 0 por 1 e vice-versa
complementoPara2 :: [Int] -> [Int]
complementoPara2 [] = []
complementoPara2 (b:bs) = if (b==0) then 1:complementoPara2 bs else 0:complementoPara2 bs

---------------------------------------------------------------------------------------------------------------------------------------------------------------
--Não é um exercício, mas esta função é fixe porque calcula o valor de uma dada representação binária (lista de Int's), apenas para representações binárias que começam por 0 
binToInt :: [Int] -> Int
binToInt [] = 0
binToInt (x:xs) = if x==1 then (power 2 (length xs))+binToInt xs else binToInt xs

--TESTES: binToInt [1,0,0,0,0,0,1,1] --> 131
--        binToInt [0,0,0,0,0,1,1,1] --> 7
---------------------------------------------------------------------------------------------------------------------------------------------------------------



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

--Aplica a função de cima a uma lista de números
primos :: [Int] -> [Bool]
primos [] = []
primos (x:xs) = if (primo x) then True:primos xs else False:primos xs

--17.(a) Cria a lista dos primeiros componentes de uma lista de pares
primeiros :: [(a,b)] -> [a]
primeiros [(a,_)] = [a]
primeiros ((a,_):l) = a:primeiros l

--17.(b) Verifica se um dado valor se encontra numa primeira posição de um par de uma lista
nosPrimeiros :: (Eq a) => a -> [(a,b)] -> Bool
nosPrimeiros a [] = False
nosPrimeiros a ((a1,_):l) = if (a==a1) then True else nosPrimeiros a l

--17.(c) Calcula a primeira componente mais pequena da lista (mínimo)
minFst :: (Ord a) => [(a,b)] -> a
minFst [(a,_)] = a
minFst [(a,b),(c,d)] = if a>c then c else a
minFst ((a,b):(c,d):l) = if a>c then minFst ((c,d):l) else minFst((a,b):l)

--17.(d) Calcula a segunda componente mais pequena da lista (mínimo)
sndMinFst :: (Ord a) => [(a,b)] -> b
sndMinFst [(a,b)] = b
sndMinFst [(a,b),(c,d)] = if a>c then d else b
sndMinFst ((a,b):(c,d):l) = if a>c then sndMinFst ((c,d):l) else sndMinFst((a,b):l)

--17.(e) Ordena uma lista tendo em conta as segundas componentes como fator de ordenação
ordenaSnd :: Ord b => [(a,b)] -> [(a,b)]  
ordenaSnd [] = []  
ordenaSnd l = buscaDuplas (quicksort (extraiSegundaComponete l)) l

--Funções auxiliares para resolução do exercício 17.(e)
extraiSegundaComponete :: [(a,b)] -> [b]
extraiSegundaComponete [] = []
extraiSegundaComponete ((x,y):l) = y:extraiSegundaComponete l

buscaDuplas :: Eq b => [b] -> [(a,b)] -> [(a,b)]
buscaDuplas _ [] = []
buscaDuplas [] _ = []
buscaDuplas (n:ns) l = (aux n l):buscaDuplas ns l

--Busca numa lista de duplas [(a,b)] a dupla com um dado campo b igual a um n dado
aux :: Eq b => b -> [(a,b)] -> (a,b)
aux b ((x,y):l) = if (b==y) then (x,y) else aux b l 

--TESTE: ordena [(10,21),(3,55),(66,3),(120,0)] --> [(120,0),(66,3),(10,21),(3,55)]



---------------------------------------------------------------------Parte 3 - PROBLEMAS --------------------------------------------------------------------
type Aluno = (Numero,Nome,ParteI,ParteII)
type Numero = Int
type Nome = String
type ParteI = Float
type ParteII = Float

type Turma = [Aluno]

--18.(a) Verifica se uma dada turma é ou não válida (CRITÉRIO: tem números repetidos?)
turmaValida :: Turma -> Bool
turmaValida [] = True
turmaValida ((num,nome,p1,p2):t) = if ((ocorreNumero num ((num,nome,p1,p2):t))==1) && (p1>=0 && p1<=12) && (p2>=0 && p2<=8) then turmaValida t else False

--Função auxiliar para o exercício 18.(a)
ocorreNumero :: Numero -> Turma -> Int
ocorreNumero _ [] = 0
ocorreNumero n ((num,_,_,_):t) = if n==num then 1+ocorreNumero n t else ocorreNumero n t

--18.(b) Cria uma lista com os alunos aprovados à U.C
aprovados :: Turma -> Turma
aprovados [] = []
aprovados ((num,nome,p1,p2):t) = if (p1>=8) && (p1+p2)/2>=9.5 then (num,nome,p1,p2):aprovados t else aprovados t

--18.(c) Cria uma lista com as notas finais dos alunos aprovados
notaFinalDosAprovados :: Turma -> [(Nome,Numero,Float)]
notaFinalDosAprovados [] = []
notaFinalDosAprovados ((num,nome,p1,p2):t) = if (aprovado (num,nome,p1,p2)) then let nota_final = ((p1+p2)/2)
	                                                                             in (nome,num,nota_final):notaFinalDosAprovados t else notaFinalDosAprovados t
--Função auxiliar para exercício 18.(c)
aprovado :: Aluno -> Bool
aprovado (num,nome,p1,p2) = if (p1>=8) && (p1+p2)/2>=9.5 then True else False

--18.(d) Calcula a média dos alunos aprovados
mediaDosAprovados :: Turma -> Float
mediaDosAprovados [] = 0.0
mediaDosAprovados t = totalNotas t / contaAprovados t

--Funções auxiliar exercício 18.(d)
contaAprovados :: Turma -> Float
contaAprovados [] = 0.0
contaAprovados ((num,nome,p1,p2):t) = if (p1>=8) && (p1+p2)/2>=9.5 then 1 + contaAprovados t else contaAprovados t

--Devolve um Float que corresponde ao somatório de todas as notas dos alunos aprovados
totalNotas :: Turma -> Float
totalNotas [] = 0.0
totalNotas ((num,nome,p1,p2):t) = if (aprovado (num,nome,p1,p2)) then let nota_final = ((p1+p2)/2)
	                                                                             in nota_final + totalNotas t else totalNotas t

--18.(e) Devolve o Aluno da turma com a nota final mais elevada
alunoComNotaMaisAlta :: Turma -> Aluno
alunoComNotaMaisAlta [(a,b,c,d)] = (a,b,c,d)
alunoComNotaMaisAlta (a1:a2:t) = if (calculaNota a1 > calculaNota a2) then alunoComNotaMaisAlta(a1:t) else alunoComNotaMaisAlta(a2:t)

--Função auxiliar exercício 18.(e)
calculaNota :: Aluno -> Float
calculaNota (num,nome,p1,p2) = ((p1+p2)/2)


-- Hora (Int,Int) já foi definido atrás
type Etapa = (Hora,Hora)
type Viagem = [Etapa]

--Funções para manipular o tipo Horas já implementadas no exercício

--timeValid :: Hora -> Bool
--timeNext :: Hora -> Hora -> Bool  #Testa se uma hora é ou não depois de outra ( timeNext A B -> se a hora B é seguinte à hora A)#
--horasParaMin :: Hora -> Int
--minParaHoras :: Int -> Hora
--difTime :: Hora -> Hora -> Int
--addTime :: Hora -> Int -> Hora
--validateTime :: Hora -> Hora

--19.(a) Testa se uma etapa está bem construída (CRITÉRIO: tempo de chegada > tempo de partida e horas válidas)
etapaValida :: Etapa -> Bool
etapaValida (h1,h2) = if timeValid h1 && timeValid h2 && timeNext h1 h2 then True else False

--19.(b) Testa se uma viagem está bem construída (CRITÉRIOS: cada etapa é válida e cada etapa seguinte começa numa hora seguinte a ter terminado a anterior)
viagemValida :: Viagem -> Bool
viagemValida [] = True
viagemValida [(h1,h2)] = if etapaValida (h1,h2) then True else False
viagemValida ((h1, h2):(h1',h2'):v) = if etapaValida (h1,h2) && etapaValida (h1',h2') && timeNext h2 h1' then viagemValida ((h1',h2'):v) else False

--19.(c) Calcular a hora de partida e de chegada de uma dada viagem
partidaChegada :: Viagem -> (Hora,Hora)
partidaChegada v = (horaPartida v, horaChegada v)

--Funções auxiliares para exercício 19.(c)
horaPartida :: Viagem -> Hora
horaPartida [(h1,h2)] = h1
horaPartida ((h1, h2): v) = h1

horaChegada :: Viagem -> Hora
horaChegada [(h1,h2)] = h2
horaChegada ((h1,h2):v) = b
                          where (a,b) = last v

--19.(d) Dada uma viagem válida calcula o tempo efetivo da mesma (vou calcular em minutos e horas), i.e as "pausas entre etapas não contam"
--Obs.: Desnecessário em minutos e horas mas disponibilizo as duas versões
tempoEfetivo :: Viagem -> (Hora,Int)
tempoEfetivo [] = ((0,0),0)
tempoEfetivo v = ((minParaHoras (tempoEfetivoMin v)), (tempoEfetivoMin v))

tempoEfetivoMin :: Viagem -> Int
tempoEfetivoMin [] = 0
tempoEfetivoMin [(h1,h2)] = (difTime h2 h1) 
tempoEfetivoMin ((h1,h2):v) = (difTime h2 h1)+tempoEfetivoMin v

--19.(e) Calcula o tempo de espera, as tais "pausas entre etapas" que referi na alínea anterio
tempoTotalEspera :: Viagem -> Int
tempoTotalEspera [] = 0
tempoTotalEspera [(h1,h2)] = 0
tempoTotalEspera ((h1, h2):(h1',h2'):v) = (difTime h2 h1')+tempoTotalEspera ((h1',h2'):v)

--19.(f) Calcular o tempo total da viagem o tempo de espera mais o tempo efetivo de viagem
tempoTotalDaViagem :: Viagem -> Int
tempoTotalDaViagem [] = 0
tempoTotalDaViagem v = tempoEfetivoMin v + tempoTotalEspera v

--Ponto (Float,Float) já definido anteriormente
type Retangulo2 = (Ponto, Float, Float) --Canto superior esquerdo, largura e altura
type Triangulo = (Ponto,Ponto,Ponto)
type Poligonal = [Ponto]

-- RELEMBRAR... Do exercício 1.(b)
--dist :: (Float, Float) -> (Float, Float) -> Float
--dist (a1, a2) (b1, b2) = sqrt((a1-a2)^2 + (b1-b2)^2)

--20.(a) Calcular o comprimento de uma linha poligonal
comprimentoLinhaPoligonal :: Poligonal -> Float
comprimentoLinhaPoligonal [p] = 0.0
comprimentoLinhaPoligonal [p1,p2] = (dist p1 p2) 
comprimentoLinhaPoligonal (p1:p2:ps) = (dist p1 p2) + comprimentoLinhaPoligonal (p2:ps)

--20.(b) Transformar um triângulo numa linha poligonal
trianguloToPoligonal :: Triangulo -> Poligonal
trianguloToPoligonal (a,b,c) = [a,b,c,a]

--20.(c) Transformar um retângulo numa linha poligonal
retanguloToPoligonal :: Retangulo2 -> Poligonal
retanguloToPoligonal ((x,y), larg, alt) =  [(x,y-alt), (x,y), (x+larg,y), (x+larg,y-alt), (x,y-alt)] --cantos: inf. esq., sup. esq., sup. dir., inf. dir.,
                                                                                                     --inf. esq. (novamente) respetivamente

--20.(d) Testar se uma linha poligonal é ou não fechada
poligonalFechada :: Poligonal -> Bool
poligonalFechada [] = True
poligonalFechada [p] = True
poligonalFechada pol = if (head pol)==(last pol) then True else False

--20.(e) Através de uma linha poligonal fechada criar triângulos cuja soma das suas áreas seja a mesma que a da área delimitada pela linha poligonal fechada
triangula :: Poligonal -> [Triangulo]
triangula [] = []
triangula (p1:p2:p3:ps) = (p1,p2,p3):triangula (ps)

--20.(f) Calcular uma área de uma linha poligonal fechada
areaPoligonal :: Poligonal -> Float
areaPoligonal [] = 0.0
areaPoligonal (p1:p2:p3:ps) = (areaTriangulo (p1,p2,p3)) + areaPoligonal (ps)


--Função auxiliar à resolução do exercício 20.(f) dada no enunciado
areaTriangulo :: Triangulo -> Float
areaTriangulo (x,y,z)
    = let a = dist x y
          b = dist y z
          c = dist z x
          s = (a+b+c)/2 --semi-perímetro
      in --fórmula de Heron
         sqrt(s*(s-a)*(s-b)*(s-c))

--20.(g) Mover uma linha poligonal no referencial dado um ponto de destino
-- 1º detetar as diferenças na abcissa e ordenada do primeiro ponto e de seguida aplicar essas diferenças a todos os pontos da linha
moverPoligonal :: Poligonal -> Ponto -> Poligonal
moverPoligonal [] _ = []
--moverPoligonal ((x,y):ps) (x1,y1) = 

buscaDeslocamento :: Poligonal -> Ponto -> Ponto
buscaDeslocamento [] _ = (0,0)
buscaDeslocamento ((x,y):p) (x1,y1) = (x1-x,y1-y)

--20.(h) Calcular uma nova linha poligonal em que multiplicamos por 2 o comprimento de cada segmento NÃO FUNCIONA ESTA MERDA
zoom2 :: Poligonal -> Poligonal
zoom2 [] = []
zoom2 [p] = [p]
zoom2 [(x1,y1),(x2,y2)] = [(x1,y1),(x2,y2+2)]
zoom2 [(x1,y1),(x2,y2),(x3,y3)] = [(x1,y1),(x2,y2+2),(x3,y3)]
zoom2 ((x1,y1):(x2,y2):pol) = (x1,y1):(x2,y2+2):(zoom2 ((x2,y2+2):pol))

type Stock = [(Produto,Preco,Quantidade)]
type Produto = String
type Preco = Float
type Quantidade = Float

--21.(a)
--i. Determinar o número de produtos em Stock
quantosStock :: Stock -> Int
quantosStock [] = 0
quantosStock s = length s

--ii. Indica a quantidade de um dado produto em Stock
emStock :: Produto -> Stock -> Quantidade
emStock _ [] = 0.0
emStock p ((pro,pre,q):s) = if p==pro then q else emStock p s

--iii. Indica o preço e a quantidade de um dado produto em Stock
consulta :: Produto -> Stock -> (Preco,Quantidade)
consulta _ [] = (0.0,0.0)
consulta p ((pro,pre,q):s) = if p==pro then (pre,q) else consulta p s

--iv. Construír uma tabela de preços i.e uma lista com tuplos produto,preço
tabPrecos :: Stock -> [(Produto,Preco)]
tabPrecos [] = [("Não existem produtos em stock",0.0)]
tabPrecos ((pro,pre,q):s) = (pro,pre):tabPrecos s 

--v. Calcular o valor total dos produtos em stock (em unidades monetárias) preço x quantidade
valorTotal :: Stock -> Preco
valorTotal [] = 0.0
valorTotal ((_,pre,q):s) = (pre*q) + valorTotal s

--vi. Aumentar uma dada percentagem a todos os preços
inflacao :: Float -> Stock -> Stock 
inflacao _ [] = []
inflacao i ((pro,pre,q):s) = (pro,pre*(1+i),q):inflacao i s

--vii. Indica o produto e respetivo preço mais barato em stock
oMaisBarato :: Stock -> (Produto,Preco)
oMaisBarato [] = ("Stock vazio",0.0)
oMaisBarato [(pro1,pre1,q1),(pro2,pre2,q2)] = if pre1<pre2 then (pro1,pre1) else (pro2,pre2)
oMaisBarato ((pro1,pre1,q1):(pro2,pre2,q2):s) = if pro1<pro2 then oMaisBarato ((pro1,pre1,q1):s) else oMaisBarato ((pro2,pre2,q2):s)

--viii. Constrói uma lista com os produtos mais caros, entada-se por "mais caros", superiores a um dado preço
maisCaros :: Preco -> Stock -> [Produto]
maisCaros _ [] = []
maisCaros pBase ((pro,pre,q):s) = if pre>pBase then pro:maisCaros pBase s else maisCaros pBase s

type ListaCompras = [(Produto,Quantidade)]
--21.(b)
--i. Verificar se é possível comprar todos os produtos (em respetivas quantidades) em lista de compras
verifLista :: ListaCompras -> Stock -> Bool
verifLista [] _ = True
verifLista _ [] = False
verifLista ((pl,ql):lc) s = if (estaEmStock pl ql s) then verifLista lc s else False

--Função auxilar para função do exercício 21.(b).i.
estaEmStock :: Produto -> Quantidade -> Stock -> Bool
estaEmStock _ _ [] = False
estaEmStock pl ql ((pro,pre,q):s) = if (pl==pro) && (q>ql) then True else  estaEmStock pl ql s

--ii. Contruír a lista com os pedidos que não foram satisfeitos
falhas :: ListaCompras -> Stock -> ListaCompras
falhas lc [] = lc
falhas [] _ = []
falhas ((p,q):lc) s = if (estaEmStock p q s) then falhas lc s else (p,q):falhas lc s

--iii. Calcula o custo total de uma dada lista de compras
custoTotal :: ListaCompras -> Stock -> Float
custoTotal [] _ = 0.0
custoTotal _ [] = 0.0
custoTotal ((p,q):lc) s = (buscaPreco p q s) + custoTotal lc s

--Função auxiliar para exercício 21.(b).iii.
buscaPreco :: Produto -> Quantidade -> Stock -> Float
buscaPreco _ _ [] = 0.0
buscaPreco _ 0 _ = 0.0
buscaPreco p q ((pro,pre,qua):s) = if (p==pro) && (qua>q) then q*pre else buscaPreco p q s

--iv. Criar a partir de uma lista de compras duas em que na primeira o preço dos produtos fica abaixo de um dado preço e na segunda são superiores a esse mesmo preço
partePreco :: Preco -> ListaCompras -> Stock -> (ListaCompras,ListaCompras)
partePreco _ [] _ = ([],[])
partePreco _ _ [] = ([],[])
partePreco preLim lc s = let menores = buscaInferiores preLim lc s
                             maiores = buscaSuperioresOuIguais preLim lc s
                         in (menores,maiores)

--Funções auxiliares para exercício 21.(b).iv
buscaInferiores :: Preco -> ListaCompras -> Stock -> ListaCompras
buscaInferiores _ [] _ = []
buscaInferiores _ _ [] = []
buscaInferiores preLim ((p,q):lc) s = if (estaEmStock p q s) && (buscaPreco p q s)<preLim then (p,q):buscaInferiores preLim lc s else buscaInferiores preLim lc s

buscaSuperioresOuIguais :: Preco -> ListaCompras -> Stock -> ListaCompras
buscaSuperioresOuIguais _ [] _ = []
buscaSuperioresOuIguais _ _ [] = []
buscaSuperioresOuIguais preLim ((p,q):lc) s = if (estaEmStock p q s) && (buscaPreco p q s)>=preLim then (p,q):buscaSuperioresOuIguais preLim lc s
	                                                                                                else buscaSuperioresOuIguais preLim lc s
type MSet a = [(a,Int)]
--22
--(a) Verificar se um dado elemento pertence a um multiconjunto (o elemento é a primeira componente de cada tuplo da lista)
my_elem :: Eq a => a -> MSet a -> Bool
my_elem _ [] = False
my_elem x ((m,n):ms) = if x==m then True else my_elem x ms

--(b). Coverter uma lista num multiconjunto
converte :: Eq a => [a] -> MSet a
converte [] = []
converte l = (trasnformaEmMSet (criaLista l) l)

--Funções auxiliares para o exercício 22.(b)
--Cria uma lista com os elementos da lista dada mas retirando da lista original elementos repetidos
criaLista :: Eq a => [a] -> [a]
criaLista [] = []
criaLista (c:cs) = if (ocorreElem c cs)==0 then c:criaLista cs else criaLista cs

--Devolve o número de vezes que um elemento ocorre numa lista
ocorreElem :: Eq a => a -> [a] -> Int
ocorreElem _ [] = 0
ocorreElem x (k:ks) = if x==k then 1+ocorreElem x ks else ocorreElem x ks

--Recebe duas listas  , uma primeira com os elementos que ocorrem na segunda lista e outra com a lista original onde existem elementos repetidos
trasnformaEmMSet :: Eq a => [a] -> [a] -> MSet a
trasnformaEmMSet _ [] = []
trasnformaEmMSet [] _ = []
trasnformaEmMSet (x:xs) l = (x,(ocorreElem x l)):trasnformaEmMSet xs l

--22.(c) Calcular o tamanho de um multi-conjunto
my_size :: MSet a -> Int
my_size [] = 0
my_size ((m,n):ms) = n+my_size ms

--22.(d) Calcular a união de dois multi-conjuntos
my_union :: Eq a => MSet a -> MSet a -> MSet a
my_union [] [] = []
my_union [] b = []
my_union a [] = a
my_union a b = a++b

--22.(e) Eliminar um elemento de um dado multi-conjunto
elimina :: Eq a => a -> MSet a -> MSet a
elimina _ [] = []
elimina x [(m,n)] = if x==m then [] else [(m,n)]
elimina x ((m,n):ms) = if x==m then elimina x ms else (m,n):elimina x ms

--22.(f) Ordenar um múltiplo conjunto pelo número crescente de ocorrências
--RELEMBRAR... quicksort :: (Ord a) => [a] -> [a] exercício 12.(k), vamos utilizar esta função na função ordena
ordena :: Eq a => MSet a -> MSet a
ordena [] = []
ordena mset = (aux22f (quicksort(listaOcorrencias mset)) mset)

--Funções auxiliares para exercício 22.(f)
listaOcorrencias :: MSet a -> [Int]
listaOcorrencias [] = []
listaOcorrencias ((m,n):ms) = n:listaOcorrencias ms

--A partir de uma lista de ocorrênias ordenadas coloca num mset o elemento que corresnponde aquele número de ocorrências
aux22f :: Eq a => [Int] -> MSet a -> MSet a
aux22f [] _ = []
aux22f _ [] = []
aux22f (x:xs) ms = let (a,b) = (buscaMultConj x ms)
                   in (a,b):aux22f xs ms

--Busca um determinado elemento do multi-conjunto por um certo número de ocorrências
buscaMultConj :: Int -> MSet a -> (a,Int)
buscaMultConj x ((m,n):ms) = if x==n then (m,n) else buscaMultConj x ms

--22.(g) Insere um elemento num multi-conjunto
insere :: Eq a => a -> MSet a -> MSet a
insere a ms = if (existeElem a ms) then insereElemExistente a ms else insereElemNovo a ms

--Funções auxiliares para  exercício 22.(g)
--Verificar se um elemento existe num Mset
existeElem :: Eq a => a -> MSet a -> Bool
existeElem _ [] = False
existeElem a ((m,n):ms) = if (a==m) then True else existeElem a ms

--Função de inserção caso se trate de uma inserção de um elemento existente
insereElemExistente :: Eq a => a -> MSet a -> MSet a
insereElemExistente a [] = [(a,1)]
insereElemExistente a [(m,n)] = if (a==m) then [(m,n+1)] else [(m,n)]
insereElemExistente a ((m,n):ms) = if (a==m) then (m,n+1):insereElemExistente a ms else (m,n):insereElemExistente a ms

--Função de inserção caso se tarte de uma inserção de um elemento novo
insereElemNovo :: Eq a => a -> MSet a -> MSet a
insereElemNovo a [] = [(a,1)]
insereElemNovo a [(m,n)] = if (a==m) then [(m,n+1)] else [(m,n),(a,1)]
insereElemNovo a ((m,n):ms) = if (a==m) then (m,n+1):insereElemNovo a ms else (m,n):insereElemNovo a ms

--22.(h) Criar uma lista com os elementos que têm maior número de ocorrências
moda :: MSet a -> [a]
moda [] = []
moda [(a,b)] = [a]
moda ms = (extrairElementos (maxOcorrencias (listaOcorrencias ms)) ms)

--Funções auxiliares para o exercício 22.(h)
--Calcular o valor máximo de uma lista de ocorrencias (O resultado do exemplo do enunciado aparece na foram "ad" e não ['a','d'], mas é o mesmo)
maxOcorrencias :: [Int] -> Int
maxOcorrencias [] = 0
maxOcorrencias [x] = x
maxOcorrencias [x,y] = if x>y then x else y
maxOcorrencias (x1:x2:xs) = if x1>x2 then maxOcorrencias (x1:xs) else maxOcorrencias (x2:xs)

--Criar a lista com os elementos com as ocorrências mais elevadas 
extrairElementos :: Int -> MSet a -> [a]
extrairElementos _ [] = []
extrairElementos x [(m,n)] = if (x==n) then [m] else []
extrairElementos x ((m,n):ms) = if (x==n) then (m):extrairElementos x ms else extrairElementos x ms

type Radar = [(Hora,Matricula,VelAutor,VelCond)]
type Matricula = String --Matrícula do carro em infração
type VelAutor = Float --Velocidade autorizada
type VelCond = Float --Velocidade do condutor

--23.(a) Dada uma matrícula calcula o excesso de velocidade do carro tendo em conta que um carro pode ter mais que uma infração
excessoVel :: Matricula -> Radar -> [Float] --Lista de valor que são o excesso de velocidade em cada infração
excessoVel _ [] = []
excessoVel m [(_,m1,va,vc)] = if (m==m1) then [vc-va] else []
excessoVel m ((_,m1,va,vc):rad) = if (m==m1)  then (vc-va):excessoVel m rad else excessoVel m rad

--TESTE: excessoVel "A01DF" [((8,10),"89dd",120,150),((8,45),"A01DF",120,150),((21,15),"A01DF",120,220)] --> [30.0,100.0]

--23.(b) Dada a primeira componente de uma hora verifica quantas infrações ocorreram nesse período de uma hora
infNumaHora :: Int -> Radar -> Int
infNumaHora _ [] = 0
infNumaHora h [((h1,m),_,va,vc)] = if (h==h1) then 1 else 0
infNumaHora h (((h1,m),_,va,vc):rad) = if (h==h1) then 1+infNumaHora h rad else infNumaHora h rad

--TESTE: infNumaHora 8 [((8,10),"89dd",120,150),((8,45),"A01DF",120,150),((21,15),"A01DF",120,220),((8,15),"1DF",120,121)] --> 3

--23.(c) Verificar se as infrações ocorrem pela ordem cronológica, i.e horas por ordem crescente
-- RELEMBRAR ...
--9.(b) Testa se uma hora é ou não depois de outra (se a hora B é seguinte à hora A)
--timeNext :: Hora -> Hora -> Bool 
--timeNext (h1,m1) (h2,m2) = if h2>h1 then True else if (h2==h1 && m2>m1) then True else False

radarValido :: Radar -> Bool
radarValido [] = True
radarValido [r1] = True
radarValido ((h1,a,b,c):(h2,d,e,f):rad) = if timeNext h1 h2 then radarValido ((h2,d,e,f):rad) else False

--TESTE: radarValido [((8,10),"89dd",120,150),((8,45),"A01DF",120,150),((21,15),"A01DF",120,220),((21,14),"1DF",120,121)] --> False

--23.(d) Verificar se um radar registou duas infrações à mesma hora
duasInfNumaHora :: Hora -> Radar -> Bool
duasInfNumaHora h r = if (contaInfNumaHora h r)==2 then True else False

--Função auxiliar para o exercício 23.(d)
contaInfNumaHora :: Hora -> Radar -> Int
contaInfNumaHora _ [] = 0
contaInfNumaHora (h,m) [((h1,m1),a,b,c)] = if (h==h1) && (m==m1) then 1 else 0
contaInfNumaHora (h,m) (((h1,m1),a,b,c):rad) = if (h==h1) && (m==m1) then 1+contaInfNumaHora (h,m) rad else contaInfNumaHora (h,m) rad 

--TESTE: duasInfNumaHora (8,45) [((8,10),"89dd",120,150),((8,45),"A01DF",120,150),((21,15),"A01DF",120,220),((8,45),"1DF",120,121)] --> True

--23.(e) Calcular maior infração registada
maiorInfracao :: Radar -> Float
maiorInfracao [] = 0.0
maiorInfracao [((h1,m1),m,va,vc)] = vc-va
maiorInfracao (((h1,m1),ma1,va1,vc1):((h2,m2),ma2,va2,vc2):rad) = if (vc1-va1)<(vc2-va2) then maiorInfracao (((h2,m2),ma2,va2,vc2):rad)
                                                                                        else maiorInfracao (((h1,m1),ma1,va1,vc1):rad)

--TESTE: maiorInfracao [((8,10),"89dd",120,150),((8,45),"A01DF",120,150),((21,15),"A01DF",120,220),((8,45),"1DF",120,121)] --> 100.0

--23.(f) Menor período de tempo (em minutos) sem infrações, basicamente é o tempo mínimo entre duas infrações.
menorPerSemInf :: Radar -> Int
menorPerSemInf [] = 0
menorPerSemInf rad = (minimoValor (listarIntervalos rad))

--Funções auxiliares para função do exercício 24.(f)
--RELEMBRAR ... 9.(e) difTime :: Hora -> Hora -> Int

--Criar lista com valores de intervalos sem infrações
listarIntervalos :: Radar -> [Int]
listarIntervalos [] = []
listarIntervalos [a] = []
listarIntervalos [(h1,_,va1,vc1),(h2,_,va2,vc2)] = [(difTime h1 h2)]
listarIntervalos ((h1,ma1,va1,vc1):(h2,ma2,va2,vc2):rad) = (difTime h1 h2):listarIntervalos ((h2,ma2,va2,vc2):rad)

--Calcular o valor máximo de uma lista de minutos
minimoValor :: [Int] -> Int
minimoValor [] = 0
minimoValor [x] = x
minimoValor [x1,x2] = if x1>x2 then x1 else x2
minimoValor (x1:x2:xs) = if (x1>x2) then minimoValor (x1:xs) else minimoValor (x2:xs)


--TESTE: menorPerSemInf [((8,10),"89dd",120,150),((8,45),"A01DF",120,150),((21,15),"A01DF",120,70),((22,0),"1DF",120,110)] --> 35

--23.(g) Verificar se houve alguma carro apanhado mais de que uma vez
superiorUmaInf :: Radar -> Bool
superiorUmaInf [] = False
superiorUmaInf ((h,m,va,vc):rad) = if (contaInf m ((h,m,va,vc):rad))>1 then True else  superiorUmaInf rad

--Função auxiliar para resolução do exercício 24.(g)
--Contar o número de infrações de um carro com uma dada matrícula

contaInf :: Matricula -> Radar -> Int
contaInf _ [] = 0
contaInf m [(h1,m1,va1,vc1)] = if (m==m1) then 1 else 0
contaInf m ((h1,m1,va1,vc1):rad) = if (m==m1) then 1+contaInf m rad else contaInf m rad

--TESTE: superiorUmaInf [((8,10),"89dd",120,150),((8,45),"A01DF",120,150),((21,15),"A01DF",120,130),((22,0),"1DF",120,140)] --> True

--23.(h) Dada uma matrícula devolve uma lista [(Hora,VELOCIDADE EM EXCESSO)]
tempoEexcesso :: Matricula -> Radar -> [(Hora,Float)]
tempoEexcesso _ [] = []
tempoEexcesso m ((h1,m1,va1,vc1):rad) = if (m==m1) then (h1,(vc1-va1)):tempoEexcesso m rad else tempoEexcesso m rad

--TESTE: tempoEexcesso "A01DF" [((8,10),"89dd",120,150),((8,45),"A01DF",120,150),((21,15),"A01DF",120,130),((22,0),"1DF",120,190)]
-- --> [((8,45),30.0),((21,15),10.0)]

--23.(i) é igual ao (c) o enunciado está repetido

--23.(j) Verificar se o radar está a funcionar corretamente i.e velocidade do condutor sempre maior que a velocidade autorizada
radarFunciona :: Radar -> Bool
radarFunciona [] = True
radarFunciona ((h,m,va,vc):rad) = if vc>va then radarFunciona rad else False

--TESTE: radarFunciona [((8,10),"89dd",120,150),((8,45),"A01DF",120,150),((21,15),"A01DF",120,130),((22,0),"1DF",120,110)] --> False

--23.(k) Calcular o total de excesso de velocidade nesse dia
totalExcessoVel :: Radar -> Float
totalExcessoVel [] = 0.0
totalExcessoVel ((h,m,va,vc):rad) = vc-va + totalExcessoVel rad

--23.(l) Calcular o maior período do dia sem infrações
maiorPerSemInf :: Radar -> Int
maiorPerSemInf [] = 0
maiorPerSemInf rad = (maximoValor (listarIntervalos rad))

--Criar lista com valores de intervalos sem infrações
--listarIntervalos :: Radar -> [Int] implementada no exercício 24.(f)(auxiliares)

--Calcular o valor máximo de uma lista de minutos
maximoValor :: [Int] -> Int
maximoValor [] = 0
maximoValor [x] = x
maximoValor [x1,x2] = if x1>x2 then x1 else x2
maximoValor (x1:x2:xs) = if (x1>x2) then maximoValor (x1:xs) else maximoValor (x2:xs)

type Inscritos = [(Number,Nome,Curso,Ano)]
type Number = Integer
type Curso = String
type Ano = Integer
--Nome já definido no exercício 18.

--24.(a) Calcular o numero de inscritos de um determinado ano de um dado curso
aluCA :: (Curso,Ano) -> Inscritos -> Int
aluCA _ [] = 0
aluCA (c,a) ((n,nom,cur,an):insc) = if (c==cur) && (a==an) then 1+aluCA (c,a) insc else aluCA (c,a) insc

--24.(b) Calcular quantos alunos de uma lista de números estão inscritos num dado curso
quantosInsc :: Curso -> [Number] -> Inscritos -> Int
quantosInsc _ [] _ = 0
quantosInsc _ _ [] = 0
quantosInsc c (n:ns) ((nx,nomx,curx,anx):insc) = if (n==nx) && (c==curx) then 1+quantosInsc c ns insc else quantosInsc c ns insc

--24.(c) Cria uma lista com todos os alunos que frequentam um determinado ano
doAno :: Ano -> Inscritos -> [(Number,Nome,Curso)]
doAno _ [] = []
doAno a ((nx,nomx,curx,anx):insc) = if (a==anx) then (nx,nomx,curx):doAno a insc else doAno a insc    

type PlayList = [(Titulo,Interprete,Duracao)]
type Titulo = String
type Interprete = String
type Duracao = Int --Duração da música em segundos

--25.(a) Calcular o tempo total da PlayList
totalPlay :: PlayList -> Int
totalPlay [] = 0
totalPlay ((_,_,d):p) = d+totalPlay p

--25.(b) Testa se todos os interpretes da lista têm músicas na PlayList
temMusicas :: [Interprete] -> PlayList -> Bool
temMusicas _ [] = False
temMusicas [] _ = True
temMusicas (i:is) p = if (contaMusicas i p)>0 then temMusicas is p else False

--Função auxiliar para o exercício 25.(b)
contaMusicas :: Interprete -> PlayList -> Int
contaMusicas _ [] = 0
contaMusicas i ((_,x,_):p) = if (i==x) then 1+contaMusicas i p else contaMusicas i p

--25.(c) Indica o título e a duração de uma das músicas da PlayList com maior duração
--(isto para salvaguardar o facto várias músicas poderem ter a mesma duração e simultaneamente essa duração ser máxima na PlayList)
maior :: PlayList -> (Titulo,Duracao)
maior [] = ("",0)
maior [(titl1,_,d1),(titl2,_,d2)] = if d1>d2 then (titl1,d1) else (titl2,d2)
maior ((titl1,i1,d1):(titl2,i2,d2):p) = if d1<d2 then maior ((titl2,i2,d2):p)  else maior ((titl1,i1,d1):p)

type TabAbrev = [(Abreviatura,Palavra)]
type Abreviatura = String
type Palavra = String

--26.(a) Testar se uma dada abreviatura existe na tabela
existeAbrv :: Abreviatura -> TabAbrev -> Bool
existeAbrv _ [] = False
existeAbrv a ((ab,pl):tab) = if (a==ab) then True else existeAbrv a tab

--26.(b) Dado um texto [String] - uma lista de Strings - substituir a abreviatura presente no mesmo pela palavra correspondente a essa abreviatura na tabela
substitui :: [String] -> TabAbrev -> [String]
substitui [] _ = []
substitui _ [] = []
substitui (abrv:abrvs) tabela = (buscaPalavra abrv tabela):substitui abrvs tabela

--Função auxiliar para exercício 26.(b)
--Busca a palavra correspondente à abreviatura dada, caso nao encontra devolve a abreviatura (pode significar que a string não é uma abreviatura ou não está na tabela)
buscaPalavra :: Abreviatura -> TabAbrev -> String
buscaPalavra a [] = a
buscaPalavra  a ((ab,pl):tab) = if (a==ab) then pl else buscaPalavra a tab  

--26.(c) Verificar se uma tabela está ordenada por ordem crescente das abreviaturas
estaOrdenada :: TabAbrev -> Bool
estaOrdenada [] = True
estaOrdenada ((a1,p1):(a2,p2):tab) = if (a1<a2) then estaOrdenada ((a2,p2):tab) else False

type TabTemp = [(Data,Temp,Temp)] -- Data, temperatura mínima seguida da máxima
type Data = (Int,Int,Int)         -- Ano, mês e dia
type Temp = Float

--27.(a) Criar uma lista com as temperaturas médias de cada dia
medias :: TabTemp -> [(Data,Temp)]
medias [] = []
medias ((d,tmax,tmin):tab) = (d,(tmax+tmin)/2):medias tab

--27.(b) Verificar se a tabela está ordenada por ordem decrescente das datas
decrescenteD :: TabTemp -> Bool
decrescenteD [] = True
decrescenteD (((ano1,mes1,dia1),tmax1,tmin1):((ano2,mes2,dia2),tmax2,tmin2):tab)
           | ano1<ano2 = decrescenteD (((ano2,mes2,dia2),tmax2,tmin2):tab)
           | ano1==ano2 && mes1<mes2 = decrescenteD (((ano2,mes2,dia2),tmax2,tmin2):tab)
           | ano1==ano2 && mes1==mes2 && dia1<=dia2 = decrescenteD (((ano2,mes2,dia2),tmax2,tmin2):tab)
           | otherwise = False

--27.(c) Conta o número de datas de uma dada lista que têm registo de temperaturas na tabela
conta :: [Data] -> TabTemp -> Int
conta _ [] = 0
conta [] _ = 0
conta ((a,m,d):ds) (((ano1,mes1,dia1),tmax1,tmin1):tab) = if (a==ano1 && m==mes1 && d==dia1) then 1+conta ds tab else conta ds tab

type Monomio = (Float,Int)
type Polinomio = [Monomio]
--28.(a) Dado um polinómio e um grau devolver o valor do coeficiente correspondene
coef :: Polinomio -> Int -> Float
coef [] _ = 0.0
coef ((cf,exp):pol) grau = if (exp==grau) then cf else coef pol grau

--TESTE: coef [(5,3),(1,1),(-5,0)] 1 --> 1.0 
--            5x^3 + x - 5

--28.(b) Testar se polinómio não tem monómios com graus repetidos nem monómios com coeficientes nulos (Caso nenhuma regra seja desrespeitada devolve True)
poliOK :: Polinomio -> Bool
poliOK [] = True
poliOK ((cf,exp):pol) = if ((contaGraus exp pol)>=1 || cf==0) then False else poliOK pol

--Função auxiliar para resolução do exercício 28.(b)
--Contar o número de monómios no polinómio com um dado grau d
contaGraus :: Int -> Polinomio -> Int
contaGraus _ [] = 0
contaGraus g ((_,exp):pol) = if (g==exp) then 1+contaGraus g pol else contaGraus g pol

--TESTES: poliOK [(5,3),(1,1),(-5,0)] --> True
--        poliOK [(5,3),(1,1),(-5,0),(-5,4),(0,2)] --> False (coeficiente a 0)
--        poliOK [(5,3),(1,1),(-5,0),(-5,4),(3,1)] --> False (graus repetidos)

--28.(c) Acrescentar um polinómio a um monómio caso se trate de uma adição váilida
addM :: Polinomio -> Monomio -> Polinomio
addM [] m = [m]
addM p m = if (poliOK p) && (poliOK (m:p)) then (m:p) else []

--TESTES: addM [(5,3),(1,1),(-5,0)] (1,0) --> []
--        addM [(5,3),(1,1),(-5,0)] (4,2) --> [(4,2),(5,3),(1,1),(-5,0)]

--28.(d) Adicionar dois polinomios (sob as condições de verificação do exercício anterior)
addP :: Polinomio -> Polinomio -> Polinomio
addP p [] = if poliOK p then p else []
addP [] p = if poliOK p then p else []
addP p1 p2 = if (poliOK (p1++p2)) then p1++p2 else []

--TESTES: addP [(5,3),(1,1),(-5,0)] [(5,2),(1,4),(-5,5)] --> [(5,3),(1,1),(-5,0),(5,2),(1,4),(-5,5)]
--        addP [(5,3),(1,1),(-5,0)] [(5,2),(1,4),(-5,1)] --> [] 
--        addP [(5,3),(1,1),(-5,0)] []                   --> [(5,3),(1,1),(-5,0)]