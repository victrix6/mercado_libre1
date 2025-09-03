package co.com.ml.model.product.gateways;

import java.util.List;

import co.com.ml.model.product.Product;

public interface ProductRepository {
    
    /**
     * Agrega un nuevo producto al repositorio
     * @param product el producto a agregar
     * @return el producto agregado
     */
    Product addProduct(Product product);
    
    /**
     * Obtiene todos los productos del repositorio
     * @return lista de todos los productos
     */
    List<Product> listAllProducts();
    
    /**
     * Obtiene los productos correspondientes a los IDs proporcionados
     * @param productIds lista de IDs de productos a recuperar/validar
     * @return lista de productos encontrados para dichos IDs
     */
    List<Product> compareProducts(List<String> productIds);
}
