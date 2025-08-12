# Adaptor1

---
**Date:** 2025-08-12 14:17:12
**Branch:** feature/AD1-3_20250812141611
---

## 📝 Project Summary

Feature: Transform XML data based on JSON mapping rules.
Input: Source XML data and JSON mapping rules.
Output: Transformed XML data.
Constraints: Must use Java 21, Spring Boot 3.5.*, layered architecture, Maven, MongoDB, Swagger/OpenAPI, JUnit, Mockito, Docker, and GitHub Actions CI.  Transformation rules are provided in a JSON file [^mapping_with_validation.json]. Expected output is defined in [^expected_target.xml]. Example source XML is provided [^source.xml].
Logic: Parse source XML, apply transformation using mapping rules from JSON, and generate target XML.

### 🛠️ Core Dependencies

- `org.springframework.boot:spring-boot-starter-web`
- `org.springframework.boot:spring-boot-starter-data-mongodb`
- `org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0`
- `com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.15.2`
- `org.springframework.boot:spring-boot-starter-test`
- `org.junit.jupiter:junit-jupiter:5.11.0-M1`
- `org.mockito:mockito-core`

--- END ---
