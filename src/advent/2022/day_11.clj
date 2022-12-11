(ns advent.2022.day-11
  (:require [advent.utils :refer [apply-until-nil divisible? lines max-n
                                  parse-int read-file split-on words]]
            [clojure.core.match :refer [match]]
            [clojure.string :as str]))

(defn parse-items [raw-items]
  (let [[_starting _items & raw-items] (words (str/replace raw-items #"," ""))]
    (mapv parse-int raw-items)))

(defn parse-op [raw-op]
  (let [[_operation _new _= raw-arg1 raw-symbol raw-arg2] (words raw-op)
        f (resolve (symbol raw-symbol))
        arg1 (case raw-arg1 "old" "old" (parse-int raw-arg1))
        arg2 (case raw-arg2 "old" "old" (parse-int raw-arg2))]
    (match [arg1 arg2]
      ["old" "old"] #(f % %)
      ["old" n] #(f % n)
      [n "old"] #(f n %))))

(defn parse-test [raw-test]
  (let [[_test _divisible _by raw-divisor] (words raw-test)]
    (parse-int raw-divisor)))

(defn parse-throw [raw-throw]
  (let [[_if _cond _throw _to _monkey raw-n] (words raw-throw)]
    (parse-int raw-n)))

(defn parse-monkey [raw-monkey]
  (let [[_name raw-items raw-op raw-test raw-if-true raw-if-false] (lines raw-monkey)]
    {:items (parse-items raw-items)
     :op (parse-op raw-op)
     :divisor (parse-test raw-test)
     :if-true (parse-throw raw-if-true)
     :if-false (parse-throw raw-if-false)
     :inspected-items 0}))

(defn parse-input [string] (mapv parse-monkey (split-on #"\n\n" string)))
(def sample-input (parse-input (read-file "day-11-sample.txt")))
(def input (parse-input (read-file "day-11.txt")))

(defn inspect-and-throw-item [monkeys monkey-number decrease-worry-level]
  (let [monkey (get monkeys monkey-number)
        {:keys [items op divisor if-true if-false]} monkey]
    (when-let [[item & other-items] items]
      (let [worry-level (decrease-worry-level (op item))
            throw-to    (if (divisible? worry-level divisor) if-true if-false)]
        (->
         monkeys
         (update-in [throw-to :items] conj worry-level)
         (update-in [monkey-number :inspected-items] inc)
         (assoc-in  [monkey-number :items] other-items))))))

(defn make-turn [monkeys monkey-number decrease-worry-level]
  (apply-until-nil #(inspect-and-throw-item % monkey-number decrease-worry-level) monkeys))

(defn make-round [monkeys decrease-worry-level]
  (let [n-monkeys (count monkeys)]
    (loop [current-monkey 0 state monkeys]
      (if (= current-monkey n-monkeys) state
          (let [new-monkey (inc current-monkey)
                new-state  (make-turn state current-monkey decrease-worry-level)]
            (recur new-monkey new-state))))))

(defn monkey-business-level [monkeys round decrease-worry-level]
  (let [rounds    (iterate #(make-round % decrease-worry-level) monkeys)
        nth-round (nth rounds round)
        inspected-items (mapv :inspected-items nth-round)
        two-most-active (max-n 2 inspected-items)]
    (apply * two-most-active)))

(defn part-a [monkeys]
  (let [decrease-worry-level (fn [worry-level] (quot worry-level 3))]
    (monkey-business-level monkeys 20 decrease-worry-level)))

(defn part-b [monkeys]
  (let [lcm (apply * (mapv :divisor monkeys))
        decrease-worry-level (fn [worry-level] (rem worry-level lcm))]
    (monkey-business-level monkeys 10000 decrease-worry-level)))
