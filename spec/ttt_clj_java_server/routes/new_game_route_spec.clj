(ns ttt-clj-java-server.routes.new-game-route-spec
  (:require [speclj.core                               :refer :all]
            [ttt-clj-java-server.routes.new-game-route :refer :all]
            [ttt-clj-java-server.api.response          :refer [new-response get-headers]]))

(def mock-request
  (reify main.requests.IRequest
    (getParams [this]
      (java.util.HashMap. {"board-size" "3" "difficulty" "unbeatable" "gametype" "computer-vs-human"}))))

(describe "NewGameRoute"
  (describe "#build-response"
    (it "should return a response with a 301 redirect to /play with the proper cookies"
      (should= (str "HTTP/1.1 301 Moved Permanently\r\n"
                    "Location: /play\r\n"
                    "Set-Cookie: computer=x\r\n"
                    "Set-Cookie: human=o\r\n"
                    "Set-Cookie: gametype=computer-vs-human\r\n"
                    "Set-Cookie: difficulty=unbeatable\r\n"
                    "Set-Cookie: board=---------\r\n\r\n") (-> (build-response mock-request (new-response)) (get-headers))))))
