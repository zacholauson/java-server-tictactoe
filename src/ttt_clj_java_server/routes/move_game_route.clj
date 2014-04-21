(ns ttt-clj-java-server.routes.move-game-route
  (:require [ttt-clj-java-server.api.response              :refer [set-status add-header add-cookie]              :as response-api]
            [ttt-clj-java-server.api.request               :refer [get-params get-cookies]                        :as request-api]
            [ttt-clojure.players.computer                  :refer [new-computer]                                  :as ttt-computer]
            [ttt-clojure.players.human                     :refer [new-human]                                     :as ttt-human]
            [ttt-clojure.gamestate                         :refer [move]                                          :as gamestate]
            [ttt-clj-java-server.helpers.gamestate-helpers :refer [build-gamestate board->board-string parse-int] :as gamestate-helpers]))

(defn pull-move-from-params [params]
  (gamestate-helpers/parse-int (clojure.string/trim (get params "move"))))

(defn build-response [request response]
  (let [params    (request-api/get-params request)
        move-int  (pull-move-from-params params)
        pre-move-gamestate (gamestate-helpers/build-gamestate (request-api/get-cookies request))
        post-move-gamestate (gamestate/move pre-move-gamestate move-int)]
    (response-api/set-redirect response "/play")
    (response-api/add-cookie   response "board" (gamestate-helpers/board->board-string (:board post-move-gamestate))))
    response)

(defrecord MoveGameRoute []
  main.routing.routes.IRoute
  (buildResponse [this request response]
    (build-response request response)))

(defn new-move-game-route []
  (MoveGameRoute.))
