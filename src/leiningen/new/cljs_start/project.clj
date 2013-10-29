(defproject {{name}} "0.0.1-SNAPSHOT"
  :description "Leiningen template for ClojureScript lib"
  :license {:name "Eclipse Public License - v 1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo}

  :min-lein-version "2.2.0"

  :source-paths ["src/clj"]
  
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-1978"]]

  :plugins [[lein-cljsbuild "1.0.0-SNAPSHOT"]]
  
  :hooks [leiningen.cljsbuild]

  :cljsbuild
  {:builds {:deploy
             {:source-paths ["src/cljs"]
              :jar true
              :compiler
              {:output-to "dev-resources/public/js/deploy.js"
               :optimizations :whitespace
               :pretty-print true}}}})

