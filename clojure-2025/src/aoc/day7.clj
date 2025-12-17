(ns aoc.day7
  (:require [aoc.core :refer [**debug?** debug-println]]
            [clojure.set :as set]
            [clojure.string :as str]
            [clojure.math.numeric-tower :as math]))

(defn in-manifold?
  ([cols beam]
   (< -1 beam cols)))

(defn next-beam [splitters {splits :splits beams :beams} beam]
  (cond
    (contains? splitters beam) {:splits (inc splits) :beams (conj beams (dec beam) (inc beam))}
    :else {:splits splits :beams (conj beams beam)}))

(defn next-beams [cols {total-splits :splits prev-beams :beams} row-splitters]
  (let [{splits :splits
         beams :beams} (reduce (partial next-beam row-splitters) {:splits 0 :beams #{}} prev-beams)]
    {:splits (+ total-splits splits)
     :beams (set/select (partial in-manifold? cols) beams)}))

(defn all-index-of [s value]
  (loop [acc #{}
         from 0]
    (let [i (str/index-of s value from)]
      (cond
        (nil? i) acc
        :else (recur (conj acc i) (inc i))))))

(defn get-splitters [line]
  (all-index-of line "^"))

(defn get-sources [line]
  (all-index-of line "S"))

(defn parse-input [lines]
  {:cols (count (first lines))
   :splitters (map get-splitters (rest lines))
   :beams (get-sources (first lines))})

(defn do-splits [{cols :cols splitters :splitters beams :beams}]
  (reduce (partial next-beams cols) {:splits 0 :beams beams} splitters))

(defn analyze-manifold [input]
  (->> input
       str/split-lines
       parse-input
       do-splits
       :splits))

;; part 2

(defn add-beam [count old-beam]
  (if (nil? old-beam) count (+ count old-beam)))

(defn next-beam-t [splitters {splits :splits beams :beams} [beam count]]
  (cond
    (contains? splitters beam) {:splits (inc splits) :beams (-> beams
                                                                (update (dec beam) (partial add-beam count))
                                                                (update (inc beam) (partial add-beam count)))}
    :else {:splits splits :beams (update beams beam (partial add-beam count))}))

(defn next-beams-t [cols {total-splits :splits prev-beams :beams} row-splitters]
  (let [{splits :splits
         beams :beams} (reduce (partial next-beam-t row-splitters) {:splits 0 :beams {}} prev-beams)]
    {:splits (+ total-splits splits)
     :beams (into {} (filter (comp (partial in-manifold? cols) first) beams))}))

(defn do-splits-t [{cols :cols splitters :splitters beams :beams}]
  (reduce (partial next-beams-t cols) {:splits 0 :beams (into {} (map #(vector % 1) beams))} splitters))

(defn count-timelines [input]
  (->> input
       str/split-lines
       parse-input
       do-splits-t
       :beams
       (map second)
       (apply +)))
