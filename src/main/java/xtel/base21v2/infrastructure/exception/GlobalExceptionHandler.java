package xtel.base21v2.infrastructure.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import xtel.base21v2.infrastructure.common.ResponseDto;

import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final HttpServletRequest request;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<?>> handleException(Exception e) {
        log.error(">[INTERNAL_SERVER_ERROR]: {}", e.getMessage(), e);
        ResponseDto<?> responseDto = ResponseDto.builder()
                .requestId(MDC.get("requestId"))
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseDto<?>> handleCustomException(CustomException e) {
        log.error(">[CUSTOM_EXCEPTION]: {}", e.getMessage());
        ResponseDto<?> responseDto = ResponseDto.builder()
                .requestId(MDC.get("requestId"))
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(e.getHttpStatus()).body(responseDto);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ResponseDto<?>> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error(">[MISSING_PARAM_EXCEPTION]: {}", e.getMessage() + " - " + e.getParameterName() + " is required.", e);
        ResponseDto<?> responseDto = ResponseDto.builder()
                .requestId(MDC.get("requestId"))
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ResponseDto<?>> handleAuthException(AuthException e) {
        log.error(">[AUTH_EXCEPTION]: {}", e.getMessage());
        ResponseDto<?> responseDto = ResponseDto.builder()
                .requestId(MDC.get("requestId"))
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.UNAUTHORIZED.value())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDto);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseDto<?>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error(">[HTTP_METHOD_NOT_SUPPORTED]: {}", e.getMessage());
        ResponseDto<?> responseDto = ResponseDto.builder()
                .requestId(MDC.get("requestId"))
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.METHOD_NOT_ALLOWED.value())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(responseDto);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDto<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error(">[HTTP_MESSAGE_NOT_READABLE]: {}", e.getMessage());
        ResponseDto<?> responseDto = ResponseDto.builder()
                .requestId(MDC.get("requestId"))
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Invalid JSON format or malformed request body")
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ResponseDto<?>> handleNoResourceFoundException(NoResourceFoundException e, HttpServletRequest request) {
        log.error(">[NO_RESOURCE_FOUND]: {}", e.getMessage());
        ResponseDto<?> responseDto = ResponseDto.builder()
                .requestId(MDC.get("requestId"))
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.NOT_FOUND.value())
                .message("Resource not found for " + request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseDto<?>> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        log.error(">[TYPE_MISMATCH]: {}", e.getMessage(), e);
        ResponseDto<?> responseDto = baseResponse(HttpStatus.BAD_REQUEST, "Invalid parameter type: " + e.getName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ResponseDto<?>> handleUnsupportedMedia(HttpMediaTypeNotSupportedException e) {
        log.error(">[UNSUPPORTED_MEDIA_TYPE]: {}", e.getMessage());
        ResponseDto<?> responseDto = baseResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Unsupported media type");
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(responseDto);
    }

    @ExceptionHandler(ClassCastException.class)
    public ResponseEntity<ResponseDto<?>> handleClassCast(ClassCastException e) {
        log.error(">[CLASS_CAST_EXCEPTION]: {}", e.getMessage(), e);
        ResponseDto<?> responseDto = baseResponse(HttpStatus.BAD_REQUEST, "Type conversion error");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    public ResponseEntity<ResponseDto<?>> handleInvalidDataAccess(InvalidDataAccessResourceUsageException e) {
        log.error(">[INVALID_DATA_ACCESS]: {}", e.getMessage(), e);
        ResponseDto<?> responseDto = baseResponse(HttpStatus.BAD_REQUEST, "Invalid database resource usage");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

    @ExceptionHandler(CannotCreateTransactionException.class)
    public ResponseEntity<ResponseDto<?>> handleCannotCreateTransaction(CannotCreateTransactionException e) {
        log.error(">[CANNOT_CREATE_TRANSACTION]: {}", e.getMessage(), e);
        ResponseDto<?> responseDto = baseResponse(HttpStatus.BAD_REQUEST, "Cannot create database transaction");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ResponseDto<?>> handleNullPointer(NullPointerException e) {
        log.error(">[NULL_POINTER_EXCEPTION]: {}", e.getMessage(), e);
        ResponseDto<?> responseDto = baseResponse(HttpStatus.BAD_REQUEST, "Unexpected null value encountered");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

    @ExceptionHandler(IncorrectResultSizeDataAccessException.class)
    public ResponseEntity<ResponseDto<?>> handleIncorrectResultSize(IncorrectResultSizeDataAccessException e) {
        log.error(">[INCORRECT_RESULT_SIZE]: {}", e.getMessage(), e);
        ResponseDto<?> responseDto = baseResponse(HttpStatus.BAD_REQUEST, "Incorrect result size returned from query");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

    @ExceptionHandler(TimeoutException.class)
    public ResponseEntity<ResponseDto<?>> handleTimeout(TimeoutException e) {
        log.error(">[TIMEOUT_EXCEPTION]: {}", e.getMessage(), e);
        ResponseDto<?> responseDto = baseResponse(HttpStatus.REQUEST_TIMEOUT, "Request timed out");
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(responseDto);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto<?>> handleIllegalArgument(IllegalArgumentException e) {
        log.error(">[ILLEGAL_ARGUMENT]: {}", e.getMessage(), e);
        ResponseDto<?> responseDto = baseResponse(HttpStatus.BAD_REQUEST, "Invalid argument: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

    private ResponseDto<?> baseResponse(HttpStatus status, String message) {
        return ResponseDto.builder()
                .requestId(MDC.get("requestId"))
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .code(status.value())
                .message(message)
                .build();
    }
}
