(ns s2ch.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [environ.core :refer [env]]
            [org.httpkit.server :as ohs]
            [s2ch.db :as db]
            [s2ch.hal9000 :as bot]
            [s2ch.html :as html]
            [s2ch.stream :as stream]))

(defn page [uri]
  (db/query-parent-pages uri))

(defn index [uri & cnt]
  {:status 200
   :headers {"Content-Type" "text/html; charset=utf-8"}
   :body (html/common-layout (page uri) cnt)})

(defn page-section [parent id]
  (db/query-children-page parent id))

(defn section-page [parent id & cnt]
  {:status 200
   :headers {"Content-Type" "text/html; charset=utf-8"}
   :body (html/common-layout (page-section parent id) cnt)})

(defn stream [{params :params :as req}]
  (ohs/with-channel req ch
    (stream/add-usr ch params)
    (ohs/on-close ch (fn [st] (stream/rm-usr ch)))))

(defroutes app-routes
  (GET "/" [] (index "/"))
  (GET "/stream" [] (index "/stream"))
  (GET "/stream/:name" [] #'stream)
  (GET "/gnu-emacs" [] (index "/emacs" (html/list-pages "emacs")))
  (GET "/gnu-emacs/:id" [id] (section-page "emacs" id))
  (GET "/posts" [] (index "/posts" (html/list-pages "posts")))
  (GET "/posts/:id" [id] (section-page "posts" id))
  (GET "/links" [] (index "/links" (html/list-pages "links")))
  (GET "/games" [] (index "/games" (html/list-pages "games")))
  (GET "/games/:id" [id] (section-page "games" id))
  (route/resources "/")
  (route/not-found "404 Page not Found! <a href='/'>Домой</a>"))

(defonce server (atom nil))

(defn stop-server []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 2405))]
    (ohs/run-server #'app-routes {:port port :join? false})))

(defn start-server []
  (reset! server (-main)))

(start-server)

(comment
  (require '[vinyasa.pull :as vp])
  (vp/pull 'clojure.java-time)
  (stop-server)
  (start-server))
