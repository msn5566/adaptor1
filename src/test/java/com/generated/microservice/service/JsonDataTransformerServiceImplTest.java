
package com.generated.microservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.generated.microservice.model.source.*;
import com.generated.microservice.model.target.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JsonDataTransformerServiceImplTest {


    @InjectMocks
    private JsonDataTransformerServiceImpl jsonDataTransformerService;


    @Mock
    private ObjectMapper objectMapper;


    private JsonNode mappingsNode;

    @BeforeEach
    public void setUp() throws IOException {
        InputStream mappingStream = getClass().getClassLoader().getResourceAsStream("mapping.json");
        if (mappingStream == null) {
            throw new IOException("Could not load mapping.json");
        }
        mappingsNode = new ObjectMapper().readTree(mappingStream);

        when(objectMapper.readTree(Mockito.any(InputStream.class))).thenReturn(mappingsNode);
    }

    @Test
    public void testTransform_success() throws IOException {

        // Sample Source Data
        Order order = createSampleOrder();




        PurchaseOrder transformedOrder = jsonDataTransformerService.transform(order);


        // Assertions
        assertEquals("John Doe", transformedOrder.getClient().getFullName());
        assertEquals("john.doe@example.com", transformedOrder.getClient().getEmail());
        assertEquals(1, transformedOrder.getProducts().getProduct().size());
        assertEquals("ITEM123", transformedOrder.getProducts().getProduct().get(0).getCode());
        assertEquals(2, transformedOrder.getProducts().getProduct().get(0).getQty());
        assertEquals(199.99, transformedOrder.getPayment().getAmount());
        assertEquals("Paid", transformedOrder.getPayment().getStatus());

    }


    @Test
    public void testTransform_IOException() throws IOException {
        when(objectMapper.readTree(Mockito.any(InputStream.class))).thenThrow(new IOException("Error reading mapping file"));

        Order order = createSampleOrder();
        PurchaseOrder transformedOrder = jsonDataTransformerService.transform(order);
        assertNull(transformedOrder);
    }

    @Test
    public void testTransform_nullInput() throws IOException {

        PurchaseOrder transformedOrder = jsonDataTransformerService.transform(null);
        assertNull(transformedOrder);

    }


    private Order createSampleOrder() {
        Order order = new Order();
        Customer customer = new Customer();
        customer.setName("John Doe");
        Contact contact = new Contact();
        contact.setEmail("john.doe@example.com");
        customer.setContact(contact);
        order.setCustomer(customer);


        Item item = new Item();
        item.setItemId("ITEM123");
        item.setQuantity(2);
        List<Item> itemsList = new ArrayList<>();
        itemsList.add(item);

        Items items = new Items();
        items.setItem(itemsList);

        order.setItems(items);

        Billing billing = new Billing();
        billing.setTotalAmount(199.99);
        billing.setPaid(true);
        order.setBilling(billing);

        return order;
    }
}