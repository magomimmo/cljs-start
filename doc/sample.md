# cljs-start sample

## Introduction

Even if [cljs-start][1] could be used to create a CLJS lib from scratch,
it could be useful to instrument an existing CLJS lib as well.

In this document we're going to demonstrate how to use `cljs-start`
lein-template to the [hiccups][2] lib which is one of the CLJS lib
which has been used in the [modern-cljs][3] series of tutorial on
[clojurescript][4].

## Fork and clone the original hiccups lib

The first step is to fork and clone the original  `hiccups` lib.

```bash
git clone https://github.com/magomimmo/hiccups.git
cd hiccups
git remote add upstream https://github.com/teropa/hiccups.git
```

> NOTE 1: I'm assuming that `hiccups` has been cloned in the
> `~/Developer/hiccups` directory.

## Create a new clojurescript lib

The next step is to create a new CLJS lib by using `cljs-start` in a temporary directory.

```bash
mkdir ~/tmp
cd tmp
lein new cljs-start hiccups
Generating fresh 'lein new' cljs-start project.
cd hiccups
```

## Copy the source from the original repo

Now we have to copy the source code from the origin `hiccups` repo to
the newly created one.

Most CLJS libs which have macros put the corresponding CLJ source
files in the same directory hosting the CLJS source files which is not
wrong at all, but I prefer to adhere to the separation of concerns
principle as much as I can and the `cljs-start` lein-template has been
implemented with this approach.

Here is the directory layout of the original `hiccups` lib:

```bash
tree ~/Developer/hiccups
.
├── LICENSE.html
├── README.md
├── phantom
│   └── unit-test.js
├── project.clj
├── src
│   └── hiccups
│       ├── core.clj
│       ├── runtime.clj
│       └── runtime.cljs
├── test
│   └── hiccups
│       ├── core_test.cljs
│       └── test_macros.clj
└── test-resources
    └── unit-test.html

6 directories, 10 files
```

As you see, all the `hiccups` source files live in the `src/hiccups`
directory. To adhere to the separation of concerns principle we want
to copy the `clj` source files in the `src/clj` directory of the newly
created `hiccups` project and the `cljs` source files in the
corresponding `src/cljs` directory.

```bash
cd ~/tmp/hiccups
mkdir -p src/clj/hiccups
cp ~/Developer/hiccups/src/hiccups/*.clj src/clj/hiccups/
cp ~/Developer/hiccups/src/hiccups/*.cljs src/cljs/hiccups/
```

Here is the obtained directories layout:

```bash
tree src/
src/
├── clj
│   └── hiccups
│       ├── core.clj
│       └── runtime.clj
└── cljs
    └── hiccups
        ├── core.cljs
        └── runtime.cljs

4 directories, 4 files
```

Note that the `cljs-start` lein template created a `core.cljs` source
file with a dummy content and we have to delete it.

```bash
rm src/cljs/hiccups/core.cljs
```

## Copy the unit test from the original repo

Next we want to copy and the original `hiccups` unit tests to the
`test/cljs` directory.

```bash
cp ~/Developer/hiccups/test/hiccups/core_test.cljs test/cljs/hiccups/
```

> NOTE 2: the name of the file containing the original `hiccups` unit
> tests will override the one created by the `cljs-start` lein-template.

## Edit the `core_test.cljs`

[Tero Parviainen][5], the author of `hiccups`, has been very
scrupulous and defined few macros in the `test_macros.clj` for the
`deftest` and the `is` symbols. This is very fortunate because it
allows us to edit very few things to port the original unit tests to
the `clojurescript.test` lib which is used by any project created by
the `cljs-start` lein-plugin.

All we have to do is just to modify the namespace declaration
according to the `clojurescript.test` requirement.

Here the new `core_test.cljs` namespace declaration.

```clj
(ns hiccups.core-test
  (:require-macros [cemerick.cljs.test :as t]
                   [hiccups.core :as hiccups])
  (:require [cemerick.cljs.test :as testing]
            [hiccups.runtime :as hiccupsrt]))
```


### Light the fire

Let's see if the project is still compiling as expected.

```bash
lein compile
Compiling ClojureScript.
Compiling "dev-resources/public/js/advanced.js" from ["src/cljs" "test/cljs"]...
Successfully compiled "dev-resources/public/js/advanced.js" in 17.54757 seconds.
Compiling "dev-resources/public/js/simple.js" from ["src/cljs" "test/cljs"]...
Successfully compiled "dev-resources/public/js/simple.js" in 9.8005 seconds.
Compiling "dev-resources/public/js/hiccups.js" from ["src/cljs" "test/cljs" "dev-resources/tools/repl"]...
Successfully compiled "dev-resources/public/js/hiccups.js" in 7.508443 seconds.
Compiling "dev-resources/public/js/useless.js" from ["src/cljs"]...
Successfully compiled "dev-resources/public/js/useless.js" in 5.727353 seconds.
```

Very good. Let's now see if the unit tests are still working as well.

```bash
lein test
Compiling ClojureScript.

lein test user

Ran 0 tests containing 0 assertions.
0 failures, 0 errors.
Running ClojureScript test: phantomjs-advanced

Testing hiccups.core-test

ERROR in (tag-contents) (:)
Uncaught exception, not in assertion.
expected: nil
  actual:
ReferenceError: Can't find variable: t
...
Ran 7 tests containing 58 assertions.
0 failures, 1 errors.
{:test 7, :pass 57, :fail 0, :error 1, :type :summary}
...
0 failures, 1 errors.
{:test 7, :pass 57, :fail 0, :error 1, :type :summary}
Subprocess failed
```

Ops, something went wrong in the `tag-contents` unit test. Take a look at its definition.

```clj
(t/deftest tag-contents
 ; empty tags
 (t/is (= (hiccups/html [:div]) "<div></div>"))
 (t/is (= (hiccups/html [:h1]) "<h1></h1>"))
 (t/is (= (hiccups/html [:script]) "<script></script>"))
 (t/is (= (hiccups/html [:text]) "<text />"))
 (t/is (= (hiccups/html [:a]) "<a></a>"))
 (t/is (= (hiccups/html [:iframe]) "<iframe></iframe>"))
 ; tags containing text
 (t/is (= (hiccups/html [:text "Lorem Ipsum"]) "<text>Lorem Ipsum</text>"))
 ; contents are concatenated
 (t/is (= (hiccups/html [:body "foo" "bar"]) "<body>foobar</body>"))
 (t/is (= (hiccups/html [:body [:p] [:br]]) "<body><p /><br /></body>"))
 ; seqs are expanded
 (t/is (= (hiccups/html [:body (list "foo" "bar")]) "<body>foobar</body>"))
 (t/is (= (hiccups/html (list [:p "a"] [:p "b"])) "<p>a</p><p>b</p>"))
 ; vecs don't expand - error if vec doesn't have tag name
 (t/is-thrown (hiccups/html (vector [:p "a"] [:p "b"])))
 ; tags can contain tags
 (t/is (= (hiccups/html [:div [:p]]) "<div><p /></div>"))
 (t/is (= (hiccups/html [:div [:b]]) "<div><b></b></div>"))
 (t/is (= (hiccups/html [:p [:span [:a "foo"]]])
          "<p><span><a>foo</a></span></p>")))
```

As you see, after the `;vet don't expand - error if vec doesn't have
tag name` content, there is a call to the `is-thrown` macro which is
not defined in the `clojurescript.test` lib. We could eventually
define it, but at the moment we are just going to comment it and run
again the `lein test` task.

```bash
lein do compile, test
Compiling ClojureScript.
Compiling "dev-resources/public/js/advanced.js" from ["src/cljs" "test/cljs"]...
Successfully compiled "dev-resources/public/js/advanced.js" in 13.598759 seconds.
Compiling "dev-resources/public/js/simple.js" from ["src/cljs" "test/cljs"]...
Successfully compiled "dev-resources/public/js/simple.js" in 7.467512 seconds.
Compiling "dev-resources/public/js/hiccups.js" from ["src/cljs" "test/cljs" "dev-resources/tools/repl"]...
Successfully compiled "dev-resources/public/js/hiccups.js" in 4.39739 seconds.
Compiling ClojureScript.

lein test user

Ran 0 tests containing 0 assertions.
0 failures, 0 errors.
Running ClojureScript test: phantomjs-advanced

Testing hiccups.core-test

Ran 7 tests containing 60 assertions.
0 failures, 0 errors.
{:test 7, :pass 60, :fail 0, :error 0, :type :summary}
Running ClojureScript test: phantomjs-simple

Testing hiccups.core-test

Ran 7 tests containing 60 assertions.
0 failures, 0 errors.
{:test 7, :pass 60, :fail 0, :error 0, :type :summary}
Running ClojureScript test: phantomjs-ws

Testing hiccups.core-test

Ran 7 tests containing 60 assertions.
0 failures, 0 errors.
{:test 7, :pass 60, :fail 0, :error 0, :type :summary}
```

Great. All the 60 assertions from the 7 unit tests passed.

### 
