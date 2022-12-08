(ns advent.2022.day-8
  (:require [advent.utils :refer [bool->int lines parse-int read-file take-while+]]
            [clojure.core.matrix :as matrix :refer [matrix]]))

(defn parse-line [string] (->> string seq (map str) (map parse-int) matrix))
(defn parse-input [string] (->> string lines (map parse-line) matrix))

(def sample-input (parse-input "30373\n25512\n65332\n33549\n35390"))
(def input (parse-input (read-file "day-8.txt")))

(defn left-neighbours  [m row col] (rseq (subvec (matrix/get-row m row) 0 col)))
(defn right-neighbours [m row col] (subvec (matrix/get-row m row) (inc col) (matrix/column-count m)))
(defn above-neighbours [m row col] (rseq (subvec (matrix/get-column m col) 0 row)))
(defn below-neighbours [m row col] (subvec (matrix/get-column m col) (inc row) (matrix/row-count m)))
(defn orthogonal-neighbours [m row col]
  (map #(% m row col) [left-neighbours right-neighbours above-neighbours below-neighbours]))

(defn visible-from-one-side? [trees row col tree]
  (let [tallest? (fn [neighbours] (every? #(< % tree) neighbours))
        orthogonal-neighbours (orthogonal-neighbours trees row col)]
    (some tallest? orthogonal-neighbours)))

(defn part-a [trees]
  (->>
   trees
   (matrix/emap-indexed
    (fn [[row col] tree] (visible-from-one-side? trees row col tree)))
   (matrix/emap bool->int)
   (matrix/esum)))

(defn viewing-score [trees row col tree]
  (let [neighbours->score (fn [neighbours] (->> neighbours (take-while+ #(< % tree)) count))
        orthogonal-neighbours (orthogonal-neighbours trees row col)
        scores (map neighbours->score orthogonal-neighbours)]
    (apply * scores)))

(defn part-b [trees]
  (->>
   trees
   (matrix/emap-indexed
    (fn [[row col] tree] (viewing-score trees row col tree)))
   (matrix/emax)))
