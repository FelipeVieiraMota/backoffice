package com.motafelipe.api.backoffice.controller.exception;


import com.motafelipe.api.backoffice.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  ResourceExceptionHandler handles runtime exceptions in our application and threat the shape of the final user's
 *  message.
 */
@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Created to return the current data and hour
     * @return LocalDateTime
     */
    private Date getFormattedDateTime(){
        return new Date();
    }

    /**
     * Created to handle NotFoundException ( HTTP status code 404 ) throws.
     * @param ex - Expected NotFoundException exception
     * @return ResponseEntity<ApiError>
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException ex){

        ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), getFormattedDateTime());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Created to handle HttpServerErrorException ( HTTP status code 500 ) throws.
     * @param ex - Expected HttpServerErrorException exception
     * @return ResponseEntity<ApiError>
     */
    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<ApiError> handleHttpServerErrorException(HttpServerErrorException ex){

        var statusCode = HttpStatus.valueOf(ex.getRawStatusCode());
        var message  = ex.getStatusText();

        ApiError error = new ApiError(statusCode.value(), message, getFormattedDateTime());

        return ResponseEntity.status(statusCode).body(error);
    }

    /**
     *
     * @param ex
     * @return ResponseEntity<ApiError>
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex){
        ApiError error = new ApiError(HttpStatus.FORBIDDEN.value(), ex.getMessage(), getFormattedDateTime());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    /**
     * handleBadCredentialsException
     * @param ex - ex
     * @return ResponseEntity<ApiError>
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentialsException(BadCredentialsException ex){
        ApiError error = new ApiError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), getFormattedDateTime());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    /**
     * handleNullPointerException
     * @param ex - ex
     * @return ResponseEntity<ApiError>
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiError> handleNullPointerException(NullPointerException ex){
        ApiError error = new ApiError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), getFormattedDateTime());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }


    /**
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return ResponseEntity<Object>
     */
    @Override
    protected ResponseEntity<Object>
        handleMethodArgumentNotValid(
          MethodArgumentNotValidException ex,
          HttpHeaders headers,
          HttpStatus status,
          WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error ->{ errors.add(error.getDefaultMessage()); });
        String defaultMessage = "Invalid fields";
        ApiErrorList error = new ApiErrorList(HttpStatus.BAD_REQUEST.value(),defaultMessage , getFormattedDateTime(), errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
