package co.com.ml.api.mapper;

import co.com.ml.api.dto.ProductDto;
import co.com.ml.model.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para ProductMapper")
class ProductMapperTest {

    private ProductMapper productMapper;
    private ProductDto validProductDto;
    private Product validProduct;

    @BeforeEach
    void setUp() {
        productMapper = new ProductMapper();
        
        validProductDto = ProductDto.builder()
                .id("550e8400-e29b-41d4-a716-446655440001")
                .productName("Laptop Gaming")
                .imageUrl("https://example.com/laptop.jpg")
                .description("Laptop para gaming de alta gama")
                .price(1500.0)
                .rating(4.5)
                .specifications("Intel i7, 16GB RAM, RTX 3070")
                .build();

        validProduct = Product.builder()
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
    @DisplayName("Debería convertir ProductDto a Product correctamente")
    void shouldConvertProductDtoToProductCorrectly() {
        // Act
        Product result = productMapper.toModel(validProductDto);

        // Assert
        assertNotNull(result);
        assertEquals(validProductDto.getId(), result.getId());
        assertEquals(validProductDto.getProductName(), result.getProductName());
        assertEquals(validProductDto.getImageUrl(), result.getImageUrl());
        assertEquals(validProductDto.getDescription(), result.getDescription());
        assertEquals(validProductDto.getPrice(), result.getPrice());
        assertEquals(validProductDto.getRating(), result.getRating());
        assertEquals(validProductDto.getSpecifications(), result.getSpecifications());
    }

    @Test
    @DisplayName("Debería convertir Product a ProductDto correctamente")
    void shouldConvertProductToProductDtoCorrectly() {
        // Act
        ProductDto result = productMapper.toDto(validProduct);

        // Assert
        assertNotNull(result);
        assertEquals(validProduct.getId(), result.getId());
        assertEquals(validProduct.getProductName(), result.getProductName());
        assertEquals(validProduct.getImageUrl(), result.getImageUrl());
        assertEquals(validProduct.getDescription(), result.getDescription());
        assertEquals(validProduct.getPrice(), result.getPrice());
        assertEquals(validProduct.getRating(), result.getRating());
        assertEquals(validProduct.getSpecifications(), result.getSpecifications());
    }

    @Test
    @DisplayName("Debería retornar null cuando ProductDto es null")
    void shouldReturnNullWhenProductDtoIsNull() {
        // Act
        Product result = productMapper.toModel(null);

        // Assert
        assertNull(result);
    }

    @Test
    @DisplayName("Debería retornar null cuando Product es null")
    void shouldReturnNullWhenProductIsNull() {
        // Act
        ProductDto result = productMapper.toDto(null);

        // Assert
        assertNull(result);
    }

    @Test
    @DisplayName("Debería convertir lista de ProductDto a lista de Product correctamente")
    void shouldConvertProductDtoListToProductListCorrectly() {
        // Arrange
        ProductDto productDto2 = ProductDto.builder()
                .id("550e8400-e29b-41d4-a716-446655440002")
                .productName("Smartphone")
                .imageUrl("https://example.com/phone.jpg")
                .description("Smartphone de última generación")
                .price(800.0)
                .rating(4.2)
                .specifications("128GB, 8GB RAM, Cámara 108MP")
                .build();

        List<ProductDto> productDtos = Arrays.asList(validProductDto, productDto2);

        // Act
        List<Product> result = productMapper.toModelList(productDtos);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(validProductDto.getId(), result.get(0).getId());
        assertEquals(productDto2.getId(), result.get(1).getId());
    }

    @Test
    @DisplayName("Debería convertir lista de Product a lista de ProductDto correctamente")
    void shouldConvertProductListToProductDtoListCorrectly() {
        // Arrange
        Product product2 = Product.builder()
                .id("550e8400-e29b-41d4-a716-446655440002")
                .productName("Smartphone")
                .imageUrl("https://example.com/phone.jpg")
                .description("Smartphone de última generación")
                .price(800.0)
                .rating(4.2)
                .specifications("128GB, 8GB RAM, Cámara 108MP")
                .build();

        List<Product> products = Arrays.asList(validProduct, product2);

        // Act
        List<ProductDto> result = productMapper.toDtoList(products);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(validProduct.getId(), result.get(0).getId());
        assertEquals(product2.getId(), result.get(1).getId());
    }

    @Test
    @DisplayName("Debería manejar lista vacía correctamente")
    void shouldHandleEmptyListCorrectly() {
        // Act
        List<Product> result = productMapper.toModelList(Collections.emptyList());

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Debería retornar null cuando lista de ProductDto es null")
    void shouldReturnNullWhenProductDtoListIsNull() {
        // Act
        List<Product> result = productMapper.toModelList(null);

        // Assert
        assertNull(result);
    }

    @Test
    @DisplayName("Debería retornar null cuando lista de Product es null")
    void shouldReturnNullWhenProductListIsNull() {
        // Act
        List<ProductDto> result = productMapper.toDtoList(null);

        // Assert
        assertNull(result);
    }

    @Test
    @DisplayName("Debería preservar todos los campos durante la conversión bidireccional")
    void shouldPreserveAllFieldsDuringBidirectionalConversion() {
        // Act - Convertir DTO a Model y luego de vuelta a DTO
        Product intermediateModel = productMapper.toModel(validProductDto);
        ProductDto result = productMapper.toDto(intermediateModel);

        // Assert
        assertEquals(validProductDto.getId(), result.getId());
        assertEquals(validProductDto.getProductName(), result.getProductName());
        assertEquals(validProductDto.getImageUrl(), result.getImageUrl());
        assertEquals(validProductDto.getDescription(), result.getDescription());
        assertEquals(validProductDto.getPrice(), result.getPrice());
        assertEquals(validProductDto.getRating(), result.getRating());
        assertEquals(validProductDto.getSpecifications(), result.getSpecifications());
    }
}
