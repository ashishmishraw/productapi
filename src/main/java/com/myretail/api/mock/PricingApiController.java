package com.myretail.api.mock;

import com.myretail.exception.ApiError;
import com.myretail.model.Price;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(value = "pricing", description = "MyRetail dynamic pricing API")
@RestController
public class PricingApiController {

    private final HttpServletRequest request;

    @Autowired
    public PricingApiController( HttpServletRequest request) {
        this.request = request;
    }

    private static final Logger log = LoggerFactory.getLogger(PricingApiController.class);

    @ApiOperation(
            value = "myRetails pricing API: Returns price details for a particular product",
            nickname = "getPriceForProductId", notes = "", response = Price.class, tags={ "pricing",
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Price about given product by ID", response = Price.class),
            @ApiResponse(code = 200, message = "Unexpected error", response = ApiError.class)
    })
    @RequestMapping(value = "/product/{id}/price", produces = { "application/json" }, method = RequestMethod.GET)
    ResponseEntity<Price> getPriceByProductId (
            @ApiParam(value = "ID of the product", required=true) @PathVariable("id") Long id) {

        log.info("Calling the mock pricing API ... ");
        try {
            Thread.currentThread().sleep(3 ); //300 milliseconds time lag induced for mocking
        } catch (InterruptedException e) {
            log.error("Interrupted by other process before 300 milliseconds ! " ) ;

        }

        Price price = new Price ( 101.23, "USD");
        return new ResponseEntity<Price>( price, HttpStatus.OK );
    }

}