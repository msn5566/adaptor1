
package adaptor.service;

import adaptor.model.source.Order;
import adaptor.model.target.PurchaseOrder;
import adaptor.repository.PurchaseOrderRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

@Service
@Slf4j
public class MappingServiceImpl implements MappingService {

    private final ObjectMapper objectMapper;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final JsonDataTransformerService jsonDataTransformerService;


    public MappingServiceImpl(ObjectMapper objectMapper, PurchaseOrderRepository purchaseOrderRepository, JsonDataTransformerService jsonDataTransformerService) {
        this.objectMapper = objectMapper;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.jsonDataTransformerService = jsonDataTransformerService;
    }

    @Override
    public String generateMappedXml(InputStream sourceXml, InputStream mappingJson) throws IOException {
        try {
            Order sourceOrder = unmarshalSourceXml(sourceXml);
            JsonNode mapping = objectMapper.readTree(mappingJson);
            PurchaseOrder purchaseOrder = jsonDataTransformerService.transformPOJO(sourceOrder, mapping);
            purchaseOrderRepository.save(purchaseOrder); // Save to MongoDB

            return marshalTargetXml(purchaseOrder);
        }  catch (IOException e) {
            log.error("Error during JSON processing: {}", e.getMessage(), e);
            throw new IOException("Error during JSON processing", e);
        } catch (JAXBException e) {
            log.error("Error during XML processing: {}", e.getMessage(), e);
            throw new IOException("Error during XML processing", e);
        }
    }


    public Order unmarshalSourceXml(InputStream sourceXml) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Order.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (Order) unmarshaller.unmarshal(sourceXml);
    }

    public String marshalTargetXml(PurchaseOrder targetOrder) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(PurchaseOrder.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter writer = new StringWriter();
        marshaller.marshal(targetOrder, writer);
        return writer.toString();
    }

}