(ns ttt-clj-java-server.routes.new-game-route
  (:require [ttt-clj-java-server.api.response.response     :refer [set-status add-header add-cookies] :as response-api]
            [ttt-clj-java-server.api.request.request       :refer [get-params]                        :as request-api]
            [ttt-clj-java-server.helpers.gamestate-helpers :refer [board->board-string parse-int]     :as gamestate-helper]
            [ttt-clojure.gamestate                         :refer [create-board]                      :as gamestate]))

(defn build-response [request response]
  (let [params     (request-api/get-params request)]
    (response-api/set-status  response 301)
    (response-api/add-header  response "Location" "/play")
    (response-api/add-cookies response
      {"computer"   "x"
       "human"      "o"
       "gametype"   (get params "gametype")
       "difficulty" (get params "difficulty")
       "board"      (board->board-string (gamestate/create-board (gamestate-helper/parse-int (get params "board-size"))))}))
  response)

(defrecord NewGameRoute []
  main.routing.routes.IRoute
  (buildResponse [this request response]
    (build-response request response)))

(defn new-game-route []
  (NewGameRoute.))
