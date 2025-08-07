
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

    @XmlElement(name = "Id")
    private String id;

    @XmlElement(name = "Date")
    private String date;

    @XmlElement(name = "Client")
    private Client client;

    @XmlElement(name = "Products")
    private Products products;

    @XmlElement(name = "Payment")
    private Payment payment;
}