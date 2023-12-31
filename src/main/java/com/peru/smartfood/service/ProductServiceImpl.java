package com.peru.smartfood.service;



import com.peru.smartfood.domain.model.Category;
import com.peru.smartfood.domain.model.Inventory;
import com.peru.smartfood.domain.model.Product;
import com.peru.smartfood.domain.repository.ProductRepository;
import com.peru.smartfood.domain.service.CategoryService;
import com.peru.smartfood.domain.service.InventoryService;
import com.peru.smartfood.domain.service.ProductService;
import com.peru.smartfood.dto.CategoriesAndProductsDto;
import com.peru.smartfood.dto.ShortProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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

        //no se puede registrar un producto si no hay el category
        //validar que el category exista
        //si no existe, lanzar throw
        //si existe, crear el producto
        //si existe, actualizar el valor total de la categoría
        //si existe, actualizar el valor total del inventario
        //si existe, guardar el producto
        //si existe, retornar el producto
        //si no existe, lanzar throw
        Category category = categoryService.getCategoryById(product.getCategory().getId());
        if (category == null) {
            throw new NoSuchElementException("Category not found with ID: " + product.getCategory().getId());
        }

        // Actualizar el valor de la categoría
        float currentTotal = category.getTotalValuesCategories();
        float VALOR_A_SUMAR= product.getAmount()*product.getUnitCost();

        product.setWarehouseValue(VALOR_A_SUMAR);
        category.setTotalValuesCategories(currentTotal + VALOR_A_SUMAR);
        categoryService.updateCategory(category.getId(), category);

        // Actualizar el valor del inventario
        Inventory inventory = inventoryService.getLatestInventory();
        if (inventory == null) {
            // Si no hay inventarios registrados, crear uno nuevo
            inventory = new Inventory();
            inventory.setCurrentSystem(new Date());
            inventory.setTotalInventory(product.getAmount());
            inventoryService.createInventory(inventory);
        } else {
            inventory.setTotalInventory(inventory.getTotalInventory() + VALOR_A_SUMAR);
            inventoryService.updateInventory(inventory.getId(), inventory);
        }
        product.setCategory(category);
        product.setDatePurchase(new Date());
        return productRepository.save(product);

    }

    @Override
    public ResponseEntity<?> deleteProduct(Integer productId) {

        Product productToDelete = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        float VALOR_A_RESTAR= productToDelete.getAmount()*productToDelete.getUnitCost();

        // Restar el valor del producto de la categoría
        Category category = productToDelete.getCategory();
        if (category != null) {
            float currentTotal = category.getTotalValuesCategories();
            category.setTotalValuesCategories(currentTotal - VALOR_A_RESTAR);
            categoryService.updateCategory(category.getId(), category);
        }

        // registrar un nuevo inventory con el nuevo valor, no se actualiza, sino se registra uno nuevo
        Inventory inventory = inventoryService.getLatestInventory();
        if (inventory != null) {
            Inventory newInventory = new Inventory();
            newInventory.setCurrentSystem(new Date());
            newInventory.setTotalInventory(inventory.getTotalInventory() - VALOR_A_RESTAR);
            inventoryService.createInventory(newInventory);
        }

        productRepository.delete(productToDelete);

        return ResponseEntity.ok().build();
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
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAllProductsByCategory(Integer categoryId) {

        Category category = categoryService.getCategoryById(categoryId);
        if (category == null) {
            throw new NoSuchElementException("Category not found with ID: " + categoryId);
        }
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<CategoriesAndProductsDto> getAllCategoriesWithProducts() {
        List<Category> categories = categoryService.getAllCategories();
        return categories.stream()
                .map(category -> {
                    CategoriesAndProductsDto categoriesAndProductsDto = new CategoriesAndProductsDto();
                    categoriesAndProductsDto.setName(category.getName());
                    categoriesAndProductsDto.setTotalValuesCategories(category.getTotalValuesCategories());
                    categoriesAndProductsDto.setProducts(
                            productRepository.findByCategoryId(category.getId())
                                    .stream()
                                    .map(product -> {
                                        ShortProductDto shortProductDto = new ShortProductDto();
                                        shortProductDto.setName(product.getName());
                                        shortProductDto.setAmount(product.getAmount());
                                        shortProductDto.setUnitCost(product.getUnitCost());
                                        shortProductDto.setDatePurchase(product.getDatePurchase());
                                        shortProductDto.setDueDate(product.getDueDate());
                                        return shortProductDto;
                                    })
                                    .collect(Collectors.toList())
                    );
                    return categoriesAndProductsDto;
                })
                .collect(Collectors.toList());
    }

}

