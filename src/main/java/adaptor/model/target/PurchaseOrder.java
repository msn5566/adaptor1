package adaptor.model.target;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "PurchaseOrder")
public class PurchaseOrder {

    private String id;
    private String date;
    private Client client;
    private Products products;
    private Payment payment;

    @XmlElement(name = "Id")
    public String getId() {
        return id;
    }

    @XmlElement(name = "Date")
    public String getDate() {
        return date;
    }

    @XmlElement(name = "Client")
    public Client getClient() {
        return client;
    }

    @XmlElement(name = "Products")
    public Products getProducts() {
        return products;
    }

    @XmlElement(name = "Payment")
    public Payment getPayment() {
        return payment;
    }
}