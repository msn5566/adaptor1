
package com.generated.microservice.model.target;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.xml.bind.annotation.XmlElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @XmlElement(name = "Code")
    private String code;

    @XmlElement(name = "Name")
    private String name;

    @XmlElement(name = "Qty")
    private int qty;

    @XmlElement(name = "UnitPrice")
    private double unitPrice;

    public void setCode(String code) {
        this.code = code;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}