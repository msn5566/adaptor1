
package com.generated.microservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.generated.microservice.model.source.Billing;
import com.generated.microservice.model.source.Customer;
import com.generated.microservice.model.source.Item;
import com.generated.microservice.model.source.Items;
import com.generated.microservice.model.source.Order;
import com.generated.microservice.model.target.Client;
import com.generated.microservice.model.target.Payment;
import com.generated.microservice.model.target.Product;
import com.generated.microservice.model.target.Products;
import com.generated.microservice.model.target.PurchaseOrder;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class JsonDataTransformerServiceImpl implements JsonDataTransformerService{

    private final ObjectMapper objectMapper;
    private JsonNode mappingRules;


    public JsonDataTransformerServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        try {
            InputStream mappingStream = getClass().getClassLoader().getResourceAsStream("mapping.json");
            if (mappingStream != null) {
                mappingRules = objectMapper.readTree(mappingStream);
            } else {
                log.error("Could not load mapping.json");
            }
        } catch (IOException e) {
            log.error("Error loading mapping.json: {}", e.getMessage(), e);
        }
    }


    public PurchaseOrder transform(Order order) {
        if (order == null) {
            return null;
        }
        try {
            PurchaseOrder purchaseOrder = new PurchaseOrder();

            if(mappingRules == null){
                log.warn("Mapping rules are not initialized. Returning empty purchase order.");
                return purchaseOrder;
            }
            JsonNode mappings = mappingRules.path("mappings");

            Client client = new Client();  // Initialize client here before the loop
            purchaseOrder.setClient(client); // Set the client immediately after initialization

            for (JsonNode mapping : mappings) {
                String sourceField = mapping.path("sourceField").asText();
                String targetField = mapping.path("targetField").asText();

                if (sourceField.equals("Order.Customer.Name")) {
                    if (order.getCustomer() != null) {
                        client.setFullName(order.getCustomer().getName());
                    }

                } else if (sourceField.equals("Order.Customer.Contact.Email")) {
                    if(order.getCustomer() != null && order.getCustomer().getContact() != null){
                        client.setEmail(order.getCustomer().getContact().getEmail());
                    }


                } else if (sourceField.equals("Order.Items.Item[]")) {
                    if(order.getItems() != null) {
                        Items sourceItems = order.getItems();
                        if (sourceItems != null && sourceItems.getItem() != null) {
                            List<Product> productList = new ArrayList<>();
                            for (Item sourceItem : sourceItems.getItem()) {
                                Product product = new Product();
                                for (JsonNode childMapping : mapping.path("children")) {

                                    if (childMapping.path("sourceField").asText().equals("ItemId")) {
                                        if (sourceItem != null) {
                                            product.setCode(sourceItem.getItemId());
                                        }
                                    } else if (childMapping.path("sourceField").asText().equals("Quantity")) {
                                        if (sourceItem != null) {
                                            product.setQty(sourceItem.getQuantity());
                                        }
                                    }
                                }
                                productList.add(product);
                            }
                            Products products = new Products();
                            products.setProduct(productList);
                            purchaseOrder.setProducts(products);
                        }
                    }
                } else if (sourceField.equals("Order.Billing.TotalAmount")) {
                    if(order.getBilling() != null) {
                        Payment payment = new Payment();
                        payment.setAmount(order.getBilling().getTotalAmount());
                        purchaseOrder.setPayment(payment);
                    }

                } else if (sourceField.equals("Order.Billing.Paid")) {
                    if (purchaseOrder.getPayment() == null) {
                        purchaseOrder.setPayment(new Payment());
                    }
                    if(order.getBilling() != null) {
                        purchaseOrder.getPayment().setStatus(booleanToPaidStatus(order.getBilling().isPaid()));
                    }
                }
            }


            return purchaseOrder;
        } catch (Exception e) {
            log.error("Transformation failed: {}", e.getMessage(), e);
            return null;
        }

    }



    private String booleanToPaidStatus(boolean paid) {
        return paid ? "Paid" : "Unpaid";
    }



}