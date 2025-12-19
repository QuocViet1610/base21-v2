package xtel.base21v2.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import xtel.base21v2.infrastructure.common.ResponseDto;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setHeader("Content-Type", "application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(
                objectMapper.writeValueAsString(
                        ResponseDto.builder()
                                .requestId(MDC.get("requestId"))
                                .timestamp(LocalDateTime.now())
                                .code(HttpServletResponse.SC_FORBIDDEN)
                                .message(accessDeniedException.getMessage())
                                .build()
                )
        );
        log.error("[ACCESS_DENIED]: {}", accessDeniedException.getMessage());
    }

}
