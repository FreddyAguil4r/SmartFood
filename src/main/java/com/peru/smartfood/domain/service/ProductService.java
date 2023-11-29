package com.peru.smartfood.domain.service;


import com.peru.smartfood.domain.model.Product;
import com.peru.smartfood.dto.CategoriesAndProductsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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


    //getAllProductsByCategory
    List<Product> getAllProductsByCategory(Integer categoryId);

    //getAllCategoriesWithProducts
    List<CategoriesAndProductsDto> getAllCategoriesWithProducts();


}
