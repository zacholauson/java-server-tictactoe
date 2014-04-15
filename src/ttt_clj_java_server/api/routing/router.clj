(ns ttt-clj-java-server.api.routing.router)

(defn add-route [router method route iroute]
  (.addRoute router method route iroute))

(defn routes [router]
  (.routes router))

(defn new-router []
  (new main.routing.routers.Router))
