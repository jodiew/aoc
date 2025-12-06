(ns aoc.day2-test
  (:require [clojure.test :refer [deftest is]]
            [aoc.day2 :as day2]))

(def example-data "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124")

(deftest test-split-ranges
  (is (= ["11-22" "95-115" "998-1012" "1188511880-1188511890" "222220-222224" "1698522-1698528" "446443-446449" "38593856-38593862" "565653-565659" "824824821-824824827" "2121212118-2121212124"] (day2/split-ranges example-data))))

(deftest test-split-range
  (is (= ["11" "22"] (day2/split-range "11-22"))))

(deftest test-int-range
  (is (= [11N 22N] (day2/->int-range "11-22")))
  (is (= [5252508299N 5252534634N] (day2/->int-range "5252508299-5252534634"))))

(deftest test-int-ranges
  (is (= [[11 22N]
          [95 115N]]
         (day2/->int-ranges ["11-22" "95-115"]))))

(deftest test-lazy-range
  (is (= [11 12N 13N 14N 15N 16N 17N 18N 19N 20N 21N 22N]
         (day2/->lazy-range [11 22N]))))

(deftest test-lazy-ranges
  (is (= [[11 12N 13N 14N 15N 16N 17N 18N 19N 20N 21N 22N]
          [95 96N 97N 98N 99N 100N 101N 102N 103N 104N 105N 106N 107N 108N 109N 110N 111N 112N 113N 114N 115N]]
         (day2/->lazy-ranges [[11 22N] [95 115N]]))))

(deftest test-invalid-id?
  (is (true? (day2/invalid-id? "55")))
  (is (true? (day2/invalid-id? "6464")))
  (is (true? (day2/invalid-id? "123123")))
  (is (false? (day2/invalid-id? "101"))))

(deftest test-filter-invalid-ids
  (is (= [11 22N]
         (day2/filter-invalid-ids [11 12N 13N 14N 15N 16N 17N 18N 19N 20N 21N 22N]))))

(deftest test-invalid-ids)