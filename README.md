# Adaptor1

---
**Date:** 2025-08-07 17:16:52
**Branch:** feature/AG-18_20250807171523
---

## 📝 Project Summary

Feature: Create a mapping adaptor to transform data from a source XML to a target XML based on a JSON mapping file.

Input:
* source.xml: Source XML data file.
* expected_target.xml: Example target XML data file (presumably for validation or testing).
* mapping_with_validation.json: JSON file containing the mapping rules and validation criteria.

Output:
* target_{current timestamp}.xml: Target XML file generated based on the mapping.

Constraints:
* Must be written in Java 17 using Spring Boot 3.5.3.
* Must follow layered architecture (Controller, Service, Repository).
* Must use Maven for build.
* Data should be stored in a MongoDB database.
* Must provide API documentation using Swagger/OpenAPI.
* Unit and integration tests must be written using JUnit and Mockito.
* Must provide Dockerfile for containerization.
* Must include GitHub Actions CI workflow for build, test, and Docker image creation.

Logic:
1. Read and parse source.xml into a source POJO.
2. Read and parse expected_target.xml (usage unclear, possibly for validation).
3. Read and parse mapping_with_validation.json into a mapping configuration object.
4. Map data from the source POJO to a target POJO based on the mapping configuration.
5. Perform validation based on the validation criteria in the mapping configuration.
6. Generate target_{current timestamp}.xml from the target POJO.
7. (Implied) Store data in MongoDB (purpose or specific data unclear).

### 🛠️ Core Dependencies

- `org.springframework.boot:spring-boot-starter-web`
- `org.springframework.boot:spring-boot-starter-data-mongodb`
- `org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0`
- `com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.15.2`
- `com.fasterxml.jackson.core:jackson-databind:2.15.2`
- `org.json:json:20230618`
- `org.springframework.boot:spring-boot-starter-test`
- `org.mockito:mockito-core`
- `org.projectlombok:lombok:1.18.30:optional`

--- END ---
