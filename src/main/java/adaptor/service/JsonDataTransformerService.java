
package adaptor.service;

import adaptor.model.source.Order;
import adaptor.model.target.PurchaseOrder;

import jakarta.xml.bind.JAXBException;
import java.io.IOException;

public interface JsonDataTransformerService {

    PurchaseOrder transformPOJO(Order source, String mappingJson) throws Exception;

    String marshalTarget(PurchaseOrder target) throws JAXBException;

    String transform(String sourceXml, String mappingJson) throws JAXBException, IOException, Exception;


}