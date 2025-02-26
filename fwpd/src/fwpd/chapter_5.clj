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

(def character-traits {:attributes {:strength 14
                                    :dexterity 8
                                    :constitution 8
                                    :wisdom 12
                                    :charisma 8
                                    :intelligence 10}})

((comp :intelligence :attributes) character-traits)

(defn attr
  [attribute]
  ((comp attribute :attributes) character-traits))

(attr :intelligence)
(attr :charisma)

(+ 1 2)

(+ (* 3 4 6) 5 7)

(defn my-assoc-in
  "When applied to a map, associates v as last value in nested ks"
  [m [k & ks] v]
  (if (empty? ks)
    (assoc m k v)
    (assoc m k (my-assoc-in m ks v))))

(my-assoc-in {} [1 2 3] 4)

(empty? nil)

(assoc-in {} [] 2)
(my-assoc-in {} [] 2)
