\documentclass{article}
\usepackage[utf8x]{inputenc}
\usepackage{a4}
\usepackage{moreverb}
\usepackage{palatino}
\usepackage{url}
\newcommand{\OlxUm}{{O\hskip-.05ex\raisebox{-.4em}L\hskip-0.8ex X\raisebox{.4em}{{\footnotesize\sc um}}}}
%----------------------------------------------------------------------
\title{\OlxUm --- Um micro sistema de leilões em Haskell}
%----------------------------------------------------------------------
\author{
\\
	Universidade do Minho
\\
	LEI ---Licenciatura de Engenharia Informática
\\
	UC8201N6 --- Lab.\ de Informática I
\\
	Ano lectivo de 2012/13
}
%----------------------------------------------------------------------
\date{Nov.\ 2012}
%----------------------------------------------------------------------
%include polycode.fmt

\begin{document}
\maketitle

\begin{comment}
% ---------------------- advanced code, not for 1st year students ------------------------------
\begin{code}
{-# LANGUAGE TemplateHaskell #-}

module Auctions where

import Data.Aeson (decode, encode)
import Data.Aeson.TH (deriveJSON)
import qualified Data.ByteString as B
import qualified Data.ByteString.Lazy as BL
import Data.List (sort,nub)
import Data.Char
\end{code}
% ----------------------------------------------------------------------------------------------
\end{comment}

\section{Tipos}
\OlxUm\ é um sítio para compra e venda em suporte internet que funciona em regime de
leilão. Começa por oferecer uma colecção de items para serem leiloados,
\begin{code}
data House = House {
               hrunning  :: Running,
               hfinished :: Finished
             } deriving (Show,Eq)
\end{code}
onde cada
\begin{code}
type Running  = [Auction]
\end{code}
é a lista dos ítens ainda por leiloar, e
\begin{code}
type Finished = [Auction]
\end{code}
contem os ítens cujo leilão já acabou.
Cada ítem do leilão é registado de acordo com a seguinte estrutura,
\begin{code}
data Auction = Auction {
                 actid     :: Int,
                 actowner  :: String,
                 actdesc   :: String,
				 actclass  :: String,
				 actinit   :: Int,
                 actvalue  :: Int,
                 actbidder :: String
               } deriving (Show,Eq,Ord) 
\end{code}
a saber:
\begin{itemize}
\item |actid| --- o inteiro que identifica o ítem que está/foi leiloado
\item |actowner| --- o identificador do seu dono
\item |actdesc| --- a sua descrição
\item |actclass| --- a sua categoria
\item |actinit| --- preço inicial de um dado item a leilão
\item |actvalue| --- o seu valor (valor da maior oferta)
\item |actbidder| --- o identificador de quem o quer comprar
\end{itemize}

\subsection{Exemplos}
Dá-se de seguida um exemplo de leilão:
\begin{code}
myhouse = House [a2,a1] [a3] where
            a1 = Auction 1 "Peter" "TV" "Tecnologia" 60 0 ""
            a2 = Auction 2 "Mary" "laptop" "Informatica" 120 0 ""
            a3 = Auction 3 "John" "phone" "Telecomunicacoes" 85 0 "Peter"
\end{code}
Este exemplo pode ser usado para testes.

\section{Acções}
Colocar novo ítem no leilão:
\begin{code}
auctionNew :: House -> String -> String -> String -> Int -> House
auctionNew h owner desc classe init = 
  let newid = length (hrunning h) + length (hfinished h) + 1
      newa = Auction newid owner desc classe init 0 ""
  in House (newa:hrunning h) (hfinished h)
\end{code}
Oferta para um particular item do leilão:
\begin{code}
-- a função auctionBid está definida em baixo no código da questão 5
\end{code}
Fechar o leilão de um ítem, dando-o como leiloado ou cancelado, caso não
tenha havido nenhum |actbidder| interessado em comprá-lo:
\begin{code}
auctionFinish :: House -> Int -> House
auctionFinish h id =
  let r = filter (\i -> (actid i) /= id) (hrunning h)
      curr = filter (\i -> (actid i) == id) (hrunning h)
  in House r (curr++(hfinished h))
\end{code}

\section{Tipos e funções auxiliares}
Função que dá o total já leiloado, total por leiloar e total sem oferta:
\begin{code}
tots :: House -> Tot
tots h = Tot f r l where
               r = foldr (+) 0 ( map actvalue (hrunning h) )
               f = foldr (+) 0 [ actvalue x | x <- hfinished h, actbidder x > "" ]
               l = foldr (+) 0 [ actvalue x | x <- hfinished h, actbidder x == "" ]
\end{code}
onde
\begin{code}
data Tot = Tot {tfin :: Int, trun :: Int, tlost :: Int} deriving Show
\end{code}
Função genérica para ordenar sequências de registos segundo um dos seus atributos:
\begin{code}
sortOn :: (Ord b) => (a -> b) -> [a] -> [a]
sortOn f l = [ l!!i | i <- map snd x ] where x = sort(zip (map f l) [0..])
\end{code}

\begin{code}
--Código da Questão 1
existe :: Int -> [Int] -> Bool
existe a [] = False
existe a (x:xs) = if a == x then True else existe a xs

repetidos :: [Int] -> Bool
repetidos [] = False
repetidos (x:xs) = if existe x xs then True else repetidos xs

pegaId :: [Auction] -> [Int]
pegaId [] = []
pegaId ((Auction actid actowner actdesc actclass actinit actvalue actbidder):lia) = actid:pegaId lia

hrf_val :: [Auction] -> Bool
hrf_val [] = True
hrf_val [x] = True
hrf_val lia = if (repetidos (pegaId lia)) then False else True

junta_auc :: House -> [Auction]
junta_auc (House [] []) = []
junta_auc (House a b) = a++b

idsOK :: House -> Bool
idsOK  (House [] []) = True
idsOK h = if (hrf_val (junta_auc h)) then True else False

--Código da Questão 2
existe_auc :: Auction -> [Auction] -> Bool
existe_auc _[] = False
existe_auc (Auction a b c d e f g) ((Auction aa bb cc dd ee ff gg):lia) = if a==aa && b==bb && c==cc && d==dd && e==ee && f==ff && g==gg
                                                                     then True 
																     else existe_auc (Auction a b c d e f g) lia
																
repetidos_auc :: [Auction] -> [Auction]
repetidos_auc [] = []
repetidos_auc (a:lia) = if (existe_auc a lia)==True then a:repetidos_auc lia else repetidos_auc lia

repetidos' :: [Int] -> [Int]
repetidos' [] = []
repetidos' (x:xs) = if existe x xs then x:repetidos' xs else repetidos' xs

idKey :: House -> Bool
idKey (House [] []) = False
idKey h | (idsOK h) == True = True
        |  length (repetidos' (pegaId (junta_auc h))) == length (repetidos_auc (junta_auc h)) = True
        |  length (repetidos' (pegaId (junta_auc h))) /= length (repetidos_auc (junta_auc h)) = False				
        |  otherwise = idKey h
		
--Código da Questão 4
ordenaId [] = []
ordenaId (s:xs) = ordenaId [x|x <- xs,x < s] ++ [s] ++ ordenaId [x|x <- xs,x >= s]

pegaordena_Id :: [Auction] -> [Int]
pegaordena_Id [] = []
pegaordena_Id auc = ordenaId (pegaId auc)

idCons :: [Int] -> Bool
idCons [] = False
idCons l | (repetidos l == True) = False
         | ((head l) + ((length l)-1) ) == (last l) = True
		 | otherwise = False
		 
allIds :: House -> Bool
allIds (House [] []) = False
allIds h = if (idCons (ordenaId (pegaId (junta_auc h))) == True) then True else False

--Código Questão 5
pega_actvalue :: Auction -> Int
pega_actvalue (Auction a b c d e f g) = f

pega_auc :: [Auction] -> Int -> Auction
pega_auc ((Auction a b c d e f g):lia) id = if a==id then (Auction a b c d e f g) else pega_auc lia id

existe_aucId :: [Auction] -> Int -> Bool
existe_aucId []_ = False
existe_aucId ((Auction aa bb cc dd ee ff gg):auc) a = if aa==a then True else existe_aucId auc a

coloca :: Auction -> Int -> String -> Auction
coloca (Auction a b c d e f g) bid bidder = (Auction a b c d e bid bidder)

prm :: House -> [Auction]
prm (House [] _) = []
prm (House r f) = r

auctionBid :: House -> Int -> String -> Int -> House
auctionBid h id bidder bid = 
  let r = filter ((/=id).actid) (hrunning h)
      curr = head (filter ((==id).actid) (hrunning h))
      newa = Auction (actid curr) (actowner curr) (actdesc curr) (actclass curr) (actinit curr) bid bidder
  in if ((existe_aucId (hrunning h) id)==False) then h
                                                else if (bid > (pega_actvalue (pega_auc (hrunning h) id))) then House (newa:r) (hfinished h)
                                                                                                           else h
--Código Questão 8
valoriza :: House -> [Int]
valoriza (House _ []) = []
valoriza (House rs ((Auction _ _ _ _ iv fv _):fs)) = if fv>iv then (fv-iv):valoriza (House rs fs)
                                                else valoriza (House rs fs)
media :: [Int] -> Int
media l = sum l `div` length l

medVal :: House -> Int
medVal (House _ []) = 0
medVal h = media (valoriza h)

\end{code}

\end{document}
