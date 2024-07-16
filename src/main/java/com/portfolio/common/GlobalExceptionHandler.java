package com.portfolio.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	
	
    @Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    	
        HandlerMethod handlerMethod = (HandlerMethod) request.getAttribute("org.springframework.web.servlet.HandlerMapping.bestMatchingHandler", WebRequest.SCOPE_REQUEST);
        String controllerName = handlerMethod != null ? handlerMethod.getBeanType().getSimpleName() : "Unknown";
        String methodName = handlerMethod != null ? handlerMethod.getMethod().getName() : "Unknown";
        String objectName = ex.getBindingResult().getObjectName();
        
        log.error("controller : "+controllerName+", methodName : "+methodName+", Error in object : "+objectName);
        ex.getBindingResult().getFieldErrors().forEach(error ->{
        	log.error(error.getDefaultMessage());
            }
        	);
        
        return new ResponseEntity<>("bad_request", HttpStatus.BAD_REQUEST);
	}



}
