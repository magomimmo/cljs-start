(ns leiningen.new.cljs-start
  "Generate a CLJS lib project with batteries included"
  (:require [leiningen.new.templates :refer [renderer
                                             multi-segment
                                             sanitize-ns
                                             project-name
                                             name-to-path 
                                             year
                                             ->files]]
            [leiningen.core.main :as main]))

(defn cljs-start
  "A general project template for ClojureScript libraries"
  [name]
  (let [render (renderer "cljs-start")
        main-ns (multi-segment (sanitize-ns name))
        data {:raw-name name
              :name (project-name name)
              :namespace main-ns
              :nested-dirs (name-to-path main-ns)
              :year (year)}]
    (main/info "Generating a project called " name "based on the 'cljs-start' template")
    (main/info "To see other templates (app, lein plugin, etc), try `lein help new`.")
    (->files data
             [".gitignore" (render "gitignore" data)]
             ["README.md" (render "README.md" data)]
             ["doc/intro.md" (render "intro.md" data)]
             ["LICENSE" (render "LICENSE" data)]
             ["project.clj" (render "project.clj" data)]
             ["profiles.clj" (render "profiles.clj" data)]
             ["dev-resources/public/index.html" (render "index.html" data)]
             ["dev-resources/tools/http/ring/server.clj" (render "server.clj" data)]
             ["dev-resources/tools/repl/brepl/connect.cljs" (render "connect.cljs" data)]
             ["src/cljs/{{nested-dirs}}.cljs" (render "core.cljs" data)]
             ["test/cljs/{{nested-dirs}}_test.cljs" (render "test.cljs" data)])))
