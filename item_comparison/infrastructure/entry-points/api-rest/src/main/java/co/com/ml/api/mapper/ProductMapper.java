package co.com.ml.api.mapper;

import co.com.ml.api.dto.ProductDto;
import co.com.ml.model.product.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
     */
    public Product toModel(ProductDto productDto) {
        if (productDto == null) {
            return null;
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
     */
    public ProductDto toDto(Product product) {
        if (product == null) {
            return null;
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
     */
    public List<Product> toModelList(List<ProductDto> productDtos) {
        if (productDtos == null) {
            return null;
        }

        return productDtos.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una lista de Product a una lista de ProductDto.
     * 
     * @param products la lista de Products del modelo de dominio
     * @return la lista de ProductDtos
     */
    public List<ProductDto> toDtoList(List<Product> products) {
        if (products == null) {
            return null;
        }

        return products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
