
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
    @XmlElement
    private String Id;
    @XmlElement
    private String Date;
    @XmlElement
    private Client Client;
    @XmlElement
    private Products Products;
    @XmlElement
    private Payment Payment;
}