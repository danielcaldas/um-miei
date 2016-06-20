
{-# LANGUAGE TemplateHaskell #-}

module Db where

import Auctions
import Data.Aeson (decode, encode)
import Data.Aeson.TH (deriveJSON)
import qualified Data.ByteString as B
import qualified Data.ByteString.Lazy as BL

-- json

$(deriveJSON id ''Auction)
$(deriveJSON id ''House)

-- > Save auction house to file.
houseSave :: House -> String -> IO ()
houseSave h f = do
  B.writeFile f (B.concat $ BL.toChunks (encode h))

-- > Read auction house from file.
houseLoad :: String -> IO House
houseLoad f = do
  s <- B.readFile f
  let h = decode (BL.pack $ B.unpack s) :: Maybe House
  case h of
    (Just a)  -> return a
    (Nothing) -> return (House [] [])
