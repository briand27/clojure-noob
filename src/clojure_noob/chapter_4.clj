;; note sum is different than + since it takes a seq
;; i.e. (sum [1 2 3]) == (+ 1 2 3)
(def sum #(reduce + %))
(def avg #(/ (sum %) (count %)))
(defn stats
  [coll]
  (map #(% coll) [sum count avg]))

(stats [3 4 10])

(defn map-reduce-impl-simple
  "Implement map with reduce function.

  Cannot take multiple collections as input."
  [fun coll]
  (reduce (fn [new-coll value]
            (conj new-coll (fun value)))
          []
          coll))

(defn equal-length-colls
  "Return true if all colls are of equal length, false otherwise."
  [& colls]
  (if (reduce (fn [existing-count-or-false coll]
                (if existing-count-or-false
                   (if (= existing-count-or-false (count coll))
                     existing-count-or-false)))
               (count (first colls))
               colls)
    true
    false))

(equal-length-colls [1 2 3] [2 3 4])
(equal-length-colls [1 2 3] [2 4])
(equal-length-colls [] [2 3 4])
(equal-length-colls [] [])

(= 0 false)
(if 0 true false)
(if nil true false)

;; (defn map-reduce-impl
  ;; "Implement map with reduce function."
  ;; [fun & colls]
  ;; (reduce (fn [new-coll value]
            ;; (conj new-coll (fun value)))
          ;; []
          ;; coll))

(map inc [1 2 3])
(map-reduce-impl-simple inc [1 2 3])

(map + [1 2 3] [3 2 1])
;; (map-reduce-impl + [1 2 3] [3 2 1])

(nth [1 2 3] 0)

(count [2 3])

