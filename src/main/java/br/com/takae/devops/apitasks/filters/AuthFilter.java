package br.com.takae.devops.apitasks.filters;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {
    private final MeterRegistry registry;
    private Counter authErrors;

    @PostConstruct
    void setUpCounters() {
        authErrors = Counter.builder("auth_error")
                .description("")
                .register(registry);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().startsWith("/actuator")) {
            filterChain.doFilter(request, response);
        } else {
            String xAuthUsername = request.getHeader("X-Auth-Username");
            if (xAuthUsername == null) {
                log.debug("Unauthorized from {}", request.getLocalAddr());
                authErrors.increment();
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
