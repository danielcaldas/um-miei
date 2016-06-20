
import Data.Char

type Auction = (Int,String,String,Int,String)
type House = ([Auction],[Auction])

--AUXILIARES

--pega actvalue de uma auction
pega_actvalue :: Auction -> Int
pega_actvalue (a,b,c,d,e) = d

--função que pega uma auction pelo seu id
pega_auc :: [Auction] -> Int -> Auction
pega_auc ((a,b,c,d,e):liar) id = if a==id then (a,b,c,d,e) else pega_auc liar id

--verifica se existe auction a partir de um id
existe_aucId :: [Auction] -> Int -> Bool
existe_aucId []_ = False
existe_aucId ((aa,bb,cc,dd,ee):auc) a = if aa==a then True else existe_aucId auc a

--auxiliar que coloca bid e bidder numa auction
coloca :: Auction -> Int -> String -> Auction
coloca (a,b,c,d,e) bid bidder = (a,b,c,bid,bidder)




