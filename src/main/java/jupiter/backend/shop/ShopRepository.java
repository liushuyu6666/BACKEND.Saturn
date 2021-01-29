package jupiter.backend.shop;

import jupiter.backend.dish.Dish;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface ShopRepository extends MongoRepository<Shop, String> {

    // for owner to find all shops
    @Query("{'owner': ?0}")
    List<Shop> findAllByOwner(String owner);

    // find all shop name
    @Query(value = "{}", fields = "{'name': 1}")
    List<HashMap<String, String>> findAllShopName();

    // find shop by name
    @Query(value = "{'name': ?0}")
    Shop findShopByName(String shopName);

    // find shop by id
    @Query(value = "{'_id': ?0}")
    Shop findBy_id(String id);

    @Query(value = "{'_id': ?0, 'dishes.name': ?1}")
    Shop findShopBy_idAndDishName(String id, String dishName);

    // find a specific dish by shop id and dish id
    @Query(value = "{'_id': ?0, 'dishes._id': ?1}")
    Shop findShopBy_idAndDish_id(String id, String dishId);

    @Query(value = "{'_id' : ?0, 'dishes._id': ?1}", fields = "{'dishes': 1}")
    Shop findDishBy_idAndDish_id(String id, String dishId);


}
