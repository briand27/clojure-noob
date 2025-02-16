(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])

(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(defn symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  ;; initialize loop with variables
  ;; remaining-asym-parts = asym-body-parts
  ;; final-body-parts = []
  (loop [remaining-asym-parts asym-body-parts final-body-parts []]
    (if (empty? remaining-asym-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-parts]
        ;; "call" loop again with
        ;; remaining-body-parts = remaining
        ;; final-body-parts = [final-body-parts] + part + (matching-part part)
        (recur remaining
               (into final-body-parts
                     (set [part (matching-part part)])))))))

(defn briand-reduce
  ([fun [first & remaining]]
   (briand-reduce fun first remaining))
  ([fun initial-value [first & remaining :as args]]
   (if (empty? args)
     initial-value
     (let [new-value (fun initial-value first)]
       (briand-reduce fun new-value remaining)))))

(defn better-symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set [part (matching-part part)])))
          []
          asym-body-parts))

(defn hit
  [asym-body-parts]
  (let [sym-parts (better-symmetrize-body-parts asym-body-parts)
        body-parts-size-sum (reduce + (map :size sym-parts))
        target (rand body-parts-size-sum)]
    (loop [[part & remaining] sym-parts
           accumulated-size (:size part)]
      (if (> accumulated-size target)
        part
        (recur remaining (+ accumulated-size (:size (first remaining))))))))

(defn add-100
  [x]
  (+ x 100))

(str "hello " "world")
(vector 1 2 3)
(list 1 2 3)
(hash-map :a 1 :b 2)
(hash-set 1 1 2 3)

(defn dec-maker
  [x]
  #(- % x))

(def dec10 (dec-maker 10))

(dec10 40)

(defn mapset
  [fun args]
  (into #{} (map fun args)))

(map inc [1 1 2 2])

(mapset inc [1 1 2 2])

(defn alien-parts
  "Returns N alien parts if PART is an appropriate body part.

  For example
  (alien-parts {:name 'arm-1' :size 5} 3) =>
  #{{:name 'arm-1' :size 5},
    {:name 'arm-2' :size 5},
    {:name 'arm-3' :size 5}}

  If inappropriate, return PART as the only element in a set."
  [part n]
  (let [{name :name size :size} part]
    (if (re-find #"-1$" name)
      (loop [i 0
             final-parts #{}]
        (if (< i n)
          (recur (inc i)
                 (conj final-parts
                       {:name (clojure.string/replace
                               name #"-1$" (str "-" i))
                        :size size}))
          final-parts))
      #{part})))

(defn symmetrize-alien-body-parts
  [asym-body-parts n]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (alien-parts part n)))
          []
          asym-body-parts))

(alien-parts {:name "hand-1" :size 5} 3)

(def asym-alien-body-parts [{:name "head-1" :size 3}
                            {:name "torso" :size 5}
                            {:name "arm-1" :size 3}])

(symmetrize-alien-body-parts asym-alien-body-parts 5)

(defn destructure-map-arg
  [{a :a b :b} c]
  (clojure.string/join " " [a b c]))

(destructure-map-arg {:a "a" :b "b"} "c")
