(ns ttt-clj-java-server.helpers.gamestate-helpers-spec
  (:require [speclj.core                                   :refer :all]
            [ttt-clj-java-server.helpers.gamestate-helpers :refer :all]
            [ttt-clojure.players.computer                  :refer [new-computer] :as ttt-computer]
            [ttt-clojure.players.human                     :refer [new-human]    :as ttt-human]))

(describe "GamestateHelpers"
  (describe "#parse-int"
    (it "should convert the given input to a integer if possible"
      (should= 1 (parse-int "1"))
      (should= 1 (parse-int  1 )))
    (it "should throw an exception if the given value cannot be converted to an integer"
      (should-throw Exception (parse-int "abc"))))

  (describe "#count-of"
    (it "should return the count of the given piece in the board"
      (should= 2 (count-of (board-string->board "xxo------") :x))
      (should= 1 (count-of (board-string->board "xxo------") :o))))

  (describe "#xs-turn?"
    (it "should evaluate the given gamestate and return true if its x's turn and false if not"
      (should= true  (xs-turn? (board-string->board "---------")))
      (should= true  (xs-turn? (board-string->board "xoxo-----")))
      (should= true  (xs-turn? (board-string->board "xoxoo----")))
      (should= false (xs-turn? (board-string->board "xoxox----")))))

  (describe "#os-turn?"
    (it "should evaluate the given gamestate and return true if its o's turn and false if not"
      (should= false (os-turn? (board-string->board "---------")))
      (should= true  (os-turn? (board-string->board "xoxox----")))))

  (describe "#turn"
    (it "should return :o if there is an greater number of :x's than :o's"
      (should= :o (turn (board-string->board "xoxox----"))))
    (it "should return :o if there is an lesser or equal number of :x's than :o's"
      (should= :x (turn (board-string->board "xoxo-----")))))

  (describe "#players-turn?"
    (it "should return true if its the computers turn based on the gamestate"
      (should= true (players-turn? (board-string->board "xoxo-----") :x))
      (should= true (players-turn? (board-string->board "xoxox----") :o))))

  (describe "#current-player"
    (it "should return the first player in the player collection from the gamestate"
      (should= ttt_clojure.players.human.Human
               (class (current-player {:players [(ttt-human/new-human :x nil) (ttt-computer/new-computer :o)]})))))

  (describe "#computer-turn?"
    (it "should return true if the current player is a computer"
      (should= true (computers-turn? {:players [(ttt-computer/new-computer :o) (ttt-human/new-human :x nil)]})))
    (it "should return false if the current player is a human"
      (should= false (computers-turn? {:players [(ttt-human/new-human :x nil) (ttt-computer/new-computer :o)]}))))

  (describe "#board->board-string"
    (it "should convert a board to a board string"
      (should= "---------" (board->board-string [:- :- :- :- :- :- :- :- :-]))
      (should= "xxx------" (board->board-string [:x :x :x :- :- :- :- :- :-]))))

  (describe "#board-string->board"
    (it "should convert a string board to a board"
      (should= [:- :- :- :- :- :- :- :- :-] (board-string->board "---------"))
      (should= [:x :x :x :- :- :- :- :- :-] (board-string->board "xxx------"))))

  (describe "#build-gamestate"
    (context "computer-vs-human"
      (it "should build a gamestate based on the given cookies"
        (let [cookies {"board" "---------" "gametype" "computer-vs-human" "difficulty" "unbeatable"}]
          (should= [:- :- :- :- :- :- :- :- :-]          (-> (build-gamestate cookies) :board))
          (should= ttt_clojure.players.computer.Computer (-> (build-gamestate cookies) :players first  class))
          (should= ttt_clojure.players.human.Human       (-> (build-gamestate cookies) :players second class))
          (should= :x                                    (-> (build-gamestate cookies) :computer))
          (should= :unbeatable                           (-> (build-gamestate cookies) :options :difficulty)))))
    (context "human-vs-computer"
      (it "should build a gamestate based on the given cookies"
        (let [cookies {"board" "---------" "gametype" "human-vs-computer" "difficulty" "unbeatable"}]
          (should= [:- :- :- :- :- :- :- :- :-]          (-> (build-gamestate cookies) :board))
          (should= ttt_clojure.players.human.Human       (-> (build-gamestate cookies) :players first  class))
          (should= ttt_clojure.players.computer.Computer (-> (build-gamestate cookies) :players second class))
          (should= :o                                    (-> (build-gamestate cookies) :computer))
          (should= :unbeatable                           (-> (build-gamestate cookies) :options :difficulty)))))
    (context "computer-vs-computer"
      (it "should build a gamestate based on the given cookies"
        (let [cookies {"board" "---------" "gametype" "computer-vs-computer" "difficulty" "unbeatable"}]
          (should= [:- :- :- :- :- :- :- :- :-]          (-> (build-gamestate cookies) :board))
          (should= ttt_clojure.players.computer.Computer (-> (build-gamestate cookies) :players first  class))
          (should= ttt_clojure.players.computer.Computer (-> (build-gamestate cookies) :players second class))
          (should= :unbeatable                           (-> (build-gamestate cookies) :options :difficulty)))))
    (context "human-vs-human"
      (it "should build a gamestate based on the given cookies"
        (let [cookies {"board" "---------" "gametype" "human-vs-human" "difficulty" "unbeatable"}]
          (should= [:- :- :- :- :- :- :- :- :-]          (-> (build-gamestate cookies) :board))
          (should= ttt_clojure.players.human.Human       (-> (build-gamestate cookies) :players first  class))
          (should= ttt_clojure.players.human.Human       (-> (build-gamestate cookies) :players second class))
          (should= :easy                                 (-> (build-gamestate cookies) :options :difficulty)))))))
