package jupiter.backend.review;

import jupiter.backend.dish.Dish;
import jupiter.backend.dish.DishRepository;
import jupiter.backend.dish.ReviewRepository;
import jupiter.backend.lsyexception.LsyException;
import jupiter.backend.order.OrderRepository;
import jupiter.backend.shop.ShopRepository;
import jupiter.backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    DishRepository dishRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    public void createReview(String shopId, String dishId, String customerId, Review review) throws LsyException {
        if(!shopRepository.existsById(shopId)){
            throw new LsyException("no such shop");
        }
        else if(shopRepository.findDishBy_idAndDish_id(shopId, dishId) == null){
            throw new LsyException("no such dish in this shop");
        }
        // check if the customer ordered this dish before
        else if(orderRepository
                .checkUserShopDishValid(shopId, dishId, customerId) == null){
            throw new LsyException("the login customer didn't order this dish");
        }
        try{
            Review newReview = new Review();
            newReview.set_id(customerId);
            newReview.setUserId(customerId);
            newReview.setContent(review.getContent());
            newReview.setStars(review.getStars());
            newReview.setCreateAt();
            reviewRepository.createReview(shopId, dishId, newReview);
        }
        catch(Exception e){
            throw e;
        }
    }

    public List<Review> listReviewUnderDish(String shopId, String dishId) throws Exception{
        if(!shopRepository.existsById(shopId)){
            throw new LsyException("no such shop");
        }
        else if(shopRepository.findDishBy_idAndDish_id(shopId, dishId) == null){
            throw new LsyException("no such dish in this shop");
        }
        else{
            Dish targetDish = dishRepository.findDishById(shopId, dishId);
            List<Review> reviews = targetDish.getReview();
            return reviews;
        }
    }
}
