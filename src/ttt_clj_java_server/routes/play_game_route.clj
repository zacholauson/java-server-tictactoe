(ns ttt-clj-java-server.routes.play-game-route
  (:require [ttt-clj-java-server.helpers.gamestate-helpers  :refer [build-gamestate board->board-string computers-turn?] :as gamestate-helpers]
            [ttt-clj-java-server.helpers.view-helpers       :refer [*view-context*]                                      :as view-helpers]
            [ttt-clj-java-server.api.response               :refer [set-status add-header add-cookie set-body]           :as response-api]
            [ttt-clj-java-server.api.request                :refer [get-cookies]                                         :as request-api]
            [ttt-clj-java-server.views                      :refer [render]                                              :as views]
            [ttt-clojure.players.computer                   :refer [new-computer]                                        :as ttt-computer]
            [ttt-clojure.players.human                      :refer [new-human]                                           :as ttt-human]
            [ttt-clojure.gamestate                          :refer [game-over? move]                                     :as gamestate]
            [ttt-clojure.ai                                 :refer [find-move]                                           :as ai]))

(defn computer-move-response [response gamestate]
  (response-api/set-status response 301)
  (response-api/add-header response "Location" "/play")
  (response-api/add-cookie response "board" (gamestate-helpers/board->board-string (:board (gamestate/move gamestate (ai/find-move gamestate)))))
  response)

(defn display-board-response [response gamestate]
  (response-api/set-status response 200)
  (response-api/set-body response (render "play" (merge *view-context* {:board       (:board gamestate)
                                                                         :game-over? (gamestate/game-over? gamestate)})))
  response)

(defn new-game-form-response [response]
  (response-api/set-status response 200)
  (response-api/set-body   response (render "play"))
  response)

(defn build-response [request response]
  (let [cookies (request-api/get-cookies request)]
    (if (empty? cookies)
      (new-game-form-response response)
      (let [gamestate (gamestate-helpers/build-gamestate cookies)]
        (if (game-over? gamestate)
          (display-board-response response gamestate)
          (if (computers-turn? gamestate)
            (computer-move-response response gamestate)
            (display-board-response response gamestate)))))))

(defrecord PlayGameRoute []
  main.routing.routes.IRoute
  (buildResponse [this request response]
    (build-response request response)))

(defn new-play-game-route []
  (PlayGameRoute.))
