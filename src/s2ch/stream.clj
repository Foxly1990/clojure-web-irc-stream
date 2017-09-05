(ns s2ch.stream
  (:require [hiccup.core :as hc]
            [hiccup.util :as hcu]
            [org.httpkit.server :as ohs]
            [java-time :as jtime]))

(def users (atom {}))

(defn current-time []
  (jtime/format "HH.mm.ss" (jtime/local-time)))

(defn add-usr [ch usr]
  (swap! users assoc ch usr))

(defn rm-usr [ch]
  (swap! users dissoc ch))

(defn b-cast [msg]
  (doseq [[ch u] @users]
    (ohs/send! ch msg)))

(defn fmt-msg [privmsg]
  (def text (hcu/escape-html (str (:text privmsg))))
  (hc/html [:div.event
            [:p.event-wrapper
             [:span.time (str "[" (current-time) "]")]
             [:span.nickname (:nick privmsg)]
             [:span.message text]]]))

(defn on-msg [privmsg]
  (b-cast (fmt-msg privmsg)))

(prn @users)
