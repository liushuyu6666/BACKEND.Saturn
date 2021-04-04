package saturn.backend.career;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareerRepository extends MongoRepository<Career, String> {
}
