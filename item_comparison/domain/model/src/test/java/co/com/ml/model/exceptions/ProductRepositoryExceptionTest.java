package co.com.ml.model.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para ProductRepositoryException")
class ProductRepositoryExceptionTest {

    @Test
    @DisplayName("Debería crear excepción con mensaje")
    void shouldCreateExceptionWithMessage() {
        // Arrange
        String message = "Error en el repositorio de productos";

        // Act
        ProductRepositoryException exception = new ProductRepositoryException(message);

        // Assert
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Debería crear excepción con mensaje y causa")
    void shouldCreateExceptionWithMessageAndCause() {
        // Arrange
        String message = "Error en el repositorio de productos";
        Throwable cause = new RuntimeException("Causa original");

        // Act
        ProductRepositoryException exception = new ProductRepositoryException(message, cause);

        // Assert
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    @DisplayName("Debería ser instancia de RuntimeException")
    void shouldBeInstanceOfRuntimeException() {
        // Act
        ProductRepositoryException exception = new ProductRepositoryException("Test");

        // Assert
        assertTrue(exception instanceof RuntimeException);
    }
}
