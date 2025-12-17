(ns aoc.day8
  (:require
   [clojure.string :as str]
   [clojure.math.numeric-tower :as math]
   [clojure.set :as set]))

(defrecord Box [x y z])

(defn str->Box [input]
  (->> input
       (#(str/split % #","))
       (map Integer/parseInt)
       (apply ->Box)))

(defn- sqr [x]
  (math/expt x 2))

(defn- euclidean-squared-distance [p q]
  (reduce + (map (comp sqr -) p q)))

(defn euclidean-distance [p q]
  (math/sqrt (euclidean-squared-distance (vals p) (vals q))))


(defn find-all-distances [boxes acc box]
  (let [find-distances (fn [acc other] (if (= other box) acc (assoc acc #{box other} (euclidean-distance box other))))]
    (reduce find-distances acc boxes)))

(defn build-adj [boxes]
  {:circuits (map (comp set list) boxes)
   :adj (sort-by val (reduce (partial find-all-distances boxes) {} boxes))})

(defn boxes-connected? [circuits box-1 box-2]
  (some #(and (some (partial = box-1) %)
              (some (partial = box-2) %)) circuits))

(defn find-circuits-to-merge [box-1 box-2 {circuits-to-merge :to-merge other-circuits :other} circuit]
  (cond
    (or (contains? circuit box-1)
        (contains? circuit box-2)) {:to-merge (conj circuits-to-merge circuit) :other other-circuits}
    :else {:to-merge circuits-to-merge :other (conj other-circuits circuit)}))

(defn merge-circuits [circuits box-1 box-2]
  (let [{circuits-to-merge :to-merge other-circuits :other} (reduce (partial find-circuits-to-merge box-1 box-2) {:to-merge '() :other '()} circuits)]
    (conj other-circuits (apply set/union circuits-to-merge))))

(defn make-n-shortest-connections [n {circuits :circuits adj :adj}]
  (loop [n n
         circuits circuits
         [[boxes _] & xs] adj]
    (cond
      (= 0 n) circuits
      (boxes-connected? circuits (first boxes) (second boxes)) (recur (dec n) circuits xs)
      :else (recur (dec n) (merge-circuits circuits (first boxes) (second boxes)) xs))))


(defn solution [num-connect num-multiply input]
  (->> input
       str/split-lines
       (map str->Box)
       build-adj
       (make-n-shortest-connections num-connect) 
       (sort-by count >)
       (take num-multiply)
       (map count)
       (apply *)))

;; Part 2

(defn make-connection [{circuits :circuits last :last} [boxes _]]
  (cond
    (= 1 (count circuits)) (reduced last)
    (boxes-connected? circuits (first boxes) (second boxes)) {:circuits circuits :last nil}
    :else {:circuits (merge-circuits circuits (first boxes) (second boxes)) :last [(first boxes) (second boxes)]}))

(defn reduce-connections [{circuits :circuits adj :adj}]
  (reduce make-connection {:circuits circuits :last nil} adj))

(defn solution-2 [input]
  (->> input
       str/split-lines
       (map str->Box)
       build-adj
       reduce-connections
       (map :x)
       (apply *)))
