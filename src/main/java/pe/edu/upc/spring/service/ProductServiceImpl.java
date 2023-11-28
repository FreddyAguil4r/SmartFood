package pe.edu.upc.spring.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.edu.upc.spring.domain.model.Category;
import pe.edu.upc.spring.domain.model.Inventory;
import pe.edu.upc.spring.domain.model.Product;
import pe.edu.upc.spring.domain.repository.ProductRepository;
import pe.edu.upc.spring.domain.service.CategoryService;
import pe.edu.upc.spring.domain.service.InventoryService;
import pe.edu.upc.spring.domain.service.ProductService;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private InventoryService inventoryService;

    @Override
    public Product createProduct(Product product) {
        product.setDatePurchase(new Date());
        Product savedProduct = productRepository.save(product);

        // Actualizar totalValuesCategories en la categoría
        Category category = savedProduct.getCategory();
        if (category != null) {
            float currentTotal = category.getTotalValuesCategories();
            category.setTotalValuesCategories(currentTotal + savedProduct.getAmount());
            categoryService.updateCategory(category.getId(), category);
        }

        // Actualizar Inventory
        Inventory updatedInventory = inventoryService.updateInventory(savedProduct.getCategory().getInventory().getId(), savedProduct.getCategory().getInventory());

        return savedProduct;

    }

    @Override
    public Product getProductById(Integer productId) {

        return productRepository.findById(productId).orElseThrow();

    }

    @Override
    public Product updateProduct(Integer productId, Product productRequest) {

        Product product = productRepository.findById(productId).orElseThrow();
        product.setName(productRequest.getName());
        product.setDatePurchase(productRequest.getDatePurchase());
        product.setDueDate(productRequest.getDueDate());
        product.setUnitCost(productRequest.getUnitCost());
        product.setAmount(productRequest.getAmount());
        product.setWarehouseValue(productRequest.getWarehouseValue());

        return productRepository.save(product);

    }

    @Override
    public ResponseEntity<?> deleteProduct(Integer productId) {
        Product productToDelete = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        // Restar el valor del producto de la categoría
        Category category = productToDelete.getCategory();
        if (category != null) {
            float currentTotal = category.getTotalValuesCategories();
            category.setTotalValuesCategories(currentTotal - productToDelete.getAmount());
            categoryService.updateCategory(category.getId(), category);
        }

        // Actualizar Inventory
        inventoryService.updateInventory(productToDelete.getCategory().getInventory().getId(), productToDelete.getCategory().getInventory());

        productRepository.delete(productToDelete);

        return ResponseEntity.ok().build();
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
