package co.com.ml.usecase.product;

import java.util.List;

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
     * @param productIds lista de IDs
     * @return lista de productos correspondientes
     */
    public List<Product> compareProducts(List<String> productIds) {
        return productRepository.compareProducts(productIds);
    }
}

