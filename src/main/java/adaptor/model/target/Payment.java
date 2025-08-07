package adaptor.model.target;

import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private String customerId;
    private double amount;
    private String status;

    @XmlElement(name = "CustomerId")
    public String getCustomerId() {
        return customerId;
    }

    @XmlElement(name = "Amount")
    public double getAmount() {
        return amount;
    }

    @XmlElement(name = "Status")
    public String getStatus() {
        return status;
    }
}