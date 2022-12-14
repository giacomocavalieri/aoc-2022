(ns advent.2022.day-14
  (:require [advent.utils :refer [conj-non-nil lines not-contains? parse-int
                                  points-of-segment read-file
                                  repeat-until-convergence split-on]]
            [clojure.set :as set]))

(defn parse-point [raw-point] (mapv parse-int (split-on #"," raw-point)))
(defn parse-line [raw-line]
  (let [points (mapv parse-point (split-on #" -> " raw-line))
        pairs  (partition 2 1 points)]
    (mapcat points-of-segment pairs)))
(defn parse-input [string] (set (mapcat parse-line (lines string))))
(def sample-input (parse-input "498,4 -> 498,6 -> 496,6\n503,4 -> 502,4 -> 502,9 -> 494,9"))
(def input (parse-input (read-file "2022/day-14.txt")))

(defn move-down       [[x y]] [x       (inc y)])
(defn move-down-left  [[x y]] [(dec x) (inc y)])
(defn move-down-right [[x y]] [(inc x) (inc y)])

(defn fall-one-down [cave highest-y sand-grains [_x y :as sand-grain]]
  (when sand-grain
    (let [free?      (fn [point] (every? #(not-contains? % point) [cave sand-grains]))
          down       (move-down sand-grain)
          down-left  (move-down-left sand-grain)
          down-right (move-down-right sand-grain)]
      (cond
        (>= y highest-y)   nil
        (free? down)       down
        (free? down-left)  down-left
        (free? down-right) down-right
        :else              sand-grain))))

(defn add-new-sand-grain [cave highest-y sand-grains]
  (let [sand-source     [500 0]
        step-sand-grain (fn [sand-grain] (fall-one-down cave highest-y sand-grains sand-grain))
        new-sand-grain  (repeat-until-convergence step-sand-grain sand-source)]
    (conj-non-nil sand-grains new-sand-grain)))

(defn let-the-sand-pour [cave] ; mandatory Umbrella citation
  (let [highest-y (apply max (map second cave))]
    (repeat-until-convergence #(add-new-sand-grain cave highest-y %) #{})))

(defn add-floor [cave]
  (let [highest-y   (apply max (map second cave))
        base-y      (+ 2 highest-y)
        base-points (points-of-segment [[-1000 base-y] [1000 base-y]])]
    (set/union cave (set base-points))))

(defn part-a [cave] (count (let-the-sand-pour cave)))
(defn part-b [cave] (count (let-the-sand-pour (add-floor cave))))
