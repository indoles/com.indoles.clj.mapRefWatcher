(ns com.indoles.clj.mapRefWatcher.core
  (:require [clojure.set :as s]))

; This function is adapted from: http://stackoverflow.com/questions/3387155/difference-between-two-maps
(defn- map-difference
  [m1 m2]
  (let [ks1 (set (keys m1))
        ks2 (set (keys m2))
        ks1-ks2 (s/difference ks1 ks2)
        ks2-ks1 (s/difference ks2 ks1)
        ks1*ks2 (s/intersection ks1 ks2)]
    [(select-keys m1 ks1-ks2)
     (select-keys m2 ks2-ks1)
     (select-keys m1 (remove (fn [k] (= (m1 k) (m2 k))) ks1*ks2))]))

(defn- watcher
  [added removed changed watch-key reference old-state new-state]
  (let [ [added-map removed-map changed-map]
         (map-difference new-state old-state)]
    (removed removed-map)
    (changed changed-map)
    (added added-map)))

(defn watch
  "watch a ref containing a map, call a function with items removed, added and/or changed
  Uses add-watch."
  [watched-ref watch-key added removed changed]
  (add-watch watched-ref watch-key (partial watcher added removed changed)))
