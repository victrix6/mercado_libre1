package co.com.ml.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;

/**
 * DTO para validación de entrada de productos en la API REST.
 * Contiene validaciones de Bean Validation para asegurar la integridad de los datos.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ProductDto {

    // Validation messages
    private static final String PRODUCT_NAME_REQUIRED = "El nombre del producto es obligatorio";
    private static final String PRODUCT_NAME_SIZE = "El nombre del producto debe tener entre 1 y 100 caracteres";
    private static final String IMAGE_URL_REQUIRED = "La URL de la imagen es obligatoria";
    private static final String IMAGE_URL_PATTERN = "La URL de la imagen debe ser válida (http:// o https://)";
    private static final String DESCRIPTION_REQUIRED = "La descripción del producto es obligatoria";
    private static final String DESCRIPTION_SIZE = "La descripción debe tener entre 10 y 500 caracteres";
    private static final String PRICE_REQUIRED = "El precio del producto es obligatorio";
    private static final String PRICE_MIN = "El precio del producto debe ser mayor a 0";
    private static final String PRICE_MAX = "El precio del producto no puede exceder 999,999.99";
    private static final String RATING_REQUIRED = "La calificación del producto es obligatoria";
    private static final String RATING_MIN = "La calificación del producto debe ser mayor o igual a 0";
    private static final String RATING_MAX = "La calificación del producto no puede exceder 5.0";
    private static final String SPECIFICATIONS_REQUIRED = "Las especificaciones del producto son obligatorias";
    private static final String SPECIFICATIONS_SIZE = "Las especificaciones deben tener entre 10 y 1000 caracteres";

    // Validation constraints
    private static final int PRODUCT_NAME_MIN_SIZE = 1;
    private static final int PRODUCT_NAME_MAX_SIZE = 100;
    private static final int DESCRIPTION_MIN_SIZE = 10;
    private static final int DESCRIPTION_MAX_SIZE = 500;
    private static final int SPECIFICATIONS_MIN_SIZE = 10;
    private static final int SPECIFICATIONS_MAX_SIZE = 1000;
    private static final String URL_PATTERN = "^https?://.*";
    private static final String PRICE_MIN_VALUE = "0.01";
    private static final String PRICE_MAX_VALUE = "999999.99";
    private static final String RATING_MIN_VALUE = "0.0";
    private static final String RATING_MAX_VALUE = "5.0";

    @JsonProperty("id")
    private String id;

    @NotBlank(message = PRODUCT_NAME_REQUIRED)
    @Size(min = PRODUCT_NAME_MIN_SIZE, max = PRODUCT_NAME_MAX_SIZE, message = PRODUCT_NAME_SIZE)
    @JsonProperty("productName")
    private String productName;

    @NotBlank(message = IMAGE_URL_REQUIRED)
    @Pattern(regexp = URL_PATTERN, message = IMAGE_URL_PATTERN)
    @JsonProperty("imageUrl")
    private String imageUrl;

    @NotBlank(message = DESCRIPTION_REQUIRED)
    @Size(min = DESCRIPTION_MIN_SIZE, max = DESCRIPTION_MAX_SIZE, message = DESCRIPTION_SIZE)
    @JsonProperty("description")
    private String description;

    @NotNull(message = PRICE_REQUIRED)
    @DecimalMin(value = PRICE_MIN_VALUE, message = PRICE_MIN)
    @DecimalMax(value = PRICE_MAX_VALUE, message = PRICE_MAX)
    @JsonProperty("price")
    private Double price;

    @NotNull(message = RATING_REQUIRED)
    @DecimalMin(value = RATING_MIN_VALUE, message = RATING_MIN)
    @DecimalMax(value = RATING_MAX_VALUE, message = RATING_MAX)
    @JsonProperty("rating")
    private Double rating;

    @NotBlank(message = SPECIFICATIONS_REQUIRED)
    @Size(min = SPECIFICATIONS_MIN_SIZE, max = SPECIFICATIONS_MAX_SIZE, message = SPECIFICATIONS_SIZE)
    @JsonProperty("specifications")
    private String specifications;
}
