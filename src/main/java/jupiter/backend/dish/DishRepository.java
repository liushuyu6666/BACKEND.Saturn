package jupiter.backend.dish;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends MongoRepository<Dish, String> {

    Optional<Dish> findById(String dishId);

    Boolean existsByShopIdAndName(String shopId, String dishName);

    Boolean existsByIdAndShopId(String dishId, String shopId);

    Optional<Dish> findByShopIdAndName(String shopId, String dishName);

    Optional<Dish> findByShopIdAndId(String shopId, String DishId);

    List<Dish> findByShopId(String shopId);

    Boolean existsByIdAndShopIdAndOwnerId(String dishId, String shopId, String ownerId);

    Optional<Dish> findByIdAndOwnerId(String id, String ownerId);

}
