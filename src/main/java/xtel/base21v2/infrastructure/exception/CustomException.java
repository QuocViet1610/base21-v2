package xtel.base21v2.infrastructure.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private final HttpStatus httpStatus;

    public CustomException(String message) {
        super(message);
        httpStatus = HttpStatus.BAD_REQUEST;
    }

    public CustomException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
        httpStatus = HttpStatus.BAD_REQUEST;
    }

    public CustomException(HttpStatus httpStatus, String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

}
