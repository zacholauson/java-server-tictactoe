(ns ttt-clj-java-server.api.response-spec
  (:require [speclj.core :refer :all]
            [ttt-clj-java-server.api.response :refer :all]))

(describe "Response"
  (describe "#new-response"
    (it "should create a new instance of response"
      (should= main.response.responses.Response (class (new-response)))))

  (context "building response"
    (describe "#set-status"
      (it "should set the status code of the response to 200"
        (let [response (new-response)]
          (set-status response 200)
          (should= "HTTP/1.1 200 OK\r\n\r\n" (get-headers response)))))

    (describe "#add-cookie"
      (it "should add a cookie to the headers of the response"
        (let [response (new-response)]
          (add-cookie response "Test" "Cookie")
          (should= "Set-Cookie: Test=Cookie\r\n\r\n" (get-headers response)))))

    (describe "#add-cookies"
      (it "should add multiple cookies at once"
        (let [response (new-response)]
          (add-cookies response {"Test1" "Cookie1" "Test2" "Cookie2" "Test3" "Cookie3"})
          (should= (str "Set-Cookie: Test2=Cookie2\r\n"
                        "Set-Cookie: Test3=Cookie3\r\n"
                        "Set-Cookie: Test1=Cookie1\r\n\r\n") (get-headers response)))))

    (describe "#add-header"
      (it "should add a header to the response"
        (let [response (new-response)]
          (add-header response "Location" "/")
          (should= "Location: /\r\n\r\n" (get-headers response)))))

    (describe "#get-headers"
      (it "should return the headers of the response"
        (let [response (new-response)]
          (set-status response 404)
          (should= "HTTP/1.1 404 Not Found\r\n\r\n" (get-headers response)))))

    (describe "#set-body"
      (it "should set the body of the response to byte array"
        (let [response (new-response)]
          (set-body response "Test Body")
          (should= "class [B" (str (class (get-body response)))))))

    (describe "#get-body"
      (it "should return the byte array body of the response"
        (let [response (new-response)]
          (set-body response "Test Body")
          (should= [84 101 115 116 32 66 111 100 121]
                   (vec (get-body response))))))

    (describe "#get-body-content"
      (it "should return the actual contents of the body byte array"
        (let [response (new-response)]
          (set-body response "Test Body")
          (should= "Test Body" (get-body-content response)))))

    (describe "#set-redirect"
      (it "should take a response and set attributes needed for redirect"
        (let [response (new-response)]
          (set-redirect response "/play")
          (should= (str "HTTP/1.1 301 Moved Permanently\r\n"
                        "Location: /play\r\n\r\n") (get-headers response)))))))
