
package adaptor.model.target;

import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @XmlElement
    private String Id;
    @XmlElement
    private String FullName;
    @XmlElement
    private String Email;
    @XmlElement
    private String Phone;
}