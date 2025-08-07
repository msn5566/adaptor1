
package adaptor.model.target;

import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @XmlElement
    private String Code;
    @XmlElement
    private String Name;
    @XmlElement
    private int Qty;
    @XmlElement
    private double UnitPrice;
}