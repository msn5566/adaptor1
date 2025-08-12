
package com.generated.microservice.model.source;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "Order")
public class Order {

    @XmlElement(name = "OrderId")
    private String orderId;

    @XmlElement(name = "OrderDate")
    private String orderDate;

    @XmlElement(name = "Customer")
    private Customer customer;

    @XmlElement(name = "Items")
    private Items items;

    @XmlElement(name = "Billing")
    private Billing billing;

    public Customer getCustomer() {
        return customer;
    }

    public Items getItems() {
        return items;
    }

    public Billing getBilling() {
        return billing;
    }
}