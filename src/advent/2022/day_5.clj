(ns advent.2022.day-5
  (:require [advent.utils :refer [find-first letter? lines parse-int
                                  partition-pad read-file split-on transpose]]))

(defn parse-stack-line [raw-stack-line]
  (let [parse-block (fn [raw-block] (find-first letter? raw-block))
        blocks      (partition-pad 4 \space raw-stack-line)]
    (map parse-block blocks)))

(defn parse-stacks [raw-stacks]
  (let [stacks-transposed (map parse-stack-line (butlast (lines raw-stacks)))
        stacks            (apply transpose stacks-transposed)]
    (vec (map #(remove nil? %) stacks))))

(defn parse-instruction-line [raw-instruction]
  (let [instruction-regex #"move ([0-9]*) from ([0-9]*) to ([0-9]*)"
        instruction-parts (rest (re-find instruction-regex raw-instruction))
        [move from to]    (map parse-int instruction-parts)]
    [move (dec from) (dec to)]))

(defn parse-input [string]
  (let [[raw-stacks raw-instructions] (split-on #"\n\n" string)]
    [(parse-stacks raw-stacks) (map parse-instruction-line (lines raw-instructions))]))

(def sample-input (parse-input "    [D]    \n[N] [C]    \n[Z] [M] [P]\n 1   2   3 \n\nmove 1 from 2 to 1\nmove 3 from 1 to 3\nmove 2 from 2 to 1\nmove 1 from 1 to 2"))
(def input (parse-input (read-file "day-5.txt")))

(defn put-crates-into [stacks to crates] (update-in stacks [to] #(concat crates %)))
(defn take-crates-from [stacks from n-crates]
  (let [from-stack              (nth stacks from)
        [crates new-from-stack] (split-at n-crates from-stack)]
    [(assoc-in stacks [from] new-from-stack) crates]))
(defn move-crates [stacks n-crates from to rearrange]
  (let [[new-stacks crates] (take-crates-from stacks from n-crates)]
    (put-crates-into new-stacks to (rearrange crates))))

(defn follow-instructions [stacks instructions rearrange]
  (let [stacks-to-string   (fn [stacks] (apply str (map first stacks)))
        follow-instruction (fn [stacks [n-crates from to]] (move-crates stacks n-crates from to rearrange))
        final-stacks       (reduce follow-instruction stacks instructions)]
    (stacks-to-string final-stacks)))

(defn part-a [[stacks instructions]] (follow-instructions stacks instructions reverse))
(defn part-b [[stacks instructions]] (follow-instructions stacks instructions identity))
