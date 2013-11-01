;;; ClojureScript's macros are written in Clojure, and are referenced
;;; via the :require-macros keyword in namespace declarations.
;;; The :as prefix selector is required in :require-macros. 

(ns {{name}}.core
    (:require-macros [{{name}}.macros :as lm]))
(defn foo [greeting]
  (if greeting 
    (str greeting "ClojureScript!")
    (str "Hello, ClojureScript!")))

(.write js/document (foo "Welcome to "))

(. js/console (log "I'm a " (ls/by-id "#test")))
