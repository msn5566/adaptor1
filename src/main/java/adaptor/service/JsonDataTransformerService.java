package adaptor.service;

import adaptor.model.source.Order;
import adaptor.model.target.PurchaseOrder;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface JsonDataTransformerService {

    String transform(String sourceXml, String mappingJson) throws JAXBException, IOException;

}