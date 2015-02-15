(defproject clj-trie "0.1.0-SNAPSHOT"
  :description "A trie implementation for prefix-matching sequences, usually strings."
  :url "http://github.com/chetbox/clj-trie"
  :license {:name "Apache 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :profiles {:dev {:dependencies [[midje      "1.6.3"]]
                   :plugins      [[lein-midje "3.1.3"]]}})

