package jupiter.backend.order;

import jupiter.backend.jwt.JWT;
import jupiter.backend.response.ResponseBody;
import jupiter.backend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.BooleanOperators;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1")
@RestController
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    JWT jwt;

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @PostMapping("/orders")
    public ResponseEntity<ResponseBody> createOrder(@RequestBody Order newOrder,
                                                    @RequestHeader("token") String token){
        try {
            String loginName = jwt.isCustomer(token);
            if (loginName != null) {
                String loginId = userService.find_Id(loginName, "customer");
                Order addedOrder = orderService.createOrder(loginId, newOrder);
                ResponseBody responseBody =
                        new ResponseBody(addedOrder, "add order", null);
                return ResponseEntity.ok(responseBody);
            }
            else{
                ResponseBody responseBody =
                        new ResponseBody(null, "only customer could create token", null);
                return ResponseEntity.ok(responseBody);
            }
        }
        catch (Exception e){
            ResponseBody responseBody =
                    new ResponseBody(null, e.getMessage(), null);
            return ResponseEntity.ok(responseBody);
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<ResponseBody> listOrders(@RequestHeader("token") String token){
        try {
            String loginName = jwt.isCustomer(token);
            if (loginName != null) {
                String loginId = userService.find_Id(loginName, "customer");
                List<Order> listOrder = orderService.listOrder(loginId);
                ResponseBody responseBody =
                        new ResponseBody(listOrder, "list order", null);
                return ResponseEntity.ok(responseBody);
            }
            else{
                ResponseBody responseBody =
                        new ResponseBody(null, "only customer could create token", null);
                return ResponseEntity.ok(responseBody);
            }
        }
        catch (Exception e){
            ResponseBody responseBody =
                    new ResponseBody(null, e.getMessage(), null);
            return ResponseEntity.ok(responseBody);
        }
    }

}
