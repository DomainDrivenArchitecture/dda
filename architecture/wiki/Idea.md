### declare a test (either manual or automated)
![a manual test](https://github.com/DomainDrivenArchitecture/ddaArchitecture/raw/master/images/30_requirements/FitnesseAbnahmeTest.png)

A manual test definition

![Automated test](content/uploads/FitnesseTest.png)

A automated test definition

### test execution

To trigger the test you may press a [test button] in your wiki

![a manual test](https://github.com/DomainDrivenArchitecture/ddaArchitecture/raw/master/images/30_requirements/FitnesseTestunterstuetzung1.png)

Manual test may ask some questions.


### controll flow

<img src="content/uploads/SmeagolDrivenTests.jpg" width="80%" alt="SmeagolDrivenTests">

Variant1: Let the smeagol server drive the test

<img src="content/uploads/BrowserDrivenTests.jpg" width="80%" alt="BrowserDrivenTests">

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
