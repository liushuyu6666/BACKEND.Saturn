package saturn.backend.language;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LanguageRepository extends MongoRepository<Language, String> {
    Optional<Language> findByIdAndCreateBy(String Id, String userId);
}
