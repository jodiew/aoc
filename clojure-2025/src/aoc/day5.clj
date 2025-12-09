(ns aoc.day5
  (:require
   [clojure.string :as str]))

(defn str->int [input-str]
  (biginteger input-str))

(defn str->range [range-raw]
  (let [[range-start range-end] (map str->int (str/split range-raw #"-"))]
    {:start range-start :end range-end}))

(defn to-state [[fresh-ranges-raw available-ids-raw]]
  {:fresh-ranges (map str->range (str/split-lines fresh-ranges-raw))
   :avaiable-ids (map str->int (str/split-lines available-ids-raw))})

(defn parse-input [data]
  (-> data
      (str/split #"\n\n")
      to-state))

(defn in-range?
  ([id] (fn [id-range] (in-range? id id-range)))
  ([id {start :start end :end}]
   (<= start id end)))

(defn fresh?
  ([fresh-ids] (fn [available-id] (fresh? fresh-ids available-id)))
  ([fresh-ids id]
   (some (in-range? id) fresh-ids)))

(defn filter-fresh-ids [{fresh-ids :fresh-ranges available-ids :avaiable-ids}]
  (filter (fresh? fresh-ids) available-ids))

(defn count-fresh-ingredients [data]
  (->> data
       parse-input
       filter-fresh-ids
       count))

;; (defn merge-range [ranges {start :start end :end}]
;;   (let [start-merge (first (filter (in-range? start) ranges))
;;         end-merge (first (filter (in-range? end) ranges))
;;         new-start (if (nil? start-merge) start (start-merge :start))
;;         new-end (if (nil? end-merge) end (end-merge :end))]
;;     (println "start-merge" start-merge)
;;     (println "end-merge" end-merge)
;;     (conj ranges {:start new-start :end new-end})))

;; (defn consolidate-ranges [{fresh-ranges :fresh-ranges}]
;;   (reductions merge-range #{} fresh-ranges))

(defn overlap? [a {start :start end :end}]
  (or (in-range? start a)
      (in-range? end a)))

(defn range-size [{start :start end :end}]
  (inc (- end start)))

(defn range-union [{a0 :start a1 :end} {b0 :start b1 :end}]
  {:start (min a0 b0)
   :end (max a1 b1)})

(defn compare-ranges [a b]
  (let [comp-start (compare (a :start) (b :start))]
    (if (= 0 comp-start)
      (compare (b :end) (a :end))
      comp-start)))

(defn reduce-ranges [acc ranges]
  (loop [[x & xs] ranges
         acc acc]
    (cond
      (nil? x) acc
      (empty? acc) (recur xs (cons x acc))
      (overlap? (first acc) x) (let [top (first acc)
                                     acc (rest acc)
                                     new (range-union top x)]
                                 (recur xs (cons new acc)))
      :else (recur xs (cons x acc)))))

(defn total-fresh-ids [data]
  (->> data
       parse-input
       :fresh-ranges
       (sort compare-ranges)
       (reduce-ranges '())
       (map range-size)
       (apply +)))
