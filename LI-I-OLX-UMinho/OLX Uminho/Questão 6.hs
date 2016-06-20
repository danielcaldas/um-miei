import Data.Char
data NHouse = NHouse {tot::[NAuction]} deriving (Show,Eq)
data NAuction = NAuction {a::Auction,st::Status} deriving (Show,Eq)
data Status = Running|Finished deriving (Eq,Show)
data Auction = Auction {
                 actid     :: Int,
                 actowner  :: String,
                 actdesc   :: String,
                 actvalue  :: Int,
                 actbidder :: String
               } deriving (Show,Eq,Ord)
data House = House {
               hrunning  :: Running,
               hfinished :: Finished
             } deriving (Show,Eq)
type Running  = [Auction]
type Finished = [Auction]


toAuc :: NAuction -> Auction
toAuc (NAuction (Auction (a,b,c,d,e),st)) = (Auction a b c d e)

toNAucR :: Auction -> NAuction
toNAucR a = (NAuction a Running)

toNAucF :: Auction -> NAuction
toNAucF a = (NAuction a Finished)


 