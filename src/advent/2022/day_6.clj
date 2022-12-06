(ns advent.2022.day-6
  (:require [advent.utils :refer [all-different? enumerate find-first read-file]]))

(defn parse-input [string] (seq string))
(def sample-input-a (parse-input "mjqjpqmgbljsphdztnvjfqwrcgsmlb"))
(def sample-input-b (parse-input "bvwbjplbgvbhsrlpgdmjqwftvncz"))
(def sample-input-c (parse-input "nppdvjthqldpwncqszvftbrmjlhg"))
(def sample-input-d (parse-input "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"))
(def sample-input-e (parse-input "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))
(def input (parse-input (read-file "day-6.txt")))

(defn valid-marker? [[_index chars]] (all-different? chars))
(defn find-marker-position [buffer marker-size]
  (let [windows (partition marker-size 1 buffer)
        markers (enumerate windows marker-size)
        [index _marker] (find-first valid-marker? markers)]
    index))

(defn part-a [input] (find-marker-position input 4))
(defn part-b [input] (find-marker-position input 14))
