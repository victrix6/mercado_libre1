package co.com.ml.usecase.product;

import co.com.ml.model.product.Product;
import co.com.ml.model.product.gateways.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para ProductUseCase")
class ProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductUseCase productUseCase;

    private Product validProduct;
    private Product productWithId;

    @BeforeEach
    void setUp() {
        validProduct = Product.builder()
                .productName("Laptop Gaming")
                .imageUrl("https://example.com/laptop.jpg")
                .description("Laptop para gaming de alta gama")
                .price(1500.0)
                .rating(4.5)
                .specifications("Intel i7, 16GB RAM, RTX 3070")
                .build();

        productWithId = validProduct.toBuilder()
                .id("550e8400-e29b-41d4-a716-446655440001")
                .build();
    }

    @Test
    @DisplayName("Debería agregar un producto válido exitosamente")
    void shouldAddValidProductSuccessfully() {
        // Arrange
        when(productRepository.addProduct(any(Product.class)))
                .thenReturn(productWithId);

        // Act
        Product result = productUseCase.addProduct(validProduct);

        // Assert
        assertNotNull(result);
        assertEquals("550e8400-e29b-41d4-a716-446655440001", result.getId());
        assertEquals("Laptop Gaming", result.getProductName());
        assertEquals(1500.0, result.getPrice());
        
        verify(productRepository).addProduct(validProduct);
    }

    @Test
    @DisplayName("Debería agregar un producto nulo (validaciones se hacen en el controlador)")
    void shouldAddNullProduct() {
        // Arrange
        when(productRepository.addProduct(null))
                .thenReturn(null);

        // Act
        Product result = productUseCase.addProduct(null);

        // Assert
        assertNull(result);
        verify(productRepository).addProduct(null);
    }



    @Test
    @DisplayName("Debería retornar lista de productos cuando existen productos")
    void shouldReturnProductListWhenProductsExist() {
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
        
        List<Product> expectedProducts = Arrays.asList(product1, product2);
        
        when(productRepository.listAllProducts())
                .thenReturn(expectedProducts);

        // Act
        List<Product> result = productUseCase.listAllProducts();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(product1.getId(), result.get(0).getId());
        assertEquals(product2.getId(), result.get(1).getId());
        
        verify(productRepository).listAllProducts();
    }

    @Test
    @DisplayName("Debería retornar lista vacía cuando no existen productos")
    void shouldReturnEmptyListWhenNoProductsExist() {
        // Arrange
        when(productRepository.listAllProducts())
                .thenReturn(Collections.emptyList());

        // Act
        List<Product> result = productUseCase.listAllProducts();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        
        verify(productRepository).listAllProducts();
    }

    @Test
    @DisplayName("Debería comparar productos con IDs diferentes exitosamente")
    void shouldCompareProductsWithDifferentIdsSuccessfully() {
        // Arrange
        List<String> ids = Arrays.asList("550e8400-e29b-41d4-a716-446655440001", "550e8400-e29b-41d4-a716-446655440002");
        Product product1 = Product.builder().id("550e8400-e29b-41d4-a716-446655440001").productName("Producto 1").price(100.0).build();
        Product product2 = Product.builder().id("550e8400-e29b-41d4-a716-446655440002").productName("Producto 2").price(200.0).build();
        List<Product> expectedProducts = Arrays.asList(product1, product2);
        
        when(productRepository.compareProducts(ids))
                .thenReturn(expectedProducts);

        // Act
        List<Product> result = productUseCase.compareProducts(ids);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedProducts, result);
        
        verify(productRepository).compareProducts(ids);
    }

    @Test
    @DisplayName("Debería procesar IDs duplicados (validaciones en controlador)")
    void shouldProcessDuplicateIds() {
        // Arrange
        List<String> ids = Arrays.asList("550e8400-e29b-41d4-a716-446655440001", "550e8400-e29b-41d4-a716-446655440001", "550e8400-e29b-41d4-a716-446655440001");
        Product product1 = Product.builder().id("550e8400-e29b-41d4-a716-446655440001").productName("Producto 1").build();
        List<Product> expectedProducts = Arrays.asList(product1);
        
        when(productRepository.compareProducts(ids))
                .thenReturn(expectedProducts);

        // Act
        List<Product> result = productUseCase.compareProducts(ids);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedProducts, result);
        verify(productRepository).compareProducts(ids);
    }

    @Test
    @DisplayName("Debería procesar IDs con duplicados (validaciones en controlador)")
    void shouldProcessIdsWithDuplicates() {
        // Arrange
        List<String> ids = Arrays.asList("550e8400-e29b-41d4-a716-446655440001", "550e8400-e29b-41d4-a716-446655440001", "550e8400-e29b-41d4-a716-446655440002");
        Product product1 = Product.builder().id("550e8400-e29b-41d4-a716-446655440001").productName("Producto 1").price(100.0).build();
        Product product2 = Product.builder().id("550e8400-e29b-41d4-a716-446655440002").productName("Producto 2").price(200.0).build();
        List<Product> expectedProducts = Arrays.asList(product1, product2);
        
        when(productRepository.compareProducts(ids))
                .thenReturn(expectedProducts);

        // Act
        List<Product> result = productUseCase.compareProducts(ids);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedProducts, result);
        
        verify(productRepository).compareProducts(ids);
    }

    @Test
    @DisplayName("Debería procesar lista de IDs nula (validaciones en controlador)")
    void shouldProcessNullIdList() {
        // Arrange
        when(productRepository.compareProducts(null))
                .thenReturn(java.util.Collections.emptyList());

        // Act
        java.util.List<Product> result = productUseCase.compareProducts(null);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(productRepository).compareProducts(null);
    }

    @Test
    @DisplayName("Debería procesar lista con menos de 2 IDs (validaciones en controlador)")
    void shouldProcessListWithLessThanTwoIds() {
        // Arrange
        List<String> singleId = Arrays.asList("550e8400-e29b-41d4-a716-446655440001");
        Product product1 = Product.builder().id("550e8400-e29b-41d4-a716-446655440001").productName("Producto 1").build();
        java.util.List<Product> expectedProducts = Arrays.asList(product1);
        
        when(productRepository.compareProducts(singleId))
                .thenReturn(expectedProducts);

        // Act
        java.util.List<Product> result = productUseCase.compareProducts(singleId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedProducts, result);
        verify(productRepository).compareProducts(singleId);
    }

    @Test
    @DisplayName("Debería procesar lista vacía de productos encontrados (validaciones en controlador)")
    void shouldProcessEmptyFoundProductsList() {
        // Arrange
        List<String> ids = Arrays.asList("550e8400-e29b-41d4-a716-446655440001", "550e8400-e29b-41d4-a716-446655440002");
        List<Product> foundProducts = Arrays.asList(); // No se encontraron productos
        
        when(productRepository.compareProducts(ids))
                .thenReturn(foundProducts);

        // Act
        List<Product> result = productUseCase.compareProducts(ids);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(productRepository).compareProducts(ids);
    }

    @Test
    @DisplayName("Debería manejar excepción del repositorio al agregar producto")
    void shouldHandleRepositoryExceptionWhenAddingProduct() {
        // Arrange
        when(productRepository.addProduct(any(Product.class)))
                .thenThrow(new RuntimeException("Error de base de datos"));

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> productUseCase.addProduct(validProduct)
        );
        
        assertEquals("Error de base de datos", exception.getMessage());
        verify(productRepository).addProduct(validProduct);
    }

    @Test
    @DisplayName("Debería manejar excepción del repositorio al listar productos")
    void shouldHandleRepositoryExceptionWhenListingProducts() {
        // Arrange
        when(productRepository.listAllProducts())
                .thenThrow(new RuntimeException("Error de conexión"));

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> productUseCase.listAllProducts()
        );
        
        assertEquals("Error de conexión", exception.getMessage());
        verify(productRepository).listAllProducts();
    }

    @Test
    @DisplayName("Debería manejar excepción del repositorio al comparar productos")
    void shouldHandleRepositoryExceptionWhenComparingProducts() {
        // Arrange
        List<String> ids = Arrays.asList("550e8400-e29b-41d4-a716-446655440001", "550e8400-e29b-41d4-a716-446655440002");
        when(productRepository.compareProducts(any()))
                .thenThrow(new RuntimeException("Producto no encontrado"));

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> productUseCase.compareProducts(ids)
        );
        
        assertEquals("Producto no encontrado", exception.getMessage());
        verify(productRepository).compareProducts(ids);
    }



    @Test
    @DisplayName("Debería manejar más de 2 IDs correctamente cuando existen al menos 2 productos")
    void shouldHandleMoreThanTwoIdsCorrectlyWhenAtLeastTwoProductsExist() {
        // Arrange
        List<String> ids = Arrays.asList("550e8400-e29b-41d4-a716-446655440001", "550e8400-e29b-41d4-a716-446655440002", "550e8400-e29b-41d4-a716-446655440003");
        Product product1 = Product.builder().id("550e8400-e29b-41d4-a716-446655440001").productName("Producto 1").price(100.0).build();
        Product product2 = Product.builder().id("550e8400-e29b-41d4-a716-446655440002").productName("Producto 2").price(200.0).build();
        List<Product> foundProducts = Arrays.asList(product1, product2); // Solo 2 de 3 encontrados
        
        when(productRepository.compareProducts(ids))
                .thenReturn(foundProducts);

        // Act
        List<Product> result = productUseCase.compareProducts(ids);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(foundProducts, result);
        
        verify(productRepository).compareProducts(ids);
    }

    @Test
    @DisplayName("Debería procesar menos de 2 productos encontrados con más de 2 IDs (validaciones en controlador)")
    void shouldProcessLessThanTwoProductsFoundWithMoreThanTwoIds() {
        // Arrange
        List<String> ids = Arrays.asList("550e8400-e29b-41d4-a716-446655440001", "550e8400-e29b-41d4-a716-446655440002", "550e8400-e29b-41d4-a716-446655440003");
        Product product1 = Product.builder().id("550e8400-e29b-41d4-a716-446655440001").productName("Producto 1").price(100.0).build();
        List<Product> foundProducts = Arrays.asList(product1); // Solo 1 de 3 encontrados
        
        when(productRepository.compareProducts(ids))
                .thenReturn(foundProducts);

        // Act
        List<Product> result = productUseCase.compareProducts(ids);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(foundProducts, result);
        verify(productRepository).compareProducts(ids);
    }
}

