//package Archive.order;
//
//import Archive.jwt.JWT;
//import jupiter.backend.response.ResponseBody;
//import jupiter.backend.user.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RequestMapping("/v1")
//@RestController
//@CrossOrigin(origins = "*")
//public class OrderController {
//
//    @Autowired
//    JWT jwt;
//
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    OrderService orderService;
//
//    @PostMapping("/orders")
//    public ResponseEntity<ResponseBody> createOrder(@RequestBody OrderDetail orderDetail,
//                                                    @RequestHeader("token") String token){
//        try{
//            String loginName = jwt.isCustomer(token);
//            if(loginName != null){
//                String customerId =  userService.find_Id(loginName, "customer");
//                orderService.createOrder(customerId, orderDetail);
//                ResponseBody responseBody = new ResponseBody(true, "create order successfully", null);
//                return ResponseEntity.ok(responseBody);
//            }
//            else{
//                ResponseBody responseBody
//                        = new ResponseBody(null, "only customer can create order", null);
//                return ResponseEntity.ok(responseBody);
//            }
//        }
//        catch (Exception e){
//            ResponseBody responseBody = new ResponseBody(null, e.getMessage(), e);
//            return ResponseEntity.ok(responseBody);
//        }
//    }
//
//    @GetMapping("/orders")
//    public ResponseEntity<ResponseBody> listOrderUnderCustomer(@RequestHeader("token") String token){
//        try {
//            String loginName = jwt.isCustomer(token);
//            if (loginName != null) {
//                String customerId = userService.find_Id(loginName, "customer");
//                Order order = orderService.listOrderUnderCustomer(customerId);
//                ResponseBody responseBody =
//                        new ResponseBody(order, "list the order", null);
//                return ResponseEntity.ok(responseBody);
//            } else {
//                ResponseBody responseBody =
//                        new ResponseBody(null, "only customer can see the order", null);
//                return ResponseEntity.ok(responseBody);
//            }
//        }
//        catch (Exception e){
//            ResponseBody responseBody =
//                    new ResponseBody(null, e.getMessage(), e);
//            return ResponseEntity.ok(responseBody);
//        }
//    }
//
////    @Autowired
////    JWT jwt;
////
////    @Autowired
////    OrderService orderService;
////
////    @Autowired
////    UserService userService;
////
////    @Autowired
////    ShopService shopService;
////
////    @Autowired
////    DishService dishService;
////
////    @PostMapping("/orders")
////    public ResponseEntity<ResponseBody> createOrder(@RequestBody Order newOrder,
////                                                    @RequestHeader("token") String token){
////        try {
////            String loginName = jwt.isCustomer(token);
////            if (loginName != null) {
////                String loginId = userService.find_Id(loginName, "customer");
////                Order addedOrder = orderService.createOrder(loginId, newOrder);
////                ResponseBody responseBody =
////                        new ResponseBody(addedOrder, "add order", null);
////                return ResponseEntity.ok(responseBody);
////            }
////            else{
////                ResponseBody responseBody =
////                        new ResponseBody(null, "only customer could create token", null);
////                return ResponseEntity.ok(responseBody);
////            }
////        }
////        catch (Exception e){
////            ResponseBody responseBody =
////                    new ResponseBody(null, e.getMessage(), null);
////            return ResponseEntity.ok(responseBody);
////        }
////    }
////
////    @GetMapping("/orders")
////    public ResponseEntity<ResponseBody> listOrders(@RequestHeader("token") String token){
////        try {
////            String loginName = jwt.isCustomer(token);
////            if (loginName != null) {
////                String loginId = userService.find_Id(loginName, "customer");
////                // list all valid shops
////                List<Shop> listOrderDetail = orderService.listOrderDetail(loginId);
////                ResponseBody responseBody =
////                        new ResponseBody(listOrderDetail, "list order detail", null);
////                return ResponseEntity.ok(responseBody);
////            }
////            else{
////                ResponseBody responseBody =
////                        new ResponseBody(null, "only customer could create token", null);
////                return ResponseEntity.ok(responseBody);
////            }
////        }
////        catch (Exception e){
////            ResponseBody responseBody =
////                    new ResponseBody(null, e.getMessage(), null);
////            return ResponseEntity.ok(responseBody);
////        }
////    }
//
//}
