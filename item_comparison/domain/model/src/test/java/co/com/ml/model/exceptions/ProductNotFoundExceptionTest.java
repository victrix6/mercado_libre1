package co.com.ml.model.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para ProductNotFoundException")
class ProductNotFoundExceptionTest {

    @Test
    @DisplayName("Debería crear excepción con mensaje")
    void shouldCreateExceptionWithMessage() {
        // Arrange
        String message = "Producto no encontrado";

        // Act
        ProductNotFoundException exception = new ProductNotFoundException(message);

        // Assert
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Debería crear excepción con mensaje y causa")
    void shouldCreateExceptionWithMessageAndCause() {
        // Arrange
        String message = "Producto no encontrado";
        Throwable cause = new RuntimeException("Causa original");

        // Act
        ProductNotFoundException exception = new ProductNotFoundException(message, cause);

        // Assert
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    @DisplayName("Debería crear excepción con ID del producto")
    void shouldCreateExceptionWithProductId() {
        // Arrange
        String productId = "550e8400-e29b-41d4-a716-446655440001";

        // Act
        ProductNotFoundException exception = ProductNotFoundException.forProductId(productId);

        // Assert
        assertNotNull(exception);
        assertEquals("Producto con ID '550e8400-e29b-41d4-a716-446655440001' no encontrado", exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Debería ser instancia de RuntimeException")
    void shouldBeInstanceOfRuntimeException() {
        // Act
        ProductNotFoundException exception = new ProductNotFoundException("Test");

        // Assert
        assertTrue(exception instanceof RuntimeException);
    }
}
