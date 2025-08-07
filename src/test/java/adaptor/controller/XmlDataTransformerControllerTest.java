
package adaptor.controller;

import adaptor.service.JsonDataTransformerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class XmlDataTransformerControllerTest {

    @Mock
    private JsonDataTransformerService jsonDataTransformerService;

    @InjectMocks
    private XmlDataTransformerController xmlDataTransformerController;

    private MockMultipartFile sourceXmlFile;
    private MockMultipartFile mappingJsonFile;


    @BeforeEach
    public void setup() {
        sourceXmlFile = new MockMultipartFile("sourceXml", "source.xml", "text/xml", "<order></order>".getBytes());
        mappingJsonFile = new MockMultipartFile("mappingJson", "mapping.json", "application/json", "{}".getBytes());

    }


    @Test
    void shouldTransformXml_whenValidInput() throws Exception {
        String expectedXml = "<purchaseOrder></purchaseOrder>";
        when(jsonDataTransformerService.transform(Mockito.anyString(), Mockito.anyString())).thenReturn(expectedXml);


        ResponseEntity<String> response = xmlDataTransformerController.transformXml(sourceXmlFile, mappingJsonFile);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedXml, response.getBody());
        Mockito.verify(jsonDataTransformerService).transform(Mockito.anyString(), Mockito.anyString());

    }

    @Test
    void shouldReturnBadRequest_whenIllegalArgumentException() throws Exception {
        String errorMessage = "Invalid input";
        when(jsonDataTransformerService.transform(Mockito.anyString(), Mockito.anyString())).thenThrow(new IllegalArgumentException(errorMessage));

        ResponseEntity<String> response = xmlDataTransformerController.transformXml(sourceXmlFile, mappingJsonFile);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
    }

    @Test
    void shouldReturnInternalServerError_whenIOException() throws Exception {
        String errorMessage = "IO error";
        when(jsonDataTransformerService.transform(Mockito.anyString(), Mockito.anyString())).thenThrow(new IOException(errorMessage));
        ResponseEntity<String> response = xmlDataTransformerController.transformXml(sourceXmlFile, mappingJsonFile);


        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error processing files: " + errorMessage, response.getBody());
    }

    @Test
    void shouldReturnInternalServerError_whenGenericException() throws Exception {
        String errorMessage = "Generic error";
        when(jsonDataTransformerService.transform(Mockito.anyString(), Mockito.anyString())).thenThrow(new RuntimeException(errorMessage));
        ResponseEntity<String> response = xmlDataTransformerController.transformXml(sourceXmlFile, mappingJsonFile);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred: " + errorMessage, response.getBody());
    }

}