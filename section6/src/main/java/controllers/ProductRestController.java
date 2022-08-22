package controllers;

import entities.Products;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import repos.ProductRepository;

import javax.validation.Valid;
import java.util.List;

// To make this class a Rest Controller, we need to mark it with @RestController
@RestController
public class ProductRestController {

    @Autowired
    ProductRepository repository;  // Injecting the product repository..

//    creating a Logger, we can use Logger instance to Log whatever is happening in our application.
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductRestController.class);

//    Mapping it to "/products/" path with a "GET" method request.
    @RequestMapping(value = "/products/", method = RequestMethod.GET)
    public List<Products> getProducts() {
        return repository.findAll();
    }

//    Method to get single product by the entered dynamic "id". Also binding the path variable here, so that Spring will know how to process 'em.
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    @Transactional(readOnly = true)  // need to use this everytime there is caching happening, "readOnly" specifies the type of method on which we're caching
    @Cacheable("product-cache")
//    cache name is given 'coz it tells that cache name is the one into which the objects by this method will go (here "product-cache")
    public Products getProduct(@PathVariable("id") int id) {
        LOGGER.info("Finding the Product bt ID "+id);  // logging this to know what's happening..
        return repository.findById(id).get();
    }

//    New product entry - HTTPS - POST protocol.
//    Binding the incoming request to the product using @RequestBody.
//    Here enabling SParing Validation (@Valid) to see the REST call that is being made is valid or not
    @RequestMapping(value = "/products/", method = RequestMethod.POST)
    public Products createProducts(@Valid @RequestBody Products product) {
        return repository.save(product);
    }

//    Updating an existing product entry - HTTPS - PUT protocol.
//    Binding the incoming request to the product using @RequestBody.
    @RequestMapping(value = "/products/", method = RequestMethod.PUT)
    public Products updateProduct(@Valid @RequestBody Products product) {
        return repository.save(product);
    }

//    deleting a product entry - HTTPS - DELETE protocol.
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
//    on the delete method, instead of caching we need to evict the cache. Since we're deleting the entry here.
    @CacheEvict("product-cache")  // we use this 'coz when the deletion of an entry happens this will basically erase the cache entry as well.
    public void deleteProduct(@PathVariable("id") int id) {
        repository.deleteById(id);
    }
}
