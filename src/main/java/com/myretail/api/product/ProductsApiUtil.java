package com.myretail.api.product;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class ProductsApiUtil {

    public static URI getURI (Long id, String host, String initialPath ) {

        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(host)
                .path(initialPath)
                .path("/{id}").buildAndExpand(id).toUri();
    }


    public static HttpEntity<String> getRequestEntity() {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        return new HttpEntity<String>(null, headers);
    }
}
