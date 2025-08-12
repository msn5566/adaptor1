
package com.generated.microservice.model.target;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.xml.bind.annotation.XmlElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @XmlElement(name = "Id")
    private String id;

    @XmlElement(name = "FullName")
    private String fullName;

    @XmlElement(name = "Email")
    private String email;

    @XmlElement(name = "Phone")
    private String phone;

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}