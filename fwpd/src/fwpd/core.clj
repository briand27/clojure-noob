(ns fwpd.core)

(def filename "suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (glitter-filter 3 (mapify (parse (slurp filename)))))

(map :name (glitter-filter 3 (mapify (parse (slurp filename)))))

(def append (partial conj (mapify (parse (slurp filename)))))

(append {:name "Nosferatu" :glitter-index 1000})
(append {:name "Nosferatu" :glitter-index 1000}
        {:name "Dracula" :glitter-index 1000})

(defn validate
  "Validates ROW before appending"
  [& rows]
  (apply append (filter #(and (:name %) (:glitter-index %)) rows)))

(validate {:name "Nosferatu" :glitter-index 1000}
          {:name "Dracula" :glitter-index 1000}
          {:name "Invalid Entry"})

(defn vampires-to-csv
  [vampire-map]
  (clojure.string/join
   "\n"
   (map #(str (:name %) "," (:glitter-index %)) vampire-map)))

(vampires-to-csv (validate {:name "Nosferatu" :glitter-index 1000}
                           {:name "Dracula" :glitter-index 1000}
                           {:name "Invalid Entry"}))
