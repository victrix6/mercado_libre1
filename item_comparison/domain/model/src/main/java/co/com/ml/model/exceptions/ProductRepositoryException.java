package co.com.ml.model.exceptions;

/**
 * Excepción lanzada cuando hay errores en el repositorio de productos.
 * Esta excepción se utiliza para errores de acceso a datos o persistencia.
 */
public class ProductRepositoryException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor con mensaje de error.
     * 
     * @param message el mensaje de error
     */
    public ProductRepositoryException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje de error y causa.
     * 
     * @param message el mensaje de error
     * @param cause la causa de la excepción
     */
    public ProductRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
