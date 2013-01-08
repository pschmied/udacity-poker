(ns poker.core
  (:use [clojure.string :only (split)]))

(defn straight
  [ranks]
  "Detect whether a hand rank contains a straight"
  (and (= 4 (- (apply max ranks) (apply min ranks)))
       (= 5 (count (set ranks)))))

(defn n-of-kind
  [n ranks]
  ;; SO rescue from mobyte http://goo.gl/fnRDl
  "Detect whether a hand rank contains n of a kind, returning a list of
  card values that occur exactly n times"
  (let [cards (keys (into {} (filter #(= (second %) n) (frequencies ranks))))]
    (if cards (into [] cards) [nil])))

(defn common-flush
  [hand]
  "Detect whether a hand contains a flush, returning true or false"
  (= 1 (count (set (map last hand)))))

(defn card-ranks
  [hand]
  "Return a list of the ranks, sorted highest first"
  (let [ranks (reverse (sort (map #(.indexOf "--23456789TJQKA" %)
                                  (map #(str (first %)) hand))))]
    (if (= ranks [14 5 4 3 2])
      [5 4 3 2 1]
      (into [] ranks))))

(defn hand-rank
  "Score a hand, returning a comparable vector of values representing
  the hand"
  [hand]
  [(and (straight (card-ranks hand)) ; straight flush
        (common-flush hand))

   (n-of-kind 4 (card-ranks hand)) ; 4 of a kind

   [(n-of-kind 3 (card-ranks hand))     ; full house
    (n-of-kind 2 (card-ranks hand))]

   (common-flush hand) ; flush
   (straight (card-ranks hand)) ; straight
   (n-of-kind 3 (card-ranks hand)) ; 3 of a kind
   (n-of-kind 2 (card-ranks hand)) ; 2 pair or one pair
   (card-ranks hand)]) ; the final tie-breaker

(defn poker
  "Return the best hand(s) from a seq of hands"
  [hands]
  (apply all-max-key hand-rank hands))

(defn all-max-key
  ;; Rescued by Michal Marczyk http://goo.gl/LYl5w
  "Returns a vector of x for which (k x), arbitrated by compare, are greatest."
  [k x & xs]
  (reduce (fn [ys x]
            (let [c (compare (k x) (k (peek ys)))]
              (cond
               (pos? c) [x]
               (neg? c) ys
               :else (conj ys x))))
          [x]
          xs))

(defn best-hand
  [hand]
  "From an arbitrarily large hand, return the best 5 card hand."
  ;; I'll get around to it
  )

(defn best-wild-hand
  [hand]
  "From an arbitrarily large hand, return the best 5 card hand, red and
  black jokers wild."
  ;; I'll get around to it
  )