(ns aoc.day5-test 
  (:require
   [aoc.day5 :as day5]
   [clojure.test :refer [deftest is testing]]))

(def example-data
  "3-5
10-14
16-20
12-18

1
5
8
11
17
32")

(def example-ranges
  '({:start 3, :end 5} {:start 10, :end 14} {:start 16, :end 20} {:start 12, :end 18}))

(deftest str->range-test
  (testing "Convert range string to a set of numbers"
    (is (= {:start 3 :end 5} (day5/str->range "3-5")))))

(deftest in-range?-test
  (testing "is id in the range"
    (is (true? (day5/in-range? 5 {:start 3 :end 5})))
    (is (false? (day5/in-range? 1 {:start 3 :end 5})))))

(deftest fresh?-test
  (testing "check if the id is in the ranges"
    (is (true? (day5/fresh? '({:start 3 :end 5}) 5)))
    (is (nil? (day5/fresh? '({:start 3 :end 5}) 8)))
    (is (nil? (day5/fresh? example-ranges 1)))))

(deftest filter-fresh-ids-test
  (testing "filter available ids by fresh ranges"
    (is (= '(5) (day5/filter-fresh-ids {:fresh-ranges '({:start 3 :end 5}) :avaiable-ids '(1 5 8)})))))

(deftest count-fresh-ingredients-test
      (testing "Part 1 final solution"
        (is (= 3 (day5/count-fresh-ingredients example-data))))) 
