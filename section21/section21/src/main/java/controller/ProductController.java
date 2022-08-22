package controller;


import entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import repos.ProductRepository;

@RestController
@RequestMapping("/products")
public class ProductController {

    // Note - won't be using any url in "PostMapping" and "GetMapping"
    //        'coz we're already using "RequestMapping" so it will map on the same
    //        url and will differentiate based on "REST" calls - POST/GET

    @Autowired
    private ProductRepository repo;

    // will return a "Mono" Product back - using this to return single product
    @PostMapping  // will use POST to create products
    public Mono<Product> addProduct(@RequestBody Product product) {
        return repo.save(product);
    }

    // will return "Flux" - using this to get all the products
    public Flux<Product> getProducts() {
        return repo.findAll();
    }

}
