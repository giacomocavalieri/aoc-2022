(ns advent.2022-test
  (:require [advent.2022.day-1 :as day-1]
            [advent.2022.day-2 :as day-2]
            [advent.2022.day-3 :as day-3]
            [advent.2022.day-4 :as day-4]
            [advent.2022.day-5 :as day-5]
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

(deftest ^:day-4-year-2022 test-year-2022-day-4-part-part-a
  (testing "[2022] Day 4 - part-a"
    (is (= (day-4/part-a day-4/sample-input) 2))
    (is (= (day-4/part-a day-4/input) 453))))

(deftest ^:day-4-year-2022 test-year-2022-day-4-part-part-b
  (testing "[2022] Day 4 - part-b"
    (is (= (day-4/part-b day-4/sample-input) 4))
    (is (= (day-4/part-b day-4/input) 919))))

(deftest ^:day-5-year-2022 test-year-2022-day-5-part-part-a
  (testing "[2022] Day 5 - part-a"
    (is (= (day-5/part-a day-5/sample-input) "CMZ"))
    (is (= (day-5/part-a day-5/input) "JDTMRWCQJ"))))

(deftest ^:day-5-year-2022 test-year-2022-day-5-part-part-b
  (testing "[2022] Day 5 - part-b"
    (is (= (day-5/part-b day-5/sample-input) "MCD"))
    (is (= (day-5/part-b day-5/input) "VHJDDCWRD"))))
