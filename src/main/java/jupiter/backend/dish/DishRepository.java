package jupiter.backend.dish;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends MongoRepository<Dish, String> {

    // list all dishes under the shop
    @Query(value = "{'_id': ?0}", fields = "{'dishes': 1}")
    Dish listAllDishesInShop(String shopName);

    // find a specific dish in the shop
    @Query(value = "{'_id': ?0, 'dishes.name': ?1}", fields = "{'dishes': 1}")
    Dish findDishByName(String shopId, String DishName);

}
