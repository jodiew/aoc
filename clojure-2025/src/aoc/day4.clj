(ns aoc.day4
  (:require
   [aoc.core :refer [**debug?** generate-adj-grid generate-grid split-to-grid]]
   [clojure.string :as str]))

(defn get-roll [rolls [row col]] ((nth rolls row) col))

(defn is-roll?
  ([rolls] (fn [roll] (is-roll? rolls roll)))
  ([rolls roll] (= "@" (get-roll rolls roll))))

(defn is-accessable? [num-adj] (< num-adj 4))

(def rolls-state (atom nil))

(defn grid->string [grid] (str/join "\n" (map #(str/join %) grid)))

(defn log-rolls [input]
  (if **debug?** (println (str "Rolls state:\n" (grid->string @rolls-state))) nil)
  input)

(defn accessable-rolls
  ([rolls] (fn [accessable roll] (accessable-rolls rolls accessable roll)))
  ([rolls accessable [row col]]
   (if (is-roll? rolls [row col])
     (let [rows-count (dec (count rolls))
           cols-count (dec (count (first rolls)))
           adjacent-rolls (generate-adj-grid rows-count cols-count [row col])
           is-roll? (is-roll? rolls)
           num-adj (count (filter is-roll? adjacent-rolls))]
       (if (is-accessable? num-adj) (conj accessable [row col]) accessable))
     accessable)))

(defn find-accessable-rolls
  ([rolls] (let [grid-indicies (generate-grid (count rolls) (count (first rolls)))
                 accessable-rolls (accessable-rolls rolls)]
             (reduce accessable-rolls () grid-indicies))))

(defn debug-rolls [rolls]
  (if **debug?** (run! (fn [roll] (swap! rolls-state assoc-in roll "x")) rolls) nil)
  rolls)

(defn set-debug-rolls [rolls]
  (reset! rolls-state (vec rolls))
  rolls)

(defn total-accessable-rolls [data]
  (->> data
       split-to-grid
       set-debug-rolls
       log-rolls
       find-accessable-rolls
       debug-rolls
       log-rolls
       count))
