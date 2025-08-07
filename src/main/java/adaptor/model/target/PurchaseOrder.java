
package adaptor.model.target;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class PurchaseOrder {
    private String Id;
    private String Date;
    private Client Client;
    private Products Products;
    private Payment Payment;
}