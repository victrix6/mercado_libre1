package co.com.ml.model.exceptions;

/**
 * Excepción lanzada cuando no se encuentra un producto específico.
 * Esta excepción se utiliza cuando se solicita un producto que no existe.
 */
public class ProductNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor con mensaje de error.
     * 
     * @param message el mensaje de error
     */
    public ProductNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje de error y causa.
     * 
     * @param message el mensaje de error
     * @param cause la causa de la excepción
     */
    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor que genera un mensaje automático basado en el ID del producto.
     * 
     * @param productId el ID del producto no encontrado
     */
    public static ProductNotFoundException forProductId(String productId) {
        return new ProductNotFoundException("Producto con ID '" + productId + "' no encontrado");
    }
}
