(ns com.indoles.clj.mapRefWatcher.core-test
  (:use clojure.test
        com.indoles.clj.mapRefWatcher.core))

(deftest a-test
  (let [m-ref (ref  {:one 1 :two 2 :three 3})
        changed (atom nil)
        added (atom nil)
        removed (atom nil)]
    (watch m-ref :test
         (fn [a] (swap! added (fn [& args] a)))
         (fn [r] (swap! removed (fn [& args] r)))
         (fn [c] (swap! changed (fn [& args] c))))
    (dosync
     (alter m-ref assoc :two 42 :three 3 :four 4)
     (alter m-ref dissoc :one))
    (testing "Testing that :four was correctly added."
      (is (= (:four @added) 4)))
    (testing "Testing that :one was correctly removed."
      (is (= (:one @removed) 1)))
    (testing "Testing associating new values for keys: :two :three and :four."
      (is (and (= (:two @changed) 42)
               (= (:four @added) 4))))))