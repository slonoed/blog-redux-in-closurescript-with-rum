(ns redux.core
  (:require [redux.components :as components]
            [redux.flow :as flow]
            [rum.core :as rum]))

(enable-console-print!)

;; Attach react tree to DOM
(rum/mount (components/+app flow/state)
           (js/document.getElementById "app"))

;; Dev helper. It touches state when code reloaded to start rerender.
(defn on-js-reload []
  (swap! flow/state update-in [:__figwheel_counter] inc))
