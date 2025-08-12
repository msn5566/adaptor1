
package com.generated.microservice.model.target;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
@NoArgsConstructor
public class Products {

    @XmlElement(name = "Product")
    private List<Product> product;

    public Products(List<Product> product) {
        this.product = product;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }
}