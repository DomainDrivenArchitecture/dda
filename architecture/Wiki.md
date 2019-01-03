# Architecture for Wiki

## Draft of idea

![SmeagolDrivenTests](https://github.com/DomainDrivenArchitecture/dda/raw/master/uploads/SmeagolDrivenTests.jpg)

![BrowserDrivenTests](https://github.com/DomainDrivenArchitecture/dda/raw/master/uploads/BrowserDrivenTests.jpg)


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
