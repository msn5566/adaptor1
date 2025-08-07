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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DataTransformationControllerTest {

    @Mock
    private JsonDataTransformerService jsonDataTransformerService;

    @InjectMocks
    private DataTransformationController dataTransformationController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(dataTransformationController).build();

    }
    String sourceXml = "<order>\n" +
            "    <orderId>12345</orderId>\n" +
            "    <orderDate>2024-07-24</orderDate>\n" +
            "    <customer>\n" +
            "        <customerId>CUST001</customerId>\n" +
            "        <name>John Doe</name>\n" +
            "        <contact>\n" +
            "            <email>john.doe@example.com</email>\n" +
            "            <phone>123-456-7890</phone>\n" +
            "        </contact>\n" +
            "    </customer>\n" +
            "    <items>\n" +
            "        <item>\n" +
            "            <itemId>ITEM001</itemId>\n" +
            "            <description>Product A</description>\n" +
            "            <quantity>2</quantity>\n" +
            "            <price>10.00</price>\n" +
            "        </item>\n" +
            "    </items>\n" +
            "    <billing>\n" +
            "        <paid>true</paid>\n" +
            "    </billing>\n" +
            "</order>";


    String mappingJson = "{}";
    String transformedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<purchaseOrder>\n" +
            "    <id>12345</id>\n" +
            "    <date>2024-07-24</date>\n" +
            "    <client>\n" +
            "        <id>CUST001</id>\n" +
            "        <fullName>John Doe</fullName>\n" +
            "        <email>john.doe@example.com</email>\n" +
            "        <phone>123-456-7890</phone>\n" +
            "    </client>\n" +
            "    <products>\n" +
            "        <product>\n" +
            "            <code>ITEM001</code>\n" +
            "            <name>Product A</name>\n" +
            "            <qty>2</qty>\n" +
            "            <unitPrice>10.00</unitPrice>\n" +
            "        </product>\n" +
            "    </products>\n" +
            "    <payment>\n" +
            "        <customerId>CUST001</customerId>\n" +
            "        <amount>20.00</amount>\n" +
            "        <status>Paid</status>\n" +
            "    </payment>\n" +
            "</purchaseOrder>\n";


    @Test
    void shouldTransformData_whenValidRequest() throws Exception {
        String requestBody = "{\"sourceXml\": \"" + sourceXml + "\", \"mappingJson\": " + mappingJson + "}";

        when(jsonDataTransformerService.transform(Mockito.anyString(), Mockito.anyString())).thenReturn(transformedXml);


        mockMvc.perform(post("/api/transform")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().xml(transformedXml));


        Mockito.verify(jsonDataTransformerService).transform(sourceXml, mappingJson);


    }

    @Test
    void shouldThrowException_whenTransformFails() throws Exception {

        String requestBody = "{\"sourceXml\": \"" + sourceXml + "\", \"mappingJson\": " + mappingJson + "}";

        when(jsonDataTransformerService.transform(Mockito.anyString(), Mockito.anyString()))
                .thenThrow(new JAXBException("Transformation failed"));

        mockMvc.perform(post("/api/transform")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is5xxServerError());

        Mockito.verify(jsonDataTransformerService).transform(sourceXml, mappingJson);
    }

}