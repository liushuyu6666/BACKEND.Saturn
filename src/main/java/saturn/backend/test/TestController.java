package saturn.backend.test;

import saturn.backend.role.RoleRepository;
import saturn.backend.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/jupiter")
public class TestController {

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_OWNER') or hasRole('ROLE_ADMIN')")
    public String allAccess(Authentication authentication) {
        ModelMapper mapper = new ModelMapper();
        User user1 = mapper.map(authentication.getPrincipal(), User.class);
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        String userId = userDetails.getId();
        return "Public Content.";
    }

    @GetMapping("/dishes")
    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_OWNER') or hasRole('ROLE_ADMIN')")
    public String StrictAccess() {
        System.out.println("TestController.StrictAccess");
        return "User Content.";
    }

    @GetMapping("/review")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public String CustomerAccess() {
        System.out.println("CustomerAccess.allAccess");
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }
}
