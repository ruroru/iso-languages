(ns jj.iso.languages-test
  (:require [clojure.test :refer [are deftest]]
            [jj.iso.languages :as core]))

(deftest get-language
  (are [language expected-value] (= expected-value (core/get-language language))
                                 :chuvash {:alpha2   "cv"
                                           :alpha3-b "chv"
                                           :english  "Chuvash"
                                           :french   "Tchouvache"}

                                 :swedish {:alpha2   "sv"
                                           :alpha3-b "swe"
                                           :english  "Swedish"
                                           :french   "Suédois"})

  (are [language] (= (nil? (core/get-language language)))
                  :country
                  {}
                  1
                  nil))

(deftest detect-languages
  (are [language expected-value] (= expected-value (core/detect-language language))
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

  (are [language] (= (nil? (core/detect-language language)))
                  :country
                  {}
                  1
                  nil))