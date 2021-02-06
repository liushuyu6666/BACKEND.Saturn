package jupiter.backend.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jupiter.backend.user.UserDetail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class JWT {

    @Value("${jupiter.jwt.token")
    private String plainSecret;

    @Value("${jupiter.jwt.expire-in-hours}")
    private Long expiration;

    private String superSecret(){
        return Base64.getEncoder().encodeToString(this.plainSecret.getBytes());
    }

    public String createToken(String username, String role){

        Date now = new Date();
        Date expireAt = new Date(now.getTime() + TimeUnit.HOURS.toMillis(this.expiration));


        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expireAt)
                .signWith(SignatureAlgorithm.HS256, superSecret())
                .claim("details", new UserDetail(username, role))
                .compact();
    }

    public LinkedHashMap<String, String> verifyToken(String token) throws Exception{
        try{
            Claims claims = Jwts.parser()
                    .setSigningKey(superSecret())
                    .parseClaimsJws(token)
                    .getBody();
            return (LinkedHashMap) claims.get("details");
        }
        catch(Exception e){
            throw new Exception(e);
        }
    }

    public String isOwner(String token) throws Exception{
        try{
            HashMap<String, String> details = verifyToken(token);
            String username = details.get("username");
            String role = details.get("role");
            if(role.equals("owner")) return username;
            else return null;
        }
        catch (Exception e){
            throw e;
        }
    }

    public String isCustomer(String token) throws Exception{
        try{
            HashMap<String, String> details = verifyToken(token);
            String username = details.get("username");
            String role = details.get("role");
            if(role.equals("customer")) return username;
            else return null;
        }
        catch (Exception e){
            throw e;
        }
    }

    public String isAdmin(String token) throws Exception{
        try{
            HashMap<String, String> details = verifyToken(token);
            String username = details.get("username");
            String role = details.get("role");
            if(role.equals("admin")) return username;
            else return null;
        }
        catch (Exception e){
            throw e;
        }
    }

}
