(ns ttt-clj-java-server.server
  (:require [ttt-clj-java-server.api.routing.router     :refer [add-route] :as router]
            [ttt-clj-java-server.routes.new-game-route  :refer [new-game-route]]
            [ttt-clj-java-server.routes.play-game-route :refer [new-play-game-route]]
            [ttt-clj-java-server.routes.move-game-route :refer [new-move-game-route]]))

(defn build-game-routes []
  (router/add-route main.Server/ROUTER "GET"  "/"         (new main.routing.routes.RedirectRoute "/play"))
  (router/add-route main.Server/ROUTER "POST" "/new-game" (new-game-route))
  (router/add-route main.Server/ROUTER "GET" "/play"      (new-play-game-route))
  (router/add-route main.Server/ROUTER "POST" "/move"     (new-move-game-route)))

(defn start [port location]
  (main.Server/init port location)
  (build-game-routes)
  (main.Server/start))
