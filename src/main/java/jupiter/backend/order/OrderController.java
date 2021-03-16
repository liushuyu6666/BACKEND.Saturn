package jupiter.backend.order;

import jupiter.backend.core.AuthenticationService;
import jupiter.backend.dish.DishService;
import jupiter.backend.payload.response.MessageResponse;
import jupiter.backend.payload.response.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/v1/jupiter")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class OrderController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    OrderService orderService;

    @Autowired
    DishService dishService;

    @PostMapping("/orders")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> createOrder(
            Authentication authentication,
            @RequestBody Order newOrder
    ){
        if(newOrder.getDishAmount().isEmpty()){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("no order in the list"));
        }
        for(Map.Entry<String, Integer> entry : newOrder.getDishAmount().entrySet()) {
            if(!dishService.existsById(entry.getKey())){
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse(String.format(
                                "no such dishId: %s", entry.getKey()
                        )));
            }
            if(entry.getValue() <= 0 ){
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse(String.format(
                                "amount of %s should be larger than 0",
                                Objects.requireNonNull(dishService.findById(entry.getKey()).orElse(null)).getName()
                        )));
            }
        }
        String userId = authenticationService.parseAuthenticationGetId(authentication);
        Order savedOrder = orderService.createOrder(newOrder, userId);
        ResponseBody responseBody = new ResponseBody(savedOrder,
                "create order successfully", null);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/orders")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> listOrders(
            Authentication authentication
    ){
        String userId = authenticationService.parseAuthenticationGetId(authentication);
        List<OrderDetail> orderDetailList = orderService.listOrders(userId);
        ResponseBody responseBody = new ResponseBody(orderDetailList, "list all orders", null);
        return ResponseEntity.ok(responseBody);
    }

}
