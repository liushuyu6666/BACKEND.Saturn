package saturn.backend.user;

import saturn.backend.exception.RedundantIssueException;
import saturn.backend.payload.request.RegisterRequest;
import saturn.backend.role.ERole;
import saturn.backend.role.Role;
import saturn.backend.role.RoleRepository;
import saturn.backend.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    public String findUserName(String userId){
        User targetUser = userRepository.findById(userId).orElse(null);
        if(targetUser == null) return null;
        else return targetUser.getUsername();
    }

    public User registerUser(RegisterRequest registerRequest) throws RedundantIssueException {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RedundantIssueException("Username is already taken!");
        }
        if (!registerRequest.getEmail().trim().equals("") &&
                userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RedundantIssueException("Email is already in use!");
        }

        User user = new User(registerRequest.getUsername(),
                encoder.encode(registerRequest.getPassword()),
                registerRequest.getEmail());

        Set<String> strRoles = registerRequest.getAuthorities();
        Set<Role> roles = new HashSet<>();

//        if (strRoles == null) {
//            strRoles = new HashSet<>();
//            strRoles.add("customer");
//        }
//        else if(strRoles.isEmpty()){
//            strRoles.add("customer");
//        }
        strRoles.forEach(role -> {
            switch (role) {
                case "careerRead":
                    Role careerRoleRead = roleRepository.findByName(ERole.ROLE_CAREER_READ)
                            .orElseThrow(() -> new RuntimeException("Error: Role(careerRead) is not found."));
                    roles.add(careerRoleRead);

                    break;
                case "careerWrite":
                    Role careerRoleWrite = roleRepository.findByName(ERole.ROLE_CAREER_WRITE)
                            .orElseThrow(() -> new RuntimeException("Error: Role(careerWrite) is not found."));
                    roles.add(careerRoleWrite);

                    break;
                case "careerManage":
                    Role careerRoleManage = roleRepository.findByName(ERole.ROLE_CAREER_MANAGE)
                            .orElseThrow(() -> new RuntimeException("Error: Role(careerManage) is not found."));
                    roles.add(careerRoleManage);

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
