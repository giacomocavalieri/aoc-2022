(ns advent.2022.day-12
  (:require [advent.utils :refer [lines matrix-indices-of neighbouring-points
                                  read-file]]
            [clojure.core.matrix :refer [emap-indexed]]
            [loom.alg :refer [bf-path]]
            [loom.graph :refer [digraph]]))

(defn parse-input [string] (mapv vec (lines string)))

(defn char->elevation [char]
  (case char
    \S (char->elevation \a)
    \E (char->elevation \z)
    (- (int char) (int \a))))

(defn can-reach? [source destination]
  (let [source-elevation      (char->elevation source)
        destination-elevation (char->elevation destination)]
    (<= (- destination-elevation source-elevation) 1)))

(defn reachable-neighbours [matrix point point-value]
  (for [neighbour (neighbouring-points point)
        :let [neighbour-value (get-in matrix neighbour)]
        :when neighbour-value
        :when (can-reach? point-value neighbour-value)]
    neighbour))

(defn point->adjacency-list [matrix point point-value]
  [point (reachable-neighbours matrix point point-value)])

(defn grid->adjacency-list [grid]
  (reduce concat (emap-indexed #(point->adjacency-list grid %1 %2) grid)))

(defn grid->digraph [grid]
  (let [adjacency-list (grid->adjacency-list grid)]
    (digraph (into {} adjacency-list))))

(def sample-input (parse-input "Sabqponm\nabcryxxl\naccszExk\nacctuvwj\nabdefghi"))
(def input (parse-input (read-file "2022/day-12.txt")))

(defn shortest-path-dist [graph start end] (dec (count (bf-path graph start end))))

(defn part-a [grid]
  (let [start (first (matrix-indices-of grid \S))
        end   (first (matrix-indices-of grid \E))
        graph (grid->digraph grid)]
    (shortest-path-dist graph start end)))

(defn part-b [grid]
  (let [starts (matrix-indices-of grid \a)
        end    (first (matrix-indices-of grid \E))
        graph  (grid->digraph grid)
        paths  (mapv #(shortest-path-dist graph % end) starts)]
    (apply min (remove neg? paths))))
