# Architecture for Wiki

## Draft of idea
see [[architecture/wiki/Idea]]

## Discussion
### Is it a good idea to separate test code & data?
* pro
  * we visualize test-data more expressive.
  * separating function & data is one of the oldest ideas in IT is there an argument against?
* mixed
  * we should not use xml for expressing data
  * we separate test function from testdata. In terms of test get harder to read thats an drawback. But in terms of handling large sets of test data separation will help keeping the overview
* contra
  * more complicated to maintain tests.  

### Language for describing tests
#### Gherkin
Having a natural test language for expressing tests in combination with test data.
* pro
  * use established test language
* contra
  * I've used BDD (with cucumber & integrity) heavily but all of these languages lack on modularity - it's not a programming language
  * I've never seen domain people write additional tests. So writing test in natural languege solves the wrong problem ... I think domain understandable tests is more a matter of having expressive domain language build in the system under test than in the used test language.

#### Fitnesse style
Separate test data & expectations from code. The way how data & expectations are matched to the code is a kind of API.
* pro
  * separation of test data & test code. A good combination of expressive data & expressive code.
  * good overview over test history.
  * sometimes domain people start to add additional test data to stress/explore the SUT
  * there is a headless execution mode (the wiki pages in SUT repo can be tested in CI).
  * tables are good to communicate the result.
* contra
  * tables are quite limited, maybe we find a better representation -> maps with good visualization, some kind of inspector ui?
  * embedding data in wiki md is not easy to handle -> maybe we separate wiki & data-storage (in a edn file?) and just visualize them together (use some kind of name conventions & include?) ?
  * fitness implements a whole test-suporting webserver - it's hard to reimplement a up to date webserver mixed with test-execution code. I do not want to enhance fitness for clojure ...

#### Plain old simple deftest
Like fitnesse style test data would be stored in smeagol, test code would reside in SUT test-section like simple unittests.
* pro
  * simple to use - every clojure dev will be able to use in milliseconds ;-)
  * well shaped, would be hard to invent sth. better in terms of suites / setup / teardown ...
* contra
  * deftest takes no parameters, we will not be able to inject test data.
  * complex data itself needs some modularization - aero has a good shape for this demand.
  * maybe we will need some expressive validation operators beside of clojure.test/is and clojure.test/are - expectations maybe is a good choice?. That will be an optional enhancement ...

### Enhanced deftest
with modular data by https://github.com/juxt/aero & rich verification by https://github.com/clojure-expectations/expectations ?

```

{:input
  {:port #profile {:default 8000
                   :dev 8001
                   :test 8002}}}


| ns/function |
| input         | expectations                |
| {aero} :input | {:default 8080} (in result) |

```
  * pro
  * contra

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

* Tests are run inside smeagol vm by appending app source-paths to smeagol source-paths (aka code goes to data)
	* pro: easy, CI friendly
	* contra: language dependent
	
* App is exposing network interface to call list of functions (aka data goes to code via network)
	* pro: will allow to expose fns run in any language, maybe even run in aws-lambda and stuff
	* contra: we need to orchestrate app startup before running smeagol suite, need to do "smeagol-app-plugin" and build uberjars with different -main, security issues?

## Decission
...
