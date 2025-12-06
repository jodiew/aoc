(ns aoc.day2
  (:require [clojure.string :as str]))

(def example-data "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124")

(defn split-ranges [data] (str/split data #","))

(defn split-range [data] (str/split data #"-"))

(defn ->int-range [data] (map biginteger (split-range data)))

(defn ->int-ranges [data] (map ->int-range data))

(defn ->lazy-range [[start end]] (range start (inc end)))

(defn ->lazy-ranges [data] (map ->lazy-range data))

(defn invalid-id? [id] (let [id-str (str id)
                             length (count id-str)
                             half (/ length 2)
                             start (subs id-str 0 half)
                             end (subs id-str half)]
                         (= start end)))

(defn filter-invalid-ids [ids] (filter invalid-id? ids))

(defn invaid-ids [data] (mapcat filter-invalid-ids data))

(defn sum [ids] (apply + ids))

(defn find-invalid-ids [data]
  (->> data
       str/trim
       split-ranges
       ->int-ranges
       ->lazy-ranges
       invaid-ids
       sum))

(find-invalid-ids example-data)

;; (find-invalid-ids (slurp "data/day_2.txt"))

(defn invalid-id-2? [id]
  (let [id-str (str id)
        id-length (count id-str)
        range-to-half (range (quot id-length 2) 0 -1)
        to-partitions #(partition % % nil id-str)
        invalid? #(apply = %)]
    (if (<= id-length 1) false
        (some invalid? (map to-partitions range-to-half)))))

(invalid-id-2? 12341234)
(invalid-id-2? 123123123)
(invalid-id-2? 1212121212)
(invalid-id-2? 1111111)
(invalid-id-2? 100N)
(invalid-id-2? 2)

(defn filter-invalid-ids-2 [ids]
  (let [id-range (str (first ids) "-" (last ids))
        filtered (filter invalid-id-2? ids)]
    (println id-range "has" (count filtered) "invalid IDs," filtered)
    filtered))

(filter-invalid-ids-2 '(11 12 13 14 15 16 17 18 19 20 21 22))
(filter-invalid-ids-2 '(95 96N 97N 98N 99N 100N 101N 102N 103N 104N 105N 106N 107N 108N 109N 110N 111N 112N 113N 114N 115N))

(defn invalid-ids-2 [id-ranges] (mapcat filter-invalid-ids-2 id-ranges))

(defn find-invalid-ids-2 [data]
  (->> data
       str/trim
       split-ranges
       ->int-ranges
       ->lazy-ranges
       invalid-ids-2
       sum))

(find-invalid-ids-2 example-data)

;; (find-invalid-ids-2 (slurp "data/day_2.txt"))
