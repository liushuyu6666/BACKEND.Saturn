//package Archive.order;
//
//import jupiter.backend.shop.ShopRepository;
//import jupiter.backend.user.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class OrderService {
//
//    @Autowired
//    OrderRepository orderRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    ShopRepository shopRepository;
//
//    public void createOrder(String userId, OrderDetail newOrderDetail) throws Exception{
//        // check if userId already exist
//        Order order = orderRepository.findById(userId).orElse(null);
//        if(order == null){
//            Order newOrder = new Order();
//            newOrder.setUsername(userRepository.findBy_IdSafely(userId).getUsername());
//            newOrder.setUserId(userId);
//            List<OrderDetail> orderDetailList = new ArrayList<>();
//            newOrderDetail.setCreateAt();
//            orderDetailList.add(newOrderDetail);
//            newOrder.setOrderDetailList(orderDetailList);
//            orderRepository.save(newOrder);
//        }
//        else{
//            newOrderDetail.setCreateAt();
//            orderRepository.addOrderDetail(userId, newOrderDetail);
//        }
//    }
//
//    public Order listOrderUnderCustomer(String userId) throws Exception{
//        try{
//            Order order = orderRepository.findById(userId).orElse(null);
//            return order;
//        }
//        catch (Exception e){
//            throw e;
//        }
//
//    }
//
////    @Autowired
////    OrderRepository orderRepository;
////
////    @Autowired
////    UserRepository userRepository;
////
////    @Autowired
////    ShopRepository shopRepository;
////
////    public Order createOrder(String userId, Order newOrder) throws Exception{
////        orderRepository.createOrder(userId, newOrder);
////        return newOrder;
////    }
////
////    public List<Order> listOrder(String userId){
////        User user = userRepository.findOrderBy_IdSafely(userId);
////        return user.getOrders();
////    }
////
////    public List<Shop> listOrderDetail(String userId) throws Exception{
////        User user = userRepository.findOrderBy_IdSafely(userId);
////        List<Order> orders = user.getOrders();
////        List<Shop> shopList = new ArrayList<>();
////        Shop shop;
////        for(Order order : orders){
////            // shop
////            shop = new Shop();
////            String shopId = order.getShopId();
////            Shop targetShop = shopRepository.findBy_id(shopId);
////            shop.setName(targetShop.getName());
////            shop.set_id(targetShop.get_id());
////            shop.setCategories(targetShop.getCategories());
////
////            // dish
////            Dish dish;
////            List<Dish> dishList = new ArrayList<>();
////            for(String dishId : order.getDishList()){
////                dish = new Dish();
////                Shop dishValid = shopRepository
////                        .findDishBy_idAndDish_id(shopId, dishId);
////                if(dishValid != null) {
////                    Dish targetDish = dishValid.getDishes().get(0);
////                    dish.setName(targetDish.getName());
////                    dish.setCategory(targetDish.getCategory());
////                    dish.setPrice(targetDish.getPrice());
////                    dish.setReview(targetDish
////                            .getReview()
////                            .stream()
////                            .filter(review -> review
////                                    .getUserId()
////                                    .equals(userId))
////                            .collect(Collectors.toList()));
////                    // review
////                    dishList.add(dish);
////                }
////            }
////            shop.setDishes(dishList);
////            shopList.add(shop);
////        }
////        return shopList;
////    }
//
//}
