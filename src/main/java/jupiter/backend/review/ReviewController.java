package jupiter.backend.review;

import jupiter.backend.jwt.JWT;
import jupiter.backend.response.ResponseBody;
import jupiter.backend.user.User;
import jupiter.backend.user.UserRepository;
import jupiter.backend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1")
@RestController
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @Autowired
    UserService userService;

    @Autowired
    JWT jwt;

    @PostMapping("/shops/{shopId}/dishes/{dishId}/reviews")
    public ResponseEntity<ResponseBody> createReview(@PathVariable("shopId") String shopId,
                             @PathVariable("dishId") String dishId,
                             @RequestBody Review newReview,
                             @RequestHeader("token") String token){
        try{
            String loginName = jwt.isCustomer(token);
            if(loginName != null){
                String loginId = userService.find_Id(loginName, "customer");
                reviewService.createReview(shopId, dishId, loginId, newReview);
                ResponseBody responseBody =
                        new ResponseBody(true, "create it", null);
                return ResponseEntity.ok(responseBody);
            }
            else{
                ResponseBody responseBody =
                        new ResponseBody(null, "only customer can create review",null);
                return ResponseEntity.ok(responseBody);
            }
        }
        catch(Exception e){
            ResponseBody responseBody = new ResponseBody(null, e.getMessage(), e);
            return ResponseEntity.ok(responseBody);
        }
    }

    @GetMapping("/shops/{shopId}/dishes/{dishId}/reviews")
    public ResponseEntity<ResponseBody> listReviewUnderDish(@PathVariable("shopId") String shopId,
                                                            @PathVariable("dishId") String dishId){
        try{
            List<Review> reviewList = reviewService.listReviewUnderDish(shopId, dishId);
            ResponseBody responseBody =
                    new ResponseBody(reviewList, "get reviews under dish", null);
            return ResponseEntity.ok(responseBody);
        }
        catch (Exception e){
            ResponseBody responseBody = new ResponseBody(null, e.getMessage(), e);
            return ResponseEntity.ok(responseBody);
        }
    }

}
