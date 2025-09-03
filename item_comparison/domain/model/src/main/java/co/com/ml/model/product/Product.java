package co.com.ml.model.product;
import lombok.*;
import lombok.NoArgsConstructor;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Product {
    private String id;
    private String productName;
    private String imageUrl;
    private String description;
    private Double price;
    private Double rating;
    private String specifications;
    
    /**
     * Genera un nuevo ID único usando UUID.
     * @return un nuevo ID único
     */
    public static String generateId() {
        return UUID.randomUUID().toString();
    }
}
