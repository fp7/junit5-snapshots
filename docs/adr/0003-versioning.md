# 3. versioning

Date: 2019-05-24

## Status

Accepted

## Context

I like the migration path of junit5 very much. Because the packages of junit4 and 
junit5 have different names both can be included at the same time and tests
can be migrated step by step. For any potential breaking changes i will do the same 
in this project

## Decision

Use packages with a version number as root. Whenever a change would break existing usages
I will increment the number so that the old and the new version can be used at the same time.

## Consequences

Copied code in case of a breaking change.