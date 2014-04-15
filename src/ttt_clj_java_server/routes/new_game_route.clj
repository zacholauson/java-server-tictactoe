(ns ttt-clj-java-server.routes.new-game-route
  (:require [ttt-clojure.gamestate :refer [create-board] :as gamestate]
            [ttt-clj-java-server.api.response.response :refer :all]))

(defn board-string [board]
  (->> board
       (map name)
       (apply str)))

(defn build-response [request response row-size]
  (set-status response 301)
  (add-header response "Location" "/play")
  (add-cookie response "computer" "x")
  (add-cookie response "human"    "o")
  (add-cookie response "board"    (board-string (create-board 3)))
  response)

(defrecord NewGameRoute [row-size]
  main.routing.routes.IRoute
  (buildResponse [this request response]
    (build-response request response row-size)))

(defn new-game-route [row-size]
  (NewGameRoute. row-size))
