(ns advent.2022.extra.day-5-test
  (:require [advent.2022.day-5 :as day-5]
            [advent.2022.extra.day-5 :as extra.day-5 :refer [generate-input
                                                             serialize-input]]
            [clojure.test :refer [deftest is testing]]))

(def default-message "HELLOWORLD")
(defn default-input [rearrange] (generate-input 1000 20 default-message rearrange))

(deftest ^:day-5-year-2022 test-input-generation
  (let [input-part-a (default-input reverse)
        input-part-b (default-input identity)]
    (testing "Generated input yields expected message"
      (is (= (day-5/part-a input-part-a) default-message))
      (is (= (day-5/part-b input-part-b) default-message)))))

(deftest ^:day-5-year-2022 test-input-serialization
  (let [input-part-a (default-input reverse)
        input-part-b (default-input identity)
        serialized-input-part-a (serialize-input input-part-a)
        serialized-input-part-b (serialize-input input-part-b)]
    (testing "Parsing is inverse of serializing"
      (is (= (day-5/parse-input serialized-input-part-a) input-part-a))
      (is (= (day-5/parse-input serialized-input-part-b) input-part-b)))))
