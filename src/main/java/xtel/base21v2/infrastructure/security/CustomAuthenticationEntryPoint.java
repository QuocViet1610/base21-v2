package xtel.base21v2.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import xtel.base21v2.infrastructure.common.ResponseDto;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("Content-Type", "application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(
                objectMapper.writeValueAsString(
                        ResponseDto.builder()
                                .requestId(MDC.get("requestId"))
                                .timestamp(LocalDateTime.now())
                                .code(HttpServletResponse.SC_UNAUTHORIZED)
                                .message(authException.getMessage())
                                .build()
                )
        );
        log.error("[UNAUTHORIZED]: {}", authException.getMessage());
    }

}
