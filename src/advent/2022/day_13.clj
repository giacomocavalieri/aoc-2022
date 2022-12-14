(ns advent.2022.day-13
  (:require [advent.utils :refer [both index-of indices-by-pred lines
                                  read-file split-on sum zip-pad]]))

(defn parse-block [block] (map read-string (lines block)))
(defn parse-input [string] (mapcat parse-block (split-on #"\n\n" string)))
(def sample-input (parse-input (read-file "2022/day-13-sample.txt")))
(def input (parse-input (read-file "2022/day-13.txt")))

(defn compare-packets [p1 p2]
  (cond
    (both number? p1 p2) (compare p1 p2)
    (nil? p1) -1
    (nil? p2) 1
    (number? p1) (compare-packets [p1] p2)
    (number? p2) (compare-packets p1 [p2])
    ; If both packets are lists compare their elements pairwise (the nil pad is needed to avoid
    ; ignoring elements of one of the packets in case it is shorter than the other) the result
    ; of the comparison is given by the first non 0 (i.e. equals) result, if all the results are
    ; 0 then the packets are equal pair-by-pair and considered equals
    :else
    (let [pairs       (zip-pad p1 p2)
          comparisons (map #(apply compare-packets %) pairs)]
      (nth (drop-while zero? comparisons) 0 0))))

(defn packet< [p1 p2] (neg? (compare-packets p1 p2)))

(defn part-a [packets]
  (let [pairs   (partition 2 packets)
        indices (indices-by-pred #(apply packet< %) pairs)]
    (sum (map inc indices))))

(defn part-b [packets]
  (let [left-divider-packet  [[2]]
        right-divider-packet [[6]]
        all-packets     (conj packets left-divider-packet right-divider-packet)
        sorted-packets  (sort packet< all-packets)
        index-of-packet (fn [packet] (inc (index-of packet sorted-packets)))]
    (* (index-of-packet left-divider-packet)
       (index-of-packet right-divider-packet))))
