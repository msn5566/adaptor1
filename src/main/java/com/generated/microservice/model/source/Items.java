
package com.generated.microservice.model.source;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Items {

    @XmlElement(name = "Item")
    private List<Item> item;

    public List<Item> getItem() {
        return item;
    }
}