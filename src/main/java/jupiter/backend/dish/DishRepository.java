package jupiter.backend.dish;

import jupiter.backend.shop.Shop;
import jupiter.backend.shop.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

interface CustomizedDishRepository{
    Dish updateDish(String shopId, Dish dish);
}

class CustomizedDishRepositoryImpl implements CustomizedDishRepository{

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    ShopRepository shopRepository;

    public Dish updateDish(String shopId, Dish dish){
        Query query = new Query(new Criteria().andOperator(
                Criteria.where("_id").is(shopId),
                Criteria.where("dishes").elemMatch(Criteria.where("_id").is(dish.get_id()))));
//        With the use of the $ array projection operator,
//        you can specify the projection to return the first
//        element that match the query condition on the array field
//        Update update = new Update().set("dishes.$", dish);
        Update update = new Update().set("dishes.$.name", dish.getName())
                .set("dishes.$.imgUrl", dish.getImgUrl())
                .set("dishes.$.desc", dish.getDesc())
                .set("dishes.$.category", dish.getCategory())
                .set("dishes.$.price", dish.getPrice());
//        findAndModify acquires a lock to the database when it starts
//        the operation so that no other operation can process when it is running.
//        When it finishes the operation it releases the lock.
        mongoTemplate.findAndModify(query, update, Shop.class);
        return shopRepository
                .findDishBy_idAndDish_id(shopId, dish.get_id())
                .getDishes()
                .stream()
                .filter(dishElem -> dishElem.get_id().equals(dish.get_id()))
                .findFirst()
                .orElse(null);
    }
}

@Repository
public interface DishRepository extends MongoRepository<Dish, String>,
        CustomizedDishRepository
{

    // list all dishes under the shop
//    @Query(value = "{'_id': ?0}", fields = "{'dishes': 1}")
//    Dish listAllDishesInShop(String shopId);

    @org.springframework.data.mongodb.repository.Query(value = "{'_id': ?0}")
    Shop listAllDishesInShop(String id);

    // find a specific dish in the shop
    @org.springframework.data.mongodb.repository.Query(value = "{'_id': ?0, 'dishes.name': ?1}", fields = "{'dishes': 1}")
    Dish findDishByName(String shopId, String DishName);

}
