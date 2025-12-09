(ns aoc.day6
  (:require
   [aoc.core :refer [debug-println]]
   [clojure.string :as str]))

(defn op->str [op]
  (cond
    (str/includes? (str op) "_STAR_") " * "
    (str/includes? (str op) "_PLUS_") " + "))

(defn do-problem [{numbers :numbers op :op}]
  (let [answer (apply op numbers)]
    (debug-println "The problem is" (str/join (op->str op) numbers) "=" answer)
    answer))

(def split-whitespace (fn [s] (str/split (str/trim s) #"\s+")))

(defn str->op [s]
  (condp = s
    "*" *
    "+" +))

(defn parse-problem [input]
  (let [numbers-strs (butlast input)
        op-str (last input)]
    {:numbers (map bigint numbers-strs)
     :op (str->op op-str)}))

(defn math-homework [input]
  (->> input
       str/split-lines
       (map split-whitespace)
       (apply map list)
       (map parse-problem)
       (map do-problem)
       (apply +)))

;; Part 2

(defn split-chars [input]
  (str/split input #""))

(defn list->int [input]
  (bigint (str/trim (str/join input))))

(def empty-problem {:numbers '() :op nil})

(defn parse-columns [{acc :acc {numbers :numbers op :op} :problem} column]
  (debug-println "parsed" acc "current" numbers op "column" column)
  (let [start-problem (fn [acc numbers column op] {:acc acc :problem {:numbers (conj numbers (list->int (butlast column))) :op (str->op op)}})
        maybe-op (last column)]
    (cond
      (or (= maybe-op "*")
          (= maybe-op "+")) (start-problem acc numbers column maybe-op)
      (every? str/blank? column) {:acc (conj acc {:numbers numbers :op op}) :problem empty-problem}
      :else {:acc acc :problem {:numbers (conj numbers (list->int column)) :op op}})))

(defn parse-cleanup [{acc :acc problem :problem}]
  (conj acc problem))

(defn correct-math-homework [input]
  (->> input
       str/split-lines
       (map split-chars)
       (apply map list)
       (reduce parse-columns {:acc '() :problem empty-problem})
       parse-cleanup
       (map do-problem)
       (apply +)))
