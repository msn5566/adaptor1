
package com.generated.microservice.model.target;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.xml.bind.annotation.XmlElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @XmlElement(name = "CustomerId")
    private String customerId;

    @XmlElement(name = "Amount")
    private double amount;

    @XmlElement(name = "Status")
    private String status;

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}