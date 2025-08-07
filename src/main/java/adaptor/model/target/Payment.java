
package adaptor.model.target;

import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @XmlElement
    private String CustomerId;
    @XmlElement
    private String Amount;
    @XmlElement
    private String Status;
}