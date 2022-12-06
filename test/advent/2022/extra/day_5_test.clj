(ns advent.2022.extra.day-5-test
  (:require [advent.2022.day-5 :as day-5]
            [advent.2022.extra.day-5 :as extra.day-5]
            [clojure.test :refer [deftest is testing]]))

(deftest ^:day-5-year-2022 test-parsed-input-generator
  (let [message "HELLOWORLD"
        n-steps 1000
        input   (extra.day-5/generate-parsed-input n-steps message reverse)]
    (testing "Generated input yields expected message"
      (is (= (day-5/part-a input) message)))))