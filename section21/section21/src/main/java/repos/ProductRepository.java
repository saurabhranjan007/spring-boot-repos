package repos;

import entities.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


// "Product" - is the entity and "String" - is the type of ID here
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

}
