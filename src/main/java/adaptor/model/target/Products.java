package adaptor.model.target;


import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Products {

    private List<Product> product;

    @XmlElement(name = "Product")
    public List<Product> getProduct() {
        return product;
    }
}