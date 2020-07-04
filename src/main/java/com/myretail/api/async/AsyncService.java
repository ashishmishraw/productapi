package com.myretail.api.async;

import com.myretail.api.product.ProductsApiUtil;
import com.myretail.exception.ProductNotFoundException;
import com.myretail.exception.ResponseProcessingException;
import com.myretail.model.Price;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toMap;

@Service
public class AsyncService {

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
    private RestTemplate restTemplate;

    private static final Logger log = LoggerFactory.getLogger(AsyncService.class);

    @Autowired
    public AsyncService( ) { }

    @Async("asyncRunner")
    public CompletableFuture<Price> getPriceFromPricingAPI(Long id)  {

        HttpEntity<String> request = ProductsApiUtil.getRequestEntity();
        ResponseEntity<Price> productPrice = null;

        if (!mockPricingApi) {
            URI extUri = ProductsApiUtil.getURI(id, pricingApiHost, pricingApiPath);
            extUri = UriComponentsBuilder.fromUri(extUri)
                    .path("/price").build().toUri();

            productPrice = restTemplate.exchange(extUri, HttpMethod.GET, request, Price.class);
        } else {
            String url = "http://localhost:3000/product/13860428/price";
            productPrice = restTemplate.exchange(url, HttpMethod.GET, request, Price.class);
        }

        return CompletableFuture.completedFuture(productPrice.getBody());
    }

    @Async("asyncRunner")
    public CompletableFuture<String> getTitleFromExternalAPI (
            @PathVariable("id") @ApiParam(value = "ID of the product", required = true) Long id) {

        HttpEntity<String> request = ProductsApiUtil.getRequestEntity();
        URI extUri = ProductsApiUtil.getURI(id, redSkyApiHost, redSkyApiPath);
        extUri = UriComponentsBuilder.fromUri(extUri)
                .queryParam("excludes", redSkyApiExcludesValue).build().toUri();
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(extUri, HttpMethod.GET, request, String.class);
        } catch ( HttpClientErrorException ex) {
            if ( null == response ) {
                response = new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            if ( response.getStatusCode()== HttpStatus.NOT_FOUND) {
                throw new ProductNotFoundException("Requested Product Not Found");
            }
        }

        String productName = " ";
        if (  response.getStatusCode() == HttpStatus.OK) {
            productName = getProductNameFromResponse(response);
        }
        return CompletableFuture.completedFuture(productName);
    }


    private String getProductNameFromResponse(ResponseEntity<String> response) {
        String productName;
        try {
            Map<String, Object> responseMap = JsonParserFactory.getJsonParser().parseMap(response.getBody());
            Map<String, Object> prod = (Map<String, Object>) responseMap.get("product");
            Map<String, Object> item = (Map<String, Object>) prod.get("item");
            Map<String, Object> proDesc = (Map<String, Object>) item.get("product_description");
            Map<Object, Object> title =
                    proDesc.entrySet().stream()
                            .filter(e -> e.getKey().equals("title"))
                            .collect(toMap(p -> p.getKey(), p -> p.getValue()));

            productName = title.get("title").toString();
        } catch (Exception e) {
            log.error("Exception while parsing and handling JSON response from external product api ", e);
            throw new ResponseProcessingException( e.getMessage() );
        }
        return productName;
    }

}