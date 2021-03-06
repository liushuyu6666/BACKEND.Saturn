package saturn.backend.career;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareerRepository extends MongoRepository<Career, String> {
    Integer countById();
    List<Career> findAllByIsActiveAndIsAppliedOrderByModifiedAtDesc(Boolean isActive, Boolean isApplied);
    List<Career> findAllByIsActiveAndIsAppliedOrderByModifiedAtDesc(Pageable paging, Boolean isActive, Boolean isApplied);
}
