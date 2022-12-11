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

; Points
(defn chess-distance [point1 point2] (apply max (mapv #(abs (- %1 %2)) point1 point2)))
(defn chess-adjacent? [point1 point2] (<= (chess-distance point1 point2) 1))

; Math
(defn divisible? [num divisor] (zero? (mod num divisor)))

; Functions
(defn apply-until-convergence [f x]
  (loop [prev x]
    (let [res (f prev)]
      (if (= res prev) res (recur res)))))
(defn apply-until-nil [f x]
  (loop [prev x]
    (let [res (f prev)]
      (if (nil? res) prev (recur res)))))

; Sequences
(defn sort-descending [seq] (sort-by - seq))
(defn max-n [n seq] (take n (sort-descending seq)))
(defn sum [seq] (reduce + 0 seq))
(defn split-in-half [coll] (let [size (count coll) half (/ size 2)] (split-at half coll)))
(defn count-predicate [predicate collection] (count (filter predicate collection)))
(defn transpose [& xs] (apply mapv vector xs))
(defn find-first [predicate coll] (first (filter predicate coll)))
(defn partition-pad [n pad coll] (partition n n (repeat pad) coll))
(defn indexes-of [pred coll] (keep-indexed #(when (pred %2) %1) coll))
(defn take-until [pred coll]
  (let [[left right] (split-with pred coll)]
    (if (empty? right) left (conj left (first right)))))
(defn all-different? [xs]
  (let [n-xs        (count xs)
        unique-xs   (set xs)
        n-unique-xs (count unique-xs)]
    (= n-xs n-unique-xs)))
