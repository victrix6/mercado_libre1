package co.com.ml.api.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para ProductDto")
class ProductDtoTest {

    private Validator validator;
    private ProductDto validProductDto;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        
        validProductDto = ProductDto.builder()
                .id("550e8400-e29b-41d4-a716-446655440001")
                .productName("Laptop Gaming")
                .imageUrl("https://example.com/laptop.jpg")
                .description("Laptop para gaming de alta gama con excelente rendimiento")
                .price(1500.0)
                .rating(4.5)
                .specifications("Intel i7, 16GB RAM, RTX 3070, SSD 512GB")
                .build();
    }

    @Test
    @DisplayName("Debería validar un ProductDto válido exitosamente")
    void shouldValidateValidProductDtoSuccessfully() {
        // Act
        Set<ConstraintViolation<ProductDto>> violations = validator.validate(validProductDto);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Debería fallar validación cuando productName es nulo")
    void shouldFailValidationWhenProductNameIsNull() {
        // Arrange
        ProductDto productDto = validProductDto.toBuilder()
                .productName(null)
                .build();

        // Act
        Set<ConstraintViolation<ProductDto>> violations = validator.validate(productDto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("El nombre del producto es obligatorio")));
    }

    @Test
    @DisplayName("Debería fallar validación cuando productName está vacío")
    void shouldFailValidationWhenProductNameIsEmpty() {
        // Arrange
        ProductDto productDto = validProductDto.toBuilder()
                .productName("   ")
                .build();

        // Act
        Set<ConstraintViolation<ProductDto>> violations = validator.validate(productDto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("El nombre del producto es obligatorio")));
    }

    @Test
    @DisplayName("Debería fallar validación cuando productName es muy largo")
    void shouldFailValidationWhenProductNameIsTooLong() {
        // Arrange
        String longName = "a".repeat(101);
        ProductDto productDto = validProductDto.toBuilder()
                .productName(longName)
                .build();

        // Act
        Set<ConstraintViolation<ProductDto>> violations = validator.validate(productDto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("El nombre del producto debe tener entre 1 y 100 caracteres")));
    }

    @Test
    @DisplayName("Debería fallar validación cuando imageUrl es inválida")
    void shouldFailValidationWhenImageUrlIsInvalid() {
        // Arrange
        ProductDto productDto = validProductDto.toBuilder()
                .imageUrl("invalid-url")
                .build();

        // Act
        Set<ConstraintViolation<ProductDto>> violations = validator.validate(productDto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("La URL de la imagen debe ser válida")));
    }

    @Test
    @DisplayName("Debería fallar validación cuando description es muy corta")
    void shouldFailValidationWhenDescriptionIsTooShort() {
        // Arrange
        ProductDto productDto = validProductDto.toBuilder()
                .description("Corta")
                .build();

        // Act
        Set<ConstraintViolation<ProductDto>> violations = validator.validate(productDto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("La descripción debe tener entre 10 y 500 caracteres")));
    }

    @Test
    @DisplayName("Debería fallar validación cuando price es nulo")
    void shouldFailValidationWhenPriceIsNull() {
        // Arrange
        ProductDto productDto = validProductDto.toBuilder()
                .price(null)
                .build();

        // Act
        Set<ConstraintViolation<ProductDto>> violations = validator.validate(productDto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("El precio del producto es obligatorio")));
    }

    @Test
    @DisplayName("Debería fallar validación cuando price es cero")
    void shouldFailValidationWhenPriceIsZero() {
        // Arrange
        ProductDto productDto = validProductDto.toBuilder()
                .price(0.0)
                .build();

        // Act
        Set<ConstraintViolation<ProductDto>> violations = validator.validate(productDto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("El precio del producto debe ser mayor a 0")));
    }

    @Test
    @DisplayName("Debería fallar validación cuando price es muy alto")
    void shouldFailValidationWhenPriceIsTooHigh() {
        // Arrange
        ProductDto productDto = validProductDto.toBuilder()
                .price(1000000.0)
                .build();

        // Act
        Set<ConstraintViolation<ProductDto>> violations = validator.validate(productDto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("El precio del producto no puede exceder 999,999.99")));
    }

    @Test
    @DisplayName("Debería fallar validación cuando rating es negativo")
    void shouldFailValidationWhenRatingIsNegative() {
        // Arrange
        ProductDto productDto = validProductDto.toBuilder()
                .rating(-1.0)
                .build();

        // Act
        Set<ConstraintViolation<ProductDto>> violations = validator.validate(productDto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("La calificación del producto debe ser mayor o igual a 0")));
    }

    @Test
    @DisplayName("Debería fallar validación cuando rating excede 5.0")
    void shouldFailValidationWhenRatingExceedsFive() {
        // Arrange
        ProductDto productDto = validProductDto.toBuilder()
                .rating(6.0)
                .build();

        // Act
        Set<ConstraintViolation<ProductDto>> violations = validator.validate(productDto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("La calificación del producto no puede exceder 5.0")));
    }

    @Test
    @DisplayName("Debería fallar validación cuando specifications es muy corta")
    void shouldFailValidationWhenSpecificationsIsTooShort() {
        // Arrange
        ProductDto productDto = validProductDto.toBuilder()
                .specifications("Corta")
                .build();

        // Act
        Set<ConstraintViolation<ProductDto>> violations = validator.validate(productDto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Las especificaciones deben tener entre 10 y 1000 caracteres")));
    }
}
