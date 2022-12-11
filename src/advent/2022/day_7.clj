(ns advent.2022.day-7
  (:require [advent.utils :refer [lines parse-int read-file sum words]]
            [clojure.core.match :refer [match]]))

(defn add-file [file-system path-to-parent file]
  (let [path-to-files (conj path-to-parent :files)]
    (update-in file-system path-to-files conj file)))

(defn add-dir [file-system path-to-parent dir-name]
  (update-in file-system path-to-parent merge {dir-name {}}))

(defn parse-file [[raw-file-size file-name]] [file-name (parse-int raw-file-size)])
(defn parse-input [string]
  (loop [file-system    {}
         current-path   []
         [line & lines] (lines string)]
    (match [(words line)]
      [nil]             (first file-system)
      [["$" "cd" ".."]] (recur file-system (pop current-path) lines)
      [["$" "cd" dir]]  (recur file-system (conj current-path dir) lines)
      [["$" "ls"]]      (recur file-system current-path lines)
      [["dir" dir]]     (recur (add-dir file-system current-path dir) current-path lines)
      [raw-file]        (recur (add-file file-system current-path (parse-file raw-file)) current-path lines))))

(def sample-input (parse-input "$ cd /\n$ ls\ndir a\n14848514 b.txt\n8504156 c.dat\ndir d\n$ cd a\n$ ls\ndir e\n29116 f\n2557 g\n62596 h.lst\n$ cd e\n$ ls\n584 i\n$ cd ..\n$ cd ..\n$ cd d\n$ ls\n4060174 j\n8033020 d.log\n5626152 d.ext\n7214296 k"))
(def input (parse-input (read-file "2022/day-7.txt")))

(defn subdirs [[_dir-name dir-contents]]
  (let [is-subdir? (fn [[name _content]] (not= name :files))]
    (filter is-subdir? dir-contents)))

(defn size-of-dir [[_dir-name dir-contents :as dir]]
  (let [size-of-file    (fn [[_file-name file-size]] file-size)
        size-of-files   (sum (map size-of-file (:files dir-contents)))
        size-of-subdirs (sum (map size-of-dir (subdirs dir)))]
    (+ size-of-files size-of-subdirs)))

(defn flatten-dirs [dir]
  (let [flattened-subdirs (mapcat flatten-dirs (subdirs dir))]
    (conj flattened-subdirs dir)))

(defn sizes-of-all-dirs [dir]
  (map size-of-dir (flatten-dirs dir)))

(defn part-a [root-dir]
  (let [too-big? (fn [size] (> size 100000))]
    (->> (sizes-of-all-dirs root-dir) (remove too-big?) sum)))

(defn space-to-free [root-dir]
  (let [total-space  70000000
        needed-space 30000000
        used-space   (size-of-dir root-dir)
        free-space   (- total-space used-space)]
    (- needed-space free-space)))

(defn part-b [root-dir]
  (let [space-to-free   (space-to-free root-dir)
        enough-to-free? (fn [size] (>= size space-to-free))]
    (->> (sizes-of-all-dirs root-dir) (filter enough-to-free?) (apply min))))
