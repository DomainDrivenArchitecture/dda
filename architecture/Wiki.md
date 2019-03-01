# Architecture for Wiki

## Draft of idea
see [[architecture/wiki/Idea]]

## Discussion
find finished disccusssion here: [[architecture/wiki/Discussion]]

### How should we scope tests?
* setup / teardown on level of one test
* setup / teardown on level of a testsuite

### Who will drive the tests?
* Frontend / backend?
* Use Rest to drive the tests ?
* trigger will be
  * a code change
  * button in wiki

### How will we represent data to the user? How to store data?
* Should we use the old tabular format from fitnesse? Should we use edn and nested maps?
  * We've many [unit-test having large maps to compare](https://github.com/DomainDrivenArchitecture/dda-git-crate/blob/master/test/src/dda/pallet/dda_git_crate/domain_test.clj)
  * represent test data as edn, visualize it using formatters
  * represent test result as formatter + markdown
* Wich nice data frontends are available (treeview / clojure.inspect* ...)
* Should we add markdown WISYWIG also?

### Data frontends

#### treeview

aka clojure.inspector/inspect-tree

![inspect-tree](content/uploads/inspect-tree.png)

contra:

- is very usual for filesystem, but filesystem nodes (dirs and files) are homogeneous: we could have vector/list/set for values, symbol/string for keys
- what if a key is a composite, not symbol by itself
- not clear how to show test failure (diff, not matching parts)

#### nested table

![nested table](content/uploads/nested-table-data.jpg)

contra:

- big gaps to the left at the bottom
- same issue with composite keys
- good at scoping error by highlight the cell, still not clear at diff/ show actual value
- not clear how to "collapse" nesting inside a cell

#### REBL-style

![REBL-style](content/uploads/rebl.png)

contra:

- show only a single level at one time

#### jupyter notebook style
* why not having a rough, as compact as posible presentation of test results (OK, FAILED with smallest posible diff or EXCEPTION with smallest posible stacktrace)
* as the tool of exploring bigdata is jupyter we should enable sth. similar for exploring test input & testresult? Offering sth. like meijure (combination of meissa is a star and clojure :) or maybe smeajure 😉

* pro
  * be very expresive in showing relevant parts of input / output / test-result
  * mixing doc & data-visualization is in general cool for analyzing large data chunks
* contra (seen at https://docs.google.com/presentation/d/1n2RlMdmv1p25Xy5thJUhkKGvjtV-dkAIsUXP-AL4ffI/edit#slide=id.g3cb1319227_1_33)
  * in notebooks is much hidden state
  * out of order excecution is hard to understand
  * no modularity support / importing notebooks is a realy bad idea
  * notebooks can not be tested
  * writing code in notebooks is hard
  * CopyPast Outputs is hard


### How should we store testresults?
* storing testresults versioned would be cool :-)

### Should we support tests for more than one project?
* Maybe we should support more than one SUT / separate vm per SUT in order to keep classpath deps separated?
  * Let's keep deployment setup as simple as posible
* If we support more than one SUT, we should maybe support a "headless test-driver" for executing the smeagol-tests independant?

### How can we expose testing in a secure way?
* How many surface should we expose to the outer world?

### Execution exposure

Given all \*.edn data is stored inside smeagol repo, where is the code?

 * INSIDE-TESTS-FULLSMEAGOL: Tests are run inside smeagol vm by appending app source-paths to smeagol source-paths (aka code goes to data)
   * pro:
     * easy, CI friendly if we provide some kind of headless
   * contra:
     * smeagol dependency in test project and way to launch smeagol
     * language dependent
     * fragile - nameclashes
     * havy project - smeagol-presentation part is not necesary here
     * have to implement headless for ci
     * more posibilities for lib-version-conflicts
     * if app requires credentials -> should be exposed to smeagol

 * INSIDE-TESTS-HEADLESS: Just a small testrunner able to interact with smeagol but not intended to be exposed public. - second REPL driven by smeagol.
   * pro
     * no lib-version-conlicts
     * no securtiy concerns - becaus it's from one hand.
     * smeagol has to work on second repo for authoring tests & test data (solved already)
   * contra
     * remote repl in ci-system may be required
     * provide some kind of headless driver
     * How we will trigger test execution?
     * How we will get results from repl? (serialize results -> text -> deserialize results in smeagol)

Maybe we can containerize inside-test-headless setups like it's done in https://blog.jupyter.org/introducing-repo2docker-61a593c0752d ?


 * REMOTE-TEST: App is exposing network interface to call list of functions (aka data goes to code via network) - speaking REST
   * pro:
     * will allow to expose fns run in any language, maybe even run in aws-lambda and stuff
     * API - better seperation of presentation & test execution
   * contra:
     * we need to orchestrate app startup before running smeagol  suite,
     * need to do "smeagol-app-plugin" and build uberjars with different  -main,  - need some kind of server
     * security issues
       * users should care about side-effects
       * smeagol users could(?) be able to "login as" (related to stored credentials)  OR always submitting whole world environment for asome case (no db connection) - (some kind of authentication federation)
     * need for additional API with authentication & authorization
     * inter-wiki:
       * Definitiion of foreign wiki-endpoint, credential-storage
       * use protocol,
       * trust/authorization federation



## Decission
1. Is it a good idea to separate test code & data? => yes. We will not write test code in markdown / smeagol istself.
2. Language for describing tests => we will use Enhanced deftest.
