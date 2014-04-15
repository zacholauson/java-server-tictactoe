(ns ttt-clj-java-server.core
  (:require [ttt-clj-java-server.server :refer [start] :as server]))

(defn -main []
  (server/start 7000 "/"))
