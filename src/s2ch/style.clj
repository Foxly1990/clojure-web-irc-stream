(ns s2ch.style
  (:require [garden.stylesheet :refer [at-media]]))

(def main
  [:html
   [:body {:margin "0 25%"
           :font-family "\"Anonymous Pro\", monospace"
           :line-height "1.34"
           :color "#333"
           :font-size "16px"
           :background-color "#fefefe"}]
   [:header {:border-bottom "1px dashed"}]
   [:ul {:list-style "none" :padding "0"}]
   [:canvas {:margin "auto" :display "block"}]
   [:nav [:ul [:li {:display "inline-block"
                    :margin-right "15px"}]]]
   [:nav.list-pages [:ul [:li {:display "block"}]]]
   [:a {:color "#1d85b3"
        :outline "none"
        :text-decoration "none"}
    [:&:hover {:color "#111"}]
    [:&:visited {:color "#19688c"}]]
   [:h1 :h2 {:font-weight "400"
             :font-size "1.3em"}]
   [:h1 {:text-align "center"}]
   [:kbd {:border "thin solid #e0e0e0"
          :border-radius "3px"
          :padding "2px 5px"
          :background "#f8f9fa"
          :box-shadow "1px 1px 0px 0px rgba(0,0,0,.1)"
          :margin "2px 0px"
          :display "inline-block"}]
   [:footer {:border-top "1px dashed"
             :padding "1em 0"}
    [:p {:margin "0"
         :text-align "center"
         :font-size "0.8em"}]]
   [:#stream {:height "500px"
              :overflow "auto"}
    [:div.event {:border-bottom "1px dotted #ccc"}
     [:span {:display "inline-block"
             :padding "0px 5px"}]
     [:span.time {:background "#00f3"
                  :margin-right "15px"}]
     [:span.nickname {:background "#ffa"
                      :font-weight "bold"
                      :margin-right "15px"}]]]])

;; @media only screen and (max-width: 768px) {
;; (prn main)
