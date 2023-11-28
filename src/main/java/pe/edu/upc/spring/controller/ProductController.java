package pe.edu.upc.spring.controller;



import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.spring.domain.model.Product;
import pe.edu.upc.spring.domain.service.ProductService;
import pe.edu.upc.spring.dto.ProductDto;
import pe.edu.upc.spring.dto.SaveProductDto;

import javax.validation.Valid;



@RestController
@AllArgsConstructor
@RequestMapping("/product")
@CrossOrigin(origins = "*")
public class ProductController {


    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper mapper;

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


    private Product convertToEntity(SaveProductDto resource) {
        return mapper.map(resource, Product.class);
    }
    private ProductDto convertToResource(Product entity)
    {
        return mapper.map(entity, ProductDto.class);
    }
    
}
