# com.indoles.clj.mapRefWatcher

Small library to watch references that contain a map.
Uses add-watch to add a watch to a reference, that is assumed to contain a map.
Different functions are called for additions, removals and changes to the map contents.

## Usage

  (let [m-ref (ref  {:one 1 :two 2 :three 3})
        changed (atom nil)
        added (atom nil)
        removed (atom nil)]
    (watch m-ref :test
         (fn [a] (swap! added (fn [& args] a)))
         (fn [r] (swap! removed (fn [& args] r)))
         (fn [c] (swap! changed (fn [& args] c)))))

## License

Copyright © 2012 Esa Laine

Distributed under the Eclipse Public License, the same as Clojure.
