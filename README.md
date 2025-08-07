# Adaptor1

---
**Date:** 2025-08-07 13:44:12
**Branch:** feature/AG-18_20250807134247
---

## 📝 Project Summary

Feature: Create a mapping adapter to generate a target XML file based on source and expected target XMLs and a JSON mapping configuration.
Input: source.xml, expected_target.xml, mapping_with_validation.json
Output: target_{current timestamp}.xml
Constraints: Java 17, Spring Boot 3.5.3, Layered architecture, Maven, MongoDB, Swagger/OpenAPI, JUnit/Mockito, Docker, GitHub Actions CI.
Logic:
1. Read and parse source.xml and expected_target.xml into POJOs.
2. Read and parse mapping_with_validation.json to define mapping rules and validations.
3. Map data from source POJO to target POJO based on the JSON configuration.
4. Generate target_{current timestamp}.xml from the populated target POJO.

### 🛠️ Core Dependencies

- `org.springframework.boot:spring-boot-starter-web`
- `org.springframework.boot:spring-boot-starter-data-mongodb`
- `org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0`
- `org.projectlombok:lombok:1.18.22:optional`
- `com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.15.2`
- `com.fasterxml.jackson.core:jackson-databind:2.15.2`
- `org.junit.jupiter:junit-jupiter:5.11.0-M1:test`
- `org.mockito:mockito-core:5.0.0:test`

--- END ---
