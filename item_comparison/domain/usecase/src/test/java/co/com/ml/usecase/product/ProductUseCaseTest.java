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
                .id(1L)
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
        assertEquals(1L, result.getId());
        assertEquals("Laptop Gaming", result.getProductName());
        assertEquals(1500.0, result.getPrice());
        
        verify(productRepository).addProduct(validProduct);
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando se intenta agregar un producto nulo")
    void shouldThrowExceptionWhenAddingNullProduct() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productUseCase.addProduct(null)
        );
        
        assertEquals("El producto no puede ser nulo", exception.getMessage());
        verify(productRepository, never()).addProduct(any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el nombre del producto es nulo")
    void shouldThrowExceptionWhenProductNameIsNull() {
        // Arrange
        Product productWithNullName = validProduct.toBuilder()
                .productName(null)
                .build();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productUseCase.addProduct(productWithNullName)
        );
        
        assertEquals("El nombre del producto es obligatorio", exception.getMessage());
        verify(productRepository, never()).addProduct(any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el nombre del producto está vacío")
    void shouldThrowExceptionWhenProductNameIsEmpty() {
        // Arrange
        Product productWithEmptyName = validProduct.toBuilder()
                .productName("   ")
                .build();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productUseCase.addProduct(productWithEmptyName)
        );
        
        assertEquals("El nombre del producto es obligatorio", exception.getMessage());
        verify(productRepository, never()).addProduct(any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el precio del producto es nulo")
    void shouldThrowExceptionWhenProductPriceIsNull() {
        // Arrange
        Product productWithNullPrice = validProduct.toBuilder()
                .price(null)
                .build();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productUseCase.addProduct(productWithNullPrice)
        );
        
        assertEquals("El precio del producto debe ser mayor a cero", exception.getMessage());
        verify(productRepository, never()).addProduct(any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el precio del producto es cero")
    void shouldThrowExceptionWhenProductPriceIsZero() {
        // Arrange
        Product productWithZeroPrice = validProduct.toBuilder()
                .price(0.0)
                .build();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productUseCase.addProduct(productWithZeroPrice)
        );
        
        assertEquals("El precio del producto debe ser mayor a cero", exception.getMessage());
        verify(productRepository, never()).addProduct(any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el precio del producto es negativo")
    void shouldThrowExceptionWhenProductPriceIsNegative() {
        // Arrange
        Product productWithNegativePrice = validProduct.toBuilder()
                .price(-100.0)
                .build();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productUseCase.addProduct(productWithNegativePrice)
        );
        
        assertEquals("El precio del producto debe ser mayor a cero", exception.getMessage());
        verify(productRepository, never()).addProduct(any());
    }

    @Test
    @DisplayName("Debería retornar lista de productos cuando existen productos")
    void shouldReturnProductListWhenProductsExist() {
        // Arrange
        Product product1 = Product.builder()
                .id(1L)
                .productName("Producto 1")
                .price(100.0)
                .build();
        
        Product product2 = Product.builder()
                .id(2L)
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
        List<Long> ids = Arrays.asList(1L, 2L);
        Product product1 = Product.builder().id(1L).productName("Producto 1").price(100.0).build();
        Product product2 = Product.builder().id(2L).productName("Producto 2").price(200.0).build();
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
    @DisplayName("Debería lanzar excepción cuando todos los IDs son duplicados (solo queda 1 único)")
    void shouldThrowExceptionWhenAllIdsAreDuplicates() {
        // Arrange
        List<Long> ids = Arrays.asList(1L, 1L, 1L);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productUseCase.compareProducts(ids)
        );
        
        assertEquals("Debe proporcionar al menos dos IDs válidos (no nulos)", exception.getMessage());
        verify(productRepository, never()).compareProducts(any());
    }

    @Test
    @DisplayName("Debería manejar IDs duplicados correctamente cuando hay al menos 2 únicos")
    void shouldHandleDuplicateIdsCorrectlyWhenAtLeastTwoUnique() {
        // Arrange
        List<Long> ids = Arrays.asList(1L, 1L, 2L);
        Product product1 = Product.builder().id(1L).productName("Producto 1").price(100.0).build();
        Product product2 = Product.builder().id(2L).productName("Producto 2").price(200.0).build();
        List<Product> expectedProducts = Arrays.asList(product1, product2);
        
        // La normalización elimina duplicados, así que el repositorio recibe [1L, 2L]
        List<Long> normalizedIds = Arrays.asList(1L, 2L);
        when(productRepository.compareProducts(normalizedIds))
                .thenReturn(expectedProducts);

        // Act
        List<Product> result = productUseCase.compareProducts(ids);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedProducts, result);
        
        verify(productRepository).compareProducts(normalizedIds);
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando la lista de IDs es nula")
    void shouldThrowExceptionWhenIdListIsNull() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productUseCase.compareProducts(null)
        );
        
        assertEquals("La lista de IDs no puede ser nula", exception.getMessage());
        verify(productRepository, never()).compareProducts(any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando hay menos de 2 IDs")
    void shouldThrowExceptionWhenLessThanTwoIds() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productUseCase.compareProducts(Arrays.asList(1L))
        );
        
        assertEquals("Debe proporcionar al menos dos IDs", exception.getMessage());
        verify(productRepository, never()).compareProducts(any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando faltan productos para comparar (exactamente 2 IDs)")
    void shouldThrowExceptionWhenMissingProductsForComparisonWithTwoIds() {
        // Arrange
        List<Long> ids = Arrays.asList(1L, 2L);
        List<Product> foundProducts = Arrays.asList(); // No se encontraron productos
        
        when(productRepository.compareProducts(ids))
                .thenReturn(foundProducts);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productUseCase.compareProducts(ids)
        );
        
        assertEquals("Alguno de los productos solicitados no existe", exception.getMessage());
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
        List<Long> ids = Arrays.asList(1L, 2L);
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
    @DisplayName("Debería lanzar excepción cuando la descripción es nula")
    void shouldThrowExceptionWhenDescriptionIsNull() {
        // Arrange
        Product productWithNullDescription = validProduct.toBuilder()
                .description(null)
                .build();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productUseCase.addProduct(productWithNullDescription)
        );
        
        assertEquals("La descripción del producto es obligatoria", exception.getMessage());
        verify(productRepository, never()).addProduct(any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando la URL de imagen es nula")
    void shouldThrowExceptionWhenImageUrlIsNull() {
        // Arrange
        Product productWithNullImageUrl = validProduct.toBuilder()
                .imageUrl(null)
                .build();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productUseCase.addProduct(productWithNullImageUrl)
        );
        
        assertEquals("La URL de la imagen es obligatoria", exception.getMessage());
        verify(productRepository, never()).addProduct(any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando la calificación es nula")
    void shouldThrowExceptionWhenRatingIsNull() {
        // Arrange
        Product productWithNullRating = validProduct.toBuilder()
                .rating(null)
                .build();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productUseCase.addProduct(productWithNullRating)
        );
        
        assertEquals("La calificación del producto debe ser mayor o igual a cero", exception.getMessage());
        verify(productRepository, never()).addProduct(any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando la calificación es negativa")
    void shouldThrowExceptionWhenRatingIsNegative() {
        // Arrange
        Product productWithNegativeRating = validProduct.toBuilder()
                .rating(-1.0)
                .build();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productUseCase.addProduct(productWithNegativeRating)
        );
        
        assertEquals("La calificación del producto debe ser mayor o igual a cero", exception.getMessage());
        verify(productRepository, never()).addProduct(any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando las especificaciones son nulas")
    void shouldThrowExceptionWhenSpecificationsIsNull() {
        // Arrange
        Product productWithNullSpecifications = validProduct.toBuilder()
                .specifications(null)
                .build();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productUseCase.addProduct(productWithNullSpecifications)
        );
        
        assertEquals("Las especificaciones del producto son obligatorias", exception.getMessage());
        verify(productRepository, never()).addProduct(any());
    }

    @Test
    @DisplayName("Debería manejar más de 2 IDs correctamente cuando existen al menos 2 productos")
    void shouldHandleMoreThanTwoIdsCorrectlyWhenAtLeastTwoProductsExist() {
        // Arrange
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        Product product1 = Product.builder().id(1L).productName("Producto 1").price(100.0).build();
        Product product2 = Product.builder().id(2L).productName("Producto 2").price(200.0).build();
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
    @DisplayName("Debería lanzar excepción cuando hay más de 2 IDs pero menos de 2 productos encontrados")
    void shouldThrowExceptionWhenMoreThanTwoIdsButLessThanTwoProductsFound() {
        // Arrange
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        Product product1 = Product.builder().id(1L).productName("Producto 1").price(100.0).build();
        List<Product> foundProducts = Arrays.asList(product1); // Solo 1 de 3 encontrados
        
        when(productRepository.compareProducts(ids))
                .thenReturn(foundProducts);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productUseCase.compareProducts(ids)
        );
        
        assertEquals("Se requieren al menos dos productos existentes para comparar", exception.getMessage());
        verify(productRepository).compareProducts(ids);
    }
}

