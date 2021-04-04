package saturn.backend.register;

import saturn.backend.exception.RedundantIssueException;
import saturn.backend.payload.request.RegisterRequest;
import saturn.backend.payload.response.ResponseBody;
import saturn.backend.security.jwt.JwtUtils;
import saturn.backend.user.User;
import saturn.backend.user.UserRepository;
import saturn.backend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/saturn")
public class RegisterController {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        try{
            User newUser = userService.registerUser(registerRequest);
            ResponseBody responseBody = new ResponseBody(newUser, "register successfully", null);
            return ResponseEntity.ok(responseBody);
        }
        catch (Exception e){
            return RedundantIssueException.ok(e.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String jwt){
        String jwtStr = jwt.substring(7);
        Boolean valid = jwtUtils.validateJwtToken(jwtStr);
        ResponseBody responseBody = new ResponseBody(valid, null, null);
        if(valid){
            return ResponseEntity.ok(responseBody);
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
        }

    }
}
