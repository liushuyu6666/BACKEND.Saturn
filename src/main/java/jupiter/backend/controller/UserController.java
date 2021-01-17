package jupiter.backend.controller;


import jupiter.backend.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jupiter.backend.response.ResponseBody;

@RequestMapping("/v1")
@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    MongoTemplate mongoTemplate;

    @PostMapping("/register")
    public ResponseEntity<ResponseBody> createUser(@RequestBody User userBody){
        try{
            User newUser = new User();
            newUser.setUsername(userBody.getUsername());
            newUser.setPassword(userBody.getPassword());
            newUser.setEmail(userBody.getEmail());
            newUser.setRole(userBody.getRole());

            newUser = mongoTemplate.insert(newUser);

            ResponseBody<User> responseBody = new ResponseBody<>(newUser,
                    "create a new user successfully", null);
            return ResponseEntity.ok(responseBody);

        } catch(Exception e){
            ResponseBody<User> responseBody = new ResponseBody<>(null,
                    e.getMessage(), e);
            return ResponseEntity.ok(responseBody);
        }
    }


}
