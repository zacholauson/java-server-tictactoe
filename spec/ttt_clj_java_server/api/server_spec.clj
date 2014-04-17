(ns ttt-clj-java-server.api.server-spec
  (:require [speclj.core                    :refer :all]
            [ttt-clj-java-server.api.server :refer :all]))

(describe "Server"
  (describe "#router"
    (it "should return the servers router"
      (should= main.routing.routers.Router (class router)))))
