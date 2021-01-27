package jupiter.backend.user;

import jupiter.backend.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWT jwt;

    public User createUser(User newUser) throws Exception{
        // check the existence of the user
        if(userRepository.checkUserByUsername(newUser.getUsername(),
                newUser.getRole()) == null){
            // check if password is null
            if(newUser.getPassword() != null){
                User savingUser = new User();
                savingUser.setUsername(newUser.getUsername());
                savingUser.setPassword(newUser.getPassword());
                savingUser.setEmail(newUser.getEmail());
                savingUser.setRole(newUser.getRole());

                return userRepository.save(savingUser);
            }
            else{
                throw new Exception("we detect you password is empty which is illegal");
            }
        }
        else{
            throw new Exception("username already existed.");
        }
    }

    // not return password
    public User retrieveUserSafely(String username, String role) throws Exception{
        User foundUser = userRepository.findUserSafely(username, role);
        if(foundUser != null){
            return foundUser;
        }
        else{
            throw new Exception("can't be retrieved, no such user");
        }
    }

    // not return password
    public List<User> listUserSafely(){
        return userRepository.findAllSafely();
    }

    public User updateUser(User updatingUser) throws Exception{
        String updatingUsername = updatingUser.getUsername();
        String updatingRole     = updatingUser.getRole();
        User updatedUser = userRepository.findUserByUsernameAndRole(updatingUsername,
                updatingRole);
        if(updatedUser != null){
            updatedUser.setEmail(
                (updatingUser.getEmail() != null)?
                updatingUser.getEmail():updatedUser.getEmail()
            );
            updatedUser.setPassword(
                (updatingUser.getPassword() != null)?
                updatingUser.getPassword(): updatedUser.getPassword()
            );
            return userRepository.save(updatedUser);
        }
        else{
            throw new Exception("can't update, no such user");
        }
    }

//    public String createToken(LoginRequestBody loginRequestBody) throws Exception{
//        String loginUsername = loginRequestBody.getUsername();
//        String loginPassword = loginRequestBody.getPassword();
//        String loginRole     = loginRequestBody.getRole();
//        User foundUser = userRepository.findUserByUsernameAndRole(loginUsername, loginRole);
//
//        if(foundUser != null){
//            if(foundUser.getPassword().equals(loginPassword)){
//                return jwt.createToken(loginUsername, foundUser.getRole());
//            }
//            else{
//                throw new Exception("password and username don't match");
//            }
//        }
//        else{
//            if(loginUsername == null) throw new Exception("username can't be empty");
//            else throw new Exception("no such user");
//        }
//    }



}
