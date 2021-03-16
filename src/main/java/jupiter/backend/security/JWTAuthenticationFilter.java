package jupiter.backend.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jupiter.backend.payload.response.ResponseBody;
import jupiter.backend.role.Role;
import jupiter.backend.role.RoleRepository;
import jupiter.backend.security.constants.SecurityConstants;
import jupiter.backend.security.jwt.JwtUtils;
import jupiter.backend.security.userDetails.UserDetailsImpl;
import jupiter.backend.security.userDetails.UserDetailsServiceImpl;
import jupiter.backend.user.User;
import jupiter.backend.user.UserProfile;
import jupiter.backend.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static jupiter.backend.security.constants.SecurityConstants.*;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private JwtUtils jwtUtils;

    private SecurityConstants securityConstants;

    private UserRepository userRepository;

    public JWTAuthenticationFilter(
            AuthenticationManager authenticationManager,
            JwtUtils jwtUtils,
            SecurityConstants securityConstants,
            UserRepository userRepository){
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.securityConstants = securityConstants;
        this.userRepository = userRepository;
        this.setFilterProcessesUrl("/v1/jupiter/login");
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
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        User user = userRepository.findByUsername(username).orElse(null);
        assert user != null;
        Set<String> strRoles = new HashSet<>();
        for(Iterator<Role> it = user.getAuthorities().iterator(); it.hasNext(); ){
            Role r = it.next();
            strRoles.add(r.getDesc());
        }
        UserProfile userProfile = new UserProfile();
        userProfile.setUsername(user.getUsername());
        userProfile.setEmail(user.getEmail());
        userProfile.setJwt(jwt);
        userProfile.setRoles(strRoles);
        ResponseBody responseBody = new ResponseBody(userProfile, "login successfully", null);

        PrintWriter out = res.getWriter();
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(responseBody);
        out.print(json);
        out.flush();
    }
}
