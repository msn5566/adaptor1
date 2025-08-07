package adaptor.service;

import adaptor.model.source.Billing;
import adaptor.model.source.Contact;
import adaptor.model.source.Customer;
import adaptor.model.source.Item;
import adaptor.model.source.Items;
import adaptor.model.source.Order;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class JsonDataTransformerServiceImplTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private JsonDataTransformerServiceImpl jsonDataTransformerService;

    @Test
    void shouldTransform_whenValidInput() throws JAXBException, IOException {
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


        String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
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

        when(objectMapper.readTree(mappingJson)).thenReturn(mock(JsonNode.class));


        String result = jsonDataTransformerService.transform(sourceXml, mappingJson);


        assertContains(result, "<id>12345</id>");
        assertContains(result, "<date>2024-07-24</date>");
        assertContains(result, "<fullName>John Doe</fullName>");
        assertContains(result, "<status>Paid</status>");
        assertContains(result, "<amount>20.00</amount>");


        verify(objectMapper).readTree(mappingJson);



    }


    @Test
    void shouldThrowJAXBException_whenInvalidSourceXml() throws IOException {
        String invalidSourceXml = "<invalid_xml>";
        String mappingJson = "{}";
        when(objectMapper.readTree(mappingJson)).thenReturn(mock(JsonNode.class));

        assertThrows(JAXBException.class, () -> jsonDataTransformerService.transform(invalidSourceXml, mappingJson));
    }


    @Test
    void shouldThrowIOException_whenInvalidMappingJson() {
        String sourceXml = "<order></order>"; // A simple valid XML
        String invalidMappingJson = "invalid_json";


        assertThrows(IOException.class, () -> jsonDataTransformerService.transform(sourceXml, invalidMappingJson));
    }

    // Helper function to assert that a string contains a substring
    private void assertContains(String text, String substring) {

        boolean contains = text.contains(substring);
        if(!contains){
            System.out.println(text);
        }

        assertEquals(true, contains);

    }

    @Test
    void testTransformPOJO() {
        Order source = createTestOrder();
        JsonNode mappingRoot = mock(JsonNode.class);

        adaptor.model.target.PurchaseOrder target = jsonDataTransformerService.transformPOJO(source, mappingRoot);

        verifyTargetPurchaseOrder(target);

    }

    @Test
    void testBooleanToPaidStatus() {
        assertEquals("Paid", jsonDataTransformerService.booleanToPaidStatus(true));
        assertEquals("Unpaid", jsonDataTransformerService.booleanToPaidStatus(false));
    }


    // Helper methods for creating test data and verification


    private Order createTestOrder() {
        Order order = new Order();
        order.setOrderId("12345");
        order.setOrderDate("2024-07-24");

        Customer customer = new Customer();
        customer.setCustomerId("CUST001");
        customer.setName("John Doe");
        Contact contact = new Contact();
        contact.setEmail("john.doe@example.com");
        contact.setPhone("123-456-7890");
        customer.setContact(contact);
        order.setCustomer(customer);

        Items items = new Items();
        Item item = new Item();
        item.setItemId("ITEM001");
        item.setDescription("Product A");
        item.setQuantity(2);
        item.setPrice(10.00);
        items.setItem(List.of(item));  // Use List.of for creating an immutable list
        order.setItems(items);

        Billing billing = new Billing();
        billing.setPaid(true);
        order.setBilling(billing);

        return order;
    }


    private void verifyTargetPurchaseOrder(adaptor.model.target.PurchaseOrder target) {

        assertEquals("12345", target.getId());
        assertEquals("2024-07-24", target.getDate());

        adaptor.model.target.Client client = target.getClient();
        assertEquals("CUST001", client.getId());
        assertEquals("John Doe", client.getFullName());
        assertEquals("john.doe@example.com", client.getEmail());
        assertEquals("123-456-7890", client.getPhone());

        adaptor.model.target.Products products = target.getProducts();
        List<adaptor.model.target.Product> productList = products.getProduct();
        assertEquals(1, productList.size()); // Verify the expected size of the list

        adaptor.model.target.Product product = productList.get(0); // Get the first product from the list
        assertEquals("ITEM001", product.getCode());
        assertEquals("Product A", product.getName());
        assertEquals(2, product.getQty());
        assertEquals(BigDecimal.valueOf(10.00), product.getUnitPrice());


        adaptor.model.target.Payment payment = target.getPayment();
        assertEquals("CUST001", payment.getCustomerId());
        assertEquals("20.00", payment.getAmount());
        assertEquals("Paid", payment.getStatus());
    }

}