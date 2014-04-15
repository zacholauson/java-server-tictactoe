(ns ttt-clj-java-server.api.routing.router-spec
  (:require [speclj.core :refer :all]
            [ttt-clj-java-server.api.routing.router :refer :all]))

(describe "Router"
  (describe "#add-route"
    (it "should add a IRoute to the routes map"
      (let [router (new-router)]
        (add-route router "GET" "/" (new main.routing.routes.TextRoute "Text Route"))
        (should= 1 (count (routes router))))))
  (describe "#routes"
    (it "should return a map of all the routes for the given router"
      (let [router (new-router)]
        (add-route router "GET" "/" (new main.routing.routes.TextRoute "Text Route"))
        (should= "GET /" (first (keys (routes router))))
        (should= main.routing.routes.TextRoute (class (get (routes router) "GET /")))))))
