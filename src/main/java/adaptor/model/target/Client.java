package adaptor.model.target;

import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    private String id;
    private String fullName;
    private String email;
    private String phone;

    @XmlElement(name = "Id")
    public String getId() {
        return id;
    }

    @XmlElement(name = "FullName")
    public String getFullName() {
        return fullName;
    }

    @XmlElement(name = "Email")
    public String getEmail() {
        return email;
    }

    @XmlElement(name = "Phone")
    public String getPhone() {
        return phone;
    }
}