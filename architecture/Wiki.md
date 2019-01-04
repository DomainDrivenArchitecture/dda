# Architecture for Wiki

## Draft of idea

### declare a test (either manual or automated)
![a manual test](https://github.com/DomainDrivenArchitecture/ddaArchitecture/raw/master/images/30_requirements/FitnesseAbnahmeTest.png)

A manual test definition

![Automated test](https://github.com/DomainDrivenArchitecture/dda/raw/master/uploads/FirnesseTest.png)

A automated test definition

### test execution

To trigger the test you may press a [test button] in your wiki

![a manual test](https://github.com/DomainDrivenArchitecture/ddaArchitecture/raw/master/images/30_requirements/FitnesseTestunterstuetzung1.png)

Manual test may ask some questions.


### controll flow

<img src="https://github.com/DomainDrivenArchitecture/dda/raw/master/uploads/SmeagolDrivenTests.jpg" width="80%" alt="SmeagolDrivenTests">

<img src="https://github.com/DomainDrivenArchitecture/dda/raw/master/uploads/BrowserDrivenTests.jpg" width="80%" alt="BrowserDrivenTests">

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

The names from testtable Division, numerator, denomerator quotient? are matching to java code ... or you can write a more explicite adapter by extending a TableFixture class ...

### results

![one test result](https://raw.githubusercontent.com/DomainDrivenArchitecture/ddaArchitecture/master/images/30_requirements/FitnesseTestunterstuetzung2.png)

Result of a single test.


![results](https://github.com/DomainDrivenArchitecture/ddaArchitecture/raw/master/images/30_requirements/FitnesseTestunterstuetzung3.png)

Test results of a whole testsuite over time ...


## Discussion
### How should we scope tests?
* setup / teardown on level of one test
* setup / teardown on level of a testsuite

### Who will trigger / drive the tests?
* Frontend / backend?
* Use Rest to drive the tests ?

### How will we represent data to the user? How to store data?
* Should we use the old tabular format from fitnesse? Should we use edn and nested maps?
* Wich nice data frontends are available (treeview / clojure.inspect* ...)
* Should we add markdown WISYWIG also?

### How should we store testresults?
* storing testresults versioned would be cool :-)

### Should we support tests for more than one project?
* Maybe support one test-vm per project in order to keep classpath deps separated?
* Butt keep deployment setup as simple as posible

### How can we expose testing in a secure way?
* How many surface should we expose to the outer world?


## Decission
