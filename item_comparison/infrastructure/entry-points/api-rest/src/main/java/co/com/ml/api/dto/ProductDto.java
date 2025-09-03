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

    @JsonProperty("id")
    private String id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(min = 1, max = 100, message = "El nombre del producto debe tener entre 1 y 100 caracteres")
    @JsonProperty("productName")
    private String productName;

    @NotBlank(message = "La URL de la imagen es obligatoria")
    @Pattern(regexp = "^https?://.*", message = "La URL de la imagen debe ser válida (http:// o https://)")
    @JsonProperty("imageUrl")
    private String imageUrl;

    @NotBlank(message = "La descripción del producto es obligatoria")
    @Size(min = 10, max = 500, message = "La descripción debe tener entre 10 y 500 caracteres")
    @JsonProperty("description")
    private String description;

    @NotNull(message = "El precio del producto es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio del producto debe ser mayor a 0")
    @DecimalMax(value = "999999.99", message = "El precio del producto no puede exceder 999,999.99")
    @JsonProperty("price")
    private Double price;

    @NotNull(message = "La calificación del producto es obligatoria")
    @DecimalMin(value = "0.0", message = "La calificación del producto debe ser mayor o igual a 0")
    @DecimalMax(value = "5.0", message = "La calificación del producto no puede exceder 5.0")
    @JsonProperty("rating")
    private Double rating;

    @NotBlank(message = "Las especificaciones del producto son obligatorias")
    @Size(min = 10, max = 1000, message = "Las especificaciones deben tener entre 10 y 1000 caracteres")
    @JsonProperty("specifications")
    private String specifications;
}
