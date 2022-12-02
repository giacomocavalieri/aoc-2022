(ns advent.days.day-2
  (:require [advent.utils :refer [read-file split-on sum]]))

(defn parse-input [string]
  (->>
   string
   (split-on #"\n")
   (map #(split-on #" " %1))))

(def sample-input (parse-input "A Y\nB X\nC Z"))
(def input (parse-input (read-file "day-2.txt")))

(def opponents-letter-to-choice {"A" :rock "B" :paper "C" :scissors})
(def my-letter-to-choice {"X" :rock "Y" :paper "Z" :scissors})
(def my-letter-to-outcome {"X" :loss "Y" :draw "Z" :win})
(def choice-to-points {:scissors 3 :paper 2 :rock 1})
(def outcome-to-points {:win 6 :draw 3 :loss 0})
(def what-loses {:rock :scissors :paper :rock :scissors :paper})
(def what-beats {:rock :paper :paper :scissors :scissors :rock})

(defn outcome-from-choices [opponents-choice my-choice]
  (cond
    (= opponents-choice my-choice)              :draw
    (= opponents-choice (what-beats my-choice)) :loss
    (= opponents-choice (what-loses my-choice)) :win))

(defn choice-from-outcome [opponents-choice outcome]
  (case outcome
    :draw opponents-choice
    :loss (what-loses opponents-choice)
    :win  (what-beats opponents-choice)))

(defn solver [input choice-outcome]
  (->>
   (for [[opponents-letter my-letter] input
         :let [opponents-choice            (opponents-letter-to-choice opponents-letter)
               {:keys [my-choice outcome]} (choice-outcome opponents-choice my-letter)]]
     (+ (choice-to-points my-choice) (outcome-to-points outcome)))
   sum))

(defn part-a [input]
  (letfn [(choice-outcome [opponents-choice my-letter]
            (let [my-choice (my-letter-to-choice my-letter)
                  outcome   (outcome-from-choices opponents-choice my-choice)]
              {:my-choice my-choice :outcome outcome}))]
    (solver input choice-outcome)))

(defn part-b [input]
  (letfn [(choice-outcome [opponents-choice my-letter]
            (let [outcome   (my-letter-to-outcome my-letter)
                  my-choice (choice-from-outcome opponents-choice outcome)]
              {:my-choice my-choice :outcome outcome}))]
    (solver input choice-outcome)))
