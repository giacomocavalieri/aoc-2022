(ns advent.2022.day-2
  (:require [advent.utils :refer [lines read-file sum words]]))

(defn parse-input [string] (map words (lines string)))
(def sample-input (parse-input "A Y\nB X\nC Z"))
(def input (parse-input (read-file "2022/day-2.txt")))

(def opponents-letter-to-choice {"A" :rock "B" :paper "C" :scissors})
(def my-letter-to-choice {"X" :rock "Y" :paper "Z" :scissors})
(def my-letter-to-outcome {"X" :loss "Y" :draw "Z" :win})
(def choice-to-points {:scissors 3 :paper 2 :rock 1})
(def outcome-to-points {:win 6 :draw 3 :loss 0})
(def what-loses-against {:rock :scissors :paper :rock :scissors :paper})
(def what-wins-against {:rock :paper :paper :scissors :scissors :rock})

(defn outcome-from-choices [opponents-choice my-choice]
  (cond
    (= opponents-choice my-choice)                      :draw
    (= opponents-choice (what-loses-against my-choice)) :win
    (= opponents-choice (what-wins-against my-choice))  :loss))

(defn choice-to-get-desired-outcome [opponents-choice outcome]
  (case outcome
    :draw opponents-choice
    :loss (what-loses-against opponents-choice)
    :win  (what-wins-against opponents-choice)))

(defn solver [input choice-from]
  (->
   (for [[opponents-letter my-letter] input
         :let [opponents-choice (opponents-letter-to-choice opponents-letter)
               my-choice        (choice-from opponents-choice my-letter)
               outcome          (outcome-from-choices opponents-choice my-choice)]]
     (+ (choice-to-points my-choice) (outcome-to-points outcome)))
   sum))

(defn part-a [input]
  (solver input
          (fn [_opponents-choice my-letter]
            (my-letter-to-choice my-letter))))

(defn part-b [input]
  (solver input
          (fn [opponents-choice my-letter]
            (let [desired-outcome (my-letter-to-outcome my-letter)]
              (choice-to-get-desired-outcome opponents-choice desired-outcome)))))
