(ns advent.2022.day-3
  (:require [advent.utils :refer [lines read-file split-in-half sum]]
            [clojure.set :refer [intersection]]))

(defn parse-input [string] (map seq (lines string)))
(def sample-input (parse-input "vJrwpWtwJgWrhcsFMMfFFhFp\njqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL\nPmmdzqPrVvPwwTWBwg\nwMqvLMZHhHMvwLHjbvcjnnSBnvTQFn\nttgJtRGJQctTZtZT\nCrZsJsPPZsGzwwsLwLmpwMDw"))
(def input (parse-input (read-file "day-3.txt")))
(defn find-common-keys [lines] (apply intersection (map set lines)))

(defn to-rank [letter]
  (cond
    (Character/isUpperCase letter) (+ 27 (- (int letter) (int \A)))
    :else (inc (- (int letter) (int \a)))))

(defn part-a [input]
  (-> (for [line input
            :let [[upper lower] (split-in-half line)
                  keys-upper (set upper)
                  keys-lower (set lower)
                  common-keys (intersection keys-upper keys-lower)
                  common-key (first common-keys)]]
        (to-rank common-key))
      sum))

(defn part-b [input]
  (->
   (for [chunk (partition 3 input)
         :let [common-keys (find-common-keys chunk)
               common-key (first common-keys)]]
     (to-rank common-key))
   sum))
