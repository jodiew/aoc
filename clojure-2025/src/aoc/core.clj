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

(defn str->int [str]
  (Integer/parseInt str))

(defn split-to-grid [data]
  (let [split-line #(str/split % #"")]
    (->> data
         str/split-lines
         (map split-line))))

(defn generate-grid [rows cols]
  (for [rows (range rows)
        columns (range cols)]
    [rows columns]))

(defn iinc [x] (inc (inc x)))

(defn generate-adj-grid [rows-count cols-count [row col]]
  (for [rows (range (dec row) (iinc row))
        cols (range (dec col) (iinc col))
        :let [adj [rows cols]]
        :when (and (not= adj [row col])
                   (>= rows-count (first adj) 0)
                   (>= cols-count (second adj) 0))]
    adj))

(def ^:dynamic **debug?** false)
