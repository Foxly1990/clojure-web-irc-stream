(ns s2ch.db
  (:require [clojure.java.jdbc :as j]))

(def db {:dbtype   "postgresql"
         :dbname   ""
         :host     ""
         :user     ""
         :password ""})

(def query-menu (j/query db ["SELECT * FROM menu"]))

(defn query-parent-pages [url]
  (if (string? url)
    (j/query db ["SELECT * FROM pages WHERE is_parent = ? AND url = ?" true url])
    false))

(defn query-children-page [parent url]
  (j/query db ["SELECT * FROM pages WHERE is_parent = ? AND parent = ? AND url = ?" false parent url]))

(defn query-children-pages-list [parent]
  (j/query db ["SELECT id, caption, href FROM pages WHERE is_parent = false AND parent = ?" parent]))
