package jupiter.backend.user;


import jupiter.backend.jwt.JWT;
import jupiter.backend.loginrequest.LoginRequest;
import jupiter.backend.loginrequest.LoginRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jupiter.backend.response.ResponseBody;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;

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

    // just create customer or owner
    @PostMapping("/register")
    public ResponseEntity<ResponseBody> createUser(@RequestBody User userBody){
        try{
            if(userBody.getRole().equals("customer") || userBody.getRole().equals("owner")){
                User newUser = userService.createUser(userBody);
                ResponseBody<User> responseBody = new ResponseBody<>(newUser,
                        "create user successfully", null);
                return ResponseEntity.ok(responseBody);
            }
            else{
                ResponseBody responseBody = new ResponseBody(null,
                        "can only create use for customer or owner", null);
                return ResponseEntity.ok(responseBody);
            }

        } catch(Exception e){
            ResponseBody<User> responseBody = new ResponseBody<>(null,
                    e.getMessage(), e);
            return ResponseEntity.ok(responseBody);
        }
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseBody> retrieveUserById(@PathVariable("userId") String userId,
                                                     @RequestHeader("token") String token){
        try{
            if(jwt.isAdmin(token) != null){
                User foundUser = userService.retrieveUserByIdSafely(userId);
                ResponseBody responseBody =
                        new ResponseBody(foundUser, "retrieve user", null);
                return ResponseEntity.ok(responseBody);
            }
            else{
                ResponseBody responseBody =
                        new ResponseBody(null, "only admin and check", null);
                return ResponseEntity.ok(responseBody);
            }
        }
        catch (Exception e){
            ResponseBody responseBody =
                    new ResponseBody(null, e.getMessage(), e);
            return ResponseEntity.ok(responseBody);
        }
    }

    // for admin, token belongs to admin
    @GetMapping("/users/{username}/role/{role}")
    public ResponseEntity<ResponseBody> retrieveUser(@PathVariable("username") String username,
                                                     @PathVariable("role") String role,
                                                     @RequestHeader("token") String token){
        try{
            if(jwt.isAdmin(token) != null){
                User foundUser = userService.retrieveUserSafely(username, role);
                ResponseBody responseBody =
                        new ResponseBody(foundUser, "retrieve user", null);
                return ResponseEntity.ok(responseBody);
            }
            else{
                ResponseBody responseBody =
                        new ResponseBody(null, "only admin and check", null);
                return ResponseEntity.ok(responseBody);
            }
        }
        catch (Exception e){
            ResponseBody responseBody =
                    new ResponseBody(null, e.getMessage(), e);
            return ResponseEntity.ok(responseBody);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<ResponseBody> listUser(@RequestHeader("token") String token){
        try{
            if(jwt.isAdmin(token) != null){
                List<User> listUser = userService.listUserSafely();
                ResponseBody responseBody =
                        new ResponseBody(listUser, "list users", null);
                return ResponseEntity.ok(responseBody);
            }
            else{
                ResponseBody responseBody =
                        new ResponseBody(null, "only admin and check", null);
                return ResponseEntity.ok(responseBody);
            }
        }
        catch (Exception e){
            ResponseBody responseBody =
                    new ResponseBody(null, e.getMessage(), e);
            return ResponseEntity.ok(responseBody);
        }
    }

    // update user info, but username and role can't be modified.
    @PostMapping("/users/{username}/role/{role}")
    public ResponseEntity<ResponseBody> updateUser(@RequestHeader("token") String token,
                                                   @RequestBody UserUpdateBody userUpdateBody,
                                                   @PathVariable("username") String username,
                                                   @PathVariable("role") String role) {
        try {
            LinkedHashMap<String, String> details = jwt.verifyToken(token);
            String usernameFromToken = details.get("username");
            String roleFromToken = details.get("role");
            String oldPassword = userUpdateBody.getOldPassword();
            User updateUser = userUpdateBody.getUser();
            String passwordStored = userService.findPassword(usernameFromToken, roleFromToken);
            if(usernameFromToken.equals(username)
                    && roleFromToken.equals(role)
            && passwordStored.equals(oldPassword)){
                // need to assemble again since username and role in updateUser need to be discarded
                User updatingUser = new User();
                updatingUser.setUsername(username);
                updatingUser.setPassword(updateUser.getPassword());
                updatingUser.setEmail(updateUser.getEmail());
                updatingUser.setRole(role);
                updatingUser = userService.updateUser(updatingUser);
                ResponseBody responseBody =
                        new ResponseBody(updatingUser,"update user", null);
                return ResponseEntity.ok(responseBody);
            }
            else{
                ResponseBody responseBody;
                if(!usernameFromToken.equals(username)
                        || !roleFromToken.equals(role)){
                    responseBody = new ResponseBody(null, "can only update your own user", null);
                }
                else{
                    responseBody = new ResponseBody(null, "password don't match", null);
                }
                return ResponseEntity.ok(responseBody);
            }
        }
        catch (Exception e) {
            ResponseBody responseBody =
                    new ResponseBody(null, e.getMessage(), e);
            return ResponseEntity.ok(responseBody);
        }
    }
}
