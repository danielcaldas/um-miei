import Data.Char
import Data.List
import System.IO
import System.Random

data Tree a = Empty
            | Leaf a
            | Fork (Tree a) (Tree a)
       deriving (Eq)

--a1 = (Fork (Fork (Leaf 4) (Fork Empty (Leaf 13))) (Fork Empty (Fork (Leaf 5) Empty)))
--a2 = (Fork (Fork (Leaf 4) (Leaf 13)) (Leaf 5))

--exemplo a1: "((4 <-*-> (<> <-*-> 13)) <-*-> (<> <-*-> (5 <-*-> <>)))"

--1. Defenir Tree como instância da classe Show de forma a que o conteúdo da árvore seja apresentado conforme o exemplo acima
instance (Show a) => Show (Tree a) where
	    show Empty = "<>"
	    show (Leaf x) = (show x)
	    show (Fork (Leaf a) (Leaf b)) = "("++(show a)++" <-*-> "++(show b)++")"
	    show (Fork esq dir) = "("++(show esq)++" <-*-> "++(show dir)++")"

--2. Calcular o elemento mais à direita de uma árvore
ultimo :: Tree a -> Maybe a
ultimo Empty = Nothing
ultimo (Leaf x) = Just x
ultimo (Fork e Empty) = ultimo e
ultimo (Fork Empty d) = ultimo d
ultimo (Fork e d) = ultimo d
	 
--TESTE: ultimo (Fork (Fork (Leaf 4) (Fork Empty (Leaf 13))) (Fork Empty (Fork (Leaf 5) Empty)))

--3. Substitui todas as ocorrencias de um dado carater por Empty numa dada árvore
apaga :: Eq a => a -> Tree a -> Tree a
apaga _ Empty = Empty
apaga x (Leaf a) = if (x==a) then Empty else (Leaf a)
apaga x (Fork e d) = (Fork (apaga x e) (apaga x d)) 

--TESTE: apaga 5 (Fork (Fork (Leaf 4) (Fork Empty (Leaf 13))) (Fork Empty (Fork (Leaf 5) Empty)))

--4. Retirar "folhas" cujo o seu valor seja Empty
limpa :: Tree a -> Tree a
limpa Empty = Empty
limpa (Leaf x) = (Leaf x)
limpa (Fork e Empty) = limpa e
limpa (Fork Empty d) = limpa d
limpa (Fork e d) = (Fork (limpa e) (limpa d))

--randomRIO :: Random a => (a,a) -> IO
--5.
--recebe uma arvore com pelo menos uma folha e retorna o resultado de remover (substituindo por Empty) uma folha aleatoria dessa arvore. folha aleatoria gerada pela funcao randomRIO
randomRemove :: Tree Int -> IO (Tree Int)
randomRemove (Leaf x) = return Empty --randomRIO(x,x)=x
randomRemove a = do r <- randomRIO(minimum(lista a),maximum(lista a))
                    if elem r (lista a)
                    then return (apaga r a)
                    else return (a)

--auxiliar: lista os elementos de uma arvore
lista :: Tree Int -> [Int]
lista Empty = []
lista (Leaf x) = [x]
lista (Fork e d) = (lista e)++(lista d)



