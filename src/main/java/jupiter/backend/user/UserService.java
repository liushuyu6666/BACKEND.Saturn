package jupiter.backend.user;

import jupiter.backend.exception.RedundantIssueException;
import jupiter.backend.payload.request.RegisterRequest;
import jupiter.backend.payload.response.MessageResponse;
import jupiter.backend.payload.response.ResponseBody;
import jupiter.backend.role.ERole;
import jupiter.backend.role.Role;
import jupiter.backend.role.RoleRepository;
import jupiter.backend.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    public User registerUser(RegisterRequest registerRequest) throws RedundantIssueException {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RedundantIssueException("Username is already taken!");
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RedundantIssueException("Email is already in use!");
        }

        User user = new User(registerRequest.getUsername(),
                encoder.encode(registerRequest.getPassword()),
                registerRequest.getEmail());

        Set<String> strRoles = registerRequest.getAuthorities();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            strRoles = new HashSet<>();
            strRoles.add("customer");
        }
        else if(strRoles.isEmpty()){
            strRoles.add("customer");
        }
        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role(admin) is not found."));
                    roles.add(adminRole);
                    break;
                case "owner":
                    Role ownerRole = roleRepository.findByName(ERole.ROLE_OWNER)
                            .orElseThrow(() -> new RuntimeException("Error: Role(owner) is not found."));
                    roles.add(ownerRole);

                    break;
                case "customer":
                    Role customerRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                            .orElseThrow(() -> new RuntimeException("Error: Role(customer) is not found."));
                    roles.add(customerRole);

                    break;
                default:
                    throw new RuntimeException(String.format("Error: no such role: %s", role));
            }
        });

        user.setAuthorities(roles);
        User newUser = userRepository.save(user);

        return newUser;
    }

}
