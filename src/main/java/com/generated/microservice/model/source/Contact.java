
package com.generated.microservice.model.source;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.xml.bind.annotation.XmlElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @XmlElement(name = "Email")
    private String email;

    @XmlElement(name = "Phone")
    private String phone;
}