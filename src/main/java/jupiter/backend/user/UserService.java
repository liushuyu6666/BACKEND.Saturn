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

    public User retrieveUserByIdSafely(String userId) throws Exception{
        User foundUser = userRepository.findBy_IdSafely(userId);
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

    // because user have no primary key, so when update, we need to delete it and add again
    public User updateUser(User updatingUser) throws Exception{
        String updatingUsername = updatingUser.getUsername();
        String updatingRole     = updatingUser.getRole();
        User updatedUser = userRepository.findUserByUsernameAndRole(updatingUsername,
                updatingRole);
        if(updatedUser != null){
            updatedUser.setPassword(updatingUser.getPassword());
            updatedUser.setEmail(updatingUser.getEmail());
            return userRepository.save(updatedUser);
        }
        else{
            throw new Exception("can't update, no such user");
        }
    }

    public String findPassword(String username, String role){
        return userRepository.findPassword(username, role).getPassword();
    }

    // get id by username and role
    public String find_Id(String username, String role){
        User user = userRepository.findUserByUsernameAndRole(username, role);
        if(user != null) return user.getId();
        else return null;
    }

}
