(ns advent.core
  (:require [advent.2022.day-1]
            [advent.2022.day-2]
            [kaocha.repl]
            [kaocha.watch]
            [java-time.api :as jt]))

(def now (jt/local-date))
(def current-day (jt/as now :day-of-month))
(def current-year (jt/as now :year))
(def config (kaocha.repl/config))
(defn id-keyword-from-day-year [day year] (keyword (str "day-" day "-year-" year)))

(defn run-all [] (kaocha.repl/run-all))
(defn watch-all [] (kaocha.watch/run config))
(defn watch-day [day year]
  (let [id-keyword (id-keyword-from-day-year day year)
        new-config (assoc-in config [:kaocha/tests 0 :kaocha.filter/focus-meta] [id-keyword])]
    (kaocha.watch/run new-config)))

(defn -main [mode & [raw-day raw-year]]
  (let [day (if (nil? raw-day) current-day raw-day)
        year (if (nil? raw-year) current-year raw-year)]
    (case mode
      "watch-all" (watch-all)
      "watch-day" (watch-day day year)
      "run-all"   (run-all))))
