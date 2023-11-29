package com.peru.smartfood.domain.service;


import com.peru.smartfood.domain.model.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {


    //create
    Category createCategory(Category category);
    //read

    Category getCategoryById(Integer categoryId);

    //update

    Category updateCategory(Integer categoryId, Category categoryRequest);

    //delete
    ResponseEntity<?> deleteCategory(Integer categoryId);

    //getAll
    List<Category> getAllCategories();

}
