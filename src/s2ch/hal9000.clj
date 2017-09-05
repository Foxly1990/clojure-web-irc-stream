(ns s2ch.hal9000
  (:require [irclj.core  :as irc]
            [s2ch.stream :as stream]))

(def connection
  (irc/connect "irc.freenode.net" 6667 "HAL9000"
               :callbacks {:privmsg (fn [irc type & s] (stream/on-msg type))}))

(irc/join connection "#s2ch")

(comment
  (irc/kill connection))
