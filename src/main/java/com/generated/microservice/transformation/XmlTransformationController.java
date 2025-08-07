package com.generated.microservice.transformation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transform")
public class XmlTransformationController {

    private final XmlTransformationService xmlTransformationService;

    public XmlTransformationController(XmlTransformationService xmlTransformationService) {
        this.xmlTransformationService = xmlTransformationService;
    }

    @PostMapping
    public Object transformXml(@RequestBody String xmlData) {
        return xmlTransformationService.transform(xmlData);
    }
}