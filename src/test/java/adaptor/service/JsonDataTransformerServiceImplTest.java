
package adaptor.service;

import adaptor.model.source.Order;
import adaptor.model.target.PurchaseOrder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JsonDataTransformerServiceImplTest {


    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private JsonDataTransformerServiceImpl jsonDataTransformerService;



    @Test
    void testTransform_success() throws IOException, JAXBException {

        String sourceXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Order>\n" +
                "    <OrderId>ORD12345</OrderId>\n" +
                "    <Customer>\n" +
                "        <Name>Jane Smith</Name>\n" +
                "        <Contact>\n" +
                "            <Email>jane@example.com</Email>\n" +
                "        </Contact>\n" +
                "    </Customer>\n" +
                "    <Billing>\n" +
                "        <Paid>true</Paid>\n" +
                "</Billing>\n" +
                "</Order>";


        String mappingJson = "{\"mappings\": []}"; // Simplified mapping JSON for testing purposes

        // Mock objectMapper to return a sample JsonNode
        JsonNode mockJsonNode = mock(JsonNode.class);
        when(objectMapper.readTree(mappingJson)).thenReturn(mockJsonNode);
        when(mockJsonNode.path("mappings")).thenReturn(mock(JsonNode.class));


        String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<PurchaseOrder>\n" +
                "    <Id/>\n" +
                "    <Date/>\n" +
                "    <Client/>\n" +
                "    <Products/>\n" +
                "    <Payment/>\n" +
                "</PurchaseOrder>\n"; // expected XML

        String result = jsonDataTransformerService.transform(sourceXml, mappingJson);

        assertEquals(normalizeXml(expectedXml), normalizeXml(result)); // Compare normalized XML strings

    }



    @Test
    void testTransform_JAXBException() throws IOException {

        String sourceXml = "invalid xml";
        String mappingJson = "{}";

        when(objectMapper.readTree(anyString())).thenReturn(mock(JsonNode.class));


        assertThrows(JAXBException.class, () -> {
            jsonDataTransformerService.transform(sourceXml, mappingJson);
        });


    }

    // Helper function to normalize XML strings for comparison
    private String normalizeXml(String xml) {
        return xml.replace("\r\n", "\n").replace("\t", "    ").trim(); // Normalize line endings and tabs
    }



    @Test
    void testTransformPOJO() throws IOException {

        String mappingJson = "{\"mappings\": []}"; // Simplified mapping JSON for testing purposes
        Order order = new Order();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode mapping = mapper.readTree(mappingJson);
        PurchaseOrder pojo = jsonDataTransformerService.transformPOJO(order, mapping);
        assertEquals(PurchaseOrder.class, pojo.getClass());

    }

}