
package com.generated.microservice.model.source;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.xml.bind.annotation.XmlElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @XmlElement(name = "CustomerId")
    private String customerId;

    @XmlElement(name = "Name")
    private String name;

    @XmlElement(name = "Contact")
    private Contact contact;
}