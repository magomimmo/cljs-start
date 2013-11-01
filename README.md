# cljs-start

A [lein template][1] for creating [ClojureScript][2] lib with
batteries included.

> NOTE: `cljs-start` has just been created. Use it at your own risk.

## Introduction

Most of the ClojureScript (CLJS) newcomers have some difficulties in
setting up and configure a complete environment from which to
seriously start developing with this very powerful programming
language for the browser.

Due to this difficulties most front-end programmers just give up and
prefer to go back to JavaScript (JS) or others programming language
compiling down to JS as well, but requiring less efforts in setting up
a productive programming environment (e.g. [CoffeScript][3]).

`cljs-start` intends to offer to the serious newcomers a more direct
path to start hacking with CLJS for building state of the art libs.

## Requirement installation

The only `cljs-start` installation requirements are [Java][4],
[Leiningen][5], and [PhantomJS][6]

#### Installing Java

Depending on your machine you could have Java already installed on
it. CLJS requires a Java Development Kit (JDK) version 6.0 or
later. If you need to install the JDK or to upgrade it, just follow
the instruction for your operating system available on the
[main java website][7].

#### Installing Leiningen

After having installed Java you need to install Leiningen. The
available [installation guide][7] is very easy to be followed:

1. Make sure you have a Java JDK version 6 or later;
2. [Download the script][8];
3. Place it on your `$PATH` (cf. `~/bin` is a good choice if it is on your `path`.);
4. Set the script to be executable. (i.e. `chmod 755 ~/bin/lein`);

If you work on Windows, follow [this instruction][9]

#### Intalling PhantomJS

[Phantomjs][6] is a headless-browser based on [WebKit][10] used mainly
for JS testing support.

[Download the version][11] for your operating system and follow the
corresponding instruction.

You're now ready to start creating the next wonderful CLJS lib with
`cljs-start`.

## Quick start guide

### Create the project

```bash
lein new cljs-start wonderful-lib
Generating fresh 'lein new' cljs-start project.
```

### Compile the project

```bash
cd wonderful-lib
lein compile
Compiling ClojureScript.
Retrieving ring/ring/1.2.1/ring-1.2.1.pom from clojars
Retrieving ring/ring-devel/1.2.1/ring-devel-1.2.1.pom from clojars
Retrieving ring/ring-jetty-adapter/1.2.1/ring-jetty-adapter-1.2.1.pom from clojars
Retrieving ring/ring-servlet/1.2.1/ring-servlet-1.2.1.pom from clojars
Retrieving ring/ring/1.2.1/ring-1.2.1.jar from clojars
Retrieving ring/ring-jetty-adapter/1.2.1/ring-jetty-adapter-1.2.1.jar from clojars
Retrieving ring/ring-devel/1.2.1/ring-devel-1.2.1.jar from clojars
Retrieving ring/ring-servlet/1.2.1/ring-servlet-1.2.1.jar from clojars
Compiling "dev-resources/public/js/advanced.js" from ["src/cljs" "test/cljs"]...
WARNING: set-print-fn! already refers to: cljs.core/set-print-fn! being replaced by: cemerick.cljs.test/set-print-fn! at line 252 /Users/mimmo/tmp/wonderful-lib/target/cljsbuild-compiler-0/cemerick/cljs/test.cljs
Successfully compiled "dev-resources/public/js/advanced.js" in 17.894572 seconds.
Compiling "dev-resources/public/js/simple.js" from ["src/cljs" "test/cljs"]...
WARNING: set-print-fn! already refers to: cljs.core/set-print-fn! being replaced by: cemerick.cljs.test/set-print-fn! at line 252 /Users/mimmo/tmp/wonderful-lib/target/cljsbuild-compiler-1/cemerick/cljs/test.cljs
Successfully compiled "dev-resources/public/js/simple.js" in 5.553004 seconds.
Compiling "dev-resources/public/js/wonderful-lib.js" from ["src/cljs" "test/cljs" "dev-resources/tools/repl"]...
WARNING: set-print-fn! already refers to: cljs.core/set-print-fn! being replaced by: cemerick.cljs.test/set-print-fn! at line 252 /Users/mimmo/tmp/wonderful-lib/target/cljsbuild-compiler-2/cemerick/cljs/test.cljs
Successfully compiled "dev-resources/public/js/wonderful-lib.js" in 3.698392 seconds.
Compiling "dev-resources/public/js/deploy.js" from ["src/cljs"]...
Successfully compiled "dev-resources/public/js/deploy.js" in 2.616146 seconds.
```

Don't worry about the received warnings. They are expected. 

### Run the tests

```bash
lein test
Compiling ClojureScript.

lein test user

Ran 0 tests containing 0 assertions.
0 failures, 0 errors.
Running ClojureScript test: phantomjs-advanced

Testing wonderful-lib.core-test

Ran 1 tests containing 2 assertions.
0 failures, 0 errors.
{:test 1, :pass 2, :fail 0, :error 0, :type :summary}
Running ClojureScript test: phantomjs-simple

Testing wonderful-lib.core-test

Ran 1 tests containing 2 assertions.
0 failures, 0 errors.
{:test 1, :pass 2, :fail 0, :error 0, :type :summary}
Running ClojureScript test: phantomjs-ws

Testing wonderful-lib.core-test

Ran 1 tests containing 2 assertions.
0 failures, 0 errors.
{:test 1, :pass 2, :fail 0, :error 0, :type :summary}
```

### Run the CLJ nREPL

```clj
lein repl
Compiling ClojureScript.
nREPL server started on port 49444 on host 127.0.0.1
REPL-y 0.2.1
Clojure 1.5.1
    Docs: (doc function-name-here)
          (find-doc "part-of-name-here")
  Source: (source function-name-here)
 Javadoc: (javadoc java-object-or-class-here)
    Exit: Control+D or (exit) or (quit)
 Results: Stored in vars *1, *2, *3, an exception in *e

user=>
```

### Run the HTTP server

```clj
user=> (do (use 'ring.server) (run))
2013-10-31 21:34:32.288:INFO:oejs.Server:jetty-7.6.8.v20121106
2013-10-31 21:34:32.321:INFO:oejs.AbstractConnector:Started SelectChannelConnector@0.0.0.0:3000
#<Server org.eclipse.jetty.server.Server@2128d26f>
user=>

### Run the bREPL from the nREPL

From the active REPL just evaluate `(browser-repl)` expression.„

```clj
user=> (browser-repl)
Type `:cljs/quit` to stop the ClojureScript REPL
nil
cljs.user=>
```

### Connect the bREPL with the browser

After having run the HTTP server and the bREPL, Just visit the
[localhost:3000][12], wait a moment and go back to the bREPL to
interact with the browser.

```clj
cljs.user=> (js/alert "Hello, ClojureScript!")
nil
```

### Run the included unit tests from the bREPL and exit the bREPL

```clj
cljs.user=> (cemerick.cljs.test/run-all-tests)

Testing wonderful-lib.core-test

Ran 1 tests containing 2 assertions.
0 failures, 0 errors.
{:test 1, :pass 2, :fail 0, :error 0, :type :summary}
cljs.user=> :cljs/quit
:cljs/quit
user=>
```

### Stop the HTTP server and restart the server

```clj
user=> (.stop server)
nil
user=> (.start server)
2013-10-31 22:21:09.543:INFO:oejs.Server:jetty-7.6.8.v20121106
2013-10-31 22:21:09.558:INFO:oejs.AbstractConnector:Started SelectChannelConnector@0.0.0.0:3000
nil
user=>
```

### Exit

```clj
user=> exit
Bye for now!
```

### Package the jar and see its content

```bash
lein jar
Compiling ClojureScript.
Created /Users/mimmo/tmp/wonderful-lib/target/wonderful-lib-0.0.1-SNAPSHOT.jar
jar tvf target/wonderful-lib-0.0.1-SNAPSHOT.jar
    92 Thu Oct 31 22:24:36 CET 2013 META-INF/MANIFEST.MF
  4461 Thu Oct 31 22:24:36 CET 2013 META-INF/maven/wonderful-lib/wonderful-lib/pom.xml
   111 Thu Oct 31 22:24:36 CET 2013 META-INF/maven/wonderful-lib/wonderful-lib/pom.properties
  2131 Thu Oct 31 22:24:36 CET 2013 META-INF/leiningen/wonderful-lib/wonderful-lib/project.clj
  2131 Thu Oct 31 22:24:36 CET 2013 project.clj
   241 Thu Oct 31 22:24:36 CET 2013 META-INF/leiningen/wonderful-lib/wonderful-lib/README.md
 11220 Thu Oct 31 22:24:36 CET 2013 META-INF/leiningen/wonderful-lib/wonderful-lib/LICENSE
     0 Thu Oct 31 21:24:32 CET 2013 wonderful_lib/
   174 Thu Oct 31 21:24:32 CET 2013 wonderful_lib/core.cljs
Giacomo-Cosenzas-iMac:wonderful-lib mimmo$
```

### Have fun

# License

Copyright © Giacomo (Mimmo) Cosenza aka Magomimmo, 2013. Released
under the Eclipse Public License, the same as Clojure.

[1]: https://github.com/technomancy/leiningen/blob/master/doc/TEMPLATES.md
[2]: https://github.com/clojure/clojurescript
[3]: http://coffeescript.org/
[4]: http://www.java.com/en/
[5]: https://github.com/technomancy/leiningen
[6]: http://phantomjs.org/download.html
[7]: https://github.com/technomancy/leiningen#installation
[8]: https://raw.github.com/technomancy/leiningen/stable/bin/lein
[9]: https://github.com/technomancy/leiningen#windows
[10]: http://www.webkit.org/
[11]: http://phantomjs.org/download.html
[12]: http://localhost:3000/
