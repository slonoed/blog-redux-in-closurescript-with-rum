(ns redux.components
  (:require [rum.core :as r]
            [clojure.string :as str]
            [redux.flow :refer [dispatch]]))

(r/defc +form
  [state]
  [:.app
   [:h1 (str "Hello, " (:name state))]
   [:input {:value (:name state)
            :on-change #(dispatch :change-name (.. % -target -value))}]])

(r/defc +app < r/reactive
  [state-atom]
  (+form (r/react state-atom)))
