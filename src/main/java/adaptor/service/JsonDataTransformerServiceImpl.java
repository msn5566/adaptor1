
package adaptor.service;

import adaptor.model.source.Order;
import adaptor.model.target.PurchaseOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class JsonDataTransformerServiceImpl implements JsonDataTransformerService {

    private final ObjectMapper objectMapper;


    @Override
    public PurchaseOrder transformPOJO(Order source, String mappingJson) throws Exception {
        // Existing transformation logic using mappingJson
        // ... (Implementation for mapping from source to target based on mappingJson)
        return new PurchaseOrder(); // Placeholder – replace with actual transformation logic. Returning a non-null object
    }

    @Override
    public String marshalTarget(PurchaseOrder target) throws JAXBException {
        if (target == null) {
            throw new IllegalArgumentException("Target object cannot be null");
        }
        JAXBContext jaxbContext = JAXBContext.newInstance(PurchaseOrder.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE); //To remove the xml declaration
        StringWriter sw = new StringWriter();
        marshaller.marshal(target, sw);
        return sw.toString();
    }

    @Override
    public String transform(String sourceXml, String mappingJson) throws JAXBException, Exception {
        throw new UnsupportedOperationException("transform method is not supported in this implementation");
    }

    private String booleanToPaidStatus(boolean paid) {
        return paid ? "Paid" : "Unpaid";
    }

    private int toInt(String value) {
        return Integer.parseInt(value);
    }

    private String toString(Object value) {
        return String.valueOf(value);
    }

    private BigDecimal toDecimal(String value) {
        return new BigDecimal(value);
    }

    private String concat(String str1, String str2) {
        return str1 + str2;
    }
}