
package adaptor.service;

import adaptor.model.source.Order;
import adaptor.model.target.PurchaseOrder;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import jakarta.xml.bind.JAXBException;

public interface JsonDataTransformerService {

    String transform(String sourceXml, String mappingJson) throws IOException, JAXBException;

    PurchaseOrder transformPOJO(Order sourceOrder, JsonNode mappingRoot);
}