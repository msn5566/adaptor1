
package com.generated.microservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@org.mockito.junit.jupiter.MockitoSettings(strictness = Strictness.LENIENT)
class XmlTransformationServiceTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private XmlMapper xmlMapper;

    @InjectMocks
    private XmlTransformationService xmlTransformationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldTransformXml_whenValidInput() throws Exception {
        String sourceXml = "<root><element1>value1</element1><element2>value2</element2></root>";
        String mappingJson = "{\"target\":{\"root\":\"newRoot\"},\"mapping\":[{\"source\":\"element1\",\"target\":\"newElement1\"},{\"source\":\"element2\",\"target\":\"newElement2\"}]}";
        String expectedXml = "<newRoot><newElement1>value1</newElement1><newElement2>value2</newElement2></newRoot>";

        ObjectMapper realObjectMapper = new ObjectMapper();
        JsonNode jsonNode = realObjectMapper.readTree(mappingJson);
        when(objectMapper.readTree(mappingJson)).thenReturn(jsonNode);


        String transformedXml = xmlTransformationService.transformXml(sourceXml, mappingJson);

        assertEquals(expectedXml, transformedXml.replaceAll("\\r?\\n", "").replaceAll(" ", ""));
    }


    @Test
    void shouldReturnEmpty_whenMappingJsonIsNull() throws Exception {


        String transformedXml = xmlTransformationService.transformXml("<root></root>", null);
        assertEquals("<newRoot/>", transformedXml.replaceAll("\\r?\\n", "").replaceAll(" ", ""));


    }




}