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

{:dev { ;; add the out dir to the dirs to be cleaned by the lein clean
        ;; command. Add here any pathname containing generated files
        ;; to be cleaned by the lein clean command.
       :clean-targets ["out"]
       ;; add the test/clj and test/cljs dir to the leiningen
       ;; :test-paths option. It has to contain also the superset of
       ;; all the pathnames used for CLJS purpose. See below the
       ;; comment on Leiningen :source-paths.
       :test-paths ["test/clj" "test/cljs"]
       ;; We need to add dev-resources/tools/repl, because cljsbuild
       ;; does not add its own source-paths to the project
       ;; source-paths.
       :source-paths ["dev-resources/tools/http" "dev-resources/tools/repl"]
       ;; add the dev-resources to the project classpath.
       :resources-paths ["dev-resources"]
       ;; to instrument the project with the brepl facilities
       ;; (i.e. the ring/compojure server and the piggieback brepl
       ;; built on top of a standard nrepl)
       :dependencies [[com.cemerick/piggieback "0.1.0"]
                      [ring "1.2.1"]
                      [compojure "1.1.6"]]

       ;; The lib for cljs unit testing which is a maximal port of
       ;; clojure.test standard lib
       :plugins [[com.cemerick/clojurescript.test "0.1.0"]]

       ;; cljsbuild settings for development and test phases
       :cljsbuild
       {;; here we configure one build for each compiler optmizations
        ;; options. We do not include the :none optimization.
        :builds {;; the :whitespace optimizations build. This is the
                 ;; only build included in the index.html page used
                 ;; for the brepl connection
                 :whitespace
                 {:source-paths ["src/cljs" "test/cljs" "dev-resources/tools/repl"]
                  :compiler
                  {:output-to "dev-resources/public/js/{{name}}.js"
                   :optimizations :whitespace
                   :pretty-print true}}
                 ;; the :simple optimizations build
                 :simple
                 {:source-paths ["src/cljs" "test/cljs"]
                  :compiler
                  {:output-to "dev-resources/public/js/simple.js"
                   :optimizations :simple
                   :pretty-print false}}
                 ;; the :advanced optimizations build
                 :advanced
                 {:source-paths ["src/cljs" "test/cljs"]
                  :compiler
                  {:output-to "dev-resources/public/js/advanced.js"
                   :optimizations :advanced
                   :pretty-print false}}}

        ;; Here we configure the test commands for running the
        ;; test. To be able to use this commands you have to install
        ;; phantomjs on you development machine. Phantomjs is the most
        ;; used webkit-based headless browser for unit testing JS code
        :test-commands {;; test :whitespace build against phantomjs
                        "phantomjs-ws"
                        ["phantomjs" :runner "dev-resources/public/js/{{name}}.js"]
                        ;; test :simple build against phantomjs
                        "phantomjs-simple"
                        ["phantomjs" :runner "dev-resources/public/js/simple.js"]
                        ;; test advanced build against phantomjs
                        "phantomjs-advanced"
                        ["phantomjs" :runner "dev-resources/public/js/advanced.js"]}}

       ;; The piggieback middleware to start a brepl session from an
       ;; nrepl session
       :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

       ;; A little bit of automation to quickly run the brepl session
       ;; from a nrepl session by just evaluating the (browser-repl)
       ;; expression.
       :injections [(require '[cljs.repl.browser :as brepl]
                             '[cemerick.piggieback :as pb])
                    (defn browser-repl []
                      (pb/cljs-repl :repl-env
                                    (brepl/repl-env :port 9000)))]}}
