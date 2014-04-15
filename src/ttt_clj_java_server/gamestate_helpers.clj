(ns ttt-clj-java-server.gamestate-helpers)

(defn board-string->board [board-string]
  (->> (clojure.string/split board-string #"")
       (remove #{""})
       (map keyword)
       (apply vector)))
