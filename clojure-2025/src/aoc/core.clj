(ns aoc.core
  (:require [clojure.string :as str]))

(defn within?
  "Returns true if min <= val <= max"
  ([min max] (<= min max))
  ([min max val] (<= min val max)))

(defn greeting [me & names]
  (println "Hello, "  ))

(def a-vector [1 2 3])
(def a-list '(1 2 3))
(def a-set #{1 2 3})
(def a-map {:one 1 :two 2 :three 3})
(defrecord a-record [title author])
(false? false) (false? nil) ; only false values, everything else is true

