package com.myretail.api.product;

import com.myretail.api.async.AsyncService;
import com.myretail.model.Price;
import com.myretail.model.Product;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class ProductsApiController implements ProductsApi {

    private static final Logger log = LoggerFactory.getLogger(ProductsApiController.class);
    private final HttpServletRequest request;

    @Value("${redsky.pdp.api.host}")
    private String redSkyApiHost;

    @Value("${redsky.pdp.api.path}")
    private String redSkyApiPath;

    @Value("${pricing.api.mock}")
    private Boolean mockPricingApi;

    @Value("${redsky.pdp.api.excludes}")
    private String redSkyApiExcludesValue;

    @Value("${pricing.api.host}")
    private String pricingApiHost;

    @Value("${pricing.api.path}")
    private String pricingApiPath;

    @Autowired
    public ProductsApiController( HttpServletRequest request) {
        this.request = request;
    }

    @Autowired
    AsyncService asyncAPICall;

    public ResponseEntity<Product> findProductById (
            @ApiParam(value="Product identifier", required=true) @PathVariable("id") @NotBlank @Positive Long id) throws ExecutionException, InterruptedException {

        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {

            CompletableFuture<String> title = asyncAPICall.getTitleFromExternalAPI(id);
            CompletableFuture<Price> price = asyncAPICall.getPriceFromPricingAPI(id);

            CompletableFuture.allOf(title, price).join();

            Product product = new Product( id, title.get(), price.get());
            return new ResponseEntity<Product>(product, HttpStatus.OK);
        }
        return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
    }

}