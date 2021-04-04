package saturn.backend.core;

import saturn.backend.role.ERole;
import saturn.backend.role.Role;
import saturn.backend.role.RoleRepository;
import saturn.backend.security.userDetails.UserDetailsImpl;
import saturn.backend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class AuthenticationService {

    @Autowired
    RoleRepository roleRepository;

    public String parseAuthenticationGetId(Authentication authentication){
        return parseAuthentication(authentication).getId();
    }

    private User parseAuthentication(Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        HashSet<Role> roles = new HashSet<>();
        for(GrantedAuthority grantedAuthority: userDetails.getAuthorities()){
            Role role = roleRepository
                    .findByName(
                            ERole.valueOf(grantedAuthority.getAuthority())
                    ).orElse(null);
            roles.add(role);
        }
        User currentUser = new User();
        currentUser.setId(userDetails.getId());
        currentUser.setUsername(userDetails.getUsername());
        currentUser.setEmail(userDetails.getEmail());
        currentUser.setAuthorities(roles);

        return currentUser;
    }
}
