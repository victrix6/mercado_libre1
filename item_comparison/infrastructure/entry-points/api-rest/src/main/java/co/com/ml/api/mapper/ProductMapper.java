package co.com.ml.api.mapper;

import co.com.ml.api.dto.ProductDto;
import co.com.ml.model.product.Product;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Mapper para convertir entre DTOs y modelos de dominio.
 * Proporciona métodos para mapear ProductDto a Product y viceversa.
 */
@Component
public class ProductMapper {

    /**
     * Convierte un ProductDto a un Product del modelo de dominio.
     *
     * @param productDto el DTO a convertir
     * @return el Product del modelo de dominio
     * @throws IllegalArgumentException si productDto es null
     */
    public Product toModel(ProductDto productDto) {
        if (productDto == null) {
            throw new IllegalArgumentException("ProductDto no puede ser null");
        }
        
        return Product.builder()
                .id(productDto.getId())
                .productName(productDto.getProductName())
                .imageUrl(productDto.getImageUrl())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .rating(productDto.getRating())
                .specifications(productDto.getSpecifications())
                .build();
    }

    /**
     * Convierte un Product del modelo de dominio a un ProductDto.
     *
     * @param product el Product del modelo de dominio
     * @return el ProductDto
     * @throws IllegalArgumentException si product es null
     */
    public ProductDto toDto(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product no puede ser null");
        }
        
        return ProductDto.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .imageUrl(product.getImageUrl())
                .description(product.getDescription())
                .price(product.getPrice())
                .rating(product.getRating())
                .specifications(product.getSpecifications())
                .build();
    }

    /**
     * Convierte una lista de ProductDto a una lista de Product.
     *
     * @param productDtos la lista de DTOs a convertir
     * @return la lista de Products del modelo de dominio
     * @throws IllegalArgumentException si productDtos es null
     */
    public List<Product> toModelList(List<ProductDto> productDtos) {
        if (productDtos == null) {
            throw new IllegalArgumentException("Lista de ProductDtos no puede ser null");
        }
        
        if (productDtos.isEmpty()) {
            return Collections.emptyList();
        }
        
        return productDtos.stream()
                .map(this::toModel)
                .toList();
    }

    /**
     * Convierte una lista de Product a una lista de ProductDto.
     *
     * @param products la lista de Products del modelo de dominio
     * @return la lista de ProductDtos
     * @throws IllegalArgumentException si products es null
     */
    public List<ProductDto> toDtoList(List<Product> products) {
        if (products == null) {
            throw new IllegalArgumentException("Lista de Products no puede ser null");
        }
        
        if (products.isEmpty()) {
            return Collections.emptyList();
        }

        return products.stream()
                .map(this::toDto)
                .toList();
    }
}
