(ns clj-trie.core)

(defrecord TrieNode [value children])

(defn insert-value
  ([tr value]
   (insert-value tr value value))
  ([tr remaining-value value]
   (if (empty? remaining-value)
     (TrieNode. value (:children tr))
     (TrieNode. (:value tr) (update-in (:children tr)
                                       [(first remaining-value)]
                                       #(insert-value % (rest remaining-value) value))))))

(defn trie
  [values]
  (reduce insert-value
          (TrieNode. nil {})
          values))

(defn all-values
  [tr]
  (concat
   (if-some [val (:value tr)] [val] [])
   (mapcat all-values (vals (:children tr)))))

(defn- find-node
  [tr value]
  (if (empty? value)
    tr
    (find-node (get (:children tr)
                    (first value))
               (rest value))))

(defn has-value?
  [tr value]
  (some? (:value (find-node tr value))))

(defn prefix-matches
  [tr prefix]
  (all-values (find-node tr prefix)))
