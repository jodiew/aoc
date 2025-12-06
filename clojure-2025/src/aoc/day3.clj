(ns aoc.day3
  (:require
   [clojure.string :as str]
   [clojure.math.numeric-tower :as math :refer [expt]]))

(defn split-batteries [bank] (str/split bank #""))

(defn find-next-digit [[batts _] digit]
  (let [to-search (reverse (drop-last digit batts))
        battery (apply max-key second to-search)
        next-search (drop-while #(<= (first %) (first battery)) batts)]
    ;; (println "In" to-search "the" digit "digit is" battery ", next search" next-search)
    [next-search battery]))

(defn largest-joltage
  ([digits] (fn [bank] (largest-joltage digits bank)))
  ([digits bank]
   (let [batteries (map Integer/parseInt (split-batteries bank))
         indexed-batteries (map-indexed vector batteries)
         find-digits (reductions find-next-digit [indexed-batteries nil] (range (dec digits) -1 -1))
         found-batteries (map (comp second second) (rest find-digits))
         joltage (apply + (map #(* %1 (expt 10 %2)) found-batteries (range (dec digits) -1 -1)))]
     ;;  (println "In" bank "the largest joltage you can produce is" joltage)
     joltage)))

(defn joltages
  ([digits] (fn [banks] (joltages digits banks)))
  ([digits banks]
   (let [largest-joltage-digits (largest-joltage digits)]
     (map largest-joltage-digits banks))))

(defn sum-joltages [joltages] (apply + joltages))

(defn total-joltage [data digits]
  (let [joltages (joltages digits)]
    (->> data
         str/split-lines
         joltages
         sum-joltages)))
