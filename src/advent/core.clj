(ns advent.core
  (:require [kaocha.repl]
            [kaocha.watch]
            [kaocha.result :refer [failed?]]
            [java-time.api :as jt]))

(def now (jt/local-date))
(def current-day (jt/as now :day-of-month))
(def current-year (jt/as now :year))
(def config (kaocha.repl/config))
(defn id-keyword-from-day-year [day year] (keyword (str "day-" day "-year-" year)))

(defn test-all []
  (let [test-result (kaocha.repl/run-all)]
    (when (failed? test-result) (System/exit 1))))
(defn watch-all [] (kaocha.watch/run config))
(defn watch-day [day year]
  (let [id-keyword (id-keyword-from-day-year day year)
        new-config (assoc-in config [:kaocha/tests 0 :kaocha.filter/focus-meta] [id-keyword])]
    (kaocha.watch/run new-config)))

(defn -main [mode & [raw-day raw-year]]
  (let [day  (if (nil? raw-day) current-day raw-day)
        year (if (nil? raw-year) current-year raw-year)]
    (case mode
      "watch-all" (watch-all)
      "watch-day" (watch-day day year)
      "test-all"  (test-all))))
