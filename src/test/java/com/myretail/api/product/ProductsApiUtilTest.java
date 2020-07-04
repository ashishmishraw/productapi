package com.myretail.api.product;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import java.net.URI;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProductsApiUtilTest {

    @Test
    public void testGetUri() throws Exception {

        URI uri = ProductsApiUtil.getURI((long) 1234567, "myRetail.com", "product");

        assertNotNull(uri);
        assertEquals( uri.getScheme(), "https");
        assertEquals(uri.getHost(), "myRetail.com");
        assertEquals(uri.getPath(), "/product/1234567");
        assertEquals(uri.toURL().toString(), "https://myRetail.com/product/1234567");
    }

    @Test
    public void testGetRequestEntity() throws Exception {

        HttpEntity<String> entity = ProductsApiUtil.getRequestEntity();
        assertNotNull(entity);

        List<MediaType> acceptedContent = entity.getHeaders().getAccept();
        assertEquals(acceptedContent.get(0), MediaType.APPLICATION_JSON);
    }
}
