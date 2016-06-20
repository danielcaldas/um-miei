
import Data.Char

type Auction = (Int,String,String,Int,String)
type House = ([Auction],[Auction])

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
idsOK  ([],[]) = True
idsOK h = if (hrf_val (junta_auc h)) then True else False
