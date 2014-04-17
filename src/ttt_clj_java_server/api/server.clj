(ns ttt-clj-java-server.api.server)

(def router main.Server/ROUTER)

(defn init [port location]
  (main.Server/init port location))

(defn start []
  (main.Server/start))
