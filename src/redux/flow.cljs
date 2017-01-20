(ns redux.flow
  (:require [cljs.core.async :as a])
  (:require-macros [cljs.core.async.macros :refer [go go-loop]]))

(defonce state (atom {:name "World"}))
(defonce actions (a/chan))

;; Components call this function to request state changing.
(defn dispatch
  "Dispatch new action. Type should be keyword."
  ([type] (dispatch type nil))
  ([type data]
   (a/put! actions [type data])))

;; All state changes should be done via this method.
(defmulti transform
  "Transform state by action. Return updated state."
  (fn [state data dispatch action-type] action-type))

;; Start actions pipeline
(go-loop []
         (when-let [a (a/<! actions)]
           (let [[type data] a]
             (println "Handle action" type)
             (swap! state transform data dispatch type))
          (recur)))

(defmethod transform :change-name
  [state value]
  ;; State here is map, not atom!
  (assoc state :name value))
