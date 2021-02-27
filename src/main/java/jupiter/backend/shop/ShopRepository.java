package jupiter.backend.shop;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository extends MongoRepository<Shop, String> {

    Optional<Shop> findShopById(String shopId);

    Optional<Shop> findShopByShopName(String shopName);

    List<Shop> findAll();

    List<Shop> findShopsByOwnerId(String ownerId);

    Optional<Shop> findShopByIdAndOwnerId(String shopId, String ownerId);

    Boolean existsByShopName(String shopName);

    Boolean existsByIdAndOwnerId(String shopId, String ownerId);

}
