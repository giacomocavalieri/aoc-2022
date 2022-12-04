(ns advent.2022.day-3
  (:require [advent.utils :refer [lines read-file split-in-half sum]]
            [clojure.set :refer [intersection]]))

(defn parse-input [string] (map seq (lines string)))
(def sample-input (parse-input "vJrwpWtwJgWrhcsFMMfFFhFp\njqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL\nPmmdzqPrVvPwwTWBwg\nwMqvLMZHhHMvwLHjbvcjnnSBnvTQFn\nttgJtRGJQctTZtZT\nCrZsJsPPZsGzwwsLwLmpwMDw"))
(def input (parse-input (read-file "day-3.txt")))

(defn to-rank [letter]
  (cond
    (Character/isUpperCase letter) (+ 27 (- (int letter) (int \A)))
    (Character/isLowerCase letter) (inc  (- (int letter) (int \a)))))

(defn find-common-key [group] (let [key-sets (map set group)] (first (apply intersection key-sets))))
(defn rank-common-key [group] (to-rank (find-common-key group)))
(defn solver [groups] (sum (map rank-common-key groups)))

(defn part-a [input] (solver (map split-in-half input)))
(defn part-b [input] (solver (partition 3 input)))
