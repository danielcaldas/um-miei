import Data.Char
import Data.List

--1º TESTE PF 2013/2014 (Não está resolvido na sua totalidade)

type Album = (Titulo,Artista,Ano,[Musica])
type Musica = (Nome,Int) -- (nome da música, duração em segundos)
type Titulo = String
type Nome = String
type Artista = String
type Ano = Int

--1.
doArtista :: [Album] -> Artista -> [(Titulo,Ano)]
doArtista [] _ = []
doArtista ((t,a,an,m):als) art = if (a==art) then (t,an):doArtista als art else doArtista als art

--3.
fun :: Artista -> [Album] -> [(Titulo,Int)]
fun art [] = [] 
fun art ((t,a,an,m):als) = if (art==a) then (t,(duracao m)):fun art als else fun art als

duracao :: [Musica] -> Int
duracao [] = 0
duracao [(m,d)] = d
duracao ((m,d):ms) = d+duracao ms

--4.
maisAntigos :: [Album] -> [Album]
maisAntigos [] = []
maisAntigos als = filter (anoIgual (menorAno als)) als

menorAno :: [Album] -> Int
menorAno [] = 0
menorAno [(_,_,a,_)] = a
menorAno ((t,ar,an,m):(t',ar',an',m'):als) = if (an<an') then menorAno ((t,ar,an,m):als) else menorAno ((t',ar',an',m'):als)

anoIgual :: Int -> Album -> Bool
anoIgual a1 (_,_,a2,_) = if (a1==a2) then True else False