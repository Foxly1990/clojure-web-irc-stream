(defproject s2ch "0.1.0-SNAPSHOT"
  :description "Personal website for #s2ch channel"
  :url "http://s2ch.ml/"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
                 [http-kit "2.2.0"]
                 [environ "0.5.0"]
                 [clojure.java-time "0.3.0"]
                 [org.clojure/java.jdbc "0.7.0"]
                 [org.postgresql/postgresql "42.1.3"]
                 [irclj "0.5.0-alpha4"]
                 [hiccup "2.0.0-alpha1"]
                 [garden "1.2.3"]]


  :main s2ch.handler
  :plugins [[environ/environ.lein "0.2.1"]
            [cider/cider-nrepl "0.15.1-SNAPSHOT"]]
  :hooks [environ.leiningen.hooks]
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [im.chit/vinyasa "0.2.2"]]}})
