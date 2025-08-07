
package adaptor.model.source;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.xml.bind.annotation.XmlElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @XmlElement(name = "ItemId")
    private String itemId;

    @XmlElement(name = "Description")
    private String description;

    @XmlElement(name = "Quantity")
    private int quantity;

    @XmlElement(name = "Price")
    private double price;
}