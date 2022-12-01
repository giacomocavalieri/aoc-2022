(ns advent.utils
  (:require [clojure.string :refer [split]]
            [clojure.java.io :as io]))

(defn parse-int [string] (Integer/parseInt string))
(defn split-on [regex string] (split string regex))
(defn sort-descending [seq] (sort-by - seq))
(defn sum [seq] (reduce + 0 seq))
(defn read-file [file-name] (slurp (io/resource file-name)))