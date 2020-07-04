package com.myretail.exception;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import java.security.Principal;
import java.util.*;
import static org.junit.Assert.*;

public class ProductApiExceptionHandlerTest {

    ProductApiExceptionHandler handler = new ProductApiExceptionHandler();

    @Test
    public void testHandleGenericException() throws Exception {

        ResponseProcessingException ex = new ResponseProcessingException("Error processing API response");
        ResponseEntity response = handler.handleGenericException(ex, getWebRequest()) ;

        assertEquals ( response.getStatusCode() , HttpStatus.INTERNAL_SERVER_ERROR );

        ApiError error = (ApiError)response.getBody();
        assertNotNull(error);
        assertEquals( error.getMessage(), "Error processing API response");
    }

    @Test
    public void testHandleProductNotFoundException() throws Exception {

        ProductNotFoundException pnfe = new ProductNotFoundException("404 Product not found");
        ResponseEntity response = handler.handleProductNotFoundException(pnfe, getWebRequest()) ;

        assertEquals ( response.getStatusCode() , HttpStatus.NOT_FOUND );

        ApiError error = (ApiError)response.getBody();
        assertNotNull(error);
        assertEquals( error.getMessage(), "404 Product not found");
    }

    @Test
    public void testHandleRestClientException() throws Exception {

        RestClientException rce = new RestClientException("Exception calling API");
        ResponseEntity response = handler.handleRestClientException(rce, getWebRequest());

        assertEquals(response.getStatusCode(), HttpStatus.FAILED_DEPENDENCY);

        ApiError error = (ApiError)response.getBody();
        assertEquals( error.getMessage(), "Exception calling API" );
    }


    private WebRequest getWebRequest () {

        return new WebRequest() {
            @Override
            public String getHeader(String s) {
                return "";
            }

            @Override
            public String[] getHeaderValues(String s) {
                return new String[]{"test"};
            }

            @Override
            public Iterator<String> getHeaderNames() {
                return null;
            }

            @Override
            public String getParameter(String s) {
                return null;
            }

            @Override
            public String[] getParameterValues(String s) {
                return new String[0];
            }

            @Override
            public Iterator<String> getParameterNames() {
                return null;
            }

            @Override
            public Map<String, String[]> getParameterMap() {
                return null;
            }

            @Override
            public Locale getLocale() {
                return null;
            }

            @Override
            public String getContextPath() {
                return "\\";
            }

            @Override
            public String getRemoteUser() {
                return "admin";
            }

            @Override
            public Principal getUserPrincipal() {
                return null;
            }

            @Override
            public boolean isUserInRole(String s) {
                return false;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public boolean checkNotModified(long l) {
                return false;
            }

            @Override
            public boolean checkNotModified(String s) {
                return false;
            }

            @Override
            public boolean checkNotModified(String s, long l) {
                return false;
            }

            @Override
            public String getDescription(boolean b) {
                return "test";
            }

            @Override
            public Object getAttribute(String s, int i) {
                return null;
            }

            @Override
            public void setAttribute(String s, Object o, int i) { }

            @Override
            public void removeAttribute(String s, int i) { }

            @Override
            public String[] getAttributeNames(int i) {
                return new String[0];
            }

            @Override
            public void registerDestructionCallback(String s, Runnable runnable, int i) { }

            @Override
            public Object resolveReference(String s) {
                return null;
            }

            @Override
            public String getSessionId() {
                return null;
            }

            @Override
            public Object getSessionMutex() {
                return null;
            }
        };
    }

}
