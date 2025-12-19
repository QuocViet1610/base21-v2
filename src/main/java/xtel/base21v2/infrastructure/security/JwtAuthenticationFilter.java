package xtel.base21v2.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import xtel.base21v2.infrastructure.exception.CustomException;
import xtel.base21v2.infrastructure.shared.model.TokenInfo;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailService;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    public static final List<String> PUBLIC_PATHS = List.of(
            "/api/auth/login",
            "/api/auth/logout",
            "/api/v2/api-docs",
            "/api/v3/api-docs",
            "/api/swagger-resources",
            "/api/swagger-ui/",
            "/api/webjars/",
            "/api/swagger-ui.html"
    );

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws IOException {

        try {
            if (isPublic(request)) {
                filterChain.doFilter(request, response);
                return;
            }

            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new CustomException("Missing or invalid Authorization header");
            }

            String token = authHeader.substring(7);

            TokenInfo tokenInfo = jwtService.getTokenInfo(token);

            CustomUserDetails accountAuthDto = (CustomUserDetails) userDetailService.loadUserByUsername(tokenInfo.getUsername());

            if (accountAuthDto.isEnabled()) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        accountAuthDto,
                        null,
                        accountAuthDto.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                accessDeniedHandler.handle(request, response, new AccessDeniedException("Account is disabled!"));
                return;
            }

            log.info(">[AUTH]: ID: {}, USERNAME: {}", tokenInfo.getId(), tokenInfo.getUsername());

            filterChain.doFilter(request, response);

        } catch (Exception ce) {
            authenticationEntryPoint.commence(request, response, new AccountExpiredException(ce.getMessage(), ce));
        }

    }


    private boolean isPublic(HttpServletRequest request) {
        return PUBLIC_PATHS.stream().anyMatch(path -> request.getRequestURI().startsWith(path));
    }

}
