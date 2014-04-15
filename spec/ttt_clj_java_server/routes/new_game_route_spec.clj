(ns ttt-clj-java-server.routes.new-game-route-spec
  (:require [speclj.core :refer :all]
            [ttt-clj-java-server.routes.new-game-route :refer :all]
            [ttt-clj-java-server.api.response.response :refer [new-response get-headers]]))

(defn new-mock-request []
  (new test.mocks.MockRequest "GET" "/"))

(describe "NewGameRoute"
  (describe "#board-string"
    (it "should create a string for the given board"
      (should= "---------" (board-string [:- :- :- :- :- :- :- :- :-]))
      (should= "xx-o--o--" (board-string [:x :x :- :o :- :- :o :- :-])))))
