package adaptor.model.target;

import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private String code;
    private String name;
    private int qty;
    private double unitPrice;

    @XmlElement(name = "Code")
    public String getCode() {
        return code;
    }

    @XmlElement(name = "Name")
    public String getName() {
        return name;
    }

    @XmlElement(name = "Qty")
    public int getQty() {
        return qty;
    }

    @XmlElement(name = "UnitPrice")
    public double getUnitPrice() {
        return unitPrice;
    }
}