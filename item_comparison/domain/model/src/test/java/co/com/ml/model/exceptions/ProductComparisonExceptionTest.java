package co.com.ml.model.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para ProductComparisonException")
class ProductComparisonExceptionTest {

    @Test
    @DisplayName("Debería crear excepción con mensaje")
    void shouldCreateExceptionWithMessage() {
        // Arrange
        String message = "Error en comparación de productos";

        // Act
        ProductComparisonException exception = new ProductComparisonException(message);

        // Assert
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Debería crear excepción con mensaje y causa")
    void shouldCreateExceptionWithMessageAndCause() {
        // Arrange
        String message = "Error en comparación de productos";
        Throwable cause = new RuntimeException("Causa original");

        // Act
        ProductComparisonException exception = new ProductComparisonException(message, cause);

        // Assert
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    @DisplayName("Debería ser instancia de RuntimeException")
    void shouldBeInstanceOfRuntimeException() {
        // Act
        ProductComparisonException exception = new ProductComparisonException("Test");

        // Assert
        assertTrue(exception instanceof RuntimeException);
    }
}
