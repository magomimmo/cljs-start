(ns leiningen.new.cljs-start
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "cljs-start"))

(defn cljs-start
  "FIXME: write documentation"
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh 'lein new' cljs-start project.")
    (->files data
             [".gitignore" (render "gitignore" data)]
             ["README.md" (render "README.md" data)]
             ["LICENSE" (render "LICENSE" data)]
             ["project.clj" (render "project.clj" data)]
             ["profiles.clj" (render "profiles.clj" data)]
             ["dev-resources/public/index.html" (render "index.html" data)]
             ["dev-resources/tools/http/ring/server.clj" (render "server.clj")]
             ["dev-resources/tools/repl/brepl/connect.cljs" (render "connect.cljs")]
             ["src/cljs/{{sanitized}}/core.cljs" (render "core.cljs" data)]
             ["test/cljs/{{sanitized}}/core_test.cljs" (render "core_test.cljs" data)])))
