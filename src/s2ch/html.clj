(ns s2ch.html
  (:require
   [garden.core :as gc]
   [s2ch.style :as css]
   [hiccup.core :as hc]
   [java-time :as jtime]
   [s2ch.db :as db]))

(defn current-time [] (jtime/format "dd.MM.yyyy" (jtime/local-date)))

(defn style [g]
  [:style (gc/css g)])

(defn script [path]
  [:script {:src path}])

(def menu [:nav [:ul (for [item db/query-menu]
                       [:li [:a {:href (:href item)} (:name item)]])]])
(defn list-pages [parent]
  (let [seq (db/query-children-pages-list parent)]
    [:nav.list-pages [:ul (for [item seq]
                            [:li [:a {:href (:href item)} (:caption item)]])]]))

(defn get-value [seq key]
  "Can get value from LazySeq."
  (map (comp key) seq))

(defn common-layout [seq & content]
  (hc/html
   [:html
    [:head
     [:meta {:charset "utf-8"}]
     [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
     [:meta {:name "Description" :content "Welcome to the official page of channel #s2ch in freenode."}]
     [:meta {:name "author" :content "carmack from #s2ch"}]
     [:title (get-value seq :title)]
     [:link {:href "https://fonts.googleapis.com/css?family=Anonymous+Pro" :rel "stylesheet"}]
     (style css/main)]
    [:body
     [:header menu]
     [:main
      [:article [:h1 (get-value seq :caption)] (if (= '("") (get-value seq :content)) content (get-value seq :content))]]
     [:footer
      [:p "Канал #s2ch существует с 10.04.2015 по " (current-time)]
      [:p "По всем вопросам обращаться на #s2ch freenode"]]
     (script "/js/websocket.js")]]))
