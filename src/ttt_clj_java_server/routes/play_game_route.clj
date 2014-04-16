(ns ttt-clj-java-server.routes.play-game-route
  (:require [ttt-clj-java-server.gamestate-helpers :refer :all :as gh]
            [ttt-clojure.players.computer :refer [new-computer] :as ttt-computer]
            [ttt-clojure.players.human :refer [new-human] :as ttt-human]
            [ttt-clj-java-server.api.response.response :refer :all]
            [ttt-clojure.ai :refer [find-move]]
            [ttt-clojure.gamestate :refer [game-over? move] :as gamestate]
            [ttt-clj-java-server.board-view-helpers :refer [build-board-layout]]))

(defn current-player [gamestate]
  (first (:players gamestate)))

(defn computer-turn? [gamestate]
  (= (str (type (current-player gamestate))) "class ttt_clojure.players.computer.Computer"))

(defn build-response [request response]
  (let [gamestate (gh/build-gamestate request)]
    (if (computer-turn? gamestate)
      (do
        (set-status response 301)
        (add-header response "Location" "/play")
        (add-cookie response "board"    (gh/board->board-string (:board (move gamestate (find-move gamestate))))))
      (do
        (set-status response 200)
        (set-body   response (build-board-layout (:board gamestate) (gamestate/game-over? gamestate))))))
  response)

(defrecord PlayGameRoute []
  main.routing.routes.IRoute
  (buildResponse [this request response]
    (build-response request response)))

(defn new-play-game-route []
  (PlayGameRoute.))
