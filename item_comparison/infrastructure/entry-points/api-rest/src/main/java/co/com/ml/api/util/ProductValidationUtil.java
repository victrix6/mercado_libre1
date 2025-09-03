package co.com.ml.api.util;

import co.com.ml.model.product.Product;
import org.springframework.stereotype.Component;

/**
 * Utilidades para validación de productos.
 * Contiene las validaciones de negocio para productos.
 */
@Component
public class ProductValidationUtil {

    /**
     * Valida que un producto cumpla con todas las reglas de negocio.
     * 
     * @param product el producto a validar
     * @throws IllegalArgumentException si el producto no cumple con las validaciones
     */
    public void validateProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
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
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }
    }

    /**
     * Valida la descripción del producto.
     */
    private void validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción del producto es obligatoria");
        }
    }

    /**
     * Valida la URL de la imagen del producto.
     */
    private void validateImageUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("La URL de la imagen es obligatoria");
        }
    }

    /**
     * Valida el precio del producto.
     */
    private void validatePrice(Double price) {
        if (price == null || price <= 0) {
            throw new IllegalArgumentException("El precio del producto debe ser mayor a cero");
        }
    }

    /**
     * Valida la calificación del producto.
     */
    private void validateRating(Double rating) {
        if (rating == null || rating < 0) {
            throw new IllegalArgumentException("La calificación del producto debe ser mayor o igual a cero");
        }
    }

    /**
     * Valida las especificaciones del producto.
     */
    private void validateSpecifications(String specifications) {
        if (specifications == null || specifications.trim().isEmpty()) {
            throw new IllegalArgumentException("Las especificaciones del producto son obligatorias");
        }
    }

    /**
     * Valida la lista de IDs para comparación de productos.
     * 
     * @param productIds lista de IDs a validar
     * @throws IllegalArgumentException si la lista no cumple con las validaciones
     */
    public void validateProductIdsForComparison(java.util.List<String> productIds) {
        if (productIds == null) {
            throw new IllegalArgumentException("La lista de IDs no puede ser nula");
        }
        if (productIds.size() < 2) {
            throw new IllegalArgumentException("Debe proporcionar al menos dos IDs");
        }
        
        // normalizar: quitar nulos, vacíos y duplicados preservando orden
        java.util.List<String> normalizedIds = productIds.stream()
                .filter(java.util.Objects::nonNull)
                .filter(id -> !id.trim().isEmpty())
                .collect(java.util.stream.Collectors.toCollection(java.util.LinkedHashSet::new))
                .stream().collect(java.util.stream.Collectors.toList());

        if (normalizedIds.size() < 2) {
            throw new IllegalArgumentException("Debe proporcionar al menos dos IDs válidos (no nulos ni vacíos)");
        }
    }

    /**
     * Valida el resultado de la comparación de productos.
     * 
     * @param productIds lista de IDs solicitados
     * @param foundProducts lista de productos encontrados
     * @throws IllegalArgumentException si no se encuentran suficientes productos
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
                throw new IllegalArgumentException("Alguno de los productos solicitados no existe");
            }
        } else { // más de 2
            if (foundProducts.size() < 2) {
                throw new IllegalArgumentException("Se requieren al menos dos productos existentes para comparar");
            }
        }
    }
}
