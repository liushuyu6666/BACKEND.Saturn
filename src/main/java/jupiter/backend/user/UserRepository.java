package jupiter.backend.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

//    ?0 references the first parameter of this method, it is username.
//    fields show that only username filed will be returned.
    @Query(value = "{ 'username': ?0, 'role': ?1}", fields = "{ 'username' : 1 }")
    public User checkUserByUsername(String username, String role);

    @Query("{ 'username': ?0 }")
    public User findUserByUsername(String username);

    @Query("{ 'username': ?0, 'role': ?1}")
    public User findUserByUsernameAndRole(String username, String role);

//    ignore password
    @Query(value = "{ 'username': ?0, 'role': ?1}",
    fields = "{ 'password': 0}")
    public User findUserSafely(String username, String role);

    @Query(value = "{}", fields = "{ 'passowrd' : 0}")
    public List<User> findAllSafely();

}
