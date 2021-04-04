package saturn.backend.security;

import saturn.backend.security.constants.SecurityConstants;
import saturn.backend.security.jwt.JwtUtils;
import saturn.backend.security.userDetails.UserDetailsServiceImpl;
import saturn.backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configurable
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userDetailsService;

    private AuthEntryPointJwt unauthorizedHandler;

    private JwtUtils jwtUtils;

    private SecurityConstants securityConstants;

    private UserRepository userRepository;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService,
                             AuthEntryPointJwt unauthorizedHandler,
                             JwtUtils jwtUtils,
                             SecurityConstants securityConstants,
                             UserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.jwtUtils = jwtUtils;
        this.securityConstants = securityConstants;
        this.userRepository = userRepository;
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        System.out.println("WebSecurityConfig.configure");
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.cors().and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/v1/auth/**").permitAll()
                .antMatchers("/v1/saturn/**").permitAll()
                .anyRequest().authenticated().and()
                .addFilter(new JWTAuthenticationFilter(
                        authenticationManager(),
                        new JwtUtils(securityConstants),
                        securityConstants,
                        userRepository))
                .addFilter(new JWTAuthorizationFilter(
                        authenticationManager(),
                        jwtUtils,
                        userDetailsService,
                        securityConstants));
    }

}
