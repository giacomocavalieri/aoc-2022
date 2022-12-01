(ns advent.days.day-1-test
  (:require [clojure.test :refer [deftest testing is]]
            [advent.days.day-1 :refer [part-a part-b input sample-input]]))

(deftest part-a-test-sample
  (testing "Day 1 solution on sample input"
    (is (= 24000 (part-a sample-input)))))

(deftest part-b-test-sample
  (testing "Day 1 solution on sample input"
    (is (= 45000 (part-b sample-input)))))

(deftest part-a-test
  (testing "Day 1 solution on real input"
    (is (= 72602 (part-a input)))))

(deftest part-b-test
  (testing "Day 1 solution on real input"
    (is (= 207410 (part-b input)))))
