
package adaptor.service;

import adaptor.model.source.Order;
import adaptor.model.target.PurchaseOrder;
import adaptor.repository.PurchaseOrderRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MappingServiceImplTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private PurchaseOrderRepository purchaseOrderRepository;

    @Mock
    private JsonDataTransformerService jsonDataTransformerService;

    @InjectMocks
    private MappingServiceImpl mappingService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void shouldGenerateMappedXml_whenInputsAreValid() throws IOException, JAXBException {
        String sourceXmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
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
        InputStream sourceXmlStream = new ByteArrayInputStream(sourceXmlString.getBytes());
        InputStream mappingJsonStream = new ByteArrayInputStream("{}".getBytes());


        Order mockSourceOrder = new Order(); // Replace with a mock Order object
        JsonNode mockMapping = mock(JsonNode.class);
        PurchaseOrder mockPurchaseOrder = new PurchaseOrder();// Replace with a mock PurchaseOrder object
        String expectedXml = "<PurchaseOrder>...</PurchaseOrder>"; // Replace with expected XML

        when(objectMapper.readTree(any(InputStream.class))).thenReturn(mockMapping);
        when(jsonDataTransformerService.transformPOJO(any(Order.class), any(JsonNode.class))).thenReturn(mockPurchaseOrder);
        when(mappingService.marshalTargetXml(any(PurchaseOrder.class))).thenReturn(expectedXml);
        when(mappingService.unmarshalSourceXml(any(InputStream.class))).thenReturn(mockSourceOrder);
        doNothing().when(purchaseOrderRepository).save(any(PurchaseOrder.class));

        String result = mappingService.generateMappedXml(sourceXmlStream, mappingJsonStream);

        assertEquals(expectedXml, result);
        verify(purchaseOrderRepository).save(any(PurchaseOrder.class));
        verifyNoMoreInteractions(purchaseOrderRepository);

    }



    @Test
    void shouldThrowJAXBException_whenUnmarshalingFails() throws IOException, JAXBException {
        InputStream sourceXmlStream = new ByteArrayInputStream("<xml>test</xml>".getBytes());
        InputStream mappingJsonStream = new ByteArrayInputStream("{}".getBytes());

        JAXBContext jaxbContext = JAXBContext.newInstance(Order.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();


        when(objectMapper.readTree(any(InputStream.class))).thenReturn(mock(JsonNode.class));
        when(jsonDataTransformerService.transformPOJO(any(Order.class), any(JsonNode.class))).thenReturn(mock(PurchaseOrder.class));
        when(mappingService.marshalTargetXml(any(PurchaseOrder.class))).thenReturn("<PurchaseOrder>...</PurchaseOrder>");

        when(mappingService.unmarshalSourceXml(any(InputStream.class))).thenThrow(new JAXBException("Unmarshaling failed"));


        assertThrows(IOException.class, () -> mappingService.generateMappedXml(sourceXmlStream, mappingJsonStream));


    }






}