
package adaptor.model.target;

import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @XmlElement(name = "CustomerId")
    private String customerId;

    @XmlElement(name = "Amount")
    private String amount;

    @XmlElement(name = "Status")
    private String status;
}