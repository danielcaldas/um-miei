
import Data.Char

--auxiliar da questão 1

existe :: Int -> [Int] -> Bool
existe a [] = False
existe a (x:xs) = if a == x then True else existe a xs

repetidos :: [Int] -> Bool
repetidos [] = False
repetidos (x:xs) = if existe x xs then True else repetidos xs

--id's Consecutivos
idCons :: [Int] -> Bool
idCons [] = False
idCons l | (repetidos l == True) = False
         | ((head l) + ((length l)-1) ) == (last l) = True
		 | otherwise = False
		 
		 




