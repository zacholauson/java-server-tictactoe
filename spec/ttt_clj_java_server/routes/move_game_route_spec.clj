(ns ttt-clj-java-server.routes.move-game-route-spec
  (:require [speclj.core :refer :all]
            [ttt-clj-java-server.routes.move-game-route :refer :all]
            [ttt-clj-java-server.api.response.response :refer [new-response get-headers]]))

(def mock-request
  (reify main.requests.IRequest
    (getParams [this]
      (java.util.HashMap. {"move" "1"}))
    (getCookies [this]
      (java.util.HashMap. {"computer"   "x"
                           "human"      "o"
                           "gametype"   "computer-vs-human"
                           "difficulty" "unbeatable"
                           "board"      "x--------"}))))

(describe "MoveGameRoute"
  (describe "#pull-move-from-params"
    (it "should pull the move from the given params"
      (should= 1 (pull-move-from-params {"move" "1"}))))
  (describe "#build-response"
    (it "should return a response redirecting to play with the board updated with the posted move"
      (should= (str "HTTP/1.1 301 Moved Permanently\r\n"
                    "Location: /play\r\n"
                    "Set-Cookie: board=xo-------\r\n\r\n")
               (get-headers (build-response mock-request (new-response)))))))
