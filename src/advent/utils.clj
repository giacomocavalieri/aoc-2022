(ns advent.utils
  (:require [clojure.string :refer [split]]
            [clojure.java.io :as io]))

; File reading
(defn read-file [file-name] (slurp (io/resource file-name)))

; Parsing 
(defn parse-int [string] (Integer/parseInt string))

; String manipulation
(defn split-on [regex string] (split string regex))
(defn lines [string] (split-on #"\n" string))
(defn words [string] (split-on #"\s\s*" string))

; Sequences
(defn sort-descending [seq] (sort-by - seq))
(defn sum [seq] (reduce + 0 seq))
(defn split-in-half [coll] (let [size (count coll) half (/ size 2)] (split-at half coll)))