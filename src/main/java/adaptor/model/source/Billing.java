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
public class Billing {
    @XmlElement(name = "CustomerId")
    private String customerId;

    @XmlElement(name = "TotalAmount")
    private double totalAmount;

    @XmlElement(name = "Paid")
    private boolean paid;
}