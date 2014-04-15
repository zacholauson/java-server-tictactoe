(ns ttt-clj-java-server.gamestate-helpers-spec
  (:require [speclj.core                           :refer :all]
            [ttt-clj-java-server.gamestate-helpers :refer :all]))

(describe "board-string->board"
  (it "should take in a board string and return a board"
    (should= [:- :- :- :- :- :- :- :- :-] (board-string->board "---------"))
    (should= [:x :- :- :o :- :- :- :- :-] (board-string->board "x--o-----"))))
