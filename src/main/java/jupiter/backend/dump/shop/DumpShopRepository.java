package jupiter.backend.dump.shop;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DumpShopRepository extends MongoRepository<DumpShop, String> {
}
