package com.myretail.api.product;

import com.myretail.api.async.AsyncService;
import com.myretail.model.Price;
import com.myretail.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebClient
public class ProductsApiControllerTest {

    @Autowired ProductsApiController productsApiController;
    @Autowired RestTemplate restTemplate;
    @Autowired AsyncService asyncService;


    @Test
    public void getProductByIdTest() throws Exception {

        Product product = new Product(
                (long) 13860429,
                "SpongeBob SquarePants: SpongeBob's Frozen Face-off",
                new Price(101.23, "USD")
        );
        try {
            ResponseEntity<Product> response =
                    restTemplate.exchange(
                            "http://localhost:3000/products/13860429",
                            HttpMethod.GET,
                            getRequest(),
                            Product.class
                    );
        } catch (Exception ignore) {
            System.out.printf("Ignoring exception " + ignore.getMessage());
        }
        //assertTrue(  response.getStatusCode().equals(HttpStatus.OK));
        //assertEquals(response.getBody().getName(), product.getName());
    }



    @Test(expected= HttpClientErrorException.class)
    public void getProduct404Test() throws Exception {

        ResponseEntity<Product> response =
                restTemplate.exchange(
                        "http://localhost:3000/products/13860430",
                        HttpMethod.GET,
                        getRequest(),
                        Product.class
                );

        assertTrue( response.getStatusCode().equals(HttpStatus.NOT_FOUND) );
    }


    @Test( expected=HttpClientErrorException.class)
    public void getProductByIdBadRequest() throws Exception {

        ResponseEntity<Product> response =
                restTemplate.exchange(
                        "http://localhost:3000/products/13860430",
                        HttpMethod.GET,
                        getBadRequest(),
                        Product.class
                );

        assertTrue( response.getStatusCode().equals(HttpStatus.NOT_ACCEPTABLE));
    }


    private HttpEntity<String> getRequest ( ) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        return new HttpEntity<String>(null, headers);
    }

    private HttpEntity<String> getBadRequest ( ) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/xml");
        return new HttpEntity<String>(null, headers);
    }


}
