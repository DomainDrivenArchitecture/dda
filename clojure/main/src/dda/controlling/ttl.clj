; Licensed to the Apache Software Foundation (ASF) under one
; or more contributor license agreements. See the NOTICE file
; distributed with this work for additional information
; regarding copyright ownership. The ASF licenses this file
; to you under the Apache License, Version 2.0 (the
; "License"); you may not use this file except in compliance
; with the License. You may obtain a copy of the License at
;
; http://www.apache.org/licenses/LICENSE-2.0
;
; Unless required by applicable law or agreed to in writing, software
; distributed under the License is distributed on an "AS IS" BASIS,
; WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
; See the License for the specific language governing permissions and
; limitations under the License.
(ns dda.controlling.ttl
  (:require [clojure.spec.alpha :as s]))

(s/def ::recurrance #{:monthly :yearly :one-time})
(s/def ::name string?)
(s/def ::amount number?)
(s/def ::salary (s/keys :req [::recurrance ::name ::amount]))

(s/def ::salaries (s/coll-of ::salary))

(s/def ::debts (s/keys :req [::salaries]))

(s/fdef calculate-debts-monthly
  :args (s/cat :debts ::debts)
  :ret number?)
(defn calculate-debts-monthly
  "calculate the debts monthly rate"
  [debts]
  (reduce
    (fn [p salary]
        (+ p (cond
               (= :monthly (::recurrance salary))
               (::amount salary)
               (= :yearly (::recurrance salary))
               (/ (::amount salary) 12))))
   0
   (::salaries debts)))
