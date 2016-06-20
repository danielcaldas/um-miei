\documentclass[a4paper]{article}
\usepackage{a4}
\usepackage{moreverb}
\usepackage{palatino}
\usepackage[utf8x]{inputenc}
\usepackage[portuges]{babel}
%---------------
\usepackage[colorlinks=true,linkcolor=blue,citecolor=blue]{hyperref}
\newcommand{\zref}[2]{\hyperref[#2]{#1\ref{#2}}}
\newcommand{\lhstotex}{\href{http://www.andres-loeh.de/lhs2tex}{lhs2tex}}
\newcommand{\Latex}{\href{http://www.latex-project.org}{\LaTeX}}
\newcommand{\Unix}{\href{http://www.unix.org}{Unix}}
\newcommand{\Haskell}{\href{http://www.haskell.org}{Haskell}}
\newcommand{\OlxUm}{\href{http://localhost:8000/}{O\hskip-.05ex\raisebox{-.4em}L\hskip-0.8ex X\raisebox{.4em}{{\footnotesize\sc um}}}}
%---------------
\usepackage{url}
\usepackage{graphicx}
\DeclareGraphicsExtensions{.pdf,.png,.jpg}
\graphicspath{{./imagens/}}
\usepackage{verbatim}
%---------------
\title{Projecto Integrado\\ de\\ Laboratórios de Informática I (UC8201N6)}
%---------------
\author{
	Universidade do Minho
\\
	Licenciatura em Engenharia Informática (LEI)
\\
	Ano lectivo de 2012/13
}
%---------------
\date{Novembro de 2012}
%---------------
%include polycode.fmt

\begin{document}

\maketitle

\begin{abstract}
Este projecto tem como objectivo consolidar uma visão integrada do
uso da linguagem \Haskell\ para programação funcional, da \emph{shell} do
sistema operativo \Unix\ para interação com sistemas de ficheiros e do sistema
\Latex\ para preparação de documentação de aplicações de software.
Do projecto consta o desenvolvimento de um sistema de software
(uma aplicação Web) de pequena escala.
\end{abstract}

\tableofcontents

\section{Objectivos}
Com este projecto pretende-se uma consolidação dos conhecimentos aprendidos
nas aulas desta disciplina e de outras deste semestre,
nomeadamente nas Programa-ção Funcional, por forma a dar
aos alunos uma visão integrada do uso dos recursos de programação que aí
foram estudados: a \emph{shell} do \Unix, o sistema \Latex\ para preparação
de texto e a linguagem \Haskell.

O projecto parte de uma base (``kit'') já desenvolvida pela equipa docente
da disciplina~\footnote{Os sete membros da equipa docente aproveitam para
agradecer a colaboração preciosa de Nuno Carvalho na preparação do ``kit''
deste projecto.}, convidando os alunos a estudar código escrito por outrem e
a usar ferramentas desenvolvidas por terceiros. O objectivo é simular o desenvolvimento
de um projecto real de software, onde é frequentemente necessário lidar com
a manutenção e integração de código já desenvolvido. 

Do tema escolhido --- a construção e administração de serviços prestados
por aplicações Web --- resulta também do trabalho uma acção pedagógica na
sensibiliza\-ção dos alunos para esta importante área da produção de software,
encarada na globalidade das unidades curriculares que ensinam programação
nesta licenciatura como uma actividade disciplinada,
sistemática e fortemente construtiva.

\section{Aplicações Web}
O projecto consiste essencialmente na extensão e manutenção de uma aplicação
Web integralmente construída na linguagem de programação \Haskell.
A aplica\-ção --- designada \OlxUm\ --- é um micro sistema de leilões, em que os utilizadores
podem afixar itens para venda, fazer ofertas, etc.

Tal como se verá na descrição que
é dada na próxima secção do material que se põe à disposição dos alunos,
a maior parte do serviço prestado por esta aplicação já se encontra implementado.
O objectivo do trabalho é corrigi-lo, melhorá-lo e acrescentar-lhe novas
funcionalidades.

\section{Material}
\label{sec:material}
O presente documento (enunciado) deve ser cuidadosamente estudado pelos alunos
antes de estes iniciarem qualquer esforço de programação neste trabalho.
 O material disponível para a sua realização obtém-se descompactando o ficheiro
\begin{quote}
\href{http://www.di.uminho.pt/~jno/tgz/li11213mp.zip}{li11213mp.zip}
\end{quote}
do qual emergem os seguintes ficheiros,
\begin{itemize}
\item	\texttt{Projecto.pdf} --- o enunciado que está a ler
\item	\texttt{WebApp.hs} --- biblioteca escrita em \Haskell
\item	\texttt{Auctions.lhs} --- biblioteca escrita em \Haskell
\item	\texttt{Db.hs} --- biblioteca escrita em \Haskell
\end{itemize}
que vão ser necessárias para fazer o projecto,
bem como:
\begin{itemize}
\item	\texttt{Projecto.lhs} --- a fonte \Latex\ do ficheiro anterior, pre-processável
	por \lhstotex
\item	\texttt{Referencias.bib} --- ficheiro bib\TeX\ necessário para produzir \texttt{Projecto.pdf}
\item	directoria \texttt{imagens} onde estão guardadas as imagens deste texto.
\end{itemize}

\section{O que há a fazer}

\subsection{Instalação e teste}
A primeira parte do projecto consiste em:
\begin{enumerate}
\item	Instalar a partir da \emph{shell} as ferramentas a usar, de acordo com as
	instruções do apêndice \ref{sec:instala}.
\item	Seguir as instruções do apêndice \ref{sec:demo} que prefazem uma demo do
	serviço que já está montado.
\end{enumerate}
A instalação acima referida decorrerá numa \emph{installation party}
que será feita na primeira aula de apoio ao projecto.

A demo que se refere é muito importante para os alunos perceberem o significado
das operações que já estão disponíveis.

\subsection{Análise do material disponível}
A figura \ref{fig:sistema} descreve a arquitectura do sistema que o material da
secção \ref{sec:material} disponibiliza, composta por três camadas, a saber:
\begin{itemize}
\item	camada \textbf W(eb) --- o portal;
\item	camada \textbf M(iddleware) --- software intermédio, onde são realizadas as principais
	 operações do serviço;
\item	camada \textbf D(ata) --- o nível dos dados (ligação ao sistema de ficheiros).
\end{itemize}

\begin{figure}[ht!]
	\hrule
	\vskip 1em
	\begin{center}
	\includegraphics[width=0.7\textwidth]{sistema.pdf}
	\end{center}
	\hrule
	\caption{Arquitectura do sistema web \label{fig:sistema}.}
	\label{fig:arquitectura}
\end{figure}

A segunda parte do trabalho consiste em fazer uma
análise do material que é fornecido, a saber:
\begin{enumerate}
\item	Estudar a biblioteca \texttt{Auctions.lhs} ---
	começar por correr, na \emph{shell} 
\begin{quote}\tt
	lhs2tex Auctions.lhs > Auctions.tex ; pdflatex Auctions
\end{quote}
	produzindo-se assim o ficheiro \texttt{Auctions.pdf} que deverá ser
	impresso e cuidadosamente estudado.
\item
	Noutra \emph{shell}, executar
\begin{quote}\tt
	ghci Auctions.lhs
\end{quote}
	para assim se perceber que código e documentação coincidem, isto é,
	estão no mesmo ficheiro~\footnote{
	Esta estratégia designa-se programa\-ção \emph{literária} \cite{Kn92} e
	o seu princípio base é o seguinte:
\emph{a documentação de um programa deve coincidir com ele próprio}. Por outras palavras,
o código fonte e a sua documentação deverão constar do mesmo documento (ficheiro).
O texto que se está a ler é um exemplo de \emph{programação literária}.
}.
\end{enumerate}

\subsection{Programação}
Esta é a parte central do trabalho.
%-----------------------------------------------------
\begin{comment}
\begin{code}
module Projecto where
import Auctions
import Data.List
\end{code}
\end{comment}
%-----------------------------------------------------
As questões que se seguem referem-se ao módulo |Auctions|, a que os alunos
deverão acrescentar as funções pretendidas.  Os testes unitários que acompanham
cada alínea, que devem ser corridos para testar a correção das funções, referem
as seguintes configurações de leilões,
\begin{code}
badhouse =
  House [b1,b2,b4] [b3] where
    b1 = Auction 1 "Peter" "TV" 60 ""
    b2 = Auction 4 "Mary" "laptop" 120 ""
    b3 = Auction 3 "John" "phone" 85 "Peter"
    b4 = Auction 3 "Anne" "car" 8500 "Peter"

badhouse1 =
  House [b1,b2,b4] [b3] where
    b1 = Auction 1 "Peter" "TV" 60 ""
    b2 = Auction 4 "Mary" "laptop" 120 ""
    b3 = Auction 3 "John" "phone" 85 "Peter"
    b4 = Auction 1 "Peter" "TV" 60 ""
\end{code}
para além de |myhouse| que vem em \texttt{Auctions.lhs}.
Se se correr
\begin{quote}\tt
	ghci Projecto
\end{quote}
(ie., o ficheiro que se está a ler) ver-se-á que os testes estão já implementados
neste ficheiro, usando funções ``fantasma'', ie.\ incompletas. Estas, que
permitem já a compilação do módulo \footnote{Ver último anexo.}, devem ir
sendo apagadas à medida que as soluções pretendidas se forem escrevendo.
\begin{enumerate}
\item	Implementar a função |idsOk :: House -> Bool|
	que verifica que não há identificadores repetidos (nas duas listas).
       Testes unitários:
\begin{code}
test01 = idsOk myhouse == True
test02 = idsOk badhouse == False
test03 = idsOk badhouse1 == False
\end{code}
Correr estes testes para a função que se definiu.

\item	Acrescentar a função |idKey :: House -> Bool| que verifica se o identificador
	|actid| de um dado item o identifica univocamente. % (cf.\ chave primária).
Testes unitários:
\begin{code}
test04 = idKey myhouse  == True
test05 = idKey badhouse == False
test06 = idKey badhouse1 == True
\end{code}
Correr estes testes para a função que se definiu.

\item Será que |idKey| implica |idsOk|? Ou será que |idsOk| é que implica |idKey|?
      Ou nenhuma dessas implicações se verifica? Justifique a sua resposta.

\item	Acrescentar o predicado |allIds :: House -> Bool| que verifica 
	se a alocação de identificadores a leilões é sequencial.
Testes unitários:
\begin{code}
test07 = allIds myhouse == True
test08 = allIds badhouse == False
\end{code}
Correr estes testes para a função que se definiu.

\item É fácil de ver, exercitando \OlxUm, que a função |auctionBid| não
está implementada convenientemente, pois:
\begin{itemize}
\item	após fazer \textsf{Reset}, se licitar o item 1 por 61 euros e depois por 1 (um) euro,
	verá que o sistema aceita qualquer oferta, mesmo que seja inferior à melhor oferta
	até ao momento, violando o princípio do \emph{quem dá
	mais ganha} inerente a qualquer leilão;
\item	se licitar um item que não existe, por exemplo o item 99, o sistema
	dá erro.
\end{itemize}
Reescreva a função por forma a nenhuma das anomalias identificadas acima se verificar.

\item	Supondo que o formato interno |House| muda para
\begin{code}
data NHouse = NHouse { tot :: [ NAuction ] } deriving (Show,Eq)
\end{code}
onde
\begin{code}
data NAuction = NAuction { a :: Auction, st:: Status } deriving (Show,Eq)

data Status = Running | Finished deriving (Eq,Show)
\end{code}
escrever funções de conversão entre os dois formatos,
\begin{spec}
toNHouse :: House -> NHouse
toHouse :: NHouse -> House
\end{spec}
Testes unitários:
\begin{code}
test09 = toHouse(toNHouse myhouse) == myhouse
test10 = toNHouse(toHouse h) == h where h = toNHouse badhouse
\end{code}
Correr estes testes para a função que se definiu.

\item Redefinir o tipo |Auction| acrescentando-lhe mais um campo --- |actclass|, 
\begin{spec}
data Auction =  Auction {
                  actid     :: Int,
                  actowner  :: String,
                  actdesc   :: String,
                  actclass  :: String,
                  actvalue  :: Int,
                  actbidder :: String
                } deriving (Show,Eq,Ord) 
\end{spec}
que classifica itens em categorias (eg.\ electro-doméstico, mobiliário) e
escrever uma nova função de administração que totaliza os valores leiloados
por categoria ($x$ de electro-domésticos, $y$ de mobiliário, etc).
\item	Um dos defeitos do modelo de dados |Auction| é não guardar o valor
        inicial de licitação. Corrijir esta limitação, criando ainda uma
        função que contabilize quanto é que, em média, os itens vendidos
        se valorizaram no leilão.
\item	Ver que funções foram definidas que podem ser generalizadas polimorficamente,
	redefinindo-as com o seu tipo mais geral.
\end{enumerate}

\paragraph{Valorização.}
Escolher entre as seguintes sugestões:
\begin{enumerate}
\item	Como operação de manutenção, pretende-se repor um item não vendido no leilão,
	especificando nova base de licitação. Implementar esta operação.
\item	O módulo |Auctions| oferece a função genérica |sortOn| para ordenar
	sequências de registos segundo um dos seus atributos. Use-a de forma a   
	que a opção \textsf{List} de \OlxUm\ mostre leilões ordenados por identificador.
\end{enumerate}

\subsection{Relatório e defesa}
Deste trabalho deverá ser feito um pequeno relatório que deverá ser entregue
ao monitor do turno de cada grupo, bem como o código desenvolvido, tudo integrado
num único ficheiro processável por \lhstotex.

\paragraph{Oral.}
Cada grupo deverá defender o seu relatório numa apresentação oral em data
e moldes a definir na 
\href{http://wiki.di.uminho.pt/twiki/bin/view/Education/LI1/WebHome}{página}
da disciplina.

\appendix

\section*{Anexos}

\section{Guia de instalação}
\label{sec:instala}
Este anexo contém as instruções de instalação das ferramentas necessárias
para correr o ``kit'' do projecto, a saber:
\begin{itemize}

\item Ubuntu
\begin{enumerate}
\item Instalar GHCI (\url{http://www.haskell.org/platform/linux.html})
\begin{verbatim}
$ sudo apt-get install haskell-platform
\end{verbatim}
\item Actualizar o Cabal
\begin{verbatim}
$ cabal update
$ cabal install cabal-install
\end{verbatim}
\item Adicionar o novo cabal à PATH com precedência em relação à versão disponível para todos os utilizadores do sistema
\begin{verbatim}
$ echo 'export PATH=~/.cabal/bin:$PATH' >> ~/.bashrc
\end{verbatim}
\item Usar Cabal para instalar o Happstack:
\begin{verbatim}
$ cabal install primitive
$ cabal install aeson
$ cabal install happstack-lite
\end{verbatim}
\item Instalar lhs2TeX:
\begin{verbatim}
$ sudo apt-get install texlive-full 
$ cabal install lhs2tex
\end{verbatim}
Como teste, regenerar o ficheiro que se está a ler:
\begin{verbatim}
$ lhs2TeX Project.lhs > Project.tex
$ pdflatex Project.tex
\end{verbatim}
\end{enumerate}


\item Mac OS X
\begin{enumerate}
\item Instalar GHCI (\url{http://www.haskell.org/platform/mac.html})
\item Corrigir a path do Cabal para o gcc
	\footnote{\url{http://tinyurl.com/c6w2ks9}}.
\item Actualizar o Cabal
\begin{verbatim}
$ cabal update
$ cabal install cabal-install
\end{verbatim} 
\item Actualizar o \emph{link} simbólico para o binário do Cabal (adaptar de acordo)
\begin{verbatim}
$ sudo rm /usr/bin/cabal
$ sudo ln -s ~/Library/Haskell/ghc-7.0.3/lib/ \
cabal-install-1.16.0.1/bin/cabal /usr/bin/cabal
\end{verbatim}
\item Usar Cabal para instalar o Happstack:
\begin{verbatim}
$ cabal install aeson
$ cabal install happstack-lite
$ cabal install happstack-server
\end{verbatim}
\item Instalar lhs2TeX:
\begin{verbatim}
$ cabal install lhs2tex
\end{verbatim}
%\item Executar o servidor fornecido:
%\begin{verbatim}
%$ ghci WebApp.hs
%> :main
%\end{verbatim}
%\item Abrir \url{http://localhost:8000} :))
Como teste, regenerar o ficheiro que se está a ler:
\begin{verbatim}
$ lhs2TeX Project.lhs > Project.tex
$ pdflatex Project.tex
\end{verbatim}
\end{enumerate}

\item Windows
\begin{enumerate}
\item Instalar GHCI (\url{http://www.haskell.org/platform/windows.html})
\item Instalar Cabal (\url{http://www.haskell.org/cabal/download.html})
\begin{verbatim}
 > cd C:\Program Files\Haskell Platform\2012.4.0.0\bin
\end{verbatim}
\item Actualizar o Cabal
\begin{verbatim}
 > cabal.exe update
 > cabal.exe install cabal-install
 > cd C:\Documents and Settings\Afonso\Application Data\cabal\bin
\end{verbatim}
\item Usar Cabal para instalar o Happstack
\begin{verbatim}
 > cabal.exe install aeson
 > cabal.exe install happstack-lite-7.2.0
\end{verbatim}
\item Definir a porta do Happstack Server
\begin{verbatim}
main = simpleHTTP nullConf { port = 80 } $ handlers
\end{verbatim}
\item Instalar MiKTeX (\url{http://miktex.org/})
	\footnote{Nota: no Windows XP pode dar erro a finalizar a instalação mas os executáveis
	encontram-se no sistema.}
\item Adicionar a directoria onde se encontra o pdflatex à PATH
\begin{verbatim}
Control Panel > System > Advanced > Environment Variables 
System Path C:\Program Files\MiKTeX 2.9\miktex\bin
\end{verbatim}
\item Definir o environment code para poder compilar o PDF do enunciado (\url{http://www.haskell.org/haskellwiki/Literate_programming})
\begin{verbatim}
\newenvironment{code}{\verbatim}{\endverbatim}
\newenvironment{spec}{\verbatim}{\endverbatim}
\end{verbatim}
\item Como teste, regenerar o ficheiro que se está a ler:
\begin{verbatim}
 > pdflatex Project.lhs
\end{verbatim}
\end{enumerate}

\end{itemize}

\section{Demo}
\label{sec:demo}
Uma vez feita a instalação tal como se descreve no apêndice \ref{sec:instala},
é recomendável executar as acções seguintes na directoria em que
o ficheiro \href{http://www.di.uminho.pt/~jno/tgz/li11213mp.zip}{li11213mp.zip}
foi descompactado,
a saber:
\begin{enumerate}
\item	Ao nível da \emph{shell}, executar
\begin{quote}
\texttt{ghci WebApp}
\end{quote}
\item	De seguida, dentro do interpretador, correr a função \texttt{main}.
\item	O interpretador, que fica aparentemente bloqueado, deverá ter 
	iniciado o serviço da aplicação web \OlxUm:
	para se interagir com este, há que arrancar um qualquer \emph{browser} e
	nele abrir o endereço
\begin{quote}
\url{http://localhost:8000}
\end{quote}
	\begin{figure}[ht!]
	\hrule
	\vskip 1em
	\begin{center}
	\includegraphics[width=0.7\textwidth]{screen01.jpg}
	\end{center}
	\hrule
	\caption{Leilão acabado de abrir.}
	\label{fig:screen01}
	\end{figure}
\end{enumerate}
	Dever-se-á obter uma página com o aspecto da figura \ref{fig:screen01}.

De seguida, há que reproduzir cuidadosamente
a sequência de operações que se segue, na interface da aplicação web:
\begin{enumerate}
\item	Carregar em \textsf{List} e verificar que o aspecto da janela do \emph{browser}
	passa a ser o da figura \ref{fig:screen02},
	em que se podem ver os artigos que estão em leilão e os que já foram leiloados ou não
	tiveram comprador.
	\begin{figure}[ht!]
	\hrule
	\vskip 1em
	\begin{center}
	\includegraphics[width=0.7\textwidth]{screen02.jpg}
	\end{center}
	\hrule
	\caption{Estado corrente do leilão.}
	\label{fig:screen02}
	\end{figure}
\item	Seleccionar a opção \textsf{New} e proceder como se mostra na
	figura \ref{fig:screen03} para registar um novo artigo para ser leilado.
	\begin{figure}[ht!]
	\hrule
	\vskip 1em
	\begin{center}
	\includegraphics[width=0.7\textwidth]{screen03.jpg}
	\end{center}
	\hrule
	\caption{Entrada de um novo artigo no leilão.}
	\label{fig:screen03}
	\end{figure}
\item	Seleccionar a opção \textsf{Bid} e, tal como se mostra na figura \ref{fig:screen04},
	registar uma oferta de \textsf{Mary} para o artigo
	nr.1 que \textsf{Peter} tem no leilão.
\item
	Simular a aceitação por parte de \textsf{Peter} da oferta de \textsf{Mary}
	carregando em \textsf{Finished} na correspondente linha.
	O resultado de \textsf{List} passa a ser o dado na figura \ref{fig:screen05}.
	\begin{figure}[ht!]
	\hrule
	\vskip 1em
	\begin{center}
	\includegraphics[width=0.7\textwidth]{screen04.jpg}
	\end{center}
	\hrule
	\caption{Uma nova oferta para um artigo em leilão.}
	\label{fig:screen04}
	\end{figure}

	\begin{figure}[ht!]
	\hrule
	\vskip 1em
	\begin{center}
	\includegraphics[width=0.7\textwidth]{screen05.jpg}
	\end{center}
	\hrule
	\caption{Uma oferta aceite no leilão (\textsf{Mary} compra a TV que \textsf{Peter}
	tinha posto a leilão).}
	\label{fig:screen05}
	\end{figure}
\item	Finalmente, carregar em \textsf{Reset} e de novo em \textsf{List},
	para se verem os dados iniciais repostos.
\end{enumerate}
Desta forma se fica a perceber como funciona o leilão \OlxUm\ do ponto de vista do \emph{utilizador}
desta aplicação no web.

\paragraph{Administração.}
Contudo, há operações no leilão que não estão visíveis aos
utilizadores. Tratam-se de operações internas, administrativas,
a que não se quer dar acesso ao utilizador,
por exemplo: fazer a contabilidade de quanto se vendeu até ao momento,
a dos itens que não foram vendidos, etc.

Operações deste género são feitas pelos administradores do sítio ``por trás
da cortina''. É o que o leitor pode agora fazer, abrindo outra \emph{shell} Unix noutra janela,
sobre a mesma directoria, e nela invocando, de novo:
\begin{quote}
	\texttt{ghci WebApp}
\end{quote}
De seguida:
\begin{enumerate}
\item
Como exemplo de execução de operações em \emph{modo de administração},
sugere-se que se corra, nesse interpretador em ``background'':
\begin{quote}
\begin{verbatim}
*Main> evalOnWebData tots
Tot {tfin = 85, trun = 180, tlost = 0}
*Main>
\end{verbatim}
\end{quote}
Esta operação calcula os totais seguintes:
\texttt{tfin} dá o total já leiloado;
\texttt{trun} dá o total a ser leiloado;
\texttt{tlost} dá o total que não foi leiloado.
\item
Para se ver como os modos de utilização e de manutenção operam concorrentemente,
volte-se ao website e repita-se a oferta de \textsf{Mary} da figura \ref{fig:screen04}.
Se na \emph{shell} de administração se repetir
\begin{quote}
\begin{verbatim}
*Main> evalOnWebData tots
Tot {tfin = 85, trun = 190, tlost = 0}
*Main>
\end{verbatim}
\end{quote}
ve-se-á que o total correntemente a ser leiloado aumentou $70-60=10$ euros.
\item Suponha-se agora que, de novo no \emph{website}, \textsf{Mary} não tem ofertas para o seu 
	\emph{laptop} e desiste do leilão, carregando em \textsf{Finished}.
	Agora a mesma operação do modo de administração,
\begin{quote}
\begin{verbatim}
*Main> evalOnWebData tots
Tot {tfin = 85, trun = 70, tlost = 120}
*Main>
\end{verbatim}
\end{quote}
passará a contabilizar $120$ euros como valor perdido (não licitado),
correspondente ao \emph{laptop} que se não vendeu.
\end{enumerate}
Sugere-se que se façam mais experiências do género das descritas acima antes
de se abordar a parte central do trabalho.

\section{Esqueletos das funções a implementar}
\begin{code}
idsOk :: House -> Bool
idsOk h = error "idsOk: Por implementar"

idKey :: House -> Bool
idKey h = error "idKey: Por implementar"

allIds :: House -> Bool
allIds h = error "allIds: Por implementar"

toHouse :: NHouse -> House
toHouse h = error "toHouse: Por implementar"

toNHouse :: House -> NHouse
toNHouse h = error "toNHouse: Por implementar"

pKey :: (Auction -> Int) -> [Auction] -> Bool
pKey a l = error "pKey: Por implementar"

\end{code}

\bibliographystyle{alpha}
\bibliography{Referencias}

\end{document}
