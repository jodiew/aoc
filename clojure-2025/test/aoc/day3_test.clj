(ns aoc.day3-test
  (:require
   [aoc.day3 :as day3]
   [clojure.test :refer [deftest is testing]]))

(def example-data
  "987654321111111
811111111111119
234234234234278
818181911112111")

(deftest joltages-test
  (testing "joltages from banks part 1"
    (is (= 98 (day3/largest-joltage 2 "987654321111111")))
    (is (= 89 (day3/largest-joltage 2 "811111111111119")))
    (is (= 78 (day3/largest-joltage 2 "234234234234278")))
    (is (= 92 (day3/largest-joltage 2 "818181911112111")))
    (is (= 99 (day3/largest-joltage 2 "819181911112111"))))
  (testing "joltages from banks part 2"
    (is (= 987654321111 (day3/largest-joltage 12 "987654321111111")))
    (is (= 811111111119 (day3/largest-joltage 12 "811111111111119")))
    (is (= 434234234278 (day3/largest-joltage 12 "234234234234278")))
    (is (= 888911112111 (day3/largest-joltage 12 "818181911112111")))))

(deftest total-joltage-test
  (testing "total output joltage"
    (is (= 357 (day3/total-joltage example-data 2)))
    (is (= 3121910778619 (day3/total-joltage example-data 12)))))
