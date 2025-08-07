package adaptor.service;

import adaptor.model.target.PurchaseOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class JsonDataTransformerServiceImplTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private JsonDataTransformerServiceImpl jsonDataTransformerService;


    @Test
    void shouldMarshalTarget_whenValidPurchaseOrder() throws Exception {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        String expectedXml = "<PurchaseOrder/>";

        String actualXml = jsonDataTransformerService.marshalTarget(purchaseOrder);

        assertEquals(expectedXml, actualXml);
    }



    @Test
    void shouldThrowJAXBException_whenMarshalTargetFails() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            jsonDataTransformerService.marshalTarget(null);
        });

        assertEquals("Target object cannot be null", exception.getMessage());
    }

}