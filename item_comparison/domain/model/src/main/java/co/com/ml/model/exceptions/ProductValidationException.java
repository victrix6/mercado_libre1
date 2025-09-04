package co.com.ml.model.exceptions;

/**
 * Excepción lanzada cuando hay errores de validación en los datos de un producto.
 * Esta excepción se utiliza para errores de validación de negocio.
 */
public class ProductValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor con mensaje de error.
     * 
     * @param message el mensaje de error
     */
    public ProductValidationException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje de error y causa.
     * 
     * @param message el mensaje de error
     * @param cause la causa de la excepción
     */
    public ProductValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
