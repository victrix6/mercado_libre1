package co.com.ml.model.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para la clase Product")
class ProductTest {

    @Test
    @DisplayName("Debería crear un producto con constructor por defecto")
    void shouldCreateProductWithDefaultConstructor() {
        // Act
        Product product = new Product();
        
        // Assert
        assertNotNull(product);
        assertNull(product.getId());
        assertNull(product.getProductName());
        assertNull(product.getImageUrl());
        assertNull(product.getDescription());
        assertNull(product.getPrice());
        assertNull(product.getRating());
        assertNull(product.getSpecifications());
    }

    @Test
    @DisplayName("Debería crear un producto con constructor con todos los parámetros")
    void shouldCreateProductWithAllArgsConstructor() {
        // Arrange
        Long id = 1L;
        String productName = "Laptop Gaming";
        String imageUrl = "https://example.com/laptop.jpg";
        String description = "Laptop para gaming de alta gama";
        Double price = 1500.0;
        Double rating = 4.5;
        String specifications = "Intel i7, 16GB RAM, RTX 3070";

        // Act
        Product product = new Product(id, productName, imageUrl, description, price, rating, specifications);

        // Assert
        assertNotNull(product);
        assertEquals(id, product.getId());
        assertEquals(productName, product.getProductName());
        assertEquals(imageUrl, product.getImageUrl());
        assertEquals(description, product.getDescription());
        assertEquals(price, product.getPrice());
        assertEquals(rating, product.getRating());
        assertEquals(specifications, product.getSpecifications());
    }

    @Test
    @DisplayName("Debería crear un producto usando el patrón Builder")
    void shouldCreateProductUsingBuilder() {
        // Arrange
        Long id = 2L;
        String productName = "Smartphone";
        String imageUrl = "https://example.com/phone.jpg";
        String description = "Smartphone de última generación";
        Double price = 800.0;
        Double rating = 4.8;
        String specifications = "128GB, 8GB RAM, Cámara 108MP";

        // Act
        Product product = Product.builder()
                .id(id)
                .productName(productName)
                .imageUrl(imageUrl)
                .description(description)
                .price(price)
                .rating(rating)
                .specifications(specifications)
                .build();

        // Assert
        assertNotNull(product);
        assertEquals(id, product.getId());
        assertEquals(productName, product.getProductName());
        assertEquals(imageUrl, product.getImageUrl());
        assertEquals(description, product.getDescription());
        assertEquals(price, product.getPrice());
        assertEquals(rating, product.getRating());
        assertEquals(specifications, product.getSpecifications());
    }

    @Test
    @DisplayName("Debería crear un producto usando Builder con algunos campos nulos")
    void shouldCreateProductUsingBuilderWithSomeNullFields() {
        // Act
        Product product = Product.builder()
                .id(3L)
                .productName("Tablet")
                .price(300.0)
                .build();

        // Assert
        assertNotNull(product);
        assertEquals(3L, product.getId());
        assertEquals("Tablet", product.getProductName());
        assertEquals(300.0, product.getPrice());
        assertNull(product.getImageUrl());
        assertNull(product.getDescription());
        assertNull(product.getRating());
        assertNull(product.getSpecifications());
    }

    @Test
    @DisplayName("Debería permitir modificar campos usando setters")
    void shouldAllowModifyingFieldsUsingSetters() {
        // Arrange
        Product product = new Product();
        Long id = 4L;
        String productName = "Monitor";
        String imageUrl = "https://example.com/monitor.jpg";
        String description = "Monitor 4K de 27 pulgadas";
        Double price = 400.0;
        Double rating = 4.2;
        String specifications = "4K, 60Hz, IPS";

        // Act
        product.setId(id);
        product.setProductName(productName);
        product.setImageUrl(imageUrl);
        product.setDescription(description);
        product.setPrice(price);
        product.setRating(rating);
        product.setSpecifications(specifications);

        // Assert
        assertEquals(id, product.getId());
        assertEquals(productName, product.getProductName());
        assertEquals(imageUrl, product.getImageUrl());
        assertEquals(description, product.getDescription());
        assertEquals(price, product.getPrice());
        assertEquals(rating, product.getRating());
        assertEquals(specifications, product.getSpecifications());
    }

    @Test
    @DisplayName("Debería crear una copia del producto usando toBuilder")
    void shouldCreateProductCopyUsingToBuilder() {
        // Arrange
        Product originalProduct = Product.builder()
                .id(5L)
                .productName("Teclado")
                .imageUrl("https://example.com/keyboard.jpg")
                .description("Teclado mecánico")
                .price(150.0)
                .rating(4.7)
                .specifications("RGB, Cherry MX Blue")
                .build();

        // Act
        Product copiedProduct = originalProduct.toBuilder()
                .id(6L)
                .productName("Teclado Pro")
                .price(200.0)
                .build();

        // Assert
        assertNotNull(copiedProduct);
        assertEquals(6L, copiedProduct.getId());
        assertEquals("Teclado Pro", copiedProduct.getProductName());
        assertEquals("https://example.com/keyboard.jpg", copiedProduct.getImageUrl());
        assertEquals("Teclado mecánico", copiedProduct.getDescription());
        assertEquals(200.0, copiedProduct.getPrice());
        assertEquals(4.7, copiedProduct.getRating());
        assertEquals("RGB, Cherry MX Blue", copiedProduct.getSpecifications());

        // Verificar que el producto original no cambió
        assertEquals(5L, originalProduct.getId());
        assertEquals("Teclado", originalProduct.getProductName());
        assertEquals(150.0, originalProduct.getPrice());
    }

    @Test
    @DisplayName("Debería implementar equals y hashCode correctamente")
    void shouldImplementEqualsAndHashCodeCorrectly() {
        // Arrange
        Product product1 = Product.builder()
                .id(1L)
                .productName("Producto Test")
                .price(100.0)
                .build();

        Product product2 = Product.builder()
                .id(1L)
                .productName("Producto Test")
                .price(100.0)
                .build();

        Product product3 = Product.builder()
                .id(2L)
                .productName("Producto Test")
                .price(100.0)
                .build();

        // Act & Assert
        assertEquals(product1, product2);
        assertNotEquals(product1, product3);
        assertEquals(product1.hashCode(), product2.hashCode());
        assertNotEquals(product1.hashCode(), product3.hashCode());
    }

    @Test
    @DisplayName("Debería implementar toString correctamente")
    void shouldImplementToStringCorrectly() {
        // Arrange
        Product product = Product.builder()
                .id(1L)
                .productName("Producto Test")
                .price(100.0)
                .build();

        // Act
        String toString = product.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("Product"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("productName=Producto Test"));
        assertTrue(toString.contains("price=100.0"));
    }
}