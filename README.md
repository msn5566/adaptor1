# Adaptor1

---
**Date:** 2025-08-07 12:29:12
**Branch:** feature/AG-18_20250807122752
---

## 📝 Project Summary

Feature: Create a mapping adapter to generate a target XML file based on source XML, expected target XML, and a JSON mapping configuration.

Input:
* source.xml: XML file containing source data.
* expected_target.xml: XML file representing the desired target structure.
* mapping_with_validation.json: JSON file defining the mapping between source and target POJOs, including validation rules.

Output:
* target_{current timestamp}.xml: XML file containing the mapped data.
* Persisted data in Mongodb.

Constraints:
* Java 21, Spring Boot 3.5.3
* Layered architecture (Controller, Service, Repository)
* Maven, Mongodb, Swagger/OpenAPI, JUnit, Mockito, Docker, GitHub Actions CI

Logic:
1. Read and parse source.xml and expected_target.xml into respective POJOs.
2. Read and parse mapping_with_validation.json to understand the mapping and validation rules.
3. Map data from the source POJO to the target POJO based on the JSON configuration.
4. Perform validation according to the rules specified in the JSON file.
5. Generate target_{current timestamp}.xml from the target POJO.
6. Persist data to Mongodb.

### 🛠️ Core Dependencies

- `org.springframework.boot:spring-boot-starter-web`
- `org.springframework.boot:spring-boot-starter-data-mongodb`
- `org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0`
- `org.springframework.boot:spring-boot-starter-validation`
- `com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.15.2`
- `org.junit.jupiter:junit-jupiter:5.11.0-M1`
- `org.mockito:mockito-core`
- `org.projectlombok:lombok:1.18.30:optional`

--- END ---
