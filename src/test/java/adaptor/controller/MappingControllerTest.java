
package adaptor.controller;

import adaptor.service.MappingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MappingControllerTest {

    @Mock
    private MappingService mappingService;

    @InjectMocks
    private MappingController mappingController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(mappingController).build();
    }

    @Test
    void shouldReturn201_whenMappingSucceeds() throws IOException {
        // Sample input streams (replace with actual data)
        InputStream sourceXmlStream = getClass().getClassLoader().getResourceAsStream("source.xml");
        InputStream mappingJsonStream = getClass().getClassLoader().getResourceAsStream("mapping.json");
        MockMultipartFile sourceXml = new MockMultipartFile("sourceXml", "source.xml", MediaType.APPLICATION_XML_VALUE, sourceXmlStream);
        MockMultipartFile mappingJson = new MockMultipartFile("mappingJson", "mapping.json", MediaType.APPLICATION_JSON_VALUE, mappingJsonStream);



        String expectedXml = "<PurchaseOrder>...</PurchaseOrder>"; // Replace with expected XML
        when(mappingService.generateMappedXml(any(InputStream.class), any(InputStream.class))).thenReturn(expectedXml);

        ResponseEntity<String> response = mappingController.generateMappedXml(sourceXml, mappingJson);


        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedXml, response.getBody());

        verify(mappingService).generateMappedXml(any(InputStream.class), any(InputStream.class));
        verifyNoMoreInteractions(mappingService);
    }


    @Test
    void shouldThrowException_whenIOExceptionOccurs() throws IOException {
        // Sample input streams (replace with actual data)
        InputStream sourceXmlStream = getClass().getClassLoader().getResourceAsStream("source.xml");
        InputStream mappingJsonStream = getClass().getClassLoader().getResourceAsStream("mapping.json");

        MockMultipartFile sourceXml = new MockMultipartFile("sourceXml", "source.xml", MediaType.APPLICATION_XML_VALUE, sourceXmlStream);
        MockMultipartFile mappingJson = new MockMultipartFile("mappingJson", "mapping.json", MediaType.APPLICATION_JSON_VALUE, mappingJsonStream);


        when(mappingService.generateMappedXml(any(InputStream.class), any(InputStream.class))).thenThrow(new IOException("Error reading file"));


        assertThrows(IOException.class, () -> {
            mappingController.generateMappedXml(sourceXml, mappingJson);
        });

        verify(mappingService).generateMappedXml(any(InputStream.class), any(InputStream.class));
    }



}