# Architecture for Wiki

## Draft of idea
see [[architecture/wiki/Idea]]

## Discussion
find finished disccusssion here: [[architecture/wiki/Discussion]]

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

See also JavaScript Notebook: https://www.heise.de/developer/meldung/Data-Science-Mozilla-bringt-ein-webbasiertes-Tool-zur-Datenanalyse-4335254.html

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

As result, 
* why not include code in same way as we include input data?
* maybe include only some lines of a namespace?
* have a headless execution mode for notebook code?
* but show code results in markdown!
* execute loads & executes the whole namespace in repl ...

### Should we support tests for more than one project?
* Maybe we should support more than one SUT / separate vm per SUT in order to keep classpath deps separated?
  * Let's keep deployment setup as simple as posible
* If we support more than one SUT, we should maybe support a "headless test-driver" for executing the smeagol-tests independant?


## Decission
1. Is it a good idea to separate test code & data? 
	1. => yes. We will not write test code in markdown / smeagol istself.
	2. More details in [[architecture/wiki/Discussion]]
	3. Realization in [data-test](https://github.com/DomainDrivenArchitecture/data-test)
2. Language for describing tests 
 1. => we will use Enhanced deftest.
 2. More details in [[architecture/wiki/Discussion]]
 3. Realization in [data-test](https://github.com/DomainDrivenArchitecture/data-test)
3. How many surface should we expose to the outer world?
	1. => we will use **SIDE-TESTS-HEADLESS**Just a small testrunner able to interact with smeagol but not intended to be exposed public. - second REPL driven by smeagol.
	2. More details in [[architecture/wiki/Discussion]]
