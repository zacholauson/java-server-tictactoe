(ns ttt-clj-java-server.cli.options-spec
  (:require [speclj.core                     :refer :all]
            [ttt-clj-java-server.cli.options :refer :all]))

(describe "Options"
  (describe "#wrap-cli"
    (it "should create a options ref"
      (wrap-cli nil)
      (should= true (not (empty? @opts))))
    (it "should set the options from the args to @opts"
      (wrap-cli nil)
      (should= {:port 7000} @opts))
    (it "should store the passed in port in @args"
      (wrap-cli '("-p" "8000"))
      (should= {:port 8000} @opts)))

  (describe "#port"
    (it "should return 7000 if port is not specified in options"
      (wrap-cli nil)
      (should= 7000 (port)))
    (it "should return the port number specified in options"
      (wrap-cli '("-p" "8000"))
      (should= 8000 (port)))))
