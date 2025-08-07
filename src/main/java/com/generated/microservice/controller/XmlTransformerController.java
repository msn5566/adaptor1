package com.generated.microservice.controller;

import com.generated.microservice.service.XmlTransformerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xml")
public class XmlTransformerController {

    private final XmlTransformerService xmlTransformerService;

    public XmlTransformerController(XmlTransformerService xmlTransformerService) {
        this.xmlTransformerService = xmlTransformerService;
    }

    @PostMapping("/transform")
    public ResponseEntity<String> transformXml(@RequestBody String xmlData) {
        String transformedXml = xmlTransformerService.transformXml(xmlData);
        return ResponseEntity.ok(transformedXml);
    }
}