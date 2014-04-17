(ns ttt-clj-java-server.helpers.board-view-helpers-spec
  (:require [speclj.core :refer :all]
            [ttt-clj-java-server.helpers.board-view-helpers :refer :all]))

(describe "BoardViewHelpers"
  (describe "#row-size"
    (it "should return the correct row size for the given board"
      (should= 3 (row-size [:- :- :- :- :- :- :- :- :-]))
      (should= 4 (row-size [:- :- :- :- :- :- :- :- :- :- :- :- :- :- :- :-]))
      (should= 5 (row-size [:- :- :- :- :- :- :- :- :- :- :- :- :- :- :- :- :- :- :- :- :- :- :- :- :-]))))

  (describe "#index-board"
    (it "should return an indexed and formatted board"
      (should= [" 0" " 1" " 2" " 3" " 4" " 5" " 6" " 7" " 8"] (index-board [:- :- :- :- :- :- :- :- :-]))))

  (describe "#partitioned-board"
    (it "should partition and index the board"
      (should= [[" 0" " 1" " 2"] [" 3" " 4" " 5"] [" 6" " 7" " 8"]] (partitioned-board [:- :- :- :- :- :- :- :- :-])))))
