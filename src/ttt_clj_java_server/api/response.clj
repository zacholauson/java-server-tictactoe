(ns ttt-clj-java-server.api.response)

(defn set-status [response status-code]
  (.setStatus response status-code))

(defn add-cookie [response cookie-key cookie-value]
  (.addCookie response cookie-key cookie-value))

(defn add-cookies [response cookie-map]
  (doseq [[cookie-key cookie-val] cookie-map] (add-cookie response cookie-key cookie-val)))

(defn add-header [response header-key header-value]
  (.addHeader response header-key header-value))

(defn get-headers [response]
  (.getHeaders response))

(defn set-body [response body-string]
  (->> body-string
       (map byte)
       (byte-array)
       (.setBody response)))

(defn get-body [response]
  (.getBody response))

(defn get-body-content [response]
  (String. (.getBody response)))

(defn set-redirect [response location]
  (set-status response 301)
  (add-header response "Location" location))

(defn new-response []
  (new main.response.responses.Response))
