
package adaptor.service;

import adaptor.model.source.Order;
import adaptor.model.target.Client;
import adaptor.model.target.Payment;
import adaptor.model.target.Product;
import adaptor.model.target.Products;
import adaptor.model.target.PurchaseOrder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class JsonDataTransformerServiceImpl implements JsonDataTransformerService {

    private final ObjectMapper objectMapper;

    public JsonDataTransformerServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    public String transform(String sourceXml, String mappingJson) throws IOException, JAXBException {
        Order sourceOrder = unmarshalSourceXml(sourceXml);
        JsonNode mappingRoot = objectMapper.readTree(mappingJson);
        PurchaseOrder targetOrder = transformPOJO(sourceOrder, mappingRoot);
        return marshalTargetXml(targetOrder);
    }



    private Order unmarshalSourceXml(String sourceXml) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Order.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StringReader reader = new StringReader(sourceXml);
        return (Order) unmarshaller.unmarshal(reader);
    }

    private String marshalTargetXml(PurchaseOrder targetOrder) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(PurchaseOrder.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter writer = new StringWriter();
        marshaller.marshal(targetOrder, writer);
        return writer.toString();
    }


    // Helper function to simplify field access with dot notation
    private Object getFieldValue(Object obj, String fieldPath) {
        String[] fields = fieldPath.split("\\.");
        Object current = obj;
        for (String field : fields) {
            try {
                if (current == null) {
                    return null;
                }
                Field declaredField = current.getClass().getDeclaredField(field);
                declaredField.setAccessible(true);
                current = declaredField.get(current);
            } catch (Exception e) {
                log.error("Error getting field value for path: {} - {}", fieldPath, e.getMessage());
                return null; // Or throw an exception depending on your error handling strategy
            }
            if (current == null) {
                return null;
            }
        }
        return current;
    }


    public PurchaseOrder transformPOJO(Order sourceOrder, JsonNode mappingRoot) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        JsonNode mappings = mappingRoot.path("mappings");

        if (mappings == null || !mappings.isArray()) {
            log.warn("Mappings array is null or not an array. Returning empty PurchaseOrder.");
            return purchaseOrder;
        }


        for (JsonNode mapping : mappings) {
            String sourceField = mapping.path("sourceField").asText();
            String targetField = mapping.path("targetField").asText();
            String transform = mapping.path("transform").asText();

            if (sourceField.endsWith("[]") && targetField.endsWith("[]")) { // Handling collections
                List<Object> sourceList = (List<Object>) getFieldValue(sourceOrder, sourceField.replace("[]", ""));
                List<Object> targetList = new ArrayList<>();

                if (sourceList != null) {
                    for (Object sourceItem : sourceList) {
                        Object targetItem = null;
                        // Handle array mappings here. Assuming Product for now
                        if (targetField.contains("Product")) {
                            targetItem = new Product();
                        }

                        JsonNode children = mapping.path("children");
                        if (!children.isMissingNode()) {
                            for (JsonNode child : children) {
                                String childSource = child.path("sourceField").asText();
                                String childTarget = child.path("targetField").asText();

                                Object value = getFieldValue(sourceItem, childSource);
                                setFieldValue(targetItem, childTarget, value);
                            }
                        }

                        targetList.add(targetItem);
                    }

                    // Setting list of target POJOs into the target object
                    setFieldValue(purchaseOrder, targetField.replace("[]",""), targetList);
                }

            } else {

                Object value = getFieldValue(sourceOrder, sourceField);

                if ("booleanToPaidStatus".equals(transform)) {
                    value = booleanToPaidStatus((boolean) value);
                }
               setFieldValue(purchaseOrder, targetField, value);

            }
        }

        return purchaseOrder;
    }



    // Helper function to simplify setting fields via reflection
    private void setFieldValue(Object targetObject, String fieldName, Object value) {
        if (targetObject == null) {
            log.warn("Target object is null, cannot set field: {}", fieldName);
            return;
        }
        String[] path = fieldName.split("\\.");

        try {
            Object current = targetObject;
            for (int i = 0; i < path.length - 1; i++) {
                String field = path[i];
                Field declaredField = current.getClass().getDeclaredField(field);
                declaredField.setAccessible(true);
                Object next = declaredField.get(current);
                if (next == null) {
                    try {
                        next = declaredField.getType().getDeclaredConstructor().newInstance();
                        declaredField.set(current, next);
                    } catch (Exception e) {
                        log.error("Could not instantiate new object for field {} of type {}", field, declaredField.getType());
                        return;
                    }
                }
                current = next;

            }
            Field lastField = current.getClass().getDeclaredField(path[path.length - 1]);
            lastField.setAccessible(true);
            lastField.set(current, value);
        }
        catch(Exception e) {
             // handle exception
            log.error("Failed to set field {} due to {}", fieldName, e.getMessage(), e);
        }
    }


    private String booleanToPaidStatus(boolean paid) {
        return paid ? "Paid" : "Unpaid";
    }

    // Other transformation functions (TO_INT, TO_STRING, TO_DECIMAL, CONCAT) can be added here


}