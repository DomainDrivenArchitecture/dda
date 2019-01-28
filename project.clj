(defproject dda "0.1.0-SNAPSHOT"
  :description "A DomainDrivenArchitecture clojure example"
  :url "https://domaindrivenarchitecture.org"
  :license {:name "Apache License, Version 2.0"
            :url "https://www.apache.org/licenses/LICENSE-2.0.html"}

  :dependencies [[org.clojure/clojure "1.10.0"]]

  :target-path "target/%s/"

  :source-paths ["clojure/main/src"]

  :resource-paths ["clojure/main/resources"]

  :profiles {:test {:test-paths ["clojure/test/src"]
                    :resource-paths ["clojure/test/resources"]}
             :dev {:source-paths ["clojure/test/src"]
                   :resource-paths ["clojure/test/resources"]}})
