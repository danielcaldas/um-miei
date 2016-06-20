
{-# LANGUAGE OverloadedStrings #-}

module Main where

import Auctions

import Db
import Control.Monad (msum)
import Control.Monad.IO.Class (liftIO)
import Happstack.Server
import Text.Blaze
import Text.Blaze.Internal
import qualified Text.Blaze.Html5 as H
import qualified Text.Blaze.Html5.Attributes as A

jsonData :: String
jsonData = "data.json"

main :: IO ()
main = simpleHTTP nullConf $ handlers

handlers :: ServerPart Response
handlers = do
  h <- liftIO $ houseLoad jsonData
  decodeBody myPolicy
  msum [ dir "list"     $ listAuctions h,
         dir "new"      $ newAuction,
         dir "add"      $ addAuction h,
         dir "bid"      $ bidAuction,
         dir "placebid" $ placebidAuction h,
         dir "finish"   $ path $ finishAuction h,
         dir "reset"    $ resetAuction,
         indexAuction h
       ]

appTemplate :: String -> H.Html -> H.Html
appTemplate title body =
  H.html $ do
    H.head $ do
      H.title (H.toHtml title)
      myStyle
    H.body $ do
      H.h1 "OLX UMinho"
      H.p $ do
        H.a "Home" ! A.href "/"
        H.span " | "
        H.a "List" ! A.href "/list"
        H.span " | "
        H.a "New" ! A.href "/new"
        H.span " | "
        H.a "Bid" ! A.href "/bid"
        H.span " | "
        H.a "Reset" ! A.href "/reset"
      H.hr
      body

indexAuction :: House -> ServerPart Response
indexAuction myh =
  ok $ toResponse $
    appTemplate "index" thisHtml
      where
        thisHtml = do
          H.p "Welcome to my auction house."
          H.p (string $ "Auctions running: "++(show $ length (hrunning myh)))
          H.p (string $ "Auctions finished: "++(show $ length (hfinished myh)))

newAuction :: ServerPart Response
newAuction =
  ok $ toResponse $
    appTemplate "New" thisHtml
         where
           thisHtml = do
             H.h3 "Create New Auction"
             H.form ! A.action "/add" ! A.method "POST" $ do
               H.table $ do
                 H.tr $ do
                   H.th "Owner"
                   H.td $ H.input ! A.name "owner" ! A.type_ "text"
                 H.tr $ do
                   H.th "Desc"
                   H.td $ H.input ! A.name "desc" ! A.type_ "text"
		 H.tr $ do
		   H.th "Class"
		   H.td $ H.input ! A.name "iclass" ! A.type_ "text"
                 H.tr $ do
                   H.th "Value"
                   H.td $ H.input ! A.name "value" ! A.type_ "text"
                 H.tr $ do
                   H.th ! A.colspan "2" $ H.input ! A.value "Create" ! A.type_ "submit"

addAuction :: House -> ServerPart Response
addAuction myh = do
    owner  <- look "owner"
    desc   <- look "desc"
    value  <- look "value"
    iclass <- look "iclass"
    let newvalue = read value :: Int
    let new = auctionNew myh owner desc iclass newvalue
    liftIO $ houseSave new jsonData
    ok $ toResponse $ appTemplate "New" (H.p "Added.")

bidAuction :: ServerPart Response
bidAuction =
  ok $ toResponse $
    appTemplate "Bid" thisHtml
         where
           thisHtml = do
             H.h3 "Place Bid"
             H.form ! A.action "/placebid" ! A.method "POST" $ do
               H.table $ do
                 H.tr $ do
                   H.th "Auction Id"
                   H.td $ H.input ! A.name "id" ! A.type_ "text"
                 H.tr $ do
                   H.th "Bidder"
                   H.td $ H.input ! A.name "bidder" ! A.type_ "text"
                 H.tr $ do
                   H.th "Value"
                   H.td $ H.input ! A.name "value" ! A.type_ "text"
                 H.tr $ do
                   H.th ! A.colspan "2" $ H.input ! A.value "Bid" ! A.type_ "submit"

placebidAuction :: House ->  ServerPart Response
placebidAuction myh = do
    id <- look "id"
    bidder  <- look "bidder"
    value <- look "value"
    let bidid = read id :: Int
    let bidvalue = read value :: Int
    let new = auctionBid myh bidid bidder bidvalue
    liftIO $ houseSave new jsonData
    ok $ toResponse $ appTemplate "New" (H.p "Bid placed.")

finishAuction :: House -> String -> ServerPart Response
finishAuction myh id = do
    let newid = read id :: Int
    let new = auctionFinish myh newid
    liftIO $ houseSave new jsonData
    ok $ toResponse $ appTemplate "Finish" (H.p "Leilão terminado.")

listAuctions :: House -> ServerPart Response
listAuctions myh =
  ok $ toResponse $
    appTemplate "Lista" thisHtml
         where
           thisHtml = do
             H.h3 "Auctions running"
             H.table $ do
               auctionHdr True
               mapM_ (auctionHtml True) (hrunning myh)
             H.h3 "Auctions finished"
             H.table $ do
               auctionHdr False
               mapM_ (auctionHtml False) (hfinished myh)

resetAuction :: ServerPart Response
resetAuction = do
    liftIO $ houseSave myhouse jsonData
    ok $ toResponse $ appTemplate "Reset" (H.p "Reset.")

auctionHdr :: Bool -> H.Html
auctionHdr running = H.tr $ do
                       H.th "Id"
                       H.th "Owner"
                       H.th "Description"
		       H.th "Class"
		       H.th "Initial Value"
                       H.th "Value"
                       if running then
                         H.th "Bidder"
                       else
                         H.th "Winner"
                       if running then
                         H.th "Actions"
                       else
                         ""

auctionHtml :: Bool -> Auction -> H.Html
auctionHtml running a = H.tr $ do
                          H.td $ H.toHtml (actid a)
                          H.td $ H.toHtml (actowner a)
                          H.td $ H.toHtml (actdesc a)
			  H.td $ H.toHtml (actclass a)
			  H.td $ H.toHtml (actinit a)
                          H.td $ H.toHtml (actvalue a)
                          H.td $ H.toHtml (actbidder a)
                          if (running) then
                            let link = "/finish/"++(show $ actid a) :: String
                            in H.td $ H.a "Finish" ! A.href (stringValue link)
                          else
                            ""

myStyle :: H.Html
myStyle = H.style ! A.type_ "text/css" $ "body { font-family: Arial, Helvetica, Tahoma, sans-serif; } th, td { border: 1px solid black; padding: 5px 15px; }"

myPolicy :: BodyPolicy
myPolicy = (defaultBodyPolicy "/tmp/" 0 1000 1000)

evalOnWebData f = do { x <- houseLoad jsonData ; return (f x) }

-------------------------- CORTAR ------
auctionBackTrans :: Int -> Int -> IO ()
auctionBackTrans id v = houseSave (auctionBack id v myhouse) jsonData

auctionBack :: Int -> Int -> House -> House
auctionBack id v h = 
  let sel = filter ((==id).actid) (hfinished h)
      rem = filter ((/=id).actid) (hfinished h)
  in  if sel == [] then h
      else let r  = head sel
               r' = Auction (actid r) (actowner r) (actdesc r) (actclass r) (actinit r) v ""
           in House (r':hrunning h) rem

