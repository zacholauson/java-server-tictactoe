(ns ttt-clj-java-server.api.request.request)

(defn get-cookies [request]
  (.getCookies request))

(defn get-params [request]
  (.getParams request))
