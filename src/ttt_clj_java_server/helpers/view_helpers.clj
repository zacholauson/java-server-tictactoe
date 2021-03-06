(ns ttt-clj-java-server.helpers.view-helpers
  (:require [clojure.math.numeric-tower :refer [sqrt] :as math]
            [hiccup.core                :refer :all]
            [hiccup.page                :refer :all]
            [hiccup.form                :refer :all]))

(def ^{:dynamic true} *view-context* {})

(defn row-size [board]
  (math/sqrt (count board)))

(defn index-board [board]
  (map-indexed #(if (= :- %2) (format "%2s" %1) (format "%2s" (name %2))) board))

(defn partitioned-board [board]
  (partition (row-size board) (index-board board)))

(defn space-taken? [string]
  (if (re-find #"x|o" string) true false))

(defn buttonify [string game-over?]
  (if (space-taken? string) string
    (if game-over? string
      [:div {:class "move-form"}
        (form-to [:post "/move"]
          (text-field    {:type :hidden} :move string)
          (submit-button {:class "move"}       string))])))
