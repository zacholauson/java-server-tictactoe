(ns ttt-clj-java-server.api.request.request-spec
  (:require [speclj.core :refer :all]
            [ttt-clj-java-server.api.request.request :refer :all]))

(def mock-request
  (reify main.requests.IRequest
    (getParams [this]
      (java.util.HashMap. {"move" "1"}))
    (getCookies [this]
      (java.util.HashMap. {"board" "---------"}))))

(describe "Request"
  (describe "#get-cookies"
    (it "should return a map of the cookies from the request"
      (should= {"board" "---------"} (get-cookies mock-request))))
  (describe "#get-params"
    (it "should return a map of the params from the request"
      (should= {"move" "1"} (get-params mock-request)))))
