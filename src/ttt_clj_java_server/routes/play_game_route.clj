(ns ttt-clj-java-server.routes.play-game-route
  (:require [ttt-clj-java-server.gamestate-helpers :refer :all :as gh]
            [ttt-clojure.players.computer :refer [new-computer] :as ttt-computer]
            [ttt-clojure.players.human :refer [new-human] :as ttt-human]
            [ttt-clj-java-server.api.response.response :refer :all]
            [ttt-clojure.ai :refer [find-move]]
            [ttt-clojure.gamestate :refer [game-over? move] :as gamestate]
            [ttt-clj-java-server.board-view-helpers :refer [build-board-layout]]))

(defn build-gamestate [request]
  (let [cookies (.getCookies request)
        computer (ttt-computer/new-computer (get cookies "computer"))
        computer-piece (get cookies "computer")
        human    (ttt-human/new-human (get cookies "human") nil)
        board    (gh/board-string->board (get cookies "board"))]
    {:board board :players [computer human] :computer computer-piece :options {:difficulty :unbeatable}}))

(defn build-response [request response]
  (let [gamestate (build-gamestate request)]
    (set-status response 200)
    (set-body response (build-board-layout (:board gamestate))))
  response)

(defrecord PlayGameRoute []
  main.routing.routes.IRoute
  (buildResponse [this request response]
    (build-response request response)))

(defn new-play-game-route []
  (PlayGameRoute.))
