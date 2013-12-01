# cljs-start

A [lein template][1] for creating [ClojureScript][2] libs with
batteries included:

* **Unit Testing**: with the [clojurescript.test][15]
  plugin
* **Browser REPL**: a REPL (Read-Evaluate-Print-Loop, a console to interact with the your programs) using the [austin][16] plugin
* **Source Maps**: allows you to debug your code in the brower with the original CLJS source code
  rather than the generated JS code (Google Chrome only at the moment)
* **separation of concerns**: uses [lein profiles][17] to keep
  the user's view of your lib separate from the the developer's view
* **ready for deployment**: thanks to *separation of
  concerns*, your lib will be correctly packaged to be deployed as a
  `jar` containing only the needed resources without any further
  intervention in the `project.clj` file

> If you're in hurry and want to see an example of using `cljs-start`
> to instrument an already implemented CLJS lib, take a look at the
> [provided sample][14].

## Introduction

Many ClojureScript (CLJS) newcomers have some difficulties
setting up and configuring a complete environment to
start developing with this very powerful programming
language for the browser.

Due to these difficulties, many front-end programmers give up and
go back to JavaScript (JS) or another language
that compiles down to JS but requires less effort to set up (e.g. [CoffeScript][3]).

`cljs-start` offers newcomers a more direct
path to start hacking with CLJS and building state of the art libs.

`cljs-start` uses a few libraries created or maintained by
[Chas Emerick][13]. Without his great work, this lein-template would
not exist.

## Requirements

The only `cljs-start` requirements are [Java `>= "1.6"`][4],
[Leiningen `>= "2.2.0"`][5], and [PhantomJS `>= "1.9.1"`][6]

## Installation

### Installing Java

Your machine may already have Java installed. Try running the following at a commandline or terminal:

```
java -version
```

CLJS requires a Java Development Kit (JDK) version 6.0 or
later. If you need to install the JDK or to upgrade it, just follow
the instruction for your operating system available on the
[main java website][4].

### Installing Leiningen

[Leiningen][5] is a project automation tool for Clojure and CLJS.

After installing Java, you need to install Leiningen. The
available [installation guide][7] is very easy to follow:

1. Make sure you have a Java JDK version 6 or later;
2. [Download the script][8];
3. Place it on your `$PATH` (cf. `~/bin` is a good choice if it is on your `path`.);
4. Set the script to be executable. (i.e. `chmod 755 ~/bin/lein`);

If you work on Windows, follow [these instructions][9] instead.

### Installing PhantomJS

[Phantomjs][6] is a headless-browser based on [WebKit][10] used mainly
for JS testing.

[Download the version][11] for your operating system and follow the
corresponding instruction.

You're now ready to start creating the next wonderful CLJS lib with
`cljs-start`.

## Quick start guide

### Create the project for your CLJS lib

```bash
lein new cljs-start wonderful-lib
Generating fresh 'lein new' cljs-start project.
```

### Compile the project

```bash
cd wonderful-lib
lein compile
Compiling ClojureScript.
Compiling "dev-resources/public/js/advanced.js" from ["src/cljs" "test/cljs"]...
Successfully compiled "dev-resources/public/js/advanced.js" in 18.017258 seconds.
Compiling "dev-resources/public/js/simple.js" from ["src/cljs" "test/cljs"]...
Successfully compiled "dev-resources/public/js/simple.js" in 8.929018 seconds.
Compiling "dev-resources/public/js/wonderful-lib.js" from ["src/cljs" "test/cljs" "dev-resources/tools/repl"]...
Successfully compiled "dev-resources/public/js/wonderful-lib.js" in 6.522373 seconds.
Compiling "dev-resources/public/js/useless.js" from ["src/cljs"]...
Successfully compiled "dev-resources/public/js/useless.js" in 4.451348 seconds.
```

### Run the dummy tests

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
nREPL server started on port 53604 on host 127.0.0.1
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
user=> (run)
2013-11-18 21:57:57.219:INFO:oejs.Server:jetty-7.6.8.v20121106
2013-11-18 21:57:57.249:INFO:oejs.AbstractConnector:Started SelectChannelConnector@0.0.0.0:3000
#<Server org.eclipse.jetty.server.Server@31153b19>
user=>
```

### Run the bREPL from the nREPL

From the active REPL evaluate the following expressions:

```clj
user=> (browser-repl)
Browser-REPL ready @ http://localhost:53659/6909/repl/start
Type `:cljs/quit` to stop the ClojureScript REPL
nil
cljs.user=>
```

### Connect the bREPL with the browser

After having run the HTTP server and the bREPL, Just open
[localhost:3000][12] in a browser, wait a moment and go back to the bREPL to
interact with the browser frow the REPL.

```clj
cljs.user=> (js/alert "Hello, ClojureScript!")
nil
cljs.user=> (ns cljs.user (:use [wonderful-lib.core :only [foo]]))
nil
cljs.user=> (foo "Welcome to ")
"Welcome to ClojureScript!"
cljs.user=>
```

### Run the included dummy unit tests from the bREPL and exit the bREPL

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

### Stop and restart the HTTP server

```clj
user=> (.stop http/server)
nil
user=> (.start http/server)
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
```

### Debugging CLJS code in the browser

> DISCLAIMER: I'm still working to understand what is the best
> development workflow with Chrome Development Tools. At the moment the
> following is a minimal workflow for debugging CLJS code by using the
> recently added *source map* feature to the CLJS compiler.

#### Enable JS source map in Chrome

* Open the Chrome Developer Tools
* Click the Setting icon at the very bottom right of the Development
  Tools Window
* Flag the `Enable JS source map` option in the `Source` section
* Close the Setting Window

Now compile the whitespace build in auto mode:

```bash
lein do clean, cljsbuild auto whitespace
Deleting files generated by lein-cljsbuild.
Compiling ClojureScript.
Compiling "dev-resources/public/js/wonderful-lib.js" from ["src/cljs" "test/cljs" "dev-resources/tools/repl"]...
Successfully compiled "dev-resources/public/js/wonderful-lib.js" in 18.386199 seconds.

```

Then:

* open the `~/Developer/wonderful-lib/dev-resources/public/index.html`
  page in Chrome
* click the `Sources` tab the Developer Tools Window
* type `Cmd+O` (or the corresponding keys chord of your operating
  system)
* type `js/core` in the search field
* select the first match from the result set
  (i.e. `...js/wonderful-lib/core.cljs`). The source code of the
  `core.cljs` will be displayed in the a new tab.

Now click on the line number in the `core.cljs` for which you want to
set a breakpoint and just reload the `index.html` page.

Unfortunately, the very first time you reload the page, the debugger
closes the `core.cljs` file and opens the `wonderful-lib.js` file
emitted by the CLJS compiler instead.

Just open the `core.cljs` again.

Now you can use the standard debugging commands from the Chrome
debugger pane on the right of the Developer Tools Window to debug your
CLJS code.

You can even modify the source code inside the Developer Tools and
then save the changes by right clicking the `core.cljs` pane. The
`cljsbuild auto whitespace` running task will recompile it and the
source map will be updated as well.

Not too bad!

## It's your turn

The `cljs-start` lein-template helps you get started by reducing the
incidental complexity for creating pure CLJS libs. It's now your turn
to design and implement a wonderful library.

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
[13]: https://github.com/cemerick
[14]: https://github.com/magomimmo/cljs-start/blob/master/doc/sample.md
[15]: https://github.com/cemerick/clojurescript.test
[16]: https://github.com/cemerick/austin
[17]: https://github.com/technomancy/leiningen/blob/master/doc/PROFILES.md
