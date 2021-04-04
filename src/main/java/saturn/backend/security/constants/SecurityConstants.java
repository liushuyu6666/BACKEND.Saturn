package saturn.backend.security.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityConstants {

    @Value("${jupiter.jwt.token}")
    public String secret;

    @Value("${jupiter.jwt.expire-in-hours}")
    public Long expireInHours;

    @Value("${jupiter.jwt.header_string}")
    public String headerString;

    @Value("${jupiter.jwt.token_prefix}")
    public String tokenPrefix;

    @Value("${jupiter.login.url}")
    public String loginUrl;
}
