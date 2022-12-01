(ns advent.days.day-1
  (:require [advent.utils :refer [read-file split-on parse-int sum sort-descending]]))

(defn parse-input [string]
  (->>
   string
   (split-on #"\n\n")
   (map #(split-on #"\n" %1))
   (map #(map parse-int %1))))

(def sample-input (parse-input "1000\n2000\n3000\n\n4000\n\n5000\n6000\n\n7000\n8000\n9000\n\n10000"))
(def input (parse-input (read-file "day-1.txt")))

(defn carried-calories [elf-snacks] (sum elf-snacks))
(defn sort-by-carried-calories [elves] (sort-descending (map carried-calories elves)))
(defn part-a [input] (first (sort-by-carried-calories input)))
(defn part-b [input] (sum (take 3 (sort-by-carried-calories input))))
