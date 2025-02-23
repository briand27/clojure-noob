(defn two-comp
  [f g]
  (fn [& args]
    (f (apply g args))))

((two-comp inc *) 3 4 5)

(defn my-comp
  ([first]
   (fn [& args]
     (apply first args)))
  ([first & rest]
   (fn [& args]
     (first (apply (apply my-comp rest) args)))))

((my-comp dec inc *) 3 4 5)

