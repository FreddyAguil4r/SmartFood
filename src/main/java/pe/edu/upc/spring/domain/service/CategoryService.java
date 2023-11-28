package pe.edu.upc.spring.domain.service;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.edu.upc.spring.domain.model.Category;

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
