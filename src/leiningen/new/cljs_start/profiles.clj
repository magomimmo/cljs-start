;;; ****************************** NOTES ******************************
;;;
;;; The {{name}}/profiles.clj file is used for keeping the developer
;;; view of your cljs lib separated from its user view. This way the
;;; user of your lib does not even see the complexity of the developer
;;; view of the lib.  The {{name}}/profiles.clj should never contain
;;; any configuration for the :user profile. The content of
;;; {{name}}/profiles.clj will be merge with the content from the
;;; ~/.lein/profiles.clj and {{name}}/project.clj. Keep in mind that
;;; any in case of conflicts, the {{name}}/profiles.clj takes
;;; precedence over the {{name}}/project.clj which, in turn, takes
;;; precedence over the ~/.lein/profiles.clj
;;;
;;; *******************************************************************

{:dev { ;; add out dir to the dirs to be cleaned by the lein clean
        ;; command
       :clean-targets ["out"]
       ;; we need to add dev-resources/tools/repl, because cljsbuild
       ;; does not add its own source-paths to the project
       ;; source-paths.
       :source-paths ["dev-resources/tools/http" "dev-resources/tools/repl"]
       ;; To add the dev-resources to the project classpath
       :resources-paths ["dev-resources"]
       ;; to instrument the project with the brepl facilities
       ;; (i.e. the ring/compojure server and the piggieback brepl
       ;; built on top of a standard nrepl)
       :dependencies [[com.cemerick/piggieback "0.1.0"]
                      [ring "1.2.1"]
                      [compojure "1.1.6"]]

       ;; the lib for cljs unit testing which is a maximal port of
       ;; clojure.test standard lib
       :plugins [[com.cemerick/clojurescript.test "0.1.0"]]

       ;; cljsbuild settings for development and test phases
       :cljsbuild
       {;; here we configure one build for each Google Closure
        ;; Compiler optmization. We do not include the :none
        ;; optimization.
        :builds {;; the whitespace optimization build
                 :whitespace
                 {:source-paths ["src/cljs" "test/cljs"]
                  :compiler
                  {:output-to "dev-resources/public/js/whitespace.js"
                   :optimizations :whitespace
                   :pretty-print true}}
                 ;; the simple optimization build
                 :simple
                 {:source-paths ["src/cljs" "test/cljs"]
                  :compiler
                  {:output-to "dev-resources/public/js/simple.js"
                   :optimizations :simple
                   :pretty-print false}}
                 ;; the advanced optimization build
                 :advanced
                 {:source-paths ["src/cljs" "test/cljs"]
                  :compiler
                  {:output-to "dev-resources/public/js/advanced.js"
                   :optimizations :advanced
                   :pretty-print false}}}

        ;; here we configure the test commands for running the
        ;; test. To be able to use this commands you have to install
        ;; both phantomjs and slimerjs on you development
        ;; machine. Phantomjs is the webkit-based headless browser,
        ;; which slimerjs is the gecko-based headless browser.
        :test-commands {;; test against phantomjs
                        ;; test whitespace build
                        "phantomjs-ws"
                        ["phantomjs" :runner "dev-resources/public/js/whitespace.js"]
                        ;; test simple build against panthomjs
                        "phantomjs-simple"
                        ["phantomjs" :runner "dev-resources/public/js/simple.js"]
                        ;; test advanced build against panthomjs
                        "phantomjs-advanced"
                        ["phantomjs" :runner "dev-resources/public/js/advanced.js"]

                        ;; test against slimerjs
                        ;; test whitespace build
                        "slimerjs-ws"
                        ["slimerjs" :runner "dev-resources/public/js/whitespace.js"]
                        ;; test simple build
                        "slimerjs-simple"
                        ["slimerjs" :runner "dev-resources/public/js/simple.js"]
                        ;; test advanced build
                        "slimerjs-advanced"
                        ["slimerjs" :runner "dev-resources/public/js/advanced.js"]}}

       ;; He we added the piggieback middleware to start a brepl
       ;; session from an nrepl session
       :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

       ;; A little bit of automation to quickly run the brepl session
       ;; from a nrepl session
       :injections [(require '[cljs.repl.browser :as brepl]
                             '[cemerick.piggieback :as pb])
                    (defn browser-repl []
                      (pb/cljs-repl :repl-env
                                    (brepl/repl-env :port 9000)))]}}
