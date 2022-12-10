(ns advent.2022.extra.day-5
  (:require [advent.2022.day-5 :refer [move-crates]]
            [advent.utils :refer [transpose unlines unwords]]
            [clojure.string :refer [join]]))

(defn random-stack [max-height]
  (let [uppercases    "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        actual-height (rand-int max-height)]
    (repeatedly actual-height #(rand-nth uppercases))))

(defn make-starting-stacks [max-height message]
  (let [message-size (count message)
        stacks       (repeatedly message-size #(random-stack max-height))]
    (vec (map conj stacks message))))

(defn random-move [stacks]
  (loop []
    (let [n-stacks      (count stacks)
          from-index    (rand-int n-stacks)
          from-stack    (get stacks from-index)
          to-index      (rand-int n-stacks)
          n-crates-from (count from-stack)
          n-crates      (inc (rand-int n-crates-from))
          valid-move?   (and (not= from-index to-index) (not-empty from-stack))]
      (if valid-move? [n-crates from-index to-index] (recur)))))

(defn generate-input [n-steps max-height message rearrange]
  (let [starting-stacks (make-starting-stacks max-height message)]
    (loop [remaining-steps n-steps
           stacks          starting-stacks
           moves           ()]
      (if (<= remaining-steps 0) [stacks moves]
          (let [[n-crates from to] (random-move stacks)
                new-stacks   (move-crates stacks n-crates from to rearrange)
                inverse-move [n-crates to from]]
            (recur (dec remaining-steps) new-stacks (conj moves inverse-move)))))))

(defn serialize-move [[n-crates from to]] (str "move " n-crates " from " (inc from) " to " (inc to)))
(defn pad-with-nil [desired-size coll]
  (let [coll-size (count coll)
        pad-size  (- desired-size coll-size)
        padding   (repeat pad-size nil)]
    (concat padding coll)))

(defn serialize-block [block] (if (nil? block) "   " (str "[" block "]")))
(defn serialize-line [line] (unwords (map serialize-block line)))
(defn generate-bottom-line [n-stacks]
  (let [numbers (map str (map inc (range n-stacks)))
        line    (join "   " numbers)]
    (str " " line)))
(defn serialize-stacks [stacks]
  (let [max-height    (apply max (map count stacks))
        padded-stacks (map #(pad-with-nil max-height %) stacks)
        transposed    (apply transpose padded-stacks)
        serialized-stacks (unlines (map serialize-line transposed))
        bottom-line   (generate-bottom-line (count stacks))]
    (str serialized-stacks "\n" bottom-line)))

(defn serialize-input [[stacks moves]]
  (let [serialized-moves  (unlines (map serialize-move moves))
        serialized-stacks (serialize-stacks stacks)]
    (str serialized-stacks "\n\n" serialized-moves)))
