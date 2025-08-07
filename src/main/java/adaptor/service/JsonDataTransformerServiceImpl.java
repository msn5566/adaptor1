package adaptor.service;

import adaptor.model.source.Order;
import adaptor.model.target.PurchaseOrder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.Marshaller;

@Service
@Slf4j
@RequiredArgsConstructor
public class JsonDataTransformerServiceImpl implements JsonDataTransformerService {

    private final ObjectMapper objectMapper;



    @Override
    public String transform(String sourceXml, String mappingJson) throws JAXBException, IOException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Order.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Order source = (Order) unmarshaller.unmarshal(new StringReader(sourceXml));

            JsonNode mappingRoot = objectMapper.readTree(mappingJson);
            PurchaseOrder target = transformPOJO(source, mappingRoot);


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
            String timestamp = formatter.format(Instant.now());
            String filename = "target_" + timestamp + ".xml";

             jaxbContext = JAXBContext.newInstance(PurchaseOrder.class);
             marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter sw = new StringWriter();
            marshaller.marshal(target, sw);
            String xmlString = sw.toString();

            // Log the generated XML
            log.info("Generated target XML: {}", xmlString);
            return xmlString;

        } catch (JAXBException | IOException e) {
            // Handle or rethrow exceptions as needed
            log.error("Error during transformation: ", e);
            throw e;
        }

    }

    public PurchaseOrder transformPOJO(Order source, JsonNode mappingRoot) {
        // Existing implementation

        PurchaseOrder target = new PurchaseOrder();

        target.setId(source.getOrderId());
        target.setDate(source.getOrderDate());


        adaptor.model.target.Client client = new adaptor.model.target.Client();
        client.setId(source.getCustomer().getCustomerId());
        client.setFullName(source.getCustomer().getName());
        client.setEmail(source.getCustomer().getContact().getEmail());
        client.setPhone(source.getCustomer().getContact().getPhone());
        target.setClient(client);




        adaptor.model.target.Products products = new adaptor.model.target.Products();
        source.getItems().getItem().forEach(sourceItem -> {
            adaptor.model.target.Product product = new adaptor.model.target.Product();
            product.setCode(sourceItem.getItemId());
            product.setName(sourceItem.getDescription());
            product.setQty(sourceItem.getQuantity());
            product.setUnitPrice(sourceItem.getPrice());
            products.getProduct().add(product);
        });
        target.setProducts(products);




        adaptor.model.target.Payment payment = new adaptor.model.target.Payment();
        payment.setCustomerId(source.getCustomer().getCustomerId());

        BigDecimal totalAmount = BigDecimal.valueOf(source.getItems().getItem().stream()
                .mapToDouble(item -> item.getQuantity() * item.getPrice()).sum());
        payment.setAmount(totalAmount.setScale(2).toString());

        payment.setStatus(booleanToPaidStatus(source.getBilling().isPaid()));
        target.setPayment(payment);


        return target;
    }

    public String booleanToPaidStatus(boolean paid) {
        return paid ? "Paid" : "Unpaid"; // Simplified logic
    }
}