package adaptor.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import adaptor.model.source.Order;
import adaptor.model.target.PurchaseOrder;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class JsonDataTransformerServiceImplTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private JsonDataTransformerServiceImpl jsonDataTransformerService;

    @Mock
    private JAXBContext jaxbContext;
    @Mock
    private Marshaller marshaller;
    @Mock
    private Unmarshaller unmarshaller;


    @Test
    void shouldTransform_whenValidInput() throws Exception {

        //todo: needs to implement
    }


    @Test
    void shouldThrowRuntimeException_whenTransformationFails() throws Exception{
        String sourceXml = "<order></order>";
        String mappingJson = "{}";
        when(objectMapper.readTree(mappingJson)).thenThrow(new RuntimeException("Transformation failed"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            jsonDataTransformerService.transform(sourceXml, mappingJson);
        });


    }

    @Test
    void testTransform_unmarshalException() throws Exception {
        String sourceXml = "<Order>...</Order>"; // Invalid XML
        String mappingJson = "{ \"mappings\": [] }";

        when(jaxbContext.createUnmarshaller()).thenReturn(unmarshaller);

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(new StringReader(sourceXml));

        when(unmarshaller.unmarshal(xmlStreamReader)).thenThrow(new IOException("Unmarshal error"));

        assertThrows(RuntimeException.class, () -> jsonDataTransformerService.transform(sourceXml, mappingJson));

    }


    @Test
    void testTransform_marshalException() throws Exception {
        String sourceXml = "<Order></Order>";
        String mappingJson = "{ \"mappings\": [] }";

        when(jaxbContext.createUnmarshaller()).thenReturn(unmarshaller);

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(new StringReader(sourceXml));

        when(unmarshaller.unmarshal(xmlStreamReader)).thenReturn(new Order());
        JsonNode jsonNode = new ObjectMapper().readTree(mappingJson);
        when(objectMapper.readTree(mappingJson)).thenReturn(jsonNode);


        when(jaxbContext.createMarshaller()).thenReturn(marshaller);
        doThrow(new IOException("Marshal error")).when(marshaller).marshal(any(PurchaseOrder.class), any(StringWriter.class));


        assertThrows(RuntimeException.class, () -> jsonDataTransformerService.transform(sourceXml, mappingJson));

    }




}