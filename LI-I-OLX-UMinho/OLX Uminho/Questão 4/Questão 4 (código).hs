
import Data.Char

type Auction = (Int,String,String,Int,String)
type House = ([Auction],[Auction])

--função ordenaId
ordenaId [] = []
ordenaId (s:xs) = ordenaId [x|x <- xs,x < s] ++ [s] ++ ordenaId [x|x <- xs,x >= s]

--pega actids questão 2
pegaId :: [Auction] -> [Int]
pegaId [] = []
pegaId ((a,b,c,d,e):l) = a:pegaId l

pegaordena_Id :: [Auction] -> [Int]
pegaordena_Id [] = []
pegaordena_Id auc = ordenaId (pegaId auc)

--junta auctions (auxiliar questão 1)
junta_auc :: House -> [Auction]
junta_auc ([],[]) = []
junta_auc (a,b) = a++b

--id's Consecutivos
idCons :: [Int] -> Bool
idCons [] = False
idCons l | (repetidos l == True) = False
         | ((head l) + ((length l)-1) ) == (last l) = True
		 | otherwise = False


allIds :: House -> Bool
allIds ([],[]) = False
allIds h = if (idCons (ordenaId (pegaId (junta_auc h))) == True) then True else False

--auxiliar da questão 1

existe :: Int -> [Int] -> Bool
existe a [] = False
existe a (x:xs) = if a == x then True else existe a xs

repetidos :: [Int] -> Bool
repetidos [] = False
repetidos (x:xs) = if existe x xs then True else repetidos xs

