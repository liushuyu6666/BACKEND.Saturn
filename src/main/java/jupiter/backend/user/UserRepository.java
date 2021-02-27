package jupiter.backend.user;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);

    Optional<User> findById(String userId);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

//    ?0 references the first parameter of this method, it is username.
//    fields show that only username filed will be returned.
//    @Query(value = "{ 'username': ?0, 'role': ?1}", fields = "{ 'username' : 1 }")
//    public User checkUserByUsername(String username, String role);
//
//    @Query("{ 'username': ?0 }")
//    public User findUserByUsername(String username);
//
//    @Query("{ 'username': ?0, 'role': ?1}")
//    public User findUserByUsernameAndRole(String username, String role);
//
////    ignore password
//    @Query(value = "{ 'username': ?0, 'role': ?1}",
//            fields = "{ 'username' : 1, 'email': 1, 'role': 1}")
//    public User findUserSafely(String username, String role);
//
//    @Query(value = "{}", fields = "{ 'username' : 1, 'email': 1, 'role': 1}")
//    public List<User> findAllSafely();
//
//    // get password
//    @Query(value = "{ 'username': ?0, 'role': ?1}")
//    public User findPassword(String username, String role);
//
//    // find user by Id
//    @Query(value = "{ 'Id': ?0}", fields = "{'username': 1, 'email': 1, 'role': 1}")
//    public User findBy_IdSafely(String Id);
//
//    // find user's order
//    @Query(value = "{ '_id': ?0}", fields = "{'username': 1, 'email': 1, 'role': 1, 'orders': 1}")
//    public User findOrderBy_IdSafely(String userId);

}
