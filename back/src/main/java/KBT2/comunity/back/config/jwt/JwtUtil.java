package KBT2.comunity.back.config.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {
    private final Key key;
    private final long accessTokenValidMillisecond = 1000 * 60 * 30;
    private final long refreshTokenValidMillisecond = 1000 * 60 * 60 * 24;

    public JwtUtil(@Value("${jwt.secret}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
    public String createAccessToken(UUID userId, String email) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidMillisecond))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    public String createRefreshToken(UUID userId, String email) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidMillisecond))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public boolean validateToken(String token) {
        try {
            Claims claims = parseToken(token);
            Date expiration = claims.getExpiration();

            if (expiration.before(new Date())) {
                throw new JwtException("만료된 토큰입니다.");
            }
            return true;
        } catch (ExpiredJwtException e) {
            throw new JwtException("만료된 토큰입니다.", e);
        } catch (MalformedJwtException e) {
            throw new JwtException("손상된 토큰입니다.", e);
        } catch (SignatureException e) {
            throw new JwtException("잘못된 서명입니다.", e);
        } catch (IllegalArgumentException e) {
            throw new JwtException("토큰이 비어있거나 잘못되었습니다.", e);
        } catch (JwtException e) {
            throw new JwtException("유효하지 않은 토큰입니다.", e);
        }
    }
    public UUID getUserIdFromToken(String token) {
        Claims claims = parseToken(token);
        return UUID.fromString(claims.getSubject());
    }
    public UsernamePasswordAuthenticationToken getAuthentication(String token, UUID userId) {
        return new UsernamePasswordAuthenticationToken(userId, token, null);
    }

}
