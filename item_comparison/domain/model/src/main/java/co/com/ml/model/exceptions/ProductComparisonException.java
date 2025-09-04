package co.com.ml.model.exceptions;

/**
 * Excepción lanzada cuando hay errores en la comparación de productos.
 * Esta excepción se utiliza para errores específicos del proceso de comparación.
 */
public class ProductComparisonException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor con mensaje de error.
     * 
     * @param message el mensaje de error
     */
    public ProductComparisonException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje de error y causa.
     * 
     * @param message el mensaje de error
     * @param cause la causa de la excepción
     */
    public ProductComparisonException(String message, Throwable cause) {
        super(message, cause);
    }
}
