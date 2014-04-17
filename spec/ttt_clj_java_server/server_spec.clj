(ns ttt-clj-java-server.server-spec
  (:require [speclj.core                            :refer :all]
            [ttt-clj-java-server.server             :refer :all]
            [ttt-clj-java-server.api.routing.router :refer [add-route routes] :as router]))

(def router (main.Server/ROUTER))

(describe "#init-routes"
  (it "should be able to add routes to the router"
    (add-route router "GET" "/" (new main.routing.routes.TextRoute "Hello World"))
    (should= main.routing.routes.TextRoute (class (get (routes router) "GET /")))))
