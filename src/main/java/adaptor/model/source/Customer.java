
package adaptor.model.source;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
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