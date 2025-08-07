
package com.generated.microservice.controller;

import com.generated.microservice.service.XmlTransformationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/transform")
public class XmlTransformationController {

    private final XmlTransformationService xmlTransformationService;

    public XmlTransformationController(XmlTransformationService xmlTransformationService) {
        this.xmlTransformationService = xmlTransformationService;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> transformXml(@RequestBody Map<String, String> request) throws Exception {
        if (request == null || !request.containsKey("sourceXml") || !request.containsKey("mappingJson")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request must contain 'sourceXml' and 'mappingJson'");
        }

        String sourceXml = request.get("sourceXml");
        String mappingJson = request.get("mappingJson");

        String transformedXml = xmlTransformationService.transformXml(sourceXml, mappingJson);
        return ResponseEntity.ok(transformedXml);
    }
}