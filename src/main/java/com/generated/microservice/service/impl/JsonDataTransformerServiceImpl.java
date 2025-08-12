
package com.generated.microservice.service.impl;

import com.generated.microservice.model.source.Order;
import com.generated.microservice.model.target.PurchaseOrder;
import com.generated.microservice.service.JsonDataTransformerService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.StringWriter;

@Service
@Slf4j
public class JsonDataTransformerServiceImpl implements JsonDataTransformerService {

    private final JsonDataTransformerService jsonDataTransformerService;

    public JsonDataTransformerServiceImpl(JsonDataTransformerService jsonDataTransformerService) {
        this.jsonDataTransformerService = jsonDataTransformerService;
    }
    @Override
    public PurchaseOrder transform(Order order) {
       try {
            PurchaseOrder purchaseOrder = jsonDataTransformerService.transform(order);
            return purchaseOrder;
        } catch (Exception e) {
           log.error("Error during transformation: {}", e.getMessage());
           return null; // Or throw an exception, depending on error handling strategy
        }
    }

}