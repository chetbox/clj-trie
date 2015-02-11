(ns clj-trie.core-test
  (:require [midje.sweet :refer :all]
            [clj-trie.core :refer :all]))

(facts "about `has-value?`"

  (fact "nothing found in an empty trie"
    (let [t (trie [])]
      (has-value? t "") => falsey
      (has-value? t "pylons") => falsey))

  (fact "trie with empty string has a value"
    (let [t (trie [""])]
      (has-value? t "") => truthy))

  (tabular
    (fact "one item"
      (has-value? (trie ["apples"]) ?value) => ?expected)
    ?value        ?expected
    "apples"      truthy
    ""            falsey
    "app"         falsey
    "applesauce"  falsey
    "x"           falsey)

  (tabular
    (fact "two items"
      (has-value? (trie ["banana" "bananas"]) ?value) => ?expected)
    ?value            ?expected
    "banana"          truthy
    "bananas"         truthy
    "apple"           falsey
    ""                falsey
    "ba"              falsey
    "bananasandwich"  falsey))


(facts "about `all-values`"

  (fact "nothing found in an empty trie"
    (all-values (trie [])) => [])

  (fact "single item"
    (all-values (trie ["clementine"]))
      => ["clementine"])

  (fact "multiple items with same prefix ordered by match"
    (all-values (trie ["dragonfruit" "dragon"]))
      => ["dragon" "dragonfruit"])

  (fact "multiple items"
    (all-values (trie ["clementines" "clementine" "dragonfruit"]))
      => (contains ["clementine" "clementines" "dragonfruit"] :in-any-order)))


(facts "about `prefix-match`"

  (tabular
    (fact "an empty trie returns no results"
      (prefix-matches (trie []) ?value) => ?expected)
    ?value  ?expected
    ""      []
    "a"     []
    "qwert" [])

  (tabular
    (fact "a trie with one element returns the element or nothing"
      (prefix-matches (trie ["banana"]) ?value) => ?expected)
    ?value    ?expected
    ""        ["banana"]
    "banana"  ["banana"]
    "ban"     ["banana"]
    "b"       ["banana"]
    "nana"    []
    "x"       [])

  (tabular
    (fact "a trie with many elemets return prefix matches"
      (prefix-matches (trie ["app" "apple" "apples"
                             "banana" "bananas"
                             "clement" "clementine" "clementines"
                             "drag" "dragon" "dragonfruit"])
                      ?value) => ?expected)
    ?value        ?expected
    ""            (contains ["app" "apple" "apples" "banana" "bananas" "clement"
                             "clementine" "clementines" "drag" "dragon" "dragonfruit"]
                            :in-any-order)
    "ban"         ["banana" "bananas"]
    "app"         ["app" "apple" "apples"]
    "clementine"  ["clementine" "clementines"]
    "drago"       ["dragon" "dragonfruit"]
    "x"           []))
