Snapshot testing with JUnit 5


TODO
- Serializing arbitrary objects to a comparable string format
  - Make serializer impl pluggable
- JSONPath based replacements with static values (like property replacement in jest)
- Write quick start
- Allow updates of snapshots via Annotation (or maybe param to matchSnapshot or both)
- Make snap target path configurable
- What to compare when only some tests are run
- Introduce versioned root package instead of semantic versioning
  - Goal is that users can always update their dependency and migrate only to new stuff where they want

References
- https://jestjs.io/docs/en/snapshot-testing
- https://junit.org/junit5/docs/current/user-guide/#extensions-registration