package com.peru.smartfood.service;

import com.peru.smartfood.domain.model.Category;
import com.peru.smartfood.domain.model.Inventory;
import com.peru.smartfood.domain.model.Product;
import com.peru.smartfood.domain.repository.ProductRepository;
import com.peru.smartfood.domain.service.*;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

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
        return productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + productId));
    }
    @Override
    public Product updateProduct(Integer productId, Product productRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + productId));

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

