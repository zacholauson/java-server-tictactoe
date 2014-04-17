(ns ttt-clj-java-server.core
  (:require [ttt-clj-java-server.api.server             :refer [init start router]  :as server]
            [ttt-clj-java-server.api.router             :refer [add-routes routes]  :as router]
            [ttt-clj-java-server.routes.new-game-route  :refer [new-game-route]]
            [ttt-clj-java-server.routes.play-game-route :refer [new-play-game-route]]
            [ttt-clj-java-server.routes.move-game-route :refer [new-move-game-route]]
            [ttt-clj-java-server.cli.options            :refer [wrap-cli opts port] :as cli]))

(defn -main [& args]
  (cli/wrap-cli args)
  (server/init (cli/port) "/")
  (router/add-routes
    server/router
    {["GET"  "/"]         (new main.routing.routes.RedirectRoute "/play")
     ["POST" "/new-game"] (new-game-route)
     ["GET"  "/play"]     (new-play-game-route)
     ["POST" "/move"]     (new-move-game-route)})
  (server/start))
