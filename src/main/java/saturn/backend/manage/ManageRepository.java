package saturn.backend.manage;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManageRepository extends MongoRepository<Manage, String> {
    Optional<Manage> findByTableName(String tableName);
}
