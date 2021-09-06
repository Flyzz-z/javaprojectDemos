package xyz.flyzz.securityjwt.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

@Slf4j
public final class JwtUtils {

    private final static String SECRET_KEY = "flyzzfighting";

    private final static Duration EXPIRATION = Duration.ofHours(1);

    public static String generate(Map<String,Object> map,Duration duration) {
        Date expiryDate = new Date(System.currentTimeMillis()+duration.toMillis());
        return Jwts.builder().setSubject("token").setClaims(map).setIssuedAt(new Date()).setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY).compact();
    }

    public static Claims parse(String token) {
        if(!StringUtils.hasLength(token)) {
            return null;
        }

        Claims claims = null;

        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            log.info(e.toString());
        }
        return claims;
    }
}
