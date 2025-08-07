package adaptor.service;

import adaptor.model.source.*;
import adaptor.model.target.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class JsonDataTransformerServiceImpl implements JsonDataTransformerService {

    private final ObjectMapper objectMapper;


    public String transform(String sourceXml, String mappingJson) {
        try {
            Order sourceOrder = unmarshalSourceXml(sourceXml);
            PurchaseOrder targetOrder = new PurchaseOrder();
            JsonNode mappings = objectMapper.readTree(mappingJson);

            applyMappings(sourceOrder, targetOrder, mappings);
            String targetXml = marshalTargetXml(targetOrder);

            return targetXml;
        } catch (Exception e) {
            log.error("Transformation failed: {}", e.getMessage());
            throw new RuntimeException("Transformation failed: " + e.getMessage(), e); // Re-throw for controller to handle
        }
    }


    private Order unmarshalSourceXml(String sourceXml) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(Order.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (Order) unmarshaller.unmarshal(new StreamSource(new StringReader(sourceXml)));
    }

    private String marshalTargetXml(PurchaseOrder targetOrder) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(PurchaseOrder.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE); // Remove xml declaration
        StringWriter writer = new StringWriter();
        marshaller.marshal(targetOrder, writer);
        return writer.toString();
    }


    private void applyMappings(Object source, Object target, JsonNode mappings) throws Exception {
        if (mappings.isArray()) {
            for (JsonNode mapping : mappings) {
                String sourceField = mapping.get("sourceField").asText();
                String targetField = mapping.get("targetField").asText();

                Object sourceValue = getValueFromPath(source, sourceField);
                // Handle Collections (Arrays/Lists)
                if (sourceValue instanceof List) {
                    JsonNode children = mapping.get("children");
                    List<?> sourceList = (List<?>) sourceValue;
                    List<Object> targetList = createTargetList(target, targetField, sourceList.size());

                    for (int i = 0; i < sourceList.size(); i++) {
                        applyMappings(sourceList.get(i), targetList.get(i), children);
                    }
                    continue; // Skip setting the list itself; children have been processed.
                }


                // Apply transformations if specified
                String transform = mapping.path("transform").asText();
                if (!transform.isEmpty()) {
                    switch (transform) {
                        case "booleanToPaidStatus":
                            sourceValue = booleanToPaidStatus((boolean) sourceValue);
                            break;
                        // Add other transformation cases here
                    }
                }

                 setValueByPath(target, targetField, sourceValue);
            }
        }
    }

    // ... (Helper methods getValueFromPath, setValueByPath, booleanToPaidStatus, createTargetList remain as before)

    private String booleanToPaidStatus(boolean paid) {
        return paid ? "Paid" : "Unpaid";
    }


    // Helper methods for nested object access (getValueFromPath and setValueByPath),
    // and dynamic list creation (createTargetList).
    // These are provided in the previous responses and not repeated here for brevity.
        private Object getValueFromPath(Object object, String path) throws Exception {
        // Implementation as provided in previous responses
        return null;
    }

    private void setValueByPath(Object object, String path, Object value) throws Exception {
         // Implementation as provided in previous responses
    }

        private <T> List<T> createTargetList(Object target, String targetField, int size) throws Exception {
            // Implementation as provided in previous responses
            return null;
    }
}