(ns aoc.core-test
  (:require [clojure.test :refer [deftest is]]
            [aoc.core :as core]))

(deftest test-within?
  (doseq [x (range 0 10)]
    (is (true? (core/within? 0 10 x))))
  (is (false? (core/within? 0 10 -1)))
  (is (false? (core/within? 0 10 11))))
