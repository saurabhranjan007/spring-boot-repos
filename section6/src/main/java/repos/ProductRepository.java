package repos;

import entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products, Integer> {

//    using this "Integer" - 'coz the type of Product ID is "int" and this is a JAVA lan syntax.

}
