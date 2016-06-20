 
import Data.Char

type Auction = (Int,String,String,Int,String)
type House = ([Auction],[Auction])

--existe auction
existe_auc :: Auction -> [Auction] -> Bool
existe_auc _[] = False
existe_auc (a,b,c,d,e) ((aa,bb,cc,dd,ee):auc) = if a==aa && b==bb && c==cc && d==dd && e==ee then True else existe_auc (a,b,c,d,e) auc

-- repetidos auction
repetidos_auc :: [Auction] -> [Auction]
repetidos_auc [] = []
repetidos_auc (x:xs) = if (existe_auc x xs)==True then x:repetidos_auc xs else repetidos_auc xs

repetidos' :: [Int] -> [Int]
repetidos' [] = []
repetidos' (x:xs) = if existe x xs then x:repetidos' xs else repetidos' xs


idKey :: House -> Bool
idKey ([],[]) = False
idKey h | (idsOK h) == True = True
        |  length (repetidos' (pegaId (junta_auc h))) == length (repetidos_auc (junta_auc h)) = True
        |  length (repetidos' (pegaId (junta_auc h))) /= length (repetidos_auc (junta_auc h)) = False				
        |  otherwise = idKey h	   
				   
				   






				   
-------------------------------------------------- Funções da Questão 1 (que auxiliam na definição de idKey)-------------------------------------------------------
existe :: Int -> [Int] -> Bool
existe a [] = False
existe a (x:xs) = if a == x then True else existe a xs

repetidos :: [Int] -> Bool
repetidos [] = False
repetidos (x:xs) = if existe x xs then True else repetidos xs

--pega actids
pegaId :: [Auction] -> [Int]
pegaId [] = []
pegaId ((a,b,c,d,e):l) = a:pegaId l

--house runnuing finished válida
hrf_val :: [Auction] -> Bool
hrf_val [] = True
hrf_val [x] = True
hrf_val lia = if (repetidos (pegaId lia)) then False else True

--junta auctions
junta_auc :: House -> [Auction]
junta_auc ([],[]) = []
junta_auc (a,b) = a++b

idsOK :: House -> Bool
idsOK ([],[]) = True
idsOK h = if (hrf_val (junta_auc h)) then True else False										  

											  