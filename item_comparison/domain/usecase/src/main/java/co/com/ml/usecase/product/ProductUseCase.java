package co.com.ml.usecase.product;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import co.com.ml.model.product.Product;
import co.com.ml.model.product.gateways.ProductRepository;

import lombok.RequiredArgsConstructor;

/**
 * Caso de uso para la gestión de productos.
 * Implementa la lógica de negocio para operaciones con productos.
 */
@RequiredArgsConstructor
public class ProductUseCase {

    private final ProductRepository productRepository;

    /**
     * Agrega un nuevo producto al sistema.
     * 
     * @param product el producto a agregar
     * @return el producto agregado con ID asignado
     * @throws IllegalArgumentException si el producto es nulo o inválido
     */
    public Product addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }
        if (product.getDescription() == null || product.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción del producto es obligatoria");
        }
        if (product.getImageUrl() == null || product.getImageUrl().trim().isEmpty()) {
            throw new IllegalArgumentException("La URL de la imagen es obligatoria");
        }
        
        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new IllegalArgumentException("El precio del producto debe ser mayor a cero");
        }
        if (product.getRating() == null || product.getRating() < 0) {
            throw new IllegalArgumentException("La calificación del producto debe ser mayor o igual a cero");
        }
        if (product.getSpecifications() == null || product.getSpecifications().trim().isEmpty()) {
            throw new IllegalArgumentException("Las especificaciones del producto son obligatorias");
        }
        
        return productRepository.addProduct(product);
    }

    /**
     * Obtiene todos los productos del sistema.
     * 
     * @return lista de todos los productos
     */
    public List<Product> listAllProducts() {
        return productRepository.listAllProducts();
    }

    /**
     * Obtiene productos por sus IDs para comparación
     * @param productIds lista de IDs (>=2)
     * @return lista de productos correspondientes
     * @throws IllegalArgumentException si la lista es nula o tiene menos de 2 elementos
     */
    public List<Product> compareProducts(List<Long> productIds) {
        if (productIds == null) {
            throw new IllegalArgumentException("La lista de IDs no puede ser nula");
        }
        if (productIds.size() < 2) {
            throw new IllegalArgumentException("Debe proporcionar al menos dos IDs");
        }
        
        // normalizar: quitar nulos y duplicados preservando orden
        List<Long> normalizedIds = productIds.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedHashSet::new))
                .stream().collect(Collectors.toList());

        if (normalizedIds.size() < 2) {
            throw new IllegalArgumentException("Debe proporcionar al menos dos IDs válidos (no nulos)");
        }
        List<Product> found = productRepository.compareProducts(normalizedIds);

        if (normalizedIds.size() == 2) {
            if (found.size() < 2) {
                throw new IllegalArgumentException("Alguno de los productos solicitados no existe");
            }
        } else { // más de 2
            if (found.size() < 2) {
                throw new IllegalArgumentException("Se requieren al menos dos productos existentes para comparar");
            }
        }
        return found;
    }
}

