(ns aoc.day6-test 
  (:require
   [aoc.day6 :as day6]
   [clojure.test :refer [deftest is testing]]))

(def example-data
  "123 328  51 64 
 45 64  387 23 
  6 98  215 314
*   +   *   +  ")

(deftest do-problem-test
  (testing "part 1 do a single problem"
    (is (= 33210 (day6/do-problem {:numbers '(123 45 6) :op *})))
    (is (= 490 (day6/do-problem {:numbers '(328 64 98) :op +})))
    (is (= 4243455 (day6/do-problem {:numbers '(51 387 215) :op *})))
    (is (= 401 (day6/do-problem {:numbers '(64 23 314) :op +})))))

(deftest math-homework-test
  (testing "part 1 solution"
    (is (= 4277556 (day6/math-homework example-data)))))
