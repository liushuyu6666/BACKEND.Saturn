//package Archive.dish;
//
//import com.mongodb.BasicDBObject;
//import jupiter.backend.review.Review;
//import jupiter.backend.shop.Shop;
//import jupiter.backend.shop.ShopRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.mongodb.core.query.Update;
//import org.springframework.data.mongodb.repository.MongoRepository;
//
//import org.springframework.stereotype.Repository;
//
//interface CustomizedReviewRepository{
////    Review updateReview(String shopId, String dishId, Review review);
//
//    void createReview(String shopId, String dishId, Review review);
//
////    void deleteReview(String shopId, String dishId, String reviewId);
//}
//
//class CustomizedReviewRepositoryImpl implements CustomizedReviewRepository{
//
//    @Autowired
//    MongoTemplate mongoTemplate;
//
//    @Autowired
//    ShopRepository shopRepository;
//
//    @Autowired
//    DishRepository dishRepository;
//
//    public void createReview(String shopId, String dishId, Review review){
//        Query query = new Query(new Criteria().andOperator(
//                Criteria.where("_id").is(shopId),
//                Criteria.where("dishes")
//                .elemMatch(Criteria
//                .where("_id")
//                .is(dishId))
//        ));
//        Update update = new Update().push("dishes.$.review", review);
//        mongoTemplate.updateFirst(query, update, Shop.class);
//    }
//
////
////    public void deleteReview(String shopId, String dishId){
////        Query query = new Query(new Criteria().andOperator(
////                Criteria.where("_id").is(shopId),
////                Criteria.where("dishes")
////                        .elemMatch(Criteria
////                                .where("_id")
////                                .is(dishId))));
////        Update update = new Update().pull("dishes",
////                new BasicDBObject("_id", dishId));
////        System.out.println(update);
////        mongoTemplate.updateMulti(query, update, Shop.class);
////    }
//}
//
//@Repository
//public interface ReviewRepository extends MongoRepository<Review, String>,
//        CustomizedReviewRepository
//{
//
////    @org.springframework.data.mongodb.repository.Query(value = "{'_id': ?0}")
////    Shop listAllReviewInShop(String id);
////
//    // find a specific dish in the shop
//    @org.springframework.data.mongodb.repository
//            .Query(value = "{'_id': ?0, 'dishes._id': ?1}",
//            fields = "{'dishes': 1}")
//    Review findSpecificReviewByUsername(String shopId, String dishId);
//
//}
//
