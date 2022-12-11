(ns advent.2022.day-9
  (:require [advent.utils :refer [chess-adjacent? lines parse-int read-file
                                  words]]))

(def parse-direction {"L" :left "R" :right "U" :up "D" :down})
(defn parse-line [string]
  (let [[raw-direction raw-number] (words string)]
    (repeat (parse-int raw-number) (parse-direction raw-direction))))
(defn parse-input [string] (mapcat parse-line (lines string)))

(def sample-input-a (parse-input "R 4\nU 4\nL 3\nD 1\nR 4\nD 1\nL 5\nR 2"))
(def sample-input-b (parse-input "R 5\nU 8\nL 8\nD 3\nR 17\nD 10\nL 25\nU 20"))
(def input (parse-input (read-file "2022/day-9.txt")))

(defn move-point [[x y] direction]
  (case direction
    :left  [(dec x) y]
    :right [(inc x) y]
    :down  [x (dec y)]
    :up    [x (inc y)]))

(defn relative-position [[x y] [relative-to-x relative-to-y]]
  (cond-> []
    (< x relative-to-x) (conj :left)
    (> x relative-to-x) (conj :right)
    (< y relative-to-y) (conj :down)
    (> y relative-to-y) (conj :up)))

(defn follow [point-to-follow point]
  (let [relative-positions (relative-position point point-to-follow)
        opposite-direction {:left :right :right :left :up :down :down :up}
        moves-to-follow    (map opposite-direction relative-positions)]
    (if-not (chess-adjacent? point point-to-follow)
      (reduce move-point point moves-to-follow)
      point)))

(defn all-follow [point-to-follow [point & points]]
  (when point
    (let [new-point  (follow point-to-follow point)
          new-points (all-follow new-point points)]
      (conj new-points new-point))))

(defn move-rope [[head & knots] direction]
  (let [new-head  (move-point head direction)
        new-knots (all-follow new-head knots)]
    (conj new-knots new-head)))

(defn move-rope-and-record-tail-path [[rope tail-path] direction]
  (let [new-rope (move-rope rope direction)
        new-tail (last new-rope)]
    [new-rope (conj tail-path new-tail)]))

(defn tail-path [rope steps] (second (reduce move-rope-and-record-tail-path [rope #{}] steps)))

(defn rope-of-size [size] (repeat size [0 0]))
(defn part-a [steps] (count (tail-path (rope-of-size 2) steps)))
(defn part-b [steps] (count (tail-path (rope-of-size 10) steps)))
