package co.com.ml.json;

import co.com.ml.model.product.Product;
import co.com.ml.model.product.gateways.ProductRepository;
import co.com.ml.model.exceptions.ProductRepositoryException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JsonRepositoryAdapter implements ProductRepository {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File file = new File("products.json");

    @Override
    public Product addProduct(Product product) {
        try {
            List<Product> products = loadProducts();
            products.add(product);
            saveProducts(products);
            return product;
        } catch (IOException e) {
            throw new ProductRepositoryException("Error al guardar el producto", e);
        }
    }

    @Override
    public List<Product> listAllProducts() {
        try {
            return loadProducts();
        } catch (IOException e) {
            throw new ProductRepositoryException("Error al listar los productos", e);
        }
    }

    @Override
    public List<Product> compareProducts(List<String> productIds) {

        try {
            List<Product> all = loadProducts();
            return all.stream().filter(p -> productIds.contains(p.getId()))
                    .toList();
        } catch (IOException e) {
            throw new ProductRepositoryException("Error al cargar productos para comparación", e);
        }
    }

    private List<Product> loadProducts() throws IOException {
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }
        return objectMapper.readValue(file, new TypeReference<>() {
        });
    }

    private void saveProducts(List<Product> products) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, products);
    }

}
