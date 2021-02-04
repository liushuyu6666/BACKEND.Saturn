package jupiter.backend.jwt;

import jupiter.backend.loginrequest.LoginRequest;
import jupiter.backend.loginrequest.LoginRequestBody;
import jupiter.backend.response.ResponseBody;
import jupiter.backend.user.User;
import jupiter.backend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;

@RequestMapping("/v1")
@RestController
@CrossOrigin(origins = "*")
public class JwtController {

    @Autowired
    JWT jwt;

    @Autowired
    UserService userService;

    @Autowired
    LoginRequest loginRequest;

    @GetMapping("/token")
    public ResponseEntity<ResponseBody> verifyAndParseToken(@RequestHeader String token){
        try{
            HashMap<String, String> details = jwt.verifyToken(token);
            ResponseBody responseBody =
                    new ResponseBody(details, "parse token successfully", null);
            return ResponseEntity.ok(responseBody);
        }
        catch (Exception e){
            ResponseBody responseBody =
                    new ResponseBody(null, e.getMessage(), e);
            return ResponseEntity.ok(responseBody);
        }
    }

    // for login user to check his own profile, password is hidden
    @GetMapping("/profile")
    public ResponseEntity<ResponseBody> checkProfile(@RequestHeader("token") String token){
        try{
            LinkedHashMap<String, String> userDetail = jwt.verifyToken(token);
            // Should be user info
            User loginUser = userService.retrieveUserSafely(userDetail.get("username"),
                    userDetail.get("role"));
            ResponseBody responseBody = new ResponseBody(loginUser, null, null);
            return ResponseEntity.ok(responseBody);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            ResponseBody responseBody = new ResponseBody(null, e.getMessage(), e);
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
}
