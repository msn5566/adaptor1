
package adaptor.model.target;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String Code;
    private String Name;
    private Integer Qty;
    private Double UnitPrice;
}