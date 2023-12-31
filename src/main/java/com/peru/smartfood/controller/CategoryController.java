package com.peru.smartfood.controller;

import com.peru.smartfood.domain.model.Category;
import com.peru.smartfood.domain.service.CategoryService;
import com.peru.smartfood.dto.CategoryDto;
import com.peru.smartfood.dto.SaveCategoryDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@AllArgsConstructor
@RequestMapping("/category")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private final ModelMapper mapper;

    @PostMapping
    public CategoryDto createCategory(@Valid @RequestBody SaveCategoryDto resource) {
        Category category = convertToEntity(resource);
        return convertToResource(categoryService.createCategory(category));
    }

    @GetMapping("/{categoryId}")
    public Category getCategoryById(@PathVariable Integer categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @PutMapping("/{categoryId}")
    public Category updateCategory(@PathVariable Integer categoryId, @RequestBody Category categoryRequest) {
        return categoryService.updateCategory(categoryId, categoryRequest);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer categoryId) {
        return categoryService.deleteCategory(categoryId);
    }

    @GetMapping("/all")
    public Iterable<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    private Category convertToEntity(SaveCategoryDto resource) {
        return mapper.map(resource, Category.class);
    }
    private CategoryDto convertToResource(Category entity)
    {
        return mapper.map(entity, CategoryDto.class);
    }
}
