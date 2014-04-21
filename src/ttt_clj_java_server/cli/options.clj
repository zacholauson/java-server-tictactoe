(ns ttt-clj-java-server.cli.options
  (:require [clojure.tools.cli :refer [parse-opts]]))

(def cli-options
  [["-p" "--port <PORT>"
    "Port Number"
    :default 7000
    :parse-fn #(Integer/parseInt %)]])

(def opts (atom {}))

(defn port []
  (:port @opts))

(defn wrap-cli [args]
  (swap! opts conj (:options (parse-opts args cli-options))))
