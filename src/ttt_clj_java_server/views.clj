(ns ttt-clj-java-server.views
  (:require [hiccup.page :refer :all]))

(def views-directory "src/ttt_clj_java_server/views/")
(def helper-namespace 'ttt-clj-java-server.helpers.view-helpers)
(def view-extension ".hiccup")

(defn load-view [view-name]
  (slurp (str views-directory view-name view-extension)))

(defn read-view [view-name]
  (read-string (load-view view-name)))

(defn eval-content
  ([hiccup-src]
    (require helper-namespace)
    (binding [*ns* (the-ns helper-namespace)]
      (eval hiccup-src)))
  ([hiccup-src view-context]
    (binding [ttt-clj-java-server.helpers.view-helpers/*view-context* view-context]
      (eval-content hiccup-src))))

(defn render
  ([template-name]
     (html5 (eval-content (read-view template-name))))
  ([template-name view-context]
     (html5 (eval-content (read-view template-name) view-context))))
