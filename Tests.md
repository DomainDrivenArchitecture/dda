# Test-View

## access to uploaded content is not working:
![example](content/uploads/SmeagolDrivenTests.jpg)

## modes

### ci/headless

* application includes both smeagol and datatest dependencies 
 * -> no, application has only data-test-dep
* ci script includes usual (clojure.test/run-tests)
 * -> maybe, but more a lein test or boot test ...
* target/datatest with **/*edn created each file contains {:input .. :expectation .. :output ..}
 * yes - {:input .. :expectation .. :output .. :message ...}
* ci script includes a (smeagol.ci/collect-test-results ??)
 * -> no, ci gets whatever clojure.test provides ... we just add a way to write testoutputs to target in a defined edn format.

### webserver

* smeagol is configured with at least 1 repository directory path, containing content mds and 
* smeagol fn submits *edn files 1) into remote storage?! 2) processes into md + edn and commits into the git right away md
 * -> yes!
 * config - {:content [{:path 
 *                                  :run-test-command 
 *                                  :nrepl-port }]}
* url/port of running nrepl into actuall app instance
* if port configured doesn't listen smeagol starts a process with nrepl on the port
* page contains a button "run" and both inputs and outputs (expected)
* on press "run" test backticks  are processed by submitting :input into nrepl
* result is rendered with actual results received

## How should run-test work?

## How should we run tests on smeagol server ?

* build own classpath an run test in nrepl from checkt out git repo
 * pro
  * after editing a resource producing edn file test execution could be more seamless
  * retest only one ns or test
  * fits good to idea of [jupyter-]clojure-notebooks
 * contra
  * makes execution against  services scary (people need to ensure proper env separation) -> multiple nrepls / one per repository?
* run nrepl from uberjar
* run some kind of "lein test" or "boot test"
 * pro
   * caching (reference files right in the target dir) would be quite easy :-)
 * contra 
   * slower (need to boot every time) roundtrippa
   * resource waster / RAM to boot another instance
   * not DRY (repeating actual CI step) <- given code/edn is not subject to change
* run test on dedicated ci and transport results to smeagol
 * pro
   * can be hocked on commits and push

{:input 1
 :expectation 1 ;; exp
 :output "test name testing desc + is diff message" 
 :data-spec-file "_edn"
 :}