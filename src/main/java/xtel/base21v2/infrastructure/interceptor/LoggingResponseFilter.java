package xtel.base21v2.infrastructure.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import xtel.base21v2.infrastructure.common.ResponseDto;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
@Order(0)
@RequiredArgsConstructor
public class LoggingResponseFilter implements ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper;
    private final HttpServletRequest httpServletRequest;

    @Override
    public boolean supports(
            @NonNull MethodParameter methodParameter,
            @NonNull Class<? extends HttpMessageConverter<?>> converterType
    ) {
        return true;
    }

    @Override
    @SneakyThrows
    public Object beforeBodyWrite(
            Object body,
            @NonNull MethodParameter methodParameter,
            @NonNull MediaType mediaType,
            @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
            @NonNull ServerHttpRequest request,
            @NonNull ServerHttpResponse response
    ) {

        if (methodParameter.getContainingClass().isAnnotationPresent(RestController.class)
                        || methodParameter.getContainingClass().isAnnotationPresent(RestControllerAdvice.class)) {

            if (body == null)
                body = new Object();

            if (!httpServletRequest.getRequestURL().toString().contains("api-docs") && body.getClass() != ResponseDto.class) {

                body = ResponseDto.builder()
                        .requestId(MDC.get("requestId"))
                        .path(httpServletRequest.getRequestURI())
                        .timestamp(LocalDateTime.now())
                        .code(200)
                        .message("SUCCESS")
                        .data(body)
                        .build();

                log.info("[RESPONSE]: {}", objectMapper.writeValueAsString(body));

            }
        }

        return body;
    }
}
