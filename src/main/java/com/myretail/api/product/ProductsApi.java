package com.myretail.api.product;

import com.myretail.exception.ApiError;
import com.myretail.model.Product;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.concurrent.ExecutionException;

@Api(value = "products", description = "Myretail products API")
public interface ProductsApi {

    @ApiOperation(
            value = "myRetails products API: Returns details about a particular product",
            nickname = "findProductById", notes = "", response = Product.class, tags={ "product",
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Details about the product by ID", response = Product.class),
            @ApiResponse(code = 404, message = "Product not found", response = ApiError.class)
    })
    @RequestMapping(value = "/products/{id}", produces = { "application/json" }, method = RequestMethod.GET)
    ResponseEntity<Product> findProductById(
            @ApiParam(value = "ID of the product", required=true)  @PathVariable("id") @NotBlank @Positive Long id)
            throws ExecutionException, InterruptedException;
}
