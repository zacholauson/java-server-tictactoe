(ns ttt-clj-java-server.routes.move-game-route
  (:require [ttt-clojure.gamestate :refer [move] :as gamestate]
            [ttt-clj-java-server.gamestate-helpers :refer :all :as gh]
            [ttt-clojure.players.computer :refer [new-computer] :as ttt-computer]
            [ttt-clojure.players.human :refer [new-human] :as ttt-human]
            [ttt-clj-java-server.api.response.response :refer :all]))

(defn parse-int [string]
  (cond
    (string? string) (Integer. (re-find  #"\d+" string ))
    (integer? string) string
    :else (throw (Exception. "cannot convert given argument to integer"))))

(defn build-response [request response]
  (let [params (.getParams request)
        move-int   (parse-int (clojure.string/trim (get params "move")))
        gamestate (move (gh/build-gamestate request) move-int)]
    (set-status response 301)
    (add-header response "Location" "/play")
    (add-cookie response "board" (gh/board->board-string (:board gamestate))))
    response)

(defrecord MoveGameRoute []
  main.routing.routes.IRoute
  (buildResponse [this request response]
    (build-response request response)))

(defn new-move-game-route []
  (MoveGameRoute.))
