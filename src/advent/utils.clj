(ns advent.utils
  (:require [clojure.string :refer [split]]
            [clojure.java.io :as io]))

; File reading
(defn read-file [file-name] (slurp (io/resource file-name)))

; Parsing 
(defn parse-int [string] (Integer/parseInt string))
(defn letter? [char] (Character/isLetter char))

; String manipulation
(defn split-on [regex string] (split string regex))
(defn lines [string] (split-on #"\n" string))
(defn words [string] (split-on #"\s\s*" string))

; Sequences
(defn sort-descending [seq] (sort-by - seq))
(defn sum [seq] (reduce + 0 seq))
(defn split-in-half [coll] (let [size (count coll) half (/ size 2)] (split-at half coll)))
(defn count-predicate [predicate collection] (count (filter predicate collection)))
(defn transpose [& xs] (apply map list xs))
(defn find-first [predicate coll] (first (filter predicate coll)))
(defn partition-pad [n pad coll] (partition n n (repeat pad) coll))
(defn enumerate
  ([coll starting] (map-indexed (fn [index x] [(+ starting index) x]) coll))
  ([coll] (enumerate coll 0)))
(defn all-different? [xs]
  (let [n-xs        (count xs)
        unique-xs   (set xs)
        n-unique-xs (count unique-xs)]
    (= n-xs n-unique-xs)))
