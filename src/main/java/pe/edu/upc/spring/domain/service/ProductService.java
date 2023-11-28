package pe.edu.upc.spring.domain.service;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.edu.upc.spring.domain.model.Product;

import java.util.List;

@Service
public interface ProductService {

    Product createProduct(Product product);
    //read

    Product getProductById(Integer productId);

    //update

    Product updateProduct(Integer productId, Product productRequest);

    //delete
    ResponseEntity<?> deleteProduct(Integer productId);

    List<Product> getAllProducts();


}
