# Architecture for Wiki

## Draft of idea

### declare a test (either manual or automated)
![a manual test](https://github.com/DomainDrivenArchitecture/ddaArchitecture/raw/master/images/30_requirements/FitnesseAbnahmeTest.png)

A manual test definition

![Automated test](https://github.com/DomainDrivenArchitecture/dda/raw/master/uploads/FitnesseTest.png)

A automated test definition

### test execution

To trigger the test you may press a [test button] in your wiki

![a manual test](https://github.com/DomainDrivenArchitecture/ddaArchitecture/raw/master/images/30_requirements/FitnesseTestunterstuetzung1.png)

Manual test may ask some questions.


### controll flow

<img src="content/uploads/SmeagolDrivenTests.jpg" width="80%" alt="SmeagolDrivenTests">

Variant1: Let the smeagol server drive the test

<img src="https://github.com/DomainDrivenArchitecture/dda/raw/master/uploads/BrowserDrivenTests.jpg" width="80%" alt="BrowserDrivenTests">

Variant2: Let the browser drive the test directly

### test adapter for your system under test

The java code example would be

```
public class Division {
  private double numerator, denominator;

  public void setNumerator(double numerator) {
    this.numerator = numerator;
  }

  public void setDenominator(double denominator) {
    this.denominator = denominator;
  }

  public double quotient() {
    return numerator/denominator;
  }
}
```

The names from testtable Division, numerator, denomerator quotient? are matching to java code ... or you can write a more explicite adapter by extending a TableFixture class ... [more details](http://fitnesse.org/FitNesse.UserGuide.TwoMinuteExample)

### results

![one test result](https://raw.githubusercontent.com/DomainDrivenArchitecture/ddaArchitecture/master/images/30_requirements/FitnesseTestunterstuetzung2.png)

Result of a single test.


![results](https://github.com/DomainDrivenArchitecture/ddaArchitecture/raw/master/images/30_requirements/FitnesseTestunterstuetzung3.png)

Test results of a whole testsuite over time ...


## Discussion
### Language for describing tests
* Gherkin
  * pro
    * use established test language
  * contra
    * I've used BDD (with cucumber & integrity) heavily but all of these languages lack on modularity - it's not a programming language
    * I've never seen domain people write additional tests. So writing test in natural languege solves the wrong problem ... I think domain understandable tests is more a matter of having expressive domain language build in the system under test than in the used test language.
* fitnesse style (separate test spec: data + function to call + fact to validate from a unittest like adapter)
  * pro
  * contra
* sth new: At my naive first idea I thought about using https://github.com/juxt/aero for data description (small & modular, u can refer defined data many times). For checking results maybe seancorfields https://github.com/clojure-expectations/expectations ?
For defining the function under test & expected results I've no opinionated idea ... maybe a fitness-like table like

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

### How should we store testresults?
* storing testresults versioned would be cool :-)

### Should we support tests for more than one project?
* Maybe we should support more than one SUT / separate vm per SUT in order to keep classpath deps separated?
* But let's keep deployment setup as simple as posible

### How can we expose testing in a secure way?
* How many surface should we expose to the outer world?


## Decission
...
