package pe.edu.upc.spring.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.spring.domain.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
