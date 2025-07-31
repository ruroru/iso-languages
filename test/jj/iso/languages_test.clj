(ns jj.iso.languages-test
  (:require [clojure.test :refer [are deftest]]
            [jj.iso.languages :as iso-languages]))

(deftest get-language
  (are [language expected-value] (= expected-value (iso-languages/get-language language))
                                 :chuvash {:alpha2   "cv"
                                           :alpha3-b "chv"
                                           :english  "Chuvash"
                                           :french   "Tchouvache"}

                                 :swedish {:alpha2   "sv"
                                           :alpha3-b "swe"
                                           :english  "Swedish"
                                           :french   "Suédois"})

  (are [language] (= (nil? (iso-languages/get-language language)))
                  :country
                  {}
                  1
                  nil))

(deftest detect-languages
  (are [language expected-value] (= expected-value (iso-languages/detect-language language))
                                 "got" :gothic
                                 "GOT" :gothic
                                 "tsi" :tsimshian
                                 "tsi" :tsimshian
                                 "de" :german
                                 "ces" :czech
                                 "Timne" :timne
                                 "timne" :timne
                                 "TIMNE" :timne
                                 "gothique" :gothic
                                 "Gothique" :gothic
                                 "GOTHIQUE" :gothic

                                 )

  (are [language] (nil? (iso-languages/detect-language language))

bytes                  :country
                  {}
                  1
                  nil))


(deftest test-detect-lang-with-default
  (are [language-code default-language expected-language]
    (= expected-language (iso-languages/detect-language language-code default-language))
    "got" :english :gothic
    "GOT" :english :gothic
    "tsi" :english :tsimshian
    "aaa" :english :english
    "not-existing" :german :german))
