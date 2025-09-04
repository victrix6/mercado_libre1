package co.com.ml.model.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para ProductValidationException")
class ProductValidationExceptionTest {

    @Test
    @DisplayName("Debería crear excepción con mensaje")
    void shouldCreateExceptionWithMessage() {
        // Arrange
        String message = "Error de validación del producto";

        // Act
        ProductValidationException exception = new ProductValidationException(message);

        // Assert
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Debería crear excepción con mensaje y causa")
    void shouldCreateExceptionWithMessageAndCause() {
        // Arrange
        String message = "Error de validación del producto";
        Throwable cause = new RuntimeException("Causa original");

        // Act
        ProductValidationException exception = new ProductValidationException(message, cause);

        // Assert
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    @DisplayName("Debería ser instancia de RuntimeException")
    void shouldBeInstanceOfRuntimeException() {
        // Act
        ProductValidationException exception = new ProductValidationException("Test");

        // Assert
        assertTrue(exception instanceof RuntimeException);
    }
}
