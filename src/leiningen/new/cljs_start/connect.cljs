;;; This namespace is for creating the connection with the browser. It
;;; lives in the dev-resources/tools/http directory. It is used in the
;;; :dev profile only.
(ns brepl.connect
  (:require [clojure.browser.repl :as repl]))

(repl/connect "http://localhost:9000/repl")
