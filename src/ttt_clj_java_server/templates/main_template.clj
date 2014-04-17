(ns ttt-clj-java-server.templates.main-template
  (:require [ttt-clj-java-server.helpers.board-view-helpers :refer [buttonify partitioned-board] :as view-helpers]
            [hiccup.page :refer :all]
            [hiccup.form :refer :all]))

(defn new-game-form []
  [:div
    (form-to [:post "/new-game"]
             [:table (:style "border: 0")
               [:tr [:th [:h3 "Tic Tac Toe"]]]
               [:tr [:td [:hr]]]
               [:tr [:th "Gametype"]]
               (for [gametype [:human-vs-human :computer-vs-computer :human-vs-computer :computer-vs-human]]
                 [:tr
                   [:td (label gametype gametype)]
                   [:td (radio-button :gametype true gametype)]])
               [:tr [:td [:hr]]]
               [:tr [:th "Board Size"]]
               (for [board-size [5 4 3]]
                 [:tr
                   [:td (label (str board-size) (str board-size "x" board-size))]
                   [:td (radio-button :board-size true board-size)]])
               [:tr [:td [:hr]]]
               [:tr [:th "Difficulty"]]
               [:tr [:th]]
               (for [difficulty [:easy :medium :unbeatable]]
                 [:tr
                   [:td (label difficulty difficulty)]
                   [:td (radio-button :difficulty true difficulty)]])
               [:tr [:td [:hr]]]
               [:tr [:td (submit-button "New Game")]]])])

(defn board-layout [board game-over?]
  [:table {:class "board" :border "1" :cellpadding 50}
    (for [row (partitioned-board board)]
      [:tr (for [space row]
        [:td (buttonify space game-over?)])])])

(defn new-game-page-layout []
  (html5
    (new-game-form)))

(defn page-layout [board game-over?]
  (html5
    [:table {:class "page-layout" :border "0" :cellpadding 10}
      [:tr
        [:td (new-game-form)]
        [:td (board-layout board game-over?)]]]))
