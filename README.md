# Adaptor1

---
**Date:** 2025-08-07 14:01:34
**Branch:** feature/AD1-3_20250807140039
---

## 📝 Project Summary

Feature: XML-to-XML Transformation
Input: Source XML document and JSON transformation mapping rules.
Output: Target XML document.
Constraints: Must use Java 21, Spring Boot 3.5.*, layered architecture, Maven, MongoDB, Swagger/OpenAPI, JUnit, Mockito, Docker, and GitHub Actions CI.  Transformation rules provided in a JSON file [^mapping_with_validation.json]. Expected output is defined in [^expected_target.xml]. Example source provided as [^source.xml].
Logic: Parse source XML, apply transformation using JSON mapping rules, generate target XML.

### 🛠️ Core Dependencies

- `org.springframework.boot:spring-boot-starter-web`
- `org.springframework.boot:spring-boot-starter-data-mongodb`
- `org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0`
- `org.junit.jupiter:junit-jupiter`
- `org.mockito:mockito-core`
- `org.projectlombok:lombok:optional`
- `com.fasterxml.jackson.core:jackson-databind`
- `com.fasterxml.jackson.dataformat:jackson-dataformat-xml`

--- END ---
