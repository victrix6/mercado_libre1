package co.com.ml.api.util;

import co.com.ml.model.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import co.com.ml.model.exceptions.ProductValidationException;
import co.com.ml.model.exceptions.ProductComparisonException;

@DisplayName("Tests para ProductValidationUtil")
class ProductValidationUtilTest {

    private ProductValidationUtil productValidationUtil;
    private Product validProduct;

    @BeforeEach
    void setUp() {
        productValidationUtil = new ProductValidationUtil();
        
        validProduct = Product.builder()
                .productName("Laptop Gaming")
                .imageUrl("https://example.com/laptop.jpg")
                .description("Laptop para gaming de alta gama")
                .price(1500.0)
                .rating(4.5)
                .specifications("Intel i7, 16GB RAM, RTX 3070")
                .build();
    }

    @Test
    @DisplayName("Debería validar un producto válido exitosamente")
    void shouldValidateValidProductSuccessfully() {
        // Act & Assert - No debe lanzar excepción
        assertDoesNotThrow(() -> productValidationUtil.validateProduct(validProduct));
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el producto es nulo")
    void shouldThrowExceptionWhenProductIsNull() {
        // Act & Assert
        ProductValidationException exception = assertThrows(
                ProductValidationException.class,
                () -> productValidationUtil.validateProduct(null)
        );
        
        assertEquals("El producto no puede ser nulo", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el nombre del producto es nulo")
    void shouldThrowExceptionWhenProductNameIsNull() {
        // Arrange
        Product productWithNullName = validProduct.toBuilder()
                .productName(null)
                .build();

        // Act & Assert
        ProductValidationException exception = assertThrows(
                ProductValidationException.class,
                () -> productValidationUtil.validateProduct(productWithNullName)
        );
        
        assertEquals("El nombre del producto es obligatorio", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el nombre del producto está vacío")
    void shouldThrowExceptionWhenProductNameIsEmpty() {
        // Arrange
        Product productWithEmptyName = validProduct.toBuilder()
                .productName("   ")
                .build();

        // Act & Assert
        ProductValidationException exception = assertThrows(
                ProductValidationException.class,
                () -> productValidationUtil.validateProduct(productWithEmptyName)
        );
        
        assertEquals("El nombre del producto es obligatorio", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando la descripción es nula")
    void shouldThrowExceptionWhenDescriptionIsNull() {
        // Arrange
        Product productWithNullDescription = validProduct.toBuilder()
                .description(null)
                .build();

        // Act & Assert
        ProductValidationException exception = assertThrows(
                ProductValidationException.class,
                () -> productValidationUtil.validateProduct(productWithNullDescription)
        );
        
        assertEquals("La descripción del producto es obligatoria", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando la URL de imagen es nula")
    void shouldThrowExceptionWhenImageUrlIsNull() {
        // Arrange
        Product productWithNullImageUrl = validProduct.toBuilder()
                .imageUrl(null)
                .build();

        // Act & Assert
        ProductValidationException exception = assertThrows(
                ProductValidationException.class,
                () -> productValidationUtil.validateProduct(productWithNullImageUrl)
        );
        
        assertEquals("La URL de la imagen es obligatoria", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el precio es nulo")
    void shouldThrowExceptionWhenPriceIsNull() {
        // Arrange
        Product productWithNullPrice = validProduct.toBuilder()
                .price(null)
                .build();

        // Act & Assert
        ProductValidationException exception = assertThrows(
                ProductValidationException.class,
                () -> productValidationUtil.validateProduct(productWithNullPrice)
        );
        
        assertEquals("El precio del producto debe ser mayor a cero", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el precio es cero")
    void shouldThrowExceptionWhenPriceIsZero() {
        // Arrange
        Product productWithZeroPrice = validProduct.toBuilder()
                .price(0.0)
                .build();

        // Act & Assert
        ProductValidationException exception = assertThrows(
                ProductValidationException.class,
                () -> productValidationUtil.validateProduct(productWithZeroPrice)
        );
        
        assertEquals("El precio del producto debe ser mayor a cero", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el precio es negativo")
    void shouldThrowExceptionWhenPriceIsNegative() {
        // Arrange
        Product productWithNegativePrice = validProduct.toBuilder()
                .price(-100.0)
                .build();

        // Act & Assert
        ProductValidationException exception = assertThrows(
                ProductValidationException.class,
                () -> productValidationUtil.validateProduct(productWithNegativePrice)
        );
        
        assertEquals("El precio del producto debe ser mayor a cero", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando la calificación es nula")
    void shouldThrowExceptionWhenRatingIsNull() {
        // Arrange
        Product productWithNullRating = validProduct.toBuilder()
                .rating(null)
                .build();

        // Act & Assert
        ProductValidationException exception = assertThrows(
                ProductValidationException.class,
                () -> productValidationUtil.validateProduct(productWithNullRating)
        );
        
        assertEquals("La calificación del producto debe ser mayor o igual a cero", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando la calificación es negativa")
    void shouldThrowExceptionWhenRatingIsNegative() {
        // Arrange
        Product productWithNegativeRating = validProduct.toBuilder()
                .rating(-1.0)
                .build();

        // Act & Assert
        ProductValidationException exception = assertThrows(
                ProductValidationException.class,
                () -> productValidationUtil.validateProduct(productWithNegativeRating)
        );
        
        assertEquals("La calificación del producto debe ser mayor o igual a cero", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando las especificaciones son nulas")
    void shouldThrowExceptionWhenSpecificationsIsNull() {
        // Arrange
        Product productWithNullSpecifications = validProduct.toBuilder()
                .specifications(null)
                .build();

        // Act & Assert
        ProductValidationException exception = assertThrows(
                ProductValidationException.class,
                () -> productValidationUtil.validateProduct(productWithNullSpecifications)
        );
        
        assertEquals("Las especificaciones del producto son obligatorias", exception.getMessage());
    }

    @Test
    @DisplayName("Debería aceptar calificación cero")
    void shouldAcceptZeroRating() {
        // Arrange
        Product productWithZeroRating = validProduct.toBuilder()
                .rating(0.0)
                .build();

        // Act & Assert - No debe lanzar excepción
        assertDoesNotThrow(() -> productValidationUtil.validateProduct(productWithZeroRating));
    }

    // Tests para validaciones de comparación de productos

    @Test
    @DisplayName("Debería validar lista de IDs válida para comparación")
    void shouldValidateValidIdListForComparison() {
        // Arrange
        java.util.List<String> validIds = java.util.Arrays.asList("550e8400-e29b-41d4-a716-446655440001", "550e8400-e29b-41d4-a716-446655440002");

        // Act & Assert - No debe lanzar excepción
        assertDoesNotThrow(() -> productValidationUtil.validateProductIdsForComparison(validIds));
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando la lista de IDs es nula")
    void shouldThrowExceptionWhenIdListIsNull() {
        // Act & Assert
        ProductComparisonException exception = assertThrows(
                ProductComparisonException.class,
                () -> productValidationUtil.validateProductIdsForComparison(null)
        );
        
        assertEquals("La lista de IDs no puede ser nula", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando hay menos de 2 IDs")
    void shouldThrowExceptionWhenLessThanTwoIds() {
        // Arrange
        java.util.List<String> singleId = java.util.Arrays.asList("550e8400-e29b-41d4-a716-446655440001");

        // Act & Assert
        ProductComparisonException exception = assertThrows(
                ProductComparisonException.class,
                () -> productValidationUtil.validateProductIdsForComparison(singleId)
        );
        
        assertEquals("Debe proporcionar al menos dos IDs", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando todos los IDs son nulos")
    void shouldThrowExceptionWhenAllIdsAreNull() {
        // Arrange
        java.util.List<String> nullIds = java.util.Arrays.asList(null, null);

        // Act & Assert
        ProductComparisonException exception = assertThrows(
                ProductComparisonException.class,
                () -> productValidationUtil.validateProductIdsForComparison(nullIds)
        );
        
        assertEquals("Debe proporcionar al menos dos IDs válidos (no nulos ni vacíos)", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando todos los IDs son duplicados")
    void shouldThrowExceptionWhenAllIdsAreDuplicates() {
        // Arrange
        java.util.List<String> duplicateIds = java.util.Arrays.asList("550e8400-e29b-41d4-a716-446655440001", "550e8400-e29b-41d4-a716-446655440001", "550e8400-e29b-41d4-a716-446655440001");

        // Act & Assert
        ProductComparisonException exception = assertThrows(
                ProductComparisonException.class,
                () -> productValidationUtil.validateProductIdsForComparison(duplicateIds)
        );
        
        assertEquals("Debe proporcionar al menos dos IDs válidos (no nulos ni vacíos)", exception.getMessage());
    }

    @Test
    @DisplayName("Debería validar resultado de comparación exitoso con 2 IDs")
    void shouldValidateSuccessfulComparisonResultWithTwoIds() {
        // Arrange
        java.util.List<String> ids = java.util.Arrays.asList("550e8400-e29b-41d4-a716-446655440001", "550e8400-e29b-41d4-a716-446655440002");
        Product product1 = Product.builder().id("550e8400-e29b-41d4-a716-446655440001").productName("Producto 1").build();
        Product product2 = Product.builder().id("550e8400-e29b-41d4-a716-446655440002").productName("Producto 2").build();
        java.util.List<Product> foundProducts = java.util.Arrays.asList(product1, product2);

        // Act & Assert - No debe lanzar excepción
        assertDoesNotThrow(() -> productValidationUtil.validateComparisonResult(ids, foundProducts));
    }

    @Test
    @DisplayName("Debería validar resultado de comparación exitoso con más de 2 IDs")
    void shouldValidateSuccessfulComparisonResultWithMoreThanTwoIds() {
        // Arrange
        java.util.List<String> ids = java.util.Arrays.asList("550e8400-e29b-41d4-a716-446655440001", "550e8400-e29b-41d4-a716-446655440002", "550e8400-e29b-41d4-a716-446655440003");
        Product product1 = Product.builder().id("550e8400-e29b-41d4-a716-446655440001").productName("Producto 1").build();
        Product product2 = Product.builder().id("550e8400-e29b-41d4-a716-446655440002").productName("Producto 2").build();
        java.util.List<Product> foundProducts = java.util.Arrays.asList(product1, product2);

        // Act & Assert - No debe lanzar excepción
        assertDoesNotThrow(() -> productValidationUtil.validateComparisonResult(ids, foundProducts));
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando faltan productos con 2 IDs")
    void shouldThrowExceptionWhenMissingProductsWithTwoIds() {
        // Arrange
        java.util.List<String> ids = java.util.Arrays.asList("550e8400-e29b-41d4-a716-446655440001", "550e8400-e29b-41d4-a716-446655440002");
        Product product1 = Product.builder().id("550e8400-e29b-41d4-a716-446655440001").productName("Producto 1").build();
        java.util.List<Product> foundProducts = java.util.Arrays.asList(product1); // Solo 1 de 2

        // Act & Assert
        ProductComparisonException exception = assertThrows(
                ProductComparisonException.class,
                () -> productValidationUtil.validateComparisonResult(ids, foundProducts)
        );
        
        assertEquals("Alguno de los productos solicitados no existe", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando faltan productos con más de 2 IDs")
    void shouldThrowExceptionWhenMissingProductsWithMoreThanTwoIds() {
        // Arrange
        java.util.List<String> ids = java.util.Arrays.asList("550e8400-e29b-41d4-a716-446655440001", "550e8400-e29b-41d4-a716-446655440002", "550e8400-e29b-41d4-a716-446655440003");
        Product product1 = Product.builder().id("550e8400-e29b-41d4-a716-446655440001").productName("Producto 1").build();
        java.util.List<Product> foundProducts = java.util.Arrays.asList(product1); // Solo 1 de 3

        // Act & Assert
        ProductComparisonException exception = assertThrows(
                ProductComparisonException.class,
                () -> productValidationUtil.validateComparisonResult(ids, foundProducts)
        );
        
        assertEquals("Se requieren al menos dos productos existentes para comparar", exception.getMessage());
    }
}
