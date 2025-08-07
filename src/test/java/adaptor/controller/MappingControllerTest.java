package adaptor.controller;

import adaptor.model.source.Order;
import adaptor.model.target.PurchaseOrder;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MappingControllerTest {

    @Mock
    private JsonDataTransformerService jsonDataTransformerService;

    @InjectMocks
    private MappingController mappingController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(mappingController).build();
    }


    @Test
    void shouldTransformOrderToXml_whenValidInput() throws Exception {
        Order sourceOrder = new Order(); // Create a sample source Order object
        PurchaseOrder purchaseOrder = new PurchaseOrder(); // Create a sample PurchaseOrder object
        String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><PurchaseOrder/>";// Example XML output

        when(jsonDataTransformerService.transformPOJO(Mockito.any(Order.class), Mockito.anyString())).thenReturn(purchaseOrder);

        when(jsonDataTransformerService.marshalTarget(any(PurchaseOrder.class))).thenReturn(expectedXml);


        mockMvc.perform(MockMvcRequestBuilders.post("/api/mapping")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"order_id\":\"123\"}")) // Example JSON request body
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_XML_VALUE))
                .andExpect(MockMvcResultMatchers.content().string(expectedXml));


        Mockito.verify(jsonDataTransformerService).transformPOJO(Mockito.any(Order.class), Mockito.anyString());
        Mockito.verify(jsonDataTransformerService).marshalTarget(Mockito.any(PurchaseOrder.class));

    }

    @Test
    void shouldReturnInternalServerError_whenExceptionIsThrown() throws Exception {
        Order sourceOrder = new Order();


        when(jsonDataTransformerService.transformPOJO(any(Order.class), any(String.class))).thenThrow(new Exception("Transformation failed"));


        mockMvc.perform(MockMvcRequestBuilders.post("/api/mapping")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"order_id\":\"123\"}")) // Example JSON request body

                .andExpect(MockMvcResultMatchers.status().isInternalServerError());

        Mockito.verify(jsonDataTransformerService).transformPOJO(Mockito.any(Order.class), Mockito.anyString());

    }


}