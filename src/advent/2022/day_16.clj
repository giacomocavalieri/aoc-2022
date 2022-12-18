(ns advent.2022.day-16
  (:require [advent.utils :refer [lines parse-int read-file split-on]]))

(defn parse-valves-list [raw-valves-list] (split-on #", " raw-valves-list))
(defn parse-line [raw-line]
  (let [regex #"Valve ([A-Z][A-Z]) has flow rate=([0-9][0-9]*); tunnels? leads? to valves? ([A-Z][A-Z](?:, [A-Z][A-Z])*)"
        [valve raw-flow reached-valves] (rest (re-find regex raw-line))]
    [[valve (parse-int raw-flow)] [valve (parse-valves-list reached-valves)]]))

(defn parse-input [string] (mapv parse-line (lines string)))
(def sample-input (parse-input (read-file "2022/day-16-sample.txt")))
(def input (parse-input (read-file "2022/day-16.txt")))

(defn part-a [valves])
(defn part-b [valves])
