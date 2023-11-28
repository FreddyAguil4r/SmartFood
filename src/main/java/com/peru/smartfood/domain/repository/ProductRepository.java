package com.peru.smartfood.domain.repository;

import com.peru.smartfood.domain.model.Product;
import com.peru.smartfood.dto.SaveProductDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByCategoryId(Integer categoryId);

}
