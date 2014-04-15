(ns ttt-clj-java-server.board-view-helpers
  (:require [clojure.math.numeric-tower :refer [sqrt] :as math]
            [hiccup.page :refer :all]
            [hiccup.form :refer :all]))

(defn row-size [board]
  (math/sqrt (count board)))

(defn index-board [board]
  (map-indexed #(if (= :- %2) (format "%2s" %1) (format "%2s" (name %2))) board))

(defn partitioned-board [board]
  (partition (row-size board) (index-board board)))

(defn space-taken? [string]
  (if (re-find #"x|o" string) true false))

(defn buttonify [string]
  (if (space-taken? string) string
    [:div {:class "move-form"}
      (form-to [:post "/move"]
        (text-field    {:type :hidden} :move string)
        (submit-button {:class "move"}       string))]))

(defn build-board-layout [board]
  (html5
    [:table {:class "board" :border "1" :cellpadding 50}
      (for [row (partitioned-board board)]
        [:tr (for [space row]
          [:td (buttonify space)])])]))
