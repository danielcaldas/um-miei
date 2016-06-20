
import Data.Char

type House = ([Auction],[Auction])
type Auction = (Int,String,String,String,Int,Int,String)

--função que nos diz quanto é que uma dado item valorizou, isto se o leilão para esse item foi encerrado
--iv -> initial value
--fv -> final value

valoriza :: House -> [Int]
valoriza (_,[]) = []
valoriza (rs,((_,_,_,_,iv,fv,_):fs)) = if fv>iv then (fv-iv):valoriza (rs,fs)
                                                else valoriza (rs,fs)
												   
--função que calcula a média de uma lista de inteiros												   
media :: [Int] -> Int
media l = sum l `div` length l

medVal :: House -> Int
medVal (_,[]) = 0
medVal h = media (valoriza h)