# Adaptor1

---
**Date:** 2025-08-07 16:19:04
**Branch:** feature/AG-18_20250807161654
---

## 📝 Project Summary

Feature: Create a mapping adapter to transform data from a source XML file to a target XML file based on a JSON mapping configuration.

Input:
* source.xml: Source XML file containing data.
* expected_target.xml: Example target XML file (for validation or comparison, possibly).
* mapping_with_validation.json: JSON file defining the mapping between source and target structures, including validation rules.

Output:
* target_{current timestamp}.xml: Target XML file containing the transformed data.

Constraints:
* Java 17, Spring Boot 3.5.3
* Layered architecture (Controller, Service, Repository)
* Maven build
* MongoDB database
* Swagger/OpenAPI documentation
* JUnit and Mockito tests
* Dockerized
* GitHub Actions CI workflow

Logic:
1. Read data from source.xml and parse into a source POJO.
2. Read mapping rules from mapping_with_validation.json.
3. Create a target POJO based on expected_target.xml structure (or infer from mapping).
4. Map data from source POJO to target POJO according to mapping_with_validation.json. Perform validation as defined in the mapping file during the transformation.
5. Generate target_{current timestamp}.xml from the populated target POJO.

### 🛠️ Core Dependencies

- `org.springframework.boot:spring-boot-starter-web`
- `org.springframework.boot:spring-boot-starter-data-mongodb`
- `org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0`
- `org.projectlombok:lombok:1.18.24:optional`
- `com.fasterxml.jackson.core:jackson-databind`
- `com.fasterxml.jackson.dataformat:jackson-dataformat-xml`
- `javax.validation:validation-api`
- `org.springframework.boot:spring-boot-starter-test:test`
- `org.mockito:mockito-core:5.2.0:test`
- `org.junit.jupiter:junit-jupiter-api:5.11.0-M1:test`

--- END ---
