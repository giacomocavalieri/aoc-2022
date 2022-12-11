(ns advent.2017.days-test
  (:require [clojure.test :refer [deftest is testing]]
            [advent.2017.day-16 :as day-16]))

(deftest ^:day-16-year-2017 test-year-2017-day-16-part-a
  (testing "[2022] Day 1 - part-a"
    (is (= (day-16/part-a day-16/input) "ebjpfdgmihonackl"))))

(deftest ^:day-16-year-2017 test-year-2017-day-16-part-b
  (testing "[2022] Day 1 - part-b"
    (is (= (day-16/part-b day-16/input) "abocefghijklmndp"))))
