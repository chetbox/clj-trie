(ns clj-trie.core)

(defrecord TrieNode [value children])

(defn insert-value
  ([root value]
   (insert-value root value value))
  ([root remaining-value value]
   (if (empty? remaining-value)
     (TrieNode. value (:children root))
     (TrieNode. (:value root) (update-in (:children root)
                                         [(first remaining-value)]
                                         #(insert-value % (rest remaining-value) value))))))

(defn trie
  [values]
  (reduce insert-value
          (TrieNode. nil {})
          values))

(defn all-values
  [root]
  (concat
   (if-some [v (:value root)] [v] [])
   (mapcat all-values (vals (:children root)))))

(defn- find-node
  [root value]
  (if (empty? value)
    root
    (find-node (get (:children root)
                    (first value))
               (rest value))))

(defn has-value?
  [root value]
  (some? (:value (find-node root value))))

(defn prefix-matches
  [root prefix]
  (all-values (find-node root prefix)))
