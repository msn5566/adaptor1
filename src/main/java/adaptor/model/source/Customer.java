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
public class Customer {

    @XmlElement(name = "CustomerId")
    private String customerId;

    @XmlElement(name = "Name")
    private String name;

    @XmlElement(name = "Contact")
    private Contact contact;
}