(ns advent.2017.day-16
  (:require [advent.utils :refer [parse-int read-file split-on swap take-until]]
            [clojure.core.match :refer [match]]))

(defn parse-instruction [raw-instruction]
  (let [raw-op   (first raw-instruction)
        raw-args (apply str (rest raw-instruction))
        parse-chars (fn [raw-args] (mapv first (split-on #"/" raw-args)))
        parse-ints  (fn [raw-ints] (mapv parse-int (split-on #"/" raw-ints)))]
    (case raw-op
      \s  [:spin (parse-int raw-args)]
      \x `[:exchange ~@(parse-ints raw-args)]
      \p `[:partner ~@(parse-chars raw-args)])))

(defn parse-input [string] (mapv parse-instruction (split-on #"," string)))
(def input (parse-input (read-file "2017/day-16.txt")))

(def starting-programs [\a \b \c \d \e \f \g \h \i \j \k \l \m \n \o \p])
(def n-programs (count starting-programs))

(defn apply-instruction [programs instruction]
  (match [instruction]
    [[:spin n]] (let [[lower upper] (split-at (- n-programs n) programs)] (vec (concat upper lower)))
    [[:partner prog1 prog2]] (swap programs (.indexOf programs prog1) (.indexOf programs prog2))
    [[:exchange pos1 pos2]] (swap programs pos1 pos2)))

(defn dance [instructions programs] (reduce apply-instruction programs instructions))
(defn part-a [instructions] (apply str (dance instructions starting-programs)))
(defn part-b [instructions]
  (let [[_ & other-states :as states] (iterate #(dance instructions %) starting-programs)
        period     (count (take-until #(not= % starting-programs) other-states))
        iterations (rem 1000000000 period)]
    (apply str (nth states iterations))))
