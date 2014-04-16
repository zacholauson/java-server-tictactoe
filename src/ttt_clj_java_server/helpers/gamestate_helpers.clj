(ns ttt-clj-java-server.helpers.gamestate-helpers
  (:require [ttt-clojure.players.computer :refer [new-computer] :as ttt-computer]
            [ttt-clojure.players.human    :refer [new-human]    :as ttt-human]))

(defn parse-int [string]
    (cond
      (string? string) (Integer. (re-find  #"\d+" string ))
      (integer? string) string
      :else (throw (Exception. "cannot convert given argument to integer")  )))

(defn count-of [board piece]
  (count (filter #(= piece %) board)))

(defn xs-turn? [board]
  (<= (count-of board :x)
      (count-of board :o)))

(defn os-turn? [board]
  (> (count-of  board :x)
     (count-of  board :o)))

(defn turn [board]
  (cond
    (xs-turn? board) :x
    (os-turn? board) :o))

(defn computers-turn? [board computers-piece]
  (= computers-piece (turn board)))

(defn board->board-string [board]
  (->> board
       (map name)
       (apply str)))

(defn board-string->board [board-string]
  (->> (clojure.string/split board-string #"")
       (remove #{""})
       (map keyword)
       (apply vector)))

(defmulti build-gamestate (fn [cookies] (keyword (get cookies "gametype"))))

(defmethod build-gamestate :computer-vs-human [cookies]
  (let [computer            (ttt-computer/new-computer :x)
        human               (ttt-human/new-human :o nil)
        players             [computer human]
        board               (board-string->board (get cookies "board"))
        difficulty-setting  (keyword (get cookies "difficulty"))]
    {:board board
     :players (if (computers-turn? board :x) [computer human] [human computer])
     :computer :x
     :options {:difficulty difficulty-setting}}))

(defmethod build-gamestate :human-vs-computer [cookies]
  (let [computer            (ttt-computer/new-computer :o)
        human               (ttt-human/new-human :x nil)
        players             [human computer]
        board               (board-string->board (get cookies "board"))
        difficulty-setting (keyword (get cookies "difficulty"))]
    {:board board
     :players (if (computers-turn? board :o) [computer human] [human computer])
     :computer :o
     :options {:difficulty difficulty-setting}}))

(defmethod build-gamestate :computer-vs-computer [cookies]
  (let [computer            (ttt-computer/new-computer :x)
        computer2           (ttt-computer/new-computer :o)
        players             [computer computer2]
        board               (board-string->board (get cookies "board"))
        difficulty-setting (keyword (get cookies "difficulty"))]
    {:board board
     :players (if (computers-turn? board :x) [computer computer2] [computer2 computer])
     :computer :x
     :options {:difficulty difficulty-setting}}))

(defmethod build-gamestate :human-vs-human [cookies]
  (let [human               (ttt-human/new-human :x nil)
        human2              (ttt-human/new-human :o nil)
        players             [human human2]
        board               (board-string->board (get cookies "board"))]
    {:board board
     :players (if (computers-turn? board :x) [human human2] [human2 human])
     :options {:difficulty :easy}}))
