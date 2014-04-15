(ns ttt-clj-java-server.server
  (:require [ttt-clj-java-server.api.routing.router    :refer [add-route] :as router]
            [ttt-clj-java-server.routes.new-game-route :refer [new-game-route]]
            [ttt-clj-java-server.routes.play-game-route :refer [new-play-game-route]]
            [ttt-clj-java-server.routes.move-game-route :refer [new-move-game-route]]))

(defn start [port location]
  (main.Server/init port location)
  (router/add-route main.Server/ROUTER "GET" "/" (new-game-route 3))
  (router/add-route main.Server/ROUTER "GET" "/play" (new-play-game-route))
  (router/add-route main.Server/ROUTER "POST" "/move" (new-move-game-route))
  (main.Server/start))
