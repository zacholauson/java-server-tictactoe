(ns ttt-clj-java-server.routes.move-game-route
  (:require [ttt-clojure.gamestate :refer [move] :as gamestate]
            [ttt-clj-java-server.gamestate-helpers :refer :all :as gh]
            [ttt-clojure.players.computer :refer [new-computer] :as ttt-computer]
            [ttt-clojure.players.human :refer [new-human] :as ttt-human]
            [ttt-clj-java-server.api.response.response :refer :all]))

(defn board-string [board]
  (->> board
       (map name)
       (apply str)))

(defn parse-int [string]
  (cond
    (string? string) (Integer. (re-find  #"\d+" string ))
    (integer? string) string
    :else (throw (Exception. "cannot convert given argument to integer"))))

(defn build-gamestate [request]
  (let [cookies (.getCookies request)
        computer (ttt-computer/new-computer (get cookies "computer"))
        computer-piece (get cookies "computer")
        human    (ttt-human/new-human (get cookies "human") nil)
        board    (gh/board-string->board (get cookies "board"))]
    {:board board :players [computer human] :computer computer-piece :options {:difficulty :unbeatable}}))

(defn build-response [request response]
  (let [params (.getParams request)
        move-int   (parse-int (clojure.string/trim (get params "move")))
        gamestate (move (build-gamestate request) move-int)]
    (prn gamestate)
    (set-status response 301)
    (add-header response "Location" "/play")
    (add-cookie response "board" (board-string (:board gamestate))))
    response)

(defrecord MoveGameRoute []
  main.routing.routes.IRoute
  (buildResponse [this request response]
    (build-response request response)))

(defn new-move-game-route []
  (MoveGameRoute.))
