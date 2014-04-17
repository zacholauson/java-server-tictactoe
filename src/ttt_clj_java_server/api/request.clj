(ns ttt-clj-java-server.api.request)

(defn get-cookies [request]
  (.getCookies request))

(defn get-params [request]
  (.getParams request))
