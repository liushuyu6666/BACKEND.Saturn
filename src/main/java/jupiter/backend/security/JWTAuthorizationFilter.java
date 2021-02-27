package jupiter.backend.security;


import jupiter.backend.security.constants.SecurityConstants;
import jupiter.backend.security.jwt.JwtUtils;
import jupiter.backend.security.userDetails.UserDetailsServiceImpl;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private JwtUtils jwtUtils;

    private UserDetailsServiceImpl userDetailsService;

    private SecurityConstants securityConstants;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
                                  JwtUtils jwtUtils,
                                  UserDetailsServiceImpl userDetailsService,
                                  SecurityConstants securityConstants){
        super(authenticationManager);
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
        this.securityConstants = securityConstants;
    }

    private static final Logger logger = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
            throw e;
        }
        filterChain.doFilter(request, response);
    }

    // get token String
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(securityConstants.headerString);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(securityConstants.tokenPrefix)) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }
}
