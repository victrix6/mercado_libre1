package co.com.ml.api;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;
import co.com.ml.model.exceptions.ProductValidationException;
import co.com.ml.model.exceptions.ProductNotFoundException;
import co.com.ml.model.exceptions.ProductComparisonException;
import co.com.ml.model.exceptions.ProductRepositoryException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
       private static final String ERROR = "error";
    private static final String MESSAGE = "message";
    private static final String TYPE = "type";
    private static final String DETAILS = "details";
    
    // Error types
    private static final String PRODUCT_VALIDATION_ERROR = "PRODUCT_VALIDATION_ERROR";
    private static final String PRODUCT_NOT_FOUND = "PRODUCT_NOT_FOUND";
    private static final String PRODUCT_COMPARISON_ERROR = "PRODUCT_COMPARISON_ERROR";
    private static final String PRODUCT_REPOSITORY_ERROR = "PRODUCT_REPOSITORY_ERROR";
    private static final String ILLEGAL_ARGUMENT = "ILLEGAL_ARGUMENT";
    private static final String VALIDATION_ERROR = "VALIDATION_ERROR";
    private static final String CONSTRAINT_VIOLATION_ERROR = "CONSTRAINT_VIOLATION_ERROR";
    @ExceptionHandler(ProductValidationException.class)
    public ResponseEntity<Map<String, Object>> handleProductValidationException(ProductValidationException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put(ERROR, "Error de validación de producto");
        body.put(MESSAGE, ex.getMessage());
        body.put(TYPE, PRODUCT_VALIDATION_ERROR);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleProductNotFoundException(ProductNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put(ERROR, "Producto no encontrado");
        body.put(MESSAGE, ex.getMessage());
        body.put(TYPE, PRODUCT_NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(ProductComparisonException.class)
    public ResponseEntity<Map<String, Object>> handleProductComparisonException(ProductComparisonException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put(ERROR, "Error en comparación de productos");
        body.put(MESSAGE, ex.getMessage());
        body.put(TYPE, PRODUCT_COMPARISON_ERROR);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(ProductRepositoryException.class)
    public ResponseEntity<Map<String, Object>> handleProductRepositoryException(ProductRepositoryException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put(ERROR, "Error en el repositorio de productos");
        body.put(MESSAGE, ex.getMessage());
        body.put(TYPE, PRODUCT_REPOSITORY_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(IllegalArgumentException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put(ERROR, "Bad Request");
        body.put(MESSAGE, ex.getMessage());
        body.put(TYPE, ILLEGAL_ARGUMENT);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();
        
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );
        
        response.put(ERROR, "Error de validación");
        response.put(DETAILS, errors);
        response.put(TYPE, VALIDATION_ERROR);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();
        
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            errors.put(fieldName, violation.getMessage());
        });
        
        response.put(ERROR, "Error de validación");
        response.put(DETAILS, errors);
        response.put(TYPE, CONSTRAINT_VIOLATION_ERROR);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}


