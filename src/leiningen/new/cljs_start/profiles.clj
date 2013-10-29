{:dev {:clean-targets ["out"]
       :source-paths ["dev-resources/tools/http"]
       :resources-paths ["dev-resources"]

       :dependencies [[com.cemerick/piggieback "0.1.0"]
                      [ring "1.2.0"]
                      [compojure "1.1.5"]]

       :plugins [[com.cemerick/clojurescript.test "0.1.0"]]
           
       :cljsbuild 
       {:builds {:dev
                 {:source-paths ["src/cljs" "test/cljs" "dev-resources/tools/repl"]
                  :compiler
                  {:output-to "dev-resources/public/js/whitespace.js"
                   :optimizations :whitespace
                   :pretty-print true}}

                 :simple
                 {:source-paths ["src/cljs" "test/cljs"]
                  :compiler
                  {:output-to "dev-resources/public/js/simple.js"
                   :optimizations :simple
                   :pretty-print false}}

                 :advanced
                 {:source-paths ["src/cljs" "test/cljs"]
                  :compiler
                  {:output-to "dev-resources/public/js/advanced.js"
                   :optimizations :advanced
                   :pretty-print false}}}
        
        :test-commands {"dev"
                        ["phantomjs" :runner "dev-resources/public/js/whitespace.js"]
                                    
                        "pre"
                        ["phantomjs" :runner "dev-resources/public/js/simple.js"]
                                    
                        "prod"
                        ["phantomjs" :runner "dev-resources/public/js/advanced.js"]}}
       :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
       :injections [(require '[cljs.repl.browser :as brepl]
                             '[cemerick.piggieback :as pb])
                    (defn browser-repl []
                      (pb/cljs-repl :repl-env
                                    (brepl/repl-env :port 9000)))]}}
