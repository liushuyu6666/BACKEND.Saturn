package jupiter.backend.user;


import jupiter.backend.jwt.JWT;
import jupiter.backend.loginrequest.LoginRequest;
import jupiter.backend.loginrequest.LoginRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jupiter.backend.response.ResponseBody;

import java.util.LinkedHashMap;

@RequestMapping("/v1")
@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JWT jwt;

    @Autowired
    LoginRequest loginRequest;

    @PostMapping("/register")
    public ResponseEntity<ResponseBody> createUser(@RequestBody User userBody){
        try{
            User newUser = userService.createUser(userBody);
            ResponseBody<User> responseBody = new ResponseBody<>(newUser,
                    "create user successfully", null);
            return ResponseEntity.ok(responseBody);

        } catch(Exception e){
            ResponseBody<User> responseBody = new ResponseBody<>(null,
                    e.getMessage(), e);
            return ResponseEntity.ok(responseBody);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseBody> login(@RequestBody LoginRequestBody loginRequestBody){
        try{
            String token = loginRequest.createToken(loginRequestBody);
            ResponseBody responseBody = new ResponseBody(token,
                    "token has been wrapped", null);
            return ResponseEntity.ok(responseBody);
        }
        catch(Exception e){
            ResponseBody responseBody = new ResponseBody(null,
                    e.getMessage(), e);
            return ResponseEntity.ok(responseBody);
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<ResponseBody> checkProfile(@RequestHeader("token") String token){
        try{
            LinkedHashMap<String, String> userDetail = jwt.verifyToken(token);
            // Should be user info
            User currUser = userService.retrieveUserSafely(userDetail.get("username"),
                    userDetail.get("role"));
            ResponseBody responseBody = new ResponseBody(currUser, null, null);
            return ResponseEntity.ok(responseBody);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            ResponseBody responseBody = new ResponseBody(null, e.getMessage(), e);
            return ResponseEntity.ok(responseBody);
        }
    }


}
