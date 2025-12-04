(ns aoc.day2
  (:require [clojure.string :as str]
            [aoc.core :as core]))

(def example-data "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124")

(defn split-ranges [data] (str/split data #","))

(defn split-range [data] (str/split data #"-"))

(defn ->int-range [data] (map biginteger (split-range data)))

(defn ->int-ranges [data] (map ->int-range data))

(split-range "5252508299-5252534634")
(map core/str->int '["5252508299" "5252534634"])
(core/str->int "5252508299")
(biginteger "5252508299")

(defn ->lazy-range [[start end]] (range start (inc end)))

(defn ->lazy-ranges [data] (map ->lazy-range data))

(defn invalid-id? [id] (let [id-str (str id)
                             length (count id-str)
                             half (/ length 2)
                             start (subs (str id) 0 half)
                             end (subs (str id) half)]
                         (= start end)))

(invalid-id? "1010")

(defn filter-invalid-ids [ids] (filter invalid-id? ids))

(filter-invalid-ids '(11 12 13 14 15 16 17 18 19 20 21 22))

(defn ->invaid-ids [data] (mapcat filter-invalid-ids data))

(defn sum [ids] (apply + ids))

(defn find-invalid-ids [data]
  (->> data
       str/trim
       split-ranges 
       ->int-ranges 
       ->lazy-ranges 
       ->invaid-ids 
       sum
       ))

(find-invalid-ids example-data)

(find-invalid-ids (slurp "data/day_2.txt"))
