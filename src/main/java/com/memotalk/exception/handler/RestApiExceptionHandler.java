package com.memotalk.exception.handler;

import com.memotalk.exception.BadRequestException;
import com.memotalk.exception.InternalServerException;
import com.memotalk.exception.NoAuthException;
import com.memotalk.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class RestApiExceptionHandler {

    private static final String FIELD_ERROR_FORMAT = "[%s] : %s, input value : [%s]";

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        // requestId 는 에러 로그를 식별하기 위해 사용됩니다.
        String requestId = UUID.randomUUID().toString();
        log.info("Bad Request Error, requestId={}, message={}", requestId, ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), requestId);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {InternalServerException.class, RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleInternalServerException(InternalServerException ex) {
        // MDC 는 에러 로그를 식별하기 위해 사용됩니다.
        String requestId = UUID.randomUUID().toString();
        log.info("Internal Server Error, requestId={}, message={}", requestId, ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), requestId);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {NoAuthException.class})
    public ResponseEntity<Object> handleNoAuthException(NoAuthException ex) {
        // MDC 는 에러 로그를 식별하기 위해 사용됩니다.
        String requestId = UUID.randomUUID().toString();
        log.info("UnAuth Error, requestId={}, message={}", requestId, ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), requestId);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleApiRequestErrorException(MethodArgumentNotValidException ex) {
        String requestId = UUID.randomUUID().toString();
        log.info("Method Argument Not Valid, requestId={}, message={}", requestId, ex.getMessage(), ex);

        BindingResult bindingResult = ex.getBindingResult();
        List<String> fieldErrors = bindingResult.getFieldErrors().stream()
                .map(error -> String.format(FIELD_ERROR_FORMAT, error.getField(), error.getDefaultMessage(), error.getRejectedValue()))
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse(String.join(", ", fieldErrors), requestId);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        String requestId = UUID.randomUUID().toString();
        log.info("Not Found Request Error, requestId={}, message={}", requestId, ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), requestId);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @Getter
    @AllArgsConstructor
    private class ErrorResponse {
        private String message;
        private String requestId;
    }
}