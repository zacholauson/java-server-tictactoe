(ns ttt-clj-java-server.api.router-spec
  (:require [speclj.core                    :refer :all]
            [ttt-clj-java-server.api.router :refer :all]))

(describe "Router"
  (describe "#add-route"
    (it "should add a IRoute to the routes map"
      (let [router (new-router)]
        (add-route router "GET" "/" (new main.routing.routes.TextRoute "Text Route"))
        (should= 1 (count (routes router))))))

  (describe "#add-routes"
    (it "should take a map of routes and add each to the router"
      (let [router (new-router)]
        (add-routes router {{"GET"     "/1"} (new main.routing.routes.TextRoute "Text Route 1")
                            {"POST"    "/2"} (new main.routing.routes.TextRoute "Text Route 2")
                            {"OPTIONS" "/3"} (new main.routing.routes.TextRoute "Text Route 3")})
        (should= 3    (count (routes router)))
        (should= true (instance? main.routing.routes.IRoute (get (routes router) "GET /1")))
        (should= true (instance? main.routing.routes.IRoute (get (routes router) "POST /2")))
        (should= true (instance? main.routing.routes.IRoute (get (routes router) "OPTIONS /3"))))))

  (describe "#routes"
    (it "should return a map of all the routes for the given router"
      (let [router (new-router)]
        (add-route router "GET" "/" (new main.routing.routes.TextRoute "Text Route"))
        (should= "GET /" (first (keys (routes router))))
        (should= main.routing.routes.TextRoute (class (get (routes router) "GET /")))))))
