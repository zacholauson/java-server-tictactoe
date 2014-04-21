(ns ttt-clj-java-server.routes.new-game-route
  (:require [ttt-clj-java-server.helpers.gamestate-helpers :refer [board->board-string parse-int]     :as gamestate-helper]
            [ttt-clj-java-server.api.response              :refer [set-status add-header add-cookies] :as response-api]
            [ttt-clj-java-server.api.request               :refer [get-params]                        :as request-api]
            [ttt-clojure.gamestate                         :refer [create-board]                      :as gamestate]))

(defn build-response [request response]
  (let [params       (request-api/get-params request)
        gametype     (get params "gametype")
        difficulty   (get params "difficulty")
        board-size   (-> (get params "board-size") (gamestate-helper/parse-int))]
    (response-api/set-redirect response "/play")
    (response-api/add-cookies  response
      {"computer"   "x"
       "human"      "o"
       "gametype"   gametype
       "difficulty" difficulty
       "board"      (board->board-string (gamestate/create-board board-size))}))
  response)

(defrecord NewGameRoute []
  main.routing.routes.IRoute
  (buildResponse [this request response]
    (build-response request response)))

(defn new-game-route []
  (NewGameRoute.))
