(ns ttt-clj-java-server.api.router)

(defn add-route [router method route iroute]
  (.addRoute router method route iroute))

(defn add-routes [router routes-map]
  (doseq [[method-route iroute] routes-map]
    (doseq [[method route] method-route]
      (add-route router method route iroute))))

(defn routes [router]
  (.routes router))

(defn new-router []
  (new main.routing.routers.Router))
