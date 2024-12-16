package com.expens.manager.expection;


import com.expens.manager.io.ErrorObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

   @ResponseStatus(HttpStatus.NOT_FOUND)
   @ExceptionHandler(ResourceNotFoundException.class)
   public ErrorObject handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request){
      log.info("Throwing ResourceNotFoundException exception  from GlobalExceptionHandler: {}", exception.getMessage());
      return ErrorObject.builder()
              .errorCode( "DATA_NOT_FOUND" )
              .statusCode( HttpStatus.NOT_FOUND.value() )
              .message( exception.getMessage() )
              .timestamp( new Date() )
              .build();
   }
}
