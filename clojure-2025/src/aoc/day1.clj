(ns aoc.day1
  (:require [clojure.string :as str]))

(def example-data
  "L68
L30
R48
L5
R60
L55
L1
L99
R14
L82")

;; Part 1

(defn str->int [str]
  (Integer/parseInt str))

(defn do-rotation [dial rotation]
  (let [right? (str/starts-with? rotation "R")
        left? (str/starts-with? rotation "L")
        amount (str->int (subs rotation 1))
        new-dial (cond
                   right? (mod (+ dial amount) 100)
                   left? (mod (- dial amount) 100)
                   :else "???")]
    ;; (println "The dial is rotated" rotation "to point at" new-dial)
    new-dial))

(defn find-password [data]
  (let [dial 50]
    ;; (println "The dial starts by pointing at" dial)
    (->> (str/split-lines data)
         (reductions do-rotation dial)
         (filter zero?)
         count)))

(find-password example-data)

(find-password (slurp "data/day_1.txt"))

;; Part 2

(defn pass-zero [dial rotation]
  (let [right? (str/starts-with? rotation "R")
        left? (str/starts-with? rotation "L")
        amount (str->int (subs rotation 1))
        pass-zero (cond right? (+ dial amount)
                        left? (+ amount)
                        :else "???")]
    (println "total rotation" pass-zero)
    pass-zero))

(defn more-rotation [[dial total-zero] rotation]
  (let [right? (str/starts-with? rotation "R")
        left? (str/starts-with? rotation "L")
        amount (str->int (subs rotation 1))
        new-dial (cond
                   right? (mod (+ dial amount) 100)
                   left? (mod (- dial amount) 100)
                   :else "???")
        zero-click (cond
                     right? (quot (+ dial (dec amount)) 100)
                     (and left? (= dial 0)) (dec (quot (+ (- 100 dial) (dec amount)) 100))
                     left? (quot (+ (- 100 dial) (dec amount)) 100)
                     :else "???")]
    ;; (println "The dial is rotated" rotation "to point at " new-dial (if (< 0 zero-click) (str "; during this rotation, it points at 0 " zero-click " times") ""))
    [new-dial (if (zero? new-dial) (+ zero-click total-zero 1) (+ zero-click total-zero))]))

(defn find-password-new [data]
  (let [dial 50]
    ;; (println "The dial starts by pointing at" dial)
    (->> (str/split-lines data)
         (reduce more-rotation [dial 0])
         last)))

(find-password-new example-data)

(find-password-new (slurp "data/day_1.txt"))
