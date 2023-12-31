package com.peru.smartfood.controller;


import com.peru.smartfood.domain.model.Category;
import com.peru.smartfood.domain.model.Product;
import com.peru.smartfood.domain.model.Supplier;
import com.peru.smartfood.domain.service.CategoryService;
import com.peru.smartfood.domain.service.ProductService;
import com.peru.smartfood.domain.service.SupplierService;
import com.peru.smartfood.dto.CategoriesAndProductsDto;
import com.peru.smartfood.dto.ProductDto;
import com.peru.smartfood.dto.SaveProductDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;



@RestController
@AllArgsConstructor
@RequestMapping("/product")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private final ModelMapper mapper;

    @PostMapping
    public ProductDto createProduct(@Valid @RequestBody SaveProductDto resource) {
        Product product = convertToEntity(resource);
        return convertToResource(productService.createProduct(product));
    }

    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable Integer productId) {
        return productService.getProductById(productId);
    }

    @PutMapping("/{productId}")
    public Product updateProduct(@PathVariable Integer productId, @RequestBody Product productRequest) {
        return productService.updateProduct(productId, productRequest);
    }


    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {
        return productService.deleteProduct(productId);
    }

    @GetMapping("/all")
    public Iterable<Product> getAllProducts() {
        return productService.getAllProducts();
    }


    //getAllProductsByCategory
    @GetMapping("/category/{categoryId}")
    public Iterable<Product> getAllProductsByCategory(@PathVariable Integer categoryId) {
        return productService.getAllProductsByCategory(categoryId);
    }


    //getAllCategoriesWithProducts
    @GetMapping("/categories")
    public Iterable<CategoriesAndProductsDto> getAllCategoriesWithProducts() {
        return productService.getAllCategoriesWithProducts();
    }




    private Product convertToEntity(SaveProductDto dto) {
        Product product = new Product();

        product.setName(dto.getName());
        product.setDueDate(dto.getDueDate());
        product.setUnitCost(dto.getUnitCost());
        product.setAmount(dto.getAmount());

        Category category = categoryService.getCategoryById(dto.getCategoryId());
        product.setCategory(category);

        Supplier supplier = supplierService.getSupplierById(dto.getSupplierId());
        product.setSupplier(supplier);

        return product;
    }

    private ProductDto convertToResource(Product entity)
    {
        return mapper.map(entity, ProductDto.class);
    }

    
}
