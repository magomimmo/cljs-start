(ns leiningen.new.cljs-start
  (:require [leiningen.new.templates :refer [renderer
                                             multi-segment
                                             sanitize-ns
                                             project-name
                                             name-to-path 
                                             year
                                             ->files]]
            [leiningen.core.main :as main]))

(defn cljs-start
  "Create a Leiningen project for developing ClojureScript Libs"
  [name]
  (let [render (renderer "cljs-start")
        main-ns (multi-segment (sanitize-ns name))
        data {:raw-name name
              :name (project-name name)
              :namespace main-ns
              :nested-dirs (name-to-path main-ns)
              :year (year)}]
    (main/info "Generating fresh" name "project based on 'cljs-start' lein template")
    (->files data
             [".gitignore" (render "gitignore" data)]
             ["README.md" (render "README.md" data)]
             ["doc/intro.md" (render "intro.md" data)]
             ["LICENSE" (render "LICENSE" data)]
             ["project.clj" (render "project.clj" data)]
             ["profiles.clj" (render "profiles.clj" data)]
             ["dev-resources/public/index.html" (render "index.html" data)]
             ["dev-resources/tools/http/ring/server.clj" (render "server.clj")]
             ["dev-resources/tools/repl/brepl/connect.cljs" (render "connect.cljs")]
             ["src/cljs/{{nested-dirs}}/core.cljs" (render "core.cljs" data)]
             ["test/cljs/{{nested-dirs}}/core_test.cljs" (render "core_test.cljs" data)])))
