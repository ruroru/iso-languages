(defproject org.clojars.jj/iso-languages "1.0.0-SNAPSHOT"
  :description "iso-languages is a Clojure library for ISO 639 language code lookup and detection.

  Provides functions to retrieve language information by ISO 639-1 (alpha-2), ISO 639-2 (alpha-3) codes, or language names, and to detect standardized language keywords from various input formats."

  :url "https://github.com/ruroru/iso-languages"
  :dependencies [[org.clojure/clojure "1.12.1"]]

  :license {:name "EPL-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}

  :plugins [[org.clojars.jj/bump "1.0.4"]
            [org.clojars.jj/bump-md "1.0.0"]
            [org.clojars.jj/strict-check "1.0.2"]]

  :deploy-repositories [["clojars" {:url      "https://repo.clojars.org"
                                    :username :env/clojars_user
                                    :password :env/clojars_pass}]])
