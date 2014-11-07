;;; ****************************** NOTES ******************************
;;; Defines four profiles:
;;;
;;; - :shared
;;; - :dev
;;; - :simple
;;; - :advanced
;;;
;;; the :dev, :simple and :advanced profiles are composite profiles,
;;; meaning that they share the content of :shared profile.
;;; *******************************************************************

{:shared {:clean-targets ["out" :target-path]
          :test-paths ["test/clj" "test/cljs"]
          :resources-paths ["dev-resources"]
          :plugins [[com.cemerick/clojurescript.test "0.3.1"]]
          :cljsbuild
          {:builds {:{{name}}
                    {:source-paths ["test/cljs"]
                     :compiler
                     {:output-dir "dev-resources/public/js"
                      :source-map "dev-resources/public/js/{{sanitized}}.js.map"}}}
           :test-commands {"phantomjs"
                           ["phantomjs" :runner "dev-resources/public/js/{{sanitized}}.js"]}}}
 :dev [:shared
       {:source-paths ["dev-resources/tools/http" "dev-resources/tools/repl"]
        :dependencies [[ring "1.3.1"]
                       [compojure "1.2.1"]
                       [enlive "1.1.5"]]
        :plugins [[com.cemerick/austin "0.1.5"]]
        :cljsbuild
        {:builds {:{{name}}
                  {:source-paths ["dev-resources/tools/repl"]
                   :compiler
                   {:optimizations :whitespace
                    :pretty-print true}}}}
        
        :injections [(require '[ring.server :as http :refer [run]]
                              'cemerick.austin.repls)
                     (defn browser-repl []
                       (cemerick.austin.repls/cljs-repl (reset! cemerick.austin.repls/browser-repl-env
                                                                (cemerick.austin/repl-env))))]}]
 ;; simple profile.
 :simple [:shared
          {:cljsbuild
           {:builds {:{{name}}
                     {:compiler {:optimizations :simple
                                 :pretty-print false}}}}}]
 ;; advanced profile
 :advanced [:shared
            {:cljsbuild
             {:builds {:{{name}}
                       {:source-paths ["test/cljs"]
                        :compiler
                        {:optimizations :advanced
                         :pretty-print false}}}}}]}

