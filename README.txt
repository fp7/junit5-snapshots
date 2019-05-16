Snapshot testing with JUnit 5


TODO
- Serializing arbitrary objects to a comparable string format
  - most likely pretty printed json
  - Make serializer impl pluggable
  - Use same serializer for snapshots? There is actually no reason to use the jest snapshot format
- JSONPath based replacements with static values (like property replacement in jest)
- Write quick start
- Allow updates of snapshots via Annotation (or maybe param to matchSnapshot or both)
- Make snap target path configurable
- What to compare when only some tests are run

References
- https://jestjs.io/docs/en/snapshot-testing
- https://junit.org/junit5/docs/current/user-guide/#extensions-registration