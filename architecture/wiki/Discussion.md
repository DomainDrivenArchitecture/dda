# Discussion
## Is it a good idea to separate test code & data?
* pro
  * we visualize test-data more expressive.
  * separating function & data is one of the oldest ideas in IT is there an argument against?
* mixed
  * we should not use xml for expressing data
  * we separate test function from testdata. In terms of test get harder to read thats an drawback. But in terms of handling large sets of test data separation will help keeping the overview
* contra
  * more complicated to maintain tests.  

## Language for describing tests
### Gherkin
Having a natural test language for expressing tests in combination with test data.
* pro
  * use established test language
* contra
  * I've used BDD (with cucumber & integrity) heavily but all of these languages lack on modularity - it's not a programming language
  * I've never seen domain people write additional tests. So writing test in natural languege solves the wrong problem ... I think domain understandable tests is more a matter of having expressive domain language build in the system under test than in the used test language.

### Fitnesse style
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

### Plain old simple deftest
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