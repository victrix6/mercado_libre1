package co.com.ml.model.product;
import lombok.*;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Product {
    private Long id;
    private String productName;
    private String imageUrl;
    private String description;
    private Double price;
    private Double rating;
    private String specifications;
}
