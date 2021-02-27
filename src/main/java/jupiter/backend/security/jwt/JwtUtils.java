package jupiter.backend.security.jwt;

import jupiter.backend.security.constants.SecurityConstants;
import jupiter.backend.security.userDetails.UserDetailsImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

//    @Value("${jupiter.jwt.token}")
//    private String jwtSecret;
//
//    @Value("${jupiter.jwt.expire-in-hours}")
//    private Long jwtExpirationHour;

    private SecurityConstants securityConstants;

    public JwtUtils(SecurityConstants securityConstants) {
        this.securityConstants = securityConstants;
    }

    public String generateJwtToken(Authentication authentication){

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        Date now = new Date();
        Date expireAt = new Date(now.getTime() + TimeUnit.HOURS.toMillis(securityConstants.expireInHours));

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(expireAt)
                .signWith(SignatureAlgorithm.HS512, securityConstants.secret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token){
        return Jwts.parser().setSigningKey(securityConstants.secret)
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken){
        try{
            Jwts.parser().setSigningKey(securityConstants.secret).parseClaimsJws(authToken);
            return true;
        }
        catch (SignatureException e){
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
