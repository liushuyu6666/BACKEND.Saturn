package jupiter.Archive.order;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

class DishInfo{

    private String dishId;

    private String dishName;

    private Float price;

    private Integer number;

    public DishInfo() {
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}

class OrderDetail {

    private Date createAt;

    private String shopId;

    private String shopName;

    private List<DishInfo> dishInfoList;

    public OrderDetail() {
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt() {
        Date now = new Date();
        this.createAt = now;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<DishInfo> getDishInfoList() {
        return dishInfoList;
    }

    public void setDishInfoList(List<DishInfo> dishInfoList) {
        this.dishInfoList = dishInfoList;
    }
}

@Document(collection = "order")
public class Order {

    private String username;

    @Id
    private String userId;

    private List<OrderDetail> orderDetailList;

    public Order() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    //    private String shopId;
//
//    private Set<String> dishList;
//
//    public Order() {
//    }
//
//    public String getShopId() {
//        return shopId;
//    }
//
//    public void setShopId(String shopId) {
//        this.shopId = shopId;
//    }
//
//    public Set<String> getDishList() {
//        return dishList;
//    }
//
//    public void setDishList(Set<String> dishList) {
//        this.dishList = dishList;
//    }
}
