package jupiter.backend.dump.dish;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DumpDishRepository extends MongoRepository<DumpDish, String> {
}
