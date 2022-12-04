(ns advent.2022.day-4
  (:require [advent.utils :refer [count-predicate lines parse-int read-file split-on]]))

(defn parse-pair [string] (map parse-int (split-on #"-" string)))
(defn parse-input [string]
  (->>
   string
   lines
   (map #(split-on #"," %))
   (map #(map parse-pair %))))

(def sample-input (parse-input "2-4,6-8\n2-3,4-5\n5-7,7-9\n2-8,3-7\n6-6,4-6\n2-6,4-8"))
(def input (parse-input (read-file "day-4.txt")))

(defn fully-contained? [[x-start x-end] [y-start y-end]] (and (>= x-start y-start) (<= x-end y-end)))
(defn overlaps? [[x-start x-end] [y-start y-end]] (and (<= x-start y-end) (>= x-end y-start)))
(defn either [predicate] (fn [[x y]] (or (predicate x y) (predicate y x))))

(defn part-a [input] (count-predicate (either fully-contained?) input))
(defn part-b [input] (count-predicate (either overlaps?) input))
