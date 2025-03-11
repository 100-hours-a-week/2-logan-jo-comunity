package KBT2.comunity.back.config.jwt;

import KBT2.comunity.back.dto.User.UserDto;
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
import java.util.UUID;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (shouldNotFilter(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = resolveToken(request);

        if (token != null && jwtUtil.validateToken(token)) {
            jwtUtil.validateToken(token);
            UUID userId = jwtUtil.getUserIdFromToken(token);

            UserDto userDto = userService.getUser(userId);

            if (userDto == null) {
                System.out.println("JWT 검증 실패");
                return;
            }

            Authentication authentication = jwtUtil.getAuthentication(token, userId);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            System.out.println("토큰 없음");
        }

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
