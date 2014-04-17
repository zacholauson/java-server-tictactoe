(defproject ttt-clj-java-server "0.1.0-SNAPSHOT"
  :description "java server serving clojure tic tac toe"
  :url "https://github.com/zacholauson/java-server-tictactoe"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/math.numeric-tower "0.0.4"]
                 [org.clojure/tools.cli "0.3.1"]
                 [ttt-clojure "0.1.2-SNAPSHOT"]
                 [hiccup "1.0.5"]]
  :profiles {:dev {:dependencies [[speclj "3.0.1"]]}}
  :plugins [[speclj "3.0.1"]]
  :resource-paths ["lib/Server.jar"]
  :test-paths ["spec"]
  :main ttt-clj-java-server.core)
