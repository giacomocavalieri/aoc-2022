(ns advent.2022-test
  (:require [advent.2022.day-1 :as day-1]
            [advent.2022.day-2 :as day-2]
            [advent.2022.day-3 :as day-3]
            [clojure.test :refer [deftest is testing]]))

(deftest ^:day-1-year-2022 test-year-2022-day-1-part-part-a
  (testing "[2022] Day 1 - part-a"
    (is (= (day-1/part-a day-1/sample-input) 24000))
    (is (= (day-1/part-a day-1/input) 72602))))

(deftest ^:day-1-year-2022 test-year-2022-day-1-part-part-b
  (testing "[2022] Day 1 - part-b"
    (is (= (day-1/part-b day-1/sample-input) 45000))
    (is (= (day-1/part-b day-1/input) 207410))))

(deftest ^:day-2-year-2022 test-year-2022-day-2-part-part-a
  (testing "[2022] Day 2 - part-a"
    (is (= (day-2/part-a day-2/sample-input) 15))
    (is (= (day-2/part-a day-2/input) 13924))))

(deftest ^:day-2-year-2022 test-year-2022-day-2-part-part-b
  (testing "[2022] Day 2 - part-b"
    (is (= (day-2/part-b day-2/sample-input) 12))
    (is (= (day-2/part-b day-2/input) 13448))))

(deftest ^:day-3-year-2022 test-year-2022-day-3-part-part-a
  (testing "[2022] Day 3 - part-a"
    (is (= (day-3/part-a day-3/sample-input) 157))
    (is (= (day-3/part-a day-3/input) 7878))))

(deftest ^:day-3-year-2022 test-year-2022-day-3-part-part-b
  (testing "[2022] Day 3 - part-b"
    (is (= (day-3/part-b day-3/sample-input) 70))
    (is (= (day-3/part-b day-3/input) 2760))))
