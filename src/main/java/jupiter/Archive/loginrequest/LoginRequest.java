//package Archive.loginrequest;
//
//import jupiter.backend.jwt.JWT;
//import jupiter.backend.user.User;
//import jupiter.backend.user.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class LoginRequest {
//
//    @Autowired
//    JWT jwt;
//
//    @Autowired
//    UserRepository userRepository;
//
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
//            if(loginUsername == null || loginUsername.trim().equals("")) throw new Exception("username can't be empty");
//            else if(loginRole == null || loginRole.trim().equals("")) throw new Exception("role can't be empty");
//            else throw new Exception("no such user");
//        }
//    }
//
//}
