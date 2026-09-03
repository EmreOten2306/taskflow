package tech.ekya.taskflow.exception;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException ex,
                                                                    HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiError(
                        Instant.now(),
                        404,
                        "Not Found",
                        ex.getMessage(),
                        request.getRequestURI(),
                        List.of()
                )
        );
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<ApiError> handleUnprocessableEntityException(UnprocessableEntityException ex,
                                                                       HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                new ApiError(
                        Instant.now(),
                        422,
                        "Unprocessable Content",
                        ex.getMessage(),
                        request.getRequestURI(),
                        List.of()
                )
        );
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiError> handleUnauthorizedException(UnauthorizedException ex,
                                                                HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ApiError(
                        Instant.now(),
                        401,
                        "Unauthorized",
                        ex.getMessage(),
                        request.getRequestURI(),
                        List.of()
                )
        );
    }


    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiError> DuplicateResourceException(DuplicateResourceException ex,
                                                               HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ApiError(
                        Instant.now(),
                        409,
                        "Conflict",
                        ex.getMessage(),
                        request.getRequestURI(),
                        List.of()

                )
        );
    }

    @ExceptionHandler(BusinessRuleViolationException.class)
    public ResponseEntity<ApiError> BusinessRuleViolationException(BusinessRuleViolationException ex,
                                                                   HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                new ApiError(
                        Instant.now(),
                        422,
                        "Unprocessable Entity",
                        ex.getMessage(),
                        request.getRequestURI(),
                        List.of()
                )
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<FieldErrorDetail> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new FieldErrorDetail(
                        error.getField(),
                        error.getDefaultMessage()
                ))
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiError(
                        Instant.now(),
                        400,
                        "Bad Request",
                        "Validation failed",
                        request.getRequestURI(),
                        fieldErrors
                )
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ApiError(
                        Instant.now(),
                        409,
                        "Conflict",
                        ex.getMessage(),
                        request.getRequestURI(),
                        List.of()
                )
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(
            Exception ex,
            HttpServletRequest request) {
        log.error("Unexpected error occurred", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(

                new ApiError(
                        Instant.now(),
                        500,
                        "Internal Server Error",
                        ex.getMessage(),
                        request.getRequestURI(),
                        List.of()
                )
        );
    }

}


