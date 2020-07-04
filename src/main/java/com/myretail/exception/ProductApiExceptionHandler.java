package com.myretail.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.concurrent.CompletionException;

@ControllerAdvice
@RestController
public class ProductApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler( {Exception.class, ResponseProcessingException.class})
    public final ResponseEntity<Object> handleGenericException (Exception ex, WebRequest wreq) {

        ApiError error = new ApiError(wreq.getDescription(false) ,  ex.getMessage());

        if ( ex instanceof CompletionException &&  ex.getMessage().contains("com.myretail.exception.ProductNotFoundException") ) {
            return new ResponseEntity(error, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler( ProductNotFoundException.class)
    public final ResponseEntity<Object> handleProductNotFoundException (Exception ex, WebRequest wreq) {

        ApiError error = new ApiError(wreq.getDescription(false) ,  ex.getMessage());
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler (RestClientException.class)
    public final ResponseEntity<Object> handleRestClientException ( Exception ex, WebRequest wreq) {

        ApiError error = new ApiError(wreq.getDescription(false) ,  ex.getMessage());
        return new ResponseEntity(error, HttpStatus.FAILED_DEPENDENCY);
    }

    @Override
    public final ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                     HttpHeaders headers, HttpStatus status, WebRequest wreq) {
        ApiError error = new ApiError( wreq.getDescription(false), ex.getMessage());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

}