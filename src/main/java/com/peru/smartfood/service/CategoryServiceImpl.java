package com.peru.smartfood.service;

import com.peru.smartfood.domain.model.Category;
import com.peru.smartfood.domain.repository.CategoryRepository;
import com.peru.smartfood.domain.service.CategoryService;
import com.peru.smartfood.domain.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private InventoryService inventoryService;

    @Override
    public Category createCategory(Category category) {

        category.setTotalValuesCategories(0);

        Category savedCategory = categoryRepository.save(category);

        // Actualizar Inventory
        inventoryService.updateInventory(savedCategory.getInventory().getId(),savedCategory.getInventory());

        return savedCategory;
    }

    @Override
    public Category getCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category not found with ID: " + categoryId));
    }

    @Override
    public Category updateCategory(Integer categoryId, Category categoryRequest) {
        return categoryRepository.findById(categoryId).map(category -> {
            category.setName(categoryRequest.getName());
            category.setTotalValuesCategories(categoryRequest.getTotalValuesCategories());
            return categoryRepository.save(category);
        }).orElseThrow(() -> new NoSuchElementException("Category not found with ID: " + categoryId));
    }
    @Override
    public ResponseEntity<?> deleteCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category not found with ID: " + categoryId));

        categoryRepository.delete(category);
        return ResponseEntity.ok().build();
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();

    }
}

