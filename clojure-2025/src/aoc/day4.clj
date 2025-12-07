(ns aoc.day4
  (:require
   [aoc.core :refer [**debug?** generate-adj-grid generate-grid iinc
                     split-to-grid]]
   [clojure.set :as set]
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

(defn grid-to-set [grid]
  (set (for [row (range (count grid))
             col (range (count (first grid)))
             :when (= "@" ((nth grid row) col))]
         [row col])))

(def debug-state (atom {:size nil
                        :rolls nil}))

(defn debug-set-size [grid]
  (if **debug?**
    (swap! debug-state assoc :size [(count grid) (count (first grid))])
    nil)
  grid)

(defn debug-set-rolls [rolls]
  (if **debug?**
    (swap! debug-state assoc :rolls rolls)
    nil)
  rolls)

(defn debug-print-set [rolls-set]
  (if **debug?**
    (let [grid-coords (apply generate-grid (@debug-state :size))
          set->string (for [coor grid-coords]
                        (if (rolls-set coor) "@" "."))]
      ())
    nil)
  rolls-set)

(defn removable-roll
  ([rolls] (fn [acc roll] (removable-roll rolls acc roll)))
  ([rolls acc [roll-row roll-col]]
   (let [adj (for [row (range (dec roll-row) (iinc roll-row))
                   col (range (dec roll-col) (iinc roll-col))
                   :when (and (not= [row col] [roll-row roll-col])
                              (contains? rolls [row col]))]
               [row col])]
     (if (< (count adj) 4)
       (conj acc [roll-row roll-col])
       acc))))


(defn find-removable-rolls [{rolls :rolls removed-count :removed-count}]
  (let [removed (reduce (removable-roll rolls) #{} rolls)]
    {:removed removed
     :rolls (set/difference rolls removed)
     :removed-count (+ removed-count (count removed))}))

(defn setup-state [rolls]
  {:removed #{[-1 -1]}
   :rolls rolls
   :removed-count 0})

(defn recur-remove-roll [init-state]
  (loop [state init-state]
    (if (empty? (state :removed))
      (state :removed-count)
      (recur (find-removable-rolls state)))))

(defn total-removed-rolls [data]
  (->> data
       split-to-grid
       grid-to-set
       setup-state
       recur-remove-roll))
