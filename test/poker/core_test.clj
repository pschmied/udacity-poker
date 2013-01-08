(ns poker.core-test
  (:use clojure.test
        [clojure.string :only (split)]
        poker.core))

(deftest poker-test
  (testing "FIXME, I fail"
    (let [sf1 (split "6C 7C 8C 9C TC" #"\s+")
          sf2 (split "6D 7D 8D 9D TD" #"\s+")
          fk (split "9D 9H 9S 9C 7D" #"\s+")
          fh (split "TD TC TH 7C 7D"#"\s+")]
      (is (= sf1 (poker [sf1 fk fh])))
      (is (= fk (poker [fh fk])))
      (is (= fh (poker [fh fh])))
      (is (= fh (poker [fh])))
      (is (= sf1 (poker (into [sf1] (repeat 99 fh)))))
      (is (= [sf1 sf2] (poker [sf1 sf2 fk fh]))))))

;; (def hands [["6C" "7C" "8C" "9C" "TC"] ["6D" "7D" "8D" "9D" "TD"] ["9D" "9H" "9S" "9C" "7D"] ["TD" "TC" "TH" "7C" "7D"]])