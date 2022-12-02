(ns advent.days.day-2-test
  (:require [clojure.test :refer [deftest testing is]]
            [advent.days.day-2 :refer [part-a part-b input sample-input]]))

(deftest part-a-test-sample
  (testing "Day 2 A - sample input"
    (is (= 15 (part-a sample-input)))))

(deftest part-b-test-sample
  (testing "Day 2 B - sample input"
    (is (= 12 (part-b sample-input)))))

(deftest part-a-test
  (testing "Day 2 A - real input"
    (is (= 13924 (part-a input)))))

(deftest part-b-test
  (testing "Day 2 B - real input"
    (is (= 13448 (part-b input)))))
