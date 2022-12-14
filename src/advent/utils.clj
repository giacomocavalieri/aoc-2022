(ns advent.utils
  (:require [clojure.java.io :as io]
            [clojure.string :refer [join split trim]]))

; Error
(defn fail [x] (throw (RuntimeException. (str x))))

; File reading
(defn read-file [file-name] (slurp (io/resource file-name)))

; Parsing 
(defn parse-int [string] (Integer/parseInt string))
(defn char->digit [char] (Character/digit char 10))
(defn letter? [char] (Character/isLetter char))

; String manipulation
(defn split-on [regex string] (split string regex))
(defn lines [string] (when string (split-on #"\n" string)))
(defn words [string] (when string (split-on #"\s\s*" (trim string))))
(defn unlines [lines] (join "\n" lines))
(defn unwords [words] (join " " words))

; Matrices
(defn matrix-indices-of [matrix to-find]
  (for [[x row] (map-indexed vector matrix)
        [y val] (map-indexed vector row)
        :when (= to-find val)]
    [x y]))

(defn neighbouring-points [[x y]]
  (let [deltas [[-1 0] [1 0] [0 -1] [0 1]]]
    (mapv #(mapv + [x y] %) deltas)))

; Math
(defn divisible? [num divisor] (zero? (mod num divisor)))

; Functions
(defn repeat-until-convergence [f x]
  (loop [prev x]
    (let [res (f prev)]
      (if (= res prev) res (recur res)))))
(defn repeat-until-nil [f x]
  (loop [prev x]
    (let [res (f prev)]
      (if (nil? res) prev (recur res)))))

; Sequences and collections
(defn not-contains? [coll x] (not (contains? coll x)))
(defn conj-non-nil [coll x] (if (nil? x) coll (conj coll x)))
(defn range-inclusive [start end] (range start (inc end)))
(defn sort-descending [seq] (sort-by - seq))
(defn max-n [n seq] (take n (sort-descending seq)))
(defn sum [seq] (reduce + 0 seq))
(defn split-in-half [coll] (let [size (count coll) half (/ size 2)] (split-at half coll)))
(defn count-predicate [predicate collection] (count (filter predicate collection)))
(defn transpose [& xs] (apply mapv vector xs))
(defn find-first [predicate coll] (first (filter predicate coll)))
(defn partition-pad [n pad coll] (partition n n (repeat pad) coll))
(defn pad [to-size coll pad-value] (take to-size (concat coll (repeat pad-value))))
(defn pad-same-size [pad-value coll1 coll2 & vs]
  (let [all      (concat [coll1 coll2] vs)
        max-size (apply max (map count all))]
    (map #(pad max-size % pad-value) all)))
(defn zip-pad [coll1 coll2] (apply map vector (pad-same-size nil coll1 coll2)))
(defn indices-by-pred [pred coll] (keep-indexed #(when (pred %2) %1) coll))
(defn index-of [elem coll] (.indexOf coll elem))
(defn swap [v key1 key2] (assoc v key1 (v key2) key2 (v key1)))
(defn take-until [pred coll]
  (let [[left right] (split-with pred coll)]
    (if (empty? right) left (conj left (first right)))))
(defn all-different? [xs]
  (let [n-xs        (count xs)
        unique-xs   (set xs)
        n-unique-xs (count unique-xs)]
    (= n-xs n-unique-xs)))

; Points
(defn chess-distance [point1 point2] (apply max (mapv #(abs (- %1 %2)) point1 point2)))
(defn chess-adjacent? [point1 point2] (<= (chess-distance point1 point2) 1))
(defn points-of-segment [[[x1 y1] [x2 y2]]]
  (cond
    (= x1 x2) (mapv vector (repeat x1) (range-inclusive (min y1 y2) (max y1 y2)))
    (= y1 y2) (mapv vector (range-inclusive (min x1 x2) (max x1 x2)) (repeat y1))
    :else     (throw (IllegalArgumentException.
                      "I could not be bothered to implement a version for oblique segments"))))

; Macros
(defmacro both [pred x y] (list 'and (list pred x) (list pred y)))