(ns s2ch.db
  (:require [clojure.java.jdbc :as j]))

(def db {:dbtype   "postgresql"
         :dbname   "s2ch_db"
         :host     "localhost"
         :user     "s2ch_db"
         :password "HYZolQG2HRKt471EiHygY2E1U8vi8e7Au_CynmOYdDypI4CVw36vGjp5SfqAk2yl"})

(def query-menu (j/query db ["SELECT * FROM menu"]))

(defn query-parent-pages [url]
  (if (string? url)
    (j/query db ["SELECT * FROM pages WHERE is_parent = ? AND url = ?" true url])
    false))

(defn query-children-page [parent url]
  (j/query db ["SELECT * FROM pages WHERE is_parent = ? AND parent = ? AND url = ?" false parent url]))

(defn query-children-pages-list [parent]
  (j/query db ["SELECT id, caption, href FROM pages WHERE is_parent = false AND parent = ?" parent]))

;; (def query-parent-pages (j/query db ["SELECT * FROM pages WHERE is_parent = ? AND url = ?" true "/"]))

;; (prn query-menu)

;; (j/insert! db :menu {:title "Список игр на javascript"
;;                      :href "/games"
;;                      :name "/games"
;;                      :parent 0})

;; (prn (j/query db ["SELECT * FROM pages WHERE is_parent = true"]))

;; (j/insert! db :pages {:title "#s2ch games список игр на javacript"
;;                           :caption "Игры на javascript"
;;                           :content ""
;;                           :url "/games"
;;                           :href nil
;;                           :parent ""
;;                           :is_parent true})



;; (prn (query-children-page "emacs" "45867"))

;; (j/query db ["SELECT * FROM pages WHERE is_parent = ? AND parent = ? AND url = ?" false "emacs" "45876"])

;; (j/query db ["SELECT * FROM pages WHERE is_parent = ? AND url = ?" true "/"])

;; (pr query-parent-pages)

;; (query-parent-pages "/")

;; (if (string? "dwa") (prn "Ok") "Not OK")

;; (string? 1)

;; (j/query db ["SELECT * FROM menu"] {:result-set-fn count})
;; (j/query db ["SELECT * FROM menu"] {:row-fn :name})


;; (prn query-parent-pages)

;; (prn query-page)

;; (def menu-sql (j/create-table-ddl :menu [[:id :serial "PRIMARY KEY"]
;;                                          [:title "VARCHAR(64)"]
;;                                          [:href "VARCHAR(128)"]
;;                                          [:name "VARCHAR(64)"]
;;                                          [:parent :int]]))

;; (def page-sql (j/create-table-ddl :pages [[:id :serial "PRIMARY KEY"]
;;                                           [:title "VARCHAR(64)"]
;;                                           [:caption "VARCHAR(64)"]
;;                                           [:content "TEXT"]
;;                                           [:url "VARCHAR(64)"]
;;                                           [:parent "VARCHAR(64)"]
;;                                           [:is_parent "boolean"]]))

;; (defn insert-into-pages []
;;   (let [id (str (rand-int 99999))]
;;     (j/insert! db :pages {:title "atari breakout на javascript"
;;                           :caption "Breakout"
;;                           :content "<canvas id='breakout' width='480' height='320'></canvas><script src='/js/breakout.js'></script>"
;;                           :url id
;;                           :href (apply str ["/games/" id])
;;                           :parent "games"
;;                           :is_parent false})))

;; (j/insert! db :pages {:title ""
;;                       :caption "Создать IPV6-туннель"
;;                       :content ""
;;                       :url ""
;;                       :href "https://tunnelbroker.net/"
;;                       :parent "links"
;;                       :is_parent false})

;;(insert-into-pages)

;; (j/insert! db :pages {:title "стрим с канала #s2ch"
;;                       :caption "Стрим c канала #s2ch"
;;                       :content "<div id='stream'></div>"
;;                       :url "/stream"
;;                       :parent ""
;;                       :is_parent true})

;; (j/update! db :pages {:caption "Практический Common Lisp (перевод)"} ["id = ?" 9])
;; (j/query db ["SELECT * FROM pages WHERE is_parent = ?" false])
;; (j/delete! db :pages ["id = ?" 12])
;; (j/query db ["ALTER TABLE pages ADD COLUMN href varchar(64)"])
;; (j/insert! db :pages {:title ""})
;; (prn page-sql)
;; (rand-int 99999)
;; (j/execute! db [menu-sql])
;; (j/insert! db :menu {:title "База знаний, готовые рецепты для GNU/Emacs" :href "/gnu_emacs" :name "GNU/Emacs" :parent 0})
;; (j/insert! db :menu {:title "Интересные ссылки" :href "/links" :name "/links" :parent 0})
;; (prn query-menu)
;; (prn menu-sql)
