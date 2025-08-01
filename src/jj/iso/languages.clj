(ns jj.iso.languages
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.string :as str])
  (:import (java.io InputStream InputStreamReader PushbackReader)
           (java.util WeakHashMap)
           (java.util.concurrent ConcurrentHashMap)))

(def ^:private get-language-cache (ConcurrentHashMap. 1))
(def ^:private detect-language-cache (ConcurrentHashMap. 1))
(def ^:private temp-cache (WeakHashMap. 1))

(defn- get-all-languages []
  (let [all-countries (.get ^WeakHashMap temp-cache :all)]
    (if (some? all-countries)
      all-countries
      (with-open [is ^InputStream (.openStream (io/resource "jj/iso639/languages.edn"))
                  isr ^InputStreamReader (InputStreamReader. is)
                  pr ^PushbackReader (PushbackReader. isr)]
        (let [computed-countries (edn/read pr)]
          (locking temp-cache
            (do
              (.put ^WeakHashMap temp-cache :all computed-countries)
              computed-countries)))))))

(defn get-language [language]
  "Retrieves language information for the specified language identifier.

  Parameters:
  language - A language identifier (string). Can be an ISO 639-1 alpha-2 code (e.g., \"en\"),
  ISO 639-2 alpha-3 code (e.g., \"eng\"), or language name (e.g., \"English\").

  Returns:
  A map containing language information with the following keys:
  :alpha2   - ISO 639-1 two-letter language code (e.g., \"en\")
  :alpha3-b - ISO 639-2/B three-letter bibliographic code (e.g., \"eng\")
  :alpha3-t - ISO 639-2/T three-letter terminologic code (e.g., \"eng\")
  :english  - English name of the language (e.g., \"English\")
  :french   - French name of the language (e.g., \"anglais\")

  Returns nil if:
  - The language parameter is nil
  - No matching language is found"
  (when (not (nil? language))
    (when (not (.containsKey ^ConcurrentHashMap get-language-cache language))
      (let [all-languages (get-all-languages)
            expected-language (get all-languages language)]
        (when expected-language
          (.put ^ConcurrentHashMap get-language-cache language (get all-languages language)))))
    (.get ^ConcurrentHashMap get-language-cache language)))


(defn detect-language
  "Detects and returns the standardized language keyword for a given language identifier.

   Takes a string representing a language identifier and returns the corresponding
   keyword. Supports multiple input formats including language names in various
   languages and ISO 639 codes (alpha-2, alpha-3-t, alpha-3-b).
   Optionally a default value can be provided.

     Returns nil if:
     - The language parameter is nil
     - No matching language is found"
  ([language]
   (when (not (nil? language))
     (let [lower-case-language (str/lower-case language)
           iso639-keyword (keyword lower-case-language)]
       (when (not (.containsKey ^ConcurrentHashMap detect-language-cache iso639-keyword))
         (let [needed-lang (some (fn [[_ rest]]
                                   (when (some #(= lower-case-language (str/lower-case %))
                                               (vals (select-keys rest [:english :french :alpha2 :alpha3-t :alpha3-b])))
                                     [_ rest]))
                                 (get-all-languages))]
           (when (some? needed-lang)
             (.put ^ConcurrentHashMap detect-language-cache iso639-keyword (first needed-lang)))))
       (.get ^ConcurrentHashMap detect-language-cache iso639-keyword))))
  ([language default]
   (let [lang (detect-language language)]
     (if (some? lang)
       lang
       default))))
