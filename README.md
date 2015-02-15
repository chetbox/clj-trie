# clj-trie

A Clojure library designed to for prefix-matching strings, but will work with other sequences too.

![Build status](https://travis-ci.org/chetbox/clj-trie.svg)

## Usage

```clojure
(require '[clj-trie.core :as tr])

(def my-trie (atom (tr/trie ["apple" "apples" "banana"])))

(tr/prefix-matches @my-trie "ap")
; => ("apple" "apples")

(tr/prefix-matches @my-trie "ba")
; => ("banana")

(swap! my-trie tr/insert-value "bananas")

(tr/prefix-matches @my-trie "ba")
; => ("banana" "bananas")

```

## License

Copyright © 2015 github.com/chetbox

Licensed under the Apache License, Version 2.0.
