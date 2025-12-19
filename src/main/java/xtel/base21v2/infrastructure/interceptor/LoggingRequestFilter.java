package xtel.base21v2.infrastructure.interceptor;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@Slf4j
public class LoggingRequestFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain
    ) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        long startTime = System.currentTimeMillis();

        log.info(
                "==> [IP]: {}, [METHOD]: {}, [URI]: {}, [PARAM]: {}",
                getRemoteIp(request),
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString()
        );

        filterChain.doFilter(servletRequest, servletResponse);

        long totalTime = System.currentTimeMillis() - startTime;

        log.info(
                "<== [TIME_EXC]: {} ms. {}",
                totalTime,
                totalTime > 500 ? "[SLOW_REQUEST]" : ""
        );
    }

    private String getRemoteIp(HttpServletRequest request) {
        String remoteIp = request.getHeader("X-FORWARDED-FOR");

        if (StringUtils.isBlank(remoteIp) || "unknown".equalsIgnoreCase(remoteIp)) {
            remoteIp = request.getHeader("Proxy-Client-IP");

            if (StringUtils.isBlank(remoteIp) || "unknown".equalsIgnoreCase(remoteIp)) {
                remoteIp = request.getRemoteAddr();
            }
        } else {
            int commaIndex = remoteIp.indexOf(',');
            if (commaIndex != -1) {
                remoteIp = remoteIp.substring(0, commaIndex).trim();
            }
        }

        return remoteIp;
    }

}
