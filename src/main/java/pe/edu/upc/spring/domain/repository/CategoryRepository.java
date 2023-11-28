package pe.edu.upc.spring.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.spring.domain.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
