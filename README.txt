Snapshot testing with JUnit 5


TODO
- Serializing arbitrary objects to a comparable string format
  - Make serializer impl pluggable
- JSONPath based replacements with static values (like property replacement in jest)
- Write quick start
- Allow updates of snapshots via Annotation (or maybe param to matchSnapshot or both)
- Make snap target path configurable
- What to compare when only some tests are run
- Release to mvn central and/or jcenter

References
- https://jestjs.io/docs/en/snapshot-testing
- https://junit.org/junit5/docs/current/user-guide/#extensions-registration