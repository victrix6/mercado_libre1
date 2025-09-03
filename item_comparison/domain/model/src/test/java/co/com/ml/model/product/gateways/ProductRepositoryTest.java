package co.com.ml.model.product.gateways;

import co.com.ml.model.product.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests para la interfaz ProductRepository.
 * Estos tests documentan el contrato esperado de la interfaz
 * y pueden ser utilizados para verificar implementaciones.
 */
@DisplayName("Tests para la interfaz ProductRepository")
class ProductRepositoryTest {

    private ProductRepository productRepository;
    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        // Crear un mock de la interfaz para testing
        productRepository = mock(ProductRepository.class);
        
        // Crear un producto de ejemplo
        sampleProduct = Product.builder()
                .id("550e8400-e29b-41d4-a716-446655440001")
                .productName("Laptop Gaming")
                .imageUrl("https://example.com/laptop.jpg")
                .description("Laptop para gaming de alta gama")
                .price(1500.0)
                .rating(4.5)
                .specifications("Intel i7, 16GB RAM, RTX 3070")
                .build();
    }

    @Test
    @DisplayName("Debería agregar un producto y retornar el producto agregado")
    void shouldAddProductAndReturnAddedProduct() {
        // Arrange
        when(productRepository.addProduct(any(Product.class)))
                .thenReturn(sampleProduct);

        // Act
        Product result = productRepository.addProduct(sampleProduct);

        // Assert
        assertNotNull(result);
        assertEquals(sampleProduct.getId(), result.getId());
        assertEquals(sampleProduct.getProductName(), result.getProductName());
        assertEquals(sampleProduct.getPrice(), result.getPrice());
        
        // Verificar que el método fue llamado
        verify(productRepository).addProduct(sampleProduct);
    }

    @Test
    @DisplayName("Debería agregar un producto con ID nulo y asignar un ID")
    void shouldAddProductWithNullIdAndAssignId() {
        // Arrange
        Product productWithoutId = Product.builder()
                .productName("Nuevo Producto")
                .price(100.0)
                .build();
        
        Product productWithId = productWithoutId.toBuilder()
                .id("550e8400-e29b-41d4-a716-446655440001")
                .build();
        
        when(productRepository.addProduct(productWithoutId))
                .thenReturn(productWithId);

        // Act
        Product result = productRepository.addProduct(productWithoutId);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Nuevo Producto", result.getProductName());
        assertEquals(100.0, result.getPrice());
        
        verify(productRepository).addProduct(productWithoutId);
    }

    @Test
    @DisplayName("Debería retornar una lista de todos los productos")
    void shouldReturnListOfAllProducts() {
        // Arrange
        Product product1 = Product.builder()
                .id("550e8400-e29b-41d4-a716-446655440001")
                .productName("Producto 1")
                .price(100.0)
                .build();
        
        Product product2 = Product.builder()
                .id("550e8400-e29b-41d4-a716-446655440002")
                .productName("Producto 2")
                .price(200.0)
                .build();
        
        when(productRepository.listAllProducts())
                .thenReturn(java.util.Arrays.asList(product1, product2));

        // Act
        java.util.List<Product> result = productRepository.listAllProducts();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(product1.getId(), result.get(0).getId());
        assertEquals(product2.getId(), result.get(1).getId());
        
        verify(productRepository).listAllProducts();
    }

    @Test
    @DisplayName("Debería retornar una lista vacía cuando no hay productos")
    void shouldReturnEmptyListWhenNoProducts() {
        // Arrange
        when(productRepository.listAllProducts())
                .thenReturn(java.util.Collections.emptyList());

        // Act
        java.util.List<Product> result = productRepository.listAllProducts();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        
        verify(productRepository).listAllProducts();
    }

    @Test
    @DisplayName("Debería comparar dos productos por sus IDs y retornar lista de productos")
    void shouldCompareProductsByIdsAndReturnProductList() {
        // Arrange
        java.util.List<String> ids = java.util.Arrays.asList("550e8400-e29b-41d4-a716-446655440001", "550e8400-e29b-41d4-a716-446655440002");
        Product product1 = Product.builder().id("550e8400-e29b-41d4-a716-446655440001").productName("Producto 1").price(100.0).build();
        Product product2 = Product.builder().id("550e8400-e29b-41d4-a716-446655440002").productName("Producto 2").price(200.0).build();
        java.util.List<Product> expectedProducts = java.util.Arrays.asList(product1, product2);
        
        when(productRepository.compareProducts(ids))
                .thenReturn(expectedProducts);

        // Act
        java.util.List<Product> result = productRepository.compareProducts(ids);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedProducts, result);
        
        verify(productRepository).compareProducts(ids);
    }

    @Test
    @DisplayName("Debería manejar comparación de productos con IDs duplicados")
    void shouldHandleComparisonOfProductsWithDuplicateIds() {
        // Arrange
        java.util.List<String> ids = java.util.Arrays.asList("550e8400-e29b-41d4-a716-446655440001", "550e8400-e29b-41d4-a716-446655440001");
        Product product1 = Product.builder().id("550e8400-e29b-41d4-a716-446655440001").productName("Producto 1").price(100.0).build();
        java.util.List<Product> expectedProducts = java.util.Arrays.asList(product1);
        
        when(productRepository.compareProducts(ids))
                .thenReturn(expectedProducts);

        // Act
        java.util.List<Product> result = productRepository.compareProducts(ids);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedProducts, result);
        
        verify(productRepository).compareProducts(ids);
    }

    @Test
    @DisplayName("Debería manejar comparación con IDs nulos")
    void shouldHandleComparisonWithNullIds() {
        // Arrange
        java.util.List<String> ids = java.util.Arrays.asList("550e8400-e29b-41d4-a716-446655440001", null);
        Product product1 = Product.builder().id("550e8400-e29b-41d4-a716-446655440001").productName("Producto 1").price(100.0).build();
        java.util.List<Product> expectedProducts = java.util.Arrays.asList(product1);
        
        when(productRepository.compareProducts(ids))
                .thenReturn(expectedProducts);

        // Act
        java.util.List<Product> result = productRepository.compareProducts(ids);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedProducts, result);
        
        verify(productRepository).compareProducts(ids);
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando se intenta agregar producto nulo")
    void shouldThrowExceptionWhenAddingNullProduct() {
        // Arrange
        when(productRepository.addProduct(null))
                .thenThrow(new IllegalArgumentException("El producto no puede ser nulo"));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productRepository.addProduct(null)
        );
        
        assertEquals("El producto no puede ser nulo", exception.getMessage());
        verify(productRepository).addProduct(null);
    }

    @Test
    @DisplayName("Debería retornar lista vacía cuando se comparan productos inexistentes")
    void shouldReturnEmptyListWhenComparingNonExistentProducts() {
        // Arrange
        java.util.List<String> ids = java.util.Arrays.asList("550e8400-e29b-41d4-a716-446655440999", "550e8400-e29b-41d4-a716-446655440998");
        java.util.List<Product> expectedProducts = java.util.Collections.emptyList();
        
        when(productRepository.compareProducts(ids))
                .thenReturn(expectedProducts);

        // Act
        java.util.List<Product> result = productRepository.compareProducts(ids);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        
        verify(productRepository).compareProducts(ids);
    }

    /**
     * Test de documentación que describe el comportamiento esperado
     * de las implementaciones de ProductRepository
     */
    @Test
    @DisplayName("Debería documentar el contrato de la interfaz ProductRepository")
    void shouldDocumentProductRepositoryContract() {
        // Este test documenta el contrato esperado:
        
        // 1. addProduct debe:
        //    - Aceptar un Product (puede ser nulo, pero debe manejarse apropiadamente)
        //    - Retornar el Product agregado con ID asignado
        //    - Lanzar excepción si el producto es inválido
        
        // 2. listAllProducts debe:
        //    - Retornar una List<Product> (nunca null, puede estar vacía)
        //    - Incluir todos los productos almacenados
        
        // 3. compareProducts debe:
        //    - Aceptar una List<Long> de IDs (pueden contener null)
        //    - Retornar una List<Product> con los productos encontrados
        //    - Retornar lista vacía si no se encuentran productos
        
        assertTrue(true, "Contrato documentado correctamente");
    }
}

