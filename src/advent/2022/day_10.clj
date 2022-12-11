(ns advent.2022.day-10
  (:require [advent.utils :refer [lines parse-int read-file words]]
            [clojure.core.match :refer [match]]))

(defn parse-line [string]
  (match [(words string)]
    [["addx" raw-n]] (parse-int raw-n)
    [["noop"]] :noop))
(defn parse-input [string] (mapv parse-line (lines string)))
(def sample-input (parse-input (read-file "2022/day-10-sample.txt")))
(def input (parse-input (read-file "2022/day-10.txt")))

(defn add-op-cycles [values op]
  (let [current-register (peek values)]
    (match [op]
      [:noop] (conj values current-register)
      [addx]  (let [new-register (+ addx current-register)]
                (conj values current-register new-register)))))

(defn cycles [ops]
  (let [starting-register-value 1]
    (reduce add-op-cycles [0 starting-register-value] ops)))

(defn part-a [ops]
  (let [cycles          (cycles ops)
        signal-strength (fn [cycle] (* cycle (get cycles cycle)))]
    (apply + (mapv signal-strength [20 60 100 140 180 220]))))

(defn lit? [pixel sprite-center] (<= (dec sprite-center) pixel (inc sprite-center)))
(defn draw-pixel [pixel sprite-center] (if (lit? pixel sprite-center) \█ \░))
(defn draw-row [row] (apply str (map-indexed draw-pixel row)))
(defn part-b [ops]
  (let [cycles (rest (cycles ops))
        rows   (partition 40 cycles)]
    (map draw-row rows)))
