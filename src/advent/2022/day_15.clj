(ns advent.2022.day-15
  (:require [advent.utils :refer [lines manhattan-distance parse-int read-file]]))

(defn to-sensor [[sensor beacon]] [sensor beacon (manhattan-distance sensor beacon)])
(defn parse-line [line] (to-sensor (partition 2 (mapv parse-int (re-seq #"-?\d\d*" line)))))
(defn parse-input [string] (mapv parse-line (lines string)))
(def sample-input (parse-input (read-file "2022/day-15-sample.txt")))
(def input (parse-input (read-file "2022/day-15.txt")))

(defn part-a [tunnels y])
(defn part-b [tunnels])
