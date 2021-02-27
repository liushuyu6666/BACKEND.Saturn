package jupiter.backend.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public Order createOrder(Order newOrder, String userId){
        newOrder.setUserId(userId);
        return orderRepository.save(newOrder);
    }

}
