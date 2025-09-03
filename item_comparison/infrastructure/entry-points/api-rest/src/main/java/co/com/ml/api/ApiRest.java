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
import lombok.RequiredArgsConstructor;

/**
 * API Rest controller para productos.
 */
@RestController
@ResponseBody
@RequestMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ApiRest {

    private final ProductUseCase productUseCase;


    /**
     * Obtiene todos los productos
     * @return lista de productos
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productUseCase.listAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * Devuelve los productos solicitados para comparación en formato JSON.
     * Recibe 2 o más IDs mediante query param: /product/compare?ids=1&ids=2&ids=3
     * @param ids lista de IDs de productos a comparar (>=2)
     * @return lista de productos solicitados
     */
    @GetMapping("/compare")
    public ResponseEntity<List<Product>> compareProducts(@RequestParam("ids") List<Long> ids) {
        List<Product> result = productUseCase.compareProducts(ids);
        return ResponseEntity.ok(result);
    }

    /**
     * Guarda un nuevo producto
     * @param product producto a guardar
     * @return producto guardado
     */
    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        Product savedProduct = productUseCase.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }
}
