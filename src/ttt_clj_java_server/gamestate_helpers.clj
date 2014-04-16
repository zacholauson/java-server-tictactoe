(ns ttt-clj-java-server.gamestate-helpers
  (:require [ttt-clojure.players.computer :refer [new-computer] :as ttt-computer]
            [ttt-clj-java-server.gamestate-helpers :refer :all :as gh]
            [ttt-clojure.players.human :refer [new-human] :as ttt-human]))

(defn board->board-string [board]
  (->> board
       (map name)
       (apply str)))

(defn board-string->board [board-string]
  (->> (clojure.string/split board-string #"")
       (remove #{""})
       (map keyword)
       (apply vector)))

(defn count-of [board piece]
  (count (filter #(= piece %) board)))

(defn xs-turn? [board]
  (<= (count-of board :x)
      (count-of board :o)))

(defn os-turn? [board]
  (> (count-of board :x)
     (count-of board :o)))

(defn turn [board]
  (cond
    (xs-turn? board) :x
    (os-turn? board) :o))

(defn computers-turn? [board computers-mark]
  (= computers-mark (turn board)))

(defn build-gamestate [request]
  (let [cookies        (.getCookies request)
        computer       (ttt-computer/new-computer (keyword (get cookies "computer")))
        computer-piece (keyword (get cookies "computer"))
        human          (ttt-human/new-human (keyword (get cookies "human")) nil)
        board          (gh/board-string->board (get cookies "board"))]
    {:board board :players (if (computers-turn? board computer-piece) [computer human] [human computer]) :computer computer-piece :options {:difficulty :unbeatable}}))
