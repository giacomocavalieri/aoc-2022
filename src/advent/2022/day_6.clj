(ns advent.2022.day-6
  (:require [advent.utils :refer [all-different? indices-by-pred read-file]]))

(defn parse-input [string] (seq string))
(def sample-input-a (parse-input "mjqjpqmgbljsphdztnvjfqwrcgsmlb"))
(def sample-input-b (parse-input "bvwbjplbgvbhsrlpgdmjqwftvncz"))
(def sample-input-c (parse-input "nppdvjthqldpwncqszvftbrmjlhg"))
(def sample-input-d (parse-input "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"))
(def sample-input-e (parse-input "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))
(def input (parse-input (read-file "2022/day-6.txt")))

(defn find-marker-position [buffer marker-size]
  (let [markers (partition marker-size 1 buffer)
        index   (first (indices-by-pred all-different? markers))]
    (+ marker-size index)))

(defn part-a [input] (find-marker-position input 4))
(defn part-b [input] (find-marker-position input 14))
