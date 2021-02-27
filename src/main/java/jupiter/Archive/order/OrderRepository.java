//package Archive.order;
//
//import com.mongodb.BasicDBObject;
//import jupiter.backend.dish.Dish;
//import jupiter.backend.shop.Shop;
//import jupiter.backend.shop.ShopRepository;
//import jupiter.backend.user.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.mongodb.core.query.Update;
//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Set;
//import java.util.concurrent.CopyOnWriteArrayList;
//import java.util.stream.Collectors;
//
//interface CustomizedOrderRepository{
//
//    void addOrderDetail(String userId, OrderDetail orderDetail);
//
////    void createOrder(String customerId, Order order);
//}
//
//class CustomizedOrderRepositoryImpl implements CustomizedOrderRepository {
//
//    @Autowired
//    MongoTemplate mongoTemplate;
//
//    public void addOrderDetail(String userId, OrderDetail orderDetail){
//        Query query = new Query(Criteria.where("userId").is(userId));
//        Update update = new Update().addToSet("orderDetailList", orderDetail);
//        mongoTemplate.updateFirst(query, update, Order.class);
//    }
////    @Autowired
////    MongoTemplate mongoTemplate;
////
////    // by default customerId, shopId and dishId are all available
////    public void createOrder(String customerId, Order newOrder){
////        // find the valid user
////        Query queryShopId = new Query(
////                new Criteria().andOperator(
////                        Criteria.where("_id").is(customerId),
////                        Criteria.where("orders")
////                                // because use elemMatch, only the matched element appears
////                                // that's why update addToSet could find this element.
////                                .elemMatch(Criteria
////                                        .where("shopId")
////                                        .is(newOrder.getShopId()))
////                )
////        );
////        User validUser = mongoTemplate.findOne(queryShopId, User.class);
////        if(validUser == null){
////            // user exists but no such shop
////            Query queryUser = new Query(
////                    Criteria.where("_id").is(customerId)
////            );
////            Update update = new Update().addToSet("orders", newOrder);
////            mongoTemplate.updateFirst(queryUser, update, User.class);
////        }
////        else{
////            // shop also exists
////            List<Order> existedOrderList = validUser
////                    .getOrders()
////                    .stream()
////                    .filter(shop ->
////                        shop.getShopId().equals(newOrder.getShopId())
////                    )
////                    .collect(Collectors.toList());
////            Order updatedOrder = existedOrderList.get(0);
////            Set<String> existedDishList = updatedOrder.getDishList();
////            existedDishList.addAll(newOrder.getDishList());
////            Update update = new Update().set("orders.$.dishList", existedDishList);
////            mongoTemplate.updateFirst(queryShopId, update, User.class);
////        }
////    }
//}
//
//@Repository
//public interface OrderRepository extends MongoRepository<Order, String>,
//        CustomizedOrderRepository
//{
//    @org.springframework.data.mongodb.repository.Query("{'userId': ?2, " +
//            "'orderDetailList.shopId': ?0, " +
//            "'orderDetailList.dishInfoList.dishId':?1}")
//    Order checkUserShopDishValid(String shopId, String dishId, String userId);
//}
