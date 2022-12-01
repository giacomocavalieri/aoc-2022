(ns advent.core
  (:require [advent.days.day-1 :as day]))

(defn -main [& _args]
  (println (day/part-a day/input))
  (println (day/part-b day/input)))