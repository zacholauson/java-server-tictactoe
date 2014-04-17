(ns ttt-clj-java-server.api.router)

(defn add-route [router method route iroute]
  (.addRoute router method route iroute))

(defn add-routes [router routes-map]
  (doseq [[method-route route] routes-map]
    (add-route router (first method-route) (second method-route) route)))

(defn routes [router]
  (.routes router))

(defn new-router []
  (new main.routing.routers.Router))
