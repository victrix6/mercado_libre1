package co.com.ml.api;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.com.ml.model.product.Product;
import co.com.ml.usecase.product.ProductUseCase;
import co.com.ml.api.util.ProductValidationUtil;
import co.com.ml.api.dto.ProductDto;
import co.com.ml.api.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;

/**
 * API Rest controller para productos.
 */
@RestController
@ResponseBody
@RequestMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ApiRest {

    private final ProductUseCase productUseCase;
    private final ProductValidationUtil productValidationUtil;
    private final ProductMapper productMapper;


    /**
     * Obtiene todos los productos
     * @return lista de productos
     */
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Product> products = productUseCase.listAllProducts();
        List<ProductDto> productDtos = productMapper.toDtoList(products);
        return ResponseEntity.ok(productDtos);
    }

    /**
     * Devuelve los productos solicitados para comparación en formato JSON.
     * Recibe 2 o más IDs mediante query param: /product/compare?ids=uuid1&ids=uuid2&ids=uuid3
     * @param ids lista de IDs de productos a comparar (>=2)
     * @return lista de productos solicitados
     */
    @GetMapping("/compare")
    public ResponseEntity<List<ProductDto>> compareProducts(@RequestParam("ids") List<String> ids) {
        productValidationUtil.validateProductIdsForComparison(ids);
        
        List<Product> result = productUseCase.compareProducts(ids);
        
        productValidationUtil.validateComparisonResult(ids, result);
        
        List<ProductDto> productDtos = productMapper.toDtoList(result);
        return ResponseEntity.ok(productDtos);
    }

    /**
     * Guarda un nuevo producto
     * @param productDto producto a guardar
     * @return producto guardado
     */
    @PostMapping
    public ResponseEntity<ProductDto> saveProduct(@Valid @RequestBody ProductDto productDto) {
        // Convertir DTO a modelo de dominio
        Product product = productMapper.toModel(productDto);
        
        // Generar ID automáticamente si no se proporciona
        if (product.getId() == null || product.getId().trim().isEmpty()) {
            product = product.toBuilder().id(Product.generateId()).build();
        }
        
        // Guardar el producto usando el caso de uso
        Product savedProduct = productUseCase.addProduct(product);
        
        // Convertir el modelo de dominio de vuelta a DTO
        ProductDto savedProductDto = productMapper.toDto(savedProduct);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProductDto);
    }
}
