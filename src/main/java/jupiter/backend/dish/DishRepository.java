package jupiter.backend.dish;

import com.mongodb.BasicDBObject;
import jupiter.backend.shop.Shop;
import jupiter.backend.shop.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

interface CustomizedDishRepository{
    Dish updateDish(String shopId, Dish dish);

    void createDish(String shopId, Dish dish);

    void deleteDish(String shopId, String dishId);

    Dish findDishByName(String shopId, String dishName);

    Dish findDishById(String shopId, String dishId);
}

class CustomizedDishRepositoryImpl implements CustomizedDishRepository{

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    ShopRepository shopRepository;

    public void createDish(String shopId, Dish dish){
        Query query = new Query(Criteria.where("_id").is(shopId));
        Update update = new Update().addToSet("dishes", dish);
        mongoTemplate.updateFirst(query, update, Shop.class);
    }

    public Dish updateDish(String shopId, Dish dish){
        Query query = new Query(new Criteria().andOperator(
                Criteria.where("_id").is(shopId),
                Criteria.where("dishes")
                        // because use elemMatch, only the matched element appears
                        // that's why update addToSet could find this element.
                        .elemMatch(Criteria
                                .where("_id")
                                .is(dish.get_id()))));
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

    public void deleteDish(String shopId, String dishId){
        Query query = new Query(new Criteria().andOperator(
                Criteria.where("_id").is(shopId),
                Criteria.where("dishes")
                        .elemMatch(Criteria
                                .where("_id")
                                .is(dishId))));
        Update update = new Update().pull("dishes",
                new BasicDBObject("_id", dishId));
        mongoTemplate.updateMulti(query, update, Shop.class);
    }

    public Dish findDishByName(String shopId, String dishName){
        Query query = new Query(new Criteria().andOperator(
                Criteria.where("_id").is(shopId),
                Criteria.where("dishes").elemMatch(Criteria.where("name").is(dishName))
        ));
        List<Shop> shopList = mongoTemplate.find(query, Shop.class);
        Shop shop = shopList.stream().findFirst().orElse(null);
        if(shop == null){
            return null;
        }
        List<Dish> dishList =
                shop.getDishes()
                .stream()
                .filter(dish -> dish.getName()
                        .equals(dishName))
                .collect(Collectors.toList());
        return dishList.get(0);
    }

    public Dish findDishById(String shopId, String dishId){
        Query query = new Query(new Criteria().andOperator(
                Criteria.where("_id").is(shopId),
                Criteria.where("dishes").elemMatch(Criteria.where("_id").is(dishId))
        ));
        List<Shop> shopList = mongoTemplate.find(query, Shop.class);
        Shop shop = shopList.stream().findFirst().orElse(null);
        if(shop == null){
            return null;
        }
        List<Dish> dishList =
                shop.getDishes()
                        .stream()
                        .filter(dish -> dish.get_id()
                                .equals(dishId))
                        .collect(Collectors.toList());
        return dishList.get(0);
    }
}

@Repository
public interface DishRepository extends MongoRepository<Dish, String>,
        CustomizedDishRepository
{

    @org.springframework.data.mongodb.repository.Query(value = "{'_id': ?0}")
    Shop listAllDishesInShop(String id);

//    // find a specific dish in the shop
//    @org.springframework.data.mongodb.repository.Query(value = "{'_id': ?0, 'dishes.name': ?1}", fields = "{'dishes': 1}")
//    Dish findDishByName(String shopId, String DishName);

}
