(ns aoc.day4-test 
  (:require
   [aoc.day4 :as day4]
   [clojure.test :refer [deftest is testing]]))

(def example-data
  "..@@.@@@@.
@@@.@.@.@@
@@@@@.@.@@
@.@@@@..@.
@@.@@@@.@@
.@@@@@@@.@
.@.@.@.@@@
@.@@@.@@@@
.@@@@@@@@.
@.@.@@@.@.")

(def example-data-grid
  '(["." "." "@" "@" "." "@" "@" "@" "@" "."]
    ["@" "@" "@" "." "@" "." "@" "." "@" "@"]
    ["@" "@" "@" "@" "@" "." "@" "." "@" "@"]
    ["@" "." "@" "@" "@" "@" "." "." "@" "."]
    ["@" "@" "." "@" "@" "@" "@" "." "@" "@"]
    ["." "@" "@" "@" "@" "@" "@" "@" "." "@"]
    ["." "@" "." "@" "." "@" "." "@" "@" "@"]
    ["@" "." "@" "@" "@" "." "@" "@" "@" "@"]
    ["." "@" "@" "@" "@" "@" "@" "@" "@" "."]
    ["@" "." "@" "." "@" "@" "@" "." "@" "."]))

(deftest is-roll?-test
  (testing "check if it's a roll"
    (is (false? (day4/is-roll? example-data-grid [0 0]))
        (true? (day4/is-roll? example-data-grid [1 0])))))

(deftest total-accessable-rolls-test
  (testing "Final solution to part 1"
    (is (= 13 (day4/total-accessable-rolls example-data))))
  (testing "Final solution to part 2"
    (is (= 43 (day4/total-removed-rolls example-data)))))
