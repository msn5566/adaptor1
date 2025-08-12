
package com.generated.microservice.model.source;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.xml.bind.annotation.XmlElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Billing {

    @XmlElement(name = "CustomerId")
    private String customerId;

    @XmlElement(name = "TotalAmount")
    private double totalAmount;

    @XmlElement(name = "Paid")
    private boolean paid;
}