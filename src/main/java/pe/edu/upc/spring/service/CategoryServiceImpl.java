package pe.edu.upc.spring.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.edu.upc.spring.domain.model.Category;
import pe.edu.upc.spring.domain.repository.CategoryRepository;
import pe.edu.upc.spring.domain.service.CategoryService;
import pe.edu.upc.spring.domain.service.InventoryService;

import java.util.List;

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
        return categoryRepository.findById(categoryId).orElseThrow();
    }

    @Override
    public Category updateCategory(Integer categoryId, Category categoryRequest) {

        return categoryRepository.findById(categoryId).map(category -> {
            category.setName(categoryRequest.getName());
            category.setTotalValuesCategories(categoryRequest.getTotalValuesCategories());
            return categoryRepository.save(category);
        }).orElseThrow();

    }

    @Override
    public ResponseEntity<?> deleteCategory(Integer categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow();

        categoryRepository.delete(category);
        return ResponseEntity.ok().build();
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();

    }
}
