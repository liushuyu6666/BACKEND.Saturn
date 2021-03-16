package jupiter.backend.order;

import jupiter.backend.dish.Dish;
import jupiter.backend.dish.DishRepository;
import jupiter.backend.shop.Shop;
import jupiter.backend.shop.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    DishRepository dishRepository;

    public Order createOrder(Order newOrder, String userId){
        newOrder.setUserId(userId);
        return orderRepository.save(newOrder);
    }

    public List<OrderDetail> listOrders(String customerId){

        List<Order> orderList = orderRepository.findAllByUserId(customerId);
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail;
        OrderGallery orderGallery;
        for(Order order: orderList){
            List<OrderGallery> orderGalleryList = new ArrayList<>();
            for (Map.Entry<String, Integer> entry: order.getDishAmount().entrySet()){
                Dish dishItem = dishRepository.findById(entry.getKey()).orElse(null);
                if(dishItem != null){
                    Shop targetShop = shopRepository.findShopById(dishItem.getShopId()).orElse(null);
                    if(targetShop != null){
                        orderGallery = new OrderGallery(dishItem.getShopId(),
                                targetShop.getShopName(),
                                dishItem.getId(),
                                dishItem.getName(),
                                entry.getValue());
                        orderGalleryList.add(orderGallery);
                    }
                }
            }
            orderDetail = new OrderDetail(order.getId(),
                    customerId, orderGalleryList, order.getCreateAt());
            orderDetailList.add(orderDetail);
        }
        return orderDetailList;
    }

}
