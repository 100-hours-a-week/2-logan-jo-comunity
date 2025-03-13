package KBT2.comunity.back.config.jwt;

import KBT2.comunity.back.dto.User.UserDto;
import KBT2.comunity.back.exception.code.UnauthorizedException;
import KBT2.comunity.back.exception.message.ErrorMessage;
import KBT2.comunity.back.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserService userService;

    private static final List<String> EXCLUDED_PATHS = List.of(
            "/users/signup", "/users/login", "/users/refresh", "/auth/",
            "/swagger-ui/", "/swagger-ui.html", "/swagger-resources/",
            "/v3/api-docs", "/v3/api-docs/", "/webjars/", "/error", "/health"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return EXCLUDED_PATHS.stream().anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = resolveToken(request);

        if (token == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ErrorMessage.Token_NOT_FOUND);
            return;
        }

        try {
            jwtUtil.validateToken(token);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ErrorMessage.INVALID_TOKEN);
            return;
        }

        UUID userId = jwtUtil.getUserIdFromToken(token);
        UserDto userDto = userService.getUser(userId);

        if (userDto == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ErrorMessage.USER_NOT_FOUND);
            return;
        }

        Authentication authentication = jwtUtil.getAuthentication(token, userId);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }


    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
