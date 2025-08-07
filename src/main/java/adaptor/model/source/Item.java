package adaptor.model.source;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
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