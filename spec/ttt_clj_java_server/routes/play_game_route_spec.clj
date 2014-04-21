(ns ttt-clj-java-server.routes.play-game-route-spec
  (:require [speclj.core :refer :all]
            [ttt-clj-java-server.routes.play-game-route :refer :all]
            [ttt-clojure.players.computer               :refer [new-computer] :as ttt-computer]
            [ttt-clojure.players.human                  :refer [new-human]    :as ttt-human]
            [ttt-clj-java-server.api.response           :refer :all           :as response-api]))

(def new-gamestate
  {:board [:- :- :- :- :- :- :- :- :-]
   :players [(ttt-computer/new-computer :x) (ttt-human/new-human :o nil)]
   :options {:difficulty :unbeatable}})

(describe "PlayGameRoute"
  (describe "#build-view-context"
    (it "should return a map with the info needed for the board to be rendered"
      (should= {:board [:- :- :- :- :- :- :- :- :-]
                :game-over? false} (build-view-context new-gamestate))))

  (describe "#computer-move-response"
    (it "should return a response with the computer move in it"
      (should= (str "HTTP/1.1 301 Moved Permanently\r\n"
                    "Location: /play\r\n"
                    "Set-Cookie: board=x--------\r\n\r\n")
               (-> (computer-move-response (response-api/new-response) new-gamestate) (response-api/get-headers)))))

  (describe "#display-board-response"
    (it "should return a response with 200 OK response"
      (should= "HTTP/1.1 200 OK\r\n\r\n"
               (-> (display-board-response (response-api/new-response) new-gamestate) (response-api/get-headers)))))

  (describe "#new-game-form-response"
    (it "should return a response with 200 OK response"
      (should= "HTTP/1.1 200 OK\r\n\r\n"
               (-> (new-game-form-response (response-api/new-response)) (response-api/get-headers))))))
