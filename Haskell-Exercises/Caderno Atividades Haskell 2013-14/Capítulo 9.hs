import Data.Char
import Data.List

------------------------------------------------------------------CAPÍTULO 9 - CLASSES----------------------------------------------------------------------

mdc :: Integer -> Integer -> Integer
mdc x y 
    | x == y = x
    | x > y  = mdc (x - y) y
    | x < y  = mdc x (y - x)

data Frac = F Integer Integer
     --deriving (Eq,Ord,Show,Num) --> Em vez deste statement temos agora os exercícios 63 alíneas b c e d 

--63.(a) Calcular a fração irredutível de uma dada fração (colocando o denominador positivo)
normaliza :: Frac -> Frac
normaliza (F a b) | a>0 && b>0 = (F (div a (mdc a b)) (div b (mdc a b)))
                  | a>0 && b<0 = (F (-(div a (mdc a (-b) ))) (div (-b) (mdc a (-b) )) )
                  | a==0||b==0       = (F 0 0)
                  | a<0 && b<0 = (F (div (-a) (mdc (-a) (-b) )) (abs (div (-b) (mdc (-a) (-b) ) )) )
                  | otherwise = (F (div a (mdc a b)) (div b (mdc a b)))

--63.(b) Definir data Frac como instância da classe Eq
instance Eq Frac where
      (F a b)==(F a' b') = a==a' && b==b'
      (F a b)/=(F a' b') = a/=a' || b/=b'

--63.(c) Definir data Frac como instância da classe Ord
instance Ord Frac where
	  (F a b)>(F a' b') = a>a' && b==b' || a>=a' && b<b'
	  (F a b)<(F a' b') = a<a' && b==b' || a<=a' && b>b' 

--63.(d) Definir data Frac como instância da classe Show
instance Show Frac where
	  show (F a b) = (show a) ++ "/" ++ (show b)

--63.(e) Definir data Frac como instância da classe Num
instance Num Frac where
	 (+) = somaFracao
	 (-) = subtraiFracao
	 (*) = multFracao
	 fromInteger = deInteger
	 negate = simetricoFracao
	 abs = moduloFracao
	 signum = sinalFracao

--Funções auxiliares para resolução do exercício 63.(e)
somaFracao :: Frac -> Frac -> Frac
somaFracao x y = somaAux (normaliza x) (normaliza y)

somaAux :: Frac -> Frac -> Frac
somaAux (F a b) (F a' b') = (F (a+a') b)

subtraiFracao :: Frac -> Frac -> Frac
subtraiFracao x y = subtraiAux (normaliza x) (normaliza y)

subtraiAux :: Frac -> Frac -> Frac
subtraiAux (F a b) (F a' b') = (F (a-a') b)

multFracao :: Frac -> Frac -> Frac
multFracao (F a b) (F a' b') = (F (a*a') (b*b'))

--Transformar um qualquer valor Integer na forma de fração 
deInteger :: Integer -> Frac
deInteger 0 = (F 0 0)
deInteger 1 = (F 1 1)
deInteger x = (F (2*x) x)

simetricoFracao :: Frac -> Frac
simetricoFracao (F a b) = (F (-a) (-b))

moduloFracao :: Frac -> Frac
moduloFracao (F a b) = (F (abs a) (abs b))

sinalFracao :: Frac -> Frac
sinalFracao (F a b) | a<0 || b<0 = (F (-1) 1)
                    | a>0 && b>0 = (F 1 1)
                    | otherwise = (F 0 0)


--63.(f) Dada uma fração f e uma lista de frações l seleciona da lista as frações que são superiores ao dobro de f (2*f)
saoOdobro :: Frac -> [Frac] -> [Frac]
saoOdobro _ [] = []
saoOdobro (F a b) [(F a' b')] = if (div a' b')>((div a b)*2) then [(F a' b')] else []
saoOdobro (F a b) ((F a' b'):fs) = if (div a' b')>((div a b)*2) then (F a' b'):saoOdobro (F a b) fs else saoOdobro (F a b) fs

--TESTE: saoOdobro (F 7 3) [(F 1 2),(F 4 4),(F 100 20),(F 1 7),(F 9 2),(F 110 10)]
--RESULTADO: [100/20,110/10]

data ExpInt = Const Int
            | Simetrico ExpInt
            | Mais ExpInt ExpInt
            | Menos ExpInt ExpInt
            | Mult ExpInt ExpInt
       deriving (Eq,Show)

--64.(a) Calcula o valor de uma expressão ExpInt
--              (*)
--        (+)         (+)
--      c     c     c     c  ---> em que c é uma contante real, Resultado: (c+c)*(c+c)=resultado  (EXEMPLO)

--calcula :: ExpInt -> Int
--calcula (Const x) = x
--calcula (Simetrico (Const x)) = (-x)
--calcula (Mais (Const x) (Const x)) = (x+x)
--calcula (Menos (Const x) (Const x)) = (x-x)
--calcula (Mult (Const x) (Const x)) = (x*x)
--calcula (op1 (op2 a b) (op3 c d)) | op1==Mais = calcula ((op2 a b)+(op3 c d))
  --                                | op1==Menos = calcula ((op2 a b)-(op3 c d))
    --                              | otherwise = calcula ((op2 a b)*(op3 c d))


data Data = D Dia Mes Ano
    deriving (Eq)
type Dia = Int
type Mes = Int
type Ano = Int
data Movimento = Credito Float | Debito Float
    deriving (Eq)
data Extracto = Ext Float [(Data,String,Movimento)]
    deriving (Eq)

--66.(a) Defenir Data como instância da classe Ord
instance Ord Data where
	(D d m a)>(D d' m' a') = a>a' || a>=a' && m>m' || a>=a' && m>=m' && d>d'
	(D d m a)<(D d' m' a') = a<a' || a<=a' && m<m' || a<=a' && m<=m' && d<d'

--66.(b) Definir Data como classe da instância Show
instance Show Data where
	show (D d m a) = (show a)++"/"++(show m)++"/"++(show d) --Esta instância faz como que as datas sejam apresentadas no formato: a/m/d

--66.(c) Ordenar um extrato pela ordem crescente da Data
ordena :: Extracto -> Extracto
ordena (Ext si []) = (Ext si [])
ordena (Ext si [x]) = (Ext si [x])
ordena (Ext si l) = (Ext si (ordenaFase2 l))

ordenaFase2 :: [(Data,String,Movimento)] -> [(Data,String,Movimento)]
ordenaFase2 [] = []
ordenaFase2 [x] = [x]
ordenaFase2 (l:ls) = ordenaAux l (ordenaFase2 ls)

--Função auxiliar para exercício 66.(c)
ordenaAux :: (Data,String,Movimento) -> [(Data,String,Movimento)] -> [(Data,String,Movimento)]
ordenaAux (date,desc,mov) [] = [(date,desc,mov)]
ordenaAux (date,desc,mov) ((date',desc',mov'):l) | date>date' = (date',desc',mov'):(ordenaAux (date,desc,mov) l)
                                                 | otherwise = ((date,desc,mov):(date',desc',mov'):l)

--TESTE: ordena (Ext 33786 [((D 20 7 2014),"mota",(Debito 10000)), ((D 2 3 2014),"salario",(Credito 950)),((D 21 7 2014),"investimentos",(Debito 20000))])
--RESULTADO: Ext 33786.0 [(2/3/2014,"salario",Credito 950.0),(20/7/2014,"mota",Debito 10000.0),(21/7/2014,"investimentos",Debito 20000.0)]

--66.(d) Definir Extrato como instância da classe Show de forma a que os dados sejam apresentados conforme é mostrado no enunciado
instance Show Extracto where
	  show (Ext si ((d,desc,m):movs)) = "\n"++"\n"++"Saldo Anterior: "++(show si)++"\n"++"----------------------------------------------"++"\n"++"Data      Descricao      Credito      Debito"++"\n"++"----------------------------------------------"++"\n"++(show d)++"  "++(show desc)++"  "++(show m)++"\n"++"----------------------------------------------"++"\n"++"Saldo Atual: "++(show (calcularSaldoAtual (Ext si ((d,desc,m):movs))) )++"\n"++"\n"
--Esta instância não está totalmente correta porque só apresenta uma linha da lista de transações de um extrato.

--Funções auxiliares para o exercício 66.(d)

calcularSaldoAtual :: Extracto -> Float
calcularSaldoAtual (Ext si []) = si
calcularSaldoAtual (Ext si ((_,_,Credito x):m)) = calcularSaldoAtual (Ext (si+x) m)
calcularSaldoAtual (Ext si ((_,_,Debito x):m)) = calcularSaldoAtual (Ext (si-x) m)

--TESTE: calcularSaldoAtual (Ext 33786 [((D 20 7 2014),"mota",(Debito 10000)), ((D 2 3 2014),"salario",(Credito 950)),((D 21 7 2014),"investimentos",(Debito 20000))])
--RESULTADO: 4736.0

--Vamos também por uma questão de cumprir com o esquema do enunciado definir o data Movimento como instância da classe Show da seguinte forma:
instance  Show Movimento where
	 show (Credito x) = "     "++(show x)
	 show (Debito x)  = "                 "++(show x)

--TESTE: O mesmo teste da função ordena 66.(c)
--NOTA: Os espaços em branco é para fazer com que o debito e credito encaixem nas "colunas Credito e Debito" da instancia Show Extrato que definimos


data LTree a = Leaf a | Fork (LTree a) (LTree a)
    --deriving (Eq,Show)

--68.(a).i
instance (Eq a) => Eq (LTree a) where
       Leaf a == Leaf b = a==b
       (Fork esq dir) == (Fork esq' dir') = esq==esq' && dir==dir'
       Leaf a /= Leaf b = a/=b
       (Fork esq dir) /= (Fork esq' dir') = esq/=esq' && dir/=dir'

--68.(a).ii Função que aplica uma dada função a todos 
mapLT :: (a -> b) -> LTree a -> LTree b
mapLT f (Leaf x) = (Leaf (f x))
mapLT f (Fork (Leaf x) dir) = Fork (Leaf (f x)) (mapLT f dir) 
mapLT f (Fork esq (Leaf x)) = Fork (mapLT f esq) (Leaf (f x))
mapLT f (Fork esq dir) = Fork (mapLT f esq) (mapLT f dir)

--TESTE: mapLT (*2) (Fork (Fork (Fork (Leaf 3) (Leaf 4)) (Leaf 6)) (Fork (Leaf 1) (Leaf 1)))
--RESULTADO: Fork (Fork (Fork (Leaf 6) (Leaf 8)) (Leaf 12)) (Fork (Leaf 2) (Leaf 2))

--68.(b).i Definir uma instancia Show de forma a apresentar os dados da forma exemplificada no enunciado (O código está comentado por causa da alínea (d))
--instance (Show a) => Show (LTree a) where
--	   show (Leaf x) = (show x)
--	   show (Fork (Leaf a) (Leaf b)) = "("++(show a)++"^"++(show b)++")"
--	   show (Fork esq dir) = "("++(show esq)++"^"++(show dir)++")"

--68.(b).ii Construir uma árvore balanceada com um dado nº de folhas todas iguais ao segundo argumento
mktree :: Int -> a -> LTree a
mktree 1 x = (Leaf x)
mktree 2 x = (Fork (Leaf x) (Leaf x))
mktree 3 x = (Fork (Fork (Leaf x) (Leaf x)) (Leaf x))
mktree n x = if ((mod n 2)==0) then (Fork (mktree (div n 2) x) (mktree (div n 2) x)) else (Fork (mktree (div n 2) x) (mktree ((div n 2)+1) x))

--TESTE: mktree 9 0
--RESULTADO: (((0^0)^(0^0))^((0^0)^((0^0)^0)))

--68.(d) Definir uma nova instancia da classe show que antes de cada folha apresenta um número de pontos representantes da profundidade da folha na árvore
instance (Show a) => Show (LTree a) where
	   show (Leaf x) = (show x)
	   show (Fork (Leaf a) (Leaf b)) = "."++(show a)++"\n"++"."++(show b)
	   show (Fork esq dir) = (alturaPontos esq)++(show esq)++"\n"++(alturaPontos esq)++(show dir)

--Não funciona corretamente
alturaPontos :: LTree a -> String
alturaPontos (Leaf x) = []
alturaPontos (Fork (Leaf _) (Leaf _)) = "."
alturaPontos (Fork esq dir) = "."++(alturaPontos esq)++(alturaPontos dir)