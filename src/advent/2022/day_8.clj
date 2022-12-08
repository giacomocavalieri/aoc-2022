(ns advent.2022.day-8
  (:require [advent.utils :refer [char->digit lines read-file take-until transpose]]))

(defn parse-line [string] (mapv char->digit string))
(defn parse-input [string] (mapv parse-line (lines string)))

(def sample-input (parse-input "30373\n25512\n65332\n33549\n35390"))
(def input (parse-input (read-file "day-8.txt")))

(defn visible? [[tree & other-trees]] (every? #(< % tree) other-trees))
(defn row-scenic-score [[tree & other-trees]] (count (take-until #(< % tree) other-trees)))
(defn scenic-score [neighbours] (apply * (map row-scenic-score neighbours)))

(defn map-filter-with-neighbours [matrix f pred]
  (let [transposed-matrix (apply transpose matrix)
        cols (count (first matrix))
        rows (count matrix)]
    (for [row (range rows)
          col (range cols)
          :let [right (subvec (matrix row) col)
                left  (rseq (subvec (matrix row) 0 (inc col)))
                below (subvec (transposed-matrix col) row)
                above (rseq (subvec (transposed-matrix col) 0 (inc row)))]
          :when (pred [right left below above])]
      (f [right left below above]))))

(defn count-with-neighbours [matrix pred]
  (count (map-filter-with-neighbours matrix (constantly nil) pred)))
(defn map-with-neighbours [matrix f]
  (map-filter-with-neighbours matrix f (constantly true)))

(defn part-a [trees] (count-with-neighbours trees #(some visible? %)))
(defn part-b [trees] (apply max (map-with-neighbours trees scenic-score)))
