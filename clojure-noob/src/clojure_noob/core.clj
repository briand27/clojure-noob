(ns clojure-noob.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "I'm a little teapot!"))

(println "Cleanliness is next to godliness")

(defn train
  []
  (println "choo choo!"))

(+ 1 2 3 4)

(+ 1 (* 2 3) 4)

(str "It was the panda " "in the library " "with a dust buster")

(if false
  2)

(defn add-x-y
  "Return X + Y or X + 1 if Y is not specified"
  ([x]
   (add-x-y x 1))
  ([x y]
   (+ x y)))

(defn announce-lat-lon
  "Announce the latitude and longitude from a MAP"
  [{lat :lat lon :lon}]
  (print (str "Treasure is at\n" "lat: " lat "\nlon: " lon)))


