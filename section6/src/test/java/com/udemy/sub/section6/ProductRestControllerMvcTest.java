package com.udemy.sub.section6;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import entities.Products;
import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import repos.ProductRepository;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Defining pure MVC tests (for unit testing).. using "MVC Controller - Mockito"
@RunWith(SpringRunner.class)
@WebMvcTest
public class ProductRestControllerMvcTest {

    // Creating final static variable names for setter functions of the "Product" instance
    private static final int PRODUCT_ID = 1;
    private static final String PRODUCT_NAME="Google Pixel 6";
    private static final String PRODUCT_DESCRIPTION="It's a good one..";
    private static final int PRODUCT_PRICE=45000;

    // Extracting Context File as well as REST url into a final static variable
    private static final String CONTEXT_PATH="/productapi";
    private static final String REST_PRODUCT_URL="/productapi/products/";


    // This will be used to make the 'RESTFull' calls..
    @Autowired
    private MockMvc mockMvc;

    // Using this Bean, it'll create a mock implementation of "ProductRepository" at run-time..
    // And it will get injected in "repository"..
    @MockBean
    private ProductRepository repository;

    // "getProducts" - .findAll() method..
    @Test
    public void testFindAll() throws JsonProcessingException {

        // Mocking the function so that the call does not actually go to the function but the mocked version of it in here.
        Products product = buildProducts();
//        extracted(product);  // using the extracted method - to set the values of the "Product"

        List<Products> products = Arrays.asList(product);; // function expects an "array" to be returned, so defining a generic one.
        when(repository.findAll()).thenReturn(products);

        // creating JSON file from scratch by passing the by hardcoding the value, same will happen for evey REST call
        // We can remove the need for that by using "Jackson API" - we do that thro "ObjectMapper"

        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

        // Running the Mock test to check the REST call (.perform() - method to make RESTFull calls)
        mockMvc.perform(get(REST_PRODUCT_URL).contextPath(CONTEXT_PATH).andExpect(status().isOk())
                .andExpect(content().json(objectWriter.writeValueAsString(products))));
                // Adding this "addExpect()" to match the exact value that gets returned from the test
                // "ObjectWriter" takes the array from defined final values and then writes 'em as JSON String values
    }

    // Creating a "Unit Test" for "POST" calls using "mockito"
    @Test
    public void testCreateProduct() throws Exception {
        Products products = buildProducts();
        when(repository.save(any())).thenReturn(products);
        // here this would mean whatever the "products" is being sent, return the same "products"
        // using "any()" 'coz' if we enter the same "products" here then the returning products from server-side will not be the same
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        // Running the test here - using the ".perform()" on "mocked" data array
        mockMvc.perform(post(REST_PRODUCT_URL).contextPath(CONTEXT_PATH).contentType(MediaType.APPLICATION_JSON)
                .content(objectWriter.writeValueAsString(products))).andExpect(status().isOk())
                .andExpect(content().json(objectWriter.writeValueAsString(products)));
                // this "addExpect()" - will match for actual content
    }

    // Creating Test case for the update "PUT" method
    @Test
    public void testUpdateProduct() throws Exception {
        Products products = buildProducts();
        products.setPrice(50000);  // setting different price to check if update method updates the price?
        when(repository.save(any())).thenReturn(products);
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        mockMvc.perform(put(REST_PRODUCT_URL).contextPath(CONTEXT_PATH).contentType(MediaType.APPLICATION_JSON)
                .content(objectWriter.writeValueAsString(products))).andExpect(status().isOk())
                .andExpect(content().json(objectWriter.writeValueAsString(products)));
    }

    // Creating unit test case for "Delete Method"
    @Test
    public void deleteProduct() throws Exception{
        // mocking "delete" method using "Mockito" since we do not expect anything from this we can use "doNothing()"
        doNothing().when(repository).deleteById(PRODUCT_ID);
        mockMvc.perform(delete(REST_PRODUCT_URL+PRODUCT_ID).contextPath(CONTEXT_PATH).andExpect(status().isOk()));
    }

    // we will need to define the "Product" and set their values everytime we are testing any of the RESTFull service.
    // So extracting these into a different method altogether, so that we can just call it in every other test calls directly
    // 'EXTRACTED method' - to set the values of the "Product" instance in every test REST call.
    private Products buildProducts() {
        Products products = new Products();
        products.setId(PRODUCT_ID);
        products.setName(PRODUCT_NAME);
        products.setDescription(PRODUCT_DESCRIPTION);
        products.setPrice(PRODUCT_PRICE);
        return products;
    }
}

