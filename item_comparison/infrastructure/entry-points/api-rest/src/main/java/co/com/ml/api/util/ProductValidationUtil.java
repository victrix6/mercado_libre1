package co.com.ml.api.util;

import co.com.ml.model.product.Product;
import co.com.ml.model.exceptions.ProductValidationException;
import co.com.ml.model.exceptions.ProductComparisonException;
import org.springframework.stereotype.Component;

/**
 * Utilidades para validación de productos.
 * Contiene las validaciones de negocio para productos.
 */
@Component
public class ProductValidationUtil {

    // Error messages
    private static final String PRODUCT_NULL_MESSAGE = "El producto no puede ser nulo";
    private static final String PRODUCT_NAME_REQUIRED = "El nombre del producto es obligatorio";
    private static final String DESCRIPTION_REQUIRED = "La descripción del producto es obligatoria";
    private static final String IMAGE_URL_REQUIRED = "La URL de la imagen es obligatoria";
    private static final String PRICE_REQUIRED = "El precio del producto debe ser mayor a cero";
    private static final String RATING_REQUIRED = "La calificación del producto debe ser mayor o igual a cero";
    private static final String SPECIFICATIONS_REQUIRED = "Las especificaciones del producto son obligatorias";
    
    // Comparison error messages
    private static final String IDS_LIST_NULL = "La lista de IDs no puede ser nula";
    private static final String MIN_TWO_IDS_REQUIRED = "Debe proporcionar al menos dos IDs";
    private static final String MIN_TWO_VALID_IDS_REQUIRED = "Debe proporcionar al menos dos IDs válidos (no nulos ni vacíos)";
    private static final String PRODUCTS_NOT_EXIST = "Alguno de los productos solicitados no existe";
    private static final String MIN_TWO_PRODUCTS_REQUIRED = "Se requieren al menos dos productos existentes para comparar";

    /**
     * Valida que un producto cumpla con todas las reglas de negocio.
     * 
     * @param product el producto a validar
     * @throws ProductValidationException si el producto no cumple con las validaciones
     */
    public void validateProduct(Product product) {
        if (product == null) {
            throw new ProductValidationException(PRODUCT_NULL_MESSAGE);
        }
        
        validateProductName(product.getProductName());
        validateDescription(product.getDescription());
        validateImageUrl(product.getImageUrl());
        validatePrice(product.getPrice());
        validateRating(product.getRating());
        validateSpecifications(product.getSpecifications());
    }

    /**
     * Valida el nombre del producto.
     */
    private void validateProductName(String productName) {
        if (productName == null || productName.trim().isEmpty()) {
            throw new ProductValidationException(PRODUCT_NAME_REQUIRED);
        }
    }

    /**
     * Valida la descripción del producto.
     */
    private void validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new ProductValidationException(DESCRIPTION_REQUIRED);
        }
    }

    /**
     * Valida la URL de la imagen del producto.
     */
    private void validateImageUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            throw new ProductValidationException(IMAGE_URL_REQUIRED);
        }
    }

    /**
     * Valida el precio del producto.
     */
    private void validatePrice(Double price) {
        if (price == null || price <= 0) {
            throw new ProductValidationException(PRICE_REQUIRED);
        }
    }

    /**
     * Valida la calificación del producto.
     */
    private void validateRating(Double rating) {
        if (rating == null || rating < 0) {
            throw new ProductValidationException(RATING_REQUIRED);
        }
    }

    /**
     * Valida las especificaciones del producto.
     */
    private void validateSpecifications(String specifications) {
        if (specifications == null || specifications.trim().isEmpty()) {
            throw new ProductValidationException(SPECIFICATIONS_REQUIRED);
        }
    }

    /**
     * Valida la lista de IDs para comparación de productos.
     * 
     * @param productIds lista de IDs a validar
     * @throws ProductComparisonException si la lista no cumple con las validaciones
     */
    public void validateProductIdsForComparison(java.util.List<String> productIds) {
        if (productIds == null) {
            throw new ProductComparisonException(IDS_LIST_NULL);
        }
        if (productIds.size() < 2) {
            throw new ProductComparisonException(MIN_TWO_IDS_REQUIRED);
        }
        
        // normalizar: quitar nulos, vacíos y duplicados preservando orden
        java.util.List<String> normalizedIds = productIds.stream()
                .filter(java.util.Objects::nonNull)
                .filter(id -> !id.trim().isEmpty())
                .collect(java.util.stream.Collectors.toCollection(java.util.LinkedHashSet::new))
                .stream().collect(java.util.stream.Collectors.toList());

        if (normalizedIds.size() < 2) {
            throw new ProductComparisonException(MIN_TWO_VALID_IDS_REQUIRED);
        }
    }

    /**
     * Valida el resultado de la comparación de productos.
     * 
     * @param productIds lista de IDs solicitados
     * @param foundProducts lista de productos encontrados
     * @throws ProductComparisonException si no se encuentran suficientes productos
     */
    public void validateComparisonResult(java.util.List<String> productIds, java.util.List<Product> foundProducts) {
        // normalizar IDs para la validación
        java.util.List<String> normalizedIds = productIds.stream()
                .filter(java.util.Objects::nonNull)
                .filter(id -> !id.trim().isEmpty())
                .collect(java.util.stream.Collectors.toCollection(java.util.LinkedHashSet::new))
                .stream().collect(java.util.stream.Collectors.toList());

        if (normalizedIds.size() == 2) {
            if (foundProducts.size() < 2) {
                throw new ProductComparisonException(PRODUCTS_NOT_EXIST);
            }
        } else { // más de 2
            if (foundProducts.size() < 2) {
                throw new ProductComparisonException(MIN_TWO_PRODUCTS_REQUIRED);
            }
        }
    }
}
