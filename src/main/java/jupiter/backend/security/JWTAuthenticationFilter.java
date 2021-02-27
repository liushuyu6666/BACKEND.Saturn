package jupiter.backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jupiter.backend.security.constants.SecurityConstants;
import jupiter.backend.security.jwt.JwtUtils;
import jupiter.backend.security.userDetails.UserDetailsImpl;
import jupiter.backend.user.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static jupiter.backend.security.constants.SecurityConstants.*;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private JwtUtils jwtUtils;

    private SecurityConstants securityConstants;

    public JWTAuthenticationFilter(
            AuthenticationManager authenticationManager,
            JwtUtils jwtUtils,
            SecurityConstants securityConstants){
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.securityConstants = securityConstants;
        this.setFilterProcessesUrl("/v1/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException{
        try{
            User creds = new ObjectMapper().readValue(req.getInputStream(), User.class);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>()
                    );
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            return authentication;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException{
        String jwt = this.jwtUtils.generateJwtToken(authentication);
        res.addHeader(securityConstants.headerString, securityConstants.tokenPrefix + jwt);
    }
}
