package com.myretail.api.async;

import com.myretail.model.Price;
import com.myretail.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AsyncServiceTest {

    @Mock
    AsyncService service;

    @Test
    public void testAsyncCapability() throws Exception {

        when( service.getPriceFromPricingAPI(anyLong()))
                .thenReturn(  CompletableFuture.completedFuture(new Price ( 101.23, "USD")));

        when( service.getTitleFromExternalAPI(anyLong()))
                .thenReturn( CompletableFuture.completedFuture("myProd123"));

        CompletableFuture<String> productName = service.getTitleFromExternalAPI((long)123);
        CompletableFuture<Price> price = service.getPriceFromPricingAPI((long)123);

        CompletableFuture.allOf(productName, price).join();

        Product product = new Product( (long)123, productName.get(), price.get());

        assertNotNull( product);
        assertEquals(product.getId().longValue(), 123);
        assertEquals(product.getCurrentPrice().getCurrencyCode(), price.get().getCurrencyCode());
        assertTrue( product.getCurrentPrice().getValue() == price.get().getValue() );
    }

}